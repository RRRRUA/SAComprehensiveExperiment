# 通讯录管理系统

一个使用 **JavaFX + Hibernate + MySQL** 实现的桌面通讯录实验项目，支持联系人查询、创建、编辑、删除与持久化。

仓库保留 IntelliJ IDEA 项目文件，当前没有 Maven/Gradle 构建脚本和完整依赖包，需要先配置本地开发环境。

## 功能

- 展示全部联系人。
- 创建联系人，保存姓名、电话、地址与创建时间。
- 在表格中编辑联系人并保存。
- 删除选中联系人。
- 按姓名、电话或地址进行模糊搜索。

## 目录结构

```text
src/
├── Main/Main.java                JavaFX 主类 Main.Main
├── Controller/                   主界面与创建联系人控制器
├── Model/                        Contracts 实体、DAO、Service、映射文件
├── View/                         MainUI.fxml、CreateUI.fxml
└── resources/hibernate.cfg.xml   Hibernate 数据库配置
SAComprehensiveExperiment.iml    IntelliJ IDEA 模块文件
```

## 环境准备

- IntelliJ IDEA 或其他能够配置 Java classpath 的 IDE。
- JDK 和与该 JDK 匹配的 JavaFX；部分 JDK 8 发行版附带 JavaFX，其他环境需要单独安装。
- 与代码使用的 `org.hibernate.*` 和 `javax.persistence.*` API 兼容的 Hibernate/JPA 依赖，例如 Hibernate 5.x 体系，并包含其传递依赖。
- MySQL 与 Connector/J 驱动，驱动类为 `com.mysql.cj.jdbc.Driver`。

项目未锁定上述依赖版本，不应直接换成使用 `jakarta.persistence` 的 Hibernate 6 并假设无需迁移。

## 数据库配置

配置位于 [src/resources/hibernate.cfg.xml](src/resources/hibernate.cfg.xml)，当前 URL 指向本机 `Hibernate` 数据库。请先在本地创建该数据库，再设置自己的连接信息。

| 配置项 | 说明 |
| --- | --- |
| `hibernate.connection.url` | 本机 MySQL JDBC URL |
| `hibernate.connection.username` | 数据库用户 |
| `hibernate.connection.password` | 本地数据库密码 |
| `hibernate.hbm2ddl.auto` | 当前为 `update`，需要相应建表/改表权限 |

实体为 `Model.Contracts`，映射到 `Hibernate.Contacts`，字段包含 `Id`、`Name`、`Phone`、`Address` 和 `CreateDate`。仓库没有 SQL 初始化文件；Hibernate 配置可更新表结构，但不会替你创建 MySQL 数据库。

连接配置会被程序直接读取，不会自动从新增的 `.env` 加载。配置文件中的本地凭据不要随提交传播。

## 在 IntelliJ IDEA 中运行

```bash
git clone https://github.com/RRRRUA/SAComprehensiveExperiment.git
cd SAComprehensiveExperiment
```

1. 打开项目，选择项目 JDK，把 `src/` 标记为源码目录。
2. 在项目库中加入 JavaFX、Hibernate/JPA、MySQL 驱动及依赖；`.iml` 中的 `lib` 是库引用，不代表 JAR 已随仓库提供。
3. 确保构建输出保留 `View/*.fxml` 和 `resources/hibernate.cfg.xml` 的相对路径。
4. 完成本机数据库配置。
5. 检查主类的 FXML 资源路径，再运行 `Main.Main`。

**现有路径问题：**`Main.java` 加载 `/view/MainUI.fxml`，仓库实际目录为 `View/`。在区分大小写的文件系统或 JAR 资源中会失败，应先把加载路径与实际目录大小写对齐。本文记录这个启动前提，没有代替你修改程序。

## 常见问题

| 错误 | 排查方向 |
| --- | --- |
| 找不到 `javafx.*` | 配置 JavaFX 库；较新 JDK 还需正确配置模块路径 |
| FXML `Location is not set` | 检查资源是否复制到 classpath，以及 `View`/`view` 大小写 |
| 找不到 `org.hibernate.*` 或 `javax.persistence.*` | 检查 Hibernate/JPA 依赖体系 |
| MySQL 连接失败 | 检查服务、数据库是否存在、驱动、用户权限与连接配置 |

## 验证与项目状态

本次检查了 JavaFX 入口、DAO/Service、实体和 Hibernate 配置。当前环境缺少项目依赖与数据库，未完成桌面程序运行验证。后续若维护此项目，建议增加统一构建文件和依赖版本锁定。

## 贡献与许可证

欢迎通过 PR 补充可复现的构建环境、修正资源路径和完善测试。仓库目前没有根目录许可证文件，复用或分发前请确认授权。
