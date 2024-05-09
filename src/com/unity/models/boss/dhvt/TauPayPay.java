package com.unity.models.boss.dhvt;

import com.unity.models.boss.BossID;
import com.unity.models.boss.BossesData;
import com.unity.models.player.Player;

/**
 * @author BTH sieu cap vippr0 
 */
public class TauPayPay extends BossDHVT {

    public TauPayPay(Player player) throws Exception {
        super(BossID.TAU_PAY_PAY, BossesData.TAU_PAY_PAY);
        this.playerAtt = player;
    }
}