package com.captainbern.minecraft.wrapper.nbt;

import com.captainbern.minecraft.collection.WrapperList;
import com.captainbern.minecraft.reflection.utils.Accessor;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class WrappedNbtTagList<T> implements WrappedNbtTag<List<NbtTagBase<T>>>, Iterable<T>, NbtTagList<T> {

    private WrappedNbtElement<List<Object>> handle;

    private WrapperList<Object, NbtTagBase<T>> nbtList;

    private NbtType listType;

    public WrappedNbtTagList(Object nmsHandle) {
        this.handle = new WrappedNbtElement<>(nmsHandle);

        Accessor<Byte> bytes = new Accessor(this.handle.getHandle()).withType(byte.class);
        this.listType = NbtType.getTypeForId(bytes.read(0));
    }

    @Override
    public NbtType getListType() {
        return this.listType;
    }

    @Override
    public void setListType(NbtType nbtType) {
        this.listType = nbtType;
    }

    @Override
    public void add(NbtTagBase<T> nbtTagBase) {
        getValue().add(nbtTagBase);
    }

    @Override
    public void add(byte b) {
        add((NbtTagBase<T>) NbtFactory.forObject(b));
    }

    @Override
    public void add(short s) {
        add((NbtTagBase<T>) NbtFactory.forObject(s));
    }

    @Override
    public void add(int i) {
        add((NbtTagBase<T>) NbtFactory.forObject(i));
    }

    @Override
    public void add(long l) {
        add((NbtTagBase<T>) NbtFactory.forObject(l));
    }

    @Override
    public void add(float f) {
        add((NbtTagBase<T>) NbtFactory.forObject(f));
    }

    @Override
    public void add(double d) {
        add((NbtTagBase<T>) NbtFactory.forObject(d));
    }

    @Override
    public void add(byte[] ba) {
        add((NbtTagBase<T>) NbtFactory.forObject(ba));
    }

    @Override
    public void add(String s) {
        add((NbtTagBase<T>) NbtFactory.forObject(s));
    }

    @Override
    public void add(int[] ia) {
        add((NbtTagBase<T>) NbtFactory.forObject(ia));
    }

    @Override
    public void remove(Object value) {
        getValue().remove(value);
    }

    @Override
    public T getValue(int index) {
        return getValue().get(index).getValue();
    }

    @Override
    public Collection<NbtTagBase<T>> toCollection() {
        return getValue();
    }

    @Override
    public int size() {
        return getValue().size();
    }

    @Override
    public Iterator<T> iterator() {
        return Iterables.transform(getValue(), new Function<NbtTagBase<T>, T>() {
            @Override
            public T apply(@Nullable NbtTagBase<T> nbtTagBase) {
                return nbtTagBase.getValue();
            }
        }).iterator();
    }

    @Override
    public Object getHandle() {
        return this.handle.getHandle();
    }

    @Override
    public NbtType getType() {
        return this.handle.getType();
    }

    @Override
    public List<NbtTagBase<T>> getValue() {
        if (this.nbtList == null) {
            this.nbtList = new WrapperList<Object, NbtTagBase<T>>(this.handle.getValue()) {
                protected void verify(NbtTagBase<T> element) {
                    if (element == null)
                        throw new IllegalArgumentException("Cannot add a NULL object to the NBT-list!");

                    if (!WrappedNbtTagList.this.getListType().equals(NbtType.TAG_END)) {
                        if (!WrappedNbtTagList.this.getListType().equals(element.getType())) {
                            throw new IllegalArgumentException("Cannot add an element of type: " + element.getType() + " to " +
                                    "an NBT-list of type: " + WrappedNbtTagList.this.getListType());
                        }
                    } else {
                        WrappedNbtTagList.this.setListType(element.getType());
                    }
                }

                @Override
                public boolean add(NbtTagBase<T> e) {
                    verify(e);
                    return super.add(e);
                }

                @Override
                public void add(int index, NbtTagBase<T> element) {
                    verify(element);
                    super.add(index, element);
                }

                @Override
                public boolean addAll(Collection<? extends NbtTagBase<T>> c) {
                    boolean result = false;

                    for (NbtTagBase<T> element : c) {
                        add(element);
                        result = true;
                    }
                    return result;
                }

                @Override
                public NbtTagBase<T> toWrapped(Object o) {
                    if (o == null)
                        return null;

                    return NbtFactory.fromNmsHandle(o);
                }

                @Override
                public Object toUnwrapped(NbtTagBase<T> nbtTagBase) {
                    if (nbtTagBase == null)
                        return null;

                    return NbtFactory.fromNbtBase(nbtTagBase).getHandle();
                }
            };
        }

        return this.nbtList;
    }

    @Override
    public void setValue(List<NbtTagBase<T>> value) {
        NbtTagBase<T> lastElement = null;
        List<Object> list = this.handle.getValue();
        list.clear();

        for (NbtTagBase<T> nbtTag : value) {
            if (nbtTag != null) {
                lastElement = nbtTag;
                list.add(NbtFactory.fromNbtBase(nbtTag).getHandle());
            } else {
                list.add(null);
            }
        }

        if (lastElement != null)
            this.setListType(lastElement.getType());
    }
}
