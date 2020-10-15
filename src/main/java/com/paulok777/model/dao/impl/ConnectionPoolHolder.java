package com.paulok777.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileReader;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static final String DB_PROPERTIES = "C://projects/cash-register/src/main/resources/db.properties";
    private static volatile DataSource dataSource;
    private static final Logger logger = LogManager.getLogger(ConnectionPoolHolder.class);

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (FileReader reader = new FileReader(DB_PROPERTIES)) {
                        Properties p = new Properties();
                        p.load(reader);
                        BasicDataSource ds = new BasicDataSource();
                        ds.setUrl(p.getProperty("db.url"));
                        ds.setUsername(p.getProperty("db.username"));
                        ds.setPassword(p.getProperty("db.password"));
                        ds.setMinIdle(Integer.parseInt(p.getProperty("db.min.idle")));
                        ds.setMaxIdle(Integer.parseInt(p.getProperty("db.max.idle")));
                        ds.setMaxOpenPreparedStatements(Integer.parseInt(
                                p.getProperty("db.max.open.prepared.statement")));
                        ds.setDriverClassName(p.getProperty("db.driver.class.name"));
                        dataSource = ds;
                    } catch (Exception e) {
                        logger.fatal("Problem with connecting to db: {}", e.getMessage());
                        System.exit(-1);
                    }
                }
            }
        }
        return dataSource;
    }
}
