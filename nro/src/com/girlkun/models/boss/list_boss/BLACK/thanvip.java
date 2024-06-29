package com.girlkun.models.boss.list_boss.BLACK;

import com.girlkun.models.boss.list_boss.Boss.*;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.list_boss.android.*;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.models.skill.Skill;
import com.girlkun.server.Client;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerNotify;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.SkillService;
import com.girlkun.services.TaskService;
import com.girlkun.utils.SkillUtil;
import com.girlkun.utils.Util;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class thanvip extends Boss {
    private long lastTimeHapThu;
    private int timeHapThu;

    public thanvip() throws Exception {
        
        super(BossID.THANVIP, BossesData.THANVIP);
    }
    
    public List<Player> PlayerPlAtt = new ArrayList<>();

    public void addPlayerPlAtt(Player pl) {
        if (!this.PlayerPlAtt.contains(pl) && pl.isPl()) {
            PlayerPlAtt.add(pl);
        }
    }

    public int gethour() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.get(11);
    }

    public String df(double sfm) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(sfm);
    }
    
   public void reward(Player plKill) {
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1048, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(47, Util.nextInt(2678, 3334)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1049, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(47, Util.nextInt(2678, 3334)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1050, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(47, Util.nextInt(2678, 3334)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1051, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(22, Util.nextInt(260, 333)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1052, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(22, Util.nextInt(260, 333)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1053, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(22, Util.nextInt(260, 333)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(7, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1054, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(0, Util.nextInt(15078, 25674)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(7, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1055, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(0, Util.nextInt(15078, 25674)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(7, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1056, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(0, Util.nextInt(15078, 25674)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1057, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(23, Util.nextInt(210, 335)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1058, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(23, Util.nextInt(210, 335)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1059, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(23, Util.nextInt(218, 335)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1060, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(14, Util.nextInt(21, 33)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1061, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(14, Util.nextInt(21, 33)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(10, 100)) {
            ItemMap caitrangmi = new ItemMap(this.zone, 1062, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x,this.location.y - 24), plKill.id);
            caitrangmi.options.add(new Item.ItemOption(14, Util.nextInt(21, 33)));
            caitrangmi.options.add(new Item.ItemOption(209, 0));
            caitrangmi.options.add(new Item.ItemOption(21, 120));
            Service.getInstance().dropItemMap(this.zone, caitrangmi);
        }
        if (Util.isTrue(80, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 14, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
        if (Util.isTrue(80, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 15, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
        if (Util.isTrue(80, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 16, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
        if (Util.isTrue(80, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 674, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
        if (Util.isTrue(80, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1421, 10, this.location.x, this.location.y, plKill.id));
                return;
        }
    }
    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(30, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            Util.isTrue(this.nPoint.tlNeDon, 10000000);
            int hpHoi = Util.nextInt(1000000, 5000000);
            PlayerService.gI().hoiPhuc(this, hpHoi, 0);
            this.nPoint.dameg += 10000;
            if (Util.isTrue(50, 100)) {
                this.chat("Hãy để bản năng tự vận động");
                this.chat("Tránh các động tác thừa");
            } else if (Util.isTrue(50, 100)) {
                this.chat("Chậm lại,các ngươi quá nhanh rồi");
                this.chat("Chỉ cần hoàn thiện nó!");
                this.chat("Các ngươi sẽ tránh được mọi nguy hiểm");
            } else if (Util.isTrue(50, 100)) {
                this.chat("Đây chính là bản năng vô cực");
            }
            damage = 0;

        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage/3);
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
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();     
    }

    @Override
    public void joinMap() {
        super.joinMap(); // To change body of generated methods, choose Tools | Templates.
    }

}