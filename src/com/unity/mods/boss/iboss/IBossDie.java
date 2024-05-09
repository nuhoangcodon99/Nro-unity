



















package com.unity.mods.boss.iboss;

import com.unity.models.player.Player;


public interface IBossDie {
    
    void doSomeThing(Player playerKill);

    void notifyDie(Player playerKill);

    void rewards(Player playerKill);

    void leaveMap();

}






















