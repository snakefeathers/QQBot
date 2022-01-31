package com.snakefeather.filter;


import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.component.mirai.message.event.MiraiGroupMsg;
import love.forte.simbot.filter.FilterData;
import love.forte.simbot.filter.ListenerFilter;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  过滤器，将制定群消息过滤出来。
 */

@Beans("GroupFilter")
public class GroupFilter implements ListenerFilter {
    private static final long GROUPID = 737616020;

    @Override
    public boolean test(@NotNull FilterData data) {
        MsgGet msg = data.getMsgGet();          //      love.forte.simbot.component.mirai.message.event.MiraiGroupMsg;
        MiraiGroupMsg miraiGroupMsg = (MiraiGroupMsg) msg;
        GroupMessageEvent groupMessageEvent = miraiGroupMsg.getEvent();
        long groupId = miraiGroupMsg.getEvent().getGroup().getId();     //          获取QQ群号

//        miraiGroupMsg.getEvent().getSender().sendMessage(" 过滤器");

        if (GROUPID == groupId){
            return true;
        }else {
            return false;
        }


//        MsgGet msg = data.getMsgGet();          //      love.forte.simbot.component.mirai.message.event.MiraiGroupMsg;
//        MiraiGroupMsg miraiGroupMsg = (MiraiGroupMsg) msg;
//        GroupMessageEvent groupMessageEvent = miraiGroupMsg.getEvent();
//        long groupId = groupMessageEvent.getGroup().getId();
//
//        net.mamoe.mirai.contact.Group q = groupMessageEvent.getGroup();
//        String id = miraiGroupMsg.getGroupInfo().getGroupCode();
//        System.out.println("getGroup---:" + miraiGroupMsg.getEvent().getGroup());
//        System.out.println("getGroupInfo---:" + miraiGroupMsg.getGroupInfo());
//        System.out.println("---:" );
//        String groupNum = msg.getOriginalData();
//        ((MiraiGroupMsg) msg).getEvent().getGroup();
    }
}
