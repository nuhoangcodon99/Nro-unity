package com.girlkun.models.boss.list_boss.BLACK;

import com.girlkun.models.boss.*;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public class thegioi extends Boss {

    public thegioi() throws Exception {
        super(BossID.THEGIOI, BossesData.THEGIOI);
    }

    @Override
   public void reward(Player plKill) {
        int[] itemDos = new int[]{14,15,16};
        int[] NRs = new int[]{14,15,16};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(5, 100)) {
            if (Util.isTrue(5, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1105, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
         if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 15, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
          if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 14, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
           if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 16, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
            if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1421, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage / 2);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

//    @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
//
//    @Override
//    public void reward(Player plKill) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.reward(plKill);
//    }
//    
//    @Override
//    protected void notifyJoinMap() {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.notifyJoinMap();
//    }
}
