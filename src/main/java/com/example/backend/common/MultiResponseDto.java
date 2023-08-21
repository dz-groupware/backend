package com.example.backend.common;

import lombok.Getter;

import java.util.List;

@Getter
public class MultiResponseDto<T> {
    private List<T> data;
    private PageDto pageInfo;

    public MultiResponseDto(List<T> data, PageDto pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
