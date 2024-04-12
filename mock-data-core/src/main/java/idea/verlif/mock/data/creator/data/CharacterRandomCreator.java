package idea.verlif.mock.data.creator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 */
public class CharacterRandomCreator extends DictDataCreator<Character> {

    private static final Character[] DICT = new Character[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            ',', '.', '?', ';', '[', ']', '{', '}', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '|', '/', '<', '>', ':', '`',
            '\'', '"'
    };

    public CharacterRandomCreator() {
        super(DICT);
    }

    @Override
    public List<Class<?>> types() {
        List<Class<?>> list = new ArrayList<>();
        list.add(Character.class);
        list.add(char.class);
        return list;
    }

}
