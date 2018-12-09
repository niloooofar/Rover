package example.com.a2dgame.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import example.com.a2dgame.MarsView;
import example.com.a2dgame.R;
import example.com.a2dgame.Utitlity;
import example.com.a2dgame.models.RoverSetting;
import example.com.a2dgame.webService.ApiClient;
import example.com.a2dgame.webService.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private ConstraintLayout noConnectionLayout;
    private Button btnRetry;
    private Context context;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int xLength = screenWidth / 10;
    private int yLength = screenHeight / 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getInfoFromServer();
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        noConnectionLayout = findViewById(R.id.no_connection_layout);
        btnRetry = findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(this);
        context = this;
    }

    private void getInfoFromServer() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RoverSetting> call = apiService.getRoverSettingInfo("12856500");
        call.enqueue(new Callback<RoverSetting>() {
            @Override
            public void onResponse(Call<RoverSetting> call, Response<RoverSetting> response) {
                if (response.code() == 200 && response.body() != null) {
                    RoverSetting roverSetting = response.body();
                    Utitlity.convertPointPosition(roverSetting.getStartPoint(), xLength, yLength);
                    Utitlity.convertPointsPositions(roverSetting.getWeirs(), xLength, yLength);
                    setContentView(new MarsView(getApplicationContext(), roverSetting));
                }
            }

            @Override
            public void onFailure(Call<RoverSetting> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                if (!Utitlity.isNetworkConnected(context)) {
                    noConnectionLayout.setVisibility(View.VISIBLE);
                } else {
                    setContentView(R.layout.error_view);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_retry) {
            if (Utitlity.isNetworkConnected(context)) {
                setContentView(R.layout.activity_main);
                getInfoFromServer();
            }
        }
    }
}