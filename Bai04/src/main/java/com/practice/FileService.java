package com.practice;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {
    
    public String getReportPath(String folder, String filename) {
        Path path = Paths.get(folder, filename);
        return path.toString();
    }
}