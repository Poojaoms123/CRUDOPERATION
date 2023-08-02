package com.anervea.employee.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
    private String fileName;
    private String filePath;
    private String fileType;
    private String mediaType;

    public FileUploadResponse(String fileName, String filePath, String fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public FileUploadResponse() {

    }
}
