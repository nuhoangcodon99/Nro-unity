package com.girlkun.models.map.ConDuongRanDoc;

import com.girlkun.models.boss.list_boss.ConDuongRanDoc.Saibamen;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Logger;
/**
 *
 * @author BTH
 *
 */
public class ConDuongRanDocService {

    private static ConDuongRanDocService i;

    private ConDuongRanDocService() {

    }

    public static ConDuongRanDocService gI() {
        if (i == null) {
            i = new ConDuongRanDocService();
        }
        return i;
    }
    
    public void openConDuongRanDoc(Player player, byte level) {
        if (level >= 1 && level <= 110) {
            if (player.clan != null && player.clan.ConDuongRanDoc == null) {
                
                    ConDuongRanDoc conDuongRanDoc = null;
                    for (ConDuongRanDoc cdrd : ConDuongRanDoc.CON_DUONG_RAN_DOCS) {
                        if (!cdrd.isOpened) {
                            conDuongRanDoc = cdrd;
                            break;
                        }
                    }
                    if (conDuongRanDoc != null) {
                        conDuongRanDoc.openConDuongRanDoc(player, player.clan, level);
                        try {
                            long bossDamage = (20 * level);
                            long bossMaxHealth = (2 * level);
                            bossDamage = Math.min(bossDamage, 200000000L);
                            bossMaxHealth = Math.min(bossMaxHealth, 2000000000L);
                            
//                            new Saibamen(player.clan.ConDuongRanDoc.getMapById(54),
//                                        (int) bossDamage,
//                                    (int) bossMaxHealth
//                 );    
                            Saibamen boss = new Saibamen(
                                    player.clan.ConDuongRanDoc.getMapById(144),
                                    player.clan.ConDuongRanDoc.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                        } catch (Exception exception) {
                            Logger.logException(ConDuongRanDocService.class, exception, "Error initializing boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Con đường rắn độc đã đầy, vui lòng quay lại sau");
                    }
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } 
    }

