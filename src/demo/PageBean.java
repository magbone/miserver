package demo;



//db bean demo.
//the table name, if you want to set a value => setTableName(Type value); if you want to get value => getTableName(Type value).
public class PageBean {
    private int id;
    private String name;
    private String content;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }
}

