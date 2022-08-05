package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        private static DBConnection dbConnection;
        private Connection connection;
        private DBConnection(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid_finder","root","password");
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static DBConnection getInstance(){
            if(dbConnection==null){
                dbConnection = new DBConnection();
            }
            return dbConnection;

        }
        public Connection  getConnection(){
            return connection;
        }
}



