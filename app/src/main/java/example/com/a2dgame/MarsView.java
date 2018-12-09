package example.com.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import example.com.a2dgame.models.RoverSetting;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MarsView extends SurfaceView implements SurfaceHolder.Callback {

    public static final long GAME_STEP_INTERVAL = 800L;

    private RoverSetting roverSetting;
    private RoverSprite roverSprite;
    private WeirsSprite weirsSprite;
    private SoundPlayer soundPlayer;
    private int commandIndex = 0;
    private Background background;
    private Handler mainHandler;

    private Disposable disposable;

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

        disposable = Observable.interval(GAME_STEP_INTERVAL, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Canvas canvas = holder.lockCanvas();
                        update();
                        draw(canvas);
                        holder.unlockCanvasAndPost(canvas);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        disposable.dispose();
    }

    public void update() {
        roverSprite.update(new RoverSprite.OnEndListener() {
            @Override
            public void onHitCorners() {
                soundPlayer.playHitSound();
                Utitlity.vibrate(getContext());
                showToastOnUi(getResources().getString(R.string.hit_corners_msg));
                surfaceDestroyed(getHolder());
            }

            @Override
            public void onHitWeirs() {
                soundPlayer.playHitSound();
                Utitlity.vibrate(getContext());
                showToastOnUi(getResources().getString(R.string.hit_weirs_msg));
                surfaceDestroyed(getHolder());
            }
        }, roverSetting.getCommand().charAt(commandIndex));

        commandIndex++;
        if (commandIndex == roverSetting.getCommand().length()) {
            showToastOnUi(getResources().getString(R.string.no_more_commands));
            surfaceDestroyed(getHolder());
        }
    }

    private void showToastOnUi(final String showMessage) {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), showMessage, Toast.LENGTH_LONG).show();
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
            background.draw(canvas);
        }
    }
}