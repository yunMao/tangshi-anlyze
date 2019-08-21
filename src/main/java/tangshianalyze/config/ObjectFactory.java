package tangshianalyze.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tangshianalyze.analyze.dao.AnalyzeDao;
import tangshianalyze.analyze.dao.impl.AnalyzeDaoImpl;
import tangshianalyze.analyze.service.AnalyzeService;
import tangshianalyze.analyze.service.impl.AnalyzeServiceImpl;
import tangshianalyze.crawler.Crawler;
import tangshianalyze.crawler.common.Page;
import tangshianalyze.crawler.pipeline.ConclePipeline;
import tangshianalyze.crawler.pipeline.DataBasePipeline;
import tangshianalyze.crawler.parse.DataPageParse;
import tangshianalyze.crawler.parse.DocumentParse;
import tangshianalyze.web.WebController;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    //单例，饿汉式
    private static final ObjectFactory instance = new ObjectFactory();

    private final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
    /*
    * 存放所有的对象
    * */
    private final Map<Class,Object> Object_Map = new HashMap<>();
    private ObjectFactory(){
        //1.初始化配置对象
        initConfigProperties();

        //2.初始化数据源对象
        initDataSource();

        //3.初始化爬虫对象
        initCrawler();

        //4.Web对象
        initWebController();

        //5.对象清单打印输出
        printObjectList();
    }

    private void initWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        WebController  webController = new WebController(analyzeService);

        Object_Map.put(WebController.class,webController);

    }

    private void initConfigProperties() {
        ConfigProperties configProperties = new ConfigProperties();
        Object_Map.put(ConfigProperties.class, configProperties);
        logger.info("ConfigProperties info:\n{}",configProperties
        .toString());
    }

    private void initDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());
        Object_Map.put(DataSource.class,dataSource);
    }

    private void initCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);
        final Page page = new Page(configProperties.getCrawlerBase(), configProperties.getCrawlerPath(),
                configProperties.isCrawlerDetail());
        Crawler crawler = new Crawler();
        crawler.addParse(new DocumentParse());
        crawler.addParse(new DataPageParse());
        if (configProperties.isEnableConsole()){
            crawler.addPipeline(new ConclePipeline());
        }
        crawler.addPipeline(new DataBasePipeline(dataSource));
        crawler.addPage(page);

        Object_Map.put(Crawler.class,crawler);
    }


    public <T> T getObject(Class classz){
        if (!Object_Map.containsKey(classz)){
            throw new IllegalArgumentException("Class"+classz.getName()+"not found Object");
        }
        return (T) Object_Map.get(classz);
    }

    public static ObjectFactory getInstance(){
        return instance;
    }
    public void printObjectList(){
        logger.info("---------------------ObjectFactory List----------------------------------");
        for (Map.Entry<Class,Object> entry : Object_Map.entrySet()){
            logger.info(String.format("[%-5s]===> [%s]",entry.getKey().getCanonicalName(),
                    entry.getValue().getClass().getCanonicalName()));
        }
        logger.info("-------------------------------------------------------------------------");
    }
}
