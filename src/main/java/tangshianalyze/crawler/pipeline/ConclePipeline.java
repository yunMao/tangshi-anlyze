package tangshianalyze.crawler.pipeline;

import tangshianalyze.crawler.common.Page;

import java.util.Map;

public class ConclePipeline implements Pipeline {

    public void pipeline(final Page page) {
        Map<String , Object> data = page.getDataSet().getData();
        //存储
        System.out.println(data);
    }
}
