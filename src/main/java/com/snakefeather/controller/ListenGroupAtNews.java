package com.snakefeather.controller;


import love.forte.simbot.annotation.*;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.GroupCodeContainer;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.component.mirai.message.event.MiraiGroupMsg;
import love.forte.simbot.filter.MatchType;


import love.forte.simbot.listener.ListenerContext;
import org.apache.log4j.Logger;


/**
 * 准备开始时，就发送开始消息。   格式是  @机器人  + 准备开始
 *
 * 对方    @机器人  应战
 *
 *
 */
@Listener
public class ListenGroupAtNews {


    private static Logger logger = Logger.getLogger(ListenGroupAtNews.class);

    private static final String GROUPID = "737616020";

    private boolean playing = false;
    private boolean readly = false;

    private String userA = "";          //      存储发起方QQ号
    private String userB = "";





    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.CONTAINS, value = "开始")
    public void getGroupNews(MsgGet msg, MsgSender msgSender, ListenerContext context) {
//    public void getGroupNews(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender, ListenerContext context, GroupInfo groupInfo) {
        logger.debug( " debug " );
        MiraiGroupMsg miraiGroupMsg = (MiraiGroupMsg)msg;
        String groupId = String.valueOf( miraiGroupMsg.getEvent().getGroup().getId());
        context.global(groupId,msg.getAccountInfo().getAccountCodeNumber());
//        GroupCodeContainer groupCodeContainer = miraiGroupMsg.

        msgSender.SENDER.sendGroupMsg(GROUPID, "收到对局确认，请应战方应战。");
        msgSender.SENDER.sendGroupMsg(GROUPID, "应战方式：" +
                "艾特本人\t应战 \n" +
                "艾特本人\t不应战   \n " +
                "（1分钟内不应战视为对方暂忙。对局取消）");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接");
    }







    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.REGEX_FIND, value = ".*[^不]{1}应战.*")
    public void getGroupNews2(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender) {
        logger.debug( " debug " );
        System.out.println("应战方法：'" +  msg.getText() + "'");

        userB = msg.getAccountInfo().getAccountCode();
        msgSender.SENDER.sendGroupMsg(GROUPID, "对局建立，请决定先后手。  \n" +
                "双方中一方艾特我 之后跟“红” 或者 “ 黑” 即可选定阵营。");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接");
    }

    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.REGEX_FIND, value = ".*不应战.*")
    public void getGroupNews4(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender) {
        logger.debug( " debug " );
        System.out.println("不应战方法：'" +  msg.getText() + "'");
        msgSender.SENDER.sendGroupMsg(GROUPID, "对局取消。");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接");
    }


    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.CONTAINS, value = "红")
    public void getGroupNews5(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender) {
        logger.debug( " debug " );
        String userId = msg.getAccountInfo().getAccountCode();
        msgSender.SENDER.sendGroupMsg(GROUPID, userId + "选定了红方。\n" +
                                            "开始走步。   \n" +
                                            "对局中需要帮助可以艾特我。");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接");
    }

    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.CONTAINS, value = "认输")
    public void getGroupNews3(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender) {
        logger.debug( " debug " );
        msgSender.SENDER.sendGroupMsg(GROUPID, "认输");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接");
        System.out.println("-----------------------------------------------------------");
    }


    //  拦截器   当对局开始之后，才有效



    //#region

    //      监听群消息     群号为 737616020
//    @OnGroup
//    @Filters(customFilter = "GroupFilter" ,groups = "737616020")
//    public void getGroupNews(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender){
//
//        String groupText = msg.getText();
//        //          收到开始信号   等待确认
//        readly = true;
//        userA = msg.getAccountInfo().getAccountCodeNumber();
//
//        msgSender.SENDER.sendGroupMsg(737616020,"收到，收到。");
//
//
//        System.out.println("通过事件过滤。。。");
//    }





//    @OnPrivate
//    public void fudu(PrivateMsg privateMsg,Sender sender){
//        sender.sendPrivateMsg(privateMsg.getAccountInfo().getAccountCode(),"收到，收到。");
//    }
//
//    @OnGroup
//    public void outputGroupNews(MsgGet msg ){
//        MiraiGroupMsg miraiGroupMsg = (MiraiGroupMsg) msg;
//        GroupMessageEvent groupMessageEvent = miraiGroupMsg.getEvent();
//        long groupId = miraiGroupMsg.getEvent().getGroup().getId();     //          获取QQ群号
//        System.out.println("-----------------------------------------------------------");
//        System.out.println("QQ群 ：" + groupId + "消息： " + msg.getText());
//        System.out.println("-----------------------------------------------------------");
//    }



//    public ReplyAble getGameNews(MsgGet msg ){
////        Sender
//        System.out.println("getId---:" + msg.getId());
//        System.out.println("getText---:" + msg.getText());
////        System.out.println(msg.getAccountInfo());
//        AccountInfo miraiMemberAccountInfo = msg.getAccountInfo();
//        System.out.println("miraiMemberAccountInfo---:" + miraiMemberAccountInfo);
////        MiraiMemberAccountInfo(memberId=631601109, member=NormalMember(631601109))
//        System.out.println("getOriginalData---:" + msg.getOriginalData());
////        GroupMessageEvent(group=737616020,
////        senderName=孤影随行, sender=631601109, permission=OWNER, message=[mirai:source:[11487],[185567011]]。)
//        System.out.println("toString---:" + msg.toString());
////        MiraiEvent(id=631601109.11487.185567011,
////        event=GroupMessageEvent(group=737616020,
////        senderName=孤影随行, sender=631601109,
////        permission=OWNER,
////        message=[mirai:source:[11487],[185567011]]。))
//        {
//            System.out.println("getClass---:" + msg.getOriginalData().getClass());
//        }
////        System.out.println("GroupMessageEvent---:" + messageEvent);
//
//        MiraiGroupMsg g1 = (MiraiGroupMsg)msg;
//        GroupMessageEvent groupMessageEvent = g1.getEvent();
//
//        return Reply.reply("盲棋辅助机器人（In test）", true);
//    }
//#endregion

}
