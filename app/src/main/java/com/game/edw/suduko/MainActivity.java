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


public class MainActivity extends Activity {
    private BlockView blockView;
    private TextView titleText; //title textview, touch to check matrix

    private int[][] ans;
    private int[][] matr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockView=(BlockView)findViewById(R.id.blockView);
        titleText=(TextView)findViewById(R.id.title_text);
        //generate matrix and initial block view
        ans= MatGen.generat();
        matr=MatGen.remove(60, ans);
        blockView.setMatr(matr);
        blockView.invalidate();
        //set title TextView's onTouchListener
        titleText.setOnTouchListener(new mOnTouchLis());
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

                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }


            return false;
        }
    }

}
