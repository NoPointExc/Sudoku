package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import util.MatChecker;


public class BlockView extends View {
    //vals
    private int[][] matr;
    private int[][] type; //1 for final, 0 for alter-able.
    //mark
    private boolean[] isRowMarked;
    private boolean[] isColMarked;
    private boolean[] isBloMarked;
    private boolean indFlag = false; //indicate Flag, is true, should show position of wrong cell
    //pen
    private Paint blackpen;
    private Paint Blackpen;
    private Paint greenpen;
    private Paint graypen;
    private Paint Yellowpen;
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
        //init marks
        isRowMarked = new boolean[9];
        isColMarked = new boolean[9];
        isBloMarked = new boolean[9];
        //init paint
        blackpen = new Paint();
        blackpen.setColor(Color.BLACK);
        blackpen.setStrokeWidth(3);
        Blackpen = new Paint();
        Blackpen.setColor(Color.BLACK);
        Blackpen.setStrokeWidth(6);
        greenpen = new Paint();
        greenpen.setColor(Color.parseColor("#71C671"));
        greenpen.setTextSize(70);
        graypen = new Paint();
        graypen.setColor(Color.GRAY);
        graypen.setTextSize(70);
        Yellowpen = new Paint();
        Yellowpen.setColor(Color.parseColor("#CD0000"));
        Yellowpen.setStrokeWidth(6);
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
                    click(x, y);
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    break;
            }
            return true;
        }
    }

    //invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        //measure size
        height = this.getMeasuredHeight();
        width = this.getMeasuredWidth();
        this.setMinimumHeight(width);
        //draw lines
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

        if(indFlag){
            //draw indicate marks
            for (int i = 0; i < 9; i++) {
                //right margin 4
                if (!isColMarked[i]) canvas.drawLine(4, i * step, 4, (i + 1) * step, Yellowpen);
                if (!isRowMarked[i]) canvas.drawLine(i * step, 4, (i + 1) * step, 4, Yellowpen);
                if(!isBloMarked[i]){
                    //vertical line
                    //canvas.drawLine(3 * (i + 1) * step, 3 * (i + 1) * step - step, 3 * (i + 1) * step, 3 * (i + 1) * step, Yellowpen);
                    Rect rect=new Rect();
                    int x=i%3,y=i/3;
                    rect.set(x*3*step+step/2,y*3*step+step/2,(x+1)*3*step-step/2,(y+1)*3*step-step/2);
                    canvas.drawRect(rect,Yellowpen);
                }
            }
        }

        //draw val
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
    /*
    * check is the correct answer
    * */
    public boolean check() {
        boolean correct = true;
        boolean[] row = MatChecker.checkRow(matr);
        boolean[] col = MatChecker.checkCol(matr);
        boolean[] block = MatChecker.checkBlock(matr);
        for (int i = 0; i < 9; i++) {
            isRowMarked[i] = row[i];
            isColMarked[i] = col[i];
            isBloMarked[i] = block[i];
            correct = correct && row[i] && col[i] && block[i];
        }
        System.out.println("--ROW--");
        MatChecker.print(isRowMarked);
        System.out.println("--COL--");
        MatChecker.print(isColMarked);
        indFlag = MatChecker.isCompleted(matr);//not complete yet, should not indicate
        this.invalidate();
        return correct;
    }

    public void unCheck(){
        indFlag=false;
        this.invalidate();
    }
}
