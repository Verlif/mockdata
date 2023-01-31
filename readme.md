# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

适用场景：

- web场景下生成测试数据测试后台接口
- 数据库中需要批量插入合规数据
- 各种场景下的随机数据测试
- 场景演示下生成随机数据展示

以下内容基于2.x版本，1.x版本请参考 [这里](readme-1.x.md)。

如果还想知道其他的用法，可以看 [这里](docs/2.x/Directions.md)，或是直接查阅 [配置说明](docs/2.x/MockConfig.md)。

## 特点

- __上手简单__
- __自定义粒度到指定类的指定属性__
- __生成数据规则完全自定义__
- __零三方依赖__

## 举例

### 基础用法

   ```java
   // 创建数据构造器
   MockDataCreator creator = new MockDataCreator();
   // 获取构造器的当前配置
   creator.getConfig()
           // 自动级联构建
           .autoCascade(true)
   // 通过类来实例化对象
   Person person = creator.mock(Person.class);
   // 或是手动实例化对象，然后填充数据
   Person person = new Person();
   creator.mock(person);
   // 如果你只要其中的某个属性也可以
   Pet pet = creator.mock(Person::getPet);
   ```

### 拓展用法

   ```java
   config
        // 使用字典生成name属性
        .fieldCreator(Student::getName, new DictDataCreator<>(new String[]{
            "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
        }))
        // 限制id属性生成范围
        .fieldCreator(Student::getId, new LongRandomCreator(0L, 9999L))
        // 限制age属性生成范围
        .fieldCreator(Student::getAge, new IntegerRandomCreator(0, 200))
        // 自定义secondChild属性
        .fieldCreator(Student::getSecondChild, new DataCreator<Student>() {

            private final Random random = new Random();

            @Override
            public Student mock(Class<?> cla, Field field, MockDataCreator.Creator creator) {
                if (random.nextBoolean()) {
                    return new Student("这是自定义的构造");
                } else {
                    return null;
                }
            }
        })
        // 设定通用的构造深度
        .creatingDepth(4)
        // 指定SelfIt的selfOne属性的构造深度为1
        .creatingDepth(SelfIt::getSelfOne, 1)
        // int类的数组默认大小为2，其他类随机大小
        .arraySize(cla -> {
            if (cla == int.class) {
                return 2;
            } else {
                return new Random().nextInt(10);
            }
        })
        // 只允许private或static的属性进行构建
        .filter(new FieldModifierFilter()
            .allowedModifiers(Modifier.PRIVATE, Modifier.STATIC))
        // 忽略Student下的score属性构建
        .filter(new FieldKeyFilter()
            .ignoredField(Student::getScore));
   ```

更多用法请参考 [使用手册](docs/2.x/Directions.md)

## 支持的类型

### 基础类型

- __8个基础属性__
   - `byte`、`boolean`、`short`、`int`、`long`、`float`、`double`、`char`
   - `Byte`、`Boolean`、`Short`、`Integer`、`Long`、`Float`、`Double`、`Character`
- __String__
- __枚举类__
- __List__
- __Date__

### 常用对象

- `Map`
- `LocalDate`、`LocalTime`、`LocalDateTime`
- `BigInteger`、`BigDecimal`

### 其他

- __数组__
   - 支持任意维度数数组。
   - 通过`mock(new T[2][3][4][5])`的方式来手动指定数组大小

### 注意

__目前自动构建暂不支持非静态内部类，有需要请使用添加自定义构建器__

## 注意事项

- __mock__ 无法实例化的类（例如接口或是抽象类）时，请给予实例构建器。
- __mock__ 构建带有泛型但未指明泛型的类时，大概率无法构建成功，请指定泛型类型或实例构建器。
- 在添加`DataCreator`时请勿使用 __lambda__ 表达式，否则会无法识别`DataCreator`的匹配类型。
  - 例如`creator.addDefaultCreator((DataCreator<String>) (cla, field, creator1) -> "String.String")`会抛出异常，可以使用匿名内部类来代替。

## 添加依赖

1. 添加Jitpack仓库源

   maven

    ```xml
    <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
    </repositories>
    ```

   Gradle

    ```text
    allprojects {
      repositories {
          maven { url 'https://jitpack.io' }
      }
    }
    ```

2. 添加依赖

   __lastVersion__ [![](https://jitpack.io/v/Verlif/mock-data.svg)](https://jitpack.io/#Verlif/mock-data)

   maven

   ```xml
      <dependencies>
          <dependency>
              <groupId>com.github.Verlif</groupId>
              <artifactId>mock-data</artifactId>
              <version>lastVersion</version>
          </dependency>
      </dependencies>
   ```

   Gradle

   ```text
   dependencies {
     implementation 'com.github.Verlif:mock-data:lastVersion'
   }
   ```
