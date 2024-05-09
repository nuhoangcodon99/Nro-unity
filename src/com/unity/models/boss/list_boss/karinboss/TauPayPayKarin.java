package com.unity.models.boss.list_boss.karinboss;

import com.unity.models.consts.ConstPlayer;
import com.unity.models.boss.Boss;
import com.unity.models.boss.BossData;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossManager;
import com.unity.models.boss.BossesData;
import com.unity.models.map.ItemMap;
import com.unity.models.map.Zone;
import com.unity.models.player.Player;
import com.unity.server.Client;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;

/**
 * @Stole By Lucy#0800
 */
public class TauPayPayKarin extends Boss {

    public TauPayPayKarin(int bossID, BossData bossData, Zone zone, Player plTarget) throws Exception {
//        super(bossID, bossData);
                super(BossID.TAU_PAY_PAY_KARIN, BossesData.TAU_PAY_PAY_KARIN);
        this.zone = zone;
        this.lockPlayerTarget = plTarget;
    }

    @Override
    public void reward(Player plKill) {
        // vật phẩm rơi khi diệt boss nhân bản
        if (plKill.pointKarin < 1) {
            plKill.pointKarin = 1;
                    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
        }

    }

    @Override
    public void active() {
        super.active();
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        if (this.playerTarger != null && Client.gI().getPlayer(this.playerTarger.id) == null) {
            playerTarger.haveTauPayPay = false;
            this.leaveMap();
        }
        if (this.playerTarger != null && this.playerTarger.isDie()) {
            playerTarger.haveTauPayPay = false;
            this.leaveMap();
        }
        if (this.playerTarger != null && this.playerTarger.zone != this.zone) {
            playerTarger.haveTauPayPay = false;
            this.leaveMap();
        }

    }

    @Override
    public void joinMap() {
        super.joinMap();
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        this.checkAnThan(plAtt);
        if (!this.isDie()) {
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }

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
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
        playerTarger.haveTauPayPay = false;
    }
}
