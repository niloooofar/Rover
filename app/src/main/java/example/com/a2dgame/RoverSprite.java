package example.com.a2dgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.List;

import example.com.a2dgame.models.Point;

public class RoverSprite {

    private OnEndListener onEndListener;
    private Bitmap image;
    private int x, y;
    private List<Point> weirPoints;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int xLength = screenWidth / 10;
    private int yLength = screenHeight / 20;

    public RoverSprite(Context context, Point startingPoint, List<Point> weirPoints) {
        this.weirPoints = weirPoints;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue);
        this.image = Utitlity.getResizedBitmap(bitmap, xLength, yLength);
        this.x = Integer.valueOf(startingPoint.getX());
        this.y = Integer.valueOf(startingPoint.getY());
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update(OnEndListener onEndListener, char command) {
        switch (command) {
            case 'M':
                y -= yLength;
                break;
            case 'L':
                x -= xLength;
                break;
            case 'R':
                x += xLength;
                break;
        }
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            onEndListener.onHitCorners();
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)) {
            onEndListener.onHitCorners();
        }
        for (int i = 0; i < weirPoints.size(); i++) {
            if (x == Integer.valueOf(weirPoints.get(i).getX()) && y == Integer.valueOf(weirPoints.get(i).getY())) {
                onEndListener.onHitWeirs();
            }
        }
    }

    public interface OnEndListener {
        void onHitCorners();
        void onHitWeirs();
    }
}