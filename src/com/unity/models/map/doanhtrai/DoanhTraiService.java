package com.unity.models.map.doanhtrai;

import com.unity.models.map.Zone;
import com.unity.models.map.doanhtrai.DoanhTrai;
import com.unity.models.player.Player;
import com.unity.models.services.ItemTimeService;
import com.unity.models.services.MapService;
import com.unity.models.services.Service;
import com.unity.models.services.func.ChangeMapService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucy
 */
public class DoanhTraiService {

    private static DoanhTraiService I;

    public static DoanhTraiService gI() {
        if (DoanhTraiService.I == null) {
            DoanhTraiService.I = new DoanhTraiService();
        }
        return DoanhTraiService.I;
    }

    public List<DoanhTrai> doanhTrais;

    private DoanhTraiService() {
        this.doanhTrais = new ArrayList<>();
        for (int i = 0; i < DoanhTrai.AVAILABLE; i++) {
            this.doanhTrais.add(new DoanhTrai(i));
        }
    }

    public void addMapDoanhTrai(int id, Zone zone) {
        this.doanhTrais.get(id).getZones().add(zone);
    }

    public void joinDoanhTrai(Player pl) {
        if (pl.clan == null) {
            Service.getInstance().sendThongBao(pl, "Không thể thực hiện");
            return;
        }
        if (pl.clan.doanhTrai != null) {
            ChangeMapService.gI().changeMapInYard(pl, 53, -1, 60);
            return;
        }
        DoanhTrai doanhTrai = null;
        for (DoanhTrai dt : this.doanhTrais) {
            if (dt.getClan() == null) {
                doanhTrai = dt;
                break;
            }
        }
        if (doanhTrai == null) {
            Service.getInstance().sendThongBao(pl, "Doanh trại đã đầy, hãy quay lại vào lúc khác!");
            return;
        }
        doanhTrai.openDoanhTrai(pl);
        ItemTimeService.gI().sendTextDoanhTrai(pl);
    }

    private void ketthucDoanhTrai(Player player) {
        List<Player> playersMap = player.zone.getPlayers();
        if (playersMap.isEmpty() && player == null && player.zone == null && player.isNewPet && !player.isPl()) {
            return;
        }
        for (int i = playersMap.size() - 1; i >= 0; i--) {
            Player pl = playersMap.get(i);
            if (MapService.gI().isMapDoanhTrai(pl.zone.map.mapId)) {
                /**
                 * Đưa mọi người trong map doanh trại về nhà
                 */
                if (MapService.gI().isMapDoanhTrai(pl.zone.map.mapId)) {
                    if (pl != null && pl.zone != null && !pl.isNewPet && pl.isPl()) {
                        Service.getInstance().sendThongBao(pl, "Trận đại chiến đã kết thúc, tàu vận chuyển sẽ đưa bạn về nhà");
                        ItemTimeService.gI().sendTextTime(pl, (byte) 0, "Aurura", 0);
                        ChangeMapService.gI().changeMapBySpaceShip(pl, pl.gender + 21, -1, 250);
                        if (pl.clan.doanhTrai != null) {
                            pl.clan.doanhTrai.dispose();
                            pl.clan.doanhTrai = null;
                        }
                    }
                }
            }
        }
    }

    public void updatePlayer(Player player) {
        if (player.zone == null || !MapService.gI().isMapDoanhTrai(player.zone.map.mapId)) {
            return;
        }
        if (player.isPl()) {
            try {
                if (player.clan != null && player.clan.doanhTrai != null) {
                    long now = System.currentTimeMillis();
                    if (!(now >= player.clan.doanhTrai_lastTimeOpen && now <= (player.clan.doanhTrai_lastTimeOpen + DoanhTrai.TIME_DOANH_TRAI))) {
                        ketthucDoanhTrai(player);
                    } else if (player.clan.doanhTrai.timePickDragonBall && (System.currentTimeMillis() - player.clan.doanhTrai.getLastTimeOpen() > 5000)) {
                        ketthucDoanhTrai(player);
                    }
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
                System.out.println("Lỗi update ở class Doanh trại");
            }
        }
    }
}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - Khánh đê zéc
 */
