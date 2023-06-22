package tcf.com.tcp.curriculumdesign2.server.model;

public class Groceries extends BaseModel{
    private static final long serialVersionUID = 1L;
    private String name;
    private String content;
    private long commentId;

    public Groceries() {
        super();
    }
    public Groceries(long id) {
        this();
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
}
