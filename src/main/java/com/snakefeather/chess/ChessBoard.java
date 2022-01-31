package com.snakefeather.chess;

import com.snakefeather.domain.Operation;
import com.snakefeather.util.PiecesFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  用于存储棋局战况
 */
public class ChessBoard {

    //  存储默认棋局
    private final static String DEFAULT = "rnbakabnr0000000000c00000c0p0p0p0p0p000000000000000000P0P0P0P0P0C00000C0000000000RNBAKABNR";
    // "rnbakabnr", "000000000", "0c00000c0", "p0p0p0p0p", "000000000", "000000000", "P0P0P0P0P", "0C00000C0", "000000000", "RNBAKABNR

    //  存储游戏中棋局
    private String boardStr = DEFAULT;
    //  存储有效中初始棋局
    private String initialBoard = DEFAULT;
    //  存储双方的QQ号        红方 和 黑方
    private long red = 0l;
    private long black = 0l;
    //  存储当前是谁的回合
    private long user = red;
    //  存储创建时间  时间戳
    private long initialDate = 0l;
    //  存储对局状况  平、红胜、黑胜、红断线、黑断线
    private int outcome = -1;
    //  存储操作
    List<Operation> operations = new ArrayList<>();
    //  对局时长
    private long duration = -1;
//    //  红方步时      暂不使用，后期添加
//    private long redOperaTime = 0;
//    //  黑方步时
//    private long blackOperaTime = 0;
    //  棋子的工厂类  用于根据棋子进行相应的走步判定
    private PiecesFactory piecesFactory = new PiecesFactory(this);




//    传入棋局，





    /**
     *  根据发送者qq号，获知发送者阵营
     * @param qqNum
     * @return
     * @throws IOException
     */
    public boolean getUserCamp(long qqNum) throws IOException {
        if (qqNum == red)return true;
        if (qqNum == black)return false;
        throw new IOException("用户与棋局匹配异常");
    }

    //#region   根据 *现有棋局  *传来的操作  转换为详细具体的操作
    /**
     *  根据棋局 结合用户的操作  转为精确的操作
     * @param operation
     * @return
     */
    public Operation unscrambleOperation(Operation operation) throws IOException {
//          转换 获取该棋局中  该类型棋子的所有位置
        List<Integer> pieceList = new ArrayList<>();
        for (int i = 0; this.boardStr.length() > i; i++) {
            if (operation.getPieceType() == boardStr.charAt(i)) {
                pieceList.add(i);
            }
        }
        if (pieceList.size() <= 0) throw new IOException("不存在该类型的棋子");

//            根据行、列 获取棋子 起点的索引

        List<Integer> indByLine = getIndByTranslation(pieceList, operation.getStartPoint(),operation.getRed());
        List<Integer> indByColumn = getIndByTranslation(pieceList, operation.getStartPoint(),operation.getRed());
        if (indByLine.size() == 1 || indByColumn.size() == 1) {
            //  校验，行和列中   必须有一个
            if (indByLine.size() == 1 && indByColumn.size() == 1) {
                //  校验，   全有的情况
                if (operation.getOperationStatus() == 0) {
                    //  根据操作状态去获取
                    //  平   此种情况  后面的数字优先代表列
                    operation.setStartPoint(indByColumn.get(0));
                } else {
                    //  进 退  此种情况  后面的数字优先代表行
                    operation.setStartPoint(indByLine.get(0));
                }
            }
            if (indByLine.size() == 1) {
                //  如果是行有一个
                operation.setStartPoint(indByLine.get(0));
            } else {
                //  否则就是列有一个
                operation.setStartPoint(indByColumn.get(0));
            }
        } else {
            throw new IOException("无法找到该棋子的起始索引");
        }

//          转换 获取终点位置
        Pieces pieces = piecesFactory.getPiece(this,operation.getPieceType());
        int endInd = pieces.getTarget(operation.getStartPoint(),operation.getOperationStatus(),operation.getOperationStatus());
        operation.setEndPoint(endInd);
        return operation;
    }

    /**
     *  筛选出已有棋子中，位于指定行的棋子
     * @param pieceList 已有棋子的索引集合
     * @param startNum 指定行
     * @return  位于指定行的棋子集合
     */
    private List<Integer> getIndByTranslation(List<Integer> pieceList,int startNum,boolean camp){
        //  红方阵营需要对称转换，转为黑方视角的行。
        if (camp){
            startNum = 10 - startNum + 1;
        }
        List<Integer> indList = new ArrayList<>();
//                进 或 退     startInd可能是行号
        for (Integer pieceInd : pieceList){
            if ((pieceInd / 9 + 1) == startNum){
                indList.add(pieceInd);
            }
        }
        return indList;
    }
    /**
     *   筛选出已有棋子中，位于指定列的棋子
     * @param pieceList 已有棋子的集合
     * @param startNum  指定列
     * @return  位于指定列的棋子集合
     */
    private List<Integer> getIndByVertical(List<Integer> pieceList,int startNum,boolean camp){
        //  红方阵营需要对称转换，转为黑方视角的列
        if (camp){
            startNum =  9 - startNum + 1;
        }
        List<Integer> indList = new ArrayList<>();
//                进 或 退     startInd可能是列号
        for (Integer pieceInd : pieceList){
            if ((pieceInd % 9 + 1) == startNum){
                indList.add(pieceInd);
            }
        }
        return indList;
    }
    //#endregion


    /**
     *  验证棋子走步有效性
     * @param operation 一步处理后的操作
     * @return  是否合理
     */
    public  boolean operationCheck(Operation operation){
        return false;
    };

    /**
     *      执行走步操作
     * @param operation     操作的相关信息
     * @return  操作是否成功
     */
    public boolean executeOperation(Operation operation){

        //  检验有效性
        if (this.operationCheck(operation)){
        }
        return  false;
    }




    //#region   阵营判定
    //          判断是红方么？     真表示是红方
    public boolean isRed(int ind) throws Exception {
        this.isPass(ind);
        //      大写字母表示红方
        return Character.isUpperCase(this.boardStr.charAt(ind));
    }
    //          判断是黑方么？     真表示是红方
    public boolean isBlack(int ind) throws Exception {
        this.isPass(ind);
        //      小写字母表示黑方
        return Character.isLowerCase(this.boardStr.charAt(ind));
    }
    //          判断是空么？      真表示为空
    public boolean isNull(int ind) throws Exception {
        this.isPass(ind);
        //  数字表示为空
        return Character.isDigit(this.boardStr.charAt(ind));
    }
    //          判断是相同阵营么？
    public boolean isSameCamp(int startInd , int endInd) throws Exception {
        this.isPass(startInd);
        this.isPass(endInd);
        //      起点必定有阵营
        if (Character.isUpperCase(this.boardStr.charAt(startInd))){
            //  终点是红方

            //  如果终点是红方，则表示是相同阵营
            return Character.isUpperCase(this.boardStr.charAt(endInd));
        }else {
            //  起点是黑方

            //  如果终点是黑方，则表示是相同阵营
            return Character.isLowerCase(this.boardStr.charAt(endInd));
        }
    }
    //#endregion


    /**
     *    检查索引符合范围    正有效就不处理，无效就抛出异常
     * @param startInd  索引
     * @throws Exception
     */
    private void isPass(int startInd) throws Exception {
        if (startInd < 0 || startInd > 90)throw new Exception("阵营判断方法数组下标越界。");
    }





    //#region   get和set方法


    public String getInitialBoard() {
        return initialBoard;
    }

    public void setInitialBoard(String initialBoard) {
        this.initialBoard = initialBoard;
    }

    public long getRed() {
        return red;
    }

    public void setRed(long red) {
        this.red = red;
    }

    public long getBlack() {
        return black;
    }

    public void setBlack(long black) {
        this.black = black;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getBoardStr() {
        return boardStr;
    }

    public void setBoardStr(String boardStr) {
        this.boardStr = boardStr;
    }


    //#endregion


}
