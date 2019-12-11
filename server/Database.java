/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.server;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author hady
 */
public class Database {
    
    private Connection conn;
    private Statement statement;
    
    public Connection openConnection() throws SQLException
    {
        if (conn == null){
            
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "distributed";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "";
            try{
                Class.forName(driver);
                this.conn = (Connection)DriverManager.getConnection(url+dbName, userName, password);

                System.out.println("connection successful");
            }
            catch(ClassNotFoundException | SQLException sqle){
                System.out.println("connection faild");
            }            
        }
        return conn;
    }
    
}
