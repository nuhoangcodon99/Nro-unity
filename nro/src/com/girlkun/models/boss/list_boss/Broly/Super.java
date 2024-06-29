package com.girlkun.models.boss.list_boss.Broly;

import com.girlkun.models.boss.list_boss.*;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.*;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.PetService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

/**
 * @Stole By BKT
 */
public class Super extends Boss {

    public Super(int bossID, BossData bossData, Zone zone) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
    }

    @Override
     public void reward(Player plKill) {
        ItemMap it = new ItemMap(this.zone, 568, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,
                this.location.y - 24), plKill.id);
        Service.gI().dropItemMap(this.zone, it);
    }

    @Override
    public void active() {
        Player player = this.zone.getPlayerInMap(id);
        if (player != null) {
            if (Util.canDoWithTime(st, 500)) {
                if (this.nPoint.hpMax < 160707770) {
                    this.nPoint.hpMax += this.nPoint.hpMax / 100;
                }
                if (this.nPoint.hpMax > 160707770) {
                    this.nPoint.hpMax = 160707770;
                }
            }
        }
        super.active();
        this.nPoint.dame = this.nPoint.hpMax / 100;//To change body of generated methods, choose Tools | Templates.
        if (!player.isBoss) {
            if (Util.canDoWithTime(st, 900000)) {
                if (player == null) {
                    this.changeStatus(BossStatus.LEAVE_MAP);
                    if (Util.isTrue(1, 100)) {
                        BossData Super = new BossData(
                                "Super Broly " + Util.nextInt(100),
                                this.gender,
                                new short[]{294, 295, 296, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
                                Util.nextInt(100000, 200000), //dame
                                new long[]{Util.nextInt(10000000, 160707770)}, //hp
                                new int[]{6}, //map join
                                new int[][]{
                                    {Skill.ANTOMIC, 7, 100}, {Skill.MASENKO, 7, 100},
                                    {Skill.KAMEJOKO, 7, 100},
                                    {Skill.KHIEN_NANG_LUONG, 5, 150000}},
                                new String[]{"|-2|SuperBroly"}, //text chat 1
                                new String[]{"|-1|Ọc Ọc"}, //text chat 2
                                new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"}, //text chat 3
                                60
                        );

                        try {
                            new Super(Util.createIdBossClone((int) this.id), Super, this.zone);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (plAtt != null) {
            switch (plAtt.playerSkill.skillSelect.template.id) {
                case Skill.ANTOMIC:
                case Skill.DEMON:
                case Skill.DRAGON:
                case Skill.GALICK:
                case Skill.KAIOKEN:
                case Skill.KAMEJOKO:
                case Skill.DICH_CHUYEN_TUC_THOI:
                case Skill.LIEN_HOAN:
                    damage = this.nPoint.hpMax / 100;
                    if (this.nPoint.hp < 1) {
                        this.setDie(plAtt);
                        die(this);
                    }
                    return super.injured(plAtt, damage, !piercing, isMobAttack);
            }
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
}
