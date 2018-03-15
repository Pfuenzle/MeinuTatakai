package seminarkurs.blume;

/**
 * Created by Leon on 13.03.2018.
 */

public class LoginPacket {
    private int length;
    private boolean ret;
    private String msg;
    private String SESSION;

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
        //0x2x1x12345678x24xUser has been registered
        String sRet = data.substring(4, 5);
        this.ret = sRet.equals("1");
        SESSION = data.substring(6, 14);
        NetworkPlayer.setSESSION(SESSION);
        int length_end = 17;
        this.length = Integer.parseInt(data.substring(15, length_end));
        int msg_start = 15 + String.valueOf(this.length).length() + 1;
        int msg_end = msg_start + this.length;
        this.msg = data.substring(msg_start, msg_end);
    }
}
