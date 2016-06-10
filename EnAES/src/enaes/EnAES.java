package enaes;
import java.security.*;
import javax.crypto.*;
/**
 *
 * @author yale918
 */
public class EnAES {
    private KeyGenerator keyGen;
    private SecretKey decodeKey;
    private Cipher cipher;
    private byte[] cipherByte;


    public EnAES() throws NoSuchAlgorithmException, NoSuchPaddingException{
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
        EnAES AES = new EnAES();
        String message = "海苔好好吃";
        byte[] enMessage = AES.Encrytor(message);
        byte[] deMessage = AES.Decryptor(enMessage);
        System.out.println("原文：" + message);
        System.out.println("加密後：" + new String(enMessage));
        System.out.println("解密後：" + new String(deMessage));
  
    }

}
