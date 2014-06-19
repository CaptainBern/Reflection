package com.captainbern.minecraft.collection;

import java.util.Collection;
import java.util.Set;

public abstract class WrappedSet<VUnwrapped, VWrapped> extends WrapperCollection<VUnwrapped, VWrapped> implements Set<VWrapped> {

    public WrappedSet(Collection<VUnwrapped> vUnwrappeds) {
        super(vUnwrappeds);
    }
}
