package org.redcenter.export;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.redcenter.export.annotation.ExcelColumn;
import org.redcenter.export.annotation.ExcelSheet;

//TODO
public class CustFieldFilter2
{
    public ExcelVo getExcelVo(Class<?> clazz)
    {
        //sheet
        String sheetName = clazz.getSimpleName();
        ExcelSheet classAnnotation = clazz.getAnnotation(ExcelSheet.class);
        if (classAnnotation != null && classAnnotation.name()!=null && !classAnnotation.name().isEmpty())
        {
            sheetName = classAnnotation.name();
        }
        
        // columns
        LinkedHashMap<String, Field> map = new LinkedHashMap<String, Field>();

        Class<?> superClass = clazz;
        do
        {
            // skip non JPA classes
            if (clazz.getAnnotation(ExcelSheet.class) == null)
            {
                continue;
            }

            for (Field field : superClass.getDeclaredFields())
            {
                ExcelColumn columnAnnotation = field.getAnnotation(ExcelColumn.class);
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
        return null;
    }
}
