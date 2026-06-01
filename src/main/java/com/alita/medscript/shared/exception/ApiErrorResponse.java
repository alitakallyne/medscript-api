package com.alita.medscript.shared.exception;

import java.time.LocalDateTime;

public record ApiErrorResponse(
    LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {

}
