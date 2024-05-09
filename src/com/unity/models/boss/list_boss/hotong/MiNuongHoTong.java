package com.unity.models.boss.list_boss.hotong;

import com.unity.models.consts.ConstPlayer;
import com.unity.models.boss.Boss;
import com.unity.models.boss.BossData;
import com.unity.models.boss.BossManager;
import com.unity.models.item.Item;
import com.unity.models.map.ItemMap;
import com.unity.models.map.Zone;
import com.unity.models.player.Player;
import com.unity.server.Client;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.InventoryServiceNew;
import com.unity.models.services.ItemService;
import com.unity.models.services.MapService;
import com.unity.models.services.PlayerService;
import com.unity.models.services.Service;
import com.unity.models.services.func.ChangeMapService;
import com.unity.models.utils.Util;
import com.unity.models.player.Inventory;

/**
 * @author Administrator
 */
public class MiNuongHoTong extends Boss {

    public MiNuongHoTong(int bossID, BossData bossData, Zone zone, int x, int y) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
        this.location.x = x;
        this.location.y = y;
    }

    // @Override
    // public void reward(Player plKill) {
    // ItemMap it = new ItemMap(this.zone, Util.nextInt(1099, 1103), Util.nextInt(3,
    // 4), this.location.x, this.zone.map.yPhysicInTop(this.location.x,
    // this.location.y - 24), plKill.id);
    // Service.getInstance().dropItemMap(this.zone, it);
    // }
    long lasttimemove;

    // @Override
    // public void reward(Player plKill) {
    // ItemMap it = new ItemMap(this.zone, 2319, 1, this.location.x,
    // this.zone.map.yPhysicInTop(this.location.x,
    // this.location.y - 24), plKill.id);
    // // it.options.add(new Item.ItemOption(73, 1));
    // Service.getInstance().dropItemMap(this.zone, it);
    // }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        if (this.playerTarger != null && Client.gI().getPlayer(this.playerTarger.id) == null) {
            this.leaveMap();
        }
        if (this.playerTarger != null && this.playerTarger.isDie()) {
            this.leaveMap();
        }
        if (Util.getDistance(playerTarger, this) > 500 && this.zone == this.playerTarger.zone) {
            Service.gI().sendThongBao(this.playerTarger, "Xa Mị Nương là bao giông tố kéo đến!! ");
            this.leaveMap();
        }
        if (Util.getDistance(playerTarger, this) > 300 && this.zone == this.playerTarger.zone) {
            Service.gI().sendThongBao(this.playerTarger, "Khoảng cách qua xa, Mị Nương sắp rời xa bạn!! ");
        }
        if (this.playerTarger != null && Util.getDistance(playerTarger, this) <= 300) {
            int dir = this.location.x - this.playerTarger.location.x <= 0 ? -1 : 1;
            if (Util.canDoWithTime(lasttimemove, 1000)) {
                lasttimemove = System.currentTimeMillis();
                this.moveTo(this.playerTarger.location.x + Util.nextInt(dir == -1 ? 0 : -30, dir == -1 ? 10 : 0),
                        this.playerTarger.location.y);
            }
        }
        if (this.playerTarger != null && playerTarger.haveMiNuong && this.zone.map.mapId == this.mapCongDuc) { // xử
                                                                                                               // lý
                                                                                                               // khi
                                                                                                               // đến
                                                                                                               // map
                                                                                                               // muốn
                                                                                                               // đến
            playerTarger.inventory.ruby += 50;
            // playerTarger.inventory.gem+=100000;

            Service.getInstance().sendMoney(playerTarger);

            Service.gI().chat(this, "Cảm ơn anh đã đưa tôi đến đây tôi tặng bạn 50 hồng ngọc ");
            // Service.gI().chat(this, "Cảm ơn thí chủ đã đưa tôi đến đây tôi tặng bạn " +
            // pointPvp++);
            this.leaveMap();
        }
        if (this.playerTarger != null && this.zone != null && this.zone.map.mapId != this.playerTarger.zone.map.mapId) {
            ChangeMapService.gI().changeMap(this, this.playerTarger.zone, this.playerTarger.location.x,
                    this.playerTarger.location.y);
        }
        if (Util.canDoWithTime(this.lastTimeAttack, 5000)) {
            Service.gI().chat(this,
                    "Anh zai hãy đưa ta đến " + MapService.gI().getMapById(this.mapCongDuc).mapName);
            this.lastTimeAttack = System.currentTimeMillis();
        }
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

            if (plAtt != null && this.playerTarger == plAtt) {
                damage = 0;
                Service.gI().chat(this, "Sao đại ca lại đánh bé? ");
            }

            if (damage > 1) {
                damage = 1;
            }

            this.nPoint.subHP(damage);

            if (this.nPoint.hp <= 10 && plAtt.isPl() && !plAtt.haveMiNuong) {
                this.nPoint.hp = this.nPoint.hpMax;

                if (playerTarger.typePk == ConstPlayer.PK_ALL) {
                    PlayerService.gI().changeAndSendTypePK(playerTarger, ConstPlayer.NON_PK);
                }
                ;

                playerTarger.haveMiNuong = false;// xoa ki la cua chu cu
                this.playerTarger = plAtt;// gan ki lan vao chu moi
                plAtt.haveMiNuong = true;// gan target cua no thanh chu moi
                Service.gI().chat(this, plAtt.name + " sẽ là chồng mới của bé !");

                if (playerTarger.typePk == ConstPlayer.NON_PK) {
                    PlayerService.gI().changeAndSendTypePK(playerTarger, ConstPlayer.PK_ALL);
                }
                Service.gI().point(this);
            }

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
        if (zoneFinal != null) {
            joinMapByZone(zoneFinal);
            this.notifyJoinMap();
            return;
        }
        if (this.zone == null) {
            if (this.parentBoss != null) {
                this.zone = parentBoss.zone;
            } else if (this.lastZone == null) {
                this.zone = getMapJoin();
            } else {
                this.zone = this.lastZone;
            }
        }
        if (this.zone != null) {
            if (this.currentLevel == 0) {
                if (this.parentBoss == null) {
                    ChangeMapService.gI().changeMap(this, this.zone, this.location.x, this.location.y);
                } else {
                    ChangeMapService.gI().changeMap(this, this.zone, this.location.x, this.location.y);
                    ;
                }
                // this.wakeupAnotherBossWhenAppear();
            } else {
                ChangeMapService.gI().changeMap(this, this.zone, this.location.x, this.location.y);
            }
            Service.getInstance().sendFlagBag(this);
            this.notifyJoinMap();
        }
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
        playerTarger.haveMiNuong = false;

    }
}
