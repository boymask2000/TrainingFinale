package com.boymask.training.math;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.boymask.training.R;
import com.boymask.training.math.MathActivity;
import com.boymask.training.math.MathParameters;

import java.util.Random;

import static java.lang.Thread.sleep;

public class GameView extends SurfaceView implements Runnable {
    private boolean mRunning;
    private Thread mGameThread = null;
    private Path mPath;

    private Context mContext;

  //  private FlashlightCone mFlashlightCone;

    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF mWinnerRect;
    private int mBitmapX;
    private int mBitmapY;
    private int mViewWidth;
    private int mViewHeight;
    private SurfaceHolder mSurfaceHolder;
    private MathParameters mathParameters;
    private MathActivity activity;


    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPath = new Path();
    }

    /**
     * Runs in a separate thread.
     * All drawing happens here.
     */
    public void run() {
        int val = 1;
        Canvas canvas;

        while (mRunning) {
            // If we can obtain a valid drawing surface...
            if (mSurfaceHolder.getSurface().isValid()) {

                // Helper variables for performance.
/*                int x = mFlashlightCone.getX();
                int y = mFlashlightCone.getY();
                int radius = mFlashlightCone.getRadius();*/

                // Lock the canvas. Note that in a more complex app, with
                // more threads, you need to put this into a try/catch block
                // to make sure only one thread is drawing to the surface.
                // Starting with O, you can request a hardware surface with
                //    lockHardwareCanvas().
                // See https://developer.android.com/reference/android/view/
                //    SurfaceHolder.html#lockHardwareCanvas()
                canvas = mSurfaceHolder.lockCanvas();

                // Fill the canvas with white and draw the bitmap.
                canvas.save();
                canvas.drawColor(Color.WHITE);
                mPaint.setTextAlign(Paint.Align.CENTER);
                //    canvas.drawText("cua",mViewHeight/2,250,mPaint);
                //     canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);

//                Paint textPaint = new Paint();
//                textPaint.setTextAlign(Paint.Align.CENTER);

                int xPos = (canvas.getWidth() / 2);
                int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
                //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.

                canvas.drawText(creaText() , xPos, yPos, mPaint);


                // Clear the path data structure.
                mPath.rewind();
                // Restore the previously saved (default) clip and matrix state.
                canvas.restore();
                // Release the lock on the canvas and show the surface's
                // contents on the screen.
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    sleep(mathParameters.getDelay() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Random r = new Random();
    private String[] ops = {"/", "*", "-", "+"};

    private String creaText() {

        String op = getOp();
        for (int i = 0; i < mathParameters.getNumOps(); i++)
            op += " " + getOp();

        return op.substring(1);
    }

    private String getOp() {
        int maxVal = 9;
        if (mathParameters.getNumDigits() > 1) maxVal = 99;
        String op = ops[rand(4)];
        return op + " " + rand(maxVal);

    }

    private int rand(int max) {
        int val = r.nextInt(max);
        System.out.println(val);
        return val;
    }

    /**
     * We cannot get the correct dimensions of views in onCreate because
     * they have not been inflated yet. This method is called every time the
     * size of a view changes, including the first time after it has been
     * inflated.
     *
     * @param w    Current width of view.
     * @param h    Current height of view.
     * @param oldw Previous width of view.
     * @param oldh Previous height of view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

  //      mFlashlightCone = new FlashlightCone(mViewWidth, mViewHeight);

        // Set font size proportional to view size.
        mPaint.setTextSize(mViewHeight / 5);

        mBitmap = BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.ic_launcher_background);
        setUpBitmap();
    }

    /**
     * Updates the game data.
     * Sets new coordinates for the flashlight cone.
     *
     * @param newX New x position of touch event.
     * @param newY New y position of touch event.
     */
  /*  private void updateFrame(int newX, int newY) {
        mFlashlightCone.update(newX, newY);
    }*/

    /**
     * Calculates a randomized location for the bitmap
     * and the winning bounding rectangle.
     */
    private void setUpBitmap() {
        mBitmapX = (int) Math.floor(
                Math.random() * (mViewWidth - 10)); //mBitmap.getWidth()));
        mBitmapY = (int) Math.floor(
                Math.random() * (mViewHeight - 10)); //mBitmap.getHeight()));
        mWinnerRect = new RectF(mBitmapX, mBitmapY,
                mBitmapX + 10,
                mBitmapY + 10);//mBitmap.getHeight());
    }

    /**
     * Called by MainActivity.onPause() to stop the thread.
     */
    public void pause() {
        mRunning = false;
        try {
            // Stop the thread == rejoin the main thread.
            mGameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called by MainActivity.onResume() to start a thread.
     */
    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        activity.finish();
        /*
        float x = event.getX();
        float y = event.getY();

        // Invalidate() is inside the case statements because there are
        // many other motion events, and we don't want to invalidate
        // the view for those.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setUpBitmap();
                // Set coordinates of flashlight cone.
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // Updated coordinates for flashlight cone.
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            default:
                // Do nothing.
        }
        System.out.println("KKKKKKK");*/
        return true;
    }

    public MathParameters getMathParameters() {
        return mathParameters;
    }

    public void setMathParameters(MathParameters mathParameters) {
        this.mathParameters = mathParameters;
    }

    public void setActivity(MathActivity mathActivity) {
        this.activity=mathActivity;
    }
}
