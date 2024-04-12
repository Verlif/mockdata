package idea.verlif.mock.data.util;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Verlif
 */
public class ContainsUtil {

    public static boolean checkContains(String key, Set<String> stringSet, List<Pattern> patternList) {
        if (stringSet != null && stringSet.contains(key)) {
            return true;
        }
        if (patternList != null && patternList.size() != 0) {
            for (Pattern pattern : patternList) {
                if (pattern.matcher(key).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
