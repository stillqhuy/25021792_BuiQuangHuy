package com.practice;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileServiceTest {
    @Test
    public void testGetReportPath() {
        FileService service = new FileService();
       
        String expected = "docs" + File.separator + "report.txt"; 
        assertEquals(expected, service.getReportPath("docs", "report.txt"), 
            "Đường dẫn file phải được nối chính xác đa nền tảng");
    }
}
