package com.szittom.picturtool.http;

/**
 * Created by SZITTom on 2016/4/19.
 */
public class ApiException extends RuntimeException{

    public static final int WRONG_REQUEST = 100;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case WRONG_REQUEST:
                message = "请求错误";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}
