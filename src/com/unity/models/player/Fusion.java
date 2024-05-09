package com.unity.models.player;

import com.unity.models.consts.ConstPlayer;
import com.unity.models.utils.Util;


public class Fusion {
    public boolean isBTC2;
    public boolean isBTC3;
     public boolean isBTC4;
    public static final int TIME_FUSION = 600000;

    private Player player;
    public byte typeFusion;
    public long lastTimeFusion;
   

    public Fusion(Player player) {
        this.player = player;
    }

    public void update() {
        if (typeFusion == ConstPlayer.LUONG_LONG_NHAT_THE && Util.canDoWithTime(lastTimeFusion, TIME_FUSION)) {
            this.player.pet.unFusion();
        }
    }
    
    public void dispose(){
        this.player = null;
    }

}
