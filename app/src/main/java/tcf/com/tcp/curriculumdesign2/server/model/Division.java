package tcf.com.tcp.curriculumdesign2.server.model;

public class Division extends BaseModel{
    private static final long serialVersionUID = 1L;

    private String name;
    private Long belong;
    private String notes;

    public Division(){
        super();
    }
    public Division(Long id){
        this();
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBelong() {
        return belong;
    }

    public void setBelong(Long belong) {
        this.belong = belong;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
