/*******************************************************************************
 * @(#)SecureTest.java 2019年12月12日 16:03
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.secure;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.junit.Test;

import static cn.hutool.crypto.digest.HmacAlgorithm.*;

/**
 * <b>Application name：</b> SecureTest.java <br>
 * <b>Application describing：
 *  HMAC介绍
 *  HMAC，全称为“Hash Message Authentication Code”，中文名“散列消息鉴别码”，主要是利用哈希算法，以一个密钥和一个消息为输入，生成一个消息摘要作为输出。
 *  一般的，消息鉴别码用于验证传输于两个共同享有一个密钥的单位之间的消息。
 *  HMAC 可以与任何迭代散列函数捆绑使用。
 *  MD5 和 SHA-1 就是这种散列函数。HMAC 还可以使用一个用于计算和确认消息鉴别值的密钥。
 * </b> <br>
 * <b>@Date：</b> 2019年12月12日 16:03 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 */
public class HMACTest {
    //key
    private String key = "111232123423423452345234523431";
    //需加密数据
    private String data = "8866993334352阿斯顿发水电费1312312";

    /**
     * HmacMD5加密(32位)
     */
    @Test
    public void testHmacMD5(){
        this.hmacCommon(HmacMD5);

        String result3 = SecureUtil.hmacMd5(key).digestHex(data);
        System.out.println(result3);

        //46e9f27103010ed5ae384ce7ecfc4e05
        //46e9f27103010ed5ae384ce7ecfc4e05
        //46e9f27103010ed5ae384ce7ecfc4e05
    }


    /**
     * HmacSHA1加密(40位)
     */
    @Test
    public void testHmacSHA1(){
        this.hmacCommon(HmacAlgorithm.HmacSHA1);

        String result3 = SecureUtil.hmacSha1(key).digestHex(data);
        System.out.println(result3);

        //e027aa6659c5d65c60b37ddde11601c4aeb9bdb3
        //e027aa6659c5d65c60b37ddde11601c4aeb9bdb3
        //e027aa6659c5d65c60b37ddde11601c4aeb9bdb3
    }

    /**
     * HmacSHA256加密(64位)
     */
    @Test
    public void testHmacSHA256(){
        this.hmacCommon(HmacSHA256);

        //aec14f8b1c924d863a93c2d1f4a8479770fa28f4db6bcce6b3bf019167549885
        //aec14f8b1c924d863a93c2d1f4a8479770fa28f4db6bcce6b3bf019167549885
    }

    /**
     * HmacSHA384加密(96位)
     */
    @Test
    public void testHmacSHA384(){
        this.hmacCommon(HmacSHA384);

        //22e6fe6940164ee7b6ac48542246573239d62b679f6650f307f990fbe459f137550fe7323f10bb2995d2d5276ec34565
        //22e6fe6940164ee7b6ac48542246573239d62b679f6650f307f990fbe459f137550fe7323f10bb2995d2d5276ec34565
    }

    /**
     * HmacSHA512加密(128位)
     */
    @Test
    public void testHmacSHA512(){
        this.hmacCommon(HmacSHA512);

        //4d4a44e1c23911093426ae06e074cfe0ceceda14ff13436ec70454ec6b54ffa0471cb98d9767b87cecb6a6381758c6ad29fa42dc2c3a824a005883261524e295
        //4d4a44e1c23911093426ae06e074cfe0ceceda14ff13436ec70454ec6b54ffa0471cb98d9767b87cecb6a6381758c6ad29fa42dc2c3a824a005883261524e295
    }

    private void hmacCommon(HmacAlgorithm hmacSHA256) {
        HMac mac = new HMac(hmacSHA256, key.getBytes());
        String result1 = mac.digestHex(data);
        System.out.println(result1);

        String result2 = SecureUtil.hmac(hmacSHA256, key.getBytes()).digestHex(data);
        System.out.println(result2);
    }
}