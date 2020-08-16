package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class EmployeeDataBase {
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/face_re?useSSL=false";
    String user = "root";
    String password = "root";
    String name = null;
    String faceid = null;
    String workid = null;
    String dept = null;
    String blacklist = null;
    
    PreparedStatement ps;
	ResultSet rs;
    
    Vector rowData;
	
	public String GetName(String id) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from employee where emfaceid = '"+id+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                name = rs.getString("emname");
            }
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
            System.out.println("数据库数据成功获取！！");
        }
        System.out.println(name);
		return name;
    }
	
	public String Getworkid(String id) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from employee where emfaceid = '"+id+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                workid = rs.getString("emworkid");
            }
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
            System.out.println("数据库数据成功获取！！");
        }
        System.out.println(workid);
		return workid;
    }
	
	public String GetDept(String id) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select dpname from dept,employee where employee.dpid = dept.dpid and employee.emfaceid = '"+id+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                dept = rs.getString("dpname");
            }
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
            System.out.println("数据库数据成功获取！！");
        }
        System.out.println(dept);
		return dept;
    }
	
	
	public String SelectName(String name, String workid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql;
            if(name==null)
            	sql = "select emfaceid from employee where emworkid = '"+workid+"'";
            else
            	sql = "select emfaceid from employee where emworkid = '"+workid+"' and emname = '"+name+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
            	faceid = rs.getString("emfaceid");
            }
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
            System.out.println("数据库数据成功获取！！");
        }
        System.out.println(faceid);
		return faceid;
    }
	
	public void InsertUser(String workid, String dept, String name, String faceid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "INSERT INTO employee (emworkid,dpid,emname,emfaceid) VALUES ('"+workid+"',(select dpid from dept where dpname = '"+dept+"'),'"+name+"','"+faceid+"')";
            statement.executeUpdate(sql);
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
    }
	
	public void DeleteUser(String faceid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            
            String sql = "delete from employee where emfaceid = '"+faceid+"'";
            statement.executeUpdate(sql);
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
            System.out.println("数据库数据成功删除！！");
        }
    }
	
	public String GetBlackList(String workid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from employee where emworkid = '"+workid+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                blacklist = rs.getString("emlimit");
            }
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
            System.out.println("数据库数据成功获取！！");
        }
//        System.out.println(blacklist);
		return blacklist;
    }
	
	public String UpdateBlackList(String workid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select * from employee where emworkid = '"+workid+"'";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                blacklist = rs.getString("emlimit");
            }
            
            System.out.println(blacklist); 
            if(blacklist.equals("是")){
            	System.out.println("1");
            	sql = "UPDATE employee SET emlimit = '"+"否"+"' WHERE emworkid = '"+workid+"'";
            }
            else{
            	System.out.println("2");
            	sql = "UPDATE employee SET emlimit = '"+"是"+"' WHERE emworkid = '"+workid+"'";
            }
            statement.executeUpdate(sql);
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
            System.out.println("数据库数据成功更新！！");
        }
        System.out.println(blacklist);
		return blacklist;
    }
	
	public Vector GetUser() {
		try {
			rowData = new Vector();
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where employee.dpid = dept.dpid");
			rs=ps.executeQuery();
			
			while(rs.next()){
				Vector item=new Vector();
				item.add(rs.getString("emworkid"));
				item.add(rs.getString("dpname"));
				item.add(rs.getString("emname"));
				item.add(rs.getString("emlimit"));
				rowData.add(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return rowData;
	}
	
	public Vector GetUsers(boolean blacklist, String workid, String name, String dept) {
		try {
			rowData = new Vector();
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            if(dept != "全部"){
				if(!blacklist){
					if(workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where dpname = '"+dept+"' and employee.dpid = dept.dpid");
					else if(!workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
					else if(workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emname like '%"+name+"%' and dpname = '"+dept+"'");
					else if(!workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emname like '%"+name+"%' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
				}
				else{
					if(workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emlimit = '"+"是"+"' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
					else if(!workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emlimit = '"+"是"+"' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
					else if(workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emname like '%"+name+"%' and emlimit = '"+"是"+"' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
					else if(!workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emname like '%"+name+"%' and emlimit = '"+"是"+"' and dpname = '"+dept+"' and employee.dpid = dept.dpid");
				}
            }
            else{
            	if(!blacklist){
					if(workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where employee.dpid = dept.dpid");
					else if(!workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and employee.dpid = dept.dpid");
					else if(workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emname like '%"+name+"%'");
					else if(!workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emname like '%"+name+"%' and employee.dpid = dept.dpid");
				}
				else{
					if(workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emlimit = '"+"是"+"' and employee.dpid = dept.dpid");
					else if(!workid.equals("")&&name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emlimit = '"+"是"+"' and employee.dpid = dept.dpid");
					else if(workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emname like '%"+name+"%' and emlimit = '"+"是"+"' and employee.dpid = dept.dpid");
					else if(!workid.equals("")&&!name.equals(""))
						ps=con.prepareStatement("select employee.emworkid,dpname,emname,emlimit from employee,dept where emworkid like '%"+workid+"%' and emname like '%"+name+"%' and emlimit = '"+"是"+"' and employee.dpid = dept.dpid");
				}
            }
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				Vector item=new Vector();
				item.add(rs.getString("emworkid"));
				item.add(rs.getString("dpname"));
				item.add(rs.getString("emname"));
				item.add(rs.getString("emlimit"));
				rowData.add(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return rowData;
	}
	
	public String[] GetNames(String name[]) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement psname=con.prepareStatement("select distinct emname from employee");
			rs=psname.executeQuery();
			rs.last();
			name = new String[rs.getRow()+1];
			rs.beforeFirst();
			name[0]="全部";
			int i = 1;
			while(rs.next()){
				name[i]=rs.getString("emname");
				i++;
			}
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
            System.out.println("数据库数据成功获取！！");
        }
		return name;
    }
	
	public String[] GetNamesByDept(String name[], String dept) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement psname=con.prepareStatement("select distinct emname from employee,dept where employee.dpid = dept.dpid and dept.dpname = '"+dept+"'");
			rs=psname.executeQuery();
			rs.last();
			name = new String[rs.getRow()+1];
			rs.beforeFirst();
			name[0]="全部";
			int i = 1;
			while(rs.next()){
				name[i]=rs.getString("emname");
				i++;
			}
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
            System.out.println("数据库数据成功获取！！");
        }
		return name;
    }
}