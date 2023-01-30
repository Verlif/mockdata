package idea.verlif.mock.data.config.filter.impl;

import idea.verlif.mock.data.config.filter.FieldFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Verlif
 */
public class FieldModifierFilter implements FieldFilter {

    /**
     * 允许构建private关键字
     */
    private int modifiers = Modifier.PRIVATE;

    public boolean isAllowPrivate() {
        return Modifier.isPrivate(modifiers);
    }

    public void setAllowPrivate(boolean allowPrivate) {
        this.modifiers = allowPrivate ? this.modifiers & Modifier.PRIVATE : this.modifiers ^ Modifier.PRIVATE;
    }

    public boolean isAllowPublic() {
        return Modifier.isPublic(modifiers);
    }

    public void setAllowPublic(boolean allowPublic) {
        this.modifiers = allowPublic ? this.modifiers | Modifier.PUBLIC : this.modifiers ^ Modifier.PUBLIC;
    }

    public boolean isAllowProtect() {
        return Modifier.isProtected(modifiers);
    }

    public void setAllowProtect(boolean allowProtect) {
        this.modifiers = allowProtect ? this.modifiers | Modifier.PROTECTED : this.modifiers ^ Modifier.PROTECTED;
    }

    public boolean isAllowStatic() {
        return Modifier.isStatic(modifiers);
    }

    public void setAllowStatic(boolean allowStatic) {
        this.modifiers = allowStatic ? this.modifiers | Modifier.STATIC : this.modifiers ^ Modifier.STATIC;
    }

    public void setAllowedModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * 添加允许的属性修饰符
     *
     * @param modifiers 属性修饰符
     */
    public FieldModifierFilter allowedModifiers(int... modifiers) {
        for (int modifier : modifiers) {
            this.modifiers |= modifier;
        }
        return this;
    }

    /**
     * 移除允许的属性修饰符
     *
     * @param modifiers 属性修饰符
     */
    public FieldModifierFilter blockedModifiers(int... modifiers) {
        for (int modifier : modifiers) {
            this.modifiers -= modifier;
        }
        return this;
    }

    public boolean isAllowedModifier(int mod) {
        return (mod & modifiers) != 0;
    }

    @Override
    public boolean accept(Field field) {
        return (field.getModifiers() | modifiers) == modifiers;
    }

}
