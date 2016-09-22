package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelExporter implements IExporter
{
    protected static final int MAX_ROW_IN_MEM = 100;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected OutputStream os;
    protected Workbook wb;
    protected Sheet sheet;
    protected int line = 0;
    protected LinkedHashMap<String, Field> map = null;

    public ExcelExporter(File file) throws FileNotFoundException
    {
        os = new FileOutputStream(file);
        wb = new SXSSFWorkbook(MAX_ROW_IN_MEM);
        sheet = wb.createSheet();
    }

    /**
     * Write records with header.
     */
    public void exoprt(List<?> records) throws IOException, IllegalArgumentException, IllegalAccessException
    {
        if (records == null || records.size() == 0)
        {
            return;
        }

        // prepare columns
        Class<?> clazz = records.get(0).getClass();
        IFieldFilter filter = new JpaFieldFilter();
        map = filter.getFieldMap(clazz);

        // create header with bold font
        createHeader();

        // create values
        writeValue(records);

        // write to file
        wb.write(os);
    }

    private void createHeader()
    {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);        
        style.setFont(font);
        
        Row row = sheet.createRow(line++);
        int colIndex = 0;
        for (Entry<String, Field> entry : map.entrySet())
        {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(entry.getKey());
            cell.setCellStyle(style);
        }
    }

    /**
     * Write records without header.
     * Need to execute export() first.
     */
    public void append(List<?> records) throws Exception
    {
        if (records == null || records.size() == 0)
        {
            return;
        }

        // prepare columns
        if (map == null)
        {
            throw new Exception("Please execute export() first.");
        }

        // create values
        writeValue(records);

        // write to file
        wb.write(os);
    }

    private void writeValue(List<?> records) throws IllegalAccessException
    {        
        for (Object record : records)
        {
            Row row = sheet.createRow(line++);
            int colIndex = 0;
            for (Entry<String, Field> entry : map.entrySet())
            {
                Cell cell = row.createCell(colIndex++);
                Field field = entry.getValue();
                field.setAccessible(true);
                Object value = field.get(record);
                if (value == null)
                {
                    value = "";
                }
                cell.setCellValue(value.toString());
            }
        }
    }

    public void close() throws IOException
    {
        wb.close();
        os.close();
    }
}
