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

public class vegeta extends Boss {

    public vegeta() throws Exception {
        super(BossID.VEGETA4, BossesData.VEGETA4);
    }

    @Override
   public void reward(Player plKill) {
        if (Util.isTrue(80, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1322, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(77, Util.nextInt(60, 200)));
            caitrangmi.options.add(new Item.ItemOption(103, Util.nextInt(60, 200)));
            caitrangmi.options.add(new Item.ItemOption(106, Util.nextInt(0, 0)));
            caitrangmi.options.add(new Item.ItemOption(94, Util.nextInt(5, 50)));
            caitrangmi.options.add(new Item.ItemOption(93, Util.nextInt(1, 1)));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }       
        if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1576, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
         if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1576, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
         if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1576, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
        if (Util.isTrue(20, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1322, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                    this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(77, Util.nextInt(60, 200)));
            caitrangmi.options.add(new Item.ItemOption(103, Util.nextInt(60, 200)));
            caitrangmi.options.add(new Item.ItemOption(106, Util.nextInt(0, 0)));
            caitrangmi.options.add(new Item.ItemOption(94, Util.nextInt(5, 50)));
            
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
    }
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
