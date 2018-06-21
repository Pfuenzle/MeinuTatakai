package seminarkurs.blume;

/**
 * Created by Leon on 22.02.2018.
 */

public class LocalPlayer {
    private static boolean bIsSelecting = false; //Ob der Spieler gerade eine Map auswählt
    private static boolean bIsIngame = false; //Ob der Spieler sich gerade im Spiel befindet
    private static Map currentMap;

    private static Map[] maps = {new MapPinku(), new MapKiseki()}; //Alle Maps

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

    public static void setMap(int map)
    {
        currentMap = maps[map];
    }
}
