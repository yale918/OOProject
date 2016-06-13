
package gameserver;


public class UserData {
    public String ID ="";
    public String PW ="";
    public String Name ="";
    public String pictureName ="";
    //public int onlineUserIndex = 0;
    public int lobbyUserIndex = 0;
    
    public UserData(){}
    
    public UserData(String id,String pw,String name, String pic){
        ID= id;
        PW= pw;
        Name=name;
        pictureName = pic;
    }
}
