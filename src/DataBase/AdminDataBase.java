package DataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class AdminDataBase {
    Connection con;
    PreparedStatement stmt;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/face_re?useSSL=false";
    String user = "root";
    String password = "root";
    boolean result;

	
	public boolean AdminLogin(String usern, String passw) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from admin where username = '"+usern+"' and password = '"+passw+"'";
            ResultSet rs = statement.executeQuery(sql);
            String un = null;
            while(rs.next()){
                un = rs.getString("username");
            }
            System.out.println(un);
            if (un!=null)
            	result = true;
            else
            	result = false;
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库查询成功！！");
        }
		return result;
    }
}
