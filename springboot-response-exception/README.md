# springboot-response-exception

*本模块演示了 Spring Boot 框架中统一响应包装和全局异常处理的实现方式。*

---

## 快速入门

### 统一响应包装

- 创建一个类 `ResponseWrapperAdvice`，实现 `ResponseBodyAdvice<Object>` 接口，在类上添加 `@ControllerAdvice` 注解。
- 实现 `supports` 方法，该方法返回是否支持当前请求的响应包装。
- 实现 `beforeBodyWrite` 方法，该方法返回包装后的响应体。

### 全局异常处理

- 自定义全局统一的异常类，应用异常 `AppException` （或者业务异常 `BizException`），继承 `RuntimeException` 类。
- 创建一个类 `GlobalExceptionHandler`，在类上添加 `@RestControllerAdvice` 注解。
- 添加异常处理方法 `handleAppException(AppException e)`，在方法上添加 `@ExceptionHandler(AppException.class)` 注解，在方法中实现你期望的异常处理逻辑。
- 如果想要自定义处理内部异常，可以继承 `ResponseEntityExceptionHandler` 类，然后重写对应的处理方法。

## 知识汇总

### 统一响应包装

- 这种一般都是在前后端分离开发模式中，要求 RESTful 接口响应成功或失败后返回统一的 JSON 数据。

- 返回 JSON 数据中，固定包含 `code`响应码，`message` 响应信息， `data` 响应数据。

- 其他返回数据可以根据需求拓展，比如 `trance` 请求链路 id，`app-name` 响应应用等等。

### 全局异常处理

- 为了配合全局异常处理，一般需要自定义统一的异常类，应用异常 `AppException` 或者业务异常 `BizException` ，继承 `RuntimeException` 类，然后在全局异常处理类中处理自定义的统一异常类。
  
- 为了防止代码中捕捉异常遗漏，建议在全局异常处理类中添加一个默认的异常处理方法 `handleException(Exception e)`，用于处理所有未被捕获的异常。
  
- 统一约定，内部异常返回对应的 `400`/`500` 状态码，自定义异常照常返回 `200` 状态码，接口响应成功与否根据响应体中 `code` 判断，约定 `99999` 为响应成功，其他为响应失败。