package com.hk.iamges.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * 爬虫工具类
 */
public class JsoupUtils {

    /** 网站连接*/
    private String url;

    /** 本地存储路径， 必须存在*/
    private String filePath;

    /** 网站地址 */
    private Set<String> urls = new HashSet<>();

    /** 所有图片路径 */
    private Set<String> images = new HashSet<>();

    public JsoupUtils(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    /**
     * 下载文件
     * @param s 图片路口
     * @param name 图片名称
     * @throws Exception
     */
    public void download(String s, String name) throws Exception {
        System.out.println("----要加载的网站：" + s);
        URL url = new URL(s);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        byte[] bs = new byte[1024];

        int len;
        String filename = filePath + "\\"+ name +".jpg";
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        os.close();
        is.close();
    }

    /**
     * 获取图片路径
     * @param query
     */
    public void getImages(String query) throws Exception {
        for(String s : urls) {
            Document document = getHtml(s);
            Elements e = document.select(query);
            String u = e.attr("src");
            System.out.println("当前获取的图片路径：");
            System.out.println(u);
            this.images.add( this.url + u);
            this.download(this.url + u, e.attr("alt"));
        }
    }

    /**
     * 获取网页列表
     * @param path 路径
     * @param query 过滤条件
     */
    public void getHtmlSet(String path, String query) {
        Elements elements = getHtml(this.url + path).select(query);
        for (Element e : elements) {
            String u = e.attr("href");
            urls.add(this.url + u);
        }
    }

    /**
     * 获取网页对象
     * @param url
     * @return
     */
    public Document getHtml(String url) {
        Document element = null;
        try {
            element = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("获取页面失败！");
        }
        return element;
    }
}
