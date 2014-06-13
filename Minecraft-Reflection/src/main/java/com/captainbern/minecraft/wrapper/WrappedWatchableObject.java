package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.accessor.ConstructorAccessor;
import com.captainbern.reflection.accessor.FieldAccessor;

import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withType;

public class WrappedWatchableObject extends AbstractWrapper {

    private static FieldAccessor<Integer> ID_ACCESSOR;
    private static FieldAccessor<Integer> INDEX_ACCESSOR;
    private static FieldAccessor<Object> VALUE_ACCESSOR;
    private static FieldAccessor<Boolean> DIRTY_STATE_ACCESSOR;

    private static ConstructorAccessor CREATE_CONSTRUCTOR;

    private static boolean IS_INITIALIZED;

    public WrappedWatchableObject(Object handle) {
        super(MinecraftReflection.getWatchableObjectClass());
        loadFromObject(handle);
    }

    public WrappedWatchableObject(int index, Object value) {
        super(MinecraftReflection.getWatchableObjectClass());

        if (value == null) {
            throw new IllegalArgumentException("Given value can't be NULL!");
        }

        Integer typeId = WrappedDataWatcher.getTypeID(value);

        if (typeId != null) {
            if (CREATE_CONSTRUCTOR == null) {
                try {
                    CREATE_CONSTRUCTOR = new Reflection().reflect(MinecraftReflection.getWatchableObjectClass()).getSafeConstructor(int.class, int.class, Object.class).getAccessor();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to retrieve the constructor!");
                }
            }

            try {
                loadFromObject(CREATE_CONSTRUCTOR.invoke(typeId, index, value));
            } catch (Exception e) {
                throw new RuntimeException("Failed to create a WatchableObject!");
            }
        } else {
            throw new IllegalArgumentException("Cannot watch type: " + value.getClass().getCanonicalName());
        }
    }

    protected static void initialize() {
        if (IS_INITIALIZED) {
            return;
        } else {
            IS_INITIALIZED = true;
        }

        ClassTemplate watchableObjectTemplate = new Reflection().reflect(MinecraftReflection.getWatchableObjectClass());

        List<SafeField<Integer>> intFields = watchableObjectTemplate.getSafeFields(withType(int.class));

        try {
            ID_ACCESSOR = intFields.get(0).getAccessor();
            INDEX_ACCESSOR = intFields.get(1).getAccessor();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve the id and index fields!");
        }

        VALUE_ACCESSOR = watchableObjectTemplate.getSafeFieldByType(Object.class).getAccessor();
        DIRTY_STATE_ACCESSOR = watchableObjectTemplate.getSafeFieldByType(boolean.class).getAccessor();
    }

    protected void loadFromObject(Object handle) {
        initialize();

        setHandle(handle);

        Class<?> handleClass = handle.getClass();
        if (!MinecraftReflection.getWatchableObjectClass().isAssignableFrom(handleClass)) {
            throw new ClassCastException("Cannot cast: " + handleClass.getCanonicalName() + " to: " + MinecraftReflection.getWatchableObjectClass().getCanonicalName());
        }
    }

    public int getId() {
        return ID_ACCESSOR.get(this.getHandle());
    }

    public void setId(int id) {
        ID_ACCESSOR.set(this.getHandle(), id);
    }

    public int getIndex() {
        return INDEX_ACCESSOR.get(this.getHandle());
    }

    public void setIndex(int index) {
        INDEX_ACCESSOR.set(this.getHandle(), index);
    }

    public Object getValue() {
        return VALUE_ACCESSOR.get(this.getHandle());
    }

    public void setValue(Object value) {
        setValue(value, true);
    }

    public void setValue(Object value, boolean update) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot watch a NULL value!");
        }

        if (update) {
            setDirty(true);
        }

        VALUE_ACCESSOR.set(this.getHandle(), value);
    }

    public boolean isDirty() {
        return DIRTY_STATE_ACCESSOR.get(this.getHandle());
    }

    public void setDirty(boolean dirty) {
        DIRTY_STATE_ACCESSOR.set(this.getHandle(), dirty);
    }
}
