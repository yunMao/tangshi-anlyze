package tangshianalyze.analyze.service;

import tangshianalyze.analyze.model.AuthorWorkNumber;
import tangshianalyze.analyze.model.WordCount;

import java.util.List;

public interface AnalyzeService {
    List<AuthorWorkNumber> analyzeAuthorCount();

    List<WordCount> analyzeWordCloud();
}
