package com.snakefeather.chess;

import com.snakefeather.chess.ChessBoard;

import java.io.IOException;
import java.util.*;

/**
 * 规范各类棋子
 */
public  class Pieces {
    //  存储棋局  构造器中初始化
    protected ChessBoard chessBoard;
    //  临时存储棋子索引#
    protected int startInd = -1;
    //  临时存储棋局
    protected String boardStr = "";
    //  临时存储计算结果
    protected List<Integer> moveRange = null;

    //  棋子必须与棋局绑定
    public Pieces(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }


    //#region  获知起点 终点
    /**
     *  根据起点 和 操作类型  获知此棋子的终点  （终点有效性未验证，只是计算，得出终点索引）
     * @param startInd  起点
     * @param distance  操作类型
     * @return  计算终点
     */
    public final Integer getTarget(int startInd,int operationStatus,int distance) throws IOException {
        //  根据操作类型  选方法
        switch (operationStatus){
            case 0:
                return getTargetByTran(startInd,distance);
            case 1:
                return getTargetByAdvance(startInd,distance);
            case 2:
                return getTargetByRetreat(startInd,distance);
            default:
                throw  new IOException("无效的操作类型");
        }
    }

    /**
     *      获知终点的方法  根据起点  终点   操作状态是平
     * @param startInd
     * @param distance
     * @return
     * @throws IOException
     */
    protected Integer getTargetByTran(int startInd,int distance) throws IOException {
        throw new IOException("该棋子不能平");
    }

    /**
     *      获知终点的方法  根据起点  终点   操作状态是进
     * @param startInd
     * @param distance
     * @return
     * @throws IOException
     */
    protected Integer getTargetByAdvance(int startInd,int distance) throws IOException {
        throw new IOException("该棋子不能进");
    }

    /**
     *     获知终点的方法  根据起点  终点   操作状态是退
     * @param startInd
     * @param distance
     * @return
     * @throws IOException
     */
    protected Integer getTargetByRetreat(int startInd,int distance) throws IOException {
        throw new IOException("该棋子不能退");
    }
    //#endregion


    /**
     * 获取棋子的移动范围
     * @param startInd 起点
     * @return  移动范围
     * @throws Exception 索引异常
     */
    public  List<Integer> getMoveRange(int startInd) throws Exception {
        selectCamp(startInd);
        return moveRange;
    }

    /**
     * 验证走步有效性  获取可移动范围，落点在范围中，过程中无报错即是有效
     * @param startInd  起点
     * @param endInd    落点
     * @return  是否有效
     * @throws Exception    索引异常
     */
    public boolean operationCheck(int startInd, int endInd) throws Exception {
        moveRange = this.getMoveRange(startInd);
        return moveRange.contains(endInd);
    }


    //#region       获取棋子上、下、左、右、的移动范围
    /**
     *  获取棋子向上的移动范围
     * @param startInd
     * @return
     */
    public final List<Integer> getTopPieces(int startInd) {
        List<Integer> range = new ArrayList<>();
        Integer endInd = startInd - 9;
        while (endInd >= 0) {
            range.add(endInd);
            endInd += 9;
        }
        return range;
    }

    /**
     *  获取棋子向下的移动范围
     * @param startInd
     * @return
     */
    public final List<Integer> getDownPieces(int startInd) {
        List<Integer> range = new ArrayList<>();
        Integer endInd = startInd + 9;
        while (endInd >= 89) {
            range.add(endInd);
            endInd -= 9;
        }
        return range;
    }

    /**
     *  获取棋子向左的移动范围
     * @param startInd
     * @return
     */
    public final List<Integer> getLeftPieces(int startInd) {
        List<Integer> range = new ArrayList<>();
        Integer endInd = startInd - 1;
        while (endInd/9 == startInd /9) {
            range.add(endInd);
            endInd -= 1;
        }
        return range;
    }
    /**
     *  获取棋子向右的移动范围
     * @param startInd
     * @return
     */
    public final List<Integer> getRightPieces(int startInd) {
        List<Integer> range = new ArrayList<>();
        Integer endInd = startInd + 1;
        while (endInd/9 == startInd /9) {
            range.add(endInd);
            endInd += 1;
        }
        return range;
    }
    //#endregion

    /**
     *  根据阵营选择移动范围。  士、将、相使用。
     * @param ind   判断对象的索引
     * @throws Exception    对象索引无效
     */
    protected void selectCamp(int ind) throws Exception {}
}
