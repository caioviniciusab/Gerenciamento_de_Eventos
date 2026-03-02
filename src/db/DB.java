package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn;

    public static Connection pegarConnection(){
        if (conn == null){
            try {
                Properties props = carregarProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }

    public static void fecharConnection(){
        if (conn != null){
            try {
                conn.close();
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static Properties carregarProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void fecharStatement(Statement st){
        if (st != null){
            try {
                st.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void fecharResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
