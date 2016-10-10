package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.redcenter.export.api.IExporter;
import org.redcenter.export.api.IFieldFilter;
import org.redcenter.export.entity.CustEntity;
import org.redcenter.export.entity.JpaEntity;
import org.redcenter.export.filter.JpaFieldFilter;

public abstract class ExporterTest
{
    protected void testJpaExport(String fileName) throws IllegalArgumentException, IllegalAccessException, IOException
    {
        export(getJpaList(), fileName, new JpaFieldFilter());
    }

    protected void testCustExport(String fileName) throws IllegalArgumentException, IllegalAccessException, IOException
    {
        export(getCustList(), fileName, new JpaFieldFilter());
    }

    private List<JpaEntity> getJpaList()
    {
        JpaEntity entity = new JpaEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        entity.setTestColumn("test");
        List<JpaEntity> list = new ArrayList<JpaEntity>();
        list.add(entity);
        return list;
    }

    protected String[] getJpaHeader()
    {
        return new String[] { "Name", "Key", "test_column" };
    }
    
    protected String[] getJpaContent()
    {
        return new String[] { "n", "k", "test" };
    }

    private List<CustEntity> getCustList()
    {
        CustEntity entity = new CustEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        List<CustEntity> list = new ArrayList<CustEntity>();
        list.add(entity);
        return list;
    }

    protected String[] getCustHeader()
    {
        return new String[] { "Name", "theValue", "Key" };
    }

    protected String[] getCustContent()
    {
        return new String[] { "n", "v", "k" };
    }
    
    private <T> void export(List<T> list, String fileName, IFieldFilter filter)
            throws IOException, IllegalArgumentException, IllegalAccessException
    {
        IExporter<T> exporter = null;
        try
        {
            File file = new File(fileName);
            exporter = getExporter(file);
            exporter.export(list, true, filter);
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    protected abstract <T> IExporter<T> getExporter(File file) throws FileNotFoundException;
}
