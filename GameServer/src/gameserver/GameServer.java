
package gameserver;
import java.io.*;
import java.net.*;
import java.util.*;


public class GameServer {
    public static int currentUserIndex ;
    public static int maxNumberOfUsers = 20;
    public static int numberOfUsers = 0;
    public static UserData[] userData = new UserData[maxNumberOfUsers];
    public static String currentUserID = "";
    public static String currentUserPW = "";
    public static String currentUserName = "";
    
    public static void createAccount(String ID, String PW, String Name){
        
        userData[numberOfUsers].ID=ID;
        userData[numberOfUsers].PW=ID;
        userData[numberOfUsers].Name=Name;
        
        numberOfUsers++;
         
    }
    
    public static void setCurrentUserData(String Name, String ID, String PW,int index){
        
        System.out.println("Name="+Name);
        currentUserIndex = index;
        currentUserName = Name;
        currentUserID = ID;
        currentUserPW = PW;
    }
    
    public static String authentication(String id, String pw){
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
    
    public static boolean isIDExist(String targetID){
        for (int i=0; i<numberOfUsers;i++)
            if ( targetID.equals(userData[i].ID))   return true;
        return false;
    }
    
    GameServer(){
        for (int i=0;i<maxNumberOfUsers;i++){
                userData[i] = new UserData();
        }
        currentUserIndex = numberOfUsers;
        createAccount("a","a","owner");
        createAccount("b","b","maid");
        createAccount("c","c","little bear");
        //System.out.println(userData[currentUserIndex].Name);
    }
    
    public static void main(String[] args) throws Exception{
        int port = 8881;
        ServerSocket server = new ServerSocket(port);   System.out.println("server run on: "+port);
        
        new GameServer();
        while(true){
            Socket socket = server.accept();
            System.out.println("new connection from"+socket.getRemoteSocketAddress());
            new Thread(new Task(socket) {}).start();
        }
        
        
    }
    
      static class Task implements Runnable{
        public Socket socket;
        public BufferedReader input;
        public DataOutputStream output;
        public String clientMessage;
        
        public Task(Socket socket){
            this.socket = socket;
        }
        public void run(){
            //System.out.println("in run");
            try{
                socketHandler();
            }
            catch(IOException IOE){
                System.out.println(IOE);
            }

        }
/*
        public void chatHandler() throws IOException{
            while(true){
                clientMessage = input.readLine();
            
            }
        }
*/
        private void socketHandler() throws IOException{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            //System.out.println("in socketHandler");
            welcomeMessage();
            while(true){
                clientMessage = input.readLine();
                
                if(clientMessage.equals("authencate")){
                    System.out.println("["+socket.getRemoteSocketAddress()+"]: is "+ "authenticating");
                    String id = input.readLine();
                    String pw = input.readLine();
                    String auth = "";
                    auth = authentication(id,pw);
                    
                    output.writeBytes(auth+"\n");   //登入結果寫回client端 YES/NO
                    
                    if(auth.equals("YES")){
                        System.out.println("current user index: "+currentUserIndex);
                        output.writeBytes(currentUserName+"\n");
                    }
                }
                else if(clientMessage.equals("regist")){
                    System.out.println("["+socket.getRemoteSocketAddress()+"]: is "+ "registing");
                    String id = input.readLine();
                    String pw = input.readLine();
                    String name = input.readLine();
                    String reg ="";
                    if(isIDExist(id))   reg="NO";  else reg="YES";
                    
                    System.out.println("server reg is:"+reg);
                    output.writeBytes(reg+"\n");    //註冊結果寫回client端 YES/NO
                    
                    if(reg.equals("YES")){
                        System.out.println("["+socket.getRemoteSocketAddress()+"]: regist successful");
                        createAccount(id,pw,name);
                        
                    }
                }
                    
                
                if(!clientMessage.equals("ov")){
                    //System.out.println("["+socket.getRemoteSocketAddress()+"]: "+ clientMessage);
                    //output.writeBytes(clientMessage);
                }else{
                    System.out.println("["+socket.getRemoteSocketAddress()+"] 已斷線" );
                    break;
                }
            }
        }
        
        public void welcomeMessage() throws IOException{
            output.writeBytes("welcome to GameServer\n");
        }
        
        
        
        
        
    }
    
}
