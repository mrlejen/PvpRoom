package PvpRoom.pvpRoom.Data;

import java.util.HashMap;
import java.util.UUID;

public class CheckData {
    public static final HashMap<UUID, Boolean> first = new HashMap<>();
    public static HashMap<UUID, Boolean> getcheckdata() {
        return first;
    }
}
