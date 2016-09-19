package org.redcenter.export;

import java.io.IOException;
import java.util.List;

public interface IExporter
{
    public void exoprt(List<?> records) throws IOException, IllegalArgumentException, IllegalAccessException;
    
    public void append(List<?> records) throws Exception;

    public void close() throws IOException;
}
