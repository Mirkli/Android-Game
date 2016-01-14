package com.example.user.magnetgame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(new Game(this));
    }

    class Game extends View {

        String GAMESTATUS = "MAINMENU";
        Bitmap image,smallimage;

        Display display = getWindowManager().getDefaultDisplay();
        int delta = 5;
        Rect r;
        Rect rect;
        Paint paint = new Paint();
        Paint pink = new Paint();
        int Time = 0;
        Hero hero = new Hero(display.getWidth());


        Map map = new Map(display.getHeight(), display.getWidth());
        public final int y = display.getHeight() - hero.size * 2;
        int size = hero.size;

        Rect p1 = new Rect(hero.pos1, y, hero.pos1 + size, y + size);
        Rect p2 = new Rect(hero.pos2, y, hero.pos2 + size, y + size);
        Rect p3 = new Rect(hero.pos3, y, hero.pos3 + size, y + size);
        Rect p4 = new Rect(hero.pos4, y, hero.pos4 + size, y + size);


        public Game(Context context) {
            super(context);
        }
        /*OnDraw on meie main tsükkel (lõpmatu)
            Siin ta kontrollib, mis on praegune mängu staatus ning joonistab sõltuvalt sellest vajalikud asjad.
            Lõppus liigutab hero´t ja map´i (Kui on gamestaatus GAME)

         */
        protected void onDraw(Canvas canvas){
            if (GAMESTATUS == "INTRO") {



                Paint text = new Paint();
                text.setColor(Color.BLUE);
                text.setTextSize(20);
                canvas.drawText(Hero.status,50,50,text);

                GAMESTATUS = "MAINMENU";
            }

            if (GAMESTATUS == "MAINMENU") {
                image = BitmapFactory.decodeResource(getResources(),R.drawable.name);
                smallimage = Bitmap.createScaledBitmap(image, display.getWidth(), display.getHeight() / 5, false);
                canvas.drawBitmap(smallimage,0,0,null);

                image = BitmapFactory.decodeResource(getResources(),R.drawable.play);
                double halfWidth = display.getWidth()/1.2;
                double w = display.getWidth()/2;
                smallimage = Bitmap.createScaledBitmap(image, (int) halfWidth, (int) halfWidth, false);
                canvas.drawBitmap(smallimage,0,display.getHeight()/5,null);
                image = BitmapFactory.decodeResource(getResources(),R.drawable.tutorial);
                smallimage= Bitmap.createScaledBitmap(image, (int) w, (int) w, false);
                canvas.drawBitmap(smallimage,display.getWidth()/2,display.getHeight()-(int) w,null);
                image = BitmapFactory.decodeResource(getResources(),R.drawable.quit);
                smallimage = Bitmap.createScaledBitmap(image, display.getWidth()/3, display.getWidth()/3, false);
                canvas.drawBitmap(smallimage,0,display.getHeight()-display.getWidth()/3,null);
            }

            if (GAMESTATUS == "TUTORIAL") {

            }

            if (GAMESTATUS == "SETTINGS") {

            }

            if (GAMESTATUS == "CHOOSEWORLD") {

            }

            if (GAMESTATUS == "PAUSE") {

            }

            if (GAMESTATUS == "LOSE") {

            }

            if (GAMESTATUS == "GAME") {//Ну и основной

                image = BitmapFactory.decodeResource(getResources(),R.drawable.river);
                smallimage = Bitmap.createScaledBitmap(image, display.getWidth() - 2 * size, display.getHeight(), false);
                canvas.drawBitmap(smallimage,0+size,0,null);

                image = BitmapFactory.decodeResource(getResources(),R.drawable.leftberegone);
                smallimage = Bitmap.createScaledBitmap(image, size, display.getHeight(), false);
                canvas.drawBitmap(smallimage,0,0,null);

                image = BitmapFactory.decodeResource(getResources(),R.drawable.rightberegone);
                smallimage = Bitmap.createScaledBitmap(image,size, display.getHeight(), false);
                canvas.drawBitmap(smallimage,display.getWidth()-size,0,null);


                r = new Rect((int) hero.x, y, (int) hero.x + size,y+ size);
                pink.setStyle(Paint.Style.FILL);
                pink.setColor(Color.rgb(255, 128, 128));
                //canvas.drawRect(p1, pink);siin on hero positsioonid testi jaoks
                //canvas.drawRect(p2, pink);
                //canvas.drawRect(p3, pink);
                //canvas.drawRect(p4, pink);


                image = BitmapFactory.decodeResource(getResources(),R.drawable.statusstop_three);
                smallimage = Bitmap.createScaledBitmap(image,size, size*2, false);
                canvas.drawBitmap(smallimage,(int)hero.x,y,null);


                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);
                //canvas.drawRect(r, paint);//Hero draw test
                for (Platform i : map.platforms) {//platforms draw
                    rect = new Rect((int) i.x, (int) i.y, (int) i.x2, (int) i.y2);
                    canvas.drawRect(rect, paint);
                }
                if (Time % delta == 0) {
                    map.generate();
                    delta = (int) map.delta(hero.size);
                    Time = 0;
                }
                hero.move();
                hero.checkPos();
                map.moveMap();
                map.clean();
                if (map.check(hero.x, y,size) == false){

                }
                Time++;


            }
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {//Siin need eventid
            float evX = event.getX();
            float evY = event.getY();
            int index = event.getActionIndex();
            int secondD = 0,secondU=0;
            float s;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    if(GAMESTATUS=="GAME") {
                        if (Hero.status == "stop") {
                            if (evX < display.getWidth() / 2 && hero.currentpos != hero.pos1) {
                                Hero.status = "slowL";
                            } else if (evX > display.getWidth() / 2 && hero.currentpos != hero.pos4) {
                                Hero.status = "slowR";
                            }
                        }
                    }
                    if(GAMESTATUS=="MAINMENU"){
                        if(evX>=0 && evX<=display.getWidth()/1.2){
                            if(evY>=display.getHeight()/5 && evY <= display.getHeight()/5+ display.getWidth()/1.2){
                                GAMESTATUS="GAME";
                            }
                        }
                        if(evX>=0 && evX<=display.getWidth()/3){
                            if(evY>=display.getHeight()-display.getWidth()/3 && evY<=display.getHeight()){
                                System.exit(0);
                            }
                        }
                        if(evX>=display.getWidth()/2 && evX<=display.getWidth()){
                            if(evY>=display.getHeight()-display.getWidth()/2 && evY<=display.getHeight()){
                                GAMESTATUS="TUTORIAL";
                            }
                        }
                    }

                case MotionEvent.ACTION_POINTER_DOWN:
                    secondD = index;
                    if(secondD == 1){
                        s=event.getX(secondD);
                        if(s < display.getWidth() / 2 && Hero.status == "slowR"){
                            Hero.status="stop";
                            hero.nextLpos=hero.currentpos;
                        }
                        else if(s > display.getWidth() / 2 && Hero.status == "slowL"){
                            Hero.status="stop";
                            hero.nextRpos=hero.currentpos;
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    if (evX > display.getWidth() / 2) {
                        if (Hero.status == "stopped")
                            Hero.status = "stop";
                        else if (Hero.status == "slowR" || Hero.status=="stop")
                            Hero.status = "fastR";
                    } else if (evX < display.getWidth() / 2) {
                        if (Hero.status == "stopped")
                            Hero.status = "stop";
                        else if (Hero.status == "slowL" || Hero.status=="stop")
                            Hero.status = "fastL";
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    secondU=index;
                    s=event.getX(secondU);
                    if(secondU<2) {
                        if (s > display.getWidth() / 2 && Hero.status == "stop") {
                            Hero.status = "fastRmid";

                        } else if (s < display.getWidth() / 2 && Hero.status == "stop") {
                            Hero.status = "fastLmid";
                        }
                    }
                    break;
            }
            return true;
        }
    }
}