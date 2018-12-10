package example.com.a2dgame;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Background {

    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 20;
    private static final int HORIZENTAL_PTS_COUNT = 76;
    private static final int VERTICAL_PTS_COUNT = 36;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int xLengthInt = screenWidth / COLUMN_COUNT;
    private int yLengthInt = screenHeight / ROW_COUNT;
    private float xLength = (float) xLengthInt;
    private float yLength = (float) yLengthInt;
    private Paint paint;
    private float[] horizentalPts = new float[HORIZENTAL_PTS_COUNT];
    private float[] verticalPts = new float[VERTICAL_PTS_COUNT];

    private void generateHorizentalPts() {
        for (int i = 0; i < HORIZENTAL_PTS_COUNT; i++) {
            if (i % 4 == 0) {
                horizentalPts[i] = 0.0f;
            } else if (i % 4 == 2) {
                horizentalPts[i] = (float) screenWidth;
            } else {
                horizentalPts[i] = (i / 4 + 1) * yLength;
            }
        }
    }

    private void generateVerticalPts() {
        for (int i = 0; i < VERTICAL_PTS_COUNT; i++) {
            if (i % 4 == 1) {
                verticalPts[i] = 0.0f;
            } else if (i % 4 == 3) {
                verticalPts[i] = (float) screenHeight;
            } else {
                verticalPts[i] = (i / 4 + 1) * xLength;
            }
        }
    }

    public Background() {
        generateHorizentalPts();
        generateVerticalPts();
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public void draw(Canvas canvas) {
        canvas.drawLines(horizentalPts, paint);
        canvas.drawLines(verticalPts, paint);
    }
}