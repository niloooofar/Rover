package example.com.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import example.com.a2dgame.models.RoverSetting;

public class MarsView extends SurfaceView implements SurfaceHolder.Callback {

    public static final long GAME_STEP_INTERVAL = 800L;

    private RoverSetting roverSetting;
    private RoverSprite roverSprite;
    private WeirsSprite weirsSprite;
    private SoundPlayer soundPlayer;
    private int commandIndex = 0;
    private Background background;
    private Handler mainHandler;

    private volatile boolean stop = false;

    private Runnable nextPositionRunnable = new Runnable() {
        @Override
        public void run() {
            if (stop) return;
            Canvas canvas = getHolder().lockCanvas();
            update();
            draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
            mainHandler.postDelayed(this, GAME_STEP_INTERVAL);
        }
    };

    public MarsView(Context context, RoverSetting roverSetting) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.roverSetting = roverSetting;
        soundPlayer = new SoundPlayer(context);
        background = new Background();
        mainHandler = new Handler(context.getMainLooper());
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        roverSprite = new RoverSprite(getContext(), this.roverSetting.getStartPoint(), this.roverSetting.getWeirs());
        weirsSprite = new WeirsSprite(getContext(), this.roverSetting.getWeirs());
        startMoving();
    }

    private void startMoving() {
        stop = false;
        mainHandler.postDelayed(nextPositionRunnable, GAME_STEP_INTERVAL);
    }

    private void stopMoving() {
        stop = true;
        mainHandler.removeCallbacks(nextPositionRunnable);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopMoving();
    }

    public void update() {
        roverSprite.update(new RoverSprite.OnEndListener() {
            @Override
            public void onHitCorners() {
                stopMoving();
                soundPlayer.playHitSound();
                Utitlity.vibrate(getContext());
                toast(getResources().getString(R.string.hit_corners_msg));
            }

            @Override
            public void onHitWeirs() {
                stopMoving();
                soundPlayer.playHitSound();
                Utitlity.vibrate(getContext());
                toast(getResources().getString(R.string.hit_weirs_msg));
            }
        }, roverSetting.getCommand().charAt(commandIndex));

        commandIndex++;
        if (commandIndex == roverSetting.getCommand().length()) {
            stopMoving();
            toast(getResources().getString(R.string.no_more_commands));

        }
    }

    private void toast(final String showMessage) {
        Toast.makeText(getContext(), showMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            roverSprite.draw(canvas);
            weirsSprite.draw(canvas);
            background.draw(canvas);
        }
    }
}