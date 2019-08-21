package tangshianalyze.crawler.parse;


import com.gargoylesoftware.htmlunit.html.*;
import tangshianalyze.crawler.common.Page;

/**
 * 古诗的文档解析
 * 详情页面解析
 */
public class DataPageParse implements Parse {

    @Override
    public void parse(final Page page) {
        if(!page.isDetail()) {
            return;
        }
        HtmlPage htmlPage = page.getHtmlpage();
        HtmlElement bodyElement = htmlPage.getBody();
        /* 标题 */
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) bodyElement.getByXPath(titlePath).get(0);
        String title = titleDom.asText();

        //作者
        String authorPAth = "//div[@class='cont']/p[@class='source']/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) bodyElement.getByXPath(authorPAth).get(0);
        String author = authorDom.asText();

        //朝代
        String dynastyPAth = "//div[@class='cont']/p[@class='source']/a[1]";
        HtmlAnchor dynastyDom = (HtmlAnchor) bodyElement.getByXPath(dynastyPAth).get(0);
        String dynasty = dynastyDom.asText();

        //正文
        String contentPAth = "//div[@class='cont']/div[@class= 'contson']";
        HtmlDivision contentDom = (HtmlDivision) bodyElement.getByXPath(contentPAth).get(0);
        String content = contentDom.asText();

        page.getDataSet().putData("title",title);
        page.getDataSet().putData("author",author);
        page.getDataSet().putData("dynasty",dynasty);
        page.getDataSet().putData("content",content);
       //还可以存放更多的数据

//        PoetryInfo poetryInfo = new PoetryInfo();
//        poetryInfo.setTitle(title);
//        poetryInfo.setAuthor(author);
//        poetryInfo.setDynasty(dynasty);
//        poetryInfo.setContent(content);
//        //创建对象，将数据存放到详情页面中
//        page.getDataSet().putData("poetry",poetryInfo);
    }
}
