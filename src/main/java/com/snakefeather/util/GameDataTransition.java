package com.snakefeather.util;


import com.snakefeather.controller.ListenGroupAtNews;
import love.forte.common.ioc.annotation.Beans;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将“马八进七”  转为步骤
 *
 *  传入步骤。           转为便于计算的步骤。
 *          马   转为类型
 *          七 、1 、 3、 之类的，根据棋盘转为一位数组坐标
 *          进 退 平       转为 int 类型  指明状态码
 *
 */
@Beans
public class GameDataTransition {

    private static Logger logger = Logger.getLogger(ListenGroupAtNews.class);

    private  final String OPERATION = ".*([兵卒车马炮象相士帅将])([一二三四五六七八九123456789])([平进退])([一二三四五六七八九123456789]).*";


    /**
     *  首先：将用户发送的消息过滤，提取。
     *  正则匹配：消息再提取，读取需要的数据。去除艾特等多余信息。
     *  数据转换：将数据转为机器容易识别的简化数据。
     *  解读数据：按照象棋规则解读数据。
     *
     */

    /**
     *      数据转化
     * @return    返回数组类型    数组元素分别为：棋子类型 、 起点数 、 棋子行为 、 终点数 、 中间转换有无失败？
     */
    public int[] gameDataStrToArray(String s) throws Exception {
        logger.debug( " debug " );

        //      暂时只将字符串读取出来，整理为有效数据
        //      存储棋子字符串         值范围：[兵卒车马炮象相士帅将]
        char pieceType = ' ';
        //      存储起点值
        int startPoint = 0;
        //      存储操作状态          值范围：进、退、平
        int operationStatus = 0;
        //      存储终点值
        int endPoint = 0;

        Pattern pattern = Pattern.compile(OPERATION);
        Matcher matcher = null;
        matcher = pattern.matcher(s);
        if (matcher.find()){
//            pieceType = this.typeStrToNum(matcher.group(1));
            startPoint = this.distanceStrToNum(matcher.group(2));
            operationStatus = this.operationTypeStrToNum(matcher.group(3));
            endPoint = this.distanceStrToNum(matcher.group(4));
            //      防止异常
            if (pieceType < 0 || startPoint < 0 || operationStatus < 0 || endPoint < 0){
                throw new Exception("正则表达式取值异常");
            }
        }else {
            throw new Exception("正则表达式匹配异常。");
        }


        return new int[]{pieceType, startPoint, operationStatus, endPoint};
    }





    //#region  读取数据信息 棋子类型、行进距离、操作类型、行进距离
    /**
     *       将棋子类型 字符 转为字符
     * @param str   棋子类型：兵卒车马炮象相士帅将
     * @return  对应下标
     */
    private int typeStrToNum(boolean isRed,String str){
        char[] piecesChar = {'兵','车','马','相','士','帅','将'};
//        [兵卒车马炮象相士帅将]
//        char[] chars = {'兵','卒','车','马','炮','象','相','士','帅','将',};
        char c = str.charAt(0);
        String s = "兵卒车马炮象相士帅将";
        return s.indexOf(c);
    }

    /**
     *      将操作距离 字符 转为数字
     * @param str   操作距离："一二三四五六七八九""123456789";
     * @return  对应下标
     */
    private int distanceStrToNum(String str){
//        一二三四五六七八九123456789
        char c = str.charAt(0);
        String zh = "一二三四五六七八九";
        String num = "123456789";

        int flag = zh.indexOf(c);
        if (flag >= 0){
            return flag +1;
        }
        if ((flag = num.indexOf(c) ) >= 0 ){
            return num.indexOf(c) + 1;
        }
        return -1;
//        return zh.indexOf(c)>=0?zh.indexOf(c)+1:num.indexOf(c) >= 0?num.indexOf(c) + 1:-1;
    }

    /**
     *       将操作方式 字符 转为数字
     * @param str 操作类型："平进退"
     * @return  对应下标
     */
    private int operationTypeStrToNum(String str){
        char c = str.charAt(0);
        String s = "平进退";
        return s.indexOf(c);
    }
    //#endregion
}
