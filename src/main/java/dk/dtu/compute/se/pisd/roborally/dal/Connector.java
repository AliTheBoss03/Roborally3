package dk.dtu.compute.se.pisd.roborally.dal;

import com.mysql.cj.util.StringUtils;
import dk.dtu.compute.se.pisd.roborally.fileaccess.IOUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @author Ali Masoud
 */


    public class Connector {

        private static final String HOST = "127.0.0.1";
        private static final int PORT = 3306;
        private static final String DATABASE = "roborally";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "Ali292003";

        private static final String DELIMITER = ";";

        private Connection connection;

        Connector() {
            try {
                String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC";
                connection = DriverManager.getConnection(url, USERNAME, PASSWORD);

                createDatabaseSchema();
            } catch (SQLException e) {

                e.printStackTrace();
                // Platform.exit();
            }
        }

        private void createDatabaseSchema() {

            String createTablesStatement =
                    IOUtil.readResource("schemas/createschema.sql");

            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                for (String sql : createTablesStatement.split(DELIMITER)) {
                    if (!StringUtils.isEmptyOrWhitespaceOnly(sql)) {
                        statement.executeUpdate(sql);
                    }
                }

                statement.close();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e1) {}
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {}
            }
        }

        Connection getConnection() {
            return connection;
        }

    }


