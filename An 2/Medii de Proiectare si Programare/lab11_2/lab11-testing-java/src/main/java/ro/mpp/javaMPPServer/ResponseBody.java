package ro.mpp.javaMPPServer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class ResponseBody<T> {
    private T data;
    private String message;

    @JsonCreator
    public ResponseBody(@JsonProperty("body") T body, @JsonProperty("message") String message) {
        this.data = body;
        this.message = message;
    }

    public T getData() {
        return data;
    }
}