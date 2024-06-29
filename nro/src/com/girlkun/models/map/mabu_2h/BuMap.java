/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.map.mabu_2h;
import com.girlkun.models.boss.list_boss.cell.*;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import com.girlkun.services.PlayerService;
import com.girlkun.services.func.ChangeMapService;
import java.util.Random;

/**
 * @Stole By vantanz
 */
public class BuMap extends Boss {
   
    public BuMap() throws Exception {
         super(BossID.BU_MAP, BossesData.BU_MAP_2H, BossesData.SUPER_BU, BossesData.BU_TENK, BossesData.BU_HAN, BossesData.KID_BU);
    }

   @Override
    public void reward(Player plKill) {
        int[] itemDos = new int[]{16,15,14,2003,2004,2005};
        int[] NRs = new int[]{2003,2004,2005};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(80, 100)) {
            if (Util.isTrue(30, 105)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 987, 10, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
        if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1421, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

   
     private long lastTimeHapThu = System.currentTimeMillis();
    private int timeHapThu = 30000;

    @Override
    public void active() {
        this.hapThu();
        super.active(); // To change body of generated methods, choose Tools | Templates.
    }

    private void hapThu() {
        if (!Util.canDoWithTime(this.lastTimeHapThu, this.timeHapThu)) {
            return;
        }
        Player pl = this.zone.getRandomPlayerInMap();
        if (pl == null || pl.isDie()) {
            return;
        }
        ChangeMapService.gI().changeMap(pl, 128, Util.nextInt(0, 0), 197, 270);
        Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
        this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
        this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
        this.lastTimeHapThu = System.currentTimeMillis();
        this.timeHapThu = Util.nextInt(30000, 60000);
        pl.lastZoneMabu = this.zone.zoneId;
    }
}
