package com.sogeti.filmland.models;

import lombok.Data;

@Data
public class JsonDataResponse<T> extends JsonResponse {
    T data;

    public JsonDataResponse(String status, String message, T data) {
        super(status, message);
        this.data = data;
    }

}
