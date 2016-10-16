package org.redcenter.export.entity;

import org.redcenter.export.annotation.ExcelColumn;
import org.redcenter.export.annotation.ExcelSheet;

@ExcelSheet
public class CustEntity
{
    @ExcelColumn(order = 2)
    private String key;

    @ExcelColumn(name = "theValue", order = 1)
    private String value;

    @ExcelColumn
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
