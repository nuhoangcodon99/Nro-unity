package com.unity.models.boss.list_boss.KhiGasHuyDiet;

import com.unity.models.boss.BossData;
import com.unity.models.boss.BossManager;
import com.unity.models.boss.BossID;
import com.unity.models.boss.Boss;
import com.unity.models.consts.ConstPlayer;
import com.unity.models.map.ItemMap;
import com.unity.models.map.Zone;
import com.unity.models.player.Player;
import com.unity.models.skill.Skill;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;
import java.util.Random;

public class DrLychee extends Boss {

    private static final int[][] FULL_DEMON = new int[][]{{Skill.DEMON, 1}, {Skill.DEMON, 2}, {Skill.DEMON, 3}, {Skill.DEMON, 4}, {Skill.DEMON, 5}, {Skill.DEMON, 6}, {Skill.DEMON, 7}};

    public DrLychee(Zone zone, int level, int dame, int hp) throws Exception {
        super(Util.randomBossId(), new BossData(
                "Dr Lychee",
                ConstPlayer.TRAI_DAT,
                new short[]{639, 640, 641, -1, -1, -1},
                ((10000 + dame) * level),
                new long[]{((500000 + hp) * level)},
                new int[]{103},
                (int[][]) Util.addArray(FULL_DEMON),
                new String[]{},
                new String[]{"|-1|Nhóc con"},
                new String[]{},
                60
        ));
        this.zone = zone;
    }

    @Override
   
    public void reward(Player plKill) {
        int lv = plKill.clan.KhiGaHuyDiet.level;
        if (lv == 110) {
            
        } else if (lv <= 50) {  
        
        }
        if (Util.isTrue(100, 100)) {
            ItemMap it = new ItemMap(this.zone, 14, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            Service.getInstance().dropItemMap(this.zone, it);
        }
         plKill.pointBoss += 0;
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);

    }

    @Override
    public void active() {
        super.active();
    }

    @Override
    public void joinMap() {
        super.joinMap();
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}
