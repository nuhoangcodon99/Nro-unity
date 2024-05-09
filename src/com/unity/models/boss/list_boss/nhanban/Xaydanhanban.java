package red.team.boss.list_boss.nhanban;

import com.unity.models.consts.ConstPlayer;
import com.unity.models.boss.Boss;
import com.unity.models.boss.BossData;
import com.unity.models.boss.BossManager;
import com.unity.models.map.ItemMap;
import com.unity.models.map.Zone;
import com.unity.models.player.Player;
import com.unity.server.Client;
import com.unity.models.services.EffectSkillService;
import com.unity.models.services.Service;
import com.unity.models.utils.Util;

/**
 * @Stole By Lucy#0800
 */
public class Xaydanhanban extends Boss {

    public Xaydanhanban(int bossID, BossData bossData, Zone zone, Player plTarget) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
        this.lockPlayerTarget = plTarget;
    }

    @Override
    public void reward(Player plKill) {

    }

    @Override
    public void active() {
        super.active();
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        if (this.playerTarger != null && Client.gI().getPlayer(this.playerTarger.id) == null) {
            playerTarger.haveNhanBan = false;
            this.leaveMap();
        }
        if (this.playerTarger != null && this.playerTarger.isDie()) {
            playerTarger.haveNhanBan = false;
            this.leaveMap();
        }
        if (this.playerTarger != null && this.playerTarger.zone != this.zone) {
            playerTarger.haveNhanBan = false;
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

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
        playerTarger.haveNhanBan = false;
    }
}
