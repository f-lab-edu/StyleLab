package com.stylelab.file.service;

import com.stylelab.file.dto.UploadFile;
import com.stylelab.file.dto.UploadResult;

import java.util.List;

public interface FileService {

    UploadResult uploadMultipartFiles(final List<UploadFile> uploadFiles);
}
