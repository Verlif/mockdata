# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

作为一个现代的数据生成工具，当然是需要更智能些的。

## 特点

- __上手简单__
- __高自定义__
  
## 支持的类型

### 基础类型

- __8个基础属性__
  - `byte`、`boolean`、`short`、`int`、`long`、`float`、`double`、`char`
  - `Byte`、`Boolean`、`Short`、`Integer`、`Long`、`Float`、`Double`、`Character`
- __String__
- __枚举类__
- __List__
- __Date__

__!!! 以上类型都可以通过`MockDataCreator.useBaseData()`进行初始化，或自行添加。__

### 常用对象

- `Map`
- `LocalDate`、`LocalTime`、`LocalDateTime`
- `BigInteger`、`BigDecimal`

__!!! 以上类型都可以通过`MockDataCreator.useExtendData()`进行初始化，或自行添加。__

### 其他

- __数组__
   - 任意深度数组，自带构建。

## 支持的特性

- __无限制循环引用__
  - A引用B且B引用A、或者A引用A都是直接支持的
  - 构造的层数可以通过`MockConfig.setCircleCount`来设定
- __方便的自定义构造器__
  - 如果想自己构建某个类的参数，例如`Student`的 __birthday__ 与 __age__ 关联，则可以自定义`Student`类的构造器。
  - 只需要实现`DataCreator`，并添加到创建器中即可
- __对特定类特殊处理__
  - 比如想让`Integer`属性只输出 __123__，可以使用`MockDataCreator.addDefaultCreator()`的方式增加一个`Integer`的数据构造器。
- __对特定类的特定属性特殊处理__
  - 如果有两个属性都是同样的类，但想分别处理的话可以通过`MockConfig.addFieldCreator()`的方式，通过 __Lambda__ 表达式将构造器与指定属性而非类型进行关联。
- __多维数组构造__
  - 通过`MockDataCreator.mock(new T[2][4][9])`的方式指定每层数组的长度
- __允许指定构造器生成对象__
  - 如果想要由创建器来构造属性，但又想要指定构造器来新建实例，可以通过`MockConfig.addInstanceCreator()`来添加指定的实例构建器
- __配置独立__
  - 每次mock都可使用不同的配置，便于快速切换配置
