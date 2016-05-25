package com.szittom.picturtool.model.bean;

/**
 * Created by SZITTom on 2016/4/19.
 */
public class HttpResult<T> {

    private boolean status;

    private int total;

    private T tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getTngou() {
        return tngou;
    }

    public void setTngou(T tngou) {
        this.tngou = tngou;
    }
}
