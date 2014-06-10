package com.captainbern.minecraft.wrapper;

import com.captainbern.minecraft.conversion.BukkitConverters;
import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.ConstructorAccessor;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.google.common.base.Objects;
import org.bukkit.entity.Entity;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.captainbern.reflection.matcher.Matchers.*;

public class WrappedDataWatcher extends AbstractWrapper implements Iterable<WrappedWatchableObject> {

    private static ConstructorAccessor<?> CREATE_CONSTRUCTOR;

    // Accessors
    private static FieldAccessor<Map> TYPE_MAP_ACCESSOR;
    private static FieldAccessor<Map> VALUE_MAP_ACCESSOR;
    private static FieldAccessor<ReadWriteLock> READ_WRITE_LOCK_FIELD;
    private static FieldAccessor<Object> ENTITY_FIELD;

    // Methods
    private static MethodAccessor CREATE_KEY_VALUE_METHOD;
    private static MethodAccessor UPDATE_KEY_VALUE_METHOD;
    private static MethodAccessor GET_KEY_VALUE_METHOD;

    private static boolean IS_INITIALIZED;

    private static Map<Class<?>, Integer> TYPE_MAP;

    private ReadWriteLock readWriteLock;

    private Map<Integer, Object> watchableObjects;
    private Map<Integer, WrappedWatchableObject> mapView;

    public WrappedDataWatcher() {
        super(MinecraftReflection.getDataWatcherClass());

        try {
            if (MinecraftReflection.isUsingNetty()) {
                setHandle(newEntityHandle(null));
            } else {
                setHandle(getType().newInstance());
            }
            initialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create a new DataWatcher", e);
        }
    }

    public WrappedDataWatcher(final Object handle) {
        super(MinecraftReflection.getDataWatcherClass());
        setHandle(handle);
        initialize();
    }

    @Override
    public Iterator<WrappedWatchableObject> iterator() {
        return getWatchableObjects().iterator();
    }

    private static void initialize() {
        if (IS_INITIALIZED) {
            return;
        } else {
            IS_INITIALIZED = true;
        }

        ClassTemplate dataWatcherTemplate = new Reflection().reflect(MinecraftReflection.getDataWatcherClass());
        for (Object field : dataWatcherTemplate.getSafeFields(withType(Map.class))) {
            SafeField<?> safeField = (SafeField<?>) field;
            if (Modifier.isStatic(safeField.getModifiers())) {
                TYPE_MAP_ACCESSOR = (FieldAccessor<Map>) safeField.getAccessor();
            } else {
                VALUE_MAP_ACCESSOR = (FieldAccessor<Map>) safeField.getAccessor();
            }
        }

        TYPE_MAP = TYPE_MAP_ACCESSOR.getStatic();

        // The ReadWriteLock
        FieldAccessor<ReadWriteLock> readWrite = dataWatcherTemplate.getSafeFieldByType(ReadWriteLock.class).getAccessor();
        if (readWrite != null)
            READ_WRITE_LOCK_FIELD = readWrite;

        if (MinecraftReflection.isUsingNetty()) {
            ENTITY_FIELD = dataWatcherTemplate.getSafeFieldByType(MinecraftReflection.getEntityClass()).getAccessor();
        }

        initializeMethods(dataWatcherTemplate);
    }

    private static void initializeMethods(ClassTemplate dataWatcher) {
        List<SafeMethod> methods = dataWatcher.getSafeMethods(withArguments(new Class[] {int.class, Object.class}));

        try {
            GET_KEY_VALUE_METHOD = ((SafeMethod<?>) dataWatcher.getSafeMethods(withReturnType(MinecraftReflection.getWatchableObjectClass())).get(0)).getAccessor();
        } catch (Exception e) {

        }

        for (SafeMethod method : methods) {
            if (method.getName().startsWith("watch")) {
                UPDATE_KEY_VALUE_METHOD = method.getAccessor();
            } else {
                CREATE_KEY_VALUE_METHOD = method.getAccessor();
            }
        }

        if (UPDATE_KEY_VALUE_METHOD == null || CREATE_KEY_VALUE_METHOD == null) {
            if (methods.size() > 1) {
                CREATE_KEY_VALUE_METHOD = methods.get(0).getAccessor();
                UPDATE_KEY_VALUE_METHOD = methods.get(1).getAccessor();
            } else {
                throw new IllegalStateException("Failed to find create and update watchable object methods!");
            }

            try {
                WrappedDataWatcher watcher = new WrappedDataWatcher();
                watcher.setObject(0, 0);
                watcher.setObject(0, 1);

                if (watcher.getInteger(0) != 1) {
                    throw new IllegalStateException("This cannot be!");
                }
            } catch (Exception e) {
                UPDATE_KEY_VALUE_METHOD = methods.get(0).getAccessor();
                CREATE_KEY_VALUE_METHOD = methods.get(1).getAccessor();
            }
        }
    }

    private static Object newEntityHandle(Entity entity) {
        try {
            Class<?> dataWatcherClass = MinecraftReflection.getDataWatcherClass();
            if (CREATE_CONSTRUCTOR == null)
                CREATE_CONSTRUCTOR = new Reflection().reflect(dataWatcherClass).getSafeConstructor(MinecraftReflection.getEntityClass()).getAccessor();

            return CREATE_CONSTRUCTOR.invoke(BukkitConverters.getInstance().convert(entity));
        } catch (Exception e) {
            throw new RuntimeException("Unable to create a new datawatcher", e);
        }
    }

    public Entity getEntity() {
        if (!MinecraftReflection.isUsingNetty())
            throw new IllegalStateException("This method is only supported in 1.7.x and above!");
        try {
            return MinecraftReflection.toBukkitEntity(ENTITY_FIELD.get(this.getHandle()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve the entity!");
        }
    }

    public void setEntity(Entity entity) {
        if (!MinecraftReflection.isUsingNetty())
            throw new IllegalStateException("This method is only supported in 1.7.x and above!");

        try {
            ENTITY_FIELD.set(this.getHandle(), BukkitConverters.getInstance().convert(entity));
        } catch (Exception e) {
            throw new RuntimeException("Failed to set the entity!");
        }
    }

    protected ReadWriteLock getReadWriteLock() {
        try {
            if (this.readWriteLock != null) {
                return this.readWriteLock;
            } else if (READ_WRITE_LOCK_FIELD == null) {
                return this.readWriteLock = new ReentrantReadWriteLock();
            } else {
                return this.readWriteLock = READ_WRITE_LOCK_FIELD.get(this.getHandle());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the ReadWriteLock!");
        }
    }

    protected Map<Integer, Object> getWatchableObjectMap() {
        if (watchableObjects == null)
            watchableObjects = VALUE_MAP_ACCESSOR.get(this.getHandle());
        return this.watchableObjects;
    }

    public List<WrappedWatchableObject> getWatchableObjects() {
        Lock readLock = getReadWriteLock().readLock();
        readLock.lock();

        try {
            List<WrappedWatchableObject> result = new ArrayList<WrappedWatchableObject>();

            for (Object watchable : getWatchableObjectMap().values()) {
                if (watchable != null) {
                    result.add(new WrappedWatchableObject(watchable));
                } else {
                    result.add(null);
                }
            }
            return result;

        } finally {
            readLock.unlock();
        }
    }

    public WrappedWatchableObject removeObject(int index) {
        Lock writeLock = getReadWriteLock().writeLock();
        writeLock.lock();

        try {
            Object removed = getWatchableObjectMap().remove(index);
            return removed != null ? new WrappedWatchableObject(removed) : null;
        } finally {
            writeLock.unlock();
        }
    }

    public void setObject(int index, Object value) {
        setObject(index, value, true);
    }

    public void setObject(int index, Object newValue, boolean update) {
        // Aquire write lock
        Lock writeLock = getReadWriteLock().writeLock();
        writeLock.lock();

        try {
            Object watchable = getWatchedObject(index);

            if (watchable != null) {
                new WrappedWatchableObject(watchable).setValue(newValue, update);
            } else {
                CREATE_KEY_VALUE_METHOD.invoke(this.getHandle(), index, newValue);
            }

            // Handle invoking the method
        } catch (Exception e) {
        } finally {
            writeLock.unlock();
        }
    }

    public Byte getByte(int index) {
        return (Byte) getObject(index);
    }

    public Short getShort(int index) {
        return (Short) getObject(index);
    }

    public Integer getInteger(int index){
        return (Integer) getObject(index);
    }

    public Float getFloat(int index) {
        return (Float) getObject(index);
    }

    public String getString(int index) {
        return (String) getObject(index);
    }

    public Object getObject(int index) {
        Object watchable = getWatchedObject(index);

        if (watchable != null) {
            return new WrappedWatchableObject(watchable).getValue();
        } else {
            return null;
        }
    }

    private Object getWatchedObject(int index) {
        if (GET_KEY_VALUE_METHOD != null) {
            try {
                return GET_KEY_VALUE_METHOD.invoke(this.getHandle(), index);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to get the watched object for index: " + index);
            }
        } else {
            try {
                getReadWriteLock().readLock().lock();
                return getWatchableObjectMap().get(index);
            } finally {
                getReadWriteLock().readLock().unlock();
            }
        }
    }

    public Map<Integer, WrappedWatchableObject> asMap() {
        if (mapView == null) {
             mapView = new HashMap<>();

             for (WrappedWatchableObject object : getWatchableObjects()) {
                 mapView.put(object.getIndex(), object);
             }
        }
        return mapView;
    }

    @Override
    public String toString() {
        return asMap().toString();
    }

    public int size() {
        Lock readLock = getReadWriteLock().readLock();
        readLock.lock();

        try {
            return getWatchableObjectMap().size();
        } finally {
            readLock.unlock();
        }
    }

    public static Integer getTypeID(Object value) {
        initialize();
        return TYPE_MAP.get(value);
    }

    public static Class<?> getTypeClass(int id) {
        initialize();

        for (Map.Entry<Class<?>, Integer> entry : TYPE_MAP.entrySet()) {
            if (Objects.equal(entry.getValue(), id)) {
                return entry.getKey();
            }
        }

        return null;
    }
}
