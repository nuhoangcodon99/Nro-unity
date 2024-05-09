package red.team.map.KhiGasHuyDiet;

import com.unity.models.item.Item;
import com.unity.models.boss.list_boss.KhiGasHuyDiet.DrLychee;
import com.unity.models.boss.list_boss.KhiGasHuyDiet.Hatchiyack;
import red.team.map.KhiGasHuyDiet.KhiGasHuyDiet;
import static red.team.map.KhiGasHuyDiet.KhiGasHuyDiet.TIME_KHI_GA_HUY_DIET;
import com.unity.models.map.Zone;
import com.unity.models.mob.Mob;
import com.unity.models.player.Player;
import com.unity.models.services.InventoryServiceNew;
import com.unity.models.services.MapService;
import com.unity.models.services.Service;
import com.unity.models.services.func.ChangeMapService;
import com.unity.models.utils.Logger;
import com.unity.models.utils.Util;
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
                Item item = InventoryServiceNew.gI().findItemBag(player, 14);
                Item item2 = InventoryServiceNew.gI().findItemBag(player, 611);

                if (item != null && item2 != null && item.quantity > 0 && item2.quantity > 0) {
                
                    KhiGasHuyDiet khiGaHuyDiet = null;
                    for (KhiGasHuyDiet kghd : KhiGasHuyDiet.KHI_GA_HUY_DIETS) {
                        if (!kghd.isOpened) {
                            khiGaHuyDiet = kghd;
                            break;
                        }
                    }
                    if (khiGaHuyDiet != null) {
                        InventoryServiceNew.gI().subQuantityItemsBag(player, item, 1);
                        InventoryServiceNew.gI().sendItemBags(player);
                    
                        khiGaHuyDiet.openKhiGaHuyDiet(player, player.clan, level);
                        try {
                            long bossDamage = (20 * level);
                            long bossMaxHealth = (2 * level);
                            bossDamage = Math.min(bossDamage, 200000000L);
                            bossMaxHealth = Math.min(bossMaxHealth, 2000000000L);
                            DrLychee boss = new DrLychee(
                                    player.clan.KhiGaHuyDiet.getMapById(188),
                                    player.clan.KhiGaHuyDiet.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                            Hatchiyack boss2 = new Hatchiyack(
                                    player.clan.KhiGaHuyDiet.getMapById(188),
                                    player.clan.KhiGaHuyDiet.level,
                                    
                                    (int) bossDamage,
                                    (int) bossMaxHealth
                            );
                        } catch (Exception exception) {
                            Logger.logException(KhiGasHuyDietService.class, exception, "Error initializing boss");
                        }
                    } else {
                        Service.getInstance().sendThongBao(player, "Khí gas đang đầy, cút đi chỗ khác mà chơi..");
                    }
                } else {
                    Service.getInstance().sendThongBao(player, "Cần 1 bản đồ và 1 viên 1s ??");
                }
            } else {
                Service.getInstance().sendThongBao(player, "Không thể thực hiện");
            }
        } else {
            Service.getInstance().sendThongBao(player, "Không thể thực hiện");
        }
    }
}

