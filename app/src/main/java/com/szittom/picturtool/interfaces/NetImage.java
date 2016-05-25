package com.szittom.picturtool.interfaces;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/25.
 */
public abstract class NetImage implements Serializable {
    public abstract String getThumbImg();

    public abstract String getLargeImg();

    public abstract int getWidth();

    public abstract int getHeight();
}
