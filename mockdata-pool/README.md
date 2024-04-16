# 虚拟数据池

虚拟数据池旨在帮助开发者更便利地使用**mock-data**，使生成的数据更加有序而符合真实性。

## 示例

只需要对MockDataCreator添加虚拟数据池即可开始使用：

```java
public void mainTest() {
    MockDataCreator creator = new MockDataCreator();
    // 启用虚拟数据池并启用预设模板
    creator.dataPool(new VirtualDataPool().withTemplate());
    System.out.println(JSONObject.toJSONString(creator.mock(Person.class)));
}
```

运行后可以得到以下结果（这里的年龄、生日与身份证号并未做关联处理，故互不相干）：

```json
{
  "address": "橄榄省白兰市莲藕区白垩街道",
  "age": 16,
  "birthday": "1986-07-16 20:14:32.686",
  "email": "anderson@virtual.com",
  "height": 193.0,
  "id": "970689198801120914",
  "ip": "114.168.126.212",
  "name": "邵城",
  "weight": 50.0
}
```

## 拓展使用

当虚拟内容池的数据不足以支撑开发者的需求时，只需要简单实现`SimplePool`接口，并将其添加到`VirtualDataPool`中即可：

```java
public void diyTest() {
    MockDataCreator creator = new MockDataCreator();
    // 使用带有模板的虚拟数据池
    VirtualDataPool dataPool = new VirtualDataPool().withTemplate();
    // 对所有nickname的属性进行拦截并返回虚拟数据
    dataPool.add("nickname", new SimplePool() {
        @Override
        public Object fetch(ClassGrc classGrc, String key) {
            return "嘻嘻";
        }
    });
    creator.dataPool(dataPool);
    Pet pet = new Pet();
    creator.mock(pet);
}
```

这里需要说明的是，`VirtualDataPool`包括了`type`和`alias`两个关键词，
`type`与`SimplePool`一一对应，表示了数据的类型与其使用的数据生成池，
`type`则可以对应多个`alias`，`alias`表示了属性名称与数据类型的关系。
