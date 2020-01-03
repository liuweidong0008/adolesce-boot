/*******************************************************************************
 * @(#)EncodeTest.java 2019年09月20日 21:01
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot;

import com.boot.framework.utils.EscapeUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <b>Application name：</b> EncodeTest.java <br>
 * <b>Application describing： </b> <br>
 * <b>@Date：</b> 2019年09月20日 21:01 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
public class EncodeTest {

    /**
     *
     * URLDecoder编码解码
     */
    @Test
    public void test1(){
        String encode = null;
        String url = "https://www.baidu.com?k1=v1&k2=v2";
        try {
            System.out.println("编码前：" + url);
            encode = URLEncoder.encode(url, "GBK");
            System.out.println("编码后：" + encode);
            String decode = URLDecoder.decode(encode, "GBK");// GBK解码
            System.out.println("解码后："+decode);

            //System.out.println(URLDecoder.decode("redirect_uri%3Dhttps%253A%252F%252Fapp.yidab.com%252Fyyf-app%252Fwechat%252Fcallback.do%253Fchannel%253D9%2526redirectUrl%253D%252Fauthorization%253FsalesmanId%253D2540%2526publicityId%253D1175038332313894912%26scope"));

            System.err.println(URLDecoder.decode("https://www.baidu.com/s?ie=UTF-8&wd=%E6%B5%8B%E8%AF%95","GBK"));
            System.err.println(URLDecoder.decode("https://www.baidu.com/s?ie=UTF-8&wd=%E6%B5%8B%E8%AF%95","UTF-8"));
            System.err.println(URLEncoder.encode("https://baike.baidu.com/item/%E6%98%93%E7%83%8A%E5%8D%83%E7%8E%BA/221450?fr=aladdin","UTF-8"));
            System.err.println(URLEncoder.encode("https://baike.baidu.com/item/%E6%98%93%E7%83%8A%E5%8D%83%E7%8E%BA/221450?fr=aladdin&salesmanId=2539","UTF-8"));
            System.err.println(URLDecoder.decode("https%3A%2F%2Fbaike.baidu.com%2Fitem%2F%25E6%2598%2593%25E7%2583%258A%25E5%258D%2583%25E7%258E%25BA%2F221450%3Ffr%3Daladdin","UTF-8"));

            System.err.println();
            System.err.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escape编码解码
     */
    @Test
    public void test2() throws UnsupportedEncodingException {
        String encode = null;
        //String url = "https://www.baidu.com?k1=v1&k2=v2";
        //String url = "https://www.gravatar.com/avatar/00000000000000000000000000000000?d=identicon%26f=y";
        String url = "https://www.baidu.com?k1=v1&k2=v2";
        System.out.println("编码前：" + url);
        encode = EscapeUtils.escape(url);
        System.out.println("编码后：" + encode);
        String decode = EscapeUtils.unescape(encode);// GBK解码
        System.out.println("解码后："+decode);
        https://www.baidu.com?k1=v1&k2=v2

        /*https%3A%2F%2Fwww.baidu.com%3Fk1%3Dv1%26k2%3Dv2
        https%3a%2f%2fwww%2ebaidu%2ecom%3fk1%3dv1%26k2%3dv2
        %3A ：
        %2F /
        %2E .
        %3F ?
        %3D =
        %26 &
        %20 空格
        */
        System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx680f894598927e4f&" +
                "redirect_uri=https://app.yidab.com/yyf-app/wechat/callback.do?channel=9&" +
                    "redirectUrl=/authorization?salesmanId=2540&publicityId=1175038332313894912&scope=snsapi_userinfo&response_type=code&scope=snsapi_userinfo&state=myscH5#wechat_redirect");
    }
}