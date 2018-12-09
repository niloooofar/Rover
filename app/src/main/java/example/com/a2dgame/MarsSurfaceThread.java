package example.com.a2dgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MarsSurfaceThread extends Thread {

    public static Canvas canvas;

    private SurfaceHolder surfaceHolder;
    private MarsView marsView;
    private boolean running;

    public MarsSurfaceThread(SurfaceHolder surfaceHolder, MarsView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.marsView = gameView;
    }

    public void setRunning(boolean isRunning) {
        this.running = isRunning;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.marsView.update();
                    this.marsView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}