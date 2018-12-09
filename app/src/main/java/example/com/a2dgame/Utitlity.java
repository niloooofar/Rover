package example.com.a2dgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.List;

import example.com.a2dgame.models.Point;

public class Utitlity {

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void convertPointPosition(Point point, int xLength, int yLength) {
        int yFromTop = Integer.valueOf(point.getY());
        int yFromBottom = 19 - yFromTop;
        point.setY(String.valueOf(yFromBottom * yLength));
        point.setX(String.valueOf(Integer.valueOf(point.getX()) * xLength));
    }

    public static void convertPointsPositions(List<Point> points, int xLength, int yLength) {
        for (int i = 0; i < points.size(); i++) {
            Point currentPoint = points.get(i);
            convertPointPosition(currentPoint, xLength, yLength);
        }
    }

    public static void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
}
