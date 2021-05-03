package com.fred.node.app.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
public class DBRepository {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public DBRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateDBSchema() throws IOException {
        log.info("Let's update the database if needed");

        if(!this.schemaExists())
            this.initializeDB();

        double version = this.getDBVersion();
        log.info("Databse version: "+version);
    }

    private boolean schemaExists() {
        log.info("Does the schema exist?");
        try {
            jdbcTemplate.queryForRowSet("select count(*) from dbinfo");

        } catch ( DataAccessException e) {
            log.info("No DBINFO table in the database. DB as not been initialized");
            return false;
        }
        log.info("The schema exists!");
        return true;
    }

    private double getDBVersion() {
        return jdbcTemplate.queryForObject(
                "select dbversion from dbinfo",
                (rs, rowId) -> rs.getDouble("dbversion") );

    }

    private String loadFile(String fileName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileName);

        InputStreamReader isReader = new InputStreamReader(is);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }

        return str;
    }

    private String loadFile2(String fileName) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:"+fileName);
        InputStream is = resource.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }

        return str;
    }
    private void initializeDB() {
        log.info("Let's initialize the database");

        String sql = "CREATE TABLE DBINFO(DBVERSION DOUBLE PRIMARY KEY NOT NULL);";
        jdbcTemplate.execute(sql);
        String sql2= "INSERT INTO DBINFO (dbversion) VALUES (1);";
        jdbcTemplate.execute(sql2);
        String  sql3 = "CREATE TABLE PRICES(\n" +
                "TOKEN    TEXT   NOT NULL,\n" +
                "TIME     LONG   NOT NULL,\n" +
                "PRICE    DOUBLE NOT NULL,\n" +
                "CURRENCY TEXT   NOT NULL,\n" +
                "PRIMARY KEY(TOKEN, TIME) )";
        jdbcTemplate.execute(sql3);
    }

}
