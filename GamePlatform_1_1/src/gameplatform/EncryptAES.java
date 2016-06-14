/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplatform;
import java.security.*;
import javax.crypto.*;
/**
 *
 * @author yale918
 */
public class EncryptAES {
    private KeyGenerator keyGen;
    private SecretKey decodeKey;
    private Cipher cipher;
    private byte[] cipherByte;


    public EncryptAES() throws NoSuchAlgorithmException, NoSuchPaddingException{
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        keyGen = KeyGenerator.getInstance("AES");
        decodeKey = keyGen.generateKey();
        cipher = Cipher.getInstance("AES");
    }
    
    public byte[] Encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        cipher.init(Cipher.ENCRYPT_MODE, decodeKey);
        byte[] source = str.getBytes();
        cipherByte = cipher.doFinal(source);
        return cipherByte;
    }
    
    public byte[] Decryptor(byte[] buff) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException{
        cipher.init(Cipher.DECRYPT_MODE, decodeKey);
        cipherByte = cipher.doFinal(buff);
        return cipherByte;
    }
    
    public static void main(String[] args) throws BadPaddingException,IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException{
        EncryptAES AES = new EncryptAES();
        String message = "海苔好好吃";
        byte[] enMessage = AES.Encrytor(message);
        byte[] deMessage = AES.Decryptor(enMessage);
        System.out.println("原文：" + message);
        System.out.println("加密後：" + new String(enMessage));
        System.out.println("解密後：" + new String(deMessage));
  
    }

}
