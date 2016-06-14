
package gameplatform;
import gameplatform.Games.*;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class GamePlatform {

    
    public JDialog SignIn;
    public JDialog userDataD;
    public ImageIcon[] icons = new ImageIcon[5];
    public TcpGameClient TGC;
    public JTextArea chatDisplay;
    public JFrame Lobby;
    public boolean threadFlag;
    public boolean exitGameFlag;
    
    public int numberOfUsersInTheRoom = 0;
    public UserData[] userData = new UserData[20];
    
    public JLabel[] userHeadName = new JLabel[5];
    public JButton[] userHeadPic = new JButton[5];
    
    public String currentUserID = "";
    public String currentUserPW = "";
    public String currentUserName = "";
    public String currentUserIndex = "";
    public String currentUserPicture = "";
    
    public UserData currentUser = new UserData();
    
    public JLabel myHeadName = new JLabel();
    public JButton myHeadPic = new JButton();
    
    GamePlatform(){
        for (int i=0; i<5 ; i++){
            userData[i] = new UserData();
            icons[i] = new ImageIcon("./src/Img/"+String.valueOf(i)+".jpg");
        }
        
        
        createSignIn();
        //socketTester();
        try{    TGC = new TcpGameClient();    }
        catch(Exception E){     System.out.println(E);        }

        //createGamePlatform();
    }
    
    public void createGamePlatform(){
        threadFlag = true;
        exitGameFlag = false;
        
        
        for(int i=0;i<userHeadName.length;i++){
            userHeadName[i] = new JLabel();
        }
        
        for(int i=0;i<userHeadPic.length;i++){
            userHeadPic[i] = new JButton();
        }
        
        
        
        Lobby = new JFrame();
        Lobby.setVisible(true);
        Lobby.setLayout(null);
        Lobby.setTitle("遊戲大廳");
        Lobby.setBounds(100,250,600,550);
        //Lobby.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        getServerData();    //numberOfUsersInTheRoom initialized
        setMyHead();
        if(numberOfUsersInTheRoom>=2)   
            setOthersHead();
 
        setGamePlatformUI();
        
        Lobby.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent WE){
                try{
                    writeOutString("leaveGame");
                    //System.out.println("leaveGame");
                    threadFlag = false;
                    exitGameFlag = true;
                    //System.out.println("threadFlag set to false;");
                    System.exit(0);
                }
                catch(Exception E1){
                    System.out.println("in E1");
                }
            }
        });
        Thread messageHandler = new Thread(){
            public void run(){
                String chatMessage = "";
                /*
                try{
                    Thread.sleep(3000);
                }
                catch(InterruptedException IE){
                    
                }
                */
                while(true && threadFlag){
                        try{
                            //System.out.println("messageHandler 等待訊息中..");
                            chatMessage = getInputString();
                            //System.out.println("in getInputString() down");
                            //System.out.println(chatMessage);
                            //if (chatMessage.equals("1")){   getInputString();   }
                            if(chatMessage.equals("2") || chatMessage.equals("3")|| chatMessage.equals("4") || chatMessage.equals("5")){
                                //System.out.println("in target1");
                                numberOfUsersInTheRoom = Integer.valueOf(chatMessage);
                                int otherUserIndex = numberOfUsersInTheRoom-2;
                                addUserUI(otherUserIndex);
                            }
                            else
                                chatDisplay.append(chatMessage+"\n");
                            
//System.out.println("receive: "+chatMessage);
                            
                        }
                        catch(Exception e){
                            System.out.println("in e");
                        }                 
                }
                if(exitGameFlag)
                    try{    
                        TGC.socket.close();
                        TGC.input.close();
                        TGC.output.close();
                    } 
                    catch(Exception E){}
            }
        };
        messageHandler.start();
        
    }
    public void getServerData(){
        
        //currentUser.lobbyUserIndex = Integer.valueOf(this.getInputString());
        numberOfUsersInTheRoom = Integer.valueOf(getInputString());
        System.out.println("numberOfUsersInTheRoom= " +numberOfUsersInTheRoom);
        
        for(int i=0; i<numberOfUsersInTheRoom-1; i++){
            userData[i].Name = getInputString();
            userData[i].pictureName = getInputString();
            System.out.println("userData["+i+"].Name="+userData[i].Name);
                
        }
    }
    public void gameFunctionThreadHandler(){
        Thread GThread = new Thread(){
        
        };
        
        GThread.start();
    }
    public void gameFunction(JButton target, String currentUserName){
        target.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                if(currentUserName.equals("釧庭"))
                    writeOutString("【釧庭】負責【SocketServer, 聊天室, 打雜】");
    
                else if(currentUserName.equals("元米")){
                    writeOutString("開始玩【男生女生配】");
                    new Thread(){
                        public void run(){
                            new oxox();
                        }
                    }.start();
                }

                else if(currentUserName.equals("于萱")){
                    writeOutString("【于萱】負責【資料整理, 介面美工, PPT, Class diagram】");
                }
                
                else if(currentUserName.equals("思蓉")){
                    writeOutString("開始玩【終極密碼】");
                    new Thread(){
                        public void run(){
                            new Secret();
                        }
                    }.start();
                }
                    
                else if(currentUserName.equals("岱比")){
                    writeOutString("開始玩【記憶遊戲】");
                    new Thread(){
                        public void run(){
                            new Memory();
                        }
                    }.start();
                    
                }

                else
                    System.out.println("比對錯誤QQ");
                
            }
        });
    }
    
    public void setMyHead(){
        myHeadName.setText(currentUser.Name);
        myHeadName.setBounds(90,60,80,20);
        myHeadPic.setIcon(new ImageIcon(currentUser.pictureName));
        myHeadPic.setBounds(50,90,80,100);
        Lobby.add(myHeadName);
        Lobby.add(myHeadPic);
        System.out.println("currentUser.lobbyUserIndex="+currentUser.lobbyUserIndex);
        gameFunction(myHeadPic,currentUser.Name);
    };
    public void setOthersHead(){
        for(int i=0; i<numberOfUsersInTheRoom-1; i++){
            //System.out.println("in target2");
            userHeadName[i].setText(userData[i].Name);
            userHeadName[i].setBounds((i+2)*90,60,80,20);
            
            userHeadPic[i].setIcon(new ImageIcon(userData[i].pictureName));
            userHeadPic[i].setBounds(50+((i+1)*100),90,80,100);
            Lobby.add(userHeadName[i]);
            Lobby.add(userHeadPic[i]);
            gameFunction(userHeadPic[i],userData[i].Name);
        }
        
        Lobby.repaint();
    }
    
    public void addUserUI(int index){
        String temp = "";
        System.out.println("numberOfUsersInTheRoom="+numberOfUsersInTheRoom);
        for(int i=0; i<numberOfUsersInTheRoom-1; i++){
            userData[index].Name = getInputString();
            userData[index].pictureName = getInputString();
            System.out.println("userData["+i+"].Name= "+userData[index].Name);
        }
        userHeadName[index].setText(userData[index].Name);
        userHeadName[index].setBounds((index+2)*90,60,80,20);
            
        userHeadPic[index].setIcon(new ImageIcon(userData[index].pictureName));
        userHeadPic[index].setBounds(50+((index+1)*100),90,80,100);
        Lobby.add(userHeadName[index]);
        Lobby.add(userHeadPic[index]);
        
        gameFunction(userHeadPic[index],userData[index].Name);
        
        Lobby.repaint();
        
    }

    public void setGamePlatformUI(){
        /*  冒險者稱謂  */
        JLabel Name = new JLabel("親愛的 【" + currentUser.Name +" 】你好");
        
        Name.setBounds(200,10,200,20);
        Lobby.add(Name);
        /*  返回按鈕    */
        JButton Back = new JButton("返回");
        Back.setBounds(500,10,70,30);
        Lobby.add(Back);
        
        
        /*  分隔線  */
        JSeparator topS = new JSeparator();
        topS.setBounds(50,50,500,10);
        Lobby.add(topS);
   
        
        //分隔線
        JSeparator topD = new JSeparator();
        topD.setBounds(50,210,500,10);
        Lobby.add(topD);
        
        /*  聊天室  */
        chatDisplay = new JTextArea();
        chatDisplay.setBounds(50,230,500,200);
        Lobby.add(chatDisplay);
        JTextField chatInput = new JTextField();
        chatInput.setBounds(50,440,500,30);
        
        JPanel painel = new JPanel(null);
        painel.add(chatDisplay);
        
        
        JScrollPane scroll = new JScrollPane (chatDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(50,230,500,200);
        Lobby.add(scroll);
        Lobby.add(chatInput);

        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){              
                writeOutString("leaveLobby");
                
                threadFlag = false;
                Lobby.dispose();
                
                userDataD.setVisible(true);
           }
        });
        
        chatInput.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){
                writeOutString(chatInput.getText());
                chatInput.setText("");
            }
        });
        
        
        
    }
        
    public void writeOutString(String data){
        try{    
            TGC.output.writeUTF(data);     
        } catch(Exception E){
            System.out.println(E);
        }
    }
    
    public String getInputString(){
        String inputString="";
        
        try{    
             inputString = TGC.input.readUTF();     
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

                    getCurrentUserDatafromServer();
                    
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
        
        SignIn.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent WE){
                System.out.println("in SignIn close event");
                try{
                    writeOutString("leaveGame");
                    //System.out.println("leaveGame");
                    threadFlag = false;
                    //System.out.println("threadFlag set to false;");
                    System.exit(0);
                }
                catch(Exception E1){
                    System.out.println("in E1");
                }
                
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
        JLabel userData_NAME_VALUE = new JLabel(currentUser.Name);

        JLabel userData_ID = new JLabel("ID： ");
        JLabel userData_ID_VALUE = new JLabel(currentUser.ID);
        JLabel userData_PW = new JLabel("PW： ");
        JLabel userData_PW_VALUE = new JLabel(currentUser.PW);
        
        JLabel numberOfUsersInTheRoomL = new JLabel("登入人數： ");
        JLabel numberOfUsersInTheRoomV = new JLabel("999");
        
        
        JButton userData_HEAD = new JButton();
        //userData_HEAD.setIcon(MyHeadImg);
        ImageIcon userData_HEAD_ICON = new ImageIcon("./src/Img/default.jpg");
        
        JButton userData_HEAD_CLICK = new JButton("上傳頭像");
        
        JButton Lobby = new JButton("進入遊戲大廳");
        JButton Back = new JButton("返回");
        
        userDataD.add(userData_NAME);   userData_NAME.setBounds(10,10,50,20);
        userDataD.add(userData_NAME_VALUE);   userData_NAME_VALUE.setBounds(50,10,70,20);
        userDataD.add(userData_ID);     userData_ID.setBounds(10,40,70,20);
        userDataD.add(userData_ID_VALUE);   userData_ID_VALUE.setBounds(50,40,70,20);
        userDataD.add(userData_PW);     userData_PW.setBounds(10,60,70,20);
        userDataD.add(userData_PW_VALUE);   userData_PW_VALUE.setBounds(50,60,70,20);
        userDataD.add(numberOfUsersInTheRoomL);     numberOfUsersInTheRoomL.setBounds(10,90,100,20);
        userDataD.add(numberOfUsersInTheRoomV);   numberOfUsersInTheRoomV.setBounds(80,90,100,20);
        
        
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
                //messageHandlerFlag = true;
                //Lobby.setVisible(true);
                userDataD.setVisible(false);
                writeOutString("gameLobbyInitialize");
                createGamePlatform();
                //System.out.println("cutName="+currentUserName);
            }
        });
        
        Back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent AE){

                userDataD.setVisible(false);
                SignIn.setVisible(true);
            }
        });
        
        userDataD.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent WE){
                //System.out.println("in userDataD close event");
                try{
                    writeOutString("leaveGame");
                    //System.out.println("leaveGame");
                    threadFlag = false;
                    //System.out.println("threadFlag set to false;");
                    System.exit(0);
                }
                catch(Exception E1){
                    System.out.println("in E1");
                }
                
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
 
    
    public void getCurrentUserDatafromServer(){
        currentUser.ID = getInputString();
        currentUser.PW = getInputString();
        currentUser.Name = getInputString();
        currentUser.pictureName = getInputString();
    }

    public static void main(String[] args) {
        new GamePlatform();
        
    }

}
