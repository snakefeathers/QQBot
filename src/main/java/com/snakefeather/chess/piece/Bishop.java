package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 相
 */
public class Bishop extends Pieces {

    //      相 与 象的固定移动范围
    private final  List<Integer> REDRANGE = new ArrayList<>(Arrays.asList(2, 6, 18, 22, 26, 38, 42));
    private final  List<Integer> BLACKRANGE = new ArrayList<>(Arrays.asList(47, 51, 63, 67, 71, 83, 87));


    public Bishop(ChessBoard chessBoard) {
        super(chessBoard);
    }

    @Override
    protected Integer getTargetByAdvance(int startInd, int distance) throws IOException {
        if ((startInd - 20) % 9 + 1 == distance) {
            return startInd - 20;
        } else {
            return startInd - 16;
        }
    }

    @Override
    protected Integer getTargetByRetreat(int startInd, int distance) throws IOException {
        if ((startInd + 20) % 9 + 1 == distance) {
            return startInd + 20;
        } else {
            return startInd + 16;
        }
    }

    @Override
    public List<Integer> getMoveRange(int startInd) throws Exception {
        super.getMoveRange(startInd);
        //  临时存储移动范围
        List<Integer> rangeList = new ArrayList<>();
        //  临时存储起点和落点的一维距离
        Integer  distance = 0;

        for (Integer end : moveRange){
            distance = Math.abs(startInd - end);
            //  通过规律  筛选出符合的位置
            if (distance == 20 || distance == 16){
                //  通过索引计算，得出规律，相走一步距离为16或20

                //  起点终点是否是相同阵营？
                if (!chessBoard.isSameCamp(startInd, end)) {
                    //  判断别腿，只要不别腿  就可以走。
                    if (isPlugUp(startInd, end)) {
                        rangeList.add(end);
                    }
                }
            }
        }
        moveRange = rangeList;
        return moveRange;
    }

    @Override
    public boolean operationCheck(int startInd, int endInd) throws Exception {
        super.getMoveRange(startInd);
        Integer distance = Math.abs(startInd - endInd);
        //  通过规律  筛选出符合的位置 并且该位置必须位于有效索引范围内  不是相同阵营 不别腿
        if (moveRange.contains(endInd)
                && (!chessBoard.isSameCamp(startInd, endInd))
                && (distance == 20 || distance == 16)
                && (isPlugUp(startInd, endInd))) {
            return true;
        }
        return false;
    }

    /**
     *  用于判断是否被别象腿
     * @param startInd  起点
     * @param endInd    落点
     * @return  是否被别
     * @throws Exception
     */
    private boolean isPlugUp(int startInd,int endInd) throws Exception {

        int pointInd = 0;
        //  根据前进后退来判断别腿点
        if (startInd > endInd){
            //  别腿点规律  之间距离砍半  之后的距离就是别腿点
            pointInd = startInd - (startInd -endInd) / 2;
        }else {
            pointInd = startInd + (startInd -endInd) / 2;
        }
        //  别腿点 不为空
        return chessBoard.isNull(pointInd);
    }


    /**
     *  根据阵营选择移动范围。并判断对象索引有效性
     * @param ind   判断对象的索引
     * @throws Exception    对象索引无效
     */
    @Override
    protected void selectCamp(int ind) throws Exception {
        //  判断是相 还是 象
        if (super.chessBoard.isRed(ind)){
            //  相
            if (Arrays.asList(REDRANGE).contains(ind)){
                moveRange = REDRANGE;
            }else throw new Exception("棋子'相'落子位置异常。");
        }else {
            //  象
            if (Arrays.asList(BLACKRANGE).contains(ind)){
                moveRange = BLACKRANGE;
            }else throw new Exception("棋子'象'落子位置异常。");
        }
    }


}
