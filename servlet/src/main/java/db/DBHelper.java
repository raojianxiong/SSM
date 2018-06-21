package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBHelper {
    //java4interface创建表的名字
    private static final String url = "jdbc:mysql://localhost:3306/java4interface?useUnicode=true&characterEncoding=UTF-8";
    private static final String name = "com.mysql.jdbc.Driver";
    //连接数据库账号
    private static final String user="root";
    private static final String password = "root";

    private Connection conn = null;
    public PreparedStatement preparedStatement = null;
    public DBHelper(String sql){
        try{
            Class.forName(name);
            conn = DriverManager.getConnection(url,user,password);
            preparedStatement = conn.prepareStatement(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void close(){
        try{
            this.conn.close();
            this.preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
