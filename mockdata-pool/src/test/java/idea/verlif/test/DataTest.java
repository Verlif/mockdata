package idea.verlif.test;

import idea.verlif.mock.data.MockDataCreator;
import idea.verlif.mock.data.pool.SimplePool;
import idea.verlif.mock.data.pool.VirtualDataPool;
import idea.verlif.mock.data.pool.template.ContinuousIntPool;
import idea.verlif.mock.data.pool.template.UUIDStringPool;
import idea.verlif.mock.data.pool.template.WebsiteStringPool;
import idea.verlif.reflection.domain.ClassGrc;
import idea.verlif.test.entity.Person;
import idea.verlif.test.entity.Pet;
import org.junit.Test;

public class DataTest {

    @Test
    public void mainTest() {
        MockDataCreator creator = new MockDataCreator();
        VirtualDataPool dataPool = new VirtualDataPool().withTemplate();
        dataPool.replace("uuid", new UUIDStringPool(false));
//        dataPool.replace("website", new WebsiteStringPool("http://"));
        creator.dataPool(dataPool);
        for (int i = 0; i < 100; i++) {
            Person person = creator.mock(Person.class);
            System.out.println(person.getUuid().length());
        }
    }

    @Test
    public void diyTest() {
        MockDataCreator creator = new MockDataCreator();
        VirtualDataPool dataPool = new VirtualDataPool().withTemplate();
        dataPool.add("nickname", new SimplePool() {
            @Override
            public Object fetch(ClassGrc classGrc, String key) {
                if (classGrc.getTarget() == Person.class) {
                    return "personNickname";
                } else if (classGrc.getTarget() == Pet.class) {
                    return "petNickname";
                }
                return null;
            }
        });
        creator.dataPool(dataPool);
        Pet pet = new Pet();
        for (int i = 0; i < 100; i++) {
            creator.mock(pet);
        }
    }

    @Test
    public void dataCreatorTest() {
        ContinuousIntPool continuousIntPool = new ContinuousIntPool();
        MockDataCreator creator = new MockDataCreator();
        creator.dataPool(new VirtualDataPool().withTemplate());
        Person person = creator.mock(Person.class);
        System.out.println(person.getId());
    }
}
