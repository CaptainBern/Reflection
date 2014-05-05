/*
 *  CaptainBern-Reflection-Framework contains several utils and tools
 *  to make Reflection easier.
 *  Copyright (C) 2014  CaptainBern
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.captainbern.reflection;

import com.captainbern.reflection.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ReflectedClassImpl<T> extends AbstractAccess<T> implements ReflectedClass<T> {

    public ReflectedClassImpl(Class<T> clazz) {
        this(clazz, false);
    }

    public ReflectedClassImpl(Class<T> clazz, boolean forceAccess) {
        super(clazz, forceAccess);
    }

    @Override
    public List<ReflectedClass> getSuperClasses() {
        List<ReflectedClass> classes = new ArrayList<>();
        for(Class<?> clazz : ReflectionUtils.getAllSuperClasses(this.clazz)) {
            classes.add(Reflection.reflect(clazz));
        }
        return classes;
    }
}
