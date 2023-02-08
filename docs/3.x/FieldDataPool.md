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
