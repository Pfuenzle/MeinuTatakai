package seminarkurs.blume;

/**
 * Created by Leon on 13.03.2018.
 */

public class LoginPacket {
    private int length;
    private boolean ret;
    private String msg;
    private String SESSION;

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
        falsch: 0x1x0x14xwrong password
         */
        try {
            String sRet = data.substring(4, 5);
            this.ret = sRet.equals("1");
            if (ret) {
                SESSION = data.substring(6, 14);
                NetworkPlayer.setSESSION(SESSION);
                int length_end = 17;
                this.length = Integer.parseInt(data.substring(15, length_end));
                int msg_start = 15 + String.valueOf(this.length).length() + 1;
                int msg_end = msg_start + this.length;
                this.msg = data.substring(msg_start, msg_end);
            } else {
                int msg_length = Integer.parseInt(data.substring(6, 10));
                this.msg = data.substring(11, 11 + msg_length);
            }
        }
        catch(Exception e)
        {
            this.bIsBroken = true;
        }
    }
}
