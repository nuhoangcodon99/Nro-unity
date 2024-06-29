package com.girlkun.models.boss.list_boss.KhiGasHuyDiet;


import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.Boss;
import com.girlkun.consts.ConstPlayer;

import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;

public class DrLychee extends Boss {

    private static final int[][] FULL_DEMON = new int[][]{{Skill.DEMON, 1}, {Skill.DEMON, 2}, {Skill.DEMON, 3}, {Skill.DEMON, 4}, {Skill.DEMON, 5}, {Skill.DEMON, 6}, {Skill.DEMON, 7}};

    public DrLychee(Zone zone, int level, int dame, int hp) throws Exception {
        super(BossID.TRUNG_UY_TRANG, new BossData(
                "Dr Lychee",
                ConstPlayer.TRAI_DAT,
                new short[]{742, 743, 744, -1, -1, -1},
                ((10000 + dame) * level),
                new int[]{((500000 + hp) * level)},
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
        int[] itemCt = new int[]{738};
        int randomDo = new Random().nextInt(itemCt.length);
        if (Util.isTrue(99, 100)) {
            if (Util.isTrue(1, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 15, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x + 1, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x + 2, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x + 3, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x + 4, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x + 5, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x - 1, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x - 2, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x - 3, this.location.y, plKill.id));
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x - 4, this.location.y, plKill.id));
        }
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
