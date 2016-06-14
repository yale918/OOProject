
package gameserver;
import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer {
    private static ArrayList<clientConnection> clientList;
    public static ServerSocket server;
    public static int maxNumberOfUsers = 20;
    public static UserData[] userData = new UserData[maxNumberOfUsers];
    public static int port = 8881;
    
    public int socketCount;
    
    public static int numberOfUsers = 0;
    
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
        public BufferedReader input;
        public DataOutputStream output;
        public String clientMessage;
        
        public  String currentUserID = "";
        public  String currentUserPW = "";
        public  String currentUserName = "";
        public  int currentUserIndex ;
        public  boolean threadFlag;
        
        public String authentication(String id, String pw){
            for (int i=0; i<numberOfUsers;i++){
                    if ( id.equals(userData[i].ID) && pw.equals(userData[i].PW)){
                        setCurrentUserData(userData[i].Name,userData[i].ID,userData[i].PW,i);
                        //System.out.println("["+id+"]: authencate successful");                        
                        return "YES";
                    }
            }
            //System.out.println("test NO");
            //System.out.println("["+id+"]: authencate failed"); 
            return "NO";
        }
        public void setCurrentUserData(String Name, String ID, String PW,int index){
            
            currentUserIndex = index;
            currentUserName = Name;
            currentUserID = ID;
            currentUserPW = PW;
            
        }
        
        public clientConnection(Socket socket) throws IOException{
            
            
            socketCount++;
            
            System.out.println("in socke ["+socketCount+"]");
            this.socket = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                clientMessage = input.readLine();
                System.out.println("clientMessage: "+clientMessage);
                
                if(clientMessage.equals("authencate")){
                    String id = input.readLine();
                    String pw = input.readLine();
                    String auth = "";
                    auth = authentication(id,pw);   //通過則 auth="YES" 否則 auth="NO"
                    output.writeBytes(auth+"\n");   //登入結果寫回client端 YES/NO
                    
                    if(auth.equals("YES")){
                    System.out.println("current user index: "+currentUserIndex);
                        output.writeBytes(currentUserName+"\n");
                        System.out.println("userName is: "+currentUserName);
                    }
                }
                else if(clientMessage.equals("regist")){
                    String id = input.readLine();
                    String pw = input.readLine();
                    String name = input.readLine();
                    String reg ="";
                    if(isIDExist(id))   reg="NO";  else reg="YES";
                    
                    output.writeBytes(reg+"\n");    //註冊結果寫回client端 YES/NO
                    
                    if(reg.equals("YES")){
                    System.out.println("["+socket.getRemoteSocketAddress()+"]: regist successful");
                        createAccount(id,pw,name);     
                    }
                }
                else if(clientMessage.equals("gameLobbyInitialize")){

                    while(true){
                        //System.out.println("in gameLobbyWhile()");
                         clientMessage = input.readLine();
                         if(clientMessage.equals("leaveLobby")){
                             System.out.println("["+currentUserName+"]: leaveLobby");
                             output.writeBytes(clientMessage+"\n");
                             break;
                         }
                         if(clientMessage.equals("leaveGame")){
                             System .out.println("["+currentUserName+"]: leaveGame");

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
                             sendToAll("["+currentUserName+"] says: "+clientMessage);
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
                output.writeBytes(data+"\n");     
            } catch(Exception E){
                System.out.println(E);
            }
        }
        
        public void welcomeMessage() throws IOException{
            output.writeBytes("welcome to GameServer\n");
        }
        
        public void write(String message) throws IOException{
            //System.out.println("message in write is: "+message);
            System.out.println("writeTo [ "+ currentUserName+" ]");
            output.writeBytes(message+"\n");
        }
    }
    
    public static void main(String[] args) throws Exception{
        new GameServer();
    }
    
    public static void createAccount(String ID, String PW, String Name){
        
        userData[numberOfUsers].ID=ID;
        userData[numberOfUsers].PW=ID;
        userData[numberOfUsers].Name=Name;
        
        numberOfUsers++;
         
    }
    public static void serverSocketInitializer() throws Exception{
        server = new ServerSocket(port);   System.out.println("server run on: "+port);
    }
    public static void userAccountInitializer(){
        //currentUserIndex = numberOfUsers;
        for (int i=0;i<maxNumberOfUsers;i++){
                userData[i] = new UserData();
        }
        createAccount("a","a","你好"); 
        createAccount("b","b","再元");  
        createAccount("c","c","little bear");
        createAccount("c","c","little bear");
        createAccount("c","c","little bear");
    };
    
    public static boolean isIDExist(String targetID){
        for (int i=0; i<numberOfUsers;i++)
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

      
}
