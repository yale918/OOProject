package finalexam;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class frame extends JFrame{
	JPanel contentpane;
	
	public frame(){//�@�}�l������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,100,600,600);
		setTitle("�s�ڰO�Ф�");
		
		contentpane = new JPanel();
		setContentPane(contentpane);
		contentpane.setLayout(null);
		
		JLabel title = new JLabel("�s��@�~�Цh�h�]�[");
		title.setBounds(225,30,200,100);
		contentpane.add(title);
		
		JButton start = new JButton("�}�l�C��");
		start.setBounds(188,100,180,50);
		contentpane.add(start);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int n = JOptionPane.showConfirmDialog(null,"�T�w�ǳƦn�F�H","���D",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE); 
				if(n==0){
				  setVisible(false);
				  JOptionPane.showConfirmDialog(null,"�w����{!","�w�w",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				  new newframe();
				}else if(n==1){
				  JOptionPane.showMessageDialog(null, "�T�T<3","��ꤣ��",JOptionPane.QUESTION_MESSAGE);
				  dispose();
				}
			}
		});
		
		JButton backgroundblue = new JButton("�I���C��:�Ŧ�");
		backgroundblue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
				contentpane.setBackground(Color.BLUE);
			}
		});
		backgroundblue.setBounds(188,170,180,30);
		contentpane.add(backgroundblue);
		
		JButton backgroundred = new JButton("�I���C��:����");
		backgroundred.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent r){
				contentpane.setBackground(Color.RED);
			}
		});
		backgroundred.setBounds(188,200,180,30);
		contentpane.add(backgroundred);
		
		JButton backgroundyellow = new JButton("�I���C��:����");
		backgroundyellow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent y){
				contentpane.setBackground(Color.YELLOW);
			}
		});
		backgroundyellow.setBounds(188,230,180,30);
		contentpane.add(backgroundyellow);
		
		JButton backgroundwhite = new JButton("�I���C��:�զ�");
		backgroundwhite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent w){
				contentpane.setBackground(Color.WHITE);
			}
		});
		backgroundwhite.setBounds(188,260,180,30);
		contentpane.add(backgroundwhite);
		
		JButton background = new JButton("���n�I���C��");
		background.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent w){
				contentpane.setBackground(null);
			}
		});
		background.setBounds(188,290,180,30);
		contentpane.add(background);
		
		JButton end = new JButton("���}�C��");
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent end){
				JOptionPane.showMessageDialog(null, "�A��~���ŦA�Ӫ���~~", "�T�T",JOptionPane.INFORMATION_MESSAGE);
				dispose();	
			}
		});
		end.setBounds(188,350,180,50);
		contentpane.add(end);
		
		setVisible(true);
	}
}

class newframe extends JFrame{
	JPanel pane;
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon back = new ImageIcon("back.jpg");
	ImageIcon[] images = new ImageIcon[5];
	JButton[] buttons = new JButton[10]; 
	JButton memorydone,memory,first,second;
	JLabel label = new JLabel();
	JLabel timecount;//���½�P�X������
	String s,ff;
	int pushnum = 0,sum=0;//�p��½�P����
	
	public newframe(){//�C������
		int x=0,y=0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,100,600,600);
		setTitle("�s�ڰO�Ф�");
		pane = new JPanel();
		setContentPane(pane);
		pane.setLayout(null);
		
		Image image = title.getImage();//���D��
		Image newimage = image.getScaledInstance(300,150,Image.SCALE_SMOOTH);
		ImageIcon newtitle = new ImageIcon(newimage);
		label.setBounds(150,20,300,150);
		label.setIcon(newtitle);
		pane.add(label);
		
		Image image0 = back.getImage();//�d���\�W����
		Image newimage0 = image0.getScaledInstance(90,120,Image.SCALE_SMOOTH);
		final ImageIcon newback = new ImageIcon(newimage0);
		
		memory = new JButton("�s���}�l");
		memory.setBounds(250,125,100,25);
		pane.add(memory);
		
		timecount = new JLabel();//�O��½�P����
		timecount.setBounds(55,100,200,100);
		pane.add(timecount);
		
		for(int i=0;i<images.length;i++){//�s�C���n�t�諸�Ϥ�
			images[i]=new ImageIcon((String.valueOf(i+1))+".jpg");
			images[i].setImage(images[i].getImage().getScaledInstance(90,120,Image.SCALE_DEFAULT));
		}
		
		memory.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sum=0;//�`½�P�����k�s
				timecount.setText("�`½�P����:"+String.valueOf(sum));
				int[] photonumber = new int[]{1,1,2,2,3,3,4,4,5,5};//�إߦ��Ʀr���e���}�C���C�ӹϪ��s��
				int n=0;
				int lastest=photonumber.length-1;//�̫�@�Ӽƪ���m
				int[] take = new int[10];//���F�Q�C���Ϥ���m���ۦP�ҥH�ΪŰ}�C���H����e�����Ʀr
				for(int i=0;i<photonumber.length;i++){//�@�Ӥ@�Ӷ]
					n =(int)(Math.random()*(lastest));//0~8���H���ü�
					take[i] = photonumber[n];//�N�H����X���Ϥ��N���̧Ǥ@�Ӥ@�ө�J�Ű}�C��
					photonumber[n] = photonumber[lastest];//��J���R�������̫᭱���ɶi�h
					lastest--;//���״�@
					buttons[i].setActionCommand(String.valueOf(take[i]));//�]�w���s�����O��ϥN���@��
					buttons[i].setToolTipText(String.valueOf(i));//���s�N��s��
					buttons[i].setIcon(newback);//�s���}�l���s�Q����d�����s����ܪ���
					buttons[i].setEnabled(true);//�d�����s����
				}

				
			}
		});

		for(int i=0;i<buttons.length;i++){//�@�Ӥ@�Ӷ]

			buttons[i] = new JButton();//���s�ƪ�
			buttons[i].setBounds(55+x*100,180+y*130,90,120);
			buttons[i].setIcon(newback);
			buttons[i].setEnabled(false);//�s���}�l���s���Q���e�d�����s�L��
			x++;
			if(i%5==4){
			y++;
			x=0;
			}
			pane.add(buttons[i]);//�N���s�[�J
			
			
		buttons[i].addActionListener(new ActionListener(){//�]�w�C�ӫ��s
			public void actionPerformed(ActionEvent e){
				pushnum++;//�n�Ϊ����U���Ƥ���ΨӤ����i�d������
				sum++;//�n��ܪ����U����
				if(pushnum==1){//�Ĥ@���������s
				
				ff = e.getActionCommand();//���o���s���O�ǥH�o���Ĥ@�����U���ϥN��
				first = (JButton)e.getSource();//���o�Ĥ@�ӫ��U�����s
				buttons[Integer.parseInt(first.getToolTipText())].setIcon(images[Integer.parseInt(ff)-1]);
				//�N���s�]�w��ܹϮ�
				}else if(pushnum==2){//�ĤG���������s
				
				s = e.getActionCommand();//���o���s���O
				second = (JButton)e.getSource();//���o�ĤG�����U�����s
				buttons[Integer.parseInt(second.getToolTipText())].setIcon(images[Integer.parseInt(s)-1]);
				//�N���s�]�w��ܹϮ�
				
				if(ff.equals(s) && first!=second){//�P�_��ӫ��s���Ϯ׬O�_�@�P
					first.setEnabled(false);//�Y�@�P�K�����s����
					second.setEnabled(false);
				}else{
					first.setIcon(newback);//�_�h�N�ܦ^�쥻���d���I����
					second.setIcon(newback);
				}
			  ff="";//�k�s
			  s="";
			  pushnum=0;
			}
				timecount.setText("�`½�P����:"+String.valueOf(sum));//����`½�P����
		  }
		 });
		}

				
	setVisible(true);
	}
}


public class playgame {

	public static void main(String[] args) {
		frame f = new frame();
	}

}
