package javial.brain.game.com.smartboursekalantaricode.model;

public class RatingsModel {
    private int id;
    private String Source;
    private String Value;

    public RatingsModel(int id, String source, String value) {
        this.id = id;
        Source = source;
        Value = value;
    }

    public RatingsModel() {
    }

    public RatingsModel(String source, String value) {
        Source = source;
        Value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
