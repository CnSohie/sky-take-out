# 天空外卖 (Sky Take-out)

基于 Spring Boot + MyBatis + MySQL 开发的全栈外卖管理系统。

## 🛠️ 技术栈
* **后端**: Java, Spring Boot, MyBatis-Plus, JWT (身份校验), Redis (缓存), Nginx (反向代理)
* **前端**: Vue, Element UI
* **工具**: Maven, Git, Swagger/Knife4j (接口文档)

## 📂 项目结构说明
* **sky-common**: 存放公共工具类、异常类、常量及配置。
* **sky-pojo**: 存放实体类 (Entity)、数据传输对象 (DTO) 和 视图对象 (VO)。
* **sky-server**: 项目的核心业务逻辑处理，包含 Controller, Service 和 Mapper。

## 📖 快速开始
1. 克隆项目：`git clone https://github.com/CnSohie/sky-take-out.git`
2. 导入 SQL 脚本并配置 `application.yml` 中的数据库连接。
3. 启动 `SkyApplication` 主类。
