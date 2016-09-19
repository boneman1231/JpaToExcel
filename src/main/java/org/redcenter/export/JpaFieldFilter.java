package org.redcenter.export;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

public class JpaFieldFilter implements IFieldFilter
{
    public LinkedHashMap<String, Field> getFieldMap(Class<?> clazz)
    {
        LinkedHashMap<String, Field> map = new LinkedHashMap<String, Field>();
        Class<?> superClass = clazz;
        do
        {
            // skip non JPA classes
            if (clazz.getAnnotation(Entity.class) == null && clazz.getAnnotation(MappedSuperclass.class) == null)
            {
                continue;
            }

            for (Field field : superClass.getDeclaredFields())
            {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null)
                {
                    // get name by annotation
                    String name = columnAnnotation.name();
                    if (name == null || name.isEmpty())
                    {
                        name = field.getName();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    }
                    else
                    {
                        name = name.toUpperCase();
                    }
                    map.put(name, field);
                }
            }
            superClass = superClass.getSuperclass();
        }
        while (superClass != null);
        return map;
    }
}
