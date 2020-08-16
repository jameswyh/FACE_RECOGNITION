package View;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.INTER_LINEAR;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import DataBase.DeptDataBase;
import DataBase.EmployeeDataBase;
import Model.ResultSetTableModel;

import javax.swing.JPasswordField;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;


public class NewUser extends JFrame implements ActionListener{
	
	private Button button;
	private JFrame jf=new JFrame();
	private JLabel labname=new JLabel();
	private JTextField textname=new JTextField();
	private JLabel labnum=new JLabel();
	private JTextField textnum=new JTextField();
	private JLabel labdept = new JLabel();
	JComboBox<String> comboBoxdept;
	
	private JTable jt;
	
	Vector rowData = new Vector(),columnNames;
	
	EmployeeDataBase idb = new EmployeeDataBase();
	DeptDataBase ddb = new DeptDataBase();
	public static String TRAIN_PATH="TrainDatabase\\";
	static File folder = new File(TRAIN_PATH);
	
	public void showUI(JTable jt) {
		this.jt = jt;
		
		jf.setTitle("������Ա����Ϣ");
		jf.setSize(340,265);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
 
		jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		//���ø�ʽ��С		
		Dimension dim=new Dimension(50, 50);	
		Dimension dim1=new Dimension(250, 30);
		Dimension dim2=new Dimension(100, 40);
		
		labname.setText("������");
		labname.setPreferredSize(dim);
		jf.add(labname);
		
		textname.setPreferredSize(dim1);
		jf.add(textname);
		
		labnum.setText("���ţ�");
		labnum.setPreferredSize(dim);
		jf.add(labnum);
		
		textnum.setPreferredSize(dim1);
		jf.add(textnum);
		
		labdept.setText("���ţ�");
		labdept.setPreferredSize(dim);
		jf.add(labdept);
		
		comboBoxdept = new JComboBox<String>(ddb.GetDeptlist());
		comboBoxdept.setPreferredSize(dim1);
		jf.add(comboBoxdept);
		
		button=new Button("ȷ��");
		button.setPreferredSize(dim2);
		jf.add(button);
		
		jf.setVisible(true);
		
//		LoginListener ll=new LoginListener(jf,textname,textnum);
		//�Ե�ǰ������Ӽ�������
		button.addActionListener(this);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(!isChinese(textname.getText())){
			JFrame jf=new JFrame();
			jf.setTitle("����ʧ��");
			jf.setSize(340,100);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
			
			JLabel labwarning=new JLabel();
			labwarning.setText("���������Ǻ��֣�");
			jf.add(labwarning);
			labwarning.setPreferredSize(new Dimension(300, 50));
			jf.setVisible(true);	
		}
		else if(count(textnum.getText())!=3){
			JFrame jf=new JFrame();
			jf.setTitle("����ʧ��");
			jf.setSize(340,100);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
			
			JLabel labwarning=new JLabel();
			labwarning.setText("����ӦΪ3λ���֣�");
			jf.add(labwarning);
			labwarning.setPreferredSize(new Dimension(300, 50));
			jf.setVisible(true);	
		}
		else{
			EmployeeDataBase db = new EmployeeDataBase();
			String faceid = db.SelectName(null,textnum.getText());
		
			if(faceid == null) {
				int foldercount = 0;
				String faceshotPath = "TrainDatabase//"+ "s"+foldercount;
		        while(new File(faceshotPath).exists()){
		        	foldercount++;
		        	System.out.println(foldercount);
		        	faceshotPath = "TrainDatabase//"+ "s"+foldercount;
		        }
				idb.InsertUser(textnum.getText(),comboBoxdept.getSelectedItem().toString(), textname.getText(),"s"+foldercount);
				new File(faceshotPath).mkdir(); 
				JFrame jf=new JFrame();
				jf.setTitle("�����ɹ�");
				jf.setSize(340,100);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setLocationRelativeTo(null);
				jf.setResizable(false);
				
				jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
				
				JLabel labwarning=new JLabel();
				labwarning.setText("�û������ɹ����뼰ʱ¼��������");
				jf.add(labwarning);
				labwarning.setPreferredSize(new Dimension(300, 50));
				jf.setVisible(true);		
				this.jf.dispose();
				
				columnNames=new Vector();
				columnNames.add("����");
				columnNames.add("����");
				columnNames.add("����");
				columnNames.add("������");
				
				rowData = idb.GetUser();
				
				jt.removeAll();
				jt.repaint();
				ResultSetTableModel model = new ResultSetTableModel(rowData,columnNames);
				jt.setModel(model);
			}
			else{
				JFrame jf=new JFrame();
				jf.setTitle("����ʧ��");
				jf.setSize(340,100);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setLocationRelativeTo(null);
				jf.setResizable(false);
				
				jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
				
				JLabel labwarning=new JLabel();
				labwarning.setText("�û�����ʧ�ܣ��ù����Ѵ���");
				jf.add(labwarning);
				labwarning.setPreferredSize(new Dimension(300, 50));
				jf.setVisible(true);	
			}
			
		}
	}
	
	public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		return flg;
	}
	
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
 }
    private Integer count (String str) {
        int number = 0;//����  
        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if ((tmp >= '0') && (tmp <= '9')) 
                number ++;
        }
        return number;
    }
}

