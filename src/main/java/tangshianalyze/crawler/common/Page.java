package tangshianalyze.crawler.common;


import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class Page {

    /**
     * 数据网站的根地址
     */
    private final String base;

    /**
     * 具体网页的路径
     */
    private final String path;

    /*网页DOM对象*/
    private HtmlPage htmlpage;

    /*是否是详情页
     * */
    private final boolean detail;

    /*子页面对象结合*/
    private Set<Page> subPage = new HashSet<>();

    /**
     * 数据对象
     */
    private DataSet dataSet = new DataSet();

    //url的路径
    public String getURL() {
        return this.base + this.path;
    }

    public Page(String base, String path, boolean detail) {
        this.base = base;
        this.path = path;
        this.detail = detail;
    }
        public String getBase() {
        return base;
    }

    public String getPath() {
        return path;
    }

    public HtmlPage getHtmlpage() {
        return htmlpage;
    }

    public boolean isDetail() {
        return detail;
    }

    public Set<Page> getSubPage() {
        return subPage;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setHtmlpage(HtmlPage htmlpage) {
        this.htmlpage = htmlpage;
    }

    public void setSubPage(Set<Page> subPage) {
        this.subPage = subPage;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}



