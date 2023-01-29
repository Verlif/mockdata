# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

适用范围：

- 生成规范或随机数据，用来测试接口或是方法的调用结果
- 批量生成假数据，填充数据库
- 其他需要自动生成对象的场景

如果还想知道其他的用法，可以看 [这里](docs/Directions.md)，或是直接查阅 [配置说明](docs/MockConfig.md)。

## 特点

- __上手简单__
- __支持定位到特定类的特定属性__
- __生成数据规则完全自定义__
- __零三方依赖__

## 举例

### 基础用法

   ```java
   // 创建数据构造器
   MockDataCreator creator = new MockDataCreator();
           // 获取构造器的当前配置
   MockDataConfig config = creator.getConfig()
           // 手动添加需要级联构造的类
           .cascadeCreateKey(Student.class)
           .cascadeCreateKey(Person.class)
           .cascadeCreateKey(Person.PersonInner.class)
           // 或是直接通过正则表达式添加
           .cascadeCreatePattern(".*Student.*")
           // 或是通过包名进行添加允许的包下的所有类
           .cascadeCreatePackage(Student.class.getPackage().getName())
           // 或是使用自动级联构建
           .autoCascade(true)
           // 将构造深度设置为1，避免构建出的数据过大
           .creatingDepth(1);
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
        // 忽略成绩属性的构造
        .ignoredField(Student::getScore)
        // 允许构建public或static标记的属性
        .allowedModifiers(Modifier.PUBLIC, Modifier.STATIC)
        // 自定义secondChild属性
        .fieldCreator(Student::getSecondChild, new DataCreator<Student>() {

            private final Random random = new Random();

            @Override
            public Student mock(Field field, MockDataCreator.Creator creator) {
                if (random.nextBoolean()) {
                    return new Student("这是自定义的构造");
                } else {
                    return null;
                }
            }
        });
   ```

更多用法请参考 [使用手册](docs/Directions.md)

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

### 注意

__目前自动构建暂不支持非静态内部类，有需要请使用添加自定义构建器__

__当数组维度大于2后，其2维以上的每个维度的大小都会被重置为`MockDataConfig.getArraySize()`__

## 支持的特性

- __无限制循环引用__
   - A引用B且B引用A、或者A引用A都是直接支持的
   - 构造的层数可以通过`MockConfig.setCreatingDepth`来设定
- __方便的自定义构造器__
   - 如果想自己构建某个类的参数，例如`Student`的 __birthday__ 与 __age__ 关联，则可以自定义`Student`类的构造器。
   - 只需要实现`DataCreator`，并添加到创建器中即可
- __对特定类特殊处理__
   - 比如想让`Integer`属性只输出 __123__，可以使用`MockDataCreator.addDefaultCreator()`的方式增加一个`Integer`的数据构造器。
- __对特定类的特定属性特殊处理__
   - 如果有两个属性都是同样的类，但想分别处理的话可以通过`MockConfig.addFieldCreator()`的方式，通过 __Lambda__
     表达式将构造器与指定属性而非类型进行关联。
- __多维数组构造__
   - 通过`MockDataCreator.mock(new T[2][4][9])`的方式指定每层数组的长度
- __允许指定构造器生成对象__
   - 如果想要由创建器来构造属性，但又想要指定构造器来新建实例，可以通过`MockConfig.addInstanceCreator()`来添加指定的实例构建器
- __配置独立__
   - 每次mock都可使用不同的配置，便于快速切换配置。例如`MockDataCreator.mock(Class|Object, config)`

## 注意事项

- __mock__ 无法实例化的类（例如接口或是抽象类）时，请给予实例构建器。
- __mock__ 基础类型的数组时，数组的大小暂时依据配置中的设定，不能像类似`new Integer[2][3]`一样指定大小。
- __mock__ 未指定泛型的类时，大概率无法构建成功，请指定泛型类型或实例构建器。

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
