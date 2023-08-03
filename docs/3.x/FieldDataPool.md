# 属性数据池

属性数据池是为了便于规范化的数据填充，例如对所有类的address填充格式化的地址字符串、对所有类的name填充设定的姓名。

## 使用

属性数据池的使用非常简单，包括三个部分：`类型`、`名称匹配`与`数据`。

如下举例：

```java
FieldDataPool dataPool = new FieldDataPool()
        // 自动识别同类型属性，包括int类型的所有名称中包含age的属性，忽略大小写，例如age、nominalAge
        .like(Person::getAge)
        .values(23, 24, 25, 26, 27).next()
        // 添加FRUIT类的数据池，则会对所有的FRUIT类进行数据池选取，忽略名称
        .type(Person.FRUIT.class)
        .values(Person.FRUIT.APPLE).next()
        // 对Date类的所有名称中能匹配`.*day`和`.*time`的属性进行数据池选取
        .type(Date.class, ".*day", ".*time")
        .values(new Date()).next();
```

## 方法

### like

使用`like`来进行名称概括匹配，例如`like(Person::getAge)`表示了所有与`person.age`属性类型相同，且名称中包括`age`（忽略大小写）的属性，例如`person.nominalAge、pet.adoptedAge`。

或是通过`like(int.class, "age")`，此方式与`like(Person::getAge)`所匹配的内容完全相同。

### type

使用type来自定义匹配，例如`type(Date.class, ".*day", ".*time")`表示了名称中含有`day`或是`time`的所有`Date`类。

当然，你也可以忽略名称，直接使用`type(Person.FRUIT.class)`，这样的方式与使用字典是相同的。

### next

非常重要，因为......通过`next`才可以将链式结果返回到`FieldDataPool`中，每个设定后都需要添加一个`next`已表示一个类型的设定完成。

## 其他

建议开发者对属性数据池进行二次封装，例如允许从配置文件中加载数据池，这样的方式事实上会更合理一些，也更方便使用。

如果需要这样做，推荐使用字符串转义组件 [param-parser](https://github.com/Verlif/param-parser) 来将配置文件中的字符串自动转换成对应的类。

（为什么我不去做呢？因为这样会引入另外的包，有悖我的设计理念）

### 举例

在 __test__ 包中我新建了一个`PropertiesDataPool`，也就是通过`properties`配置来构建属性数据池，以下是配置文件内容

```properties
int#age=[22, 23, 24, 25, 25, 27]
int#nominalAge=[23, 24, 25, 25, 27, 28]
double#height=[120, 130, 140, 150, 160, 170, 180, 190, 200]
double#weight=[40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160]
String#name=["假装这个名字", "这么长的姓名"]
String#nickname=["小明", "小红", "小张", "小丽"]
java.util.Date#birthday=["1998-01-08", "1998-03-08", "1998-05-08", "1998-07-08"]
```

可以看出每行数据包括三个部分，这里以第一行举例。

`int`表示了类型，对于基本类型可以直接使用名称，例如`double`。像是`Integer`这种包装类也可以忽略包名，直接写`Integer`。
`age`表示属性名，这里做的是忽略大小写的全属性名称匹配，也就是`age`无法匹配到`nominalAge`。
等号后面的数组就表示数据池，我这里用了 __param-parser__，方便转义不同的类型，比如`Date`。

随后运行以下代码：

```java
// 新建PropertiesDataPool对象
PropertiesDataPool dataPool = new PropertiesDataPool();
// 加载配置文件
dataPool.load("src/test/resources/data-pool.properties");
// 新建MockDataCreator
MockDataCreator creator = new MockDataCreator();
// 进行基础设定并加载属性数据池
creator.getConfig().autoCascade(true).fieldDataPool(dataPool);
// 开始mock
System.out.println(JSONObject.toJSONString(creator.mock(Person.class)));
```

最终得到以下结果：

```json
{
  "age": 25,
  "birthday": "1998-01-08 00:00:00",
  "favouriteFruit": 2,
  "height": 190.0,
  "name": "假装这个名字",
  "nickname": "小丽",
  "nominalAge": 25,
  "weight": 150.0
}
```

这种数据应该是符合正常标准的，当然，开发者可以根据需要自己继承`FieldDataPool`，实现类似`JavaFaker`的效果。
