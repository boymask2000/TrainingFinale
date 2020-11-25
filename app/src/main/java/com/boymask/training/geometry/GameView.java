package com.boymask.training.geometry;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.boymask.training.R;
import com.boymask.training.colors.Figures;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    private Random r = new Random();

    private GeometryParameters geoParameters;
    private GeometryActivity activity;
    private final String figures[] = {"Quadrato", "Rettangolo", "Cerchio", "Triangolo", "Rombo"};


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


        Canvas canvas;

        while (mRunning) {
            // If we can obtain a valid drawing surface...
            if (mSurfaceHolder.getSurface().isValid()) {

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

                mPaint.setTextSize(40);
                mPaint.setFakeBoldText(true);
                mPaint.setLinearText(true);



                canvas.drawColor(Color.WHITE);
                wordsSet.clear();
                for (int i = 0; i < geoParameters.getNumFigures(); i++) {


                    float x = (canvas.getWidth() / (geoParameters.getNumFigures() + 1)) * (i + 1);
                    float y = canvas.getHeight() / 2;
                    drawFigure(canvas, x, y);


                    if (geoParameters.isWithWords()) {
                        mPaint.setColor(Color.BLACK);

                        mPaint.setStrokeWidth(0);
                        mPaint.setStyle(Paint.Style.FILL);

                     //   canvas.drawText(figures[r.nextInt(figures.length)], x, y, mPaint);
                        canvas.drawText(getText(), x, y, mPaint);
                    }
                }


                // Clear the path data structure.
                mPath.rewind();
                // Restore the previously saved (default) clip and matrix state.
                canvas.restore();
                // Release the lock on the canvas and show the surface's
                // contents on the screen.
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    sleep(geoParameters.getDelay() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private Set<Integer> wordsSet = new HashSet<>();

    private String getText() {

        while (true) {
            int v = r.nextInt(figures.length);
            if (!wordsSet.contains(v)) {
                wordsSet.add(v);
                return figures[v];
            }
        }
    }
    private void drawFigure(Canvas canvas, float x, float y) {
        int num = r.nextInt(figures.length);
        Figures fig = Figures.CERCHIO;
        switch (num) {
            case 0:
                fig = Figures.CERCHIO;
                break;
            case 1:
                fig = Figures.QUADRATO;
                break;
            case 2:
                fig = Figures.RETTANGOLO;
                break;
            case 3:
                fig = Figures.ROMBO;
                break;
            case 4:
                fig = Figures.TRIANGOLO;
                break;
            default:
                fig = Figures.CERCHIO;
        }
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        if (fig == Figures.CERCHIO) {
            canvas.drawCircle(x, y, 150, mPaint);
            return;
        }
        if (fig == Figures.QUADRATO) {
            canvas.drawRect(x - 150, y - 150, x + 150, y + 150, mPaint);
            return;
        }
        if (fig == Figures.RETTANGOLO) {
            canvas.drawRect(x - 150, y - 100, x + 150, y + 100, mPaint);
            return;
        }
        if (fig == Figures.ROMBO) {
            canvas.drawLine(x, y + 150, x + 150, y, mPaint);
            canvas.drawLine(x, y + 150, x - 150, y, mPaint);
            canvas.drawLine(x, y - 150, x + 150, y, mPaint);
            canvas.drawLine(x, y - 150, x - 150, y, mPaint);
            return;
        }
        if (fig == Figures.TRIANGOLO) {
            canvas.drawLine(x, y - 150, x + 150, y+50, mPaint);
            canvas.drawLine(x, y -150, x - 150, y+50, mPaint);
            canvas.drawLine(x - 150, y+50, x + 150, y+50, mPaint);
        }

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

        return true;
    }

    public GeometryParameters getGeoParameters() {
        return geoParameters;
    }

    public void setGeoParameters(GeometryParameters geoParameters) {
        this.geoParameters = geoParameters;
    }

    public void setActivity(GeometryActivity activity) {
        this.activity = activity;
    }
}
