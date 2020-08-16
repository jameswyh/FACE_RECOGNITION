package View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.eltima.components.ui.DatePicker;

import DataBase.RecordDataBase;
import Model.ResultSetTableModel;

public class HistoryRecord extends JFrame  implements ActionListener{
	
	Vector rowData = new Vector(),columnNames;
	JTable jt=null;
	JScrollPane jsp=null;
	
	JPanel jpup = new JPanel();
	JTextField keyword = new JTextField();
	JTextField datestart = new JTextField();
	JTextField dateend = new JTextField();
	JLabel lb1 = new JLabel("������");
	JLabel lb2 = new JLabel("�ӣ�");
	JLabel lb3 = new JLabel("����");
	JLabel lb4 = new JLabel("������");
	String[] s = {"ȫ��","ͨ��","����"};
	JComboBox<String> comboBoxaction = new JComboBox<String>(s);
	Date dates;
	Date datee;
	JFrame jf = new JFrame();
	JLabel lb = new JLabel();
	
	Button search = new Button("����");
	
	DatePicker datepickstart = getDatePicker();
	DatePicker datepickend = getDatePicker();
	
	RecordDataBase rdb = new RecordDataBase();
	

	public void showUI(){
		
		this.setTitle("�ÿͼ�¼");
		this.setSize(700, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		columnNames=new Vector();
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("ʱ��");
		columnNames.add("����");
		columnNames.add("����");
		
		rowData = rdb.GetRecord();
		
		ResultSetTableModel model = new ResultSetTableModel(rowData,columnNames);
		jt = new JTable(model);
		settableSize();
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		jt.setDefaultRenderer(Object.class, r);
		jt.setEnabled(false);
		
		jsp = new JScrollPane(jt);
		
		jpup.setSize(600, 100);
		jpup.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		keyword.setPreferredSize(new Dimension(50,20));
		datestart.setPreferredSize(new Dimension(100,20));
		dateend.setPreferredSize(new Dimension(100,20));
		search.setPreferredSize(new Dimension(50,30));
		search.addActionListener(this);
		
		jpup.add(lb1);
		jpup.add(keyword);
		jpup.add(lb2);
		jpup.add(datepickstart);
		jpup.add(lb3);
		jpup.add(datepickend);
		jpup.add(lb4);
		jpup.add(comboBoxaction);
		jpup.add(search);
		
		
		this.add(jpup,BorderLayout.NORTH);
		this.add(jsp,BorderLayout.CENTER);			
		}
	
	
	private static DatePicker getDatePicker() {
        final DatePicker datepick;
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date();
        Dimension dimension = new Dimension(140, 20);
        datepick = new DatePicker(date, DefaultFormat, null, dimension);
        datepick.setTimePanleVisible(true);
        return datepick;
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dates = (Date) datepickstart.getValue();
		datee = (Date) datepickend.getValue();
		if(dates.after(datee)){

			jf.setTitle("���ڴ���");
			
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			jf.setSize(300,100); 
			jf.setVisible(true);
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			
			lb.setText("����ѡ�������ʼʱ�����С�ڽ���ʱ�䣡");
			lb.setPreferredSize(new Dimension(280, 30));
			jf.add(lb);
		}
		
		else{
			rowData = rdb.GetRecords(keyword.getText(), comboBoxaction.getSelectedItem().toString(),dates,datee);
			jt.removeAll();
			jt.repaint();
			ResultSetTableModel model = new ResultSetTableModel(rowData,columnNames);
			jt.setModel(model);
			settableSize();
		}
	}
	
	public void settableSize(){
		jt.setRowHeight(100);
		jt.getColumnModel().getColumn(0).setPreferredWidth(30);
		jt.getColumnModel().getColumn(1).setPreferredWidth(30);
		jt.getColumnModel().getColumn(2).setPreferredWidth(30);
		jt.getColumnModel().getColumn(3).setPreferredWidth(60);
		jt.getColumnModel().getColumn(4).setPreferredWidth(60);
		jt.getColumnModel().getColumn(5).setPreferredWidth(30);
		
	}
	
}
