/*******************************************************************************
 * @(#)SecureTest.java 2019年12月12日 16:03
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce.secure;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import org.junit.Test;

/**
 * <b>Application name：</b> SecureTest.java <br>
 * <b>Application describing：
 *  摘要算法介绍
 *  摘要算法是一种能产生特殊输出格式的算法，这种算法的特点是：无论用户输入什么长度的原始数据，经过计算后输出的密文都是固定长度的，
 *  这种算法的原理是根据一定的运算规则对原数据进行某种形式的提取，这种提取就是摘要，被摘要的数据内容与原数据有密切联系，
 *  只要原数据稍有改变,输出的“摘要”便完全不同，因此，基于这种原理的算法便能对数据完整性提供较为健全的保障。
 *  但是，由于输出的密文是提取原数据经过处理的定长值，所以它已经不能还原为原数据，即消息摘要算法是不可逆的，理论上无法通过反向运算取得原数据内容，因此它通常只能被用来做数据完整性验证。
 * </b> <br>
 * <b>@Date：</b> 2019年12月12日 16:03 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 */
public class DigesterTest {
    //盐
    private String salt = "111";
    //需加密数据
    private String data = "886699";

    /**
     * MD5摘要加密（32位）
     */
    @Test
    public void testMD5(){
        //普通加密
        String result1 = SecureUtil.md5(data);
        //String result1 = DigestUtil.md5Hex(data);
        System.out.println(result1);

        //加盐加密
        String result2 = SecureUtil.md5().setSalt(salt.getBytes()).digestHex(data);
        System.out.println(result2);

        //6b10dbf4ead6d767f992c6005d3ce91d
        //5fa4d7a94215aef153579c85ff6fe2c6
    }

    /**
     * SHA1摘要加密（40位）
     */
    @Test
    public void testSHA1(){
        String result1 = SecureUtil.sha1(data);
        System.out.println(result1);

        String result2 = SecureUtil.sha1().setSalt(salt.getBytes()).digestHex(data);
        System.out.println(result2);

        //2018cb9c229516d95174737face2ed5ea6f03776
        //5fb24223f08565a35656d67ccbdd004e25cbc51e
    }

    /**
     * SHA256摘要加密（64位）
     */
    @Test
    public void testSHA256(){
        String result1 = SecureUtil.sha256(data);
        System.out.println(result1);

        String result2 = SecureUtil.sha256().setSalt(salt.getBytes()).digestHex(data);
        System.out.println(result2);

        //e78b488eaba23524f7b4581285672452a2bd0e26924a7ba6c4d840f5fa00e467
        //e7fd0e96878f5e243ac862cfac43f79ddcee04bde256ebeaebb5ee460d2a2c80
    }

    /**
     * SHA384摘要加密（96位）
     */
    @Test
    public void testSHA384(){
        String result1 = new Digester(DigestAlgorithm.SHA384).digestHex(data);
        System.out.println(result1);

        String result2 = new Digester(DigestAlgorithm.SHA384).setSalt(salt.getBytes()).digestHex(data);
        System.out.println(result2);

        //92c90dbd95e47793f554c4d4d9001661f5c9a0a872582a87d59e207c3d7379f841306f5d4b8ce5a4b545dd1381d16cb9
        //4b0b0985843ac9811175bd9f89ee4d97dd53b45f579d5430ab9f8e079b00d7c85599282774e801cf66be0ffefb6fa6d4
    }

    /**
     * SHA512摘要加密（128位）
     */
    @Test
    public void testSHA512(){
        String result1 = new Digester(DigestAlgorithm.SHA512).digestHex(data);
        System.out.println(result1);

        String result2 = new Digester(DigestAlgorithm.SHA512).setSalt(salt.getBytes()).digestHex(data);
        System.out.println(result2);

        //20f175ad516b6b72d1167eaaed446a3978e84c37b90e714264570a4d260eca8d8b4ebbf0b2f324ccc331896cad4f032764134409cee9ebb544443482511fa2ae
        //298c1b251e8bda95583ed99a2bde846f6f5ad40ec095688b2b33a53b67808f26c87154951477cc25721224f0da49cd6667badbf9d81126377ff29237ed9ac0ca

        String md5Hex1 = DigestUtil.md5Hex(data);
    }

}