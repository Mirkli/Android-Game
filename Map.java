package com.example.user.magnetgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 29.12.2015.
 */
public class Map {


    double MapSpeed=5;
    Platform plat;
    Random random = new Random();
    int height;
    int width;
    int pos1,pos2,pos3,pos4,size,currentpos;
    static ArrayList<Integer> nextPos = new ArrayList<Integer>();
    static String prefPos;
    int s;
    double curPos;

    double pos;
    String nextType;
    ArrayList<Platform> platforms = new ArrayList<Platform>();

    public Map(int height,int width){
        this.height = height;
        this.width = width;
        this.size = width/8;
        this.pos1 = width/16;
        this.pos2 = width/16+2*size;
        this.pos3 = width/16+4*size;
        this.pos4 = width/16+6*size;
        currentpos=pos1;
        curPos = currentpos;
    }



    public void generate(){
        nextType = Platform.AllPlatforms[random.nextInt(2)];
        updateNextPoses(nextType, curPos);
        s = nextPos.size();
        System.out.println(nextPos);
        pos = nextPos.get(random.nextInt(s));
        plat = new Platform(pos,nextType,size,height);
        platforms.add(plat);
        curPos=pos;
        for(int i=0;i<=nextPos.size();i++){
            nextPos.remove(0);
        }


    }
    public double delta(double HeroSize){
        return (plat.y2-plat.y-HeroSize)/MapSpeed;
    }

    public boolean check(double x,int y,int size){
        for(Platform p:platforms){
            if(p.type=="basic" || p.type == "square"){
                for(Zone z:p.zones){
                    if(z.X1 < x+size/2 && z.X2 > x+size/2 && z.Y1<y+size/2 && z.Y2 >y+size/2){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void moveMap(){
        for(Platform i : platforms){
            i.y+=MapSpeed;
            i.y2+=MapSpeed;
        }
    }
    public void updateNextPoses(String type,double curPos){
        if(type=="basic")
            prefPos="L1 R1";
        else if(type=="square")
            prefPos="L1 R1";




        if(curPos==pos1){
            if(prefPos=="L1 R1") {
                nextPos.add(pos2);
            }
        }
        if(curPos==pos2){
            if(prefPos=="L1 R1") {
                nextPos.add(pos1);
                nextPos.add(pos3);
            }
        }
        if(curPos==pos3){
            if(prefPos=="L1 R1"){
                nextPos.add(pos2);
                nextPos.add(pos4);
            }
        }
        if(curPos==pos4){
            if(prefPos=="L1 R1"){
                nextPos.add(pos3);
            }
        }
    }
    public void clean(){
        for(Platform i:platforms){
            if(i.y>height) {
                platforms.remove(i);
                break;
            }
        }
    }

}
