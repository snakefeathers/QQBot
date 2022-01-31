package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  士
 */
public class Abvisor extends Pieces {


    //      士的固定移动范围
    private final  List<Integer> REDRANGE = new ArrayList<>(Arrays.asList(3, 5, 13, 21, 23));
    private final  List<Integer> BLACKRANGE = new ArrayList<>(Arrays.asList(66, 68, 76, 84, 86));

    public Abvisor(ChessBoard chessBoard) {
        super(chessBoard);
    }

    @Override
    protected Integer getTargetByAdvance(int startInd, int distance) throws IOException {
        if ((startInd - 10) % 9 + 1 == distance) {
            return startInd - 10;
        } else {
            return startInd - 8;
        }
    }

    @Override
    protected Integer getTargetByRetreat(int startInd, int distance) throws IOException {
        if ((startInd + 10) % 9 + 1 == distance) {
            return startInd + 10;
        } else {
            return startInd + 8;
        }
    }

    @Override
    public List<Integer> getMoveRange(int startInd) throws Exception {
        selectCamp(startInd);
        //  临时存储移动范围
        List<Integer> rangeList = new ArrayList<>();
        //  临时存储起点和落点的一维距离
        Integer  distance = 0;

        for (Integer end : moveRange){
            distance = Math.abs(startInd - end);
            //  通过规律  筛选出符合的位置
            if (distance == 10 || distance == 8){
                //  通过索引计算，得出规律，相走一步距离为10或8
                //  起点终点是否是相同阵营？
                if (!chessBoard.isSameCamp(startInd, end)) {
                        rangeList.add(end);
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
        //  通过规律  筛选出符合的位置 并且该位置必须位于有效索引范围内  不是相同阵营
        if (moveRange.contains(endInd)
                && (!chessBoard.isSameCamp(startInd, endInd))
                && (distance == 10 || distance == 8)) {
            return true;
        }
        return false;
    }

    /**
     *  根据阵营选择移动范围。并判断对象索引有效性
     * @param ind   判断对象的索引
     * @throws Exception    对象索引无效
     */
    @Override
    protected void selectCamp(int ind) throws Exception {
        //  判断是红方还是黑方
        if (super.chessBoard.isRed(ind)){
            //  红方
            if (Arrays.asList(REDRANGE).contains(ind)){
                this.moveRange = REDRANGE;
            }else throw new Exception("红'士'落子位置异常。");
        }else {
            //  黑方
            if (Arrays.asList(BLACKRANGE).contains(ind)){
                this.moveRange = BLACKRANGE;
            }else throw new Exception("黑'士'落子位置异常。");
        }
    }

    
}
