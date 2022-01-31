package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 兵，卒
 */
public class Pawn extends Pieces {

    public Pawn(ChessBoard chessBoard) {
        super(chessBoard);
    }

    @Override
    protected Integer getTargetByTran(int startInd, int distance) throws IOException {
        return (startInd / 9) * 9 + (distance - 1);
    }

    @Override
    protected Integer getTargetByAdvance(int startInd, int distance) throws IOException {
        return startInd - distance * 9;
    }

    @Override
    protected Integer getTargetByRetreat(int startInd, int distance) throws IOException {
        return startInd + distance * 9;
    }



    @Override
    public List<Integer> getMoveRange(int startInd) throws Exception {
        super.getMoveRange(startInd);
        //  临时存储移动范围
        ArrayList<Integer> rangeList = new ArrayList<>();
        for (Integer end : moveRange) {
            //  通过规律  筛选出符合的位置
            //  起点终点是否是相同阵营？
            if (!chessBoard.isSameCamp(startInd, end)) {
                rangeList.add(end);
            }
        }
        moveRange = rangeList;
        return moveRange;
    }



    /**
     * 根据阵营选择移动范围。
     *
     * @param ind 判断对象的索引
     * @throws Exception 对象索引无效
     */
    @Override
    protected void selectCamp(int ind) throws Exception {
        List<Integer> range = null;
        if (super.chessBoard.isRed(ind)) {
            //  兵
            range = super.getDownPieces(ind);
            if (range.size() > 0) {
                moveRange.add(range.get(0));
            }
        } else {
            //  卒
            range = super.getTopPieces(ind);
            if (range.size() > 0) {
                moveRange.add(range.get(0));
            }
        }
        range = super.getLeftPieces(ind);
        if (range.size() > 0) {
            moveRange.add(range.get(0));
        }
        range = super.getRightPieces(ind);
        if (range.size() > 0) {
            moveRange.add(range.get(0));
        }
    }

}
