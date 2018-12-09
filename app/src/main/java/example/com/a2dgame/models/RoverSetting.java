package example.com.a2dgame.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoverSetting {

    @SerializedName("start_point")
    @Expose
    private Point startPoint;
    @SerializedName("weirs")
    @Expose
    private List<Point> weirs;
    @SerializedName("command")
    @Expose
    private String command;

    public Point getStartPoint() {
        return startPoint;
    }

    public List<Point> getWeirs() {
        return weirs;
    }

    public String getCommand() {
        return command;
    }
}
