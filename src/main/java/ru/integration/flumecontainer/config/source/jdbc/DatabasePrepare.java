package ru.integration.flumecontainer.config.source.jdbc;

import java.sql.Connection;

/**
 * Created by semya on 17.05.2017.
 */
public class DatabasePrepare {

    Connection connection;

    public DatabasePrepare(Connection connection) {
        this.connection = connection;
    }

    public void prepare(){

    }
}
