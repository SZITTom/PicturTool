package com.szittom.picturtool.interfaces;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface SearcherConstructor {
    public HashMap<String,String> getHeader();
    public String getUrl(String word, int page);
    public NetImage[] getImageList(String response);
}
