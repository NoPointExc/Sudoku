package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Edw on 2015/6/10.
 */

public class ClockView extends TextView {
    private static Handler handler;
    private int time = 0;
    private boolean isPause=false;
    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /*
    *initial clock view
    * */
    private void init() {
        this.setText("0:00:00");
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                time=msg.what;
                if(isPause&&!getText().equals("Paused")) {
                    setText("Paused");
                }else update();
            }
        };
        new Thread(new Ticktock()).start();
        this.setOnClickListener(new mOnClickLis());
    }

    /*
    * tick&tock every second
    * */
    class Ticktock implements Runnable {

        /*
    * update time per second
    * */
        @Override
        public void run() {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //max time is 7200s=2hr
                    if(!isPause) time = (time + 1) % 7200;
                    handler.sendEmptyMessage(time);
                }
            };
            timer.schedule(timerTask, 0, 1000);

        }
    }



    /*
    * update time text and flash the view
    * */
    private void update() {
        StringBuilder sb = new StringBuilder("");
        int second = time % 60;
        int min = (time % 3600) / 60;
        int hour = time / 3600;
        sb.append(hour);
        sb.append(':');
        if(min<10) sb.append('0');
        sb.append(min);
        sb.append(':');
        if(second<10) sb.append('0');
        sb.append(second);
        this.setText(sb.toString());
        this.invalidate();
    }

    class mOnClickLis implements OnClickListener {
        @Override
        public void onClick(View v) {
            isPause=!isPause;
        }
    }

}
