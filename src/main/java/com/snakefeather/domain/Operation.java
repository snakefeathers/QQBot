package com.snakefeather.domain;

import java.io.IOException;

public class Operation {
    //      存储棋子字符         值范围：[兵卒车马炮象相士帅将]
    private  char pieceType = ' ';
    //      存储起点值
    private  int startPoint = 0;
    //      存储操作状态          值范围：进、退、平
    private  int operationStatus = 0;
    //      存储终点值
    private  int endPoint = 0;

    //      存储终点棋子类型
    private  char endPieceType = ' ';
    //      存储这步操作的所属阵营
    private boolean isRed = true;


    public Operation(String pieceType, String startPoint, String operationStatus, String endPoint) throws IOException {
        this.setPieceType(pieceType);
        this.setStartPoint(startPoint);
        this.setOperationStatus(operationStatus);
        this.setEndPoint(endPoint);
    }

    //#region   将获取到的字符串读取为有效数据

    /**
     *   设置棋子的类型
     * @param pieceType 外部正则取出的棋子数据
     * @throws IOException
     */
    private void setPieceType(String pieceType) throws IOException {
        switch (pieceType.charAt(0)){
            case '士':
                this.pieceType = 'a';break;
            case '相': case '象':
                this.pieceType = 'b';break;
            case '炮':
                this.pieceType = 'c';break;
            case '将':case '帅':
                this.pieceType = 'k';break;
            case '卒':case '兵':
                this.pieceType = 'p';break;
            case '马':
                this.pieceType = 'n';break;
            case '车':
                this.pieceType = 'r';break;
            default:
                throw new IOException("棋子类型错误，操作建立异常。");
        }
    }

    /**
     *  设置棋子的索引    二维索引的行号或列号，不是一维索引，需要后期转换
     * @param startPoint    外部正则取出的棋子起点数据
     * @throws IOException
     */
    private void setStartPoint(String startPoint) throws IOException {
        //        一二三四五六七八九123456789
        char startChar = startPoint.charAt(0);
        String zh = "一二三四五六七八九";
        String num = "123456789";
        if (zh.indexOf(startChar) >= 0){
            this.startPoint = zh.indexOf(startChar) + 1;
            return;
        }
        if (num.indexOf(startChar) >= 0){
            this.startPoint = num.indexOf(startChar) + 1;
            return;
        }
        throw new IOException("棋子索引错误，操作建立异常。");
    }

    /**
     *  设置棋子的操作类型
     * @param operationStatus    外部正则取出的棋子操作类型数据
     * @throws IOException
     */
    private void setOperationStatus(String operationStatus) throws IOException {
        String statusList = "平进退";
        if (operationStatus.charAt(0) >= 0){
            this.operationStatus = statusList.indexOf(operationStatus.charAt(0));
            return;
        }
        throw new IOException("棋子操作类型错误，操作建立异常。");
    }

    /**
     *  设置棋子的终点索引
     * @param endPoint      外部正则取出的棋子终点数据
     * @throws IOException
     */
    private void setEndPoint(String endPoint) throws IOException {
        //        一二三四五六七八九123456789
        char endChar = endPoint.charAt(0);
        String zh = "一二三四五六七八九";
        String num = "123456789";
        if (zh.indexOf(endChar) >= 0){
            this.startPoint = zh.indexOf(endChar) + 1;
            return;
        }
        if (num.indexOf(endChar) >= 0){
            this.startPoint = num.indexOf(endChar) + 1;
            return;
        }
        throw new IOException("棋子移动距离错误，操作建立异常。");
    }

    //#endregion


    public char getPieceType() {
        return pieceType;
    }

    public void setPieceType(char pieceType) {
        this.pieceType = pieceType;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(int operationStatus) {
        this.operationStatus = operationStatus;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public char getEndPieceType() {
        return endPieceType;
    }

    public void setEndPieceType(char endPieceType) {
        this.endPieceType = endPieceType;
    }

    public boolean getRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
        int c = (int) this.pieceType + ('a' - 'A');
        if (isRed){
            this.pieceType = (char) c;
        }
        if (isRed && this.operationStatus != 0){
            //  进变成退 退变成进
            if (this.operationStatus == 1){
                this.operationStatus = 2;
            }
            if (this.operationStatus == 2){
                this.operationStatus = 1;
            }
        }



    }
}
