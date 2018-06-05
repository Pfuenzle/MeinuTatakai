package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

public class LocalPlayer {
    private static boolean bIsSelecting = false;
    private static boolean bIsIngame = false;
    private static Map currentMap;

    public static boolean isSelecting()
    {
        return bIsSelecting;
    }

    public static void setbIsSelecting(boolean IsSelecting) {
        bIsSelecting = IsSelecting;
    }

    public static boolean isIngame()
    {
        return bIsIngame;
    }

    public static void setbIsIngame(boolean inGame)
    {
        bIsIngame = inGame;
    }


    public static Map getMap()
    {
        return currentMap;
    }

    public static void setMap(Map map)
    {
        currentMap = map;
    }
}
