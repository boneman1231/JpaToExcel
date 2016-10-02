package org.redcenter.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.redcenter.export.api.IExporter;
import org.redcenter.export.api.IFieldFilter;
import org.redcenter.export.entity.CustEntity;
import org.redcenter.export.entity.JpaEntity;
import org.redcenter.export.filter.CustFieldFilter;
import org.redcenter.export.filter.JpaFieldFilter;

public class CsvExporterTest
{

    @Test
    public void testJpaExoprt()
    {
        // Assert.fail("Not yet implemented");
        JpaEntity entity = new JpaEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        entity.setTestColumn("test");
        List<JpaEntity> list = new ArrayList<JpaEntity>();
        list.add(entity);
        
        export(list, "testJpa.csv", new JpaFieldFilter());
    }

    @Test
    public void testCustExoprt()
    {
        // Assert.fail("Not yet implemented");
        CustEntity entity = new CustEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        List<CustEntity> list = new ArrayList<CustEntity>();
        list.add(entity);
        
        export(list, "testCust.csv", new CustFieldFilter());
    }

    private <T> void export(List<T> list, String fileName, IFieldFilter filter)
    {
        try
        {
            File file = new File(fileName);
            IExporter<T> exporter = new CsvExporter<T>(file);
            exporter.export(list, true, filter);
            exporter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("End");
    }

    @Test
    public void testExoprtWithMassData()
    {
        // TODO
    }
}
