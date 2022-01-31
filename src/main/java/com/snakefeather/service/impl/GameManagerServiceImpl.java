package com.snakefeather.service.impl;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.service.GameManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManagerServiceImpl implements GameManagerService {

    /**
     *  存储棋局  管理棋局
     */
    List<ChessBoard> boardList = new ArrayList<>();


    @Override
    public ChessBoard getBoardByUser(long qqNum) {
        for (ChessBoard chessBoard : boardList){
            if (chessBoard.getRed() == qqNum || chessBoard.getBlack() == qqNum){
                return chessBoard;
            }
        }
        return null;
    }

    @Override
    public boolean challenge(long qqNum) {

        return false;
    }


}
