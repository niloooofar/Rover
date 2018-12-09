package example.com.a2dgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoverSetting {

    @SerializedName("start_point")
    private Point startPoint;

    @SerializedName("weirs")
    private List<Point> weirs;

    @SerializedName("command")
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
