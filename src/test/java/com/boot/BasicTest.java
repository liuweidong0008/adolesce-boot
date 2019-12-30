/*******************************************************************************
 * @(#)BasicTest.java 2019年09月17日 11:30
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.boot.common.utils.UUIDUtils;
import com.boot.entity.MyAddress;
import com.boot.user.entity.User;
import com.boot.users.entity.Users;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <b>Application name：</b> BasicTest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年09月17日 11:30 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
public class BasicTest {

    /**
     * 测试实体对象数据传递
     */
    @Test
    public void test1(){
        User user = new User();
        user.setName("lwd");
        user.setAge(12);

        List<User> users = new ArrayList<User>();
        Map<String,User> map = new HashMap<String,User>();

        users.add(user);
        map.put("1",user);

        User user1 = user;
        user1.setName("ly");
        user1.setAge(22);
        System.out.println(user);
        System.out.println(users);
        System.out.println(map.get("1"));
    }

    /**
     * 测试SnowFlakeIdUtils生成ID
     */
    @Test
    public void test2(){
       System.out.println(UUIDUtils.getUUID());
       System.out.println(Long.valueOf(UUIDUtils.getUUID()));
    }

    /**
     * 测试HuTool DateUtil获取当前时间
     */
    @Test
    public void test3(){
       System.out.println(DateUtil.date());
       System.out.println(new Date());
    }

    /**
     * 测试获取java.io.tmpdir 路径
     */
    @Test
    public void test4(){
        String path = System.getProperty("java.io.tmpdir");
        System.out.println(path);
    }

    /**
     * 时间戳
     */
    @Test
    public void test5(){
        Date start = DateUtil.parse("2019-09-20 20:49:17");
        Date end = DateUtil.parse("2020-10-31 20:49:17");

        System.out.println(start.getTime());
        System.out.println(end.getTime());
    }

    /**
     * new BigDecimal(5.158)形式会产生很多位小数
     * 用valueOf或format形式代替
     * format形式有四舍五入效果
     */
    @Test
    public void test6(){
        BigDecimal b1 = new BigDecimal(5.158);
        BigDecimal b2 = BigDecimal.valueOf(5.158);

        DecimalFormat format = new DecimalFormat("0.00");

        System.out.println(b1);
        System.out.println(format.format(b1));
        System.out.println(b2);
    }

    /**
     * 先创建对象，再传递进方法进行值修改
     */
    @Test
    public void test7(){
        Users users = null;
        if(users == null){
            users = new Users();
        }
        this.disUsers(users);
        System.out.println(users);
    }

    private Users disUsers(Users users) {
        if(users == null){
            users = new Users();
        }
        users.setUserName("ly");
        return users;
    }

    /**
     * 测试基本类型比较（基本类型 == 是比较值是否相等）
     * 基本数据类型和String常量都是存储在方法区常量池中的，指向的都是同一内存地址，可以通过==来直接比较
     */
    @Test
    public void test8(){
        int a = 1;
        int b = 1;
        System.out.println(a == b);
        System.out.println(a);
    }

    /**
     * 测试包装类型比较
     * == 是比较两个对象地址指向是否一样（但是-128 ~ 127是在缓冲池中，这个范围中的Integer堆地址指向都是同一个，不在这个范围就新new一个）
     * JDK源码（low：-128，high：127）
     * public static Integer valueOf(int i) {
     *     if (i >= IntegerCache.low && i <= IntegerCache.high)
     *         return IntegerCache.cache[i + (-IntegerCache.low)];
     *     return new Integer(i);
     * }
     *
     * equals先intValue进行自动拆箱，再==比较基本数据是否相等
     *  Integer equals源码：
     *  public boolean equals(Object obj) {
     *         if (obj instanceof Integer) {
     *             return value == ((Integer)obj).intValue();
     *         }
     *         return false;
     *     }
     *
     */
    @Test
    public void test9(){
        Integer a = 128;
        Integer b = 128;

        Integer c = 127;
        Integer d = 127;
        System.out.println(a == b); //不在 -128~127范围
        System.out.println(c == d); //在 -128~127范围

        System.out.println(a.equals(b)); //先拆箱再比较基本数据是否相等
        System.out.println(c.equals(d)); //先拆箱再比较基本数据是否相等
    }


    /**
     * 测试字符串比较及相关知识点
     * String类是由final修饰的，所以是无法被继承的，一旦创建了String对象，我们就无法改变它的值。因此，它是线程安全的，可以安全地用于多线程环境中。
     * 一、创建方式：
     * 1、通过new关键字通过构造方法去创建，此时仅仅在JVM的堆内存中创建了一个对象
     * 2、通过双引号“”创建：如果字符串常量池存在该字符串，直接返回该字符串对象在字符串常量池的地址，否则创建一个新的字符串对象并存储在字符串常量池。
     * 3、通过字符串连接符+和其余字符串进行拼接创建（底层是通过创建几个StringBuilder进行append然后toString）
     *
     * 二、StringBuffer 和 StringBuilder 二者都继承了 AbstractStringBuilder ，底层都是利用可修改的char数组(JDK 9 以后是 byte数组)。
     * 两者的区别是StringBuilder是线程不安全的，而StringBuffer是线程安全的。性能上来说，StringBuilder要高于StringBuffer。
     *
     * 三、单线程情况下，如有大量的字符串操作情况，不能使用String来拼接而是使用StringBuilder，避免产生大量无用的中间对象，耗费空间且执行效率低下（新建对象、回收对象花费大量时间）
     *     多线程情况下，应当使用StringBuffer来保证线程的安全~
     *
     * 四、String s = new String("a") 创建了2个对象  常量池中的“a”和堆内存中new的s
     */
    @Test
    public void test10(){
        String a = "ab";
        String b = "ab";
        String c = new String("ab");
        String d = "ab"+"ef";
        String e = "ef";

        //true，比较的是放置于常量池中的字符串
        System.out.println("a == b : " + (a == b));
        //true，比较的字符串内容
        System.out.println("a.equals(b) : " + a.equals(b));
        //false，a是放置于常量池中的字符串，c一个是在堆中new的对象
        System.out.println("a == c : " + (a == c));
        //true，比较的是字符串内容
        System.out.println("a.equals(c) : " + a.equals(c));
        //true，比较的是放置于常量池中的字符串
        System.out.println("e == \"ef\" : "+ (e == "ef"));

        //false
        System.out.println("(\"ab\"+e) == \"abef\" : " + (("ab"+e) == "abef"));
        //true
        System.out.println("(\"ab\"+e).equals(d) : " + ("ab"+e).equals(d));
        //true
        System.out.println("a == (\"a\"+\"b\") : "+ (a == ("a"+"b")));
        //true
        System.out.println("(\"ab\"+\"ef\") == \"abef\" : "+ (("ab"+"ef") == "abef"));
        //false
        System.out.println("(a+e) == \"abef\" : "+ ((a+e) == "abef"));
        //false
        System.out.println("(a+e) == d : "+ ((a+e) == d));

        String s1 = null;
        String s2 = "abc";
        //nullabc
        System.out.println(s1 + s2);
        //相当于以下：（如有大量的字符串操作情况，不能使用String来拼接而是使用StringBuilder，避免产生大量无用的中间对象）
        StringBuilder sb1 = new StringBuilder(String.valueOf("null"));
        StringBuilder sb2 = new StringBuilder("abc");
        sb1.append(sb2).toString();
    }

    /**
     * 用最少循环将具有父子关系对象列表转换成树结构
     */
    @Test
    public void test11(){
        List<MyAddress> list = getAddressList();
        Map<String, MyAddress> map = Maps.uniqueIndex(list, myAddress -> myAddress.getCode());
        Consumer<MyAddress> consumer = myAddress -> {
            //如果为空则为根节点，不做处理
            if(Objects.nonNull(myAddress.getParentCode())){
                MyAddress parentMyAddress = map.get(myAddress.getParentCode());
                if(Objects.isNull(parentMyAddress.getChildMyAddresses())){
                    parentMyAddress.setChildMyAddresses(new ArrayList<MyAddress>());
                }
                parentMyAddress.getChildMyAddresses().add(myAddress);
            }
        };

        list.stream().forEach(consumer);
        String rootCode = list.stream().filter(myAddress -> myAddress.getParentCode() == null).
                collect(Collectors.toList()).get(0).getCode();
        System.out.println(map.get(rootCode));
    }

    private List<MyAddress> getAddressList() {
        MyAddress myAddress0 = new MyAddress("0",null);

        MyAddress myAddress1 = new MyAddress("1","0");
        MyAddress myAddress2 = new MyAddress("2","0");

        MyAddress myAddress3 = new MyAddress("11","1");
        MyAddress myAddress4 = new MyAddress("12","1");
        MyAddress myAddress5 = new MyAddress("13","1");

        MyAddress myAddress6 = new MyAddress("21","2");
        MyAddress myAddress7 = new MyAddress("22","2");
        MyAddress myAddress8 = new MyAddress("23","2");

        List<MyAddress> list = new ArrayList<>();
        list.add(myAddress0);
        list.add(myAddress1);
        list.add(myAddress2);
        list.add(myAddress3);
        list.add(myAddress4);
        list.add(myAddress5);
        list.add(myAddress6);
        list.add(myAddress7);
        list.add(myAddress8);
        return list;
    }

    @Test
    public void test12(){
        Set<String> sets = new HashSet<String>();

        String s1 = "abc";
        String s2 = "abcd";
        String s3 = "abc";
        List<String> list1 = new ArrayList<String>();
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);

        String s4 = "abc";
        String s5 = "abcd";
        String s6 = "abc";
        List<String> list2 = new ArrayList<String>();
        list2.add(s4);
        list2.add(s5);
        list2.add(s6);

        sets.addAll(list1);
        sets.addAll(list2);
        System.out.println(sets);
    }

    @Test
    public void test13(){
       new HashMap<>(0);
        System.out.println(DateTime.now().toString());
        System.out.println("A"+"    aa   bb   ".trim()+"B");
        System.out.println("A"+ StringUtils.trim("    aa   bb   ")+"B");
        System.out.println("A"+ StringUtils.trimToEmpty("    aa   bb   ")+"B");
        System.out.println("A"+ StringUtils.trimToNull("    aa   bb   ")+"B");

        System.out.println("A"+ "       ".trim()+"B");
        System.out.println("A"+ StringUtils.trim("       ")+"B");
        System.out.println("A"+ StringUtils.trimToEmpty("       ")+"B");
        System.out.println("A"+ StringUtils.trimToNull("       ")+"B");

        System.out.println(StringUtils.isEmpty("    "));
        System.out.println(StringUtils.isBlank("    "));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank(null));
        System.out.println(StringUtils.isBlank(null));


    }

}