package DBWriters.Writers;

import DBWriters.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBWriter {

    private DBWriter() {}

    public static void prepareSql(String... sqls) throws SQLException {
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                for (String sql : sqls) {
                    System.out.println("Calling: " + sql);
                    statement.executeUpdate(sql);
                }
            }
        }
    }

    public static ResultSet assertSql(String... sqls) throws SQLException {
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                for (String sql : sqls) {
                    try (ResultSet resultSet = statement.executeQuery(sql)) {
                        System.out.println("Asserting: " + sql);
                        while (resultSet.next())
                            System.out.println(resultSet.getString(2));
                        return resultSet;
                    }
                }
            }
        }
        return null;
    }

    public static void clean(String... strs) throws SQLException {
        DB.setUp();
        try (Connection con = DB.getConnection()) {
            try (Statement statement = con.createStatement()) {
                for (String str : strs) {
                    System.out.println("Clearing: " + str);
                    statement.executeUpdate(str);
                }
            }
        }
    }
}
