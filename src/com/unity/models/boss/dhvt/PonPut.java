package com.unity.models.boss.dhvt;

import com.unity.models.boss.BossID;
import com.unity.models.boss.BossesData;
import com.unity.models.player.Player;

/**
 * @author BTH sieu cap vippr0 
 */
public class PonPut extends BossDHVT {

    public PonPut(Player player) throws Exception {
        super(BossID.PON_PUT, BossesData.PON_PUT);
        this.playerAtt = player;
    }
}