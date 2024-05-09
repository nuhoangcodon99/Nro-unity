package com.unity.models.matches.pvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import com.unity.models.player.Player;
import com.unity.server.Manager;
import com.unity.models.utils.Util;
import kygui.ShopKyGuiManager;


public class DaiHoiVoThuat implements Runnable{
    public ArrayList<Player> listReg = new ArrayList<>();
    public ArrayList<Player> listPlayerWait = new ArrayList<>();
    public String NameCup;
    public String[] Time;
    public int gem;
    public int gold;
    public int min_start;
    public int min_start_temp;
    public int min_limit;
    public int round = 1;
    
    public int Hour;
    public int Minutes;
    public int Second;
    
    private static DaiHoiVoThuat instance;    

    public static DaiHoiVoThuat gI() {
        if (instance == null) {
            instance = new DaiHoiVoThuat();
        }
        return instance;
    }
    
    public DaiHoiVoThuat getDaiHoiNow(){
        for(DaiHoiVoThuat dh : Manager.LIST_DHVT){
            if(dh != null && Util.contains(dh.Time, String.valueOf(Hour))){
                return dh;
            }
        }
        return null;
    }
    
    public String Info() {
        for(DaiHoiVoThuat daihoi : Manager.LIST_DHVT){
            if (daihoi.gold > 0) {
                return "Lịch thi đấu trong ngày\bGiải " + daihoi.NameCup + ": " + Arrays.toString(daihoi.Time).replace("[", "").replace("]", "") + "h\nLệ phí đăng ký thi đấu\bGiải " + daihoi.NameCup + ": " + Util.powerToString(daihoi.gold) + " vàng\b";
            } else if (daihoi.gem > 0) {
                return "Lịch thi đấu trong ngày\bGiải " + daihoi.NameCup + ": " + Arrays.toString(daihoi.Time).replace("[", "").replace("]", "") + "h\nLệ phí đăng ký thi đấu\bGiải " + daihoi.NameCup + ": " + Util.powerToString(daihoi.gem) + " ngọc\b";
            }
        }
        return "Không có giải đấu nào được tổ chức\b";
    }
    
    @Override
    public void run() {
        while (true) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            try { 
                Second = calendar.get(Calendar.SECOND);
                Minutes = calendar.get(Calendar.MINUTE);
                Hour = calendar.get(Calendar.HOUR_OF_DAY);
                if(Util.canDoWithTime(ShopKyGuiManager.gI().lastTimeUpdate, 1800000)){
                    ShopKyGuiManager.gI().lastTimeUpdate = System.currentTimeMillis();
                    ShopKyGuiManager.gI().save();
                }
                DaiHoiVoThuatService.gI(getDaiHoiNow()).Update();
                Thread.sleep(1000);
            }catch(Exception e){
            }
        }
    }

    public void initDaiHoiVoThuat() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}