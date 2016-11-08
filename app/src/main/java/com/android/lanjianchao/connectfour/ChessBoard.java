package com.android.lanjianchao.connectfour;

/**
 * Created by lanjianchao on 2016/10/18.
 */

public class ChessBoard {
    private int[][] chess=new int[6][7];
    private boolean redMove;
    private boolean greenMove;
    private boolean isStop;

    public int[][] getChess() {
        return chess;
    }

    public void setChess(int[][] chess) {
        this.chess = chess;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isRedMove() {
        return redMove;
    }

    public void setRedMove(boolean redMove) {
        this.redMove = redMove;
    }

    public boolean isGreenMove() {
        return greenMove;
    }

    public void setGreenMove(boolean greenMove) {
        this.greenMove = greenMove;
    }


    public void initBoard(){
        redMove=true;
        greenMove=false;
        setStop(false);
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                chess[i][j]=0;
            }
        }
    }
    public void changeEmptyState(int i,int j){
        chess[i][j]=0;
        if(redMove){
            setRedMove(false);
            setGreenMove(true);
        }else{
            setRedMove(true);
            setGreenMove(false);
        }

    }
    public boolean isAvailable(int coloum){
        boolean flag=false;
        for(int i=5;i>=0;i--){
            if(chess[i][coloum]==0){
                flag=true;
            }
        }
        return flag;
    }
    public int addPosition(int coloum){
        int row=0;
        for(int i=5;i>=0;i--){
            if(chess[i][coloum]==0){
                row=i;
                break;
            }
        }
        return row;
    }
    public void changeRedState(int i,int j){
        chess[i][j]=1;
        setRedMove(false);
        setGreenMove(true);
    }
    public void changeGreenState(int i,int j){
        chess[i][j]=2;
        setGreenMove(false);
        setRedMove(true);
    }
    public boolean isRedWinGame(int i,int j){
        boolean flag=false;
        //judege horizion
        for(int k=0;k<chess[i].length-3;k++){
            if(chess[i][k]==1&& chess[i][k+1]==1&&chess[i][k+2]==1&&chess[i][k+3]==1){
                int l=0;
                while((k+l<7)&& chess[i][k+l]==1){
                    chess[i][k+l]=3;
                    l++;

                }

                flag=true;
                setStop(true);
                break;
            }
        }

        for(int k=0;k<chess.length-3;k++){
            if((chess[k][j]==1||chess[k][j]==3)&& (chess[k+1][j]==1||chess[k+1][j]==3)
                    &&(chess[k+2][j]==1||chess[k+2][j]==3)&&(chess[k+3][j]==1||chess[k+3][j]==3)){
                int l=0;
                while(k+l<6){
                    chess[k+l][j]=3;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
        }
        int r=i;
        int c=j;
        if(i<j){
            r=0;
            c=j-i;
        }else{
            c=0;
            r=i-j;
        }
        while(r+3<6 && c+3<7){
            if((chess[r][c]==1 || chess[r][c]==3)&& (chess[r+1][c+1]==1 || chess[r+1][c+1]==3)
                    &&(chess[r+2][c+2]==1||chess[r+2][c+2]==3)&&(chess[r+3][c+3]==1||chess[r+3][c+3]==3)){
                int l=0;
                while(r+l<6&&c+l<7){
                    chess[r+l][c+l]=3;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
            r++;
            c++;
        }
        if(i>6-j){
            c=6;
            r=i-6+j;
        }else{
            r=0;
            c=j+i;
        }
        while(r+3<6&&c-3>=0){
            if((chess[r][c]==1||chess[r][c]==3)&& (chess[r+1][c-1]==1||chess[r+1][c-1]==3)
                    &&(chess[r+2][c-2]==1||chess[r+2][c-2]==3)&&(chess[r+3][c-3]==1||chess[r+3][c-3]==3)){
                int l=0;
                while(r+l<6&&c-l>-1){
                    chess[r+l][c-l]=3;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
            r++;
            c--;
        }
        return flag;
    }
    public boolean isGreenWinGame(int i,int j){
        boolean flag=false;
        //judege horizion
        for(int k=0;k<chess[i].length-3;k++){
            if(chess[i][k]==2&& chess[i][k+1]==2&&chess[i][k+2]==2&&chess[i][k+3]==2){
                int l=0;
                while((k+l<7)&& chess[i][k+l]==2){
                    chess[i][k+l]=4;
                    l++;

                }
                flag=true;
                setStop(true);
                break;
            }
        }
        for(int k=0;k<chess.length-3;k++){
            if((chess[k][j]==2||chess[k][j]==4)&& (chess[k+1][j]==2||chess[k+1][j]==4)
                    &&(chess[k+2][j]==2||chess[k+2][j]==4)&&(chess[k+3][j]==2||chess[k+3][j]==4)){
                int l=0;
                while(k+l<6){
                    chess[k+l][j]=4;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
        }
        int r=i;
        int c=j;
        if(i<j){
            r=0;
            c=j-i;
        }else{
            c=0;
            r=i-j;
        }
        while(r+3<6 && c+3<7){
            if((chess[r][c]==2 || chess[r][c]==4)&& (chess[r+1][c+1]==2 || chess[r+1][c+1]==4)
                    &&(chess[r+2][c+2]==2||chess[r+2][c+2]==4)&&(chess[r+3][c+3]==2||chess[r+3][c+3]==4)){
                int l=0;
                while(r+l<6&&c+l<7){
                    chess[r+l][c+l]=4;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
            r++;
            c++;
        }
        if(i>6-j){
            c=6;
            r=i-6+j;
        }else{
            r=0;
            c=j+i;
        }
        while(r+3<6&&c-3>=0){
            if((chess[r][c]==2||chess[r][c]==4)&& (chess[r+1][c-1]==2||chess[r+1][c-1]==4)
                    &&(chess[r+2][c-2]==2||chess[r+2][c-2]==4)&&(chess[r+3][c-3]==2||chess[r+3][c-3]==4)){
                int l=0;
                while(r+l<6&&c-l>-1){
                    chess[r+l][c-l]=4;
                    l++;
                }
                flag=true;
                setStop(true);
                break;
            }
            r++;
            c--;
        }
        return flag;
    }



}
