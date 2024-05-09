package com.unity.models.npc.specialnpc;

import com.girlkun.network.io.Message;
import com.unity.models.player.Player;
import com.unity.models.services.PetService;
import com.unity.models.services.Service;
import com.unity.models.services.func.ChangeMapService;
import com.unity.models.utils.Logger;
import com.unity.models.utils.Util;


public class NgokhongEgg {

   private static final long DEFAULT_TIME_DONE = 2592000000L;
    // private static final long DEFAULT_TIME_DONE = 86400000L;

    private Player player;
    public long lastTimeCreate;
    public long timeDone;

    private final short id = 93;

    public NgokhongEgg(Player player, long lastTimeCreate, long timeDone) {
        this.player = player;
        this.lastTimeCreate = lastTimeCreate;
        this.timeDone = timeDone;
    }

    public static void createNgokhongEgg(Player player) {
        player.ngokhongEgg = new NgokhongEgg(player, System.currentTimeMillis(), DEFAULT_TIME_DONE);
    }

    public void sendNgokhongEgg() {
        Message msg;
        try {
//            Message msg = new Message(-117);
//            msg.writer().writeByte(100);
//            player.sendMessage(msg);
//            msg.cleanup();

            msg = new Message(-122);
            msg.writer().writeShort(this.id);
            msg.writer().writeByte(1);
            msg.writer().writeShort(4664);
            msg.writer().writeByte(0);
            msg.writer().writeInt(this.getSecondDone());
            this.player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(NgokhongEgg.class, e);
        }
    }

    public int getSecondDone() {
        int seconds = (int) ((lastTimeCreate + timeDone - System.currentTimeMillis()) / 1000);
        return seconds > 0 ? seconds : 0;
    }

    public void openEgg(int gender) {
        if (this.player.pet != null) {
            try {
                destroyEgg();
                Thread.sleep(4000);
                if (this.player.pet == null) {
                    PetService.gI().createNgokhongPet(this.player, gender);
                } else {
                    PetService.gI().changeNgokhongPet(this.player, gender);
                }
                ChangeMapService.gI().changeMapInYard(this.player, this.player.gender * 7, -1, Util.nextInt(300, 500));
                player.ngokhongEgg = null;
            } catch (Exception e) {
            }
        } else {
            Service.gI().sendThongBao(player, "Yêu cầu phải có đệ tử");
        }
    }

    public void destroyEgg() {
        // try {
        //     Message msg = new Message(-117);
        //     msg.writer().writeByte(101);
        //     player.sendMessage(msg);
        //     msg.cleanup();
        // } catch (Exception e) {
        // }
        this.player.ngokhongEgg = null;
    }

    public void subTimeDone(int d, int h, int m, int s) {
        this.timeDone -= ((d * 24 * 60 * 60 * 1000) + (h * 60 * 60 * 1000) + (m * 60 * 1000) + (s * 1000));
        this.sendNgokhongEgg();
    }
    
    public void dispose(){
        this.player = null;
    }
}
