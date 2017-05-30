package ru.integration.flumecontainer.config.source.jdbc;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by semya on 17.05.2017.
 */
public class DatabasePrepare {

    private DataSource dataSource;

    private String shema_sql;

    public DatabasePrepare(DataSource dataSource, String shema_sql) {
        this.dataSource = dataSource;
        this.shema_sql = shema_sql;
    }

    public void prepare(){
        try {
            SqlFile sqlFile=new SqlFile(new File("src/test/resources/sql_init/confRegistry_mysql_create.sql"));
            sqlFile.setConnection(this.dataSource.getConnection());
            sqlFile.setAutoClose(true);
            sqlFile.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SqlToolError sqlToolError) {
            sqlToolError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
