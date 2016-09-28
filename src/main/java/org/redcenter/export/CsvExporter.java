package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;

import org.redcenter.export.api.IExporter;
import org.redcenter.export.filter.ExcelVo;

/**
 * 
 * @author boneman
 *
 * @param <T>
 */
public class CsvExporter<T> extends AbstractExporter<T> implements IExporter<T>
{
    protected static final int MAX_ROW_IN_MEM = 1000;
    protected static final String newline = "\n\r"; //TODO

    public CsvExporter(File file) throws FileNotFoundException
    {
        super(file);
    }

    @Override
    protected void export(List<T> records, boolean includeHeader, ExcelVo vo)
            throws IOException, IllegalArgumentException, IllegalAccessException
    {
        // create header
        if (includeHeader)
        {
            createHeader();
        }

        // create values
        createValue(records);

        // write to file
        // TODO
    }

    private void createHeader()
    {
//        Row row = sheet.createRow(line++);
        for (Entry<String, Field> entry : map.entrySet())
        {
            //TODO
        }
    }
    
    private void createValue(List<T> records) throws IllegalAccessException
    {
        for (Object record : records)
        {
//            Row row = sheet.createRow(line++);
            for (Entry<String, Field> entry : map.entrySet())
            {
                Field field = entry.getValue();
                field.setAccessible(true);
                Object value = field.get(record);
                if (value == null)
                {                    
                    // avoid NullPointerException for value.toString()
                    value = "";
                }
//                cell.setCellValue(value.toString());
                //TODO
            }
        }   
    }
}
