package com.snakefeather.task;


import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.timer.EnableTimeTask;
import love.forte.simbot.timer.Fixed;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Beans
@EnableTimeTask
public class TimeTask {


    /**
     * 5分钟执行一次。
     */
    @Fixed(value = 5, timeUnit = TimeUnit.MINUTES)
    public void task() {
        System.out.println(new Date().toString());
    }
}
