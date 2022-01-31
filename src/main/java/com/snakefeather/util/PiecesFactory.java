package com.snakefeather.util;

import com.snakefeather.chess.ChessBoard;
import com.snakefeather.chess.Pieces;
import com.snakefeather.chess.piece.*;

import java.io.IOException;

public class PiecesFactory {

    private ChessBoard chessBoard;

    public PiecesFactory(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }


    private Pieces abvisorPiece = new Abvisor(chessBoard);     //      士
    private Pieces bishopPiece = new Bishop(chessBoard);     //      相
    private Pieces cannonPiece = new Cannon(chessBoard);     //      炮
    private Pieces kingPiece = new King(chessBoard);     //      将
    private Pieces pawnPiece = new Pawn(chessBoard);     //      兵
    private Pieces knightPiece = new Knight(chessBoard);     //      马
    private Pieces rookPiece = new Rook(chessBoard);     //      车


    /**
     *  根据棋子类型 返回对应的棋子工具类
     * @param chessBoard    棋局
     * @param piecesStr     棋子类型        "kabnrcp KABNRCP"
     * @return  具体棋子工具类
     * @throws IOException
     */
    public Pieces getPiece(ChessBoard chessBoard,char piecesStr) throws IOException {
        switch(piecesStr){
            case 'a':case 'A':
                return abvisorPiece;
            case 'b': case 'B':
                return bishopPiece;
            case 'c':case 'C':
                return cannonPiece;
            case 'k':case 'K':
                return kingPiece;
            case 'p':case 'P':
                return pawnPiece;
            case 'n':case 'N':
                return knightPiece;
            case 'r':case 'R':
                return rookPiece;
            default:
                //  有可能为0
                throw new IOException("棋子类型异常");
        }
    }
}
