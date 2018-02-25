package seminarkurs.blume;

import java.util.Arrays;

/**
 * Created by Leon on 25.02.2018.
 */

public class Crypt {
    public static String encrypt(String str, String key) {
        int[] output = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            int o = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(key.charAt(i % (key.length() - 1)))) + '0';
            output[i] = o;
        }
        return arr2String(output);
    }

    private static int[] string2Arr(String str) {
        String[] sarr = str.split(",");
        int[] out = new int[sarr.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = Integer.valueOf(sarr[i]);
        }
        return out;
    }

    private static String arr2String(int[] arr)
    {
        String temp = "";
        for(int i = 0; i < arr.length;i++)
        {
            temp += Integer.toString(arr[i]) + ",";
        }
        return temp;
    }

    public static String decrypt(String input_str, String key) {
        int[] input = string2Arr(input_str);
        String output = "";
        for(int i = 0; i < input.length; i++) {
            output += (char) ((input[i] - 48) ^ (int) key.charAt(i % (key.length() - 1)));
        }
        return output;
    }
}
