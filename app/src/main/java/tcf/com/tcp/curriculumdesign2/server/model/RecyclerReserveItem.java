package tcf.com.tcp.curriculumdesign2.server.model;

public class RecyclerReserveItem {
    private Reserve reserve;
    private Scheduling scheduling;

    public RecyclerReserveItem(Reserve reserve, Scheduling scheduling) {
        this.reserve = reserve;
        this.scheduling = scheduling;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }
}
