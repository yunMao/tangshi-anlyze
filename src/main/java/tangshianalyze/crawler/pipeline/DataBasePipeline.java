package tangshianalyze.crawler.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tangshianalyze.crawler.common.Page;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBasePipeline implements Pipeline {

    private final Logger logger = LoggerFactory.getLogger(DataBasePipeline.class);
    private final DataSource dataSource;

    public DataBasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void pipeline(final Page page) {
        String title = (String) page.getDataSet().getData("title");
        String dynasty = (String) page.getDataSet().getData("dynasty");
        String author = (String) page.getDataSet().getData("author");
        String content = (String) page.getDataSet().getData("content");
//        修改对象

        String sql = "insert into poetry_ifo(title, dynasty, author, content) VALUES (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, dynasty);
            statement.setString(3, author);
            statement.setString(4, content);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Database insert occur exception {}.",e.getMessage());
        }
    }

}

