/*******************************************************************************
 * @(#)LocalDateTest.java 2019年12月17日 20:26
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.adolesce;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * <b>Application name：</b> LocalDateTest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2019 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2019年12月17日 20:26 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
public class LocalDateTest {
    /**
     * LocalDate
     *
     * 只会获取年月日
     */
    @Test
    public void testLocalDate(){
        //创建LocalDate
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2019, 9, 10);

        //获取年、月、日、星期几
        int year = localDate.getYear();
        int year1 = localDate.get(ChronoField.YEAR);
        Month month = localDate.getMonth();
        int month1 = localDate.get(ChronoField.MONTH_OF_YEAR);
        int day = localDate.getDayOfMonth();
        int day1 = localDate.get(ChronoField.DAY_OF_MONTH);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK);

        System.out.println(localDate1);
        System.out.println(localDate);
        System.out.println(year);
        System.out.println(year1);
        System.out.println(month1);
        System.out.println(day);
        System.out.println(day1);
        System.out.println(dayOfWeek1);
    }

    /**
     * LocalTime
     *
     * 只会获取几点几分几秒
     */
    @Test
    public void testLocalTime(){
        //创建LocalTime
        LocalTime localTime = LocalTime.now();
        LocalTime localTime1 = LocalTime.of(13, 51, 10);

        //获取时分秒
        //获取小时
        int hour = localTime.getHour();
        int hour1 = localTime.get(ChronoField.HOUR_OF_DAY);
        //获取分
        int minute = localTime.getMinute();
        int minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR);
        //获取秒
        int second = localTime.getSecond();
        int second1 = localTime.get(ChronoField.SECOND_OF_MINUTE);

        System.out.println(localTime);
        System.out.println(localTime1);
        System.out.println(hour);
        System.out.println(hour1);
        System.out.println(minute);
        System.out.println(minute1);
        System.out.println(second);
        System.out.println(second1);
    }

    /**
     * LocalDateTime
     *
     * 获取年月日时分秒，等于LocalDate+LocalTime
     */
    @Test
    public void testLocalDateTime(){
        //创建LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.of(localDate, localTime);
        LocalDateTime localDateTime3 = localDate.atTime(localTime);
        LocalDateTime localDateTime4 = localTime.atDate(localDate);

        //获取LocalDate
        LocalDate localDate2 = localDateTime.toLocalDate();
        //获取LocalTime
        LocalTime localTime2 = localDateTime.toLocalTime();

        System.out.println(localDateTime);
        System.out.println(localDateTime1);
        System.out.println(localDateTime2);
        System.out.println(localDateTime3);
        System.out.println(localDateTime4);
        System.out.println(localDate2);
        System.out.println(localTime2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(formatter));
    }

    /**
     * Instant
     *
     * 获取秒数
     */
    @Test
    public void testInstant(){
        //创建Instant对象
        Instant instant = Instant.now();
        //获取秒数
        long currentSecond = instant.getEpochSecond();
        //获取毫秒数
        long currentMilli = instant.toEpochMilli();

        long current = System.currentTimeMillis();
        System.out.println(currentSecond);
        System.out.println(currentMilli);
        System.out.println(current);
    }

    /**
     * 修改LocalDate、LocalTime、LocalDateTime、Instant
     *
     */
    @Test
    public void testUpdate(){
        //LocalDate、LocalTime、LocalDateTime、Instant为不可变对象，修改这些对象对象会返回一个副本
        //增加、减少年数、月数、天数等 以LocalDateTime为例
        LocalDateTime localDateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 10,
                14, 46, 56);
        //增加一年
        localDateTime = localDateTime.plusYears(1);
        localDateTime = localDateTime.plus(1, ChronoUnit.YEARS);
        //减少一个月
        localDateTime = localDateTime.minusMonths(1);
        localDateTime = localDateTime.minus(1, ChronoUnit.MONTHS);

        //通过with修改某些值
        //修改年为2019
        localDateTime = localDateTime.withYear(2020);
        //修改为2022
        localDateTime = localDateTime.with(ChronoField.YEAR, 2022);

        //还可以修改月、日
        //时间计算
        //比如有些时候想知道这个月的最后一天是几号、下个周末是几号，通过提供的时间和日期API可以很快得到答案
        LocalDate localDate = LocalDate.now();
        //LocalDate localDate1 = localDate.with(firstDayOfYear());
    }

    /**
     * 格式化时间
     */
    @Test
    public void testFormart(){
        LocalDate localDate = LocalDate.of(2019, 9, 10);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        //自定义格式化
        DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = localDate.format(dateTimeFormatter);

        //DateTimeFormatter默认提供了多种格式化方式，如果默认提供的不能满足要求，可以通过DateTimeFormatter的ofPattern方法创建自定义格式化方式
        //和SimpleDateFormat相比，DateTimeFormatter是线程安全的
        //解析时间
        LocalDate localDate1 = LocalDate.parse("20190910", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate localDate2 = LocalDate.parse("2019-09-10", DateTimeFormatter.ISO_LOCAL_DATE);

    }
}