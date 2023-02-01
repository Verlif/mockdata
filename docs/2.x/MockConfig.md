# 配置说明

`MockConfig`是构造器的配置，用来控制数据构造流程。合理地使用`MockConfig`可以最大程度地满足开发者的需求。

需要注意的是，在构建流程中，__忽略设置__ 优先度 __最高__，哪怕已经指定了数据构建器也不会执行构建。
__级联设定__ 优先度 __最低__，如果有对应的数据构建器，则会执行构建器的构建而不会进行级联构建。

## 调整引用深度

为了避免在循环引用导致的栈溢出问题，这里使用了引用深度设置，用来控制创建深度。

当使用两个参数时，第一个参数则表示了引用深度的生效范围。

- `creatingDepth([SFunction|Class], int)`

## 设置数组大小

在进行数组填充时，如果数组没有进行初始化，则数组大小就会由配置决定。

- `arraySize(int)`
  - 设置构建数组的通用大小
- `arraySize(SizeCreator)`
  - 自定义构建数组的大小

## 强制新建对象

在默认情况下，当属性有值时则会跳过此属性的创建。可以通过设定强制创建来重新创建对象。

- `forceNew(boolean)`

## 添加数据构建器

自定义数据构建器，方便添加随机数据生成规则。

- `fieldValue([SFunction|String|Class], Object|DataCreator)`

## 添加实例构建器

当存在无法直接新建的对象或需要在创建对象后特殊处理时，可以添加其对应的实例构建器。

推荐使用场景：
- 无法直接构造的类
- 需要指定实例化方法的类

- `instanceCreator(InstanceCreator)`

## 设定自动级联构建

如果不需要单独指定需要级联构建的类，可以使用自动级联构建，让构建器对所有类都进行级联构建。

默认情况 __false__

- `autoCascade(boolean)`

## 添加需要级联构建的属性

级联构建表示在创建此对象后，会继续构建此对象的内部属性。

- `cascadeCreateKey(SFunction|String|Class...)`
- `cascadeCreatePackage(String)`
  - 级联构建包下的所有类
- `cascadeCreatePattern(String...)`
  - 级联构建与正则表达式匹配的所有属性或类全名

## 添加需要忽略的属性

某些属性不需要或是不想要构建时可以通过此方法进行忽略。开发者可以通过此方法添加数个过滤器。

__默认情况下允许所有的属性与类进行构建__

- `filter(ClassFilter)`
  - 忽略指定类的构建
- `filter(FieldFilter)`
  - 忽略指定属性的构建

### ClassFilter

内置实现类：
- `ClassKeyFilter`
  - 类名过滤器，允许过滤掉 __指定类__、__指定包__、__类全名正则过滤__

### FieldFilter

内置实现类：
- `FiledKeyFilter`
  - 属性名过滤器，允许过滤 __指定类__、__指定类的指定属性__、__属性名正则过滤__
- `FieldModifierFilter`
  - 属性标志过滤器，允许指定例如`private`、`static`等允许或不允许的标志
