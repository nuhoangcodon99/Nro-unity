package com.girlkun.models.map.mabu_2h;

import com.girlkun.models.map.Map;
import com.girlkun.models.map.MapMaBu.MapMaBu2h;
import com.girlkun.models.map.Zone;
import com.girlkun.services.MapService;
import com.girlkun.utils.Logger;

public class MapXHMaBu2h {

    public static MapXHMaBu2h i;

    public static MapXHMaBu2h gI() {
        if (i == null) {
            i = new MapXHMaBu2h();
        }
        return i;
    }

    public void initBoss(int MapId) {
        Map map = MapService.gI().getMapById(MapId);
        try {
            for (Zone zone : map.zones) {
                new BuMap();
            }
            MapMaBu2h.gI().isSpawnMabu = true;
        } catch (Exception e) {
            Logger.error("Lỗi init boss : " + e);
        }

    }
}
