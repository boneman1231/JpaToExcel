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
    protected static final String NEW_LINE = "\r\n";
    protected static final String SEPARATOR = ",";

    public CsvExporter(File file) throws FileNotFoundException
    {
        super(file);
    }

    @Override
    protected void export(List<T> records, boolean includeHeader, ExcelVo vo)
            throws IOException, IllegalAccessException
    {
        StringBuilder sb = new StringBuilder();

        // create header
        if (includeHeader)
        {
            createHeader(sb);
        }

        // create values
        createValue(records, sb);

        // write to file        
        os.write(sb.toString().getBytes());
    }

    private void createHeader(StringBuilder sb)
    {
        for (Entry<String, Field> entry : map.entrySet())
        {
            sb.append(entry.getKey() + SEPARATOR);
        }
        sb.append(NEW_LINE);
        line++;
    }

    private void createValue(List<T> records, StringBuilder sb) throws IllegalAccessException
    {
        for (Object record : records)
        {
            for (Entry<String, Field> entry : map.entrySet())
            {
                Field field = entry.getValue();
                Object value = field.get(record);
                if (value == null)
                {
                    // avoid NullPointerException for value.toString()
                    value = "";
                }
                sb.append(value + SEPARATOR);
            }
            sb.append(NEW_LINE);
            line++;
        }
    }
}
