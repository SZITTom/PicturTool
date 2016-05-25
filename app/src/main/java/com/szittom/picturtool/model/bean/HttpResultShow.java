package com.szittom.picturtool.model.bean;

/**
 * Created by SZITTom on 2016/4/21.
 */
public class HttpResultShow<T> {

    private boolean status;

    private T list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
