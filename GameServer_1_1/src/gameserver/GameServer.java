
package gameserver;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GameServer {
    private static ArrayList<clientConnection> clientList;
    public static ServerSocket server;
    public static int maxNumberOfUsers = 20;
    public static UserData[] userData = new UserData[maxNumberOfUsers];
    public static int port = 8883;
    
    JLabel connectionCounterV;
    JLabel loginCounterV;
    JLabel lobby1UserCounterV;
            
    public static int numberOfAccounts = 0;
    
    public  int currentUserIndex = 0;
    
    public int connectionCounter = 0;
    public int loginCounter = 0;
    public static int lobby1UserCounter = 0;
    
    public JFrame ServerUI = new JFrame("GameServer");
    
    public void updateUI(){
        connectionCounterV.setText(String.valueOf(connectionCounter));
        loginCounterV.setText(String.valueOf(loginCounter));
        lobby1UserCounterV.setText(String.valueOf(lobby1UserCounter));
        ServerUI.repaint();                                         
    }
    
    
    public void ServerUI(){
        
        ServerUI.setLayout(null);
        ServerUI.setVisible(true);
        ServerUI.setBounds(400,700,300,150);
        ServerUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        
   
        
        JLabel connectionCounterL = new JLabel("connectionCounter： ");
        connectionCounterV = new JLabel(String.valueOf(connectionCounter));
        
        ServerUI.add(connectionCounterL);   connectionCounterL.setBounds(10,10,150,20);        
        ServerUI.add(connectionCounterV);   connectionCounterV.setBounds(150,10,70,20);
        
        JLabel loginCounterL = new JLabel("loginCounter： ");
        loginCounterV = new JLabel(String.valueOf(loginCounter));
        
        ServerUI.add(loginCounterL);   loginCounterL.setBounds(10,30,150,20);        
        ServerUI.add(loginCounterV);   loginCounterV.setBounds(150,30,70,20);
        
        JLabel lobby1UserCounterL = new JLabel("lobby1UserCounter： ");
        lobby1UserCounterV = new JLabel(String.valueOf(lobby1UserCounter));
        
        ServerUI.add(lobby1UserCounterL);   lobby1UserCounterL.setBounds(10,50,150,20);        
        ServerUI.add(lobby1UserCounterV);   lobby1UserCounterV.setBounds(150,50,70,20);
        
                
    }
    
    public GameServer() throws Exception{
        //connectionCounter = 0;
        ServerUI();
       
        clientList = new ArrayList<clientConnection>();



        serverSocketInitializer();
        userAccountInitializer();
        
        
        Thread accept = new Thread(){
            public void run(){
                //System.out.println("in run");
                while(true){
                    try{
                        Socket socket = server.accept();            // Tcp Socket Connection *****
                        clientList.add(new clientConnection(socket) );  // MultiThread connectiond *****
                    }catch(IOException IOE){
                        //System.out.println("hello world");
                        IOE.printStackTrace();
                    }
                }
            }
        };
        //accept.setDaemon(true);
        accept.start();
        
        
    }
    public void show(String position, String message){
        System.out.println("in: "+position);
        System.out.println(message);
    }
    
    public class clientConnection {
        public Socket socket;
        public DataInputStream input;                   // Input Stream Object *****
        public DataOutputStream output;
        public String clientMessage;
        
        public  String currentUserID = "";
        public  String currentUserPW = "";
        public  String currentUserName = "";
        public  String currentUserPicture = "";
        public  int currentUserIndex = 0; 
        public  boolean threadFlag;
        
        public  UserData currentUser;
        
        
        public String authentication(String id, String pw){
            for (int i=0; i<numberOfAccounts;i++){
                    if ( id.equals(userData[i].ID) && pw.equals(userData[i].PW)){
                        currentUserIndex = i;
                        return "YES";
                    }
            }

            return "NO";
        }
        public void setCurrentUserData(String Name, String ID, String PW,  String pic){
            currentUser = new UserData(Name,ID,PW,pic);
        }
        

        public clientConnection(Socket socket) throws IOException{

            connectionCounter++;    updateUI();
            
            System.out.println("new connection from: "+socket.getRemoteSocketAddress());
            System.out.println("connectionCounter= "+connectionCounter);

            this.socket = socket;

            input = new DataInputStream(socket.getInputStream());       // Connect input stream *****
            output = new DataOutputStream(socket.getOutputStream());    // Connect output stream *****
            
            threadFlag = true;
            
            Thread socketHandler = new Thread(){
                public void run(){
                    //String currentName = 
                    try{
                        socketHandler();
                    }
                    catch(IOException IOE){
                        System.out.println(IOE);
                    }
                }
            };

            socketHandler.start();
        }
        
        public void sendCurrentUserDataToClient() throws IOException{
            this.write(currentUser.ID);
            this.write(currentUser.PW);
            this.write(currentUser.Name);
            this.write(currentUser.pictureName);
        }
        
        public void H1() {
       
        try{
            while(true && threadFlag){
                clientMessage = input.readUTF();
                System.out.println("clientMessage: "+clientMessage);
                //System.out.println("up authencate");
                
                if(clientMessage.equals("authencate")){
                    //System.out.println("down authencate");
                    String id = input.readUTF();
                    String pw = input.readUTF();
                    String auth = "";
                    auth = authentication(id,pw);   //通過則 auth="YES" 否則 auth="NO"
                    output.writeUTF(auth);   //登入結果寫回client端 YES/NO
                    
                    if(auth.equals("YES")){
                        setCurrentUserData(userData[currentUserIndex].ID,userData[currentUserIndex].PW,userData[currentUserIndex].Name, userData[currentUserIndex].pictureName);
                        loginCounter++;     updateUI();
                        
                        sendCurrentUserDataToClient();
                        
                        System.out.println(currentUser.Name+ " 登入成功");
                        //loginCounter
                    }
                }
                else if(clientMessage.equals("regist")){
                    String id = input.readUTF();
                    String pw = input.readUTF();
                    String name = input.readUTF();
                    String reg ="";
                    if(isIDExist(id))   reg="NO";  else reg="YES";
                    
                    output.writeUTF(reg);    //註冊結果寫回client端 YES/NO
                    
                    if(reg.equals("YES")){
                        System.out.println("["+socket.getRemoteSocketAddress()+"]: regist successful");
                        createAccount(id,pw,name,"default.jpg");     
                    }
                }
                else if(clientMessage.equals("gameLobbyInitialize")){
                    lobby1UserCounter++;    updateUI();
                    currentUser.lobbyUserIndex = lobby1UserCounter;

                    //write(String.valueOf(this.currentUser.lobbyUserIndex));

                    sendUserDataToAll(currentUser.Name);
                    System.out.println(currentUser.Name+ " 進入遊戲大廳");
                    while(true){
                        //System.out.println("in gameLobbyWhile()");
                         clientMessage = input.readUTF();
                         if(clientMessage.equals("leaveLobby")){
                             
                             System.out.println(currentUser.Name+ " 離開遊戲大廳");
                             
                             lobby1UserCounter--;   updateUI();
                             output.writeUTF(clientMessage);
                             break;
                         }
                         if(clientMessage.equals("leaveGame")){
                             System.out.println(currentUser.Name+ " 離開遊戲");
                             
                             connectionCounter--;    
                             loginCounter--;
                             lobby1UserCounter--;
                             updateUI(); 
                             threadFlag = false;

                             socket.close();
                             clientList.remove(this);
                             break;
                         }
                         else{
                             System.out.println("clientMessage: "+clientMessage);
                             sendToAll(currentUser.Name+"： "+clientMessage);
                         }

                    }
                }
                else if(!threadFlag){
                    System.out.println("in threadFlag statement");
                    System.out.println("threadFlag = "+threadFlag);
                    //socket.close();
                }    

            }
            /*
            if(socket.isClosed())  {
                    System.out.println("socket.isClosed()");
                    
            }
            System.out.println("leave the thread");
            */
        }
        catch(IOException IOE){            
            System.out.println(currentUser.Name+" disconnected");
            clientList.remove(this);
        }
        //catch(NullPointerException NPE){
        
        //}
        }
        
        private void socketHandler() throws IOException{
            welcomeMessage();
            H1();
   
        }

        public void welcomeMessage() throws IOException{
            output.writeUTF("歡迎光臨伺服器");
        }
        
        public void write(String message) throws IOException{
// 重要flag
System.out.println("writeTo [ "+ currentUser.Name+" ]: "+ message);
//          
            output.writeUTF(message);
        }
    }
  
    public static void main(String[] args) throws Exception{
        new GameServer();
    }
    
    public static void createAccount(String ID, String PW, String Name, String picName){
        
        userData[numberOfAccounts].ID=ID;
        userData[numberOfAccounts].PW=ID;
        userData[numberOfAccounts].Name=Name;
        userData[numberOfAccounts].pictureName=picName;
        numberOfAccounts++;
        //System.out.println("numberOfAccounts="+numberOfAccounts);
    }
    public static void serverSocketInitializer() throws Exception{
        server = new ServerSocket(port);   System.out.println("server run on: "+port);
    }
    public static void userAccountInitializer(){
        //currentUserIndex = lobby1UserCounter;
        for (int i=0;i<maxNumberOfUsers;i++){
                userData[i] = new UserData();
        }
        createAccount("a","a","釧庭","./src/Img/0.jpg"); 
        createAccount("b","b","思蓉","./src/Img/1.jpg");  
        createAccount("c","c","于萱","./src/Img/2.jpg");
        createAccount("d","d","元米","./src/Img/3.jpg");
        createAccount("e","e","岱比","./src/Img/4.jpg");
    };
    
    public static boolean isIDExist(String targetID){
        for (int i=0; i<numberOfAccounts;i++)
            if ( targetID.equals(userData[i].ID))   return true;
        return false;
    }
  
    public static void sendToOne(){
    
    }
    public static void sendToAll(String message) throws IOException{        // Broad Casting *****
       //System.out.println("in sendToAll");
        for(clientConnection client : clientList ){
            //System.out.println("in for");
            client.write(message);
        }
    }

    public static void sendUserDataToAll(String currentUserName) throws IOException{    // Broad Casting *****
        int count1=0,count2 =0;

        for(clientConnection clientTarget : clientList ){
            count1++;
            //System.out.println("for1 exec "+count1+" times");
            String strI = Integer.toString(lobby1UserCounter);
            clientTarget.write(strI);

            for(clientConnection clientData : clientList){
                count2++;
                //System.out.println("for2 exec "+count2+" times");
                if(clientTarget.currentUser.Name.equals(clientData.currentUser.Name)){    }
                else{
                    System.out.println("in else");
                    clientTarget.write(clientData.currentUser.Name);
                    clientTarget.write(clientData.currentUser.pictureName);
                }
            }
        }
    }
    
    public static void sendLeaveDataToAll() throws IOException{
        for(clientConnection client : clientList ){
            String strI = Integer.toString(lobby1UserCounter);
            client.write(strI);

            int i=0;
            for(clientConnection clientnum : clientList ){
                
                client.write(userData[i].Name);
                client.write(userData[i].pictureName);
                i++;
            }
        }
    }
}
