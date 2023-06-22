package tcf.com.tcp.curriculumdesign2.server.model;

public class RecyclerItemDate {
    private String date;
    private String week;

    public RecyclerItemDate(String date, String week) {
        this.date = date;
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
