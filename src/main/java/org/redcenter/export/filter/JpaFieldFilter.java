package org.redcenter.export.filter;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.redcenter.export.api.IFieldFilter;

public class JpaFieldFilter implements IFieldFilter
{
    public ExcelVo getExcelVo(Class<?> clazz)
    {
        ExcelVo vo = new ExcelVo();

        // setup sheet name
        String sheetName = clazz.getSimpleName();
        Entity classAnnotation = clazz.getAnnotation(Entity.class);
        if (classAnnotation == null)
        {
            // skip non JPA Entity classes
            return null;
        }
        if (classAnnotation.name() != null && !classAnnotation.name().isEmpty())
        {
            sheetName = classAnnotation.name();
        }
        vo.setSheetName(sheetName);

        // setup columns
        setupColumns(clazz, vo);

        return vo;
    }

    private void setupColumns(Class<?> clazz, ExcelVo vo)
    {
        LinkedHashMap<String, Field> map = new LinkedHashMap<String, Field>();
        Class<?> superClass = clazz;
        do
        {
            // skip non JPA classes
            if (!clazz.isAnnotationPresent(Entity.class) && !clazz.isAnnotationPresent(MappedSuperclass.class))
            {
                superClass = superClass.getSuperclass();
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
                        // make first char upper-case
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    }
                    else
                    {
                        // upper case for column name
//                        name = name.toUpperCase();
                    }
                    
                    field.setAccessible(true);
                    map.put(name, field);
                }
            }
            superClass = superClass.getSuperclass();
        }
        while (superClass != null);
        
        vo.setColumnMap(map);
    }
}
