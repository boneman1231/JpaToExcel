package org.redcenter.export;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public interface IFieldFilter
{
    public LinkedHashMap<String, Field> getFieldMap(Class<?> clazz);
}
