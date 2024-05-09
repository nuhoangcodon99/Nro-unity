package com.unity.models.player;

// đây
import java.util.ArrayList;
import java.util.List;

import com.unity.models.consts.ConstMap;
import com.unity.models.boss.BossesData;
import red.team.boss.list_boss.nhanban.Traidatnhanban;
import red.team.boss.list_boss.nhanban.Xaydanhanban;
import com.unity.models.map.Map;
import com.unity.models.map.Zone;
import com.unity.models.shop.ShopServiceNew;
import com.unity.server.Manager;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.MapService;
import com.unity.models.services.PlayerService;
import com.unity.models.services.Service;
import com.unity.models.services.TaskService;
import com.unity.models.utils.Util;

/**
 * @author BTH sieu cap vippr0
 */
public class Xayda extends Player {

  private long lastTimeChat;
  private Player playerTarget;

  private long lastTimeTargetPlayer;
  private long timeTargetPlayer = 5000;
  private long lastZoneSwitchTime;
  private long zoneSwitchInterval;
  private List<Zone> availableZones;

  public void initXayda() {
    init();
  }

  @Override
  public short getHead() {
    return 538;
  }

  @Override
  public short getBody() {
    return 474;
  }

  @Override
  public short getLeg() {
    return 475;
  }

  public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
    this.checkAnThan(plAtt);
    if(this.nPoint.hp <= 500000000){
      this.nPoint.hp = this.nPoint.hpMax;
       Service.gI().point(this);
    }
    if (plAtt != null && plAtt.isPl() && plAtt.cFlag == 1) {
      if (!plAtt.haveNhanBan) {
        try {
          Xaydanhanban xaydanhanban = new Xaydanhanban(Util.randomBossId(),
              BossesData.XAYDA, plAtt.zone, plAtt);
          plAtt.haveNhanBan = true;
        } catch (Exception e) {

        }
      }

    }
     if(plAtt!=null && plAtt.isPl() && (plAtt.playerTask.taskMain.id == 11 || plAtt.playerTask.taskMain.id == 27)){
      TaskService.gI().sendNextTaskMain(plAtt);
      this.chat("Á đù người hướng nội !");
    }
    if (!this.isDie()) {
      if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 100)) {
        this.chat("Xí hụt");
        return 0;
      }
      this.chat("|7| Sát thương vừa nhận: " + Util.numberToMoney(damage));
      return damage;
    } else {
      return 0;
    }
  }
  public void joinMap(Zone z, Player player) {
    MapService.gI().goToMap(player, z);
    z.load_Me_To_Another(player);
  }


  @Override
  public void update() {
    String[] textBaoCat = { "|7|serizawa.store", "|7|lucyonfire", "|7|Welcome to NRO Lucy!" };
    if (Util.canDoWithTime(lastTimeChat, 5000)) {
      Service.getInstance().chat(this, textBaoCat[Util.nextInt(textBaoCat.length)]);
      lastTimeChat = System.currentTimeMillis();
      this.nPoint.setFullHpMp();
      Service.gI().point(this);
    }
  }

  private void init() {
    int id = Util.randomBossId();
    for (Map m : Manager.MAPS) {
      if (m.mapId == 192) {
        for (Zone z : m.zones) {
          Xayda pl = new Xayda();
          pl.name = "Xayda";
          pl.gender = 2;
          pl.id = id;
          pl.isBuNhin = true;
          pl.nPoint.hpMax = (int) 2000000000;
          pl.nPoint.hpg = (int) 2000000000;
          pl.nPoint.hp = (int) 2000000000;
          pl.nPoint.tlPST = 5;

          pl.nPoint.setFullHpMp();
          pl.location.x = 559;
          pl.location.y = 384;
          pl.nPoint.power = 99999999999L;
          pl.cFlag = 8;
          joinMap(z, pl);
          z.setReferee(pl);
          

        }
      }
    }
  }
}

// }
