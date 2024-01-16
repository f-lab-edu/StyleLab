package com.stylelab.common.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagingResponse<T>(
        boolean firstPage,
        boolean lastPage,
        long totalElements,
        int totalPage,
        int page,
        int size,
        List<T> items
) {

    public static <T, P extends Page<T>> PagingResponse<T> createPagingResponse(P page) {
        return new PagingResponse<>(
                page.isFirst(),
                page.isLast(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.getContent()
        );
    }
}
