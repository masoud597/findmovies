package com.example.findmovies;

public class Resource<T> {
    public enum Status {
        LOADING, SUCCESS, ERROR
    }

    private final Status status;
    private final T data;
    private final String message;

    public Resource (Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> Success (T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> Error (String message, T data) {
        return new Resource<>(Status.ERROR, data, message);
    }

    public static <T> Resource<T> Loading () {
        return new Resource<>(Status.LOADING, null, null);
    }

    public Status getStatus () {
        return status;
    }

    public T getData () {
        return data;
    }

    public String getMessage () { return message; }
}
