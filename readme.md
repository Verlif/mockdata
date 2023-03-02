# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

适用场景：

- web场景下生成测试数据测试后台接口
- 数据库中需要批量插入合规数据
- 各种场景下的随机数据测试
- 场景演示下生成随机数据展示

以下内容基于3.x版本，1.x版本请参考 [这里](readme-1.x.md)，2.x版本请参考 [这里](readme.md)。

如果还想知道其他的用法，可以看 [这里](docs/3.x/Directions.md)，或是直接查阅 [配置说明](docs/3.x/MockConfig.md)。

## 特点

- __上手简单，三行代码即可开始使用__
- __自定义粒度到指定类的指定属性__
- __提供数据池方式，规则式批量填充同类属性__
- __支持绝大多数泛型结构__

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
   // 获取构造器的当前配置
   creator.getConfig()
           // 自动级联构建
           .autoCascade(true)
           // 替换目标对象中已存在的对象，例如基础类型
           .forceNew(true)
   // 通过类来实例化对象
   Person person = creator.mock(Person.class);
   // 或是手动实例化对象，然后填充数据
   Person person = new Person();
   creator.mock(person);
   // 如果你只要其中的某个属性也可以
   Pet pet = creator.mock(Person::getPet);
   ```

更多用法请参考 [使用手册](docs/3.x/Directions.md)

## 用法举例

1. 我想让 __person.weight__ 的范围在*3-200*之间，__person.height__ 的范围在*10-260*之间，如何设置？

   ```java
   MockDataCreator creator = new MockDataCreator();
   creator.getConfig()
        // 开启级联构建并对默认值进行替换
        .autoCascade(true).forceNew(true)
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

2. 这里有10多个类，但是基本的属性都很相似，如何让其中所有的 __address__ 都填充正规的地址，__name__ 填充正常的名称呢？

   ```java
   MockDataCreator creator = new MockDataCreator();
   FieldDataPool dataPool = new FieldDataPool()
           // 自动识别同类型属性，包括int类型的所有名称中包含age的属性，忽略大小写，例如age、nominalAge
           .like(Person::getAge, 23, 24, 25, 26, 27)
           .next()
           // 添加FRUIT类的数据池，则会对所有的FRUIT类进行数据池选取，忽略名称
           .type(Person.FRUIT.class)
           .values(Person.FRUIT.APPLE)
           .next()
           // 对Date类的所有名称中能匹配`.*day`和`.*time`的属性进行数据池选取
           .type(Date.class)
           .values(new Date[]{new Date()}, ".*day", ".*time").next();
   // 设置属性数据池
   creator.fieldDataPool(dataPool);
   // 开始构建
   Person person = creator.mock(Person.class);
   ```

   更多属性数据池的用法看 [这里](docs/3.x/FieldDataPool.md)

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
- __自定义对象__
  - 通过设定`autoCascade(true)`来自动构建内部属性

## 注意事项

- __目前自动构建暂不支持非静态内部类，有需要请使用添加自定义构建器。__
- __mock__ 无法实例化的类（例如接口或是抽象类）时，请给予实例构建器。
- __mock__ 构建带有泛型但未指明泛型的类时，大概率无法构建成功，请指定泛型类型或实例构建器。
- 在使用`fieldValue(DataCreator)`时请勿使用 __lambda__ 表达式，否则会无法识别`DataCreator`的匹配类型。
  - 例如`creator.fieldValue((DataCreator<String>) (cla, field, creator1) -> "String.String")`会抛出异常，可以使用匿名内部类表述或通过双参的方法显式指明匹配类型。
- 对于基础类型（例如`int`）在进行构建时，其属性是有默认值*0*的，在没有设定`forceNew`的情况下会被跳过。

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
