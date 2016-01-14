package com.example.user.magnetgame;


import java.util.ArrayList;

public class Platform {
    double x,y;
    double x2,y2=0;
    double size;
    static String AllPlatforms[] = {"square","basic"};
    ArrayList<Zone> zones = new ArrayList<Zone>();
    String type;
    String feel;
    public Platform(double x,String type,int width,int height){
        this.x = x;
        this.type = type;
        this.x2=this.x+width;
        if(type=="basic") {
            this.size = height/2;
            this.y=y2-size;
            zones.add(new Zone(this.x,x2,y,y2));

        }
        else if(type=="square") {
            this.size = height/3;
            this.y=y2-size;
            zones.add(new Zone(this.x,x2,y,y2));

        }

    }

}


