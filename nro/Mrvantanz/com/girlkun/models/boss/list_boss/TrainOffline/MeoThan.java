package com.girlkun.models.boss.list_boss.TrainOffline;

import com.girlkun.models.boss.*;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

/**
 * @Stole By MITCHIKEN ZALO 0358689793
 */
public class MeoThan extends TrainBoss {

    public MeoThan(byte bossID, BossData bossData, Zone zone, int x, int y) throws Exception {
        super(BossID.MEO_THAN, BossesData.THAN_MEO,zone,x,y);
    }
}