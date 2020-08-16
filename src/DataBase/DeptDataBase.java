package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeptDataBase {
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/face_re?useSSL=false";
    String user = "root";
    String password = "root";
    
    String[] s;
    
	public String[] GetDeptlist() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from dept";
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            s = new String[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                s[i] = rs.getString("dpname");
                i++;
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }
		return s;
    }
	public String[] GetDepts(String dept[]) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement ps=con.prepareStatement("select distinct dpname from dept");
            ResultSet rs=ps.executeQuery();
			rs.last();
			dept = new String[rs.getRow()+1];
			dept[0]="全部";
			int i = 1;
			rs.beforeFirst();
			while(rs.next()){
				dept[i]=rs.getString("dpname");
				i++;
			}
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }
		return dept;
    }
	
}
