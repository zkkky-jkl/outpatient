package tcf.com.tcp.curriculumdesign2.server.model;

public class Reserve extends BaseModel{
    private Long schedulingId;
    private int state;

    public Reserve() { super();}
    public Reserve(Long id){
        super();
        this.setId(id);
    }

    public Long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Long schedulingId) {
        this.schedulingId = schedulingId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
