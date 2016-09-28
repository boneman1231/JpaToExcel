package org.redcenter.export.api;

import org.redcenter.export.filter.ExcelVo;

public interface IFieldFilter
{
    public ExcelVo getExcelVo(Class<?> clazz);
}
