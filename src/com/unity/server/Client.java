package com.unity.server;

import com.girlkun.database.GirlkunDB;
import com.girlkun.network.server.GirlkunSessionManager;
import com.girlkun.network.session.ISession;
import kygui.ShopKyGuiManager;
import com.unity.models.jdbc.daos.PlayerDAO;
import com.unity.models.item.Item;
import com.unity.models.map.ItemMap;
import com.unity.models.matches.pvp.DaiHoiVoThuat;
import com.unity.models.matches.pvp.DaiHoiVoThuatService;
import com.unity.models.player.Player;
import com.unity.models.server.io.MySession;
import com.unity.models.services.InventoryServiceNew;
import com.unity.models.services.ItemTimeService;
import com.unity.models.services.NgocRongNamecService;
import com.unity.models.services.Service;
import com.unity.models.services.func.ChangeMapService;
import com.unity.models.services.func.SummonDragon;
import com.unity.models.services.func.TransactionService;
import com.unity.models.utils.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements Runnable {

    private static Client i;

    private final Map<Long, Player> players_id = new HashMap<Long, Player>();
    private final Map<Integer, Player> players_userId = new HashMap<Integer, Player>();
    private final Map<String, Player> players_name = new HashMap<String, Player>();
    private final List<Player> players = new ArrayList<>();

    private boolean running = true;

    private Client() {
        new Thread(this).start();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public static Client gI() {
        if (i == null) {
            i = new Client();
        }
        return i;
    }

    public void put(Player player) {
        if (!players_id.containsKey(player.id)) {
            this.players_id.put(player.id, player);
        }
        if (!players_name.containsValue(player)) {
            this.players_name.put(player.name, player);
        }
        if (!players_userId.containsValue(player)) {
            this.players_userId.put(player.getSession().userId, player);
        }
        if (!players.contains(player)) {
            this.players.add(player);
        }

    }

    private void remove(MySession session) {
        if (session.player != null) {
            this.remove(session.player);
            session.player.dispose();
        }
        if (session.joinedGame) {
            session.joinedGame = false;
            try {
                GirlkunDB.executeUpdate("update account set last_time_logout = ? where id = ?", new Timestamp(System.currentTimeMillis()), session.userId);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.logException(Manager.class, e, "Lỗi remove client");
            }
        }
        ServerManager.gI().disconnect(session);
    }

    private void remove(Player player) {
        this.players_id.remove(player.id);
        this.players_name.remove(player.name);
        this.players_userId.remove(player.getSession().userId);
        this.players.remove(player);
        if (!player.beforeDispose) {
            DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).removePlayerWait(player);  
            DaiHoiVoThuatService.gI(DaiHoiVoThuat.gI().getDaiHoiNow()).removePlayer(player);  
            player.beforeDispose = true;
            player.mapIdBeforeLogout = player.zone.map.mapId;
            if(player.idNRNM != -1){
                ItemMap itemMap = new ItemMap(player.zone, player.idNRNM, 1, player.location.x, player.location.y, -1);
                Service.gI().dropItemMap(player.zone, itemMap);
                NgocRongNamecService.gI().pNrNamec[player.idNRNM - 353] = "";
                NgocRongNamecService.gI().idpNrNamec[player.idNRNM - 353] = -1;
                player.idNRNM = -1;
            }
            ChangeMapService.gI().exitMap(player);
            TransactionService.gI().cancelTrade(player);
            if (player.clan != null) {
                player.clan.removeMemberOnline(null, player);
            }
           if (player.itemTime != null && player.itemTime.isUseTDLT) {
               Item tdlt = null;
               try {
                   tdlt = InventoryServiceNew.gI().findItemBag(player, 521);
               } catch (Exception ex) {
               }
               if (tdlt != null) {
                   ItemTimeService.gI().turnOffTDLT(player, tdlt);
               }
           }
            if (SummonDragon.gI().playerSummonShenron != null
                    && SummonDragon.gI().playerSummonShenron.id == player.id) {
                SummonDragon.gI().isPlayerDisconnect = true;
            }
            if (player.mobMe != null) {
                player.mobMe.mobMeDie();
            }
            if (player.pet != null) {
                if (player.pet.mobMe != null) {
                    player.pet.mobMe.mobMeDie();
                }
                ChangeMapService.gI().exitMap(player.pet);
            }
        }
        PlayerDAO.updatePlayer(player);
    }

    public void kickSession(MySession session) {
        if (session != null) {
            this.remove(session);
            session.disconnect();
        }
    }

    public Player getPlayer(long playerId) {
        return this.players_id.get(playerId);
    }

    public Player getPlayerByUser(int userId) {
        return this.players_userId.get(userId);
    }

    public Player getPlayer(String name) {
        return this.players_name.get(name);
    }

    public void close() {
        Logger.error("BEGIN KICK OUT SESSION.............................." + players.size() + "\n");
       while(!GirlkunSessionManager.gI().getSessions().isEmpty()){
           Logger.error("LEFT PLAYER: " + this.players.size() + ".........................\n");
           this.kickSession((MySession) GirlkunSessionManager.gI().getSessions().remove(0));
       }
        while (!players.isEmpty()) {
            this.kickSession((MySession) players.remove(0).getSession());
        }
        Logger.error("...........................................SUCCESSFUL\n");
    }

    public void cloneMySessionNotConnect() {
        Logger.error("BEGIN KICK OUT MySession Not Connect...............................\n");
        Logger.error("COUNT: " + GirlkunSessionManager.gI().getSessions().size());
        if (!GirlkunSessionManager.gI().getSessions().isEmpty()) {
            for (int j = 0; j < GirlkunSessionManager.gI().getSessions().size(); j++) {
                MySession m = (MySession) GirlkunSessionManager.gI().getSessions().get(j);
                if (m.player == null) {
                    this.kickSession((MySession) GirlkunSessionManager.gI().getSessions().remove(j));
                }
            }
        }
        Logger.error("..........................................................SUCCESSFUL\n");
    }

    private void update() {
        for (ISession s : GirlkunSessionManager.gI().getSessions()) {
            MySession session = (MySession) s;
            if (session.timeWait > 0) {
                session.timeWait--;
                if (session.timeWait == 0) {
                    kickSession(session);
                }
            }
        }
    }

    @Override
    public void run() {
        while (ServerManager.isRunning) {
            try {
                long st = System.currentTimeMillis();
                update();
                Thread.sleep(800 - (System.currentTimeMillis() - st));
            } catch (Exception e) {
            }
            
            ShopKyGuiManager.gI().save();
        }


    }

    public void show(Player player) {
        String txt = "";
        txt += "sessions: " + GirlkunSessionManager.gI().getSessions().size() + "\n";
        txt += "players_id: " + players_id.size() + "\n";
        txt += "players_userId: " + players_userId.size() + "\n";
        txt += "players_name: " + players_name.size() + "\n";
        txt += "players: " + players.size() + "\n";
        Service.gI().sendThongBao(player, txt);
    }
}
