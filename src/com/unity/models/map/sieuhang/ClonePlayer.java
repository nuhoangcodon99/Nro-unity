package com.unity.models.map.sieuhang;

import com.unity.models.boss.BossData;
import com.unity.models.boss.dhvt.BossDHVT;
import com.unity.models.player.Player;
import com.unity.models.utils.Util;

/**
 *
 * @author Béo Mập :3
 */
public class ClonePlayer extends BossDHVT{
    
    public ClonePlayer(Player player, BossData data, int id) throws Exception {
        super(Util.randomBossId(), data,5000);
        this.playerAtt = player;
        this.idPlayer = id;
    }
}
