package com.example.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SingleResponseDto<T> {
    private T data;

    public SingleResponseDto(T data) {
        this.data = data;
    }
}