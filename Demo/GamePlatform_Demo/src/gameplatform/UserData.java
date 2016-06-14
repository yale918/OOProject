/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplatform;

/**
 *
 * @author yale918
 */
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
