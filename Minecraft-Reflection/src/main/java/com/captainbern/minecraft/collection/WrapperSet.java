package com.captainbern.minecraft.collection;

import java.util.Collection;
import java.util.Set;

public abstract class WrapperSet<VUnwrapped, VWrapped> extends WrapperCollection<VUnwrapped, VWrapped> implements Set<VWrapped> {

    public WrapperSet(Collection<VUnwrapped> vUnwrappeds) {
        super(vUnwrappeds);
    }
}
