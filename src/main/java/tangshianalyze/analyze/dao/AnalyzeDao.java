package tangshianalyze.analyze.dao;

import tangshianalyze.analyze.entity.PoetryInfo;
import tangshianalyze.analyze.model.AuthorWorkNumber;

import java.util.List;

public interface AnalyzeDao {
/*
* 分析唐诗中的作者的创作数量
* */
List<AuthorWorkNumber> analyzeAuthorCount();

/*
* 查询所有的诗文，提供给业务层进行分析
* */
List<PoetryInfo> queryALLpoetryInfo();



}
