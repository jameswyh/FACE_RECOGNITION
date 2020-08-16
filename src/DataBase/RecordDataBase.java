package DataBase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;




public class RecordDataBase {
    Connection con;
    PreparedStatement stmt;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/face_re?useSSL=false";
    String user = "root";
    String password = "root";
	//定义数据库需要的全局变量
	PreparedStatement ps;
	ResultSet rs;
	Vector rowData;
	
	public String InsertRecord(String workid, String dept, String name, String date, String face, String action) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            String sql = "insert into record(emworkid,rddate,rdpath,rdaction) values (?,?,?,?)";
            stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, workid);
            Timestamp dates =Timestamp.valueOf(date);
            stmt.setTimestamp(2, dates);
            stmt.setString(3, face);
            stmt.setString(4, action);
			stmt.executeUpdate();
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
            System.out.println("数据库数据成功插入！！");
        }
		return name;
    }
	
	
	public Vector GetRecord() {
		try {
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
			ps=con.prepareStatement("select record.emworkid,dpname,emname,rddate,rdpath,rdaction from record,employee,dept where record.emworkid = employee.emworkid and employee.dpid = dept.dpid");
			rs=ps.executeQuery();
			rowData = new Vector();
			while(rs.next()){
				//rowData可以存放多行
				Vector item=new Vector();
				item.add(rs.getString(1));
				item.add(rs.getString(2));
				item.add(rs.getString(3));
				item.add(rs.getString(4).substring(0, 19));
				ImageIcon face=new ImageIcon(rs.getString(5));
				item.add(face);
				item.add(rs.getString(6));
				
				rowData.add(item);
			}
			Collections.reverse(rowData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return rowData;
    }
	
	public Vector GetRecords(String keyword, String action, Date dates, Date datee) {
		try {
			if(keyword.equals(""))
				ps=con.prepareStatement("select record.emworkid,dpname,emname,rddate,rdpath,rdaction from record,employee,dept where record.emworkid = employee.emworkid and employee.dpid = dept.dpid");
			else
				ps=con.prepareStatement("select record.emworkid,dpname,emname,rddate,rdpath,rdaction from record,employee,dept where record.emworkid = employee.emworkid and employee.dpid = dept.dpid and emname like '%"+keyword+"%'");
			
			rs=ps.executeQuery();
			rowData = new Vector();
			while(rs.next()){
				Vector item=new Vector();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				if((sdf.parse(rs.getString(4).substring(0, 19))).after(dates)&&(sdf.parse(rs.getString(4).substring(0, 19))).before(datee)){
					if(!action.equals("全部")&&rs.getString(6).equals(action)){
						item.add(rs.getString(1));
						item.add(rs.getString(2));
						item.add(rs.getString(3));
						item.add(rs.getString(4).substring(0, 19));
						
						ImageIcon face=new ImageIcon(rs.getString(5));
						item.add(face);
						item.add(rs.getString(6));
						
						rowData.add(item);
					}
					else if(action.equals("全部")){
						item.add(rs.getString(1));
						item.add(rs.getString(2));
						item.add(rs.getString(3));
						item.add(rs.getString(4).substring(0, 19));
						
						ImageIcon face=new ImageIcon(rs.getString(5));
						item.add(face);
						item.add(rs.getString(6));
						
						rowData.add(item);
					}
						
				}
			}
			Collections.reverse(rowData);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return rowData;
    }
}
