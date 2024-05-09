package com.unity.models.boss.list_boss.hungvuong;

import java.util.Random;

import com.unity.models.boss.Boss;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossStatus;
import com.unity.models.boss.BossesData;
import com.unity.models.map.ItemMap;
import com.unity.models.player.Player;
import com.unity.models.skill.Skill;
import com.unity.models.services.PetService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;

/**
 *
 * @Stole By Lucy#0800
 */
public class VoiChinNga extends Boss {

    public VoiChinNga() throws Exception {
        super(BossID.VOI_9_NGA, BossesData.VOI_9_NGA);
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
       if (Util.canDoWithTime(st, 900000)) {
           this.changeStatus(BossStatus.LEAVE_MAP);
       }
       
       
    }
    @Override
    public void reward(Player plKill) {
        plKill.pointBoss += 0;
        for(int i=0;i<=10;i++){
          Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1211, 1, this.location.x+i*5, this.location.y, -1));
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - Lucy
 */