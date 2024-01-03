package com.stylelab.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtensionType {
    PNG(".png"),
    JPG(".jpg"),
    JPEG(".jpeg"),
    WEBP(".webp");

    private final String extension;
}
