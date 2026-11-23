# Project Structure Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 按用户目录树整理两个 Maven 子模块，并补齐可编译骨架。

**Architecture:** 父 POM 管理模块和版本；实时模块保留业务源码；Canal 模块移动至 project/canal2kafka。

**Tech Stack:** Java 8, Scala 2.11.12, Flink 1.9.0, Maven 3.9.9。

**Spec:** `docs/superpowers/specs/2026-11-23-project-structure-design.md`

## Global Constraints

- 保留用户已有未提交改动，不提交或重置索引。
- 缺失业务文件只写带 TODO 说明的可编译骨架，不臆造字段、消息格式或业务实现。
- 保留已有实时配置及源码；不连接外部业务服务。

## Review Focus

- 父 POM 无重复模块，所有模块和 parent.relativePath 均指向实际文件。
- Scala 编译插件仅在实时模块启用；Java 子模块使用 Java 8。
- 所有类包声明与目录一致，旧嵌套目录不保留源码。
- 两个模块的配置文件打包进入对应 jar，骨架不会被自动接入现有业务。
- 版本统一不改变 Flink、Scala、CDH 和现有实时依赖版本。

## Task 1: 整理 Maven 和 Canal 目录

- [x] 基线检查：`mvn -o validate` 因模块重复、父 POM 路径错误失败。
- [x] 修改三个 POM，移动 Canal 源码并修正包名。
- [x] 删除已确认的三个 Hello World 模板文件，清理移动后空目录。
- [x] 运行 `mvn -o -Dmaven.repo.local=C:/Users/25783/.m2/repository validate`，三个 reactor 项目全部成功。

## Task 2: 补齐骨架及资源

- [x] 创建两个任务27实体、业务处理对象、ClusterCanalClient 和 Canal 资源模板。
- [x] 核对22个目标文件存在，15个源码文件路径与包名一致。
- [x] 使用 Java 8 执行 Maven package（包含 test 阶段），核对两个 jar 内类及资源。
- [x] 核对已有业务文件 SHA256，审查改动并记录验证结果。

## 验证结果

- 使用 Java 8 及 Maven 3.9.9，`package` 的三个 reactor 项目均为 SUCCESS；两个子模块当前没有自动化测试。
- 首次离线编译因本地缺少编译插件的传递依赖失败；获准下载依赖后完整构建成功。
- 两个 jar 均包含对应配置及新增骨架类，不含已移除的 App.class 或重复包路径。
- 原有12个 Scala 源码和实时资源文件 SHA256 全部一致。
- 只读独立复核未发现重要缺陷，`git diff --check` 通过。
- Canal 依赖统一使用父 POM 中的 Kafka 2.2.1、Typesafe Config 1.3.4、SLF4J 1.7.25；Canal client 仍为1.1.2。
- 保留用户索引和既有未提交改动，未创建提交；未启动 Kafka、Redis、MySQL 或 Canal 服务。
