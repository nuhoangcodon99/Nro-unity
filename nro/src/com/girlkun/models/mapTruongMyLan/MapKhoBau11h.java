package com.girlkun.models.mapTruongMyLan;

import com.girlkun.models.map22h.*;
import com.girlkun.models.player.Player;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;

import java.util.List;

public class MapKhoBau11h {


    public static final byte HOUR_OPEN_MAP_11h = 7;
    public static final byte MIN_OPEN_MAP_11h = 30;
    public static final byte SECOND_OPEN_MAP_11h = 0;


    public static final byte HOUR_CLOSE_MAP_11h = 21;
    public static final byte MIN_CLOSE_MAP_11h = 0;
    public static final byte SECOND_CLOSE_MAP_11h = 0;

    public static final int AVAILABLE = 7;

    private static MapKhoBau11h i;

    public static long TIME_OPEN_11h;
    public static long TIME_CLOSE_11h;

    private int day = -1;

    public static MapKhoBau11h gI() {
        if (i == null) {
            i = new MapKhoBau11h ();
        }
        i.setTimeJoinMap11h();
        return i;
    }

    public void setTimeJoinMap11h() {
        if (i.day == -1 || i.day != TimeUtil.getCurrDay()) {
            i.day = TimeUtil.getCurrDay();
            try {
                TIME_OPEN_11h = TimeUtil.getTime(TimeUtil.getTimeNow("dd/MM/yyyy") + " " + HOUR_OPEN_MAP_11h + ":" + MIN_OPEN_MAP_11h + ":" + SECOND_OPEN_MAP_11h, "dd/MM/yyyy HH:mm:ss");
                TIME_CLOSE_11h = TimeUtil.getTime(TimeUtil.getTimeNow("dd/MM/yyyy") + " " + HOUR_CLOSE_MAP_11h + ":" + MIN_CLOSE_MAP_11h + ":" + SECOND_CLOSE_MAP_11h, "dd/MM/yyyy HH:mm:ss");
            } catch (Exception ignored) {
            }
        }
    }


    private void kickOutOfMap11h(Player player) {
        if (MapService.gI().isMap11h(player.zone.map.mapId)) {
            Service.getInstance().sendThongBao(player, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
        }
    }

    private void ketthuc11h(Player player) {
        player.zone.finishMap11h = true;
        List<Player> playersMap = player.zone.getPlayers();
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            kickOutOfMap11h(player);
        }
    }

    public void joinMap11h(Player player) {
        boolean changed = false;
        if (player.clan != null) {
            List<Player> players = player.zone.getPlayers();
            for (Player pl : players) {
                if (pl.clan != null && !player.equals(pl) && player.clan.equals(pl.clan) && !player.isBoss) {
                    Service.getInstance().changeFlag(player, Util.nextInt(9, 10));
                    changed = true;
                    break;
                }
            }
        }
        if (!changed && !player.isBoss) {
            Service.getInstance().changeFlag(player, Util.nextInt(9, 10));
        }
    }

    public void update(Player player) {
        if (player.zone == null || !MapService.gI().isMapBlackBallWar(player.zone.map.mapId)) {
            
        
            try {
                long now = System.currentTimeMillis();
                if ((now < TIME_OPEN_11h || now > TIME_CLOSE_11h)) {
                    
                    ketthuc11h(player);
                  
                    
                }
            } catch (Exception ignored) {
            }
         }
     }
}
