# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

适用范围：

- 生成规范或随机数据，用来测试接口或是方法的调用结果
- 批量生成假数据，填充数据库
- 其他需要自动生成对象的场景

如果还想知道其他的用法，可以看 [这里](docs/Directions.md)，或是直接查阅 [配置说明](docs/MockConfig.md)。

## 特点

- __上手简单__
- __支持复杂类型和自定义类型__
- __生成数据规则完全自定义__

## 举例

### 基础用法

   ```java
   // 创建数据构造器
   MockDataCreator creator = new MockDataCreator();
   // 使用基础数据包
   creator.useBaseData();
   // 使用拓展包
   creator.useExtendData();
   // 添加需要级联构造的类，让创造器可以对其内部属性进行构造
   config.addCascadeCreateKey(Person.class);
   // 使用类的方式构建Person实例
   Person claPerson = creator.mock(Person.class);
   // 使用对象的方式构建其中的属性，返回值是其对象自身
   Person selfPerson = creator.mock(new Person());
   // 使用Lambda的方式构建类的某个属性，返回值是此属性的实例
   Long id = creator.mock(Person::getId());
   ```

### 拓展用法

   ```java
   // 设置默认的Long值构造器，将Long的构建值范围设定在-100到200之间
   creator.addDefaultCreator(new LongRandomCreator(-100, 200));
   // 将构造深度设置为1，避免在进行递归构造循环引用时数据过大
   config.setCreatingDepth(1);
   // 让person中的name属性使用字典类型构造
   config.addFieldCreator(Person::getName, new DictDataCreator<>(new String[]{
        "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
       }));
   // 让person的secondChild属性使用自定义构造器
   config.addFieldCreator(Person::getSecondChild, new DataCreator<Student>() {

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
   // 开启public属性构建
   config.addAllowedModifiers(Modifier.PUBLIC);
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

__!!! 以上类型都可以通过`MockDataCreator.useBaseData()`进行初始化，或自行按需添加。__

### 常用对象

- `Map`
- `LocalDate`、`LocalTime`、`LocalDateTime`
- `BigInteger`、`BigDecimal`

__!!! 以上类型都可以通过`MockDataCreator.useExtendData()`进行初始化，或自行按需添加。__

### 其他

- __数组__
   - 支持任意维度数数组。

__！！！注意，目前自动构建暂不支持非静态内部类，有需要请使用添加自定义构建器__

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
