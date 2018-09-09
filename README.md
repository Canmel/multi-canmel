# multi-canmel 

```
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃    ━     ┃
 *  ┃          ┃
 *  ┃  ┳┛  ┗┳  ┃
 *  ┃          ┃
 *  ┃    ┻     ┃
 *  ┃          ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃             ┣┓
 *     ┃             ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫       ┃┫┫
 *      ┗┻┛       ┗┻┛
```

# 各部分说明
## auth2
```
> SpringBoot Security oauth2密码授权模式（授权服务器）

> 与iweb结对，iweb为resouce server 
```

## config
```
> 存放一些主要的配置文件
> 当需要使用的时候，引入改项目，然后使用配置类继承需要使用的配置文件即可

```

## core
```
> 当多个模块需要交互使用相同的类型，这种类型定义，应该在这个模块中
> 多模块协作时，不需要重复定义相同的类型
```

## ioauth2
```
> SpringBoot Security oauth2密码授权模式（授权服务器）
> 与restful结对，与restful 为resouce server 
> 完成了仓库内canmel的后台接口，在xx/api/。。以下, 那是一个Angular4的后台管理项目
> ...
```

## iweb
```
> 学习使用， 使用anth2当做授权服务，当前模块作为资源服务管理
> ...
```

## restful
```
> 相对`ioauth2`模块的资源服务模块
> 完成的用户-角色-权限管理
> 接口部分添加了用户权限限制
> 基本完成Angular4项目`canmel`的后台接口 [Canmel](https://github.com/Canmel/camel)

```

### 启动顺序
1. 启动授权服务器（ioauth2）
2. 启动restful
3. 对应前端camel （angular）

## web
```
还没想好。。。哈哈

```

