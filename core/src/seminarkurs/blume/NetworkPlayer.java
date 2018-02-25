package seminarkurs.blume;

/**
 * Created by Leon on 01.02.2018.
 */

public class NetworkPlayer {
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username_in) {
        username = username_in;
    }

    public static  int getRP() {
        return RP;
    }

    public static void setRP(int RP_in) {
        RP = RP_in;
    }

    public static int getWins() {
        return wins;
    }

    public static void setWins(int wins_in) {
        wins = wins_in;
    }

    public static int getLosses() {
        return losses;
    }

    public static void setLosses(int losses_in) {
        losses = losses_in;
    }

    private static String username = "Fehler";
    private static String SESSION_KEY;
    private static int RP = 1337;
    private static int wins = 88;
    private static int losses = -2;

    //Network
    private static String SESSION;

}
