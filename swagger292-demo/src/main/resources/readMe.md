###Swagger

- #### 0: 资料
[官网](http://springfox.github.io/springfox/docs/current/#quick-start-guides)

[网上资料](https://blog.csdn.net/qq_40644236/article/details/88620333)

[B站视频](https://www.bilibili.com/video/BV1Y441197Lw?p=2)
---
- #### 1：引入依赖包
当前demo用的是`2.9.2`版本
```xml
<dependencies>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.9.2</version>
    </dependency>
</dependencies>
```
---
- #### 2:开启`swagger` 
`@EnableSwagger2`
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {   
}
```

现在可以访问了
http://localhost:8080/swagger-ui.html

---
- #### 3:配置
