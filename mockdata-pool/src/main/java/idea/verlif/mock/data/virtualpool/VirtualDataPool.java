package idea.verlif.mock.data.virtualpool;

import idea.verlif.mock.data.config.DataPool;
import idea.verlif.mock.data.virtualpool.template.*;
import idea.verlif.reflection.domain.ClassGrc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟数据池，提供了更多的模拟真实数据，让随机数据更加真实
 */
public class VirtualDataPool implements DataPool {

    private final Map<String, SimplePool> poolMap;
    private final Map<String, String> aliasMap;

    public VirtualDataPool() {
        this.poolMap = new HashMap<>();
        this.aliasMap = new HashMap<>();
    }

    /**
     * 使用预设模板
     */
    public VirtualDataPool withTemplate() {
        replace("name", new ChineseNameStringPool());
        mapType("name", "name");
        replace("age", new LimitIntPool(15, 25));
        mapType("age", "age");
        replace("birthday", new LimitDateDatePool(LocalDate.now().plusYears(-80), LocalDate.now().plusYears(-15)));
        mapType("birthday", "birthday", "birthDay");
        replace("weight", new LimitIntPool(40, 100, 50, 75));
        mapType("weight", "weight");
        replace("height", new LimitIntPool(140, 200, 150, 175));
        mapType("height", "height");
        replace("id", new ContinuousIntPool());
        mapType("id", "id");
        replace("idCard", new IdNumberStringPool());
        mapType("idCard", "idCard", "identityCard");
        replace("address", new AddressStringPool());
        mapType("address", "address", "addr");
        replace("email", new EmailStringPool());
        mapType("email", "email");
        replace("gender", new GenderStringPool());
        mapType("gender", "gender", "sex");
        replace("ip", new IpStringPool());
        mapType("ip", "ip");
        replace("website", new WebsiteStringPool());
        mapType("website", "url", "website");
        replace("fruit", new FruitStringPool());
        mapType("fruit", "fruit");
        replace("vegetable", new VegetableStringPool());
        mapType("vegetable", "vegetable");
        replace("uuid", new UUIDStringPool());
        mapType("uuid", "uuid", "guid");

        return this;
    }

    /**
     * 添加或替换数据池
     *
     * @param type       数据名称
     * @param simplePool 数据池
     */
    public void add(String type, SimplePool simplePool) {
        replace(type, simplePool);
        mapType(type, type);
    }

    /**
     * 替换数据类型的数据池
     *
     * @param type       目标数据类型
     * @param simplePool 此数据类型的数据池
     */
    public void replace(String type, SimplePool simplePool) {
        poolMap.put(type, simplePool);
    }

    /**
     * 获取数据类型的数据池
     *
     * @param type 目标数据类型
     * @return 此数据类型的数据池
     */
    public SimplePool getType(String type) {
        return poolMap.get(type);
    }

    /**
     * 映射属性名称与数据类型
     *
     * @param aliases 属性名称
     * @param type    数据类型
     */
    public void mapType(String type, String... aliases) {
        for (String alias : aliases) {
            aliasMap.put(alias, type);
        }
    }

    @Override
    public Object[] getValues(ClassGrc classGrc, String key) {
        String type = aliasMap.get(key);
        if (type != null) {
            SimplePool simplePool = poolMap.get(type);
            if (simplePool != null) {
                Object mock = simplePool.fetch(classGrc, key);
                if (mock == null) {
                    return null;
                }
                return new Object[]{mock};
            }
        }
        return null;
    }

}
