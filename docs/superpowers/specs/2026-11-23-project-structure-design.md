# 项目结构整理

## 范围

按用户提供的目录树整理 `warehouse_root`，用户已确认缺失类只建立可编译骨架，业务逻辑后续实现。

## 方案

直接整理当前工作目录，保留已有未提交改动和实时业务源码；不另建项目，不复制一套业务实现。
父工程只管理模块、仓库、依赖版本和公共构建设置；两个 jar 子模块为 `shop_realtime` 和 `project/canal2kafka`。

## 修改

- 将 Canal 子模块上移一层，修正 Java 包路径和父 POM 相对路径。
- 父 POM 去除重复模块，统一版本；子 POM 仅声明所需依赖。
- 新建 CanalEntity、OrderGoodsWideEntity、OrderDetailWideBizProcess、ClusterCanalClient 骨架及 Canal 配置模板。
- 清理三个 Maven archetype 的 Hello World 模板源码，仅删除确认无业务内容的文件。
- 不修改现有实时程序、应用配置或外部服务。

## 验证

核对目标目录、包声明、模块和父工程关系，使用 Java 8 执行 Maven validate/test/package。
对照整理前 SHA256，确认原有 Scala 源码和实时资源未变；不启动会连接 Kafka、Redis、MySQL 的入口。
