# springboot-02-filter-listener-interceptor

---

### 模块说明
本模块主要演示 Spring Boot 中 filter 过滤器，listener 监听器以及 interceptor 拦截器的实现和配置.

### 快速入门

#### filter 过滤器
- 自定义一个过滤器类，实现 `Filter` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，返回封装该过滤器的 `FilterRegistrationBean` 类型的 bean。

#### listener 监听器
- 自定义一个监听器类，实现 `ServletContextListener` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，返回封装该监听器的 `ServletListenerRegistrationBean` 类型的 bean。

#### interceptor 拦截器
- 自定义一个拦截器类，实现 `HandlerInterceptor` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，实现 `WebMvcConfigurer` 接口，重写 `addInterceptors` 方法，将你自定义的拦截器添加进去。



