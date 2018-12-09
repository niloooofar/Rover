package example.com.a2dgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import example.com.a2dgame.models.Point;

public class WeirsSprite {

    private Bitmap image;
    private List<Point> points = new ArrayList<>();
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int xLength = screenWidth / 10;
    private int yLength = screenHeight / 20;

    public WeirsSprite(Context context, List<Point> points) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);
        this.image = Utitlity.getResizedBitmap(bitmap, xLength, yLength);
        this.points = points;
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            canvas.drawBitmap(image, Integer.valueOf(points.get(i).getX()), Integer.valueOf(points.get(i).getY()), null);
        }

    }
}