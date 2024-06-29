package com.girlkun.models.map.mabu_2h;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;
import java.util.ArrayList;
import java.util.List;

public class BuBung extends Boss {

  public BuBung() throws Exception {
    super(Util.randomBossId(), BossesData.BU_BUNG);
  }

  @Override
  public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
    
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
        this.non();
      }

      return damage;
    } else {
      return 0;
    }
  }

  @Override
  public void reward(Player plKill) {
    
    TaskService.gI().checkDoneTaskKillBoss(plKill, this);
  }

  private void non() {
    List<Player> playersCopy = new ArrayList<>(this.zone.getPlayers());
    for (Player pl : playersCopy) {
      if (pl != null && pl.isPl()) {
        int zoneLast = pl.lastZoneMabu;
        if (zoneLast == 0) {
          zoneLast = Util.nextInt(0, 8);
        }
        ChangeMapService.gI().changeMapInYard(pl, 128, zoneLast, 200);
      }
    }
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
        ChangeMapService.gI().changeMap(pl, 128, Util.nextInt(0, 8), 197, 270);
        Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
        this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
        this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
        this.lastTimeHapThu = System.currentTimeMillis();
        this.timeHapThu = Util.nextInt(30000, 60000);
        pl.lastZoneMabu = this.zone.zoneId;
    }
}


  

