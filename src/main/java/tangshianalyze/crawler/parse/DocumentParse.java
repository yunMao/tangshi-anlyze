package tangshianalyze.crawler.parse;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import tangshianalyze.crawler.common.Page;

import java.util.concurrent.atomic.AtomicInteger;
/**
 *链接解析
 */

public class DocumentParse implements Parse {

    @Override
    public void parse(final Page page) {
        if(page.isDetail()){
            return;
        }
        final AtomicInteger count = new AtomicInteger(0);

           HtmlPage htmlPage= page.getHtmlpage();
            htmlPage.getBody()
                   .getElementsByAttribute("div",
                           "class","typecont")
                    .forEach(div ->  {
                          DomNodeList<HtmlElement> nodeList =
                                  div.getElementsByTagName("a");
                          nodeList.forEach(aNode->{
                              String path = aNode.getAttribute("href");
                              Page subPage = new Page(
                                      page.getBase(),path,true);
                              page.getSubPage().add(subPage);
                          });
                    });

        }

}
