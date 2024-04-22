package idea.verlif.mock.data.config.creator;

import idea.verlif.mock.data.config.SizeCreator;

/**
 * 固定大小创建器
 */
public final class StaticSizeCreator implements SizeCreator {

    private final int size;

    public StaticSizeCreator(int size) {
        this.size = size;
    }

    @Override
    public int getSize(Class<?> cla) {
        return size;
    }
}
