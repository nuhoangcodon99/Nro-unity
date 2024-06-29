package com.girlkun.models.boss.list_boss.cell;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import com.girlkun.services.PlayerService;
import java.util.Random;


public class SieuBoHung extends Boss {
   
    public SieuBoHung() throws Exception {
        super(BossID.SIEU_BO_HUNG, BossesData.SIEU_BO_HUNG_1, BossesData.SIEU_BO_HUNG_2, BossesData.SIEU_BO_HUNG_3);
    }

   @Override
    public void reward(Player plKill) {
        int[] itemDos = new int[]{14,15,16,2003,2004,2005};
        int[] NRs = new int[]{2003,2004,2005};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(100, 100)) {
            if (Util.isTrue(30, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 674, 10, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
             } else if (Util.isTrue(50, 100)) {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
        if (Util.isTrue(100, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 1421, 20, this.location.x, this.location.y, plKill.id));
                return;
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
     @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
   
        this.attack();
    }

  
//    @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
//
//    @Override
//    public void reward(Player plKill) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.reward(plKill);
//    }
//    
//    @Override
//    protected void notifyJoinMap() {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.notifyJoinMap();
//    }
}






















