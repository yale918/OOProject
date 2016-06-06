
package gameplatform;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class GamePlatform extends JFrame{
    //SignIn Dialog Objects
    public JDialog SignIn;
public int currentUserIndex ;
public String currentUserID = "";
public String currentUserPW = "";
public String currentUserName = "";
public int maxNumberOfUsers = 20;
public int numberOfUsers = 0;
public UserData[] userData = new UserData[maxNumberOfUsers];
    public JDialog userDataD;
    public ImageIcon MyHeadImg = new ImageIcon(".\\Img\\MyHead.jpg");
    public ImageIcon Enemy1Img = new ImageIcon(".\\Img\\Lazy.jpg");
    public ImageIcon Enemy2Img = new ImageIcon(".\\Img\\Chrng.jpg");
    public ImageIcon Enemy3Img = new ImageIcon(".\\Img\\Sena.png");
    public TcpGameClient TGC;

    
    GamePlatform(){
    
        for (int i=0;i<maxNumberOfUsers;i++){
            userData[i] = new UserData();
        }
        createSignIn();
        //socketTester();
        try{    TGC = new TcpGameClient();    }
        catch(Exception E){     System.out.println(E);        }
        
        
                
        createAccount("a","a","主人");
        //createGamePlatform();
        
        
        
    
    }
    
    public void createGamePlatform(){
        //setVisible(true);
        
        super.setLayout(null);
        super.setTitle("遊戲大廳");
        setBounds(100,250,600,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setGamePlatformUI();
        
        JButton Back = new JButton("Back");
        add(Back);
        
        add(new JLabel("對戰角色"));
        add(new JLabel("聊天室"));


        BorderLayout bl = (BorderLayout)getLayout();
        bl.setVgap(10);
        
        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                setVisible(false);
                SignIn.setVisible(true);
            }
        });
        
        
        
    }
    public void setGamePlatformUI(){
        /*  冒險者稱謂  */
        JLabel Name = new JLabel("親愛的 【" + currentUserName +" 】你好");
        
        Name.setBounds(200,10,200,20);
        add(Name);
        /*  返回按鈕    */
        JButton Back = new JButton("返回");
        Back.setBounds(500,10,70,30);
        add(Back);
        
        
        /*  分隔線  */
        JSeparator topS = new JSeparator();
        topS.setBounds(50,50,500,10);
        add(topS);
        
        /*  房間內玩家 - 自己    */
        JLabel MyName = new JLabel(  );
        MyName.setText(currentUserName);
        MyName.setBounds(90,60,100,20);
        JButton MyHeadB = new JButton();
        MyHeadB.setIcon(MyHeadImg);
        MyHeadB.setBounds(50,90,100,100);
        add(MyName);
        add(MyHeadB);
        
        /*  房間內玩家 - 別人   */
        JLabel Enemy1 = new JLabel("Lazy");
        Enemy1.setBounds(240,60,100,20);
        add(Enemy1);
        JButton Enemy1B = new JButton();
        Enemy1B.setIcon(Enemy1Img);
        Enemy1B.setBounds(200,90,100,100);
        add(Enemy1B);
        
        JLabel Enemy2 = new JLabel("Chrng");
        Enemy2.setBounds(350,60,100,20);
        add(Enemy2);
        JButton Enemy2B = new JButton();
        Enemy2B.setIcon(Enemy2Img);
        Enemy2B.setBounds(320,90,100,100);
        add(Enemy2B);

        JLabel Enemy3 = new JLabel("Cena");
        Enemy3.setBounds(470,60,100,20);
        add(Enemy3);
        JButton Enemy3B = new JButton();
        Enemy3B.setIcon(Enemy3Img);
        Enemy3B.setBounds(440,90,100,100);
        add(Enemy3B);
        //分隔線
        JSeparator topD = new JSeparator();
        topD.setBounds(50,210,500,10);
        add(topD);
        
        /*  聊天室  */
        JTextArea chatDisplay = new JTextArea();
        chatDisplay.setBounds(50,230,500,200);
        add(chatDisplay);
        JTextField chatInput = new JTextField();
        chatInput.setBounds(50,440,500,30);
        add(chatInput);
        
        messageHandler(chatDisplay);
        
        
        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                dispose();
                Name.setText("");
                MyName.setText("");
                //setVisible(false);
                SignIn.setVisible(true);
            }
        });
        
        chatInput.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                //chatInput.setText("");
                //chatDisplay.setText(chatInput.getText());
                //String temp = chatInput.getText();
                //System.out.println(temp);
                System.out.println(chatInput.getText());
                chatDisplay.append(currentUserName+": "+chatInput.getText()+"\n");
                chatInput.setText("");
                
                
                //MyName.setText("");
                //System.out.println("hello world");
            }
        });
        
        
        
    }
    
    public void messageHandler(JTextArea display){

    }
    
    public void writeOutString(String data){
        try{    
            TGC.output.writeBytes(data+"\n");     
        } catch(Exception E){
            System.out.println(E);
        }
    }
    
    public String getInputString(){
        String inputString="";
        
        try{    
             inputString = TGC.input.readLine();     
        } catch(Exception E){
            System.out.println(E);
        }
        return inputString;
    }
    
    
    public void socketTester(){
        JDialog jd = new JDialog();        jd.setBounds(100,250,300,200);    jd.setVisible(true);
        jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jd.setLayout(null);
        JTextField jtf = new JTextField();              jtf.setBounds(50,50,100,30);     jd.add(jtf);
        //jtf.setText();
        JButton jb = new JButton("送出");                     jb.setBounds(50,80,70,30);      jd.add(jb);
        
        
         
        jb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                writeOutString(jtf.getText());
            }
        });
        
    }
    
    public void createSignIn(){
        
        SignIn = new JDialog();
        //SignIn.setLayout(null);
        SignIn.setBounds(100,100,300,150);
        SignIn.setVisible(false);
        SignIn.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        SignIn.addWindowListener(new WindowAdapter(){
            public void windowClosed(WindowEvent e){    System.out.println("jdialog window closed event received"); }
            public void windowClosing(WindowEvent e){   SignIn.dispose();   System.out.println("jdialog window closing event received");    } 
        });
        /*  objects on the JDialog  */
        JLabel ID = new JLabel("ID:  ", JLabel.RIGHT);
        JTextField ID_T = new JTextField(20); 
        JLabel PW = new JLabel("PW:  ", JLabel.RIGHT);
        JTextField PW_T = new JTextField(20);
        JButton regist = new JButton("註冊");
        JButton logIn = new JButton("登入");
        
        /*  create 3 panes  */
        //  deal pane1
        JPanel JP1 = new JPanel(new GridLayout(2,2,5,5));        
        JP1.add(ID);    JP1.add(ID_T);
        JP1.add(PW);    JP1.add(PW_T);
        //  deal pane2
        JPanel JP2 = new JPanel(new GridLayout(2,1,5,5));
        JP2.add(new JLabel("登入畫面"));
        JP2.add(new JLabel("歡迎光臨 釧庭網遊"));
        //  deal pane3
        JPanel JP3 = new JPanel();
        JP3.add(logIn);
        JP3.add(regist);
        //  deal Layout
        BorderLayout bl = (BorderLayout)SignIn.getLayout();
        bl.setVgap(10);
        SignIn.add(JP1);
        SignIn.add(JP2, BorderLayout.NORTH);
        SignIn.add(JP3, BorderLayout.SOUTH);
        
        SignIn.setVisible(true);
        
        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent AE) {
                boolean loginTag = false;
                String id = ID_T.getText();
                String pw = PW_T.getText();
                String auth ="";
                 
                writeOutString("authencate");
                writeOutString(id);
                writeOutString(pw);
                auth = getInputString();
                
                if(auth.equals("YES") ){
                    setCurrentUserData(getInputString(),id,pw);
                    System.out.println("client auth is:"+auth);
                    createUserPage();
                    loginTag = true;
                    SignIn.setVisible(false);
                    JOptionPane.showMessageDialog(null, "請按確定繼續", "登入成功",JOptionPane.INFORMATION_MESSAGE );
                }
                else 
                    JOptionPane.showMessageDialog(null, "請先註冊", "帳號不存在",JOptionPane.INFORMATION_MESSAGE );

        
            }

            private void println(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        regist.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                createRegistPage();
            }
        });
 
        
    }
    
     public void createUserPage(){
        userDataD = new JDialog();
        userDataD.setTitle("會員資料");
        userDataD.setLayout(null);
        userDataD.setBounds(50, 50, 300, 200);
        userDataD.setVisible(true);
        //JLabel userDataL = new JLabel("會員資料");
        JLabel userData_NAME = new JLabel("暱稱： ");
        JLabel userData_NAME_VALUE = new JLabel(currentUserName);

        JLabel userData_ID = new JLabel("ID： ");
        JLabel userData_ID_VALUE = new JLabel(currentUserID);
        JLabel userData_PW = new JLabel("PW： ");
        JLabel userData_PW_VALUE = new JLabel(currentUserPW);
        
        JButton userData_HEAD = new JButton();
        ImageIcon userData_HEAD_ICON = new ImageIcon();
        JButton userData_HEAD_CLICK = new JButton("上傳頭像");
        JButton Lobby = new JButton("進入遊戲大廳");
        JButton Back = new JButton("返回");
        
        userDataD.add(userData_NAME);   userData_NAME.setBounds(10,10,50,20);
        userDataD.add(userData_NAME_VALUE);   userData_NAME_VALUE.setBounds(50,10,70,20);
        userDataD.add(userData_ID);     userData_ID.setBounds(10,50,70,20);
        userDataD.add(userData_ID_VALUE);   userData_ID_VALUE.setBounds(50,50,70,20);
        userDataD.add(userData_PW);     userData_PW.setBounds(10,70,70,20);
        userDataD.add(userData_PW_VALUE);   userData_PW_VALUE.setBounds(50,70,70,20);
        
        userData_HEAD.setIcon(userData_HEAD_ICON);
        userDataD.add(userData_HEAD);
        userData_HEAD.setBounds(150, 10, 100, 70);
        
        userDataD.add(userData_HEAD_CLICK);
        userData_HEAD_CLICK.setBounds(150, 90, 100, 20);
        
        userDataD.add(Lobby);
        Lobby.setBounds(10, 130, 130, 20);
        userDataD.add(Back);
        Back.setBounds(150, 130, 70, 20);
        
        Lobby.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                setVisible(true);
                userDataD.setVisible(false);
                createGamePlatform();
                
                System.out.println("cutName="+currentUserName);
            }
        });
        
        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                userDataD.setVisible(false);
                SignIn.setVisible(true);
            }
        });
        
        
    }
    
    public void createRegistPage(){
        //regist page
        JDialog RegistPage = new JDialog();
        RegistPage.setBounds(415,100,300,200);
        RegistPage.setVisible(true);
        RegistPage.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        //  註冊頁面，輸入以下資訊
        JPanel JP1 = new JPanel(new GridLayout(2,1,5,5));
        JP1.add(new JLabel("註冊頁面, 請輸入以下資訊："));
        //  ID, PW, Name
        JPanel JP2 = new JPanel(new GridLayout(3,2,5,5));
        JLabel ID = new JLabel("ID: ",JLabel.RIGHT);
        JTextField ID_T = new JTextField(20);
        JLabel PW = new JLabel("PW: ",JLabel.RIGHT);
        JTextField PW_T = new JTextField(20);
        JLabel Name = new JLabel("Name: ",JLabel.RIGHT);
        JTextField Name_T = new JTextField(20);
        JP2.add(ID);    JP2.add(ID_T);
        JP2.add(PW);    JP2.add(PW_T);
        JP2.add(Name);  JP2.add(Name_T);
        //  OK, Cancel, Back
        JPanel JP3 = new JPanel();
        JButton OK = new JButton("OK");
        JButton Cancel = new JButton("Cancel");
        JButton Back = new JButton("Back");
        JP3.add(OK);    JP3.add(Cancel);    JP3.add(Back);
        //  set Layout
        BorderLayout bl = (BorderLayout)RegistPage.getLayout();
        bl.setVgap(10);
        RegistPage.add(JP1,BorderLayout.NORTH);
        RegistPage.add(JP2);
        RegistPage.add(JP3,BorderLayout.SOUTH);
        //  ActionListener
        OK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                String ID = ID_T.getText();
                String PW = PW_T.getText();
                String Name = Name_T.getText();
                
                String reg ="";
                writeOutString("regist");
                writeOutString(ID);
                writeOutString(PW);
                writeOutString(Name);
                reg = getInputString();
                System.out.println("reg is: " +reg);
                if(reg.equals("YES") ){
                    
                    System.out.println("client reg is:"+reg);
                    RegistPage.setVisible(false);
                    JOptionPane.showMessageDialog(null, "請按確定繼續", "註冊成功",JOptionPane.INFORMATION_MESSAGE );
                }
                else {
                    JPanel JP = new JPanel();   JOptionPane.showMessageDialog(null, "請重新輸入", "帳號已存在",JOptionPane.INFORMATION_MESSAGE );
                }
        
            }
        });
        
        Cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                
            }
        });
        
        
        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                RegistPage.setVisible(false);
            }
        });
   
    } 
    
public void createAccount(String ID, String PW, String Name){
        userData[numberOfUsers].ID=ID;
        userData[numberOfUsers].PW=ID;
        userData[numberOfUsers].Name=Name;
        
        numberOfUsers++;
         
}
 
public void setCurrentUserData(String Name, String ID, String PW){
        
        System.out.println("Name="+Name);
        currentUserName = Name;
        currentUserID = ID;
        currentUserPW = PW;
    }
    
public boolean isIDExist(String targetID){
        for (int i=0; i<numberOfUsers;i++)
            if ( targetID.equals(userData[i].ID))   return true;
        return false;
    }
  
    public static void main(String[] args) {
        new GamePlatform();
        
    }

}
