package seminarkurs.blume;

/**
 * Created by Leon on 25.02.2018.
 */

public class UserPacket {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRP() {
        return RP;
    }

    public void setRP(int RP) {
        this.RP = RP;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    private String username;
    private int RP;
    private int wins;
    private int losses;
    private boolean ret;

    public boolean getRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }


    public UserPacket(String data)
    {
        //1x1x00003x100x00001x0x00001x0
        //points Länge: 5
        //wins Laenge: 5
        //loose Laenge: 5
        String sRet = "0";
        try {
            sRet = data.substring(2, 3); //Rückgabewert der Anfrage
        }
        catch(Exception e)
        {
        }
        try {
            if (Integer.parseInt(sRet) == 1) { //User existert
                ret = true;
                int offset = 9;
                int points_length = Integer.parseInt(data.substring(4, offset));
                offset += 1;
                this.RP = Integer.parseInt(data.substring(offset, offset + points_length)); //Lese RP aus
                offset += points_length + 1;
                int wins_length = Integer.parseInt(data.substring(offset, offset + 5));
                offset += 6;
                this.wins = Integer.parseInt(data.substring(offset, offset + wins_length)); //Lese Siege aus
                offset += wins_length + 1;
                int loose_length = Integer.parseInt(data.substring(offset, offset + 5));
                offset += 6;
                this.losses = Integer.parseInt(data.substring(offset, offset + loose_length)); //Lese Niederlagen aus
            } else {//User existert nicht
                ret = false;
            }
        }
        catch(Exception e)
        {
            ret = false;
        }
    }
}
