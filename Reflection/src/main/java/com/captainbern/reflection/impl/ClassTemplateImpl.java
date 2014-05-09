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

package com.captainbern.reflection.impl;

import com.captainbern.reflection.AbstractAccess;
import com.captainbern.reflection.ClassTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.captainbern.reflection.Reflection.reflect;

public class ClassTemplateImpl<T> extends AbstractAccess<T> implements ClassTemplate<T> {

    public ClassTemplateImpl(Class<T> clazz) {
        this(clazz, false);
    }

    public ClassTemplateImpl(Class<T> clazz, boolean forceAccess) {
        super(clazz, forceAccess);
    }

    @Override
    public T newInstance() {
        try {
            return this.clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassTemplate> getSuperClasses() {
        List<ClassTemplate> classes = new ArrayList<>();
        for(Class<?> clazz : super.getAllSuperClasses()) {
            classes.add(reflect(clazz));
        }
        return classes;
    }
}
