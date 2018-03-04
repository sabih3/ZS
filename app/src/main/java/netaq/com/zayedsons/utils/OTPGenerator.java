package netaq.com.zayedsons.utils;

import java.security.SecureRandom;

/**
 * Created by Sabih Ahmed on 4/3/2018.
 */

public class OTPGenerator {


    public static int generateRandomNumber(){
        int randomNumber;

        int range = 9; // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++){
            int number = secureRandom.nextInt(range);
            if(number == 0 && i == 0){
                i = - 1;
                continue;
            }
            s = s + number;
        }

        randomNumber = Integer.parseInt(s);

        return randomNumber;
    }
}
