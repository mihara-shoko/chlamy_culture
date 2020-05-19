package com.mi.chlamyculture;

import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class ChangePos extends MainActivity {

    float posX, posY, size;
    ImageView iv;
    int period;
    float frW, frH;
    Handler handlerEle = new Handler();
    Timer timerEle = new Timer();
    int delay;


    public ChangePos(ImageView iv, float posX, float posY, float size, float frW, float frH, int delay) {
        this.iv = iv;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.frW = frW;
        this.frH = frH;
        this.delay = delay;

    }


    public void elementPos(){

        period = (int)Math.round(Math.random()*10000+5000);

        timerEle.schedule(new TimerTask() {
            @Override
            public void run() {
                handlerEle.post(new Runnable(){
                    @Override
                    public void run(){

                        if(posY <= 0){
                            posX = (float)Math.floor(Math.random()*(frW - size));
                            posY = (float)Math.floor(Math.random()*(frH - size));
                            iv.setX(posX);
                            iv.setY(posY);
                            posY = 1000;
                        }else{
                            iv.setY(-1000);
                            posY -= 1000;
                        }

                    }
                });
            }
        },delay, period);

    }

    public void stopEleTimer(){
        if (timerEle != null){
            timerEle.cancel();
            timerEle = null;
        }
    }

}
