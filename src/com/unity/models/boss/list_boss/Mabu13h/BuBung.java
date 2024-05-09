package com.unity.models.boss.list_boss.Mabu13h;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.unity.models.consts.ConstPlayer;
import com.unity.models.boss.Boss;
import com.unity.models.boss.BossesData;
import com.unity.models.map.ItemMap;
import com.unity.models.player.Player;
import com.unity.server.Manager;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.PlayerService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.services.func.ChangeMapService;
import com.unity.models.utils.Util;

public class BuBung extends Boss {

  public BuBung() throws Exception {
    super(Util.randomBossId(), BossesData.BU_BUNG);
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
        this.non();
      }

      return damage;
    } else {
      return 0;
    }
  }

  @Override
  public void reward(Player plKill) {
    plKill.pointBoss += 0;
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
        ChangeMapService.gI().changeMapInYard(pl, 144, zoneLast, 200);
      }
    }

  }
}
