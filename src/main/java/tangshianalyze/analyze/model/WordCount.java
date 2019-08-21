package tangshianalyze.analyze.model;

import lombok.Data;

/**
 * 将统计的古诗中出现的词汇生成词云
 */
@Data
public class WordCount {
    private String word;
    private Integer count;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
