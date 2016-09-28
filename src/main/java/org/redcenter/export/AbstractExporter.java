package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import org.redcenter.export.api.IExporter;
import org.redcenter.export.api.IFieldFilter;
import org.redcenter.export.filter.CustFieldFilter;
import org.redcenter.export.filter.ExcelVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExporter<T> implements IExporter<T>
{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected OutputStream os;
    protected int line = 0;
    protected LinkedHashMap<String, Field> map = null;

    public AbstractExporter(File file) throws FileNotFoundException
    {
        os = new FileOutputStream(file);
    }

    public void export(List<T> records, boolean includeHeader)
            throws IOException, IllegalArgumentException, IllegalAccessException
    {
        // filter by customized annotation by default
        export(records, includeHeader, new CustFieldFilter());
        // export(records, includeHeader, new JpaFieldFilter());
    }

    public void export(List<T> records, boolean includeHeader, IFieldFilter filter)
            throws IOException, IllegalArgumentException, IllegalAccessException
    {
        if (records == null || records.size() == 0)
        {
            return;
        }

        // prepare columns by fields 
        Class<?> clazz = records.get(0).getClass();
        // map = filter.getFieldMap(clazz);
        ExcelVo vo = filter.getExcelVo(clazz);
        map = vo.getColumnMap();
        
        // start to process
        export(records, includeHeader, vo);
    }

    /**
     * 
     * @param records
     * @param includeHeader
     * @param vo
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    protected abstract void export(List<T> records, boolean includeHeader, ExcelVo vo)
            throws IOException, IllegalArgumentException, IllegalAccessException;

    public void close() throws IOException
    {
        os.close();
    }
}