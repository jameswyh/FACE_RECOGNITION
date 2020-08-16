package View;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import DataBase.DeptDataBase;
import DataBase.EmployeeDataBase;
import DataBase.FaceDataBase;
import FaceRecognition.PreRecognition;
import Model.FaceModel;

public class FaceGrid extends JFrame implements MouseListener,ActionListener{
	
	public static String TRAIN_PATH="TrainDatabase\\";
	public static String RECORD_PATH="FaceRecord\\";
	public static String DOT_JPG=".jpg";
	public static String THUMBS="Thumbs.db";
	static File folder = new File(TRAIN_PATH);
	
	JPanel jpup = new JPanel();
	JPanel jp = new JPanel();
	JScrollPane jsp = null;
	
	JComboBox<String> comboBoxdept;
    JComboBox<String> comboBoxname;
    JComboBox<String> comboBoxdate;
    
    DeptDataBase ddb = new DeptDataBase();	
	FaceDataBase fdb = new FaceDataBase();
	EmployeeDataBase edb = new EmployeeDataBase();
	DeptDataBase tdb = new DeptDataBase();
	
    JLabel jls[] = null;
    
    FaceModel[] fms;
    
    //构造函数
    public void FaceGrid() {
  
    	int facecount = 0;
		int foldernum = PreRecognition.foldercount();
		List<String> fileList = null;
		for (int i = 0; i < foldernum; i++) {
			if(new File(TRAIN_PATH+"s"+Integer.toString(i)).exists()){
				fileList = PreRecognition.getFileList(TRAIN_PATH+"s"+Integer.toString(i)+"\\");
				System.out.println(TRAIN_PATH+"s"+Integer.toString(i)+"\\");
				for (int j = 0; j < fileList.size(); j++) {
					facecount++;
				}	
			}
			else{
				foldernum++;
				System.out.println(i);
			}
		}
		
		System.out.println(facecount);
		
		
	    JLabel jl[] = new JLabel[facecount];
	    String name[] = new String[PreRecognition.foldercount()+1];
	    String datelist[] = null;
	    String deptlist[] = null;
	    
	    FaceModel[] fm = new FaceModel[facecount];
	    
	    fm = fdb.SelectFaces(fm);
	    name = edb.GetNames(name);
	    datelist = fdb.GetDates(datelist);
	    deptlist = tdb.GetDepts(deptlist);
    	
        //创建组件
        for(int i = 0; i<facecount; i++){
            jl[i] = new JLabel(fm[i].getFacedate(), new ImageIcon(fm[i].getFacepath()),0);
            jl[i].setName(fm[i].getFaceno());
            jl[i].setSize(150, 150);
            jl[i].setVerticalTextPosition(JLabel.BOTTOM);
            jl[i].setHorizontalTextPosition(JLabel.CENTER);
            jl[i].addMouseListener(this);
        }
        
        //添加组件
        for(int i = 0; i<facecount; i++){
            jp.add(jl[i]);
        }
        
        Dimension dim=new Dimension(80, 30);
        Dimension dimup=new Dimension(600, 60);
        
        Label ll1 = new Label("姓名：");
        Label ll2 = new Label("日期：");
        Label ll3 = new Label("部门：");
        
        comboBoxname = new JComboBox<String>(name);
        comboBoxdate = new JComboBox<String>(datelist);
        comboBoxdept = new JComboBox<String>(deptlist);
        
        comboBoxname.setPreferredSize(new Dimension(80, 20));
        comboBoxdate.setPreferredSize(new Dimension(90, 20));
        comboBoxdept.setPreferredSize(new Dimension(80, 20));
        
        jpup.add(ll3);
        jpup.add(comboBoxdept);
        jpup.add(ll1);
        jpup.add(comboBoxname);
        jpup.add(ll2);
        jpup.add(comboBoxdate);

        comboBoxdept.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
            	if (e.getStateChange() == ItemEvent.SELECTED) {

            		String[] namelist = null;
                    String[] datelist = null;
                	
                    if(comboBoxdept.getSelectedItem()=="全部"){
                    	namelist = edb.GetNames(namelist);
                    	datelist = fdb.GetDates(datelist);
                    }
                    else{
                    	namelist = edb.GetNamesByDept(namelist,comboBoxdept.getSelectedItem().toString());
                    	datelist = fdb.GetDatesByDept(datelist,comboBoxdept.getSelectedItem().toString());
                    }
                    comboBoxname.removeAllItems();
        			comboBoxname.setModel(new DefaultComboBoxModel<>(namelist));;
                    comboBoxdate.removeAllItems();
        			comboBoxdate.setModel(new DefaultComboBoxModel<>(datelist));;
                }  
            }
        });
        comboBoxname.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
            	if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + comboBoxname.getSelectedIndex() + " = " + comboBoxname.getSelectedItem());
                    String[] datelist = null;
                    
                    if(comboBoxname.getSelectedItem()=="全部")
                    	datelist = fdb.GetDates(datelist);
                    else
                    	datelist = fdb.GetDatesByName(datelist, comboBoxname.getSelectedItem().toString());
                    	
                    comboBoxdate.removeAllItems();
        			comboBoxdate.setModel(new DefaultComboBoxModel<>(datelist));;
                }  
            }
        });
            
        // 设置默认选中的条目
//        comboBoxname.setSelectedIndex(7);

        Button bt1 = new Button("搜索");
        bt1.setPreferredSize(dim);
        bt1.addActionListener(this);
        
        jpup.add(bt1);
        jpup.setPreferredSize(dimup);
        jpup.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
             
        jp.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        
        jp.setPreferredSize(new Dimension(570,(int)(Math.ceil(jl.length/5))*180));
        jsp=new JScrollPane(jp);
        
        this.add(jpup, BorderLayout.NORTH);
        this.add(jsp, BorderLayout.CENTER);
        
        //设置窗格属性
        this.setTitle("人脸库管理");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);  //Resizable:可调整大小的
    }
    
//    public void itemStateChanged(ItemEvent e) {
//    	System.out.println(e.getID());
//        
//    }
    
    @Override
    public void mouseClicked(MouseEvent e){
    	if(e.getComponent().getName()!=null){
            System.out.println(e.getComponent().getName());
            ImageViewer iv = new ImageViewer();
            iv.imshow(Integer.parseInt(e.getComponent().getName())); 
    	}
      }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		refresh();
	}
	
	public void refresh(){
		if(comboBoxdept.getSelectedItem().toString()!="全部"&&comboBoxname.getSelectedItem().toString()=="全部"&&comboBoxdate.getSelectedItem().toString()=="全部")
			fms = fdb.SelectFaceByDept(fms,comboBoxdept.getSelectedItem().toString());
		else
			fms = fdb.SelectFaceByKeyword(fms,comboBoxname.getSelectedItem().toString(),comboBoxdate.getSelectedItem().toString());
		
		jls = new JLabel[fms.length];
    	
        //创建组件
        for(int i = 0; i<fms.length; i++){
        	jls[i] = new JLabel(fms[i].getFacedate(), new ImageIcon(fms[i].getFacepath()),0);
            jls[i].setName(fms[i].getFaceno());
            jls[i].setSize(150, 150);
            jls[i].setVerticalTextPosition(JLabel.BOTTOM);
            jls[i].setHorizontalTextPosition(JLabel.CENTER);
            jls[i].addMouseListener(this);
        }
        jp.removeAll();
        jp.repaint();
        jp.setPreferredSize(new Dimension(570,(int)(Math.ceil(jls.length/5))*180));
        //添加组件
        for(int i = 0; i<fms.length; i++){
            jp.add(jls[i]);
        }
        jp.revalidate();
	}
	
}