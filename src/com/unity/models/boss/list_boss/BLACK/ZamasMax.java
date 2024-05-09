package com.unity.models.boss.list_boss.BLACK;

import java.util.Random;

import com.unity.models.boss.Boss;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossManager;
import com.unity.models.boss.BossStatus;
import com.unity.models.boss.BossesData;
import com.unity.models.map.ItemMap;
import com.unity.models.player.Player;
import com.unity.server.Manager;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;

public class ZamasMax extends Boss {

    public ZamasMax() throws Exception {
        super(BossID.ZAMASMAX, BossesData.THANZM2);
    }

    @Override
    public void reward(Player plKill) {
        byte randomDo = (byte) new Random().nextInt(Manager.itemIds_TL.length - 1);
        byte randomNR = (byte) new Random().nextInt(Manager.itemIds_NR_SB.length);
        int[] itemDos = new int[] { 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567 };
        if (Util.isTrue(BossManager.ratioReward, 100)) {
            int[] manhthuong = new int[] { 1066, 1067, 1068, 1069, 1070 };
            int[] manhhiem = new int[] { 561 };

            int randomAWJ = new Random().nextInt(manhthuong.length);
            int randomGR = new Random().nextInt(manhhiem.length);
            if (Util.isTrue(20, 100)) {
                Service.gI().dropItemMap(this.zone,
                        Util.manhTS(zone, manhthuong[randomAWJ], 1, this.location.x, this.location.y, plKill.id));
            } else {
                Service.gI().dropItemMap(this.zone,
                        Util.manhTS(zone, manhhiem[randomGR], 3, this.location.x, this.location.y, plKill.id));
            }
            if (Util.isTrue(10, 20)) {
                Service.gI().dropItemMap(this.zone,
                        new ItemMap(zone, 725, 1, this.location.x, this.location.y, plKill.id));
                if (Util.isTrue(4, 10)) {
                    Service.gI().dropItemMap(this.zone,
                            Util.ratiItem(zone, 344, 1, this.location.x + 2, this.location.y, plKill.id));
                    Service.gI().dropItemMap(this.zone,
                            Util.ratiItem(zone, 2000 + plKill.gender, 1, this.location.x, this.location.y, plKill.id));
                }
            } else {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, Manager.itemIds_TL[randomDo], 1,
                        this.location.x, this.location.y, plKill.id));
            }
        } else if (Util.isTrue(70, 100)) {
            Service.gI().dropItemMap(this.zone,
                    new ItemMap(zone, Manager.itemIds_NR_SB[randomNR], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone,
                    Util.RaitiDoc12(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
        }
        plKill.pointBoss += 3;
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

    @Override
    public void active() {
        super.active(); // To change body of generated methods, choose Tools | Templates.
        if (Util.canDoWithTime(st, 1800000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); // To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        this.checkAnThan(plAtt);
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
                this.chat("Xí hụt");
                return 0;
            }
            if (plAtt != null && plAtt.nPoint.isSieuThan) {
                damage = this.nPoint.subDameInjureWithDeff(damage);
            } else {
                damage = this.nPoint.subDameInjureWithDeff(damage / 2);
            }
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = damage / 4;
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
    public void moveTo(int x, int y) {
        if (this.currentLevel == 1) {
            return;
        }
        super.moveTo(x, y);
    }
    //
    // @Override
    // public void reward(Player plKill) {
    // if(this.currentLevel == 1){
    // return;
    // }
    // super.reward(plKill);
    // }
    //
    // @Override
    // protected void notifyJoinMap() {
    // if(this.currentLevel == 1){
    // return;
    // }
    // super.notifyJoinMap();
    // }
}
