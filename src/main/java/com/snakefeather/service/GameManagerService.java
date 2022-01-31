package com.snakefeather.service;

import com.snakefeather.chess.ChessBoard;

public interface GameManagerService {

    /**
     *  根据发送者，获取发送者正在参与的棋局   传入QQ号
     * @param qqNum     qq号
     * @return      根据qq号返回这个棋局
     */
    public ChessBoard getBoardByUser(long qqNum);

    /**
     *      用户进行约战
     * @return
     */
    public boolean challenge(long qqNum);

    /**
     *    用户初始化棋局
     * @param qqNum
     * @return  初始化成功了么？
     */
//    public boolean initBoard(long qqNum);

}
