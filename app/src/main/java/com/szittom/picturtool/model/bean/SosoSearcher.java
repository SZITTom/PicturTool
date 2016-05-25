package com.szittom.picturtool.model.bean;

import com.google.gson.Gson;
import com.szittom.picturtool.interfaces.NetImage;
import com.szittom.picturtool.interfaces.SearcherConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/25.
 */
public class SosoSearcher implements SearcherConstructor {

    @Override
    public HashMap<String, String> getHeader() {
        return null;
    }

    @Override
    public String getUrl(String word, int page) {
        try {
            word = URLEncoder.encode(word, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "http://pic.sogou.com/pics?query=" + word + "&start=" + page * 24 + "&reqType=ajax&reqFrom=result";
    }

    @Override
    public NetImage[] getImageList(String response) {
        try {
            SosoImage.WallImageResult result = new Gson().fromJson(response, SosoImage.WallImageResult.class);
            SosoImage[] imgs = result.getData();
            return imgs;
        } catch (Exception e) {
            return new SosoImage[]{};
        }
    }

    public class SosoImage extends NetImage {
        private String thumbUrl;
        private String pic_url_noredirect;
        private int thumb_width;
        private int thumb_height;


        @Override
        public String getThumbImg() {
            return thumbUrl;
        }

        @Override
        public String getLargeImg() {
            return pic_url_noredirect;
        }

        @Override
        public int getWidth() {
            return thumb_width;
        }

        @Override
        public int getHeight() {
            return thumb_height;
        }

        public class WallImageResult {
            private SosoImage[] items;

            public SosoImage[] getData() {
                return items;
            }

            public void setData(SosoImage[] data) {
                this.items = data;
            }

        }
    }
}
