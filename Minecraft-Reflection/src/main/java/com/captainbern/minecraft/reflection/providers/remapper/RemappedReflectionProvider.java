package com.captainbern.minecraft.reflection.providers.remapper;

import com.captainbern.minecraft.reflection.ReflectionConfiguration;
import com.captainbern.minecraft.reflection.providers.StandardReflectionProvider;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.accessor.MethodAccessor;
import com.captainbern.reflection.provider.type.ClassProvider;
import com.captainbern.reflection.provider.type.FieldProvider;
import com.captainbern.reflection.provider.type.MethodProvider;
import com.captainbern.reflection.provider.type.impl.DefaultFieldProvider;
import org.bukkit.Bukkit;

import java.util.Map;

/**
 * Thanks to bergerkiller/BKCommonLib for these excellent "hacks"
 */
public class RemappedReflectionProvider extends StandardReflectionProvider {

    private Object remapper;
    private MethodAccessor<String> mapType;
    private MethodAccessor<String> mapField;
    private Map<String, String> classes;
    private Map<String, String> fields;
    private Map<String, String> methods;

    public RemappedReflectionProvider(final ReflectionConfiguration configuration) {
        super(configuration);
        init();
    }

    private RemappedReflectionProvider init() {
        if (Bukkit.getServer() == null || !Bukkit.getServer().getVersion().contains("MCPC-Plus")) {
            throw new RemapperException(RemapperException.Reason.MCPC_NOT_PRESENT);
        }

        this.remapper = new Reflection().reflect(getConfiguration().getClassLoader().getClass()).getSafeFieldByName("remapper").getAccessor().get(getConfiguration().getClassLoader());

        if (remapper == null) {
            throw new RemapperException(RemapperException.Reason.REMAPPER_DISABLED);
        }

        ClassTemplate<?> remapperTemplate = new Reflection().reflect(this.remapper.getClass());
        this.mapType = remapperTemplate.getSafeMethod("map", String.class).getAccessor();
        this.mapField = remapperTemplate.getSafeMethod("mapFieldName", String.class, String.class, String.class, int.class).getAccessor();

        Object jarMapping = remapperTemplate.getSafeFieldByName("jarMapping");
        ClassTemplate<?> jarMappingTemplate = new Reflection().reflect(jarMapping.getClass());
        //this.classes = (Map<String, String>) jarMappingTemplate.getSafeFieldByName("classes").getAccessor().getStatic();
        //this.fields = (Map<String, String>) jarMappingTemplate.getSafeFieldByName("fields").getAccessor().getStatic();
        //this.methods = (Map<String, String>) jarMappingTemplate.getSafeFieldByName("methods").getAccessor().getStatic();

        return this;
    }

    @Override
    public Class<?> loadClass(String className) {
        String remapped = getRemappedClassName(getConfiguration().getPackagePrefix() + "." + className);
        try {
            return getConfiguration().getClassLoader().loadClass(remapped);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load class: " + className + " (remapped: " + remapped + ")");
        }
    }

    private String getRemappedClassName(final String className) {
        try {
            return this.mapType.invoke(this.remapper, className.replace('.', '/')).replace('/', '.');
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to remap class: " + className);
        }
    }

    private String getOriginalClassName(final Class<?> clazz) {
        String name = clazz.getCanonicalName().replace('.', '/');
        String result = name;

        for (Map.Entry<String, String> entry : this.classes.entrySet()) {
            if (entry.getValue().equals(name)) {
                result = entry.getKey();

                if (result.contains(getConfiguration().getPackagePrefix())) {
                    return result;
                }
            }
        }

        return result;
    }

    // https://github.com/md-5/SpecialSource/blob/master/src/main/java/net/md_5/specialsource/JarRemapper.java#L157
    public String getRemappedFieldName(final Class<?> type, final String name) {
        return mapField.invoke(this.remapper, getOriginalClassName(type), name, -1);
    }

    public String getRemappedMethodName(final Class<?> type, final String name, final Class<?>... args) {
        final String path = getOriginalClassName(type) + "/" + name + " ";

        for (Map.Entry<String, String> entry : this.methods.entrySet()) {
            if (entry.getKey().startsWith(path)) {
                try {
                    type.getDeclaredMethod(name, args);

                    return entry.getValue();
                } catch (NoSuchMethodException e) {
                    // Swallow
                }
            }
        }

        if (type != null && !type.equals(Object.class))
            return getRemappedMethodName(type, name, args);

        return name;
    }

    @Override
    public <T> ClassProvider<T> getClassProvider(Reflection reflection, String className, boolean forceAccess) throws ClassNotFoundException {
        return (ClassProvider<T>) getClassProvider(reflection, this.loadClass(className), forceAccess);
    }

    @Override
    public <T> FieldProvider<T> getFieldProvider(Reflection reflection, Class<?> clazz, String fieldName) {
        try {
            return new DefaultFieldProvider<T>(reflection, clazz.getDeclaredField(getRemappedFieldName(clazz, fieldName)));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to find field: " + fieldName + " (remapped: " + getRemappedFieldName(clazz, fieldName) + ") in class: " + clazz.getCanonicalName());
        }
    }

    @Override
    public <T> MethodProvider<T> getMethodProvider(Reflection reflection, Class<?> clazz, String methodName, Class... args) {
        try {
            return getMethodProvider(reflection, clazz.getDeclaredMethod(getRemappedMethodName(clazz, methodName, args), args));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to find method: " + methodName + " (remapped: " + getRemappedMethodName(clazz, methodName, args) + ") in class: " + clazz.getCanonicalName());
        }
    }

    @Override
    public String toString() {
        return "RMR-Provider";
    }
}
