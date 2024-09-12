package com.simcheong.cctv._core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiUtils<T> {
    private final boolean success;
    private final T response;
    private final ApiError error;

    public static <T> ApiUtils<T> success(final T response) { return new ApiUtils<>(true, response, null); }

    public static ApiUtils<?> error(final String message, final HttpStatus status) {
        return new ApiUtils<>(false, null, new ApiError(message, status.value()));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ApiError {
        private final String message;
        private final int status;
    }
}
