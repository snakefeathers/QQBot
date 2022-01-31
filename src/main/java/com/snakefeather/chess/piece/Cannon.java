package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.List;

/**
 * 炮
 */
public class Cannon extends Pieces {

    public Cannon(ChessBoard chessBoard) {
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
        return super.getMoveRange(startInd);
    }

    @Override
    public boolean operationCheck(int startInd, int endInd) throws Exception {
        return super.operationCheck(startInd, endInd);
    }

    /**
     *  根据阵营选择移动范围。
     * @param ind   判断对象的索引
     * @throws Exception    对象索引无效
     */
    @Override
    protected void selectCamp(int ind) throws Exception {
        //  添加上下左右的移动有效范围
        getValidRange(super.getTopPieces(ind));
        getValidRange(super.getDownPieces(ind));
        getValidRange(super.getLeftPieces(ind));
        getValidRange(super.getRightPieces(ind));
    }

    //  筛选出数组中不是同一阵营的索引。  短路操作，检测出是同一阵营之后，就不再执行。
    private void getValidRange(List<Integer> range) throws Exception {
        for (int i = 0; i < range.size(); i++) {

            //  如果不为空 就判断，是否可以吃  能吃，就包括，不能吃就不包括。  之后结束。
            if (super.chessBoard.isNull(range.get(i))) {
                //  为空 直接添加 并开始下次循环
                moveRange.add(range.get(i));
            } else if (!super.chessBoard.isSameCamp(startInd,range.get(i))){
                //  能吃，添加，并结束循环
                moveRange.add(range.get(i));
                break;
            }else break;        //  自己阵营的棋子 不能吃，结束循环
        }
    }


}
