package com.unity.models.boss.dhvt;

import com.unity.models.boss.BossData;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossesData;
import com.unity.models.player.Player;

/**
 * @author BTH sieu cap vippr0 
 */
public class JackyChun extends BossDHVT {

    public JackyChun(Player player) throws Exception {
        super(BossID.JACKY_CHUN, BossesData.JACKY_CHUN);
        this.playerAtt = player;
    }
}