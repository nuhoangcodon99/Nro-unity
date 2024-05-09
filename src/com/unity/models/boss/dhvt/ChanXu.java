package com.unity.models.boss.dhvt;

import com.unity.models.boss.BossData;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossesData;
import com.unity.models.player.Player;
/**
 * @author BTH sieu cap vippr0 
 */
public class ChanXu extends BossDHVT {

    public ChanXu(Player player) throws Exception {
        super(BossID.CHAN_XU, BossesData.CHAN_XU);
        this.playerAtt = player;
    }
}