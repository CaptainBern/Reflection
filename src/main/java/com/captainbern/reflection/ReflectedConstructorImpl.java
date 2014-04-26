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

import com.captainbern.reflection.accessor.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.util.List;

public class ReflectedConstructorImpl implements ReflectedConstructor {

    @Override
    public Constructor member() {
        return null;
    }

    @Override
    public ConstructorAccessor getAccessor() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public List<ReflectedClass> getArguments() {
        return null;
    }

    @Override
    public ReflectedClass getType() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    @Override
    public int getModifiers() {
        return 0;
    }
}
