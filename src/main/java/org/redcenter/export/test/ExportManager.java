package org.redcenter.export.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExportManager
{

    List<String> stringList = new ArrayList<String>();
    List<Integer> integerList = new ArrayList<Integer>();

    public static void main(String... args) throws Exception
    {
        // Field stringListField =
        // ExportManager.class.getDeclaredField("stringList");
        // ParameterizedType stringListType = (ParameterizedType)
        // stringListField.getGenericType();
        // Class<?> stringListClass = (Class<?>)
        // stringListType.getActualTypeArguments()[0];
        // System.out.println(stringListClass); // class java.lang.String.

        ArrayList<String> records = new ArrayList<String>();
        records.add("test");
        records.add("test2");

        ParameterizedType p = (ParameterizedType) records.getClass().getGenericSuperclass();
        Type x = p.getActualTypeArguments()[0];
        System.out.println(x);

        // new ExportManager().export(records);

        Field field = ExportManager.class.getDeclaredField("stringList");
        printType(field.getGenericType());

        Method[] methods = ExportManager.class.getMethods();
        for (Method method : methods)
        {
            Type[] types = method.getGenericParameterTypes();
            for (Type type : types)
            {
                printType(type);
            }
        }
    }

    private static void printType(Type type)
    {
        if (type instanceof ParameterizedType)
        {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] argTypes = paramType.getActualTypeArguments();
            if (argTypes.length > 0)
            {
                System.out.println("Generic type is " + argTypes[0]);
            }
        }
    }

    public <T> void export(List<T> records)
    {
        ParameterizedType p = (ParameterizedType) records.getClass().getGenericSuperclass();
        final Class<?> c = (Class<?>) p.getRawType();

        Type x = p.getActualTypeArguments()[0];
        System.out.println(x);
    }
}
