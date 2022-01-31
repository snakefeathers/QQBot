package com.snakefeather.chess.piece;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 车
 */
public class Rook extends Pieces {

    //  根据阵营来临时存储移动范围
    private ArrayList<Integer> moveRange = null;

    public Rook(ChessBoard chessBoard) {
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
                //  为空 之间添加
                moveRange.add(range.get(i));
            } else {
                //  有了炮架子  往后走，判断接下来第一颗棋子是否是敌方棋子  是的话，添加
                int j = i +1;
                while (j < range.size()){
                    //  找到接下来第一颗不为空的棋子
                    if (!super.chessBoard.isNull(range.get(j))){
                        if (!super.chessBoard.isSameCamp(startInd,range.get(j))){
                            moveRange.add(range.get(j));
                        }
                    }
                    j++;
                }
                break;
            }
        }
    }
}
