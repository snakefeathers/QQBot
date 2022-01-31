package com.snakefeather;


import love.forte.simbot.annotation.SimbotApplication;
import love.forte.simbot.core.SimbotApp;
import love.forte.simbot.core.SimbotContext;

@SimbotApplication
public class Init {
    public static void main(String[] args) {

        final SimbotContext context = SimbotApp.run(Init.class, args);
    }
}
