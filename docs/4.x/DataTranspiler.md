# 数据转义器

数据转义器的目的是为了协助自定义的数据生成器适配更多的场景。

举例：

1. 有两个类都叫**id**，但是一个是`int`类型，一个是`String`类型。
2. 使用了数据池，而多个类型的属性都需要使用同一个生成池。

对于以上的情况，通常是对不同的类型都定义一个数据生成器。但是这样不仅麻烦，兼容性也不佳。
为此，就有必要定义一个数据转义器（`DataTranspiler`）。

## 使用

数据转义器的使用很直观，如下：

```java
@Test
public void DataTranspilerTest() {
    MockDataConfig config = creator.getConfig().copy();
    FieldDataPool dataPool = new FieldDataPool();
    // 定义一个对Fruit属性填充的数据池，并设置成"apple"
    dataPool.likeName(".*Fruit", Person.FRUIT.APPLE.name());
    config.dataPool(dataPool);
    // 定义一个将String转换到FRUIT枚举的转义器
    ObjectTranspiler<String> transpiler = new ObjectTranspiler<String>() {
        @Override
        public Object trans(String s) {
            return Person.FRUIT.valueOf(s.toUpperCase());
        }
        @Override
        public Class<?>[] targets() {
            return new Class[]{Person.FRUIT.class};
        }
    };
    // 添加转义器
    config.addTranspiler(transpiler);
    Person person = creator.mock(Person.class, config);
}
```

其中`trans`方法就是将源类型`String.class`转义成目标类型`Person.FRUIT.class`，其中的泛型就是源类型，也就是接受的类型。
`targets`则表示转义器支持的目标类型，构建器会将不匹配的支持类型传入，由转义器转义后返回。

## 过程

在构造过程中，首先会对属性进行数据生成，当数据通过各种方式成功生成后，会对生成的数据与目标属性进行类型对比。

若类型匹配成功，则进行直接赋值。

若类型匹配失败，则尝试通过转义器进行转义。若转义成功，则继续赋值操作，否则忽略赋值。
