# 使用手册

__mock-data__ 的使用其实很简单，与大部分工具一样，只需要三步：

1. 创建`MockDataCreator`对象

   ```java
   MockDataCreator creator = new MockDataCreator();
   ```

2. 对`MockDataCreator`进行自定义的初始化

   ```java
   // 使用基础数据包
   creator.useBaseData();
   // 使用拓展包
   creator.useExtendData();
   // 添加需要级联构造的类，让创造器可以对其内部属性进行构造
   config.addCascadeCreateKey(Person.class);
   // 以及一些其他的例如构建值的生成规则等设定
   ```

3. 使用`MockDataCreator`的`mock`方法

   ```java
   // 使用类的方式构建Person实例
   Person claPerson = creator.mock(Person.class);
   // 使用对象的方式构建其中的属性，返回值是其对象自身
   Person selfPerson = creator.mock(new Person());
   // 使用Lambda的方式构建类的某个属性，返回值是此属性的实例
   Long id = creator.mock(Person::getId());
   ```
   
对于数据的生成规则来说，每个业务或是每个人或是每个类甚至是每个属性都可能有不同的定义，所以我们才需要有契合自己需要的数据构建工具来帮助我们做数据生成工作。

在这里，__mock-data__ 就提供了很多自定义的功能，方便开发者根据自己的实际需要去调整，例如以下功能：

- __控制值的生成范围__
  - 在内置的数据构建器中，绝大部分都提供了范围参数，例如`new LongRandomCreator(-100, 200)`，
    开发者只需要通过`creator.addDefaultCreator(new LongRandomCreator(-100, 200))`即可对所有的 __Long__ 属性进行构建范围控制。
- __给两个相同类型的属性不同的构建规则__
  - 例如在`Person`中有一个 __height__ 和 __weight__ 两个 __double__ 类型的属性，但这两个的生成规则不相同。
  - 使用`MockConfig`的`addFieldCreator(Person::getHeight, new HeightCreator())`和`addFieldCreator(Person::getWeight, new WeightCreator())`即可将两个属性分别做构建控制。
- __存在循环引用的值需要递归创建__
  - __mock-data__ 本身是支持循环引用的，但为了避免生成的数据过大，开发这可以使用`setCreatingDepth(int)`进行循环引用的构建深度设定。

更多的设置在 [MockConfig方法说明](MockConfig.md) 中有说明。
