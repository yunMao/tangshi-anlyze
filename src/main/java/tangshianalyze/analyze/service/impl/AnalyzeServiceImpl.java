package tangshianalyze.analyze.service.impl;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import tangshianalyze.analyze.dao.AnalyzeDao;
import tangshianalyze.analyze.entity.PoetryInfo;
import tangshianalyze.analyze.model.AuthorWorkNumber;
import tangshianalyze.analyze.model.WordCount;
import tangshianalyze.analyze.service.AnalyzeService;


import java.util.*;

import static java.util.Collections.*;

public class AnalyzeServiceImpl implements AnalyzeService {
    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorWorkNumber> analyzeAuthorCount() {
/**
 * 此处的结果并未排序，
 * 排序方式
 *1.DAO层SQL排序
 * 2.Service层进行数据排序
 */
        List<AuthorWorkNumber> authorWorkNumbers = analyzeDao.analyzeAuthorCount();
        //此处按照作者的创作数量升序排序
        authorWorkNumbers.sort(
                Comparator.comparing(AuthorWorkNumber::getWorknumber));
        return authorWorkNumbers;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {
        //1.查询出所有的数据
        //2.取出title content
        //3.分词/过滤  /w(标点符号),null、length<2
        //4.统计k-v k是词，v是词频
        Map<String, Integer> map = new HashMap<>();
        List<PoetryInfo> poetryInfos = analyzeDao.queryALLpoetryInfo();
        for (PoetryInfo poetryInfo:poetryInfos){
            List<Term> terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();
            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());
            //过滤不需要的词
            //迭代器
            Iterator<Term> termIterator = terms.iterator();
            while (termIterator.hasNext()){
                //词性的过滤
                Term term = termIterator.next();
                if (term.getNatureStr() == null || term.getNatureStr().equals("w")){
                    termIterator.remove();
                    continue;
                }
                //词的过滤
                if (term.getRealName().length()<2){
                    termIterator.remove();
                    continue;
                }
                //统计
                Integer count = 0;
                String realName = term.getRealName();
                if (map.containsKey(realName)){
                    count = map.get(realName)+1;
                }else {
                    count = 1;
                }
                map.put(realName,count);
            }
        }
        List<WordCount> workCounts = new ArrayList<>();
        for (Map.Entry<String,Integer> entry:map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            //将分析后的结果放到list集合中
            workCounts.add(wordCount);
        }
        return workCounts;
    }
}
