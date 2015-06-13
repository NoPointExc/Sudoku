package com.game.edw.suduko;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
    //intinialized flag
    private static boolean initFlag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockView=(BlockView)findViewById(R.id.blockView);
        titleText=(TextView)findViewById(R.id.title_text);
        clockView=(ClockView)findViewById(R.id.clockView);
        //generate matrix and initial block view
        System.out.println("initFlag:"+initFlag);
        if(!initFlag){
            initFlag=true;
            System.out.println("initFlag:"+initFlag);
            ans= MatGen.generat();
            System.out.println("generate");
            matr=MatGen.remove(20, ans);
        }

        blockView.setMatr(matr);
        blockView.invalidate();
        //set title TextView's onTouchListener
        titleText.setOnTouchListener(new mOnTouchLis());
        //set clock view on click listener
        clockView.setOnClickListener(new clkOnClickLis());
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * title TextView's onTouchListener
    * ::check matrix
    * */
    class mOnTouchLis implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    blockView.check(); //return correct ans or not
                    System.out.println("DOWN");
                    break;
                case MotionEvent.ACTION_UP:
                    blockView.unCheck();
                    System.out.println("UP");
                    break;
            }


            return true;
        }
    }

    class clkOnClickLis implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(!clockView.isPause()) clockView.pause();
            else clockView.restart();
        }
    }

}
