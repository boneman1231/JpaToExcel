package org.redcenter.export;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.redcenter.export.entity.JpaEntity;

public class ExcelExporterTest
{

    @Test
    public void testExoprt()
    {
//        Assert.fail("Not yet implemented");
        try
        {
            exportExcel();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("End");
    }

    private void exportExcel() throws IOException
    {
//        Workbook wb = new SXSSFWorkbook(MAX_ROW_IN_MEM);
//        Sheet sheet = wb.createSheet();
//
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("test");
//
//        OutputStream os = new FileOutputStream("test.xlsx");
//        wb.write(os);
//        wb.close();
                
        JpaEntity entity = new JpaEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        List<JpaEntity> list = new ArrayList<JpaEntity>();
        list.add(entity);

        File file = new File("test.xlsx");
        IExporter exporter = new ExcelExporter(file );
        try
        {
            exporter.exoprt(list);
            exporter.close();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
