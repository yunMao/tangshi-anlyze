package tangshianalyze.web;

import com.google.gson.Gson;
import spark.*;
import tangshianalyze.analyze.model.AuthorWorkNumber;
import tangshianalyze.analyze.model.WordCount;
import tangshianalyze.analyze.service.AnalyzeService;

import java.util.List;

/**
 * Web API
 * 1.Sparkjava 框架完成web API开发
 * 2.Servlet 技术实现Web API
 * 3.java-httpd 实现Web API（纯java语言实现的web服务）
 * Socket 对HTTP协议非常了解
 * */

public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    //url:http:1//27.0.0.1:4567/analyze/author_count
    // ->/analyze/author_count
    private List<AuthorWorkNumber> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }

    // url:->http://127.0.0.1:4567/analyze/word_cloud
    //  ->/analyze/word_cloud
    private List<WordCount> analyzeWordCount() {
        return analyzeService.analyzeWordCloud();
    }
//  数据可视化
    public void launchWeb() {
        ResponseTransformer transformer = new JsonTransformer();
        //前端静态文件的目录
        //src/main/resources/static
        Spark.staticFileLocation("/static");

        //服务端接口
        Spark.get("/analyze/author_count", (request, response) -> analyzeAuthorCount(),
                transformer);
        Spark.get("/analyze/word_cloud", (request, response) ->
        analyzeWordCount(),transformer);

//        Spark.get("/crawler/stop",((request, response) ->{
//            Crawler crawler= ObjectFactory.getInstance()
//                    .getObject(Crawler.class);
//            crawler.stop();
//            return "爬虫停止";
//        }));

    }

    public static class JsonTransformer implements ResponseTransformer {
        //        Object->to String
        private Gson gson = new Gson();

        @Override
        public String render(Object o) throws Exception{
            return gson.toJson(o);
        }
    }
}



