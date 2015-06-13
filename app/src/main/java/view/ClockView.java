package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Edw on 2015/6/10.
 */

public class ClockView extends TextView {
    private static Handler handler;
    private  static int time = 0;
    private  static boolean isPause=false;
    private static Thread tickThread=null;

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
        if(isPause())this.setText("Paused");
        else this.setText("0:00:00");
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                time=msg.what;
                if(isPause) {
                    setText("Paused");
                }else update();
            }
        };
        if(tickThread==null){
            tickThread=new Thread(new Ticktock());
            tickThread.start();
        }


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
                    System.out.println("time: "+time+" isPause:"+isPause);
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



    public void pause(){
        isPause=true;

    }
    public void restart(){
        isPause=false;
    }

    public boolean isPause(){
        return isPause;
    }

}
