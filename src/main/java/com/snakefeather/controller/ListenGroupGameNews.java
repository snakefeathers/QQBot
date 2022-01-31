package com.snakefeather.controller;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.domain.Operation;
import com.snakefeather.service.FormatChessService;
import com.snakefeather.service.GameManagerService;
import com.snakefeather.service.impl.FormatChessServiceImpl;
import com.snakefeather.service.impl.GameManagerServiceImpl;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.Listener;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupCodeContainer;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.message.events.MsgGet;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import love.forte.simbot.listener.ListenerContext;
import net.mamoe.mirai.data.GroupInfo;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Listener
public class ListenGroupGameNews {

    private static Logger logger = Logger.getLogger(ListenGroupAtNews.class);

    private final String OPERATION = "[兵卒车马炮象相士帅将][一二三四五六七八九123456789][平进退][一二三四五六七八九123456789]";
//    private final String OPERATION = "([兵卒车马炮象相士帅将])([一二三四五六七八九123456789])([平进退])([一二三四五六七八九123456789])";
//    private final String OPERATION = ".*([兵卒车马炮象相士帅将])([一二三四五六七八九123456789])([平进退])([一二三四五六七八九123456789]).*";

    private static final String GROUPID = "737616020";

    //  格式化棋局操作
    private FormatChessService formatChessService = new FormatChessServiceImpl();
    //  棋局管理
    private GameManagerService gameManagerService = new GameManagerServiceImpl();

    /**
     *  收到操作，  处理操作
     * @param msg
     * @param groupMsg
     * @param msgSender
     * @throws Exception
     */
    @OnGroup
    @Filter(groups = GROUPID,value = OPERATION,matchType = MatchType.REGEX_FIND)
    public void getGameNews(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender, ListenerContext context){
        logger.debug( " getGameNews方法执行 " );
        //  获取QQ号
//        System.out.println(context.global(String.valueOf(groupInfo.getGroupCode())));
//        System.out.println(groupInfo.getGroupCode());
        long qqNum = msg.getAccountInfo().getAccountCodeNumber();
        //  获取对应棋局
        ChessBoard chessBoard = gameManagerService.getBoardByUser(qqNum);
        if (chessBoard == null){
            System.out.println("QQ：" + qqNum + "未加入棋局。操作：" + msg.getText());
        }
        //  判定是否是该用户回合？
//        if ()
        //  将数据格式化  转为操作
        Operation operation = null;
        try {
            operation = formatChessService.formatString(msg.getText());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("操作读取异常");return;
        }
        //  将阵营记入操作中
        try {
            operation.setRed(chessBoard.getUserCamp(qqNum));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("程序异常。发送者与棋局不匹配");return;
        }
        //  根据棋局解读操作
        try {
            operation = chessBoard.unscrambleOperation(operation);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("操作解读失败，操作无效或程序异常。");
            //  返回提示信息  根据用户需求  重新修改 或
        }
        //  棋局进行操作
        if (chessBoard.executeOperation(operation)){
            System.out.println("走步有效：" + msg.getText());
        }else {
            System.out.println("走步无效：");
            //  返回提示信息
        }
    }



    @OnGroup
    @Filter(groups = GROUPID, atBot = true, matchType = MatchType.CONTAINS, value = "测试")
    public void getGroupNews(MsgGet msg, GroupMsg groupMsg, MsgSender msgSender) {
        logger.debug( " 测试连接 " );
        String userA = msg.getAccountInfo().getAccountCode();
        msgSender.SENDER.sendGroupMsg(GROUPID, "测试连接222");
        msgSender.SENDER.sendPrivateMsg(631601109,"测试连接222");
    }

}
