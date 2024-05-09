package com.unity.models.boss.list_boss.DUONGTANG;

import java.util.Calendar;
import java.util.Random;

import com.unity.models.boss.Boss;
import com.unity.models.boss.BossID;
import com.unity.models.boss.BossStatus;
import com.unity.models.boss.BossesData;
import com.unity.models.map.ItemMap;
import com.unity.models.player.Player;
import com.unity.models.skill.Skill;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.PetService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;


public class DUONGTANG extends Boss {
    final Calendar rightNow = Calendar.getInstance();
    int hour = rightNow.get(11);
    
    public DUONGTANG() throws Exception {
        super(BossID.DUONGTANG, BossesData.DUONGTANG);
    }
    @Override
    public void reward(Player plKill) {

        if (Util.isTrue(2, 100)) {
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 2000+plKill.gender, 2, this.location.x, this.location.y, plKill.id));
        }
        plKill.NguHanhSonPoint+=20;
        Service.gI().sendThongBao(plKill,"Bạn nhận được 20 điểm ngũ hành sơn");
        plKill.pointBoss += 1;
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

  
   @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        if(Util.canDoWithTime(st,900000)){
          this.changeStatus(BossStatus.LEAVE_MAP);
        }
        
    }
   @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
 this.checkAnThan(plAtt);
        if (Util.isTrue(50, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 100);
            if (Util.isTrue(1, 100)) {
            }
            damage = 0;

        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            if (damage >= 1) {
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
    
}
/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - Lucy
 */
    