package View;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DataBase.EmployeeDataBase;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EnterName extends JFrame implements ActionListener{
	
	private Button button;
	private JFrame jf=new JFrame();
	private JLabel labname=new JLabel();
	private JTextField textname=new JTextField();
	private JLabel labnum=new JLabel();
	private JTextField textnum=new JTextField();
	
	int type = 0;//1Ϊ����ͷ��2Ϊ�ļ�
	
	public void showUI(int type) {
		
		this.type = type;
		
		jf.setTitle("�����¼������Ϣ");
		jf.setSize(340,200);
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
		
		button=new Button("ȷ��");
		button.setPreferredSize(dim2);
		jf.add(button);
		
		jf.setVisible(true);
		
		button.addActionListener(this);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		EmployeeDataBase db = new EmployeeDataBase();
		String faceid = db.SelectName(textname.getText(),textnum.getText());
	
		if(faceid == null) {
			JFrame jf=new JFrame();
			jf.setTitle("��¼ʧ��");
			jf.setSize(340,100);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
			
			JLabel labwarning=new JLabel();
			labwarning.setText("��¼ʧ�ܣ������µ�¼����ϵ����Ա��");
			jf.add(labwarning);
			labwarning.setPreferredSize(new Dimension(300, 50));
				
			jf.setVisible(true);	
		}
		else
			if(type==1)
			{
				FaceRegister fr = new FaceRegister();
				fr.videoCapture(faceid,textnum.getText(),textname.getText());
				jf.dispose();
			}
			else
			{
				ImageRegister ir = new ImageRegister();
				ir.showui(faceid,textnum.getText(),textname.getText());
				jf.dispose();
			}


	}
	

}
