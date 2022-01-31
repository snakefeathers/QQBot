package com.snakefeather.filter;

import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import org.jetbrains.annotations.NotNull;

/**
 * 确定对局开始了。
 */
@Beans("GameOperatorFilter")
public class GameOperatorFilter implements ListenerFilter {
    @Override
    public boolean test(@NotNull FilterData data) {

        return true;
    }
}
