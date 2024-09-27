package lk.thogakade.pos.util;

import org.mindrot.jbcrypt.BCrypt;

/*SOLID PRINCIPLES => 1. Single Responsibility for one class */
public class PasswordManager {
    public static String encryptPassword(String plainText){
        return BCrypt.hashpw(plainText,BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String plainText, String hashPassword){
        return BCrypt.checkpw(plainText,hashPassword);
    }
}
