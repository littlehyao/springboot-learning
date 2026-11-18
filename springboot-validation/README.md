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
  >
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
  > // 场景：用户创建时，用户 ID 必须为 null，用户更新时，用户 ID 不能为空。
  > // 用户类
  > public class User {
  >     @Null(message = "用户ID不能为 null", groups = CreateGroup.class)
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

#### Hibernate Validation 规范常用的注解

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

#### 分组校验

Spring 框架分组校验还支持一些额外的特性，诸如顺序分组，动态分组，级联分组等等。

> 使用校验分组时会定义一个接口，这个接口只是起到一个标识分组的作用，没有其他意义。

##### 顺序分组

*顺序分组用于解决校验顺序先后的问题。*

有时候校验也有优先级，比如先执行所有必填字段的非空校验，再执行字符串字段的合法性校验，再执行业务字段的业务规则校验。这样可以更早的发现最简单的错误参数，避免同时执行所有校验规则。

- 核心注解： `@GroupSequence`

- 快速入门

  1. 就像定义校验分组接口一样，定义一个顺序分组接口。

  2. 在接口上添加 `@GroupSequence` 注解，通过 `value` 属性指定校验分组的顺序。

  3. 实际校验时，按照指定的分组顺序索引从小到大执行执行。

  ```java
  // 第一个分组
  public interface FirstGroup {
  }
  
  // 第二个分组
  public interface SecondGroup {
  }
  
  // 指定分组顺序
  @GroupSequence({FirstGroup.class, SecondGroup.class})
  public interface OrderedGroup {
  }
  ```

- 使用场景

  1. 先执行非空校验，再执行其他校验。

  2. 先执行简单基础校验，再执行复杂业务校验。

##### 动态分组

*Hibernate Validation 的动态分组特性用于解决条件校验的问题。*

有时候某些字段的校验是联动的，比如证件类型为居民身份证时，证件号码长度是18位，证件类型为护照时，证件号码长度是9位。

- 核心注解： `@GroupSequenceProvider`

- 快速入门

  1. 新建一个分组提供器类，实现 `DefaultGroupSequenceProvider` 接口，接口泛型类型为该分组提供器所校验的类型。

  2. 实现接口方法 `getValidationGroups` 方法返回校验分组。

  3. 在要校验的类上添加注解 `@GroupSequenceProvider`，通过 `value` 属性指定上面新建的分组提供器类。

  4. 在要校验的类中使用校验注解时，根据校验规则通过 `groups` 属性指定校验分组。

  5. 在使用 `@Validated` 校验时，不需要指定 `groups` 属性，直接走默认分组即可。

  ```java
  // 场景：当类型字段为 OTHER 时，描述字段不能为空。
  // 待校验的类
  @Data
  @GroupSequenceProvider(IDNumberGroupSequenceProvider.class)
  public class DemoDTO {
      @NotBlank(message = "证件类型不能为空")
      private String idType;
      @NotBlank(message = "证件号码不能为空")
      @Size(min = 18, max = 18, message = "居民身份证号码长度必须是18位", groups = IDCardGroup.class)
      @Size(min = 9, max = 9, message = "护照号码长度必须是9位", groups = PassportGroup.class)
      private String idNumber;
  }
  
  // 定义校验分组
  public interface IDCardGroup {
  }
  
  public interface PassportGroup {
  }
  
  // 定义分组提供器
  public class IDNumberGroupSequenceProvider implements DefaultGroupSequenceProvider<DemoDTO> {
      @Override
      public List<Class<?>> getValidationGroups(Class<?> klass, DemoDTO object) {
          List<Class<?>> groups = new ArrayList<>();
          groups.add(DemoDTO.class);
          String idType;
          if (object == null || StringUtils.isBlank(idType = object.getIdType())) {
              return groups;
          }
          if (IDType.ID_CARD.getCode().equals(idType)) {
              groups.add(IDCardGroup.class);
          } else if (IDType.PASSPORT.getCode().equals(idType)) {
              groups.add(PassportGroup.class);
          }
          return groups;
      }
  }
  ```

- 使用场景：条件必填。

- 使用注意

  > - 在实现接口方法时，Hibernate Validation 要求必须把被校验对象自身的 Class 加进去, 代表默认分组。
  >
  > - 一次校验里 provider 实际会被调用 3 次（详见[Hibernate官网相关讨论](https://discourse.hibernate.org/t/when-using-a-groupsequenceprovider-why-is-it-called-3-times-and-why-the-first-time-with-a-null-validation-instance/873)），为了避免 NPE 或产生脏数据，provider 里只做纯计算，保持幂等、轻量，不要依赖被调用的次数，也不要在这里查库、发消息、改状态。

##### 级联分组转换

*级联分组转换用于解决嵌套对象默认沿用外层的分组的问题。*

如果被校验的对象中有嵌套对象，嵌套对象校验会默认沿用外层指定的分组，当二者使用不同的校验分组，就需要将外层校验分组转换成内层校验分组。

> 这句话看得有点不知所云哈，结合下面的示例一起看吧。

- 核心注解： `@ConvertGroup`

- 快速入门

  1. 创建各自的校验分组标识接口。

  2. 在校验对象及其嵌套对象中添加校验注解时指定各自的校验分组。

  3. 在校验对象中的内层嵌套对象字段上添加 `@ConvertGroup` 注解，通过 `from` 和 `to` 属性指定从什么分组转成什么分组。

  4. 在使用 `@Validated` 注解时，指定外层校验分组。

  ```java
  // 场景：外层对象必须指定校验分组，内层校验对象需要另外的校验分组。
  // 定义校验分组标识接口
  public interface UserCreateGroup {
  }
  
  public interface UserUpdateGroup {
  }
  
  public interface IdentificationGroup {
  }
  
  // 校验对象
  @Data
  public class User {
      @Null(message = "创建时 userId 为空", groups = UserCreateGroup.class)
      @NotBlank(message = "更新时 userId 不能为空", groups = UserUpdateGroup.class)
      private String userId;
      @NotBlank(message = "用户名不能为空", groups = {UserCreateGroup.class, UserUpdateGroup.class})
      private String username;
      @Valid
      @ConvertGroup(from = UserCreateGroup.class, to = IdentificationGroup.class)
      @ConvertGroup(from = UserUpdateGroup.class, to = IdentificationGroup.class)
      private Identification identificationInfo;
  }
  
  @Data
  public class Identification {
      @NotBlank(message = "证件类型不能为空", groups = IdentificationGroup.class)
      private String idType;
      @NotBlank(message = "证件号码不能为空", groups = IdentificationGroup.class)
      private String idNumber;
  }
  
  // 执行校验
  @RestController
  public class DemoController {
      @PostMapping("/createUser")
        public Map<String, Object> createUser(@Validated(UserCreateGroup.class) @RequestBody User user) {
          //...
      }
      @PostMapping("/updateUser")
      public Map<String, Object> updateUser(@Validated(UserUpdateGroup.class) @RequestBody User user) {
          //...
      }
  }
  ```

- 使用场景：被校验的对象中有嵌套对象，二者使用不同的校验分组。
