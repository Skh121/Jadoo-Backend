package com.example.tourtravel.shared;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GlobalApiResponse<T> {
    private String message;
    private T data;
    private Integer statusCode;
}
