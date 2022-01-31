package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.*;

/**
 * 马
 */
public class Knight extends Pieces {

    //  走一步的一维距离
    private final  List<Integer> missDistance = new ArrayList<>(Arrays.asList(-19, -17, -11, -7, 7, 11, 17, 19));

    public Knight(ChessBoard chessBoard) {
        super(chessBoard);
    }

    @Override
    public List<Integer> getMoveRange(int startInd) throws Exception {
        int endInd = 0;
        moveRange = new ArrayList<>();
        for (int x : missDistance) {
            endInd = startInd + x;
            //  起点终点是否是相同阵营？  马没有别腿
            if (!chessBoard.isSameCamp(startInd, endInd)
                    && isPlugUp(startInd,endInd))
                moveRange.add(x);
        }
        moveRange.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2)return o1;
                return o2;
            }
        });
        return moveRange;
    }

    @Override
    protected Integer getTargetByAdvance(int startInd, int distance) throws IOException {
        switch ((startInd % 9) - distance) {
            case 2:
                return startInd - 7;
            case 1:
                return startInd - 17;
            case -1:
                return startInd - 19;
            case -2:
                return startInd - 11;
            default:
                return -1;
        }
    }

    @Override
    protected Integer getTargetByRetreat(int startInd, int distance) throws IOException {
        switch ((startInd % 9) - distance) {
            case 2:
                return startInd + 11;
            case 1:
                return startInd + 19;
            case -1:
                return startInd + 17;
            case -2:
                return startInd + 7;
            default:
                return -1;
        }
    }

    @Override
    public boolean operationCheck(int startInd, int endInd) throws Exception {
        Integer distance = startInd - endInd;
        //  两点间的一维距离属于有效距离   目标点阵营允许可达   不别腿
        if (missDistance.contains(distance)
                && (!chessBoard.isSameCamp(startInd, endInd))
                && isPlugUp(startInd,endInd)) {
            return true;
        }
        return false;
    }

    /**
     * 用于校验是否被别马腿   真表示没有被别，通过。
     * @param startInd  起点位置
     * @param endInd    落点位置
     * @return  真表示没有被别
     * @throws Exception
     */
    private boolean isPlugUp(int startInd,int endInd) throws Exception {
        switch (endInd - startInd) {
            case 19:
            case 17:
                // 判定是否别腿
                return chessBoard.isNull(startInd - 9);
            case 11:
            case -7:
                return chessBoard.isNull(startInd - 1);
            case 7:
            case -11:
                return chessBoard.isNull(startInd + 1);
            case -19:
            case -17:
                return chessBoard.isNull(startInd + 9);
            default:
                return false;
        }
    }


}
