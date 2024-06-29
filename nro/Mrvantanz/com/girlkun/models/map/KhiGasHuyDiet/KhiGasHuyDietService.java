package com.girlkun.models.map.KhiGasHuyDiet;

import com.girlkun.models.item.Item;
import com.girlkun.models.boss.list_boss.KhiGasHuyDiet.DrLychee;
import com.girlkun.models.boss.list_boss.KhiGasHuyDiet.Hatchiyack;
import com.girlkun.models.map.KhiGasHuyDiet.KhiGasHuyDiet;
import static com.girlkun.models.map.KhiGasHuyDiet.KhiGasHuyDiet.TIME_KHI_GA_HUY_DIET;
import com.girlkun.models.map.Zone;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;
import java.util.List;

/**
 *
 * @author BTH
 *
 */
public class KhiGasHuyDietService {

    private static KhiGasHuyDietService i;

    private KhiGasHuyDietService() {

    }

    public static KhiGasHuyDietService gI() {
        if (i == null) {
            i = new KhiGasHuyDietService();
        }
        return i;
    }
    
    public void openKhiGaHuyDiet(Player player, byte level) {
        if (level >= 1 && level <= 110) {
            if (player.clan != null && player.clan.KhiGaHuyDiet == null) {
                
                    KhiGasHuyDiet khiGaHuyDiet = null;
                    for (KhiGasHuyDiet kghd : KhiGasHuyDiet.KHI_GA_HUY_DIETS) {
                        if (!kghd.isOpened) {
                            khiGaHuyDiet = kghd;
                            break;
                        }
                    }
                    if (khiGaHuyDiet != null) {
                        khiGaHuyDiet.openKhiGaHuyDiet(player, player.clan, level);
                        try {
                            long bossDamage = (20 * level);
                            long bossMaxHealth = (2 * level);
                            bossDamage = Math.min(bossDamage, 200000000L);
                            bossMaxHealth = Math.min(bossMaxHealth, 2000000000L);
                            DrLychee boss = new DrLychee(
                                    player.clan.KhiGaHuyDiet.getMapById(150),
                                    player.clan.KhiGaHuyDiet.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                            Hatchiyack boss2 = new Hatchiyack(
                                    player.clan.KhiGaHuyDiet.getMapById(150),
                                    player.clan.KhiGaHuyDiet.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                        } catch (Exception exception) {
                            Logger.logException(KhiGasHuyDietService.class, exception, "Error initializing boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Khí Ga Hủy Diệt đã đầy, vui lòng quay lại sau");
                    }
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } 
    }

