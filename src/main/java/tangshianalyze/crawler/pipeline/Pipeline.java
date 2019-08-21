package tangshianalyze.crawler.pipeline;

import tangshianalyze.crawler.common.Page;

public interface Pipeline {
    void pipeline(Page page);
}
