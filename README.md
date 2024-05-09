# Mock-Data

一个易用且高度自定义的数据生成工具。主要用于测试或是批量随机数据生成。

适用场景：

- WEB场景下生成测试数据测试后台接口
- 数据库中需要批量插入合规数据
- 各种场景下的随机数据测试
- 场景演示下生成随机数据展示

以下内容基于**4.x**版本，**1.x**版本请参考 [这里](readme-1.x.md)，**2.x**版本请参考 [这里](readme-2.x.md)，**3.x**版本请参考 [这里](readme-3.x.md)。

如果还想知道其他的用法，可以看 [这里](docs/4.x/Directions.md)，或是直接查阅 [配置说明](docs/4.x/MockConfig.md)。

如果希望用于接口上协助Web开发，可以参考 [MockApi](https://github.com/Verlif/mock-api)。

## 特点

- **上手简单，两行代码即可开始使用（一行新建，一行使用）**
- **自定义粒度到指定类的指定属性**
- **提供数据池方式，规则式批量填充同类属性**
- **无感支持绝大多数泛型结构**
- **速度快，反正很快就对了**

## 举例

### 简单用法

   ```java
   MockDataCreator creator = new MockDataCreator();
   // 就像new Random.next一样，随机基础类型的数据
   int i = creator.mock(int.class);
   // 随机包装类型
   int inte = creator.mock(Integer.class);
   // 随机数组
   int[] ints = creator.mock(int[].class);
   // 指定数组大小
   int[][] intsArray = creator.mock(new int[2][3]);
   // 随机日期
   Date date = creator.mock(Date.class);
   ```

### 基础用法

   ```java
   // 创建数据构造器
   MockDataCreator creator = new MockDataCreator();
   // 通过类来实例化对象
   Person person = creator.mock(Person.class);
   // 或是手动实例化对象，然后填充数据
   Person thisPerson = new Person();
   creator.mock(thisPerson);
   // 如果你只要其中的某个属性也可以
   Pet pet = creator.mock(Person::getPet);
   ```

更多用法请参考 [使用手册](docs/4.x/Directions.md)

### 图示

![图示](/docs/4.x/imgs/mockDebugger.png)

## 更多特性

1. 多配置，`MockDataCreator`自带有全局配置，也可以在`mock`的时候指定本次构建使用的临时配置而不影响其他`mock`。
2. 构建过滤器，使用`FieldFilter`和`ClassFilter`来过滤不需要构建的属性或类型。
3. 精确到某个类下的某个属性，允许开发者通过`fieldValue`方法指定不同类的不同属性所使用的构建方式。
4. 通过 [转义器](/docs/4.x/DataTranspiler.md) 将自定义的构建数据支持到更多的类型中。

## 用法举例

1. 使 __person.weight__ 的范围在*3-200*之间，__person.height__ 的范围在*10-260*之间。

   ```java
   MockDataCreator creator = new MockDataCreator();
   creator.getConfig()
        // 设定weight的范围
        .fieldValue(Person::getWeight, new DoubleRandomCreator(3D, 200D))
        // 设定height的范围
        .fieldValue(Person::getHeight, new DoubleRandomCreator(10D, 260D))
        // 如果需要，也可以设定其他double类型的默认范围
        .fieldValue(double.class, new DoubleRandomCreator(0D, 1000D));
   // 开始构建
   Person person = creator.mock(Person.class);
   ```

   *上面的三个`fieldValue`方法的顺序可以随意交换，因为属性绑定的优先级是高于类型绑定的。*

2. 对 **address** 属性填充地址，对 **name** 填充名称。

   ```java
   MockDataCreator creator = new MockDataCreator();
   // 使用属性池的方式
   DataPool dataPool = new FieldDataPool()
           // 只对所有的名为name的String属性填充["小明", "小红", "小刚", "小丽"]
           .typeName(String.class, "name", "小明", "小红", "小刚", "小丽")
           .next()
           // 只对所有的名称中带有address的String属性填充["这里", "那里"]
           .likeName(String.class, "address", "这里", "那里")
           .next();
   // 设置属性数据池
   creator.dataPool(dataPool);
   // 与下面的方式类似，但数据池方式更灵活
   creator.fieldValue(Person::getName, new DictDataCreator<>(new String[]{"小明", "小红", "小刚", "小丽"}))
           .fieldValue(Person::getAddress, new DictDataCreator<>(new String[]{"这里", "那里"}));
   // 开始构建
   Person person = creator.mock(Person.class);
   ```

   更多属性数据池的用法看 [这里](docs/4.x/FieldDataPool.md)

## 支持的类型

### 基础类型

- __8个基础属性__
   - `byte`、`boolean`、`short`、`int`、`long`、`float`、`double`、`char`
   - `Byte`、`Boolean`、`Short`、`Integer`、`Long`、`Float`、`Double`、`Character`
- __String__
- __枚举类__

### 常用对象

- `List`、`Set`
- `Map`
- `Date`、`LocalDate`、`LocalTime`、`LocalDateTime`
- `BigInteger`、`BigDecimal`

### 其他

- __数组__
   - 支持任意维度数数组。
   - 通过`mock(new T[2][3][4][5])`的方式来手动指定数组大小
- **接口**
  - 通过`interfaceValue`方法添加接口实例化构造器，从而支持接口类型。

## 注意事项

- __目前自动构建暂不支持创建非静态内部类，有需要请先添加自定义构建器（`DataCreator`）或使用实例构建器（`InstanceCreator`）。__
- __mock__ 无法实例化的类（例如接口或是抽象类）时，请给予实例构建器（`InstanceCreator`）。
- __mock__ 构建未指明泛型的类时，大概率无法构建成功，请指定泛型类型或实例构建器。
- 在使用`fieldValue(DataCreator)`时请勿使用 __lambda__ 表达式，否则会无法识别`DataCreator`的匹配类型。
  - 例如`creator.fieldValue((DataCreator<String>) (cla, field, creator1) -> "String.String")`会抛出异常，可以使用匿名内部类表述或通过双参的方法显式指明匹配类型。
- 对于基础类型（例如`int`）在进行构建时，其属性是有默认值*0*的，在`forceNew`为`false`的情况下会被跳过。

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
           <groupId>com.github.Verlif.mockdata</groupId>
           <artifactId>mockdata-core</artifactId>
           <!--<artifactId>mockdata-pool</artifactId>-->
           <version>lastVersion</version>
       </dependency>
   </dependencies>
   ```

   Gradle

   ```text
   dependencies {
     implementation 'com.github.Verlif.mock-data:mockdata:lastVersion'
   }
   ```

