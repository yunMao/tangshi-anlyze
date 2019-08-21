package tangshianalyze.analyze.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tangshianalyze.analyze.dao.AnalyzeDao;
import tangshianalyze.analyze.entity.PoetryInfo;
import tangshianalyze.analyze.model.AuthorWorkNumber;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeDaoImpl implements AnalyzeDao {

    private final Logger logger = LoggerFactory.getLogger(AnalyzeDaoImpl.class);
    private DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorWorkNumber> analyzeAuthorCount() {
        List<AuthorWorkNumber> datas = new ArrayList<>();

//        try()自动关闭

        String sql = "select count(*) as count , author from poetry_ifo group by author;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()){
                AuthorWorkNumber authorWorkNumber = new AuthorWorkNumber();
                authorWorkNumber.setAuthor(rs.getString("author"));
                authorWorkNumber.setWorknumber(rs.getInt("count"));
                datas.add(authorWorkNumber);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}.",e.getMessage());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryALLpoetryInfo() {
        List<PoetryInfo> datas = new ArrayList<>();
        String sql = "select title, dynasty,author,content from poetry_ifo;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery();
        ) {
            while (rs.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(rs.getString("title"));
                poetryInfo.setDynasty(rs.getString("dynasty"));
                poetryInfo.setAuthor(rs.getString("author"));
                poetryInfo.setContent(rs.getString("content"));
                //ORM对象映射框架简化代码，不用重复代码 myBatis JOOQ
                datas.add(poetryInfo);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}.",e.getMessage());
        }
        return datas;
    }
}
