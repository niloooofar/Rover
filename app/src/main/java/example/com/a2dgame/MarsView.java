package example.com.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import example.com.a2dgame.models.RoverSetting;

public class MarsView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private RoverSetting roverSetting;
    private RoverSprite roverSprite;
    private WeirsSprite weirsSprite;
    private SoundPlayer soundPlayer;
    private Context context;
    private int i = 0;

    public MarsView(Context context, RoverSetting roverSetting) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.roverSetting = roverSetting;
        soundPlayer = new SoundPlayer(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        roverSprite = new RoverSprite(getContext(), this.roverSetting.getStartPoint(), this.roverSetting.getWeirs());
        weirsSprite = new WeirsSprite(getContext(), this.roverSetting.getWeirs());
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        try {
            new Thread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        roverSprite.update(new RoverSprite.OnEndListener() {
            @Override
            public void onHitCorners() {
                soundPlayer.playHitSound();
                Utitlity.vibrate(context);
                showToastOnUi("Rover hitted the corners!");
                surfaceDestroyed(getHolder());
            }

            @Override
            public void onHitWeirs() {
                soundPlayer.playHitSound();
                Utitlity.vibrate(context);
                showToastOnUi("Rover hitted the weirs!");
                surfaceDestroyed(getHolder());
            }
        }, roverSetting.getCommand().charAt(i));

        i++;
        if (i == roverSetting.getCommand().length()) {
            showToastOnUi("No more commands!");
            surfaceDestroyed(getHolder());
        }
    }

    private void showToastOnUi(final String showMessage) {
        Handler mainHandler = new Handler(context.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, showMessage, Toast.LENGTH_LONG).show();
            }
        };
        mainHandler.post(myRunnable);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            roverSprite.draw(canvas);
            weirsSprite.draw(canvas);
        }
    }
}