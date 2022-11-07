package task_02;

public class TestClass {

   String id;
    String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
