package javial.brain.game.com.smartboursekalantaricode.model;

public class MovieListModel {
    private int id;
    private String imdbId;
    private String Type;
    private String Title;
    private String Year;
    private String Poster;

    public MovieListModel() {}

    public MovieListModel(int id, String imdbId, String type, String title, String year, String poster) {
        this.id = id;
        this.imdbId = imdbId;
        Type = type;
        Title = title;
        Year = year;
        Poster = poster;
    }

    public MovieListModel(String imdbId, String type, String title, String year, String poster) {
        this.imdbId = imdbId;
        Type = type;
        Title = title;
        Year = year;
        Poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}
