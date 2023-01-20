package idea.verlif.mock.data;

import idea.verlif.mock.data.config.MockDataConfig;
import idea.verlif.mock.data.creator.data.LongRandomCreator;
import idea.verlif.mock.data.domain.Person;
import org.junit.Test;

public class ValidationTest {

    @Test
    public void validate() throws IllegalAccessException {
        MockDataCreator creator = new MockDataCreator();
        MockDataConfig config = creator.getConfig();
        config.addFieldCreator(Person::getId, new LongRandomCreator(3, 10));

        for (int i = 0; i < 10; i++) {
            Person person = creator.mock(Person.class);
            if (person.getId() < 3 || person.getId() > 10) {
                System.out.println("错误的id范围设定");
            }
            person = creator.mock(person);
            if (person.getId() < 3 || person.getId() > 10) {
                System.out.println("错误的id范围设定");
            }
        }
    }

}
