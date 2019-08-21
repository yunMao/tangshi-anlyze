package tangshianalyze.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class ConfigProperties {
    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClass;

    private Boolean enableConsole;

    public ConfigProperties() {
        InputStream inputStream = ConfigProperties.class.getClassLoader()
                .getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.crawlerBase = String.valueOf(p.get("crawler.Base"));
        this.crawlerPath = String.valueOf(p.get("crawler.Path"));
        this.crawlerDetail = Boolean.parseBoolean(
        String.valueOf(p.get("crawler.Detail"))
        );

        this.dbUsername = String.valueOf(p.get("db.Username"));
        this.dbPassword = String.valueOf(p.get("db.Password"));
        this.dbUrl= String.valueOf(p.get("db.Url"));
        this.dbDriverClass = String.valueOf(p.get("db.driver_class"));
        this.enableConsole= Boolean.valueOf(String.valueOf(p.getProperty("config.enable_consonle","false"))
        );
    }

    public String getCrawlerBase() {
        return crawlerBase;
    }

    public String getCrawlerPath() {
        return crawlerPath;
    }

    public boolean isCrawlerDetail() {
        return crawlerDetail;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbDriverClass() {
        return dbDriverClass;
    }

    public Boolean isEnableConsole() {
        return enableConsole;
    }
}
