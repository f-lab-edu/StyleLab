package com.stylelab.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PagingResponse<T>(
        Boolean firstPage,
        Boolean lastPage,
        Long totalElements,
        Integer totalPage,
        Integer page,
        Integer size,
        Long nextToken,
        List<T> items
) {

    public static <T, P extends Page<T>> PagingResponse<T> createOffSetPagingResponse(P page) {
        return new PagingResponse<>(
                page.isFirst(),
                page.isLast(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                null,
                page.getContent()
        );
    }

    public static <T, P extends Slice<T>> PagingResponse<T> createCursorPagingResponse(P page, Long nextToken) {
        return new PagingResponse<>(
                null,
                page.isLast(),
                null,
                null,
                page.getNumber(),
                page.getSize(),
                nextToken,
                page.getContent()
        );
    }
}
