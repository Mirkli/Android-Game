package com.example.user.magnetgame;

/**
 * Created by User on 29.12.2015.
 */
public class Hero {
    static int nextLpos, nextRpos;
    static double v=5;
    static String status = "stop";
    int size;
    int pos1;
    int pos2;
    int pos3;
    int pos4;
    int currentpos;
    int y;
    double x;
    public Hero(int width){
        this.size = width/8;
        this.pos1 = width/16;
        this.pos2 = width/16+2*size;
        this.pos3 = width/16+4*size;
        this.pos4 = width/16+6*size;
        currentpos=pos2;
        x=currentpos;
        updatePos();
    }


    public void updatePos(){
        if(currentpos == pos1){
            nextRpos = pos2;
        }
        else if(currentpos == pos2){
            nextLpos = pos1;
            nextRpos = pos3;
        }
        else if(currentpos==pos3){
            nextLpos = pos2;
            nextRpos = pos4;
        }
        else if(currentpos==pos4){
            nextLpos = pos3;
        }
    }

    public void slowMoveL(){
        x-=v;
    }
    public void slowMoveR(){
        x+=v;
    }
    public void fastMoveL(){
        x=nextLpos;
    }
    public void fastMoveR(){
        x=nextRpos;
    }


    public void move(){
        if(status=="slowL")
            slowMoveL();
        else if(status=="slowR")
            slowMoveR();
        else if(status=="fastL")
            fastMoveL();
        else if(status=="fastR")
            fastMoveR();
        else if(status=="fastRmid")
            fastMoveR();
        else if(status=="fastLmid")
            fastMoveL();
    }

    public void checkPos(){
        if(status=="slowL" || status=="fastL" || status=="fastLmid"){
            if(x-nextLpos<3){
                currentpos=nextLpos;
                x=currentpos;
                if(status=="fastL")
                    status="stop";
                else
                    status = "stopped";
                updatePos();
            }
        }
        if(status=="slowR" || status=="fastR" || status=="fastRmid"){
            if(nextRpos-x<3){
                currentpos=nextRpos;
                x=currentpos;
                if(status=="fastR")
                    status="stop";
                else
                    status = "stopped";
                updatePos();
            }
        }
    }
  /*  public Hero(int y,Bitmap res){
        this.y=y;
        image = res;
    }*/
}
