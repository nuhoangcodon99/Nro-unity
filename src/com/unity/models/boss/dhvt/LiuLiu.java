package com.unity.models.boss.dhvt;

import com.unity.models.boss.BossData;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossesData;
import com.unity.models.player.Player;
/**
 * @author BTH sieu cap vippr0 
 */
public class LiuLiu extends BossDHVT {

    public LiuLiu(Player player) throws Exception {
        super(BossID.LIU_LIU, BossesData.LIU_LIU);
        this.playerAtt = player;
    }
}