package example.com.a2dgame.models;

import com.google.gson.annotations.SerializedName;

public class Point {

    @SerializedName("x")
    private String x;

    @SerializedName("y")
    private String y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Point(String x, String y) {
        this.x = x;
        this.y = y;
    }
}
