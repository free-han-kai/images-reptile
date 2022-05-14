package com.hk.iamges;

import com.hk.iamges.utils.JsoupUtils;

public class ImageMain {

    public static void main(String[] args) throws Exception {
        JsoupUtils j = new JsoupUtils("https://pic.netbian.com" , "D:\\images");
        j.getHtmlSet("/4kmeinv", ".slist a");
        j.getImages(" #img img");
    }
}
