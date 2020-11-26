###1.引入依赖包

####主要的
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```
####次要的
```xml
<properties>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.3.4.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-config</artifactId>
        <version>5.3.4.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-web</artifactId>
        <version>5.3.4.RELEASE</version>
    </dependency>
</properties>
```

###2.application.yml
```yaml
springfox:
  documentation:
    swagger:
      v2:
        use-model-v3: false
```
###3.认证要放行
设置白名单
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/documentation/swagger-resources/**",
            "/documentation/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/documentation/webjars/**",
            "/documentation/swagger-ui/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }
}
```
现在就可以用了，但如果要精细控,还需要以下配置

###3.配置
[官网配置](http://springfox.github.io/springfox/docs/current/#quick-start-guides)
```java
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket myDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                    // 配置哪些接口暴露给swagger
                .select()
                    //扫描哪个包
                .apis(RequestHandlerSelectors.basePackage("com.wuxia.ms.drivinglicense.controller"))
                    // 这个是更精准的控制(regex, ant, any, none)
                    //.paths(PathSelectors.any())
                .build();
                    //在接口加上前缀path
                    //.pathMapping("/") 

    }
}

```
###4.使用
[官网](http://springfox.github.io/springfox/docs/current/#quick-start-guides)
[网上资料](https://blog.csdn.net/qq_40644236/article/details/88620333)