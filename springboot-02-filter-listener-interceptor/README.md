# springboot-02-filter-listener-interceptor

---

### 模块说明
本模块主要演示 Spring Boot 中 filter 过滤器，listener 监听器以及 interceptor 拦截器的实现和配置.

### 快速入门

#### filter 过滤器
- 自定义一个过滤器类，实现 `Filter` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，通过 `@Bean` 注解返回封装该过滤器的 `FilterRegistrationBean` 实例。
- 或者定义一个 `@Configuration` 配置类，通过 `@Bean` + `@FilterRegistration` 注解返回该过滤器的实例。

#### listener 监听器
- 自定义一个监听器类，实现 `ServletContextListener` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，通过 `@Bean` 注解返回封装该监听器的 `ServletListenerRegistrationBean` 实例。

#### interceptor 拦截器
- 自定义一个拦截器类，实现 `HandlerInterceptor` 接口。
- 实现接口方法。
- 定义一个 `@Configuration` 配置类，实现 `WebMvcConfigurer` 接口，实现 `addInterceptors` 方法，将你自定义的拦截器添加进去。

### 知识汇总

- 过滤器和监听器是 Servlet 规范中的概念，而拦截器是 Spring 框架中的概念。
- 过滤器用于对请求进行预处理，例如 CSRF 拦截，XSS 过滤，链路追踪等。
- 监听器用于监听 ServletContext，HttpSession，ServletRequest 的生命周期事件，用于监控作用域以及作用域中属性的变化。
- 拦截器用于拦截对 Controller 的请求，可以在请求处理前后进行一些处理，例如权限检查等。
