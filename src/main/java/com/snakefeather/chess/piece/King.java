package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 将，帅
 */
public class King extends Pieces {

    //      帅 与 将的固定移动范围
    private final List<Integer> REDRANGE = new ArrayList<>(Arrays.asList(3, 4, 5,12, 13, 14,21, 22, 23));
    private final  List<Integer> BLACKRANGE = new ArrayList<>(Arrays.asList(66, 67, 68,75, 76, 77,84, 85, 86));

    public King(ChessBoard chessBoard) {
        super(chessBoard);
    }

    @Override
    protected Integer getTargetByTran(int startInd, int distance) throws IOException {
        return (startInd / 9) * 9 + (distance - 1);
    }

    @Override
    protected Integer getTargetByAdvance(int startInd, int distance) throws IOException {
        return startInd - (distance * 9);
    }

    @Override
    protected Integer getTargetByRetreat(int startInd, int distance) throws IOException {
        return startInd + (distance * 9);
    }

    @Override
    public List<Integer> getMoveRange(int startInd) throws Exception {
        super.getMoveRange(startInd);
        //  临时存储移动范围
        ArrayList<Integer> rangeList = new ArrayList<>();
        //  临时存储起点和落点的一维距离
        Integer  distance = 0;

        for (Integer end : moveRange){
            distance = Math.abs(startInd - end);
            //  通过规律  筛选出符合的位置
            if (distance == 1 || distance == 9){
                //  通过索引计算，得出规律，相走一步距离为1或9
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
                && (distance == 1 || distance == 9)) {
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
        //  判断是帅 还是 将
        if (super.chessBoard.isRed(ind)){
            //  帅
            if (Arrays.asList(REDRANGE).contains(ind)){
                this.moveRange = REDRANGE;
            }else throw new Exception("棋子'帅'落子位置异常。");
        }else {
            //  将
            if (Arrays.asList(BLACKRANGE).contains(ind)){
                this.moveRange = BLACKRANGE;
            }else throw new Exception("棋子'将'落子位置异常。");
        }
    }




}
