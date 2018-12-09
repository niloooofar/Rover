package example.com.a2dgame;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Background {

    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 20;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int xLengthInt = screenWidth / COLUMN_COUNT;
    private int yLengthInt = screenHeight / ROW_COUNT;
    private float xLength = (float) xLengthInt;
    private float yLength = (float) yLengthInt;
    private Paint paint;

    private float[] horizentalPts = {
            0.0f,
            yLength,
            (float) screenWidth,
            yLength,

            0.0f,
            2 * yLength,
            (float) screenWidth,
            2 * yLength,

            0.0f,
            3 * yLength,
            (float) screenWidth,
            3 * yLength,

            0.0f,
            4 * yLength,
            (float) screenWidth,
            4 * yLength,

            0.0f,
            5 * yLength,
            (float) screenWidth,
            5 * yLength,

            0.0f,
            6 * yLength,
            (float) screenWidth,
            6 * yLength,

            0.0f,
            7 * yLength,
            (float) screenWidth,
            7 * yLength,

            0.0f,
            8 * yLength,
            (float) screenWidth,
            8 * yLength,

            0.0f,
            9 * yLength,
            (float) screenWidth,
            9 * yLength,


            0.0f,
            10 * yLength,
            (float) screenWidth,
            10 * yLength,


            0.0f,
            11 * yLength,
            (float) screenWidth,
            11 * yLength,

            0.0f,
            12 * yLength,
            (float) screenWidth,
            12 * yLength,

            0.0f,
            13 * yLength,
            (float) screenWidth,
            13 * yLength,

            0.0f,
            14 * yLength,
            (float) screenWidth,
            14 * yLength,

            0.0f,
            15 * yLength,
            (float) screenWidth,
            15 * yLength,

            0.0f,
            16 * yLength,
            (float) screenWidth,
            16 * yLength,

            0.0f,
            17 * yLength,
            (float) screenWidth,
            17 * yLength,

            0.0f,
            18 * yLength,
            (float) screenWidth,
            18 * yLength,

            0.0f,

            19 * yLength,
            (float) screenWidth,
            19 * yLength,

            0.0f,
            20 * yLength,
            (float) screenWidth,
            20 * yLength,
    };

    private float[] verticalPts = {
            xLength,
            0.0f,
            xLength,
            (float) screenHeight,

            2 * xLength,
            0.0f,
            2 * xLength,
            (float) screenHeight,


            3 * xLength,
            0.0f,
            3 * xLength,
            (float) screenHeight,


            4 * xLength,
            0.0f,
            4 * xLength,
            (float) screenHeight,


            5 * xLength,
            0.0f,
            5 * xLength,
            (float) screenHeight,


            6 * xLength,
            0.0f,
            6 * xLength,
            (float) screenHeight,


            7 * xLength,
            0.0f,
            7 * xLength,
            (float) screenHeight,


            8 * xLength,
            0.0f,
            8 * xLength,
            (float) screenHeight,


            9 * xLength,
            0.0f,
            9 * xLength,
            (float) screenHeight,


            10 * xLength,
            0.0f,
            10 * xLength,
            (float) screenHeight,
    };

    public Background() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    public void draw(Canvas canvas) {
        canvas.drawLines(horizentalPts, paint);
        canvas.drawLines(verticalPts, paint);

    }
}
