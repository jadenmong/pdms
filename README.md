# 企业项目管理系统

## 介绍
该系统主要为用户提供项目管理服务，比如提供创建和管理项目，邀请用户，用户权限管理，团队建设等服务，主要有用户模块，项目模块，团队模块，菜单模块，信息模块，基础数据模块等。

## 软件架构
软件架构说明：由spring boot +spring+SpringMvc+layui开发

### 技术：
- jdk：1.8
- 核心框架：spring boot 2.1.12.RELEASE
- 安全框架：Apache Shiro
- 工作流引擎：Activiti
- 数据库连接池：druid
- 视图框架：spring mvc
- 持久层框架：MyBatis
- 模板引擎：freemarker
- 缓存：redis、ehcache
- 前端页面：layui


## 启动说明
```bash
git clone https://gitee.com/bweird/lenosp.git
mvn clean package
mvn package
java -jar len-web.jar
```
- db使用mysql，项目数据库在 根目录db文件夹下，
导入数据库后 设定数据库用户名密码 在文件lenosp\len-web\src\main\resources\application.yml中
项目开始会报实体类 get set错误，这是正常的，因为本项目entity使用的是 lombok 大大简化了代码量
您可以直接运行，项目可以正常启动。
如何消除？
如果您使用的为idea 只需要file -> setting->plugins->Browse Repositeories 输入 lombok 集成插件重启idea即可消除错误
如果您使用 eclipse 需要下载 lombk jar包 手动集成。
