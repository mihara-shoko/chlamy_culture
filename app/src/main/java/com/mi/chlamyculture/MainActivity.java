package com.mi.chlamyculture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView scoreLabel, start;
    ImageView lightTop, lightBottom, lightLeft, lightRight, chlamy;
    ImageView carbon, carbon2, phosphorus, nitrogen, nitrogen2, calcium, toxin, toxin2, toxin3,
            toxin4, star, star2;
    FrameLayout frame;
    private int carbonScore = 0, nitrogenScore = 0, phosphorusScore = 0, calciumScore = 0;

    float chlamyX, chlamyY, carbonX, carbonY, nitrogenX, nitrogenY, phosphorusX, phosphorusY,
            calciumX, calciumY, toxinX, toxinY, starX, starY, carbonX2, carbonY2, toxinX2, toxinY2,
            toxinX3, toxinY3, toxinX4, toxinY4, starX2, starY2, nitrogenX2, nitrogenY2;
    float chlamyWidth, chlamyHeight, carbonSize, nitrogenSize, phosphorusSize, calciumSize;
    float toxinSize, starSize;
    String direction = "top";
    boolean phototaxis = true;

    Handler handler = new Handler();
    Timer timer = new Timer();

    float frameWidth, frameHeight;
    float frameX, frameY;
    int screenWidth, screenHeight;
    int chlamySpeedX, chlamySpeedY;

    ChangePos cpCarbon, cpCarbon2, cpNitrogen, cpNitrogen2, cpPhospho, cpCal, cpToxin, cpToxin2;
    ChangePos cpToxin3, cpToxin4, cpStar, cpStar2;

    private Sound sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = findViewById(R.id.scoreLabel);
        lightTop = findViewById(R.id.light_top);
        lightBottom = findViewById(R.id.light_bottom);
        lightLeft = findViewById(R.id.light_left);
        lightRight = findViewById(R.id.light_right);
        chlamy = findViewById(R.id.chlamy);
        start = findViewById(R.id.start);

        carbon = findViewById(R.id.carbon);
        carbon2 = findViewById(R.id.carbon2);
        phosphorus = findViewById(R.id.phosphorus);
        nitrogen = findViewById(R.id.nitrogen);
        nitrogen2 = findViewById(R.id.nitrogen2);
        calcium = findViewById(R.id.calcium);
        toxin = findViewById(R.id.toxin);
        toxin2 = findViewById(R.id.toxin2);
        toxin3 = findViewById(R.id.toxin3);
        toxin4 = findViewById(R.id.toxin4);
        star = findViewById(R.id.star);
        star2 = findViewById(R.id.star2);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        chlamySpeedX = screenWidth/120;
        chlamySpeedY = screenHeight/213;


        carbon.setY(carbonY = -500.0f);
        carbon2.setY(carbonY2 = -500.0f);
        phosphorus.setY(phosphorusY = -500.0f);
        nitrogen.setY(nitrogenY = -500.0f);
        nitrogen2.setY(nitrogenY2 = -500.0f);
        calcium.setY(calciumY = -500.0f);
        toxin.setY(toxinY = -500.0f);
        toxin2.setY(toxinY2 = -500.0f);
        toxin3.setY(toxinY3 = -500.0f);
        toxin4.setY(toxinY4 = -500.0f);
        star.setY(starY = -500.0f);
        star2.setY(starY2 = -500.0f);
        scoreLabel.setText("C = " + carbonScore + ", N = " + nitrogenScore +
                ", P = " + phosphorusScore + ", Ca = " + calciumScore + " ⇒ 0 cells");



    }


    public void setLight(ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4){
            iv1.setImageResource(R.drawable.light_on);
            iv2.setImageResource(R.drawable.light_off);
            iv3.setImageResource(R.drawable.light_off);
            iv4.setImageResource(R.drawable.light_off);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sound = new Sound(this);

        lightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLight(lightTop, lightBottom, lightLeft, lightRight);
                direction = "top";
                if(phototaxis){
                    chlamy.setImageResource(R.drawable.chlamy);
                }else{
                    chlamy.setImageResource(R.drawable.chlamy_n_bottom);
                }

            }
        });

        lightBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLight(lightBottom, lightTop, lightLeft, lightRight);
                direction = "bottom";
                if (phototaxis){
                    chlamy.setImageResource(R.drawable.chlamy_bottom);
                }else{
                    chlamy.setImageResource(R.drawable.chlamy_n);
                }

            }
        });

        lightLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLight(lightLeft, lightBottom, lightTop, lightRight);
                direction = "left";
                if (phototaxis){
                    chlamy.setImageResource(R.drawable.chlamy_left);
                }else{
                    chlamy.setImageResource(R.drawable.chlamy_n_right);
                }

            }
        });

        lightRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLight(lightRight, lightBottom, lightLeft, lightTop);
                direction = "right";
                if (phototaxis){
                    chlamy.setImageResource(R.drawable.chlamy_right);
                }else{
                    chlamy.setImageResource(R.drawable.chlamy_n_left);
                }

            }
        });

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                sound.playStartSound();

                frame = findViewById(R.id.frame);
                frameX = frame.getX();
                frameY = frame.getY();
                frameHeight = frame.getHeight();
                frameWidth = frame.getWidth();
                carbonSize = carbon.getWidth();
                nitrogenSize = nitrogen.getWidth();
                phosphorusSize = phosphorus.getWidth();
                calciumSize = calcium.getWidth();
                toxinSize = toxin.getWidth();
                starSize = star.getWidth();


                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable(){
                            @Override
                            public void run(){
                                chlamyPos();
                                hitCheck();
                                int[] elements = {carbonScore / 3, nitrogenScore / 2, phosphorusScore, calciumScore};
                                Arrays.sort(elements);
                                int cellNumber = elements[0];
                                scoreLabel.setText("C = " + carbonScore + ", N = " + nitrogenScore +
                                        ", P = " + phosphorusScore + ", Ca = " + calciumScore +
                                        " ⇒ " + cellNumber + " cells");

                            }
                        });

                    }
                }, 0, 20);

                // carbon
                cpCarbon = new ChangePos(carbon, carbonX, carbonY, carbonSize,
                        frameWidth,  frameHeight, 0);
                cpCarbon.elementPos();

                cpCarbon2 = new ChangePos(carbon2, carbonX2, carbonY2, carbonSize,
                        frameWidth,  frameHeight, 3000);
                cpCarbon2.elementPos();

                // nitrogen
                cpNitrogen = new ChangePos(nitrogen, nitrogenX, nitrogenY, nitrogenSize,
                        frameWidth, frameHeight, 0);
                cpNitrogen.elementPos();

                cpNitrogen2 = new ChangePos(nitrogen, nitrogenX2, nitrogenY2, nitrogenSize,
                        frameWidth, frameHeight, 4000);
                cpNitrogen2.elementPos();

                // phosphorus
                cpPhospho = new ChangePos(phosphorus, phosphorusX, phosphorusY, phosphorusSize,
                        frameWidth, frameHeight, 0);
                cpPhospho.elementPos();

                //calcium
                cpCal = new ChangePos(calcium, calciumX, calciumY, calciumSize,
                        frameWidth, frameHeight, 2000);
                cpCal.elementPos();

                // toxin
                cpToxin = new ChangePos(toxin, toxinX, toxinY, toxinSize,
                        frameWidth, frameHeight, 0);
                cpToxin.elementPos();

                cpToxin2 = new ChangePos(toxin2, toxinX2, toxinY2, toxinSize,
                        frameWidth, frameHeight, 5000);
                cpToxin2.elementPos();

                cpToxin3 = new ChangePos(toxin3, toxinX3, toxinY3, toxinSize,
                        frameWidth, frameHeight, 60000);
                cpToxin3.elementPos();

                cpToxin4 = new ChangePos(toxin4, toxinX4, toxinY4, toxinSize,
                        frameWidth, frameHeight, 120000);
                cpToxin4.elementPos();

                // star
                cpStar = new ChangePos(star, starX, starY, starSize,
                        frameWidth, frameHeight, 3000);
                cpStar.elementPos();

                cpStar2 = new ChangePos(star2, starX2, starY2, starSize,
                        frameWidth, frameHeight, 60000);
                cpStar2.elementPos();


            }
        });

    }

    public void chlamyPos(){


        chlamyWidth = chlamy.getWidth();
        chlamyHeight = chlamy.getHeight();


        // chlamy
        switch (direction){
            case "bottom":
                if (phototaxis){
                    chlamyY += chlamySpeedY;
                }else{
                    chlamyY -= chlamySpeedY;
                }
                break;

            case "left":
                if (phototaxis){
                    chlamyX -= chlamySpeedX;
                }else{
                    chlamyX += chlamySpeedX;
                }
                break;

            case "right":
                if (phototaxis){
                    chlamyX += chlamySpeedX;
                }else{
                    chlamyX -= chlamySpeedX;
                }
                break;

            default:
                if (phototaxis){
                    chlamyY -= chlamySpeedY;
                }else{
                    chlamyY += chlamySpeedY;
                }

        }

        if (chlamyX < 0) chlamyX = 0;
        if (chlamyX > frameWidth - chlamyWidth) chlamyX = frameWidth - chlamyWidth;
        if (chlamyY < 0) chlamyY = 0;
        if (chlamyY > frameHeight - chlamyHeight) chlamyY = frameHeight - chlamyHeight;

        chlamy.setX(chlamyX);
        chlamy.setY(chlamyY);

    }





    public void hitCheck(){
        // carbon
        if(hitElement(carbonSize, carbon)){
            carbonY = -1000;
            carbon.setY(carbonY);
            carbonScore += 1;
            sound.playHitSound();

        }

        if(hitElement(carbonSize, carbon2)){
            carbonY2 = -1000;
            carbon2.setY(carbonY2);
            carbonScore += 1;
            sound.playHitSound();
        }

        // nitrogen
        if(hitElement(nitrogenSize, nitrogen)){
            nitrogenY = -1000;
            nitrogen.setY(nitrogenY);
            nitrogenScore += 1;
            sound.playHitSound();
        }

        if(hitElement(nitrogenSize, nitrogen2)){
            nitrogenY2 = -1000;
            nitrogen2.setY(nitrogenY2);
            nitrogenScore += 1;
            sound.playHitSound();
        }

        // phosphorus
        if (hitElement(phosphorusSize, phosphorus)){
            phosphorusY = -1000;
            phosphorus.setY(phosphorusY);
            phosphorusScore += 1;
            sound.playHitSound();
        }

        // calcium
        if (hitElement(calciumSize, calcium)){
            calciumY = -1000;
            calcium.setY(calciumY);
            calciumScore += 1;
            sound.playHitSound();
        }

        // star
        if (hitElement(starSize, star)| hitElement(starSize, star2)){
            starY = -1000;
            starY2 = -1000;
            star.setY(starY);
            star2.setY(starY2);
            phototaxis = !phototaxis;
            sound.playStarSound();
            switch (direction){
                case "bottom":
                    if (phototaxis){
                        chlamy.setImageResource(R.drawable.chlamy_bottom);
                    }else{
                        chlamy.setImageResource(R.drawable.chlamy_n);
                    }
                    break;
                case "left":
                    if (phototaxis){
                        chlamy.setImageResource(R.drawable.chlamy_left);
                    }else{
                        chlamy.setImageResource(R.drawable.chlamy_n_right);
                    }
                    break;
                case "right":
                    if (phototaxis){
                        chlamy.setImageResource(R.drawable.chlamy_right);
                    }else{
                        chlamy.setImageResource(R.drawable.chlamy_n_left);
                    }
                    break;
                default:
                    if (phototaxis){
                        chlamy.setImageResource(R.drawable.chlamy);
                    }else{
                        chlamy.setImageResource(R.drawable.chlamy_n_bottom);
                    }
            }
        }

        // toxin (GAME OVER)
        if (hitElement(toxinSize, toxin) | hitElement(toxinSize, toxin2)|
                hitElement(toxinSize, toxin3) | hitElement(toxinSize, toxin4)){
            sound.playToxinSound();
            if (timer != null){
                timer.cancel();
                timer = null;
            }
            cpCarbon.stopEleTimer();
            cpCarbon2.stopEleTimer();
            cpNitrogen.stopEleTimer();
            cpNitrogen2.stopEleTimer();
            cpPhospho.stopEleTimer();
            cpCal.stopEleTimer();
            cpToxin.stopEleTimer();
            cpToxin2.stopEleTimer();
            cpToxin3.stopEleTimer();
            cpToxin4.stopEleTimer();
            cpStar.stopEleTimer();
            cpStar2.stopEleTimer();

            //go to result
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("C_SCORE", carbonScore);
            intent.putExtra("N_SCORE", nitrogenScore);
            intent.putExtra("P_SCORE", phosphorusScore);
            intent.putExtra("Ca_SCORE", calciumScore);
            startActivity(intent);
        }


    }

    public boolean hitElement(float size, ImageView iv){
        float centerX = iv.getX() + size/2;
        float centerY = iv.getY() + size/2;

        // chlamy(xi-xf, yi-yf)
        float xi = chlamy.getX();
        float xf = xi + chlamy.getWidth();
        float yi = chlamy.getY();
        float yf = yi + chlamy.getHeight();
        return centerX >= xi && centerX <= xf && centerY >= yi && centerY <= yf;
    }

    @Override
    public void onBackPressed(){}

    @Override
    protected void onUserLeaveHint() {

        if (timer != null){
            timer.cancel();
            timer = null;
        }
        cpCarbon.stopEleTimer();
        cpCarbon2.stopEleTimer();
        cpNitrogen.stopEleTimer();
        cpNitrogen2.stopEleTimer();
        cpPhospho.stopEleTimer();
        cpCal.stopEleTimer();
        cpToxin.stopEleTimer();
        cpToxin2.stopEleTimer();
        cpToxin3.stopEleTimer();
        cpToxin4.stopEleTimer();
        cpStar.stopEleTimer();
        cpStar2.stopEleTimer();

        this.finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        cpCarbon.stopEleTimer();
        cpCarbon2.stopEleTimer();
        cpNitrogen.stopEleTimer();
        cpNitrogen2.stopEleTimer();
        cpPhospho.stopEleTimer();
        cpCal.stopEleTimer();
        cpToxin.stopEleTimer();
        cpToxin2.stopEleTimer();
        cpToxin3.stopEleTimer();
        cpStar.stopEleTimer();
        cpStar2.stopEleTimer();

        this.finish();
    }
}
