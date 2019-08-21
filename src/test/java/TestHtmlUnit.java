import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import tangshianalyze.analyze.entity.PoetryInfo;

import java.io.IOException;

public class TestHtmlUnit {
    public static void main(String[] args) {
       try {
           WebClient webClient = new WebClient(BrowserVersion.CHROME);
           webClient.getOptions().setJavaScriptEnabled(false);

           HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org");
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
           PoetryInfo poetryInfo = new PoetryInfo();
           poetryInfo.setTitle(title);
           poetryInfo.setAuthor(author);
           poetryInfo.setDynasty(dynasty);
           poetryInfo.setContent(content);

           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}