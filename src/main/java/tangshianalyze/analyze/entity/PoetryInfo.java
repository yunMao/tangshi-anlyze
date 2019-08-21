package tangshianalyze.analyze.entity;

import lombok.Data;

@Data
public class PoetryInfo {
    /**
     * 标题
     */
    private  String title;
    /**
     * 作者
     */
    private  String author;
    /**
     * 作者所在朝代
     */
    private  String dynasty;
    /**
     * 标题
     */
    private  String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
