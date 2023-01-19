# Mock-Data

一个易用但高度自定义的数据生成工具，主要用于测试或是批量数据生成。

## 特点

- __上手简单__

   ```java
   // 创建数据构造器
   MockDataCreator creator = new MockDataCreator();
   // 使用基础数据包
   creator.useBaseData();
   // 获取构造器的当前配置
   MockDataConfig config = creator.getConfig();
   // 添加需要级联构造的类
   config.addCascadeCreateKey(Person.class);
   // 构造测试
   for (int i = 0; i < 10; i++) {
       System.out.println(creator.mock(Person.class));
   }
   ```

- __高自定义__

   ```java
   System.out.println("------>>> 使用字典生成name属性");
   config.addFieldCreator(Person::getName, new DictDataCreator<>(new String[]{
           "小明", "小红", "小王", "小赵", "小李", "小周", "小强"
   }));
   System.out.println("------>>> 限制id属性生成范围");
   config.addFieldCreator(Person::getId, new LongRandomCreator(0L, 9999L));
   System.out.println("------>>> 限制age属性生成范围");
   config.addFieldCreator(Person::getAge, new IntegerRandomCreator(0, 200));
   System.out.println("------>>> 忽略birthday属性");
   config.addIgnoredField(Person::getBirthday);
   System.out.println("------>>> 自定义secondChild属性");
   config.addFieldCreator(Person::getSecondChild, new DataCreator<Person>() {

       private final Random random = new Random();

       @Override
       public Person mock(Field field, MockDataCreator creator) {
           if (random.nextBoolean()) {
               return new Person("啊哈");
           } else {
               return null;
           }
       }
   });
   for (int i = 0; i < 10; i++) {
       System.out.println(creator.mock(Person.class));
   }
   ```
  
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

