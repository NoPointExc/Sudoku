package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import util.MatChecker;


public class BlockView extends View {
    //vals
    private int[][] matr;
    private int[][] type; //1 for final, 0 for alter-able.
    //pen
    private Paint blackpen;
    private Paint Blackpen;
    private Paint greenpen;
    private Paint graypen;
    private Paint yellowpen;
    //size
    private int height = 50, width = 50;
    private int step;

    public BlockView(Context context) {
        super(context);
        init();
    }

    public BlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //init matr
        matr = new int[9][9];
        type = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                matr[i][j] = 0;
                type[i][j] = 0;
            }

        //init paint
        blackpen = new Paint();
        blackpen.setColor(Color.BLACK);
        blackpen.setStrokeWidth(3);
        Blackpen = new Paint();
        Blackpen.setColor(Color.BLACK);
        Blackpen.setStrokeWidth(6);
        greenpen = new Paint();
        greenpen.setColor(Color.GREEN);
        greenpen.setTextSize(70);
        graypen = new Paint();
        graypen.setColor(Color.GRAY);
        graypen.setTextSize(70);
        yellowpen = new Paint();
        yellowpen.setColor(Color.YELLOW);
        yellowpen.setStrokeWidth(3);
        //listener
        this.setOnTouchListener(new myTouchLis());
    }

    class myTouchLis implements View.OnTouchListener {
        int x = 0, y = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getX();
                    y = (int) event.getY();
                    System.out.println("!!!!!!!!!!!!!x:" + x + " y:" + y);
                    click(x, y);
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    break;
            }
            return false;
        }
    }

    //invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        //measure size
        height = this.getMeasuredHeight();
        width = this.getMeasuredWidth();
        this.setMinimumHeight(width);
        // lines
        step = width / 9;
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                canvas.drawLine(i * step, 0, i * step, width, Blackpen);
                canvas.drawLine(0, i * step, width, i * step, Blackpen);
            } else {
                canvas.drawLine(i * step, 0, i * step, width, blackpen);
                canvas.drawLine(0, i * step, width, i * step, blackpen);
            }
        }

        //val
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matr[i][j] == 0) continue; //empty block, draw nothing
                String text = String.valueOf(matr[i][j]);
                //System.out.println("type"+type[i][j]);
                if (type[i][j] == 0)
                    canvas.drawText(text, i * step + step / 5, j * step + step * 4 / 5, greenpen);
                else canvas.drawText(text, i * step + step / 5, j * step + step * 4 / 5, graypen);
            }
        }
    }

    public void setMatr(int[][] m) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.matr[i][j] = m[i][j];
                if (matr[i][j] != 0) {
                    type[i][j] = 1;
                }
            }
        }

    }
    /*
    * set a value in the matrix and invalidate the view
    * */
    public void setVal(int x, int y, int val) {

        if (type[x][y] == 1) return;
        matr[x][y] = val;
        this.invalidate();
    }
    /*
    * click on value in the matrix
    *
    * */
    public void click(int x, int y) {
        int i = x / step, j = y / step;
        System.out.println("i:" + i + "j" + j);
        if (i < 9 && j < 9) setVal(i, j, (matr[i][j] + 1) % 10);
    }

    public boolean check(){
        boolean[] row=MatChecker.checkRow(matr);
        boolean[] col=MatChecker.checkRow(matr);
        boolean[] block=MatChecker.checkBlock(matr);
        for(int i=0;i<9;i++){
            //unfish
        }
        return MatChecker.isFinish(matr);
    }
}
