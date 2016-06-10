
package gameserver;
import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer {
    private static ArrayList<clientConnection> clientList;
    public static ServerSocket server;
    public static int maxNumberOfUsers = 20;
    public static UserData[] userData = new UserData[maxNumberOfUsers];
    public static int port = 8883;
    
    public int socketCount;
    
    public static int numberOfUsers = 0;
    public static int numberOfAccounts = 0;
    
    
    public GameServer() throws Exception{
        socketCount = 0;
        
        clientList = new ArrayList<clientConnection>();
        serverSocketInitializer();
        userAccountInitializer();
        
        Thread accept = new Thread(){
            public void run(){
                //System.out.println("in run");
                while(true){
                    try{
                        Socket socket = server.accept();
                        System.out.println("new connection from"+socket.getRemoteSocketAddress());
                        
                        
     
                        clientList.add(new clientConnection(socket) );
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
  
    
    public class clientConnection {
        public Socket socket;
        //public BufferedReader input;
        public DataInputStream input;
        public DataOutputStream output;
        public String clientMessage;
        
        public  String currentUserID = "";
        public  String currentUserPW = "";
        public  String currentUserName = "";
        public  int currentUserIndex = 0;
        public  boolean threadFlag;
        
        public String authentication(String id, String pw){
            for (int i=0; i<numberOfAccounts;i++){
                    if ( id.equals(userData[i].ID) && pw.equals(userData[i].PW)){
                        setCurrentUserData(userData[i].Name,userData[i].ID,userData[i].PW);
                        //System.out.println("["+id+"]: authencate successful");                        
                        return "YES";
                    }
            }

            return "NO";
        }
        public void setCurrentUserData(String Name, String ID, String PW){
            
            //currentUserIndex = index;
            currentUserName = Name;
            currentUserID = ID;
            currentUserPW = PW;
            
        }
        
        public clientConnection(Socket socket) throws IOException{

            socketCount++;
            //numberOfUsers++;
            System.out.println("in socke ["+socketCount+"]");
            this.socket = socket;
            input = new DataInputStream(socket.getInputStream());
            //input = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            output = new DataOutputStream(socket.getOutputStream());
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
            //socketHandler.setDaemon(true);
            socketHandler.start();
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
                        currentUserIndex++;
                        System.out.println("current user index: "+currentUserIndex);
                        output.writeUTF(currentUserName);
                        //output.writeUTF(String.valueOf(currentUserIndex));
                        System.out.println("userName is: "+currentUserName);
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
                    numberOfUsers++;
                    sendUserDataToAll();
                    while(true){
                        //System.out.println("in gameLobbyWhile()");
                         clientMessage = input.readUTF();
                         if(clientMessage.equals("leaveLobby")){
                             
                             System.out.println("["+currentUserName+"]: leaveLobby");
                             numberOfUsers--;
                             output.writeUTF(clientMessage);
                             break;
                         }
                         if(clientMessage.equals("leaveGame")){
                             System .out.println("["+currentUserName+"]: leaveGame");
                                numberOfUsers--;
                                threadFlag = false;
                                System.out.println("socket close up");
                                socketCount--;
                                socket.close();
                                System.out.println("socket close down");
                                
                                clientList.remove(this);

                             break;
                         }
                         else{
                             System.out.println("clientMessage: "+clientMessage);
                             sendToAll(currentUserName+"： "+clientMessage);
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
            System.out.println(currentUserName+" disconnected");
            clientList.remove(this);
        }
        //catch(NullPointerException NPE){
        
        //}
        }
        
        private void socketHandler() throws IOException{
            welcomeMessage();
            H1();
   
        }
        
        public void writeOutString(String data){
            try{    
                output.writeUTF(data);     
            } catch(Exception E){
                System.out.println(E);
            }
        }
        
        public void welcomeMessage() throws IOException{
            output.writeUTF("歡迎光臨伺服器");
        }
        
        public void write(String message) throws IOException{
            //System.out.println("message in write is: "+message);
            System.out.println("writeTo [ "+ currentUserName+" ]: "+ message);
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
        //currentUserIndex = numberOfUsers;
        for (int i=0;i<maxNumberOfUsers;i++){
                userData[i] = new UserData();
        }
        createAccount("a","a","釧庭","1.jpg"); 
        createAccount("b","b","思蓉","2.jpg");  
        createAccount("c","c","于萱","3.jpg");
        createAccount("d","d","元米","4.jpg");
        createAccount("e","e","岱比","5.jpg");
    };
    
    public static boolean isIDExist(String targetID){
        for (int i=0; i<numberOfAccounts;i++)
            if ( targetID.equals(userData[i].ID))   return true;
        return false;
    }
  
    public static void sendToOne(){
    
    }
    public static void sendToAll(String message) throws IOException{
       //System.out.println("in sendToAll");
        for(clientConnection client : clientList ){
            //System.out.println("in for");
            client.write(message);
        }
    }
    
    public static void sendUserDataToAll() throws IOException{

        for(clientConnection client : clientList ){
            String strI = Integer.toString(numberOfUsers);
            client.write(strI);

            int i=0;
            for(clientConnection clientnum : clientList ){
                
                client.write(userData[i].Name);
                client.write(userData[i].pictureName);
                i++;
            }
        }
    }
    
    public static void sendLeaveDataToAll() throws IOException{
        for(clientConnection client : clientList ){
            String strI = Integer.toString(numberOfUsers);
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
