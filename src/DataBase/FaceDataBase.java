package DataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.FaceModel;

public class FaceDataBase {
    Connection con;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/face_re?useSSL=false";
    String user = "root";
    String password = "root";
    String name = null;
    String path = null;
    String workid = null;
    
    FaceModel fm = new FaceModel();
    
	public void DeleteFace(int no) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement1 = con.createStatement();
            String sql1 = "select facepath from face where faceno = '"+no+"'";
            ResultSet rs = statement1.executeQuery(sql1);
            while(rs.next()){
                path = rs.getString("facepath");
            }
            rs.close();
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    System.out.println("删除文件成功！");
                } else {
                    System.out.println("删除文件失败！");
                }
            }
            Statement statement2 = con.createStatement();
            String sql2 = "delete from face where faceno = '"+no+"'";
            statement2.executeUpdate(sql2);
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
            System.out.println("数据库数据成功删除！！");
        }
    }
	
	public void InsertFace(String workid, String name, String date, String path) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
//            if(!con.isClosed())
//                System.out.println("Succeeded connecting to the Database!");
            Statement statement = con.createStatement();
            String sql = "INSERT INTO face (emworkid,facedate,facepath) VALUES ('"+workid+"','"+date+"','"+path+"')";
            statement.executeUpdate(sql);
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
            System.out.println("数据库数据成功插入！！");
        }
    }
	
	public void DeleteUser(String workid) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "delete from face where emworkid = '"+workid+"'";
            statement.executeUpdate(sql);
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
            System.out.println("数据库数据成功删除！！");
        }
    }
	
	public FaceModel SelectFace(int no){
		try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            Statement statement = con.createStatement();
            String sql = "select face.emworkid,emname,facedate,facepath from face, employee where faceno = '"+no+"' and face.emworkid = employee.emworkid";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
				fm.setEmworkid(rs.getString("emworkid")); 
				fm.setEmname(rs.getString("emname"));
				fm.setFacedate(rs.getString("facedate"));
				fm.setFacepath(rs.getString("facepath"));
            }
            rs.close();
            con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return fm;
	}
	
	public FaceModel[] SelectFaces(FaceModel[] fm){
		try {
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement ps=con.prepareStatement("select * from face");
			ResultSet rs=ps.executeQuery();
			int i = 0;
			while(rs.next()){
				fm[i] = new FaceModel();
				fm[i].setFacedate(rs.getString("facedate"));
				fm[i].setFacepath(rs.getString("facepath"));
				fm[i].setFaceno(rs.getString("faceno"));
				i++;
			}
            rs.close();
            con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return fm;
	}
	
	public String[] GetDates(String datelist[]) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement psdate=con.prepareStatement("select distinct facedate from face");
            ResultSet rs=psdate.executeQuery();
			rs.last();
			datelist = new String[rs.getRow()+1];
			datelist[0]="全部";
			int i = 1;
			rs.beforeFirst();
			while(rs.next()){
				datelist[i]=rs.getString("facedate");
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
		return datelist;
    }
	
	public String[] GetDatesByName(String datelist[],String name) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement psdate=con.prepareStatement("select distinct facedate from face where emworkid = (select emworkid from employee where emname = '"+name+"')");
            ResultSet rs=psdate.executeQuery();
			rs.last();
			datelist = new String[rs.getRow()+1];
			rs.beforeFirst();
			datelist[0]="全部";
			int i = 1;
			while(rs.next()){
				datelist[i]=rs.getString("facedate");
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
		return datelist;
    }
	
	public String[] GetDatesByDept(String datelist[],String dept) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement psdate=con.prepareStatement("select distinct facedate from face, employee, dept where face.emworkid = employee.emworkid and employee.dpid = dept.dpid and dept.dpname = '"+dept+"'");
            ResultSet rs=psdate.executeQuery();
			rs.last();
			datelist = new String[rs.getRow()+1];
			rs.beforeFirst();
			datelist[0]="全部";
			int i = 1;
			while(rs.next()){
				datelist[i]=rs.getString("facedate");
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
		return datelist;
    }
	
	public FaceModel[] SelectFaceByKeyword(FaceModel[] fm, String name, String date){
		try {
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement ps;
            if(name=="全部"){
				if(date=="全部")
					ps=con.prepareStatement("select * from face");
				else
					ps=con.prepareStatement("select * from face where facedate = '"+date+"'");
			}
			else{
				if(date=="全部")
					ps=con.prepareStatement("select * from face where emworkid = (select emworkid from employee where emname = '"+name+"')");
				else
					ps=con.prepareStatement("select * from face where emworkid = (select emworkid from employee where emname = '"+name+"') and facedate = '"+date+"'");
			}
			ResultSet rs=ps.executeQuery();
			rs.last();
			fm = new FaceModel[rs.getRow()];
			int i = 0;
			rs.beforeFirst();
			while(rs.next()){
				fm[i] = new FaceModel();
				fm[i].setFacedate(rs.getString("facedate"));
				fm[i].setFacepath(rs.getString("facepath"));
				fm[i].setFaceno(rs.getString("faceno"));
				i++;
			}
			
            rs.close();
            con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return fm;
	}
	
	public FaceModel[] SelectFaceByDept(FaceModel[] fm, String dept){
		try {
			Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            PreparedStatement ps = con.prepareStatement("select * from face, employee, dept where face.emworkid = employee.emworkid and employee.dpid = dept.dpid and dept.dpname = '"+dept+"'");
			ResultSet rs=ps.executeQuery();
			rs.last();
			fm = new FaceModel[rs.getRow()];
			int i = 0;
			rs.beforeFirst();
			while(rs.next()){
				fm[i] = new FaceModel();
				fm[i].setFacedate(rs.getString("facedate"));
				fm[i].setFacepath(rs.getString("facepath"));
				fm[i].setFaceno(rs.getString("faceno"));
				i++;
			}
			
            rs.close();
            con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			System.out.println("数据库数据成功读取！！");
		}
		return fm;
	}
	
}
