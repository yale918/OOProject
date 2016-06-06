
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
    
    
    public static int numberOfUsers = 0;
    
    
    

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
        createAccount("a","a","owner"); createAccount("b","b","maid");  createAccount("c","c","little bear");
    };
    //public static void acceptSocketThread(){};
    
    
    
    
    public static boolean isIDExist(String targetID){
        for (int i=0; i<numberOfUsers;i++)
            if ( targetID.equals(userData[i].ID))   return true;
        return false;
    }
    
    
    public GameServer() throws Exception{
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
                        IOE.printStackTrace();
                    }
                }
            }
        };
        //accept.setDaemon(true);
        accept.start();
        
    }
    
    public static void main(String[] args) throws Exception{
        new GameServer();
        
        
        
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
        
        public String authentication(String id, String pw){
            for (int i=0; i<numberOfUsers;i++){
                    if ( id.equals(userData[i].ID) && pw.equals(userData[i].PW)){
                        setCurrentUserData(userData[i].Name,userData[i].ID,userData[i].PW,i);
                        System.out.println("["+id+"]: authencate successful");                        
                        return "YES";
                    }
            }
            //System.out.println("test NO");
            System.out.println("["+id+"]: authencate failed"); 
            return "NO";
        }
        public void setCurrentUserData(String Name, String ID, String PW,int index){
            
            currentUserIndex = index;
            currentUserName = Name;
            currentUserID = ID;
            currentUserPW = PW;
            
        }
        
        public clientConnection(Socket socket) throws IOException{
            this.socket = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            
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
            /*
            Thread messageHandling = new Thread(){
                public void run(){
                    while(true){
                        try{
                            clientMessage = input.readLine();
                            System.out.println("clientMessage: " + clientMessage);
                        }catch(Exception E){
                        }
                    }
                }
            };
            //messageHandling.setDaemon(true);
            messageHandling.start();
            */    
        }
        
   
        public void H1() throws IOException{
            while(true){
                clientMessage = input.readLine();
                System.out.println("clientMessage: "+clientMessage);
                if(clientMessage.equals("authencate")){
                    String id = input.readLine();
                    String pw = input.readLine();
                    String auth = "";
                    auth = authentication(id,pw);
                    System.out.println("f_auth: "+auth);
                    output.writeBytes(auth+"\n");   //登入結果寫回client端 YES/NO
                    
                    if(auth.equals("YES")){
                    System.out.println("current user index: "+currentUserIndex);
                        output.writeBytes(currentUserName+"\n");
                    }
                }
                else if(clientMessage.equals("regist")){
                    String id = input.readLine();
                    String pw = input.readLine();
                    String name = input.readLine();
                    String reg ="";
                    if(isIDExist(id))   reg="NO";  else reg="YES";
                    
                    //System.out.println("server reg is:"+reg);
                    output.writeBytes(reg+"\n");    //註冊結果寫回client端 YES/NO
                    
                    if(reg.equals("YES")){
                    System.out.println("["+socket.getRemoteSocketAddress()+"]: regist successful");
                        createAccount(id,pw,name);
                        
                    }
                }
                else if(clientMessage.equals("gameLobbyInitialize")){
                    System.out.println("["+currentUserID+"]: enterLobby");

                    while(true){
                         clientMessage = input.readLine();
                         if(clientMessage.equals("leaveLobby")){
                             System .out.println("["+currentUserID+"]: leaveLobby");
                             output.writeBytes(clientMessage+"\n");
                             break;
                         }
                         else{
                             System.out.println("clientMessage: "+clientMessage);
                             sendToAll("["+currentUserName+"] says: "+clientMessage);
                         }

                    }
                }

                
            }
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
            output.writeBytes(message+"\n");
        }
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
