package com.captainbern.minecraft.wrapper.nbt.io;

import com.captainbern.minecraft.reflection.MinecraftReflection;
import com.captainbern.minecraft.wrapper.nbt.NbtFactory;
import com.captainbern.minecraft.wrapper.nbt.NbtTagBase;
import com.captainbern.minecraft.wrapper.nbt.WrappedNbtTag;
import com.captainbern.reflection.ClassTemplate;
import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeMethod;
import com.captainbern.reflection.accessor.FieldAccessor;
import com.captainbern.reflection.accessor.MethodAccessor;

import java.io.*;
import java.util.List;

import static com.captainbern.reflection.matcher.Matchers.withArguments;
import static com.captainbern.reflection.matcher.Matchers.withReturnType;

/**
 * A Class used to serialize and deserialize NBT-tags.
 *
 * Note: This only works with MC 1.7.8 and above
 */
public class NbtSerializer {

    protected static MethodAccessor<Void> WRITE;
    protected static MethodAccessor<Object> READ;

    protected static NbtLoad LOAD;

    private static interface NbtLoad {
        public Object load(DataInput dataInput);
    }

    public static void write(NbtTagBase base, DataOutput dataOutput) {
        try {

            if (WRITE == null) {
                ClassTemplate template = new Reflection().reflect(MinecraftReflection.getNbtCompressedStreamToolsClass());

                List<SafeMethod<Void>> candidates = template.getSafeMethods(withArguments(MinecraftReflection.getNbtBaseClass(),
                        DataOutput.class), withReturnType(void.class));

                if (candidates.size() > 0) {
                    WRITE = candidates.get(0).getAccessor();
                }
            }

            WRITE.invokeStatic(NbtFactory.fromNbtBase(base).getHandle(), dataOutput);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write tag: " + base, e);
        }
    }

    public static <T> WrappedNbtTag<T> read(DataInput dataInput) {
        try {

            if (READ == null) {
                Class<?> readLimiter = MinecraftReflection.getNbtReadLimiterClass();
                ClassTemplate tagTemplate = new Reflection().reflect(MinecraftReflection.getNbtCompressedStreamToolsClass());

                List<SafeMethod<Object>> candidates = tagTemplate.getSafeMethods(withArguments(DataInput.class, int.class, readLimiter), withReturnType(MinecraftReflection.getNbtBaseClass()));
                if (candidates.size() > 0) {
                    READ = candidates.get(0).getAccessor();
                } else {
                    throw new RuntimeException("Failed to retrieve the NBT-Load method!");
                }

                if (LOAD == null) {
                    ClassTemplate limiterTemplate = new Reflection().reflect(readLimiter);
                    FieldAccessor<Object> instanceField = limiterTemplate.getSafeFieldByType(readLimiter).getAccessor();
                    final Object singletonInstance = instanceField.getStatic();

                    LOAD = new NbtLoad() {
                        @Override
                        public Object load(DataInput dataInput) {
                            return READ.invokeStatic(dataInput, 0, singletonInstance);
                        }
                    };
                }
            }

             return NbtFactory.fromNmsHandle(LOAD.load(dataInput));

        } catch (Exception e) {
            throw new RuntimeException("Failed to read tag!", e);
        }
    }

    public static byte[] toBytes(NbtTagBase<?> tagBase) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteStream);

        write(tagBase, outputStream);

        return byteStream.toByteArray();
    }

    public static <T> NbtTagBase<T> toTag(byte[] bytes) {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes), 8192));

        return read(inputStream);
    }
}
