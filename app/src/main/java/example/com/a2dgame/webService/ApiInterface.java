package example.com.a2dgame.webService;

import example.com.a2dgame.models.RoverSetting;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(".")
    @FormUrlEncoded
    Call<RoverSetting> getRoverSettingInfo(@Field("rover_id") String roverId);
}
