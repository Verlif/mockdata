# 数据转义器

数据转义器的目的是为了协助自定义的数据生成器适配更多的场景，会在生成的数据与属性不匹配时生效。

举例：

1. 有两个属性都叫**id**，但是一个是`int`类型，一个是`String`类型。
2. 使用了数据池，而多个类型的属性都需要使用同一个生成池，此生成池生成的数据需要对每个类型做适配。

对于以上的情况，通常是对不同的类型都定义一个数据生成器。但是这样不仅麻烦，兼容性也不佳。
为此，就有必要定义一个数据转义器（`DataTranspiler`）。

## 使用

数据转义器的使用很直观，如下：

```java
@Test
public void DataTranspilerTest() {
    MockDataConfig config = creator.getConfig().copy();
    // 定义数据池
    FieldDataPool dataPool = new FieldDataPool();
    dataPool.likeName(".*Fruit", Person.FRUIT.APPLE.name());
    config.dataPool(dataPool);
    // 添加转义器，将部分String类型的数据转成Person.FRUIT枚举类
    ObjectTranspiler<Person.FRUIT> transpiler = new TypeTranspiler<Person.FRUIT>() {
        @Override
        public Person.FRUIT trans(Object t) {
            return Person.FRUIT.valueOf(t.toString().toUpperCase());
        }

        @Override
        public boolean support(Class<?> cla) {
            return cla == String.class;
        }
    };
    config.addTranspiler(transpiler);
    // 此时生成的数据中，所有名字中带有Fruit的枚举类型属性也可以使用定义的数据池
    Person person = creator.mock(Person.class, config);
}
```

其中`trans`方法就是将源对象转义成目标类型`Person.FRUIT.class`。
`support`则用于判定转义器是否支持目标类型，构建器只会将匹配的类型对象传入到`trans`方法中，由转义器转义后返回。

## 过程

在构造过程中，首先会对属性进行数据生成，当数据通过各种方式成功生成后，会对生成的数据与目标属性进行类型对比。

若类型匹配成功，则进行直接赋值。

若类型匹配失败，则尝试通过转义器进行转义。若转义成功，则继续赋值操作，否则忽略赋值。
