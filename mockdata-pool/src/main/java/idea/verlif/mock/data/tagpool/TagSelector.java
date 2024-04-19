package idea.verlif.mock.data.tagpool;

public class TagSelector {

    private static final ThreadLocal<String> LOCAL_TAG;
    static {
        LOCAL_TAG = new ThreadLocal<>();
    }


    public static String getTag() {
        return LOCAL_TAG.get();
    }

    public static void setTag(String tag) {
        LOCAL_TAG.set(tag);
    }
}
