package tangshianalyze.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tangshianalyze.crawler.common.Page;
import tangshianalyze.crawler.pipeline.ConclePipeline;
import tangshianalyze.crawler.pipeline.Pipeline;
import tangshianalyze.crawler.parse.DataPageParse;
import tangshianalyze.crawler.parse.DocumentParse;
import tangshianalyze.crawler.parse.Parse;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Crawler {
    private final Logger logger = LoggerFactory.getLogger(Crawler.class);
    /**
     * 放置文档页面，主要是古诗的超连接
     * 放置详情页面(数据)
     *
     * 放置未被采集的页面
     * page htmlpage dataset
     */
    private final Queue<Page> DocmQueue = new LinkedBlockingQueue<>();

    /**
     * 放置详情页面(处理完成，数据在dataset中)
     */
    private final Queue<Page> detailQueue = new LinkedBlockingQueue<>();

    //古诗采集器
    private WebClient webClient;

    //所有的解析器
    private final List<Parse> parseList = new LinkedList<>();

    //所有的提取器
    private final List<Pipeline> pipelineList = new LinkedList<>();

    //线程调度器
    private ExecutorService executorService;

    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
            private AtomicInteger id = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thead-" + id.getAndIncrement());
                return thread;
            }
        });
    }

    public void start() {
        //爬取
        //解析
        //清洗
        this.executorService.submit(this::parse);
        this.executorService.submit(this::pipeline);

    }

    private void parse() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               logger.error("Parse occur exception{}.",e.getMessage());
            }
            final Page page =  this.DocmQueue.poll();
            if (page == null) {
                continue;
            }
            //base path detail htmlpage
            this.executorService.submit(() -> {
                try {
                    //采集
                    HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getURL());
                    page.setHtmlpage(htmlPage);
                    for (Parse parse : Crawler.this.parseList) {
                        parse.parse(page);
                    }
                    if (page.isDetail()) {
                        Crawler.this.detailQueue.add(page);
                    } else {
                        Iterator<Page> iterator = page.getSubPage().iterator();
                        while (iterator.hasNext()) {
                            Page subPage = iterator.next();
                            //System.out.println(page);
                            Crawler.this.DocmQueue.add(subPage);
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    logger.error("Parse task occur exception{}.",e.getMessage());
                }
            });
        }
    }

    //提取古诗文内容
    private void pipeline() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Extract occur exception{}.",e.getMessage());
            }
            final Page page = this.detailQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(() -> {
                for (Pipeline pipeline :Crawler.this.pipelineList) {
                    pipeline.pipeline(page);
                }
            });
        }
    }

    public void addPage(Page page) {
        this.DocmQueue.add(page);
    }


    public void addParse(Parse parse) {
        this.parseList.add(parse);
    }

    public void addPipeline(Pipeline pipeline){
        this.pipelineList.add(pipeline);
    }


    //爬虫线程停止
    public void stop() {
        if (this.executorService != null && !this.executorService.isShutdown()) {
           this.executorService.isShutdown();
        }
        logger.info("Crawler stopped...");
    }
    public static void main(String[] args) throws IOException {
        final Page page = new Page("https://so.gushiwen.org", "/gushi/tangshi.aspx",
                false);
        Crawler crawler = new Crawler();

        crawler.addParse(new DocumentParse());
        crawler.addParse(new DataPageParse());
        crawler.addPipeline(new ConclePipeline());

        crawler.addPage(page);

        crawler.start();
    }
}









