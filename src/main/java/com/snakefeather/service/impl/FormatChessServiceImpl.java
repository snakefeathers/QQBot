package com.snakefeather.service.impl;

import com.snakefeather.domain.Operation;
import com.snakefeather.service.FormatChessService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatChessServiceImpl implements FormatChessService {

    private  final String OPERATION = ".*([兵卒车马炮象相士帅将])([一二三四五六七八九123456789])([平进退])([一二三四五六七八九123456789]).*";

    @Override
    public Operation formatString(String userNews) throws IOException {
        Matcher matcher = Pattern.compile(OPERATION).matcher(userNews);
        if (!matcher.find()) throw new IOException("正则匹配失败，消息格式化异常");
        Operation operation = new Operation(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4));
        return operation;
    }


}
