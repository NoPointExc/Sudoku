package com.game.edw.suduko;


import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import util.MatGen;
import view.BlockView;
import view.ClockView;


public class MainActivity extends Activity {
    private BlockView blockView;
    private TextView titleText; //title textview, touch to check matrix
    private ClockView clockView;

    private static int[][]  ans;
    private static int[][]  matr;
    private static int[][] type;
    //intinialized flag
    private static boolean initFlag=false;
    //paused flag
    public static boolean pauseFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("-----onCreate------");
        setContentView(R.layout.activity_main);
        blockView=(BlockView)findViewById(R.id.blockView);
        titleText=(TextView)findViewById(R.id.title_text);
        clockView=(ClockView)findViewById(R.id.clockView);
        //generate matrix and initial block view

        //System.out.println("initFlag:" + initFlag);
        if(!initFlag){
            initFlag=true;
            System.out.println("initFlag:"+initFlag);
            ans= MatGen.generat();
            System.out.println("generate");
            matr=MatGen.remove(20, ans);
            type=initType(matr);
        }
        blockView.setMatr(matr,type);
        blockView.setOnTouchListener(new bvOnTouchLis(this));
        //set title TextView's onTouchListener
        titleText.setOnTouchListener(new mOnTouchLis(this));
        //set clock view on click listener
        clockView.setOnClickListener(new clkOnClickLis(this));
    }

    private int[][] initType(int[][] m){
        //init matr
        int[][] t = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if(m[i][j]!=0) t[i][j]=1;
                else t[i][j] = 0;
            }
        return t;
    }


    @Override
    protected void onPause() {
        clockView.pause();
        super.onPause();

    }

    @Override
    protected void onStop() {
        clockView.pause();
        super.onStop();

    }

    class bvOnTouchLis implements View.OnTouchListener{
        Context context;
        public bvOnTouchLis(Context context){
            this.context=context;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(pauseFlag) {
                Animation blowUp= AnimationUtils.loadAnimation(context,R.anim.blowup);
                clockView.startAnimation(blowUp);
                return true;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    blockView.click(x, y);
                    matr=blockView.getMatr();
                    type=blockView.getType();
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    break;
                case MotionEvent.ACTION_UP:
                    blockView.release();
                    break;
            }
            return true;
        }
    }


    /*
    * title TextView's onTouchListener
    * ::check matrix
    * */
    class mOnTouchLis implements View.OnTouchListener {
        Context context;
        ColorStateList orignColor;
        public mOnTouchLis(Context context){
            orignColor=titleText.getTextColors();
            this.context=context;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    boolean correct=blockView.check();
                    if(correct){

                    }else {
                        Animation shark=AnimationUtils.loadAnimation(context,R.anim.shark);

                        titleText.setTextColor(Color.RED);
                        titleText.startAnimation(shark);
                    }

                    //System.out.println("DOWN");
                    break;
                case MotionEvent.ACTION_UP:
                    titleText.setTextColor( orignColor);

                    blockView.unCheck();
                    //System.out.println("UP");
                    break;
            }


            return true;
        }
    }

    class clkOnClickLis implements View.OnClickListener {
        Context context;
        public clkOnClickLis(Context context){
            this.context=context;
        }
        @Override
        public void onClick(View v) {
            if(!pauseFlag) {
                clockView.pause();
                pauseFlag=true;
            }else {
                clockView.restart();
                pauseFlag=false;
            }
        }
    }

}
