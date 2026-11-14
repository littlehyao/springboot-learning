# springboot-configuration

---

## 模块说明

本模块演示了 Spring Boot 配置文件的切换以及配置属性绑定。

## 快速入门

### 配置文件切换

- 创建不同的配置文件，文件名称前缀为 `application-`，名称后缀一般都是环境名称，例如 `dev`、`test`、`prod`。
- 在 `application.yml` 配置文件中添加 `spring.profiles.active` 属性，激活指定的配置文件。

### 配置属性绑定

- 在 `application.yml` 配置文件中添加自定义配置项（单个配置项或成组的配置项集）。
- 对于成组的配置项集，新建配置属性类，成员变量对应配置项集的属性，在类上添加 `@ConfigurationProperties` 注解实现配置类与配置文件进行绑定。
- 新建一个 `@Configuration` 配置类，在类上添加 `@EnableConfigurationProperties` 注解，启用配置属性绑定。
- 也可以直接在 `@ConfigurationProperties` 注解的配置属性类上添加 `@Configuration` 注解，实现配置属性类与配置文件的绑定。
- 对于单个配置项，在配置类或 Bean 类中定义一个成员变量, 添加 `@Value` 注解实现单个配置项的绑定。

## 知识汇总

### 配置文件切换

- 主配置文件 `application.yml`和各环境配置文件 `application-*.yml`。
- 服务启动后，先加载主配置文件，然后通过 `spring.profiles.active` 激活指定环境的配置文件。
- 加载`application-*.yml` 中的配置项覆盖 `application.yml` 中的同名的配置项，或将新的配置项添加进来。
- 除了配置文件切换，通过 `@Profile` 注解可以指定配置类或 Bean 类只在指定环境下生效。
  > 注：`@Profile` 注解放在类上表示整个配置类按环境切换。
  ```java
  @Configuration
  public class CacheConfiguration {
      @Bean
      @Profile("dev")
      public CacheService cacheService() {
          return new InMemoryCacheService(); // 开发用内存缓存
      }
      
      @Bean
      @Profile("prod")
      public CacheService cacheService() {
          return new RedisCacheService();    // 生产用 Redis
      }
  }
  ```


### 配置属性绑定

- `@ConfigurationProperties` 通过 `value` 属性指定前缀，将配置文件中该前缀的属性绑定到配置类中。
- 配置类上添加 `@Configuration` 注解，否则不生效。
- `@Value` 通过 `value` 属性指定配置项的键，将配置文件中的属性值绑定到成员变量中。
- 如果 `@Value` 指定的配置项没有，也可以通过以下方式提供默认值。
```java
    @Value("${time-zone:Asia/Shanghai}")
    private String timeZone;
```
- 配置属性类中可以使用 Spring 框架提供的 Validation 来校验配置值。
```java
@Validated
@ConfigurationProperties(prefix = "my-app")
public class MyAppProperties {
    //...
    @NotBlank(message = "环境配置不能为空")
    @Pattern(regexp = "dev|st|uat|prod", message = "环境配置必须是 dev|st|uat|prod 中的一个")
    private String env;
    //...
}
```