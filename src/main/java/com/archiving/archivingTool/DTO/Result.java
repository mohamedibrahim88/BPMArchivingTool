package com.archiving.archivingTool.DTO;

public class Result<T> {
    String status ;
    T data;

    public Result() {
    }

    public Result(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
