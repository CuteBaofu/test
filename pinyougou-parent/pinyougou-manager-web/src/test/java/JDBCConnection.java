import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/pinyougoudb?characterEncoding=utf-8", "root", "root");
            if (connection!=null){
                System.out.println("连接成功");
            }else{
                System.out.println("连接失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
