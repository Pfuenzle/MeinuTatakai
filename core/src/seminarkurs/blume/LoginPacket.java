package seminarkurs.blume;

/**
 * Created by Leon on 13.03.2018.
 */

public class LoginPacket {
    private int length;
    private boolean ret; //Ob der Login fehlgeschlagen ist oder nicht
    private String msg; //Nachricht des Servers
    private String SESSION; //Session-Key

    private boolean bIsBroken = false;

    public boolean isBroken()
    {return bIsBroken;}

    public boolean getReturn()
    {
        return this.ret;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public LoginPacket(String data)
    {
        /*
        existiert nicht: 0x1x0x23xUsername does not exist
        richtig: 0x1x1x40847549x20xLogin was successful
        falsches Passwort: 0x1x0x14xwrong password
         */
        try {
            String sRet = data.substring(4, 5); //Setz Rückgabewert
            this.ret = sRet.equals("1");
            if (ret) { //Login hat geklappt
                SESSION = data.substring(6, 14); //Session-Key auslesen
                NetworkPlayer.setSESSION(SESSION);//Session-Key setzen
                int length_end = 17;
                this.length = Integer.parseInt(data.substring(15, length_end)); //Länge und Positon der Nachricht bestimmen
                int msg_start = 15 + String.valueOf(this.length).length() + 1;
                int msg_end = msg_start + this.length;
                this.msg = data.substring(msg_start, msg_end); //Nachricht auslesen
            } else { //Login ist fehlgeschlagen
                int msg_length = Integer.parseInt(data.substring(6, 10));
                this.msg = data.substring(11, 11 + msg_length); //Nachricht auslesen
            }
        }
        catch(Exception e)
        {
            this.bIsBroken = true; //Packet ist kaputt
        }
    }
}
