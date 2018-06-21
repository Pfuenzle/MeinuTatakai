package seminarkurs.blume;

/**
 * Created by Leon on 28.01.2018.
 */

public class RegisterPacket {
    private int length;
    private boolean ret;
    private String msg;

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

    public RegisterPacket(String data)
    {
        //0x2x1x24xUser has been registered
        try {
            String sRet = data.substring(4, 5);
            this.ret = sRet.equals("1"); //Setze Rückgabewert
            int length_end = 8;
            this.length = Integer.parseInt(data.substring(6, length_end));
            int msg_start = 6 + String.valueOf(this.length).length() + 1;
            int msg_end = msg_start + this.length; //Länge, Anfang und Ende der Nachricht
            this.msg = data.substring(msg_start, msg_end); //Setze Nachricht
        }
        catch(Exception e) { //Packet ist fehlerhaft
            this.bIsBroken = true;
        }
    }
}
