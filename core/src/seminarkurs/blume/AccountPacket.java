package seminarkurs.blume;

/**
 * Created by Leon on 28.01.2018.
 */

public class AccountPacket {
    private int length;
    private boolean ret;
    private String msg;

    public boolean getReturn()
    {
        return this.ret;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public AccountPacket(String data)
    {
        //0x2x1x24xUser has been registered
        String sRet = data.substring(4, 5);
        this.ret = sRet.equals("1");
        int length_end = 8;
        this.length = Integer.parseInt(data.substring(6, length_end));
        int msg_start = 6 + String.valueOf(this.length).length() + 1;
        int msg_end = msg_start + this.length;
        this.msg = data.substring(msg_start, msg_end);
    }
}
