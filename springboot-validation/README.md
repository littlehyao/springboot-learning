## springboot-validation

*本模块演示了 Spring Validation 参数校验。*

---

### 快速入门

#### 使用 Spring Validation

- 引入 `spring-boot-starter-validation` 依赖。
- 在请求参数对象中根据需求使用校验注解。
- 在 controller 请求响应方法的参数中使用 `@Validated` 注解生效。

#### 自定义校验注解

- 新建一个校验注解，标注元注解：`@Target`可以是成员变量，方法参数，类型使用处等等，`@Retention` 默认 `RetentionPolicy.RUNTIME`。

- 添加 `jakarta.validation.Constraint` 注解，通过 `validatedBy` 属性指定该注解对应的校验器类。

- 按照 Jakarta Validation 规范添加参数和默认值。

- 新建校验器类，实现 `jakarta.validation.ConstraintValidator` 接口，接口泛型参数中，第一个标明校验注解，第二个标明支持的校验类型，在 `isValid` 接口方法中实现校验逻辑。

- 像使用 `@NotNull` 一样使用它。

### 知识汇总

#### Jakarta 标准注解

- `@NotNull`: 校验字段不能为 `null`。

  > 支持任意类型。

- `@NotEmpty`: 校验字段不能为 `null` 和空。

  > 支持字符串，集合，数组，Map。

- `@NotBlank`: 校验字段不能为空，并且不能只包含空格。

  > 只支持字符串类型。

- `@Size`: 校验字段长度在指定范围内。

  > 支持字符串，集合，数组，Map。
  >
  > `min` 和 `max` 属性分别表示最小和最大长度。

- `@Min`: 校验字段值不能小于指定值。 

  > 支持 `BigDecimal`, `BigInteger`, `byte`, `short`, `int`, `long` 及其包装类。 
  >
  > `value` 属性表示指定值。
  >
  > 相似的还有，也可以组合使用。
  >
  > - `@Max`: 校验字段值不能大于指定值。

- `@Pattern`: 校验字段值符合指定正则表达式。

  > 仅支持字符串。
  >
  > `regex` 属性表示正则表达式，`flag` 属性表示正则表达式的标志。

- `@Email`: 校验字段值符合邮箱格式。

  > 仅支持字符串。

- `@Positive`: 校验字段值为正数。

  > 支持 `BigDecimal`, `BigInteger`, `byte`, `short`, `int`, `long` 及其包装类。
  > 相似的还有：
  >
  > - `@Negative`: 校验字段值为负数。
  > - `@PositiveOrZero`: 校验字段值为正数或零。
  > - `@NegativeOrZero`: 校验字段值为负数或零。

- `@AssertTrue`: 校验字段值为 `true`。

  > 仅支持 `boolean` 及其包装类。 
  >
  > 一般没有直接在字段上用的情况，而是在 getter 方法上使用。**注意：方法名必须满足 getter 方法命名规范，且返回类型必须是 `boolean` 类型。**
  >
  > 相似的还有：
  > - `@AssertFalse`: 校验字段值为 `false`。

- `@Past`: 校验字段值为过去的日期。

  > 仅支持日期/时间类型。 
  >
  > 相似的还有:
  >
  > - `@Future`: 校验字段值为将来的日期。
  > - `@PastOrPresent`: 校验字段值为过去的日期或当前日期。
  > - `@FutureOrPresent`: 校验字段值为将来的日期或当前日期。

- `@Valid`: 级联/嵌套校验。

  > 用在自定义对象的字段上，表示对该对象内部进行校验。
  >
  > ```java
  > public class User {
  >     @Valid
  >     private Address address;
  > }
  > ```
  >
  > 用在泛型类型参数上，表示对该类型参数进行校验。
  >
  > ```java
  > public class User {
  >     private List<@Valid Address> addresses;
  > }
  > ```

#### 自定义校验注解

- **重要注意**：为了体现每个注解的单一职责，自定义校验注解在实现校验逻辑时要对 `null` 放行（上面 `@AssertTrue` 代码中也体现了这一点）。

- 像 [PasswordMatch](src/main/java/com/hyao/learning/validation/valid/annotation/PasswordMatch.java) 这样类级别的校验注解，
  我们可以手动构建校验失败的错误信息，指定属性名称，将默认的类级别的错误改成字段级别的错误。
 
- Spring Boot 的 `LocalValidatorFactoryBean` 使用 `SpringConstraintValidatorFactory`，校验器实例由 Spring 容器创建，支持构造器自动装配。
  所以即使 [UniqueUsernameValidator](src/main/java/com/hyao/learning/validation/valid/validator/UniqueUsernameValidator.java) 
  没有注册到 Bean 容器中，也可以注入 Bean，但是只能支持构造方法注入或字段注入。

### 扩展

#### 关于 Valid 和 Validated

| 维度          | `@Valid`                           | `@Validated`                                              |
| ------------- | ---------------------------------- | --------------------------------------------------------- |
| 来源          | Jakarta Bean Validation            | Spring 框架                                               |
| 定位          | 标准校验                           | 标准校验增强，支持 Spring 框架特性                        |
| 标注位置      | 方法、字段、构造器、参数、类型用途 | 类、方法、参数                                            |
| 类级别作用    | 无                                 | 生成 AOP 代理，使方法参数（如 @PathVariable）上的约束生效 |
| 分组校验      | 不支持                             | 支持                                                      |
| 级联/嵌套校验 | 支持                               | 不支持                                                    |

> Spring Boot 项目中，约定使用 `@Validated` 注解进行参数校验，使用 `@Valid` 注解进行级联/嵌套校验。

#### Jakarta 标准注解三个核心属性

- `String message()`: 校验失败后的提示信息。

- `Class<?>[] groups()`: 适用的校验分组。

  > 在 `@Validated` 注解中可以通过 `groups` 属性指定校验组，这样实现分组校验。
  >
  > ```java
  > // 用户类
  > public class User {
  >     @Null(message = "用户ID不能为null", groups = CreateGroup.class)
  >     @NotBlank(message = "用户ID不能为空", groups = UpdateGroup.class)
  >     private String id;
  >     //...
  > }
  > 
  > // 校验分组
  > public interface CreateGroup {}
  > public interface UpdateGroup {}
  > 
  > // 用户 controller
  > @RestController
  > public class UserController {
  >     public void createUser(@Validated(CreateGroup.class) @RequestBody User user) {
  >         //...
  >     }
  > 
  >     public void updateUser(@Validated(UpdateGroup.class) @RequestBody User user) {
  >       //...
  >     }
  > }
  > ```

- `Class<? extends Payload>[] payload()`: 扩展（严重级别、错误类型、处理策略等）。

  > 一般在全局异常处理器中使用，用于一些特定的逻辑处理。

#### Hibernate Validation 规范

Hibernate Validation 规范是在 Jakarta Validation 规范下扩展的，Spring 框架也支持 Hibernate Validation 规范中的注解。

- `@Length`: 校验字符串长度.

  > `@Size` 用在字符串上面的平替，语义更明确。

- `@Range`: 校验数值范围。

  > `@Max` 和 `@Min` 的组合体。

- `@URL`: 校验 URL 字符串的合法性。

  > 可以指定 `protocol`，`host`，`port`，也可以直接用 `regexp`。

- `@CreditCardNumber`: 校验信用卡号字符串的合法性。

  > 通过 Luhn 算法验证. 这种算法用来验证各种标识号码（如信用卡号、银行卡号、手机 IMEI 码等）是否合法。
  >
  > 这个注解只能提前过滤掉随便敲的一串数字, 具体的合法性校验（是否存在/有效等）需要更详细的业务校验逻辑。

- `@CodePointLength`: 校验字符串的码点长度。

  > 部分生僻字或 emoji 长度校验场景可以使用（ `@Length` 或 `@Size` 不准确）。
  >
  > Java 内部使用 UTF-16 编码 `String`，部分生僻字、emoji 这类增补平面字符在 UTF-16 中由两个 `char` 组成代理对，使用 `String.length()` 会返回 2 个长度。
  
