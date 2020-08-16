package View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DataBase.DeptDataBase;
import DataBase.FaceDataBase;
import DataBase.EmployeeDataBase;
import Model.ResultSetTableModel;


public class UserManagement extends JFrame  implements ActionListener,MouseListener{
	
	Vector rowData = new Vector(),columnNames;
	JTable jt = new JTable();
	JScrollPane jsp=null;
	
	JPanel jpup = new JPanel();
	JPanel jpdown = new JPanel();
	JTextField tf1 = new JTextField();
	JTextField tf2 = new JTextField();
	JCheckBox blacklistbox = new JCheckBox("黑名单");
	JLabel lb1 = new JLabel("工号：");
	JLabel lb2 = new JLabel("姓名：");
	JLabel lb3 = new JLabel("部门：");
	JFrame jf = new JFrame();
	JLabel lb = new JLabel();
	
	JComboBox<String> comboBoxdept;
	
	Button search = new Button("搜索");
	Button delete = new Button("删除");
	Button add = new Button("新增");
	Button black = new Button("移入黑名单");
	
	JFrame jfd = new JFrame();
	JFrame jfw = new JFrame();
	
    private JButton btconfirm = new JButton("确认");
    private JButton btcancel = new JButton("取消");
	
    EmployeeDataBase idb = new EmployeeDataBase();
    FaceDataBase fdb = new FaceDataBase();
    DeptDataBase ddb = new DeptDataBase();
		
	public void showUI(){
		
		this.setTitle("用户管理");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		columnNames=new Vector();
		columnNames.add("工号");
		columnNames.add("部门");
		columnNames.add("姓名");
		columnNames.add("黑名单");
		
		rowData = idb.GetUser();
		
		ResultSetTableModel model = new ResultSetTableModel(rowData,columnNames);
		jt.setModel(model);
		jt.setRowHeight(20);
		jt.addMouseListener(this);
		
		jsp = new JScrollPane(jt);
		jsp.setPreferredSize(new Dimension(400,500));
		jpup.setSize(400, 100);
		jpup.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		tf1.setPreferredSize(new Dimension(50,20));
		tf2.setPreferredSize(new Dimension(50,20));
		search.setPreferredSize(new Dimension(50,30));
		search.addActionListener(this);
		
		String deptlist[] = null;
		deptlist = ddb.GetDepts(deptlist);
		comboBoxdept = new JComboBox<String>(deptlist);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		jpdown.setSize(400, 100);
		jpdown.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		delete.setPreferredSize(new Dimension(50,30));
		delete.addActionListener(this);
		add.setPreferredSize(new Dimension(50,30));
		add.addActionListener(this);
		black.setPreferredSize(new Dimension(80,30));
		black.addActionListener(this);
		
		jpup.add(lb1);
		jpup.add(tf1);
		jpup.add(lb3);
		jpup.add(comboBoxdept);
		jpup.add(lb2);
		jpup.add(tf2);
		jpup.add(blacklistbox);
		jpup.add(search);
		
		jpdown.add(add);
		jpdown.add(delete);
		jpdown.add(black);
		
		this.add(jpup,BorderLayout.NORTH);
		this.add(jsp,BorderLayout.CENTER);
		this.add(jpdown,BorderLayout.SOUTH);	
	}
	
	public void refresh(){
		rowData = idb.GetUsers(blacklistbox.isSelected(),tf1.getText(),tf2.getText(),comboBoxdept.getSelectedItem().toString());
		
		jt.removeAll();
		jt.repaint();
		ResultSetTableModel model = new ResultSetTableModel(rowData,columnNames);
		jt.setModel(model);
		
		black.setLabel("移入黑名单");
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object source=e.getSource();
		if(source==search) {
			refresh();
			}
		else if(source==add) {
			NewUser nu = new NewUser();
			nu.showUI(jt);
		}
		
		else if(source==delete) {
			if(jt.getSelectedRow()==-1){
				
				JFrame jfnone = new JFrame();
				jfnone.setTitle("删除失败");
				
				jfnone.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfnone.setLocationRelativeTo(null);
				jfnone.setResizable(false);
				jfnone.setSize(300,130); 
				jfnone.setVisible(true);
				jfnone.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
				
				lb.setText("请在表中选中要删除的用户！");
				lb.setPreferredSize(new Dimension(200, 30));
				
				jfnone.add(lb);
			}
			
			else{
				
				jfd.setTitle("删除用户");
				
				jfd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfd.setLocationRelativeTo(null);
				jfd.setResizable(false);
				jfd.setSize(320,130); 
				jfd.setVisible(true);
				jfd.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
				
				lb.setText("删除后将清空该用户所有人脸，确认要删除吗？");
				lb.setPreferredSize(new Dimension(300, 30));
				
				jfd.add(lb);
				jfd.add(btconfirm);
				jfd.add(btcancel);
				
				btconfirm.addActionListener(this);	
			    btcancel.addActionListener(this);
			}
		}
		
		else if(source==btconfirm) {
			
			if(jt.getSelectedRow()!=-1){
			String faceid = idb.SelectName(jt.getValueAt(jt.getSelectedRow(), 2).toString(), jt.getValueAt(jt.getSelectedRow(), 0).toString());
			System.out.println(jt.getValueAt(jt.getSelectedRow(), 2).toString());
			String Path = "TrainDatabase//"+ faceid;
			File dir=new File(Path);
	         if(dir.exists()){
	             File[] tmp=dir.listFiles();
	             for(int i=0;i<tmp.length;i++){
	                     tmp[i].delete();
	                 }
	             dir.delete();
	             }
			idb.DeleteUser(faceid);
			fdb.DeleteUser(jt.getValueAt(jt.getSelectedRow(), 0).toString());
			
			jfw.setTitle("删除成功");
			
			jfw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jfw.setLocationRelativeTo(null);
			jfw.setResizable(false);
			jfw.setSize(300,130); 
			jfw.setVisible(true);
			jfw.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			
			lb.setText("删除用户成功！");
			lb.setPreferredSize(new Dimension(100, 30));
			
			jfw.add(lb);
			refresh();
			jfd.dispose();
			}
		}
		else if(source==btcancel) {
			jfd.dispose();
		}
		
		else if(source==black) {
			if(jt.getSelectedRow()==-1){
				
				JFrame jfnone = new JFrame();
				jfnone.setTitle("移入失败");
				
				jfnone.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfnone.setLocationRelativeTo(null);
				jfnone.setResizable(false);
				jfnone.setSize(300,130); 
				jfnone.setVisible(true);
				jfnone.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
				
				lb.setText("请在表中选中要移入黑名单的用户！");
				lb.setPreferredSize(new Dimension(250, 30));
				
				jfnone.add(lb);
			}
			else{
				idb.UpdateBlackList(jt.getValueAt(jt.getSelectedRow(), 0).toString());
				refresh();
			}

		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(jt.getSelectedRow()!=-1){
			if(jt.getValueAt(jt.getSelectedRow(), 2).equals("是"))
				black.setLabel("移出黑名单");
			else
				black.setLabel("移入黑名单");
		}

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}