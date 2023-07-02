/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fees_report_management_system;

/**
 *
 * @author HP
 */
import java.sql.*;
public class ConnectionProvider {

    private static Connection con1;
    
    public static Connection getConnection(){


        if(con1 == null){

            try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/Fees_Report_Management";
            String userName = "root";
            String password = "Admin@123";

            con1 = DriverManager.getConnection(url, userName, password);

            } 
            catch (Exception e) {
                e.printStackTrace();
            }


        }

        return con1; 
    }
}

