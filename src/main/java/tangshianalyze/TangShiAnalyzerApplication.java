package tangshianalyze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tangshianalyze.config.ObjectFactory;
import tangshianalyze.crawler.Crawler;
import tangshianalyze.web.WebController;

public class TangShiAnalyzerApplication {

    private static final Logger logger = LoggerFactory.getLogger
            (TangShiAnalyzerApplication.class);
    public static void main(String[] args) {
        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);
        //运行了web服务，提供接口
        logger.info("Web Sever launch... ");
        webController.launchWeb();

        //启动爬虫
        if (args.length==1 && args[0].equals("run-crawler")){
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            logger.info("Crawler start...");
            crawler.start();
        }

    }
}
