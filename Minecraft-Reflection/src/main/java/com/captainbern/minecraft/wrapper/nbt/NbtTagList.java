package com.captainbern.minecraft.wrapper.nbt;

import java.util.List;

public interface NbtTagList<T> extends NbtTagBase<List<NbtTagBase<T>>>, Iterable<T> {
}
