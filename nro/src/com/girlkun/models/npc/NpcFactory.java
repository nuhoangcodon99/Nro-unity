package com.girlkun.models.npc;

import com.girlkun.models.map22h.Map22h;
import com.arriety.kygui.ItemKyGui;
import com.arriety.kygui.ShopKyGuiService;
import com.girlkun.consts.ConstMap;
import com.girlkun.consts.ConstNpc;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.consts.ConstTask;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.list_boss.NhanBan;
import com.girlkun.models.clan.Clan;
import com.girlkun.models.clan.ClanMember;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.MapMaBu.MapMaBu;
import com.girlkun.models.map.Zone;
import com.girlkun.models.map.bando.BanDoKhoBau;
import com.girlkun.models.map.bando.BanDoKhoBauService;
import com.girlkun.models.map.blackball.BlackBallWar;
import com.girlkun.models.map.challenge.MartialCongressService;
import com.girlkun.models.map.doanhtrai.DoanhTrai;
import com.girlkun.models.map.doanhtrai.DoanhTraiService;
import com.girlkun.models.map.nguhanhson.nguhs;
import com.girlkun.data.DataGame;
import com.girlkun.database.GirlkunDB;
import com.girlkun.jdbc.daos.PlayerDAO;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.boss.list_boss.Broly.ThachDauGoJo;
import com.girlkun.models.boss.list_boss.TrainOffline.Bill;
import com.girlkun.models.boss.list_boss.TrainOffline.MeoThan;
import com.girlkun.models.boss.list_boss.TrainOffline.ThanVuTru;
import com.girlkun.models.boss.list_boss.TrainOffline.Thuongde;
import com.girlkun.models.boss.list_boss.TrainOffline.ToSuKaio;
import com.girlkun.models.boss.list_boss.TrainOffline.Yajiro;
import com.girlkun.models.map.ConDuongRanDoc.ConDuongRanDoc;
import com.girlkun.models.map.ConDuongRanDoc.ConDuongRanDocService;
import com.girlkun.models.map.GiaiCuuMiNuong.GiaiCuuMiNuongService;
import com.arriety.MaQuaTang.MaQuaTangManager;
import com.girlkun.models.boss.list_boss.Broly.ThachDauWhis;
import com.girlkun.models.item.Item.ItemOption;
import com.girlkun.models.map.MapMaBu.MapMaBu2h;
import com.girlkun.models.map.daihoi.DaiHoiManager;
import com.girlkun.models.map.gas.Gas;
import com.girlkun.models.map.gas.GasService;
import com.girlkun.models.map.vodai.MartialCongressServices;
import com.girlkun.models.mapTruongMyLan.MapKhoBau11h;
import com.girlkun.models.matches.PVPService;
import com.girlkun.models.matches.TOP;
import com.girlkun.models.matches.pvp.DaiHoiVoThuat;
import com.girlkun.models.matches.pvp.DaiHoiVoThuatService;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Inventory;
import com.girlkun.models.player.NPoint;
import com.girlkun.models.player.Player;
import com.girlkun.models.shop.ShopServiceNew;
import com.girlkun.models.skill.Skill;
import com.girlkun.network.io.Message;
import com.girlkun.result.GirlkunResultSet;
import com.girlkun.server.Client;
import com.girlkun.server.Maintenance;
import com.girlkun.server.Manager;
import com.girlkun.server.ServerManager;
import com.girlkun.services.*;
import com.girlkun.services.func.*;
import static com.girlkun.services.func.CombineServiceNew.DAP_SET_KICH_HOAT_TS;
import static com.girlkun.services.func.CombineServiceNew.EP_CHUNG_NHAN_XUAT_SU;
import static com.girlkun.services.func.CombineServiceNew.EP_DA_DANH_HIEU;
import com.girlkun.utils.Logger;
import com.girlkun.utils.TimeUtil;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static com.girlkun.services.func.SummonDragon.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class NpcFactory {

    private static final int COST_HD = 50000000;

    private static boolean nhanVang = false;
    private static boolean nhanDeTu = false;

    //playerid - object
    public static final java.util.Map<Long, Object> PLAYERID_OBJECT = new HashMap<Long, Object>();

    private NpcFactory() {

    }
    private static Npc sukien(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào " + player.name + "\nTôi là npc sự kiện\nTôi có thể giúp gì cho bạn",
                            "Làm\nhộp thần linh td", "Làm\nhộp thần linh xd", "Đổi Hộp\nthần linh td");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU: {
                            switch (select) {
                                case 0:
                                    Item thitBaChi = InventoryServiceNew.gI().findItemBag(player, 1520);
                                    Item gaoNep = InventoryServiceNew.gI().findItemBag(player, 1521);
                                    Item doXanh = InventoryServiceNew.gI().findItemBag(player, 1522);
                                    Item laChuoi = InventoryServiceNew.gI().findItemBag(player, 1523);
                                    Item laChuoi99 = InventoryServiceNew.gI().findItemBag(player, 1523);
                                    if ((laChuoi99 != null && thitBaChi != null && gaoNep != null && doXanh != null && laChuoi != null)
                                            && (thitBaChi.quantity >= 999 && gaoNep.quantity >= 999
                                            && laChuoi99.quantity >= 999 && doXanh.quantity >= 999 && laChuoi.quantity >= 999)) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, thitBaChi, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, gaoNep, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, doXanh, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, laChuoi, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, laChuoi99, 999);
                                        Item banhtet = ItemService.gI().createNewItem((short) 1573);
                                        banhtet.itemOptions.add(new Item.ItemOption(73, 0));
                                        InventoryServiceNew.gI().addItemBag(player, banhtet);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.getInstance().sendThongBao(player, "Bạn nhận được rương thần linh");
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ nguyên liệu");
                                    }
                                    break;
                                case 1:
                                    Item thitBaChi1 = InventoryServiceNew.gI().findItemBag(player, 1520);
                                    Item gaoNep1 = InventoryServiceNew.gI().findItemBag(player, 1521);
                                    Item doXanh1 = InventoryServiceNew.gI().findItemBag(player, 1522);
                                    Item laChuoi1 = InventoryServiceNew.gI().findItemBag(player, 1523);
                                    if ((thitBaChi1 != null && gaoNep1 != null && doXanh1 != null && laChuoi1 != null)
                                            && (thitBaChi1.quantity >= 999 && gaoNep1.quantity >= 999
                                            && doXanh1.quantity >= 999 && laChuoi1.quantity >= 999)) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, thitBaChi1, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, gaoNep1, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, doXanh1, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, laChuoi1, 999);
                                        Item banhChung = ItemService.gI().createNewItem((short) 1574);
                                        banhChung.itemOptions.add(new Item.ItemOption(74, 0));
                                        InventoryServiceNew.gI().addItemBag(player, banhChung);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.getInstance().sendThongBao(player, "Bạn nhận được rương thiên sứ");
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ nguyên liệu");
                                    }
                                    break;
                                case 2:
                                    Item thitBaChi12 = InventoryServiceNew.gI().findItemBag(player, 1521);
                                    Item gaoNep12 = InventoryServiceNew.gI().findItemBag(player, 1522);
                                    Item doXanh12 = InventoryServiceNew.gI().findItemBag(player, 1523);
                                    Item laChuoi12 = InventoryServiceNew.gI().findItemBag(player, 1524);
                                    if ((thitBaChi12 != null && gaoNep12 != null && doXanh12 != null && laChuoi12 != null)
                                            && (thitBaChi12.quantity >= 999 && gaoNep12.quantity >= 999
                                            && doXanh12.quantity >= 999 && laChuoi12.quantity >= 999)) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, thitBaChi12, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, gaoNep12, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, doXanh12, 999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, laChuoi12, 999);
                                        Item banhChung = ItemService.gI().createNewItem((short) 1575);
                                        banhChung.itemOptions.add(new Item.ItemOption(74, 0));
                                        InventoryServiceNew.gI().addItemBag(player, banhChung);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.getInstance().sendThongBao(player, "Bạn nhận được hộp danh hiệu");
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ nguyên liệu");
                                    }
                                    break;
                                
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc gohanzombie(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Để tới Nghĩa Trang cần có vé du hành\n"
                                + "Thời gian còn lại có thể tới là " + Util.tinhgio(player.thoigianduhanh),
                                "Đến vũ trụ\nNghĩa Trang");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.thoigianduhanh > 0) {

                                    ChangeMapService.gI().changeMapBySpaceShip(player, 182, -1, 384);
                                } else {
                                    Service.gI().sendThongBao(player, "hết thời gian");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc noibanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 5) {
                    if (NauBanh.gI().NauXong == true) {
                        this.createOtherMenu(player, 0, "Bánh đã chín, Bạn có "
                                + Util.tinhgio(NauBanh.gI().ThoiGianChoLayBanh) + " để lấy\n|2|Nếu offline số bánh có thể được lấy vào đợt sau!",
                                "Lấy Bánh");
                    } else if (NauBanh.gI().ChoXong == true) {
                        this.createOtherMenu(player, 1, "|2|Nồi Bánh Toàn Server Đợt " + NauBanh.gI().Count
                                + "\n|-1|Đang trong thời gian nấu, bạn có thể cho thêm bánh vào nấu ké"
                                + "\nSố lượng bánh nấu sẽ không giới hạn"
                                + "\nThời gian nấu bánh còn: " + Util.tinhgio(NauBanh.gI().ThoiGianNau)
                                + "\nHiện tại có: " + (NauBanh.gI().ListPlNauBanh.size()) + " bánh đang nấu"
                                + "\nTrong đó bạn có: " + (NauBanh.gI().plBanhChung + NauBanh.gI().plBanhTet) + " bánh mới\n("
                                + (player.BanhChung + player.BanhTet) + " bánh trước đó chưa lấy)", "Nấu bánh chưng", "Nấu bánh tét", "Hướng dẫn");
                    } else if (NauBanh.gI().ChoXong == false) {
                        this.createOtherMenu(player, 4, "|2|Nồi Bánh Toàn Server Đợt " + NauBanh.gI().Count
                                + "\n|-1|Thời gian chờ nấu còn: " + Util.tinhgio(NauBanh.gI().ThoiGianCho)
                                + "\nMực nước trong nồi: " + Util.format(NauBanh.gI().Nuoc) + " % "
                                + (NauBanh.gI().Nuoc >= 50 && NauBanh.gI().Nuoc < 100 ? "(Trung bình)" : NauBanh.gI().Nuoc >= 100 ? "(Đã đầy)" : "(Thấp)")
                                + "\nSố củi đã thêm: " + NauBanh.gI().Cui
                                + "\nĐủ củi vả nước sẽ bắt đầu nấu"
                                + "\nThêm đủ nước để nồi không bị cháy và nhận đủ số bánh nấu"
                                + "\nThêm củi lửa để tăng tốc thời gian nấu bánh",
                                "Thêm nước nấu", "Thêm củi lữa", "Hướng dẫn");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        switch (player.iDMark.getIndexMenu()) {
                            case 1:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, 2, "Bánh chưng: 12 Lá giong, 2 Gạo nếp, 1 Đậu xanh, 12 Gióng tre, 3 Thịt lợn, 1 Muối và 03 Nước nấu.", "Nấu", "Đóng");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, 3, "Bánh tết: 12 Lá chuối, 2 Gạo nếp, 1 Đậu xanh, 12 Giống tre, 3 Thịt lợn, 1 Muối và 03 Nước nấu.", "Nấu", "Đóng");
                                        break;
                                }
                                break;
                            case 2:
                                Input.gI().createFormNauBanhChung(player);
                                break;
                            case 3:
                                Input.gI().createFormNauBanhTet(player);
                                break;
                        }
                        if (player.iDMark.getIndexMenu() == 0) {
                            if (player.BanhChung == 0 && player.BanhTet == 0) {
                                Service.gI().sendThongBao(player, "Có nấu gì đéo đâu mà đòi nhận");
                                return;
                            }
                            if (player.BanhTet != 0) {
                                Item BanhChung = ItemService.gI().createNewItem((short) 1446, player.BanhTet);
                                InventoryServiceNew.gI().addItemBag(player, BanhChung);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã nhận được bánh tét");
                                player.BanhTet = 0;
                            }
                            if (player.BanhChung != 0) {
                                Item BanhTet = ItemService.gI().createNewItem((short) 1445, player.BanhChung);
                                InventoryServiceNew.gI().addItemBag(player, BanhTet);
                                InventoryServiceNew.gI().sendItemBags(player);
                                Service.gI().sendThongBao(player, "Bạn đã nhận được bánh chưng");
                                player.BanhChung = 0;
                            }
                        } else if (player.iDMark.getIndexMenu() == 4) {
                            switch (select) {
                                case 0:
                                    Item nuocNau = InventoryServiceNew.gI().findItemBag(player, 1443);
                                    if (nuocNau == null) {
                                        Service.gI().sendThongBao(player, "Có nước nấu đâu cu");
                                        return;
                                    }
                                    if (NauBanh.gI().Nuoc < 100) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, nuocNau, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        NauBanh.gI().Nuoc++;
                                    } else {
                                        Service.gI().sendThongBao(player, "Đủ nước rồi cu");
                                    }
                                    break;
                                case 1:
                                    Item cuiLua = InventoryServiceNew.gI().findItemBag(player, 1444);
                                    if (cuiLua == null) {
                                        Service.gI().sendThongBao(player, "Có cui lửa đâu cú");
                                        return;
                                    }
                                    if (NauBanh.gI().Cui < 100) {
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, cuiLua, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        NauBanh.gI().Cui++;
                                        NauBanh.gI().ThoiGianNau -= (1000);
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc Vodai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU, "|1|Ta Có Thể Giúp Gì Cho Ngươi?",
                                "Đến Võ Đài Bà Hạt Mít", "Từ chối");
                    } else if (this.mapId == 112) {
                        int goldchallenge = pl.goldChallenge;
                        if (pl.levelWoodChest == 0) {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Về Đảo Kame"};
                        } else {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Nhận thưởng\nRương cấp\n" + pl.levelWoodChest, "Về Đảo Kame"};
                        }
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Võ Đài Bà Hạt Mít\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào", menuselect, "Từ chối");

                    } else {
                        super.openBaseMenu(pl);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.map.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 112, 203, 408);
                                    break;
                            }
                        }
                    } else if (this.mapId == 112) {
                        int goldchallenge = player.goldChallenge;
                        if (player.levelWoodChest == 0) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressServices.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 5, -1, 450, 288);
                                    break;
                            }
                        } else {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressServices.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    if (!player.receivedWoodChest) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                            Item it = ItemService.gI().createNewItem((short) 674);
                                            it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                            it.itemOptions.add(new Item.ItemOption(30, 0));
                                            it.createTime = System.currentTimeMillis();
                                            InventoryServiceNew.gI().addItemBag(player, it);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            player.receivedWoodChest = true;
                                            player.levelWoodChest = 0;
                                            Service.getInstance().sendThongBao(player, "Bạn nhận được rương ngọc rồng");
                                        } else {
                                            this.npcChat(player, "Hành trang đã đầy");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Mỗi ngày chỉ có thể nhận rương báu 1 lần");
                                    }
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMap(player, 5, -1, 1030, 408);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc vihop(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?",
                            "Đổi Đệ Vip");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(player, ConstNpc.BUY_DE_TU_VIP,
                                            "Xin chào, ta có một số bé pet vip nè!\n|1|Số tiền của bạn còn : " + player.getSession().vnd
                                            + "\n|7|Đệ Thiên Tử: 30k coin, Hợp thể tăng 30% chỉ số"
                                            + "\n|7|Đệ Black Goku: 40k coin, Hợp thể tăng 40% chỉ số"
                                            + "\n|7|Đệ Kaido: 50k coin, Hợp thể tăng 50% chỉ số"
                                            + "\n|7| Lưu Ý Phải Có Đệ Thường Mới Mua Được Đệ Vip",
                                            "Thiên Tử", "Black Goku", "Kaido");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.BUY_DE_TU_VIP) {
                            PreparedStatement ps = null;
                            try (Connection con = GirlkunDB.getConnection();) {
                                switch (select) {
                                    case 0:
                                        if (player.getSession().vnd < 30000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                            return;
                                        }
                                        if (player.pet == null) {
                                            Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                            return;
                                        }
                                        player.getSession().vnd -= 30000;
                                        PetService.gI().createThienTuPetVip(player, true, player.pet.gender);
                                        break;
                                    case 1:
                                        if (player.getSession().vnd < 40000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                            return;
                                        }
                                        if (player.pet == null) {
                                            Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                            return;
                                        }

                                        player.getSession().vnd -= 40000;
                                        PetService.gI().createBlackGokuPetVip(player, true, player.pet.gender);
                                        break;
                                    case 2:
                                        if (player.getSession().vnd < 50000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 50k coin");
                                            return;
                                        }
                                        if (player.pet == null) {
                                            Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                            return;
                                        }

                                        player.getSession().vnd -= 50000;
                                        PetService.gI().createKaidoPetVip(player, player.pet != null, player.pet.gender);
                                        break;
                                }
                                ps = con.prepareStatement("update account set vnd = ? where id = ?");
                                ps.setInt(1, player.getSession().vnd);
                                ps.setInt(2, player.getSession().userId);
                                ps.executeUpdate();
                                ps.close();

                            } catch (Exception e) {
                                Logger.logException(NpcFactory.class, e, "Lỗi update vnd " + player.name);
                            } finally {
                                try {
                                    if (ps != null) {
                                        ps.close();
                                    }
                                } catch (SQLException ex) {
                                    System.out.println("Lỗi khi update vnd");

                                }
                            }
                        }

                    }
                }
            }
        };
    }

    private static Npc gapthu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, 1234, "|7|- •⊹٭Ngọc Rồng UNITY٭⊹• -\nMÁY GẮP LINH THÚ\n" + "|3|GẮP THƯỜNG : 5-10% CHỈ SỐ\nGẮP CAO CẤP : 10-20% CHỈ SỐ\nGẮP VIP : 15-25% CHỈ SỐ" + "\nGẮP X1 : GẮP THỦ CÔNG\nGẮP X10 : AUTO X10 LẦN GẮP\nGẮP X100 : AUTO X100 LẦN GẮP\n" + "|7|LƯU Ý : MỌI CHỈ SỐ ĐỀU RANDOM KHÔNG CÓ OPTION NHẤT ĐỊNH\nNẾU MUỐN NGƯNG AUTO GẤP CHỈ CẦN THOÁT GAME VÀ VÀO LẠI!",
                                "Gắp Thường", "Gắp Cao Cấp", "Gắp VIP", "Xem Top", "Rương Đồ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.getIndexMenu() == 1234) {
                            switch (select) {
                                case 0:
                                    this.createOtherMenu(player, 12345, "|6|Gắp Thú Thường" + "\n|7|Tiến Hành Gắp",
                                            "Gắp x1", "Gắp x10", "Gắp x100", "Rương Đồ");
                                    break;
                                case 1:
                                    this.createOtherMenu(player, 12346, "|6|Gắp Thú Cao Cấp" + "\n|7|Tiến Hành Gắp",
                                            "Gắp x1", "Gắp x10", "Gắp x100", "Rương Đồ");
                                    break;
                                case 2:
                                    this.createOtherMenu(player, 12347, "|6|Gắp Thú VIP" + "\n|7|Tiến Hành Gắp",
                                            "Gắp x1", "Gắp x10", "Gắp x100", "Rương Đồ");
                                    break;
                                case 3:
                                    Service.gI().showListTop(player, Manager.TopGapThu);
                                    break;
                                case 4:
                                    this.createOtherMenu(player, ConstNpc.RUONG_PHU,
                                            "|1|Tình yêu như một dây đàn\n"
                                            + "Tình vừa được thì đàn đứt dây\n"
                                            + "Đứt dây này anh thay dây khác\n"
                                            + "Mất em rồi anh biết thay ai?",
                                            "Rương Phụ\n(" + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa Hết\nRương Phụ", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 12345) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                        this.createOtherMenu(player, 12345, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x1 lần");
                                        int timex1 = 1;
                                        int count = 0;
                                        while (timex1 > 0) {
                                            timex1--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394), 1);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                                this.createOtherMenu(player, 12345, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12345, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2019, 2020, 2021};
                                            Item gapx1 = Util.petrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx1);
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx1);
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 1:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                        this.createOtherMenu(player, 12345, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x10 lần");
                                        int timex10 = 10;
                                        int count = 0;
                                        while (timex10 > 0) {
                                            timex10--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394), 1);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                                this.createOtherMenu(player, 12345, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12345, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2019, 2020, 2021};
                                            Item gapx10 = Util.petrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx10);
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx10);
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 2:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                        this.createOtherMenu(player, 12345, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x100 lần");
                                        int timex100 = 100;
                                        int count = 0;
                                        while (timex100 > 0) {
                                            timex100--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394), 1);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394) == null) {
                                                this.createOtherMenu(player, 12345, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12345, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2019, 2020, 2021};
                                            Item gapx100 = Util.petrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx100);
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx100);
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12345, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1394).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.RUONG_PHU,
                                            "|1|Tình yêu như một dây đàn\n"
                                            + "Tình vừa được thì đàn đứt dây\n"
                                            + "Đứt dây này anh thay dây khác\n"
                                            + "Mất em rồi anh biết thay ai?",
                                            "Rương Phụ\n(" + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa Hết\nRương Phụ", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 12346) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                        this.createOtherMenu(player, 12346, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x1 lần");
                                        int timex1 = 1;
                                        int count = 0;
                                        while (timex1 > 0) {
                                            timex1--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395), 2);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                                this.createOtherMenu(player, 12346, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12346, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2022, 2023, 2024};
                                            Item gapx1 = Util.petccrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx1);
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx1);
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 1:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                        this.createOtherMenu(player, 12346, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x10 lần");
                                        int timex10 = 10;
                                        int count = 0;
                                        while (timex10 > 0) {
                                            timex10--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395), 2);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                                this.createOtherMenu(player, 12346, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12346, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2022, 2023, 2024};
                                            Item gapx10 = Util.petccrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx10);
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx10);
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 2:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                        this.createOtherMenu(player, 12346, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x100 lần");
                                        int timex100 = 100;
                                        int count = 0;
                                        while (timex100 > 0) {
                                            timex100--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395), 2);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395) == null) {
                                                this.createOtherMenu(player, 12346, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12346, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {2022, 2023, 2024};
                                            Item gapx100 = Util.petccrandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx100);
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx100);
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12346, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1395).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.RUONG_PHU,
                                            "|1|Tình yêu như một dây đàn\n"
                                            + "Tình vừa được thì đàn đứt dây\n"
                                            + "Đứt dây này anh thay dây khác\n"
                                            + "Mất em rồi anh biết thay ai?",
                                            "Rương Phụ\n(" + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa Hết\nRương Phụ", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == 12347) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                        this.createOtherMenu(player, 12347, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x1 lần");
                                        int timex1 = 1;
                                        int count = 0;
                                        while (timex1 > 0) {
                                            timex1--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396), 3);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                                this.createOtherMenu(player, 12347, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12347, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {1397, 1398, 1399, 1400, 1401, 1402, 1377};
                                            Item gapx1 = Util.petviprandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx1);
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx1);
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx1.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex1 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 1:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                        this.createOtherMenu(player, 12347, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x10 lần");
                                        int timex10 = 10;
                                        int count = 0;
                                        while (timex10 > 0) {
                                            timex10--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396), 3);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                                this.createOtherMenu(player, 12347, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12347, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {1397, 1398, 1399, 1400, 1401, 1402, 1377};
                                            Item gapx10 = Util.petviprandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx10);
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X10\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx10);
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx10.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex10 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 2:
                                    if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                        this.createOtherMenu(player, 12347, "|7|HẾT XU!",
                                                "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                        break;
                                    }
                                    try {
                                        Service.gI().sendThongBao(player, "Tiến hành auto gắp x100 lần");
                                        int timex100 = 100;
                                        int count = 0;
                                        while (timex100 > 0) {
                                            timex100--;
                                            count++;
                                            InventoryServiceNew.gI().subQuantityItemsBag(player, InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396), 3);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Thread.sleep(100);
                                            if (InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396) == null) {
                                                this.createOtherMenu(player, 12347, "|7|HẾT XU!\nSỐ LƯỢT ĐÃ GẮP : " + count,
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            if (1 + player.inventory.itemsBoxCrackBall.size() > 200) {
                                                this.createOtherMenu(player, 12347, "|7|DỪNG AUTO GẮP, RƯƠNG PHỤ ĐÃ ĐẦY!\n" + "|2|TỔNG LƯỢT GẮP : " + count + " LƯỢT" + "\n|7|VUI LÒNG LÀM TRỐNG RƯƠNG PHỤ!",
                                                        "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                break;
                                            }
                                            player.point_gapthu += 1;
                                            short[] bkt = {1397, 1398, 1399, 1400, 1401, 1402, 1377};
                                            Item gapx100 = Util.petviprandom(bkt[Util.nextInt(bkt.length)]);
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    InventoryServiceNew.gI().addItemBag(player, gapx100);
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|ĐANG TIẾN HÀNH GẮP AUTO X1\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu + "\nNẾU HÀNH TRANG ĐẦY, ITEM SẼ ĐƯỢC THÊM VÀO RƯƠNG PHỤ",
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                            if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                                if (Util.isTrue(10, 100)) {
                                                    player.inventory.itemsBoxCrackBall.add(gapx100);
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Đã gắp được : " + gapx100.template.name + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                } else {
                                                    this.createOtherMenu(player, 12347, "|7|HÀNH TRANG ĐÃ ĐẦY\nĐANG TIẾN HÀNH GẮP AUTO X10 VÀO RƯƠNG PHỤ\nSỐ LƯỢT CÒN : " + timex100 + " LƯỢT\n" + "|2|Gắp hụt rồi!" + "\nSố xu còn : " + InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1396).quantity + "\n|7|TỔNG ĐIỂM : " + player.point_gapthu,
                                                            "Gắp X1", "Gắp X10", "Gắp X100", "Rương Đồ");
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                    break;
                                case 3:
                                    this.createOtherMenu(player, ConstNpc.RUONG_PHU,
                                            "|1|Tình yêu như một dây đàn\n"
                                            + "Tình vừa được thì đàn đứt dây\n"
                                            + "Đứt dây này anh thay dây khác\n"
                                            + "Mất em rồi anh biết thay ai?",
                                            "Rương Phụ\n(" + (player.inventory.itemsBoxCrackBall.size()
                                            - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                            + " món)",
                                            "Xóa Hết\nRương Phụ", "Đóng");
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.RUONG_PHU) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "RUONG_PHU", true);
                                    break;
                                case 1:
                                    NpcService.gI().createMenuConMeo(player,
                                            ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                            "|3|Bạn chắc muốn xóa hết vật phẩm trong rương phụ?\n"
                                            + "|7|Sau khi xóa sẽ không thể khôi phục!",
                                            "Đồng ý", "Hủy bỏ");
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc miNuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.MENU_JOIN_GIAI_CUU_MI_NUONG,
                            "Ta đang bị kẻ xấu lợi dụng kiểm soát bản thân\n"
                            + "Các chàng trai hãy cùng nhau nhanh chóng tập hợp lên đường giải cứu ta",
                            "Tham gia", "Hướng dẫn", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                int nPlSameClan = 0;
                for (Player pl : player.zone.getPlayers()) {
                    if (!pl.equals(player) && pl.clan != null
                            && pl.clan.equals(player.clan) && pl.location.x >= 1285
                            && pl.location.x <= 1645) {
                        nPlSameClan++;
                    }
                }
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_GIAI_CUU_MI_NUONG:
                            if (select == 0) {
                                if (player.clan == null) {
                                    Service.gI().sendThongBao(player, "Yêu cầu gia nhập bang hội");
                                    break;
                                }
                                if (player.clan.giaiCuuMiNuong != null) {
                                    ChangeMapService.gI().changeMapInYard(player, 185, player.clan.giaiCuuMiNuong.id, 60);
                                    break;
                                } else if (nPlSameClan < 0) {
                                    Service.gI().sendThongBao(player, "Yêu cầu tham gia cùng 2 đồng đội");
                                    break;
                                } else if (player.clanMember.getNumDateFromJoinTimeToToday() < 0) {
                                    Service.gI().sendThongBao(player, "Yêu cầu tham gia bang hội trên 1 ngày");
                                    break;
                                } else if (player.clan.haveGoneGiaiCuuMiNuong) {
                                    Service.gI().sendThongBaoOK(player, "Bang hội của ngươi đã tham gia vào lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenGiaiCuuMiNuong, "HH:mm:ss") + "\nVui lòng tham gia vào ngày mai");
                                    break;
                                } else {
                                    GiaiCuuMiNuongService.gI().openGiaiCuuMiNuong(player);
                                }
                            } else if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_GIAI_CUU_MI_NUONG);
                            }
                            break;
                    }
                }
            }
        };
    }

    private static Npc TrongTai(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đại hội võ thuật Siêu Hạng\n(thử nghiệm)\ndiễn ra 24/7 kể cả ngày lễ và chủ nhật\nHãy thi đấu để khẳng định đẳng cấp của mình nhé", "Top 100\nCao thủ", "Nhận thưởng quà top", "Đấu ngay", "Về\nĐại Hội\nVõ Thuật");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 113) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    try (Connection con = GirlkunDB.getConnection()) {
                                    Manager.topSieuHang = Manager.realTopSieuHang(con);
                                } catch (Exception ignored) {
                                    Logger.error("Lỗi đọc top");
                                }
                                Service.gI().showListTop(player, Manager.topSieuHang, (byte) 1);
                                break;
                                case 2:
try {
    List<TOP> tops = Manager.realTopSieuHang(player); // Lấy danh sách TOP từ Manager

    if (tops != null && !tops.isEmpty()) {
        Service.gI().showListTop(player, tops, (byte) 1); // Hiển thị danh sách TOP
        tops.clear(); // Xóa danh sách sau khi hiển thị
    } else {
        // Xử lý trường hợp danh sách rỗng hoặc null (tuỳ theo logic của bạn)
        System.out.println("Danh sách TOP trống hoặc không có.");
    }
} catch (Exception e) {
    // Xử lý ngoại lệ nếu có
    e.printStackTrace();
}

                                    break;
                                case 3:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, -1, 432);
                                    break;
                                      case 1:
                                this.createOtherMenu(player, 1, "|7|Ngươi ở top " + player.rankSieuHang, "Nhận thưởng\n"
                                        +"Mỗi ngày chỉ có thể nhận thưởng một lần");
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {
                        switch (select) {
                          case 0:
    if (!player.getSession().isnhanthuong1) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastClaimDate = Instant.ofEpochMilli(player.getSession().timesieuhang).atZone(ZoneId.systemDefault()).toLocalDate();
        
        if (lastClaimDate.isEqual(currentDate)) {
            Service.gI().sendThongBao(player, "Bạn đã nhận thưởng rồi trong ngày hôm nay");
        } else {
            Item voso = null;
            voso = InventoryServiceNew.gI().findItemBag(player, 1418);
            if (player.rankSieuHang == 10) {
                voso.quantity += 50000;
            }
            if (player.rankSieuHang == 9) {
                voso.quantity += 100000;
            }
            if (player.rankSieuHang == 8) {
                voso.quantity += 150000;
            }
            if (player.rankSieuHang == 7) {
                voso.quantity += 250000;
            }
            if (player.rankSieuHang == 6) {
                voso.quantity += 450000;
            }
            if (player.rankSieuHang == 5) {
                voso.quantity += 1000000;
            }
            if (player.rankSieuHang == 4) {
                voso.quantity += 2000000;
            }
            if (player.rankSieuHang == 3) {
                voso.quantity += 4000000;
            }
            if (player.rankSieuHang == 2) {
                voso.quantity += 6000000;
            }
            if (player.rankSieuHang == 1) {
                voso.quantity += 10000000;
            }
            InventoryServiceNew.gI().addItemBag(player, voso);
            InventoryServiceNew.gI().sendItemBags(player);
            PlayerService.gI().sendInfoHpMpMoney(player);
           Service.getInstance().sendThongBao(player, "Bạn đã nhận được " + voso.template.name);
            
            // Cập nhật thông tin ngày nhận thưởng
            player.getSession().timesieuhang = System.currentTimeMillis();
            player.getSession().isnhanthuong1 = true;
            PlayerDAO.nhanthuong1(player);
        }
        if (!lastClaimDate.isEqual(currentDate)) {
    player.getSession().isnhanthuong1 = false;
}
    } else {
        Service.gI().sendThongBao(player, "Bạn đã nhận thưởng rồi");
    }
    break;
                                
                        
                    
                    
                    
                                    
                            }
                        }
                    
                    }
            
                }
            }
        
                
        };
    }

    private static Npc trungLinhThu(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đổi Trứng Linh thú cần:\b|7|X99 Hồn Linh Thú + 1 Tỷ vàng", "Đổi Trứng\nLinh thú", "Nâng Chiến Linh", "Mở chỉ số ẩn\nChiến Linh", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Item honLinhThu = null;
                                    try {
                                        honLinhThu = InventoryServiceNew.gI().findItemBag(player, 2029);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (honLinhThu == null || honLinhThu.quantity < 99) {
                                        this.npcChat(player, "Bạn không đủ 99 Hồn Linh thú");
                                    } else if (player.inventory.gold < 1_000_000_000) {
                                        this.npcChat(player, "Bạn không đủ 1 Tỷ vàng");
                                    } else if (InventoryServiceNew.gI().getCountEmptyBag(player) == 0) {
                                        this.npcChat(player, "Hành trang của bạn không đủ chỗ trống");
                                    } else {
                                        player.inventory.gold -= 1_000_000_000;
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, honLinhThu, 99);
                                        Service.gI().sendMoney(player);
                                        Item trungLinhThu = ItemService.gI().createNewItem((short) 2028);
                                        InventoryServiceNew.gI().addItemBag(player, trungLinhThu);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được 1 Trứng Linh thú");
                                    }
                                    break;
                                }

                                case 1:
//                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.Nang_Chien_Linh);
                                    break;
                                case 2:
                                    //                                   CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_Chien_Linh);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
//                            switch (player.combineNew.typeCombine) {
                            //                               case CombineServiceNew.Nang_Chien_Linh:
                            //                               case CombineServiceNew.MO_CHI_SO_Chien_Linh:
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                            //                                break;
                        }
                        //                     }
                    }
                }
            }
        };
    }

    private static Npc kyGui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, 0, "Cửa hàng chúng tôi chuyên mua bán hàng hiệu, hàng độc, cảm ơn bạn đã ghé thăm.", "Hướng\ndẫn\nthêm", "Mua bán\nKý gửi", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    switch (select) {
                        case 0:
                            Service.gI().sendPopUpMultiLine(pl, tempId, avartar, "Cửa hàng chuyên nhận ký gửi mua bán vật phẩm\bChỉ với 5 hồng ngọc\bGiá trị ký gửi 10k-200Tr vàng hoặc 2-2k ngọc\bMột người bán, vạn người mua, mại dô, mại dô");
                            break;
                        case 1:
                            ShopKyGuiService.gI().openShopKyGui(pl);
                            break;

                    }
                }
            }
        };
    }

    private static Npc poTaGe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đa vũ trụ song song \b|7|Con muốn gọi con trong đa vũ trụ \b|1|Với giá 200tr vàng không?", "Gọi Boss\nNhân bản", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 140) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: {
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossClone((int) player.id));
                                    if (oldBossClone != null) {
                                        this.npcChat(player, "Nhà ngươi hãy tiêu diệt Boss lúc trước gọi ra đã, con boss đó đang ở khu " + oldBossClone.zone.zoneId);
                                    } else if (player.inventory.gold < 200_000_000) {
                                        this.npcChat(player, "Nhà ngươi không đủ 200 Triệu vàng ");
                                    } else {
                                        List<Skill> skillList = new ArrayList<>();
                                        for (byte i = 0; i < player.playerSkill.skills.size(); i++) {
                                            Skill skill = player.playerSkill.skills.get(i);
                                            if (skill.point > 0) {
                                                skillList.add(skill);
                                            }
                                        }
                                        int[][] skillTemp = new int[skillList.size()][3];
                                        for (byte i = 0; i < skillList.size(); i++) {
                                            Skill skill = skillList.get(i);
                                            if (skill.point > 0) {
                                                skillTemp[i][0] = skill.template.id;
                                                skillTemp[i][1] = skill.point;
                                                skillTemp[i][2] = skill.coolDown;
                                            }
                                        }
                                        BossData bossDataClone = new BossData(
                                                "Nhân Bản" + player.name,
                                                player.gender,
                                                new short[]{player.getHead(), player.getBody(), player.getLeg(), player.getFlagBag(), player.idAura, player.getEffFront()},
                                                player.nPoint.dame,
                                                new long[]{player.nPoint.hpMax},
                                                new int[]{140},
                                                skillTemp,
                                                new String[]{"|-2|Boss nhân bản đã xuất hiện rồi"}, //text chat 1
                                                new String[]{"|-1|Ta sẽ chiếm lấy thân xác của ngươi hahaha!"}, //text chat 2
                                                new String[]{"|-1|Lần khác ta sẽ xử đẹp ngươi"}, //text chat 3
                                                60
                                        );

                                        try {
                                            new NhanBan(Util.createIdBossClone((int) player.id), bossDataClone, player.zone);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //trừ vàng khi gọi boss
                                        player.inventory.gold -= 200_000_000;
                                        Service.gI().sendMoney(player);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
    public static Npc Tapion(int mapId, int status, int cx, int cy, int tempId, int avartar) {
                   return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    Map22h.gI().setTimeJoinMap22h();} 
                            if (this.mapId == 19) {                                               
                                long now = System.currentTimeMillis();
                                if (now > Map22h.TIME_OPEN_22h && now < Map22h.TIME_CLOSE_22h) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB,   "phong ấn đã bị phá vỡ, "
                                            + "Xin hãy cứu lấy người dân",
                                           "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                    "Ác quỷ truyền thuyết Hirudegarn đã thoát khỏi phong ấn ngàn năm nHãy giúp tôi chế ngự nó?",
                                            "Hướng dẫn", "Từ chối");
                                }                           
                            }
                        }    
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                   switch (this.mapId) {
                        case 19:
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                            case ConstNpc.MENU_OPEN_MMB:
                                    if (select == 0)
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_chi22h);
                                    if (!player.getSession().actived) {                                   
                                    }
                                   if (select == 1){
                                        ChangeMapService.gI().changeMap(player, 126, 0, 66, 312);
                                    }
                                    break;
                                   case ConstNpc.MENU_NOT_OPEN_BDW:
                                    if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_2h);
                                }
                                break;
                               }
                          }
                      }
                  }    
              };
           }
    public static Npc truongmylan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
                   return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    MapKhoBau11h.gI().setTimeJoinMap11h();} 
                            if (this.mapId == 5) {                                               
                                long now = System.currentTimeMillis();
                                if (now > MapKhoBau11h.TIME_OPEN_11h && now < MapKhoBau11h.TIME_CLOSE_11h) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB1,   "Tài Sản Của Admin đã bị chiếm đoạt, "
                                            + "Các Dân Chơi Hãy Giúp Admin Lấy Lại Vàng Bạc",
                                           "Hướng dẫn\nthêm", "Tham gia", "Shop\nKho Báu","Từ Chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB1,
                                    "44 tỷ đô của admin đã bay màu bạn có muốn tham gia tìm kiếm số tiền đó?",
                                            "Hướng dẫn", "Từ chối");
                                } 
                                 
                            }
                        }    
            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                   switch (this.mapId) {
                        case 5:
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB1:
                            case ConstNpc.MENU_OPEN_MMB1:
                                    if (select == 0)
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_11h);
                                    if (!player.getSession().actived) {                                   
                                    }
                                   if (select == 1){
                                        ChangeMapService.gI().changeMap(player, 179, 0, 66, 312);
                                    }
                                   if (select == 2){
                                        ShopServiceNew.gI().opendShop(player, "BIBI", false);
                                    }
                                   
                                    break;
                                   case ConstNpc.MENU_NOT_OPEN_BDW:
                                    if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MYLAN);
                                }
                                break;
                               }
                          }
                      }
                  } 
                      
              };
           }

    private static Npc quyLaoKame(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (TaskService.gI().getIdTask(player) == ConstTask.TASK_18_4) {
                            TaskService.gI().sendNextTaskMain(player);
                        } else if (TaskService.gI().getIdTask(player) == ConstTask.TASK_16_4) {
                            TaskService.gI().sendNextTaskMain(player);
                        }
                        if (player.getSession().is_gift_box) {
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?",
                                    "Giải tán bang hội", "Lãnh địa Bang Hội", "Bản Đồ Kho Báu", "Xuất Sư\n Đệ Tử");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                if (clan != null) {
                                    ClanMember cm = clan.getClanMember((int) player.id);
                                    if (cm != null) {
                                        if (clan.members.size() > 1) {
                                            Service.gI().sendThongBao(player, "Bang phải còn một người");
                                            break;
                                        }
                                        if (!clan.isLeader(player)) {
                                            Service.gI().sendThongBao(player, "Phải là bang chủ");
                                            break;
                                        }
//                                        
                                        NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1, "Con có chắc chắn muốn giải tán bang hội không? Ta cho con 2 lựa chọn...",
                                                "Yes you do!", "Từ chối!");
                                    }
                                    break;
                                }
                                Service.gI().sendThongBao(player, "Có bang hội đâu ba!!!");
                                break;
                            case 1:
                                if (player.getSession().player.nPoint.power >= 39999999999L) {

                                    ChangeMapService.gI().changeMapBySpaceShip(player, 153, -1, 432);
                                } else {
                                    this.npcChat(player, "Bạn chưa đủ 40 tỷ sức mạnh để vào");
                                }
                                break;
                            case 2:
                                if (player.clan == null) {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Vào bang hội trước", "Đóng");
                                    break;
                                }
                                if (player.clan.banDoKhoBau != null) {
                                    createOtherMenu(player, ConstNpc.MENU_OPENED_DBKB,
                                            "Bang hội của con đang tham gia Bản đồ kho báu cấp độ " + player.clan.banDoKhoBau.level + "\n"
                                            + "Thời gian còn lại là "
                                            + TimeUtil.getSecondLeft(player.clan.banDoKhoBau.getLastTimeOpen(), BanDoKhoBau.TIME_BAN_DO_KHO_BAU / 1000)
                                            + " giây. Con có muốn tham gia không?",
                                            "Tham gia", "Không");
                                    break;
                                }
                                Input.gI().createFormChooseLevelBDKB(player);
                                break;
                            case 3:
                                if (player.pet != null) {
                                    if (player.getSession().player.pet.nPoint.power < 20_000_000_000L) {
                                        Service.gI().sendThongBaoOK(player, "Đệ Tử ngươi còn thiếu " + (20_000_000_000L - player.pet.nPoint.power) + " sức mạnh nữa!");
                                    } else {
                                        ChangeMapService.gI().exitMap(player.pet);
                                        player.pet.dispose();
                                        player.pet = null;
                                        Service.gI().sendThongBao(player, "Đệ tử đã xuất sư thành công, vui lòng thoát game vào lại !");
                                        player.xuatsu++;
                                        Item Chungnhan = ItemService.gI().createNewItem((short) 720);
                                        InventoryServiceNew.gI().addItemBag(player, Chungnhan);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        Service.gI().sendThongBao(player, "Bạn nhận được Giấy chứng nhận đệ tử");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Bạn không có đệ tử !");
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_DBKB) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= BanDoKhoBau.POWER_CAN_GO_TO_DBKB) {
                                    Input.gI().createFormChooseLevelBDKB(player);
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(BanDoKhoBau.POWER_CAN_GO_TO_DBKB));
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCEPT_GO_TO_BDKB) {
                        switch (select) {
                            case 0:
                                Object level = PLAYERID_OBJECT.get(player.id);
                                BanDoKhoBauService.gI().openBDKB(player, (int) level);
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_DBKB) {
                        if (select == 0) {
                            BanDoKhoBauService.gI().joinBDKB(player);
                        }
                    }
                }
            }
        };
    }

    public static Npc truongLaoGuru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc vuaVegeta(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc ongGohan_ongMoori_ongParagus(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (TaskService.gI().getIdTask(player) == ConstTask.TASK_4_3) {
                            TaskService.gI().sendNextTaskMain(player);
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Tốt lắm, con hoàn thành rất xuất xắc", "Đóng");
                        } else if (TaskService.gI().getIdTask(player) == ConstTask.TASK_10_1) {
                            TaskService.gI().sendNextTaskMain(player);
                        }
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "|7|Chào con thầy còn dữ lại " + player.getSession().vnd + " Coin của con! "
                                .replaceAll("%1", player.gender == ConstPlayer.TRAI_DAT ? "Quy lão Kamê"
                                                : player.gender == ConstPlayer.NAMEC ? "Trưởng lão Guru" : "Vua Vegeta"),
                                "Nhận Ngọc", "Mở\nThành Viên", "GiftCode", "Next\nNhiệm","Đổi\nMật Khẩu","Nhận Đệ Tử","Mốc Nạp");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                createOtherMenu(player, ConstNpc.HOTRO,
                                        "|7| Bú Ít Thôi Con",
                                        "Nhận Ngọc Xanh");
                                break;
                            case 1:
                                if (!player.getSession().actived) {
                                    if (player.getSession().vnd >= 10000) {
                                        player.getSession().actived = true;
                                        if (PlayerDAO.subvnd(player, 10000)) {
                                            Service.gI().sendThongBao(player, "Bạn đã mở thành viên");
                                        }
                                    } else {
                                        this.npcChat(player, "Bạn còn thiếu " + (10000 - player.getSession().vnd) + " để mở thành viên");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Bạn đã mở thành viên rồi");

                                }
                                break;
                            case 2:
                                Input.gI().createFormGiftCode(player);
                                break;
                             case 3:
                                if (player.getSession() != null) {
                                    int currentTask = TaskService.gI().getIdTask(player);
                                    if (currentTask >= ConstTask.TASK_1_0 && currentTask <= ConstTask.TASK_17_0) {
                                        TaskService.gI().sendNextTaskMain(player);
                                        return;
                                    } else {
                                        Service.gI().sendThongBao(player, "bạn chỉ có thể skip đến nhiệm vụ nappa!");
                                        return;
                                    }
                                }
                                break;
                                case 4:
                                Input.gI().createFormChangePassword(player);
                                break;
                                case 5:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    Service.gI().sendThongBao(player, "Bạn đã có đệ tử trước\nđó rồi");
                                }
                                break;
                                case 6:
                                createOtherMenu(player, ConstNpc.NAPTICHLUY,
                                        "|7|Sự kiện nạp tích luỹ nhận quà\n"
                                        + "Số lượng tích luỹ của bạn là: " + player.getSession().tongnap
                                        + "\nCác mốc nhận quà hiện tại\n"
                                        + "1. 50k Nhận 1 set thần linh cùng hành tinh (không thể giao dịch)\n"
                                        + "2. 100k Nhận 2 rương huỷ diệt cùng hành tinh (không thể giao dịch)\n",
                                        "Nhận Quà\n50k", "Nhận Quà\n100k", "Đóng");
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.NAPTICHLUY) {
                        switch (select) {
                            case 0:
                                try {
                                    GirlkunResultSet checkmocnap = GirlkunDB.executeQuery(
                                            "select * from account where id = ? and username = ?", player.getSession().userId, player.getSession().uu);
                                    if (checkmocnap.first()) {
                                        int mocnap = checkmocnap.getInt("mocnap");
                                        if (player.getSession().tongnap >= 50000 && mocnap < 50000) {
                                            GirlkunDB.executeUpdate(
                                                    "update account set mocnap = ? where id = ? and username = ?", 50000, player.getSession().userId, player.getSession().uu);
                                            if (player.gender == 0) {
                                                Item ao = ItemService.gI().itemtlinhItem((short) 555);
                                                Item quan = ItemService.gI().itemtlinhItem((short) 556);
                                                Item gang = ItemService.gI().itemtlinhItem((short) 562);
                                                Item giay = ItemService.gI().itemtlinhItem((short) 563);
                                                Item rada = ItemService.gI().itemtlinhItem((short) 561);
                                                InventoryServiceNew.gI().addItemBag(player, ao);
                                                InventoryServiceNew.gI().addItemBag(player, quan);
                                                InventoryServiceNew.gI().addItemBag(player, gang);
                                                InventoryServiceNew.gI().addItemBag(player, giay);
                                                InventoryServiceNew.gI().addItemBag(player, rada);
                                                InventoryServiceNew.gI().sendItemBags(player);
                                            } else if (player.gender == 1) {
                                                Item ao = ItemService.gI().itemtlinhItem((short) 557);
                                                Item quan = ItemService.gI().itemtlinhItem((short) 558);
                                                Item gang = ItemService.gI().itemtlinhItem((short) 564);
                                                Item giay = ItemService.gI().itemtlinhItem((short) 565);
                                                Item rada = ItemService.gI().itemtlinhItem((short) 561);
                                                InventoryServiceNew.gI().addItemBag(player, ao);
                                                InventoryServiceNew.gI().addItemBag(player, quan);
                                                InventoryServiceNew.gI().addItemBag(player, gang);
                                                InventoryServiceNew.gI().addItemBag(player, giay);
                                                InventoryServiceNew.gI().addItemBag(player, rada);
                                                InventoryServiceNew.gI().sendItemBags(player);
                                            } else {
                                                Item ao = ItemService.gI().itemtlinhItem((short) 559);
                                                Item quan = ItemService.gI().itemtlinhItem((short) 560);
                                                Item gang = ItemService.gI().itemtlinhItem((short) 566);
                                                Item giay = ItemService.gI().itemtlinhItem((short) 567);
                                                Item rada = ItemService.gI().itemtlinhItem((short) 561);
                                                InventoryServiceNew.gI().addItemBag(player, ao);
                                                InventoryServiceNew.gI().addItemBag(player, quan);
                                                InventoryServiceNew.gI().addItemBag(player, gang);
                                                InventoryServiceNew.gI().addItemBag(player, giay);
                                                InventoryServiceNew.gI().addItemBag(player, rada);
                                                InventoryServiceNew.gI().sendItemBags(player);
                                            }
                                            Service.gI().sendThongBao(player, "Tiến Hành Nhận Mốc Nạp 50K");
                                            Thread.sleep(3000);
                                            Service.gI().sendThongBao(player, "Bạn Đã Nhận Được một set thần linh");
                                        } else {
                                            this.npcChat(player, "Bạn đã nhận mốc nạp " + Util.format(mocnap) + "\nHãy Nạp Thêm Để Nhận Mốc Nạp");
                                        }
                                    }
                                } catch (Exception DM_BKT) {
                                }
                                break;
                            case 1:
                                try {
                                    GirlkunResultSet checkmocnap = GirlkunDB.executeQuery(
                                            "select * from account where id = ? and username = ?", player.getSession().userId, player.getSession().uu);
                                    if (checkmocnap.first()) {
                                        int mocnap = checkmocnap.getInt("mocnap");
                                        if (player.getSession().tongnap >= 100000 && mocnap < 100000) {
                                            GirlkunDB.executeUpdate(
                                                    "update account set mocnap = ? where id = ? and username = ?", 100000, player.getSession().userId, player.getSession().uu);

                                            int dohd = player.gender == 0 ? 2003 : player.gender == 1 ? 2004 : 2005;
                                            Item cc = ItemService.gI().createNewItem((short) dohd, 2);
                                            cc.itemOptions.add(new Item.ItemOption(30, 0));
                                            InventoryServiceNew.gI().addItemBag(player, cc);
                                            InventoryServiceNew.gI().sendItemBags(player);
                                            Service.gI().sendThongBao(player, "Tiến Hành Nhận Mốc Nạp 100k");
                                            Thread.sleep(3000);
                                            Service.gI().sendThongBao(player, "Bạn Đã Nhận Được " + cc.template.name);
                                        } else {
                                            this.npcChat(player, "Bạn đã nhận mốc nạp " + Util.format(mocnap) + "\nHãy Nạp Thêm Để Nhận Mốc Nạp");
                                        }
                                    }
                                } catch (Exception DM_BKT) {
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.HOTRO) {
                        switch (select) {
                            case 0:
                                if (player.inventory.gem == 200000000) {
                                    this.npcChat(player, "Tham Lam");
                                    break;
                                }
                                player.inventory.gem = 2000000;
                                Service.getInstance().sendMoney(player);
                                Service.getInstance().sendThongBao(player, "Bạn vừa nhận được 1 ngọc xanh");
                                break;
                            
                        }
                    }
                }
            }
        };
    }

    public static Npc bulmaQK(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Cậu cần trang bị gì cứ đến chỗ tôi nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.TRAI_DAT) {
                                    ShopServiceNew.gI().opendShop(player, "BUNMA", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi cưng, chị chỉ bán đồ cho người Trái Đất", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc dende(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.idNRNM != -1) {
                            if (player.zone.map.mapId == 7) {
                                this.createOtherMenu(player, 1, "Ồ, ngọc rồng namếc, bạn thật là may mắn\nnếu tìm đủ 7 viên sẽ được Rồng Thiêng Namếc ban cho điều ước", "Hướng\ndẫn\nGọi Rồng", "Gọi rồng", "Từ chối");
                            }
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Anh cần trang bị gì cứ đến chỗ em nhé", "Cửa\nhàng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.NAMEC) {
                                    ShopServiceNew.gI().opendShop(player, "DENDE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Xin lỗi anh, em chỉ bán đồ cho dân tộc Namếc", "Đóng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 1) {

                        if (player.clan == null) {
                            Service.gI().sendThongBao(player, "Không có bang hội");
                            return;
                        }
                        if (player.idNRNM != 353) {
                            Service.gI().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            return;
                        }

                        byte numChar = 0;
                        for (Player pl : player.zone.getPlayers()) {
                            if (pl.clan.id == player.clan.id && pl.id != player.id) {
                                if (pl.idNRNM != -1) {
                                    numChar++;
                                }
                            }
                        }
                        if (numChar < 6) {
                            Service.gI().sendThongBao(player, "Anh hãy tập hợp đủ 7 viên ngọc rồng nameck đi");
                            return;
                        }

                        if (player.zone.map.mapId == 7 && player.idNRNM != -1) {
                            if (player.idNRNM == 353) {
                                NgocRongNamecService.gI().tOpenNrNamec = System.currentTimeMillis() + 86400000;
                                NgocRongNamecService.gI().firstNrNamec = true;
                                NgocRongNamecService.gI().timeNrNamec = 0;
                                NgocRongNamecService.gI().doneDragonNamec();
                                NgocRongNamecService.gI().initNgocRongNamec((byte) 1);
                                NgocRongNamecService.gI().reInitNrNamec((long) 86399000);
                                SummonDragon.gI().summonNamec(player);
                            } else {
                                Service.gI().sendThongBao(player, "Anh phải có viên ngọc rồng Namếc 1 sao");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc appule(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi cần trang bị gì cứ đến chỗ ta nhé", "Cửa\nhàng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0://Shop
                                if (player.gender == ConstPlayer.XAYDA) {
                                    ShopServiceNew.gI().opendShop(player, "APPULE", true);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Về hành tinh hạ đẳng của ngươi mà mua đồ cùi nhé. Tại đây ta chỉ bán đồ cho người Xayda thôi", "Đóng");
                                }
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc drDrief(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.mapId == 84) {
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                pl.gender == ConstPlayer.TRAI_DAT ? "Đến\nTrái Đất" : pl.gender == ConstPlayer.NAMEC ? "Đến\nNamếc" : "Đến\nXayda");
                    } else if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nNamếc", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 84) {
                        ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 24, -1, -1);
                    } else if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cargo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang sợ hãi lắm rồi");
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                    "Tàu Vũ Trụ của ta có thể đưa cậu đến hành tinh khác chỉ trong 3 giây. Cậu muốn đi đâu?",
                                    "Đến\nTrái Đất", "Đến\nXayda", "Siêu thị");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                break;
                            case 1:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 26, -1, -1);
                                break;
                            case 2:
                                ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc cui(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_FIND_BOSS = 50000000;

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(pl, this)) {
                        if (pl.playerTask.taskMain.id == 7) {
                            NpcService.gI().createTutorial(pl, this.avartar, "Hãy lên đường cứu đứa bé nhà tôi\n"
                                    + "Chắc bây giờ nó đang bị bắt cóc rồi");
                        } else {
                            if (this.mapId == 19) {

                                int taskId = TaskService.gI().getIdTask(pl);
                                switch (taskId) {
                                    case ConstTask.TASK_19_0:
                                        this.createOtherMenu(pl, ConstNpc.MENU_FIND_KUKU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến chỗ\nKuku\n(" + Util.numberToMoney(COST_FIND_BOSS) + " vàng)",
                                                "Đến Cold", "Đến\nNappa", "Từ chối");
                                        break;
                                    default:
                                        this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                                "Đội quân của Fide đang ở Thung lũng Nappa, ta sẽ đưa ngươi đến đó",
                                                "Đến Cold", "Đến\nNappa", "Từ chối");

                                        break;
                                }
                            } else if (this.mapId == 68) {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Ngươi muốn về Thành Phố Vegeta", "Đồng ý", "Từ chối");
                            } else {
                                this.createOtherMenu(pl, ConstNpc.BASE_MENU,
                                        "Tàu vũ trụ Xayda sử dụng công nghệ mới nhất, "
                                        + "có thể đưa ngươi đi bất kỳ đâu, chỉ cần trả tiền là được.",
                                        "Đến\nTrái Đất", "Đến\nNamếc", "Siêu thị");
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 26) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 24, -1, -1);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 25, -1, -1);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 84, -1, -1);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 19) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.getSession().player.nPoint.power >= 41000000000L
                                            && player.playerTask.taskMain.id > 26) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    } // break;
                                    else {
                                        Service.gI().sendThongBaoOK(player, "Làm Nhiệm Vụ 26 Và Đạt 41 Tỷ Sức Mạnh");
                                    }
                                    break;
                                case 1:
                                    if (player.playerTask.taskMain.id >= 17) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    } else {
                                        Service.gI().sendThongBaoOK(player, "Làm Nhiệm Vụ Đi");
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_FIND_KUKU) {
                            switch (select) {
                                case 0:
                                    Boss boss = BossManager.gI().getBossById(BossID.KUKUMDDRAMBO);
                                    if (boss != null && !boss.isDie()) {
                                        if (player.inventory.gold >= COST_FIND_BOSS) {
                                            Zone z = MapService.gI().getMapCanJoin(player, boss.zone.map.mapId,
                                                    boss.zone.zoneId);
                                            if (z != null && z.getNumOfPlayers() < z.maxPlayer) {
                                                player.inventory.gold -= COST_FIND_BOSS;
                                                ChangeMapService.gI().changeMap(player, boss.zone, boss.location.x,
                                                        boss.location.y);
                                                Service.gI().sendMoney(player);
                                            } else {
                                                Service.gI().sendThongBao(player, "Khu vực đang full.");
                                            }
                                        } else {
                                            Service.gI().sendThongBao(player, "Không đủ vàng, còn thiếu "
                                                    + Util.numberToMoney(COST_FIND_BOSS - player.inventory.gold)
                                                    + " vàng");
                                        }
                                        break;
                                    }
                                    Service.gI().sendThongBao(player, "Chết rồi ba...");
                                    break;
                                case 1:
                                    if (player.getSession().player.nPoint.power >= 41000000000L
                                            && player.playerTask.taskMain.id > 26) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 109, -1, 295);
                                    } // break;
                                    else {
                                        Service.gI().sendThongBaoOK(player, "Làm Nhiệm Vụ 26 Và Đạt 41 Tỷ Sức Mạnh");
                                    }
                                    break;
                                case 2:
                                    if (player.playerTask.taskMain.id >= 17) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 68, -1, 90);
                                    } else {
                                        Service.gI().sendThongBaoOK(player, "Làm Nhiệm Vụ Đi");
                                    }
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 68) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 19, -1, 1100);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc santa(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Xin chào, ta có một số vật phẩm đặt biệt cậu có muốn xem không?",
                            "Cửa hàng", "Sự Kiện", "QUY ĐỔI");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop
                                    createOtherMenu(player, ConstNpc.SHOPSANTA,
                                            "|7|Xin chào, Ngươi muốn mua gì",
                                            "Shop Thường", "Shop Hồng Ngọc", "Shop Thỏi Vàng");
                                    break;

                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "SU_KIEN", true);
                                    break;
                                case 2:
                                    this.createOtherMenu(player, ConstNpc.QUY_DOI, "|7|Số tiền của bạn còn : " + player.getSession().vnd + "\n"
                                            + "Muốn quy đổi không", "Quy Đổi\n10.000\n 20 Thỏi Vàng", "Quy Đổi\n20.000\n 40 Thỏi Vàng", "Quy Đổi\n30.000\n 60 Thỏi Vàng", "Quy Đổi\n50.000\n 100 Thỏi Vàng", "Quy Đổi\n100.000 \n200 Thỏi Vàng", "Đóng");
                                    break;

                            }

                        } else if (player.iDMark.getIndexMenu() == ConstNpc.SHOPSANTA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "SANTA", true);
                                    break;
                                case 1: //shoptrue
                                    ShopServiceNew.gI().opendShop(player, "SHOP_NGU_SAC", false);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BKT", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.QUY_DOI) {
                            PreparedStatement ps = null;
                            try (Connection con = GirlkunDB.getConnection();) {
                                switch (select) {
                                    case 0:
                                        Item thoivang = ItemService.gI().createNewItem((short) (457));
                                        thoivang.quantity += 19;
                                        if (player.getSession().vnd < 10000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 10k coin");
                                            return;
                                        }
                                        player.getSession().vnd -= 10000;
                                        InventoryServiceNew.gI().addItemBag(player, thoivang);
                                        Service.gI().sendThongBao(player, "Bạn Nhận Được 20 " + thoivang.template.name + " Nhớ out game vô lại");
                                        break;
                                    case 1:
                                        Item thoivangg = ItemService.gI().createNewItem((short) (457));
                                        thoivangg.quantity += 39;
                                        if (player.getSession().vnd < 20000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 20k coin");
                                            return;
                                        }
                                        player.getSession().vnd -= 20000;
                                        InventoryServiceNew.gI().addItemBag(player, thoivangg);
                                        Service.gI().sendThongBao(player, "Bạn Nhận Được 40 " + thoivangg.template.name + " Nhớ out game vô lại");
                                        break;
                                    case 2:
                                        Item thoivanggg = ItemService.gI().createNewItem((short) (457));
                                        thoivanggg.quantity += 59;
                                        if (player.getSession().vnd < 30000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                            return;
                                        }
                                        player.getSession().vnd -= 30000;
                                        InventoryServiceNew.gI().addItemBag(player, thoivanggg);
                                        Service.gI().sendThongBao(player, "Bạn Nhận Được 60 " + thoivanggg.template.name + " Nhớ out game vô lại");
                                        break;
                                    case 3:
                                        Item thoivangggg = ItemService.gI().createNewItem((short) (457));
                                        thoivangggg.quantity += 99;
                                        if (player.getSession().vnd < 50000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 50k coin");
                                            return;
                                        }
                                        player.getSession().vnd -= 50000;
                                        InventoryServiceNew.gI().addItemBag(player, thoivangggg);
                                        Service.gI().sendThongBao(player, "Bạn Nhận Được 1000 " + thoivangggg.template.name + " Nhớ out game vô lại");
                                        break;
                                    case 4:
                                        Item thoivanggggg = ItemService.gI().createNewItem((short) (457));
                                        thoivanggggg.quantity += 199;
                                        if (player.getSession().vnd < 100000) {
                                            Service.gI().sendThongBao(player, "Bạn không có đủ 100k coin");
                                            return;
                                        }
                                        player.getSession().vnd -= 100000;
                                        InventoryServiceNew.gI().addItemBag(player, thoivanggggg);
                                        Service.gI().sendThongBao(player, "Bạn Nhận Được 200 " + thoivanggggg.template.name + " Nhớ out game vô lại");
                                        break;
                                }

                                ps = con.prepareStatement("update account set vnd = ? where id = ?");
                                ps.setInt(1, player.getSession().vnd);
                                ps.setInt(2, player.getSession().userId);
                                ps.executeUpdate();
                                ps.close();

                            } catch (Exception e) {
                                Logger.logException(NpcFactory.class, e, "Lỗi update vnd " + player.name);
                            } finally {
                                try {
                                    if (ps != null) {
                                        ps.close();
                                    }
                                } catch (SQLException ex) {
                                    System.out.println("Lỗi khi update vnd");

                                }
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc thodaika(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "\b|8|Trò chơi Tài Xỉu đang được diễn ra\n\n|6|Thử vận may của bạn với trò chơi Tài Xỉu! Đặt cược và dự đoán đúng"
                        + "\n kết quả, bạn sẽ được nhận thưởng lớn. Hãy tham gia ngay và\n cùng trải nghiệm sự hồi hộp, thú vị trong trò chơi này!"
                        + "\n\n|7|(Điều kiện tham gia : mở thành viên)\n\n|2|Đặt tối thiểu: 1.000 Hồng ngọc\n Tối đa: 1.000.000.000.000 Hồng ngọc"
                        + "\n\n|7| Lưu ý : Thoát game khi chốt Kết quả sẽ MẤT Tiền cược và Tiền thưởng", "Thể lệ", "Tham gia");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    String time = ((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "|5|Có 2 nhà cái Tài và Xĩu, bạn chỉ được chọn 1 nhà để tham gia"
                                    + "\n\n|6|Sau khi kết thúc thời gian đặt cược. Hệ thống sẽ tung xí ngầu để biết kết quả Tài Xỉu"
                                    + "\n\nNếu Tổng số 3 con xí ngầu <=10 : XỈU\nNếu Tổng số 3 con xí ngầu >10 : TÀI\nNếu 3 Xí ngầu cùng 1 số : TAM HOA (Nhà cái lụm hết)"
                                    + "\n\n|7|Lưu ý: Số Hồng ngọc nhận được sẽ bị nhà cái lụm đi 20%. Trong quá trình diễn ra khi đặt cược nếu thoát game trong lúc phát thưởng phần quà sẽ bị HỦY", "Ok");
                        } else if (select == 1) {
                            if (TaiXiu.gI().baotri == false) {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---NHÀ CÁI TÀI XỈU---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                }
                            } else {
                                if (pl.goldTai == 0 && pl.goldXiu == 0) {
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else if (pl.goldTai > 0) {
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                } else {
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n\nTổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc\n\n|5|Thời gian còn lại: " + time + "\n\n|7|Bạn đã cược Xỉu : " + Util.format(pl.goldXiu) + " Hồng ngọc" + "\n\n|7|Hệ thống sắp bảo trì", "Cập nhập", "Đóng");
                                }
                            }
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai == 0 && pl.goldXiu == 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                    break;
                                case 1:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().TAI_taixiu(pl);
                                    }
                                    break;
                                case 2:
                                    if (!pl.getSession().actived) {
                                        Service.gI().sendThongBao(pl, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                    } else {
                                        Input.gI().XIU_taixiu(pl);
                                    }
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0 && TaiXiu.gI().baotri == false) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");
                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldTai > 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu > 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        } else if (((TaiXiu.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0 && pl.goldXiu == 0 && pl.goldTai == 0 && TaiXiu.gI().baotri == true) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "\n|7|---ĐỠ THẾ LỒN NÀO ĐƯỢC CÁC ÔNG À---\n\n|3|Kết quả kì trước:  " + TaiXiu.gI().x + " : " + TaiXiu.gI().y + " : " + TaiXiu.gI().z
                                            + "\n\n|6|Tổng nhà TÀI: " + Util.format(TaiXiu.gI().goldTai) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt TÀI: " + TaiXiu.gI().PlayersTai.size() + " người"
                                            + "\n\n|6|Tổng nhà XỈU: " + Util.format(TaiXiu.gI().goldXiu) + " Hồng ngọc"
                                            + "\n|1|Tổng người đặt XỈU: " + TaiXiu.gI().PlayersXiu.size() + " người"
                                            + "\n\n|5|Thời gian còn lại: " + time, "Cập nhập", "Theo TÀI", "Theo XỈU", "Đóng");

                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc uron(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    ShopServiceNew.gI().opendShop(pl, "URON", false);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc gohannn(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 0 || this.mapId == 7 || this.mapId == 14) {
                        this.createOtherMenu(player, 0, "Tiến vào map hỗ trợ tân thủ\nNơi up set kích hoạt và nhiều phần quà hấp dẫn\nChỉ dành cho người chơi từ 2k đến 60 tỷ sức mạnh!",
                                "Đến\nRừng Aurura", "Từ chối");
                    } else if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Hãy mang cho ta x20 mảnh SKH mỗi loại ta sẽ giúp ngươi đổi SKH",
                                "Đổi SKH\n Trái Đất", "Đổi SKH\n Namec", "Đổi SKH\n Xayda");
                    } else {
                        this.createOtherMenu(player, 0, "Ngươi muốn quay về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    Item manhSKHao = null;
                                    Item manhSKHquan = null;
                                    Item manhSKHgang = null;
                                    Item manhSKHgiay = null;
                                    Item manhSKHrada = null;
                                    try {
                                        manhSKHao = InventoryServiceNew.gI().findItemBag(player, 1394);
                                        manhSKHquan = InventoryServiceNew.gI().findItemBag(player, 1395);
                                        manhSKHgang = InventoryServiceNew.gI().findItemBag(player, 1396);
                                        manhSKHgiay = InventoryServiceNew.gI().findItemBag(player, 1397);
                                        manhSKHrada = InventoryServiceNew.gI().findItemBag(player, 1398);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (manhSKHao == null || manhSKHquan == null || manhSKHgang == null || manhSKHgiay == null || manhSKHrada == null
                                            || manhSKHao.quantity < 20 || manhSKHquan.quantity < 20
                                            || manhSKHgang.quantity < 20 || manhSKHgiay.quantity < 20 || manhSKHrada.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");
                                    } else {

                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHao, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHquan, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgang, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgiay, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHrada, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopSKH = ItemService.gI().createNewItem((short) 2000, 1); // Hộp SKH Trái Đất                                                              
                                        InventoryServiceNew.gI().addItemBag(player, hopSKH);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được hộp SKH Trái Đất");
                                    }
                                    break;
                                case 1:
                                    Item manhSKHao2 = null;
                                    Item manhSKHquan2 = null;
                                    Item manhSKHgang2 = null;
                                    Item manhSKHgiay2 = null;
                                    Item manhSKHrada2 = null;
                                    try {
                                        manhSKHao2 = InventoryServiceNew.gI().findItemBag(player, 1394);
                                        manhSKHquan2 = InventoryServiceNew.gI().findItemBag(player, 1395);
                                        manhSKHgang2 = InventoryServiceNew.gI().findItemBag(player, 1396);
                                        manhSKHgiay2 = InventoryServiceNew.gI().findItemBag(player, 1397);
                                        manhSKHrada2 = InventoryServiceNew.gI().findItemBag(player, 1398);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (manhSKHao2 == null || manhSKHquan2 == null || manhSKHgang2 == null || manhSKHgiay2 == null || manhSKHrada2 == null
                                            || manhSKHao2.quantity < 20 || manhSKHquan2.quantity < 20
                                            || manhSKHgang2.quantity < 20 || manhSKHgiay2.quantity < 20 || manhSKHrada2.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");
                                    } else {

                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHao2, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHquan2, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgang2, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgiay2, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHrada2, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopSKH = ItemService.gI().createNewItem((short) 2001, 1); // Hộp SKH Trái Đất                                                              
                                        InventoryServiceNew.gI().addItemBag(player, hopSKH);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được hộp SKH Namek");
                                    }
                                    break;
                                case 2:
                                    Item manhSKHao3 = null;
                                    Item manhSKHquan3 = null;
                                    Item manhSKHgang3 = null;
                                    Item manhSKHgiay3 = null;
                                    Item manhSKHrada3 = null;
                                    try {
                                        manhSKHao3 = InventoryServiceNew.gI().findItemBag(player, 1394);
                                        manhSKHquan3 = InventoryServiceNew.gI().findItemBag(player, 1395);
                                        manhSKHgang3 = InventoryServiceNew.gI().findItemBag(player, 1396);
                                        manhSKHgiay3 = InventoryServiceNew.gI().findItemBag(player, 1397);
                                        manhSKHrada3 = InventoryServiceNew.gI().findItemBag(player, 1398);
                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
                                    }
                                    if (manhSKHao3 == null || manhSKHquan3 == null || manhSKHgang3 == null || manhSKHgiay3 == null || manhSKHrada3 == null
                                            || manhSKHao3.quantity < 20 || manhSKHquan3.quantity < 20
                                            || manhSKHgang3.quantity < 20 || manhSKHgiay3.quantity < 20 || manhSKHrada3.quantity < 20) {
                                        this.npcChat(player, "Bạn không đủ vật phẩm");
                                    } else {

                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHao3, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHquan3, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgang3, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHgiay3, 20);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, manhSKHrada3, 20);
                                        Service.getInstance().sendMoney(player);
                                        Item hopSKH = ItemService.gI().createNewItem((short) 2002, 1); // Hộp SKH Trái Đất                                                              
                                        InventoryServiceNew.gI().addItemBag(player, hopSKH);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được hộp SKH Xayda");
                                    }
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAY_PS_HOA_TRANG_BI);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.AN_TRANG_BI);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.EP_SAO_TRANG_BI:

                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    } else if (this.mapId == 112) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                    break;
                            }
                        }
                    } else if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                            + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                    break;
                                case 1:
//
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                    break;
                                case 2: //nâng cấp bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                    break;
                                case 3: //Mở chỉ số bông tai
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_CHUNG_NHAN_XUAT_SU);
                                    break;
                                case 5:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                    break;

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                case CombineServiceNew.NANG_CAP_BONG_TAI:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                case CombineServiceNew.LAM_PHEP_NHAP_DA:
                                case CombineServiceNew.EP_CHUNG_NHAN_XUAT_SU:
                                case CombineServiceNew.NHAP_NGOC_RONG:
                                case CombineServiceNew.PHAN_RA_DO_THAN_LINH:
                                case CombineServiceNew.NANG_CAP_DO_TS:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc baHatMit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Ép sao\ntrang bị",
                                "Pha lê\nhóa\ntrang bị",
                                "Nâng Cấp SKH",
                                "Chân Mệnh",
                                "Mở Chỉ Số Cải Trang",
                                "Đến Võ Đài",
                                "Ấn Trang Bị",
                                "Pháp Sư\nTrang Bị",
                                "Tẩy\npháp sư",
                                "Nâng Trang\nBị Cấp 16",
                                "Cường hóa\nlỗ spl thứ 9");
                    } else if (this.mapId == 121) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Về đảo\nrùa");

                    } else if (this.mapId == 181) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Điểm bí tịch của ngươi hiện tại là : " + player.diembitich,
                                "Mở chỉ số\n Bí Tịch", "Đổi Sách\n Luyện\n Bí Tịch", "Luyện\n Bí Tịch");

                    } else {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Cửa hàng\nBùa", "Nâng cấp\nVật phẩm",
                                "Nâng cấp\nBông tai\nPorata",
                                "Ép Chứng Nhận",
                                "Nhập\nNgọc Rồng",
                                "Sách Tuyệt Kỹ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5 || this.mapId == 13 || this.mapId == 20) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_SAO_TRANG_BI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PHA_LE_HOA_TRANG_BI);
                                    break;
                                case 2:
                                    createOtherMenu(player, ConstNpc.DAPSKH,
                                            "Ngươi muốn nâng bông tai à",
                                            "Nâng SKH Thường", "Nâng SKH Vip", "Nâng Skh\nThần Linh","Nâng skh\nThiên sứ","Từ Chối");
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_CHAN_MENH);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_CAI_TRANG);
                                    break;
                                case 5:
                                    ChangeMapService.gI().changeMap(player, 112, -1, 213, 408);
                                    break;
                                case 6:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.AN_TRANG_BI);
                                    break;
                                 case 7:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.PS_HOA_TRANG_BI);
                                    break;
                                case 8:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.TAY_PS_HOA_TRANG_BI);
                                    break;
                                case 9:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.AN_TRANG_BI_VIP);
                                    break;
                                case 10:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.SAO_9);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.DAPSKH) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_HUY_DIET_LEN_SKH_VIP);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT_TS);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DAP_SET_KICH_HOAT_TS_VIP);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.EP_SAO_TRANG_BI:
                                case CombineServiceNew.PHA_LE_HOA_TRANG_BI:
                                case CombineServiceNew.NANG_HUY_DIET_LEN_SKH_VIP:
                                case CombineServiceNew.DAP_SET_KICH_HOAT_TS:
                                case CombineServiceNew.DAP_SET_KICH_HOAT:
                                case CombineServiceNew.NANG_CAP_CHAN_MENH:
                                case CombineServiceNew.MO_CHI_SO_CAI_TRANG:
                                case CombineServiceNew.AN_TRANG_BI:
                                case CombineServiceNew.PS_HOA_TRANG_BI:
                                case CombineServiceNew.TAY_PS_HOA_TRANG_BI:
                                case CombineServiceNew.AN_TRANG_BI_VIP:
                                case CombineServiceNew.DAP_SET_KICH_HOAT_TS_VIP:
                                case CombineServiceNew.SAO_9:
                                    switch (select) {
                                        case 0:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 1;
                                            }
                                            break;
                                        case 1:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 10;
                                            }
                                            break;
                                        case 2:
                                            if (player.combineNew.typeCombine == CombineServiceNew.PHA_LE_HOA_TRANG_BI) {
                                                player.combineNew.quantities = 100;
                                            }
                                            break;
                                    }
                                    CombineServiceNew.gI().startCombine(player);
                                    break;
                            }
                        }
                    } else if (this.mapId == 112) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 1156);
                                    break;
                            }
                        }
                    } else if (this.mapId == 181) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BI_TICH);
                                    break;
                                case 1:
                                    if (player.diembitich >= 250) {
                                        player.diembitich -= 250;
                                        Service.getInstance().sendMoney(player);
                                        Item SachBiTich = ItemService.gI().createNewItem((short) 1392, 1); // Sách Bí Tịch                                                             
                                        InventoryServiceNew.gI().addItemBag(player, SachBiTich);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        this.npcChat(player, "Bạn nhận được Sách Luyện Bí Tịch");
                                    } else {
                                        this.npcChat(player, "Bạn không đủ điểm bí tịch");
                                        return;
                                    }
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.LUYEN_BI_TICH);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.MO_CHI_SO_BI_TICH:
                                case CombineServiceNew.LUYEN_BI_TICH:
                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }
                    }  else if (this.mapId == 42 || this.mapId == 43 || this.mapId == 44) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0: //shop bùa
                                    createOtherMenu(player, ConstNpc.MENU_OPTION_SHOP_BUA,
                                            "Bùa của ta rất lợi hại, nhìn ngươi yếu đuối thế này, chắc muốn mua bùa để "
                                            + "mạnh mẽ à, mua không ta bán cho, xài rồi lại thích cho mà xem.",
                                            "Bùa\n1 giờ", "Bùa\n8 giờ", "Bùa\n1 tháng", "Đóng");
                                    break;
                                case 1:
//
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_VAT_PHAM);
                                    break;
                                case 2:
                                    createOtherMenu(player, ConstNpc.MENU_NANG_BONGTAI,
                                            "Ngươi muốn nâng bông tai à",
                                            "Nâng Bông Tai", "Mở Chỉ Số\nBông Tai", "Nâng Bông Tai\nCấp 3", "Mở Chỉ Số\nBông Tai\nCấp 3", "Nâng Bông Tai\nCấp 4", "Mở Chỉ Số\nBông Tai\nCấp 4", "Đóng");
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.EP_CHUNG_NHAN_XUAT_SU);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NHAP_NGOC_RONG);
                                    break;
                                case 5:
                                    createOtherMenu(player, ConstNpc.SACH_TUYET_KY, "Ta có thể giúp gì cho ngươi ?",
                                            "Đóng thành\nSách cũ",
                                            "Đổi Sách\nTuyệt kỹ",
                                            "Giám định\nSách",
                                            "Tẩy\nSách",
                                            "Nâng cấp\nSách\nTuyệt kỹ",
                                            "Hồi phục\nSách",
                                            "Phân rã\nSách");
                                    break;

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.SACH_TUYET_KY) {
                            switch (select) {
                                case 0:
                                    Item trangSachCu = InventoryServiceNew.gI().findItemBag(player, 1556);

                                    Item biaSach = InventoryServiceNew.gI().findItemBag(player, 1552);
                                    if ((trangSachCu != null && trangSachCu.quantity >= 9999) && (biaSach != null && biaSach.quantity >= 1)) {
                                        createOtherMenu(player, ConstNpc.DONG_THANH_SACH_CU,
                                                "|2|Chế tạo Cuốn sách cũ\n"
                                                + "|1|Trang sách cũ " + trangSachCu.quantity + "/9999\n"
                                                + "Bìa sách " + biaSach.quantity + "/1\n"
                                                + "Tỉ lệ thành công: 20%\n"
                                                + "Thất bại mất 99 trang sách và 1 bìa sách", "Đồng ý", "Từ chối");
                                        break;
                                    } else {
                                        String NpcSay = "|2|Chế tạo Cuốn sách cũ\n";
                                        if (trangSachCu == null) {
                                            NpcSay += "|7|Trang sách cũ " + "0/9999\n";
                                        } else {
                                            NpcSay += "|1|Trang sách cũ " + trangSachCu.quantity + "/9999\n";
                                        }
                                        if (biaSach == null) {
                                            NpcSay += "|7|Bìa sách " + "0/1\n";
                                        } else {
                                            NpcSay += "|1|Bìa sách " + biaSach.quantity + "/1\n";
                                        }

                                        NpcSay += "|7|Tỉ lệ thành công: 20%\n";
                                        NpcSay += "|7|Thất bại mất 99 trang sách và 1 bìa sách";
                                        createOtherMenu(player, ConstNpc.DONG_THANH_SACH_CU_2,
                                                NpcSay, "Từ chối");
                                        break;
                                    }
                                case 1:
                                    Item cuonSachCu = InventoryServiceNew.gI().findItemBag(player, 1555);
                                    Item kimBam = InventoryServiceNew.gI().findItemBag(player, 1553);

                                    if ((cuonSachCu != null && cuonSachCu.quantity >= 10) && (kimBam != null && kimBam.quantity >= 1)) {
                                        createOtherMenu(player, ConstNpc.DOI_SACH_TUYET_KY,
                                                "|2|Đổi sách tuyệt kỹ 1\n"
                                                + "|1|Cuốn sách cũ " + cuonSachCu.quantity + "/10\n"
                                                + "Kìm bấm giấy " + kimBam.quantity + "/1\n"
                                                + "Tỉ lệ thành công: 100%\n", "Đồng ý", "Từ chối");
                                        break;
                                    } else {
                                        String NpcSay = "|2|Đổi sách Tuyệt kỹ 1\n";
                                        if (cuonSachCu == null) {
                                            NpcSay += "|7|Cuốn sách cũ " + "0/10\n";
                                        } else {
                                            NpcSay += "|1|Cuốn sách cũ " + cuonSachCu.quantity + "/10\n";
                                        }
                                        if (kimBam == null) {
                                            NpcSay += "|7|Kìm bấm giấy " + "0/1\n";
                                        } else {
                                            NpcSay += "|1|Kìm bấm giấy " + kimBam.quantity + "/1\n";
                                        }
                                        NpcSay += "|7|Tỉ lệ thành công: 20%\n";
                                        createOtherMenu(player, ConstNpc.DOI_SACH_TUYET_KY_2,
                                                NpcSay, "Từ chối");
                                    }
                                    break;
                                case 2:// giám định sách
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.GIAM_DINH_SACH);
                                    break;
                                case 3:// tẩy sách
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.TAY_SACH);
                                    break;
                                case 4:// nâng cấp sách
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.NANG_CAP_SACH_TUYET_KY);
                                    break;
                                case 5:// phục hồi sách
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.PHUC_HOI_SACH);
                                    break;
                                case 6:// phân rã sách
                                    CombineServiceNew.gI().openTabCombine(player,
                                            CombineServiceNew.PHAN_RA_SACH);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.DOI_SACH_TUYET_KY) {
                            switch (select) {
                                case 0:
                                    Item cuonSachCu = InventoryServiceNew.gI().findItemBag(player, 1555);
                                    Item kimBam = InventoryServiceNew.gI().findItemBag(player, 1553);

                                    short baseValue = 1544;
                                    short genderModifier = (player.gender == 0) ? -2 : ((player.gender == 2) ? 2 : (short) 0);

                                    Item sachTuyetKy = ItemService.gI().createNewItem((short) (baseValue + genderModifier));

                                    if (Util.isTrue(100, 100)) {

                                        sachTuyetKy.itemOptions.add(new ItemOption(233, 0));
                                        sachTuyetKy.itemOptions.add(new ItemOption(21, 40));
                                        sachTuyetKy.itemOptions.add(new ItemOption(30, 0));
                                        sachTuyetKy.itemOptions.add(new ItemOption(87, 1));
                                        sachTuyetKy.itemOptions.add(new ItemOption(230, 5));
                                        sachTuyetKy.itemOptions.add(new ItemOption(231, 1000));
                                        try { // send effect susscess
                                            Message msg = new Message(-81);
                                            msg.writer().writeByte(0);
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeShort(tempId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(1);
                                            msg.writer().writeByte(2);
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, kimBam));
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, cuonSachCu));
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(7);
                                            msg.writer().writeShort(sachTuyetKy.template.iconID);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        } catch (Exception e) {
                                            System.out.println("lỗi 4");
                                        }
                                        InventoryServiceNew.gI().addItemBag(player, sachTuyetKy);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, cuonSachCu, 10);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, kimBam, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
//                                                    npcChat(player, "Thành công gòi cu ơi");
                                        return;
                                    } else {
                                        try { // send effect faile
                                            Message msg = new Message(-81);
                                            msg.writer().writeByte(0);
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeShort(tempId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(1);
                                            msg.writer().writeByte(2);
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, kimBam));
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, cuonSachCu));
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(8);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        } catch (Exception e) {
                                            System.out.println("lỗi 3");
                                        }
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, cuonSachCu, 5);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, kimBam, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
//                                                    npcChat(player, "Thất bại gòi cu ơi");
                                    }
                                    return;
                                case 1:
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.DONG_THANH_SACH_CU) {
                            switch (select) {
                                case 0:

                                    Item trangSachCu = InventoryServiceNew.gI().findItemBag(player, 1556);
                                    Item biaSach = InventoryServiceNew.gI().findItemBag(player, 1552);
                                    Item cuonSachCu = ItemService.gI().createNewItem((short) 1555);
                                    if (Util.isTrue(20, 100)) {
                                        cuonSachCu.itemOptions.add(new ItemOption(30, 0));

                                        try { // send effect susscess
                                            Message msg = new Message(-81);
                                            msg.writer().writeByte(0);
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeShort(tempId);
                                            player.sendMessage(msg);
                                            msg.cleanup();

                                            msg = new Message(-81);
                                            msg.writer().writeByte(1);
                                            msg.writer().writeByte(2);
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, trangSachCu));
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, biaSach));
                                            player.sendMessage(msg);
                                            msg.cleanup();

                                            msg = new Message(-81);
                                            msg.writer().writeByte(7);
                                            msg.writer().writeShort(cuonSachCu.template.iconID);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            player.sendMessage(msg);
                                            msg.cleanup();

                                        } catch (Exception e) {
                                            System.out.println("lỗi 1");
                                        }

                                        InventoryServiceNew.gI().addItemList(player.inventory.itemsBag, cuonSachCu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, trangSachCu, 9999);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, biaSach, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                        return;
                                    } else {
                                        try { // send effect faile
                                            Message msg = new Message(-81);
                                            msg.writer().writeByte(0);
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeUTF("test");
                                            msg.writer().writeShort(tempId);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(1);
                                            msg.writer().writeByte(2);
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, biaSach));
                                            msg.writer().writeByte(InventoryServiceNew.gI().getIndexBag(player, trangSachCu));
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                            msg = new Message(-81);
                                            msg.writer().writeByte(8);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            msg.writer().writeShort(-1);
                                            player.sendMessage(msg);
                                            msg.cleanup();
                                        } catch (Exception e) {
                                            System.out.println("lỗi 2");
                                        }
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, trangSachCu, 99);
                                        InventoryServiceNew.gI().subQuantityItemsBag(player, biaSach, 1);
                                        InventoryServiceNew.gI().sendItemBags(player);
                                    }
                                    return;
                                case 1:
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_SHOP_BUA) {
                            switch (select) {
                                case 0:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1H", true);
                                    break;
                                case 1:
                                    ShopServiceNew.gI().opendShop(player, "BUA_8H", true);
                                    break;
                                case 2:
                                    ShopServiceNew.gI().opendShop(player, "BUA_1M", true);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                            if (select == 0) {
                                CombineServiceNew.gI().startCombine(player);
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_BONGTAI) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI);
                                    break;
                                case 1:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI);
                                    break;
                                case 2:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI_CAP3);
                                    break;
                                case 3:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI_CAP3);
                                    break;
                                case 4:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.NANG_CAP_BONG_TAI_CAP4);
                                    break;
                                case 5:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.MO_CHI_SO_BONG_TAI_CAP4);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.NANG_CAP_VAT_PHAM:
                                case CombineServiceNew.NANG_CAP_BONG_TAI:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI:
                                case CombineServiceNew.NANG_CAP_BONG_TAI_CAP3:
                                case CombineServiceNew.NANG_CAP_BONG_TAI_CAP4:
                                case CombineServiceNew.MO_CHI_SO_BONG_TAI_CAP4:
                                case CombineServiceNew.LAM_PHEP_NHAP_DA:
                                case CombineServiceNew.NHAP_NGOC_RONG:
                                case CombineServiceNew.PHAN_RA_DO_THAN_LINH:
                                case CombineServiceNew.NANG_CAP_DO_TS:
                                case CombineServiceNew.NANG_CAP_SKH_VIP:
                                case CombineServiceNew.EP_CHUNG_NHAN_XUAT_SU:
                                case CombineServiceNew.GIAM_DINH_SACH:
                                case CombineServiceNew.TAY_SACH:
                                case CombineServiceNew.NANG_CAP_SACH_TUYET_KY:
                                case CombineServiceNew.PHUC_HOI_SACH:
                                case CombineServiceNew.PHAN_RA_SACH:

                                    if (select == 0) {
                                        CombineServiceNew.gI().startCombine(player);
                                    }
                                    break;
                            }
                        }

                    }
                }
            }
        };
    }

    public static Npc ruongDo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    InventoryServiceNew.gI().sendItemBox(player);
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                }
            }
        };
    }

    public static Npc duongtank(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (mapId == 0) {
                        nguhs.gI().setTimeJoinnguhs();
                        long now = System.currentTimeMillis();
                        if (now > nguhs.TIME_OPEN_NHS && now < nguhs.TIME_CLOSE_NHS) {
                            this.createOtherMenu(player, 0, "Ngũ Hàng Sơn x10 Tnsm\nHỗ trợ cho Ae trên 80 Tỷ SM?\nThời gian từ 13h - 18h, 250 hồng ngọc 1 lần vào", "OK", "Đóng");
                        } else {
                            this.createOtherMenu(player, 0, "Ngũ Hàng Sơn x10 Tnsm\nHỗ trợ cho Ae trên 80 Tỷ SM?\nThời gian từ 13h - 18h, 250 hồng ngọc 1 lần vào", "OK");
                        }
                    }
                    if (mapId == 122) {
                        this.createOtherMenu(player, 0, "Bạn Muốn Quay Trở Lại Làng Ảru?", "OK", "Từ chối");

                    }
                    if (mapId == 124) {
                        this.createOtherMenu(player, 0, "Xia xia thua phùa\b|7|Thí chủ đang có: " + player.NguHanhSonPoint + " điểm ngũ hành sơn\b|1|Thí chủ muốn đổi cải trang x4 chưởng ko?", "Âu kê", "Top Ngu Hanh Son", "No");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                    if (mapId == 0) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.power < 1L) {
                                    Service.getInstance().sendThongBao(player, "Sức mạnh bạn Đéo đủ để qua map!");
                                    return;
                                } else if (player.inventory.ruby < 500) {
                                    Service.getInstance().sendThongBao(player, "Phí vào là 500 hồng ngọc một lần bạn ey!\nBạn đéo đủ!");
                                    return;
                                } else {
                                    player.inventory.ruby -= 500;
                                    PlayerService.gI().sendInfoHpMpMoney(player);
                                    ChangeMapService.gI().changeMapInYard(player, 122, -1, -1);
                                }
                                break;
                        }
                    }
                    if (mapId == 122) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapInYard(player, 0, -1, 469);
                        }
                    }
                    if (mapId == 124) {
                        if (select == 0) {
                            if (player.NguHanhSonPoint >= 500) {
                                player.NguHanhSonPoint -= 500;
                                Item item = ItemService.gI().createNewItem((short) (711));
                                item.itemOptions.add(new Item.ItemOption(49, 25));
                                item.itemOptions.add(new Item.ItemOption(77, 25));
                                item.itemOptions.add(new Item.ItemOption(103, 25));
                                item.itemOptions.add(new Item.ItemOption(207, 0));
                                item.itemOptions.add(new Item.ItemOption(33, 0));
//                                      
                                InventoryServiceNew.gI().addItemBag(player, item);
                                Service.gI().sendThongBao(player, "Chúc Mừng Bạn Đổi Vật Phẩm Thành Công !");
                            } else {
                                Service.gI().sendThongBao(player, "Không đủ điểm, bạn còn " + (500 - player.pointPvp) + " điểm nữa");
                            }

                        }
                    }

                }
            }
        };
    }

    public static Npc dauThan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    player.magicTree.openMenuTree();
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_LEFT_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                if (player.magicTree.level == 11) {
                                    player.magicTree.fastRespawnPea();
                                } else {
                                    player.magicTree.showConfirmUpgradeMagicTree();
                                }
                            } else if (select == 2) {
                                player.magicTree.fastRespawnPea();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_NON_UPGRADE_FULL_PEA:
                            if (select == 0) {
                                player.magicTree.harvestPea();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUpgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UPGRADE:
                            if (select == 0) {
                                player.magicTree.upgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_UPGRADE:
                            if (select == 0) {
                                player.magicTree.fastUpgradeMagicTree();
                            } else if (select == 1) {
                                player.magicTree.showConfirmUnuppgradeMagicTree();
                            }
                            break;
                        case ConstNpc.MAGIC_TREE_CONFIRM_UNUPGRADE:
                            if (select == 0) {
                                player.magicTree.unupgradeMagicTree();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc calick(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            private final byte COUNT_CHANGE = 50;
            private int count;

            private void changeMap() {
                if (this.mapId != 102) {
                    count++;
                    if (this.count >= COUNT_CHANGE) {
                        count = 0;
                        this.map.npcs.remove(this);
                        Map map = MapService.gI().getMapForCalich();
                        this.mapId = map.mapId;
                        this.cx = Util.nextInt(100, map.mapWidth - 100);
                        this.cy = map.yPhysicInTop(this.cx, 0);
                        this.map = map;
                        this.map.npcs.add(this);
                    }
                }
            }

            @Override
            public void openBaseMenu(Player player) {
                player.iDMark.setIndexMenu(ConstNpc.BASE_MENU);
                if (TaskService.gI().getIdTask(player) < ConstTask.TASK_20_0) {
                    Service.gI().hideWaitDialog(player);
                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                    return;
                }
                if (this.mapId != player.zone.map.mapId) {
                    Service.gI().sendThongBao(player, "Calích đã rời khỏi map!");
                    Service.gI().hideWaitDialog(player);
                    return;
                }

                if (this.mapId == 102) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?",
                            "Kể\nChuyện", "Quay về\nQuá khứ");
                } else {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Chào chú, cháu có thể giúp gì?", "Kể\nChuyện", "Đi đến\nTương lai", "Từ chối", "Sát Thương\n Thần Mèo");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (this.mapId == 102) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            //kể chuyện
                            NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                        } else if (select == 1) {
                            //về quá khứ
                            ChangeMapService.gI().goToQuaKhu(player);
                        }
                    }
                } else if (player.iDMark.isBaseMenu()) {
                    if (select == 0) {
                        //kể chuyện
                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.CALICK_KE_CHUYEN);
                    } else if (select == 3) {
                        Service.gI().sendThongBaoOK(player,
                                "Bạn đã gây " + player.damethanmeo + " Sát thương"
                                + "\nBạn đã gây " + player.ptdame + "% Sát thương");
                    } else if (select == 1) {
                        //đến tương lai
//                                    changeMap();
                        if (TaskService.gI().getIdTask(player) >= ConstTask.TASK_20_0) {
                            ChangeMapService.gI().goToTuongLai(player);
                        }
                    } else {
                        Service.gI().sendThongBao(player, "Không thể thực hiện");
                    }
                }
            }
        };
    }

    public static Npc jaco(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Gô Tên, Calich và Monaka đang gặp chuyện ở hành tinh Potaufeu \n Hãy đến đó ngay", "Đến \nPotaufeu");
                    } else if (this.mapId == 139) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        if (player.getSession().player.nPoint.power >= 800000000L) {

                            ChangeMapService.gI().goToPotaufeu(player);
                        } else {
                            this.npcChat(player, "Bạn chưa đủ 800tr sức mạnh để vào!");
                        }
                    } else if (this.mapId == 139) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về trạm vũ trụ
                                case 0:
                                    this.npcChat(player, "Chức Năng Tạm Thời Đang Bảo Trì!");
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    //public static Npc Potage(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                if (canOpenNpc(player)) {
//                    if (this.mapId == 149) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "tét", "Gọi nhân bản");
//                    }
//                }
//            }
//            @Override
//            public void confirmMenu(Player player, int select) {
//                if (canOpenNpc(player)) {
//                   if (select == 0){
//                        BossManager.gI().createBoss(-214);
//                   }
//                }
//            }
//        };
//    }
//    public static Npc npclytieunuong54(int mapId, int status, int cx, int cy, int tempId, int avartar) {
//        return new Npc(mapId, status, cx, cy, tempId, avartar) {
//            @Override
//            public void openBaseMenu(Player player) {
//                createOtherMenu(player, 0, "Trò chơi Chọn ai đây đang được diễn ra, nếu bạn tin tưởng mình đang tràn đầy may mắn thì có thể tham gia thử", "Đóng");
//            }
//
//            @Override
//            public void confirmMenu(Player pl, int select) {
//                if (canOpenNpc(pl)) {
//                    String time = ((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
//                    if (((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) < 0) {
//                        ChonAiDay.gI().lastTimeEnd = System.currentTimeMillis() + 300000;
//                    }
//                    if (pl.iDMark.getIndexMenu() == 0) {
//                        if (select == 0) {
//                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "Thời gian giữa các giải là 5 phút\nKhi hết giờ, hệ thống sẽ ngẫu nhiên chọn ra 1 người may mắn.\nLưu ý: Số thỏi vàng nhận được sẽ bị nhà cái lụm đi 5%!Trong quá trình diễn ra khi đặt cược nếu thoát game mọi phần đặt đều sẽ bị hủy", "Ok");
//                        } else if (select == 1) {
//                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
//                        }
//                    } else if (pl.iDMark.getIndexMenu() == 1) {
//                        if (((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0) {
//                            switch (select) {
//                                case 0:
//                                            Service.gI().sendThongBao(pl, "Chờ ĐứcNT update nhé!!!");                                    
////                                    createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
////                                    
//                                    break;
//                                case 1: {
//                                    try {
//                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 20) {
//                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 20);
//                                            InventoryServiceNew.gI().sendItemBags(pl);
//                                            pl.goldNormar += 20;
//                                            ChonAiDay.gI().goldNormar += 20;
//                                            ChonAiDay.gI().addPlayerNormar(pl);
//                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
//                                        } else {
//                                            Service.gI().sendThongBao(pl, "Bạn không đủ thỏi vàng");
//
//                                        }
//                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class
//                                                .getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                }
//                                break;
//
//                                case 2: {
//                                    try {
//                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 200) {
//                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 200);
//                                            InventoryServiceNew.gI().sendItemBags(pl);
//                                            pl.goldVIP += 200;
//                                            ChonAiDay.gI().goldVip += 200;
//                                            ChonAiDay.gI().addPlayerVIP(pl);
//                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
//                                        } else {
//                                            Service.gI().sendThongBao(pl, "Bạn không đủ thỏi vàng");
//                                        }
//                                    } catch (Exception ex) {
////                                            java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                }
//                                break;
//
//                            }
//                        }
//                    }
//                }
//            }
//        };
//    }
    public static Npc npclytieunuong54(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                createOtherMenu(player, 0, "Trò chơi Chọn ai đây đang được diễn ra, nếu bạn tin tưởng mình đang tràn đầy may mắn thì có thể tham gia thử", "Thể lệ", "Chọn\nThỏi vàng");
            }

            @Override
            public void confirmMenu(Player pl, int select) {
                if (canOpenNpc(pl)) {
                    String time = ((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) + " giây";
                    if (pl.iDMark.getIndexMenu() == 0) {
                        if (select == 0) {
                            createOtherMenu(pl, ConstNpc.IGNORE_MENU, "Thời gian giữa các giải là 5 phút\nKhi hết giờ, hệ thống sẽ ngẫu nhiên chọn ra 1 người may mắn.\nLưu ý: Số thỏi vàng nhận được sẽ bị nhà cái lụm đi 5%!Trong quá trình diễn ra khi đặt cược nếu thoát game mọi phần đặt đều sẽ bị hủy", "Ok");
                        } else if (select == 1) {
                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                        }
                    } else if (pl.iDMark.getIndexMenu() == 1) {
                        if (((ChonAiDay.gI().lastTimeEnd - System.currentTimeMillis()) / 1000) > 0) {
                            switch (select) {
                                case 0:
                                    createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                    break;
                                case 1: {
                                    try {
                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 20) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 20);
                                            InventoryServiceNew.gI().sendItemBags(pl);
                                            pl.goldNormar += 20;
                                            ChonAiDay.gI().goldNormar += 20;
                                            ChonAiDay.gI().addPlayerNormar(pl);
                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                        } else {
                                            Service.gI().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                            return;
                                        }
                                    } catch (Exception ex) {
//                                        java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;

                                case 2: {
                                    try {
                                        if (InventoryServiceNew.gI().findItemBag(pl, 457).isNotNullItem() && InventoryServiceNew.gI().findItemBag(pl, 457).quantity >= 200) {
                                            InventoryServiceNew.gI().subQuantityItemsBag(pl, InventoryServiceNew.gI().findItemBag(pl, 457), 200);
                                            InventoryServiceNew.gI().sendItemBags(pl);
                                            pl.goldVIP += 200;
                                            ChonAiDay.gI().goldVip += 200;
                                            ChonAiDay.gI().addPlayerVIP(pl);
                                            createOtherMenu(pl, 1, "Tổng giải thường: " + ChonAiDay.gI().goldNormar + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(0) + "%\nTổng giải VIP: " + ChonAiDay.gI().goldVip + " thỏi vàng, cơ hội trúng của bạn là: " + pl.percentGold(1) + "%\nSố thỏi vàng đặt thường: " + pl.goldNormar + "\nSố thỏi vàng đặt VIP: " + pl.goldVIP + "\n Thời gian còn lại: " + time, "Cập nhập", "Thường\n20 thỏi\nvàng", "VIP\n200 thỏi\nvàng", "Đóng");
                                        } else {
                                            Service.gI().sendThongBao(pl, "Bạn không đủ thỏi vàng");
                                        }
                                    } catch (Exception ex) {
//                                            java.util.logging.Logger.getLogger(NpcFactory.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;

                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc thuongDe(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (!canOpenNpc(player)) {
                    return;
                }

                String message;
                if (this.mapId == 141) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hãy nắm lấy tay ta mau!", "Về\nthần điện");
                } else {
                    if (player.typetrain == 1 && !player.istrain) {
                        message = "Pôpô là đệ tử của ta, luyện tập với Pôpô con sẽ có nhiều kinh nghiệm đánh bại được Pôpô ta sẽ dạy võ công cho con";
                    } else if (player.typetrain == 2 && player.istrain) {
                        message = "Từ nay con sẽ là đệ tử của ta. Ta sẽ truyền cho con tất cả tuyệt kĩ";
                    } else if (player.typetrain == 1 && player.istrain) {
                        message = "Pôpô là đệ tử của ta, luyện tập với Pôpô con sẽ có nhiều kinh nghiệm đánh bại được Pôpô ta sẽ dạy võ công cho con";
                    } else if (player.typetrain == 2 && !player.istrain) {
                        message = "Từ nay con sẽ là đệ tử của ta. Ta sẽ truyền cho con tất cả tuyệt kĩ";
                    } else {
                        message = "Con đã mạnh hơn ta, ta sẽ chỉ đường cho con đến Kaio để gặp thần Vũ Trụ Phương Bắc\nNgài là thần cai quản vũ trụ này, hãy theo ngài ấy học võ công";
                    }

                    if (player.typetrain == 1 && !player.istrain) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Mr.PôPô", "Thách đấu Mr.PôPô", "Đến Kaio", "Quay số\nmay mắn");
                    } else if (player.typetrain == 2 && player.istrain) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Mr.PôPô", "Thách đấu\nvới thượng đế", "Đến Kaio", "Quay số\nmay mắn");
                    } else if (player.typetrain == 1 && player.istrain) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Mr.PôPô", "Thách đấu Mr.PôPô", "Đến Kaio", "Quay số\nmay mắn");
                    } else if (player.typetrain == 2 && !player.istrain) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện\nvới Mr.PôPô", "Thách đấu\nvới thượng đế", "Đến Kaio", "Quay số\nmay mắn");
                    } else {
                        if (!player.istrain) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Mr.PôPô", "Tập luyện với thượng đế", "Đến Kaio", "Quay số\nmay mắn");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Mr.PôPô", "Tập luyện với thượng đế", "Đến Kaio", "Quay số\nmay mắn");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player) && this.mapId == 45) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            if (!player.istrain) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ " + player.nPoint.getexp() + " sức mạnh mỗi phút", "Hướng dẫn thêm", "Đồng ý 1 ngọc mỗi lần", "Không đồng ý");
                            } else {
                                player.istrain = false;
                                this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con đã hủy thành công đăng ký tập tự động", "Đóng");
                            }
                        } else if (select == 1) {
                            this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY0, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                        } else if (select == 2) {
                            if (player.typetrain > 2) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY1, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                            } else if (player.typetrain == 1) {
                                player.setfight((byte) 1, (byte) 0);
                                player.zone.load_Me_To_Another(player);
                                player.zone.load_Another_To_Me(player);

                            } else {

                                ChangeMapService.gI().changeMap(player, 49, 0, 578, 456);
                                player.setfight((byte) 1, (byte) 1);
                                try {
                                    new Thuongde(BossID.THUONG_DE, BossesData.THUONG_DE, player.zone, player.location.x - 10, player.location.y);
                                    player.zone.load_Another_To_Me(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (select == 3) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 48, -1, 354);
                        } else if (select == 4) {
                            this.createOtherMenu(player, ConstNpc.MENU_CHOOSE_LUCKY_ROUND,
                                    "Con muốn làm gì nào?", "Quay bằng\nvàng", "Quay bằng\nngọc",
                                    "Rương phụ\n("
                                    + (player.inventory.itemsBoxCrackBall.size()
                                    - InventoryServiceNew.gI().getCountEmptyListItem(player.inventory.itemsBoxCrackBall))
                                    + " món)",
                                    "Xóa hết\ntrong rương", "Đóng");

                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE) {
                        switch (select) {
                            case 0:
                                Service.gI().sendPopUpMultiLine(player, tempId, this.avartar, ConstNpc.INFOR_TRAIN_OFFLINE);
                                break;
                            case 1:
                                player.istrain = true;
                                NpcService.gI().createTutorial(player, this.avartar, "Từ giờ, quá 30 phút Offline con sẽ tự động luyện tập");
                                break;
                            case 3:
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY0) {
                        switch (select) {
                            case 0:
                                player.setfight((byte) 0, (byte) 0);
                                player.zone.load_Me_To_Another(player);
                                player.zone.load_Another_To_Me(player);

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY1) {
                        switch (select) {
                            case 0:
                                player.setfight((byte) 1, (byte) 1);
                                ChangeMapService.gI().changeMap(player, 49, 0, 578, 456);
                                try {
                                    new Thuongde(BossID.THUONG_DE, BossesData.THUONG_DE, player.zone, player.location.x - 10, player.location.y);
                                    player.zone.load_Another_To_Me(player);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_CHOOSE_LUCKY_ROUND) {
                        switch (select) {
                            case 0:
                                LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GOLD);
                                break;
                            case 1:
                                LuckyRound.gI().openCrackBallUI(player, LuckyRound.USING_GEM);
                                break;
                            case 2:
                                ShopServiceNew.gI().opendShop(player, "ITEMS_LUCKY_ROUND", true);
                                break;
                            case 3:
                                NpcService.gI().createMenuConMeo(player,
                                        ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND, this.avartar,
                                        "Con có chắc muốn xóa hết vật phẩm trong rương phụ? Sau khi xóa "
                                        + "sẽ không thể khôi phục!",
                                        "Đồng ý", "Hủy bỏ");
                                break;
                        }
                    }
                }
                if (this.mapId == 141) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ChangeMapService.gI().changeMapInYard(player, 45, 0, 408);
                                Service.gI().sendThongBao(player, "Hãy xuống dưới gặp thần\nmèo Karin");
                                player.clan.gobosscdrd = true;
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc thanVuTru(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    String message;
                    if (this.mapId == 48) {
                        if (player.typetrain == 3 && !player.istrain) {
                            message = "Thượng đế đưa người đến đây, chắc muốn ta dạy võ chứ gì\nBắt được con khỉ Bubbles rồi hãy tính";
                        } else if (player.typetrain == 4 && player.istrain) {
                            message = "Ta là Thần Vũ Trụ Phương Bắc cai quản khu vực Bắc Vũ Trụ\nnếu thắng được ta\nngươi sẽ đến lãnh địa Kaio, nơi ở của thần linh ";
                        } else if (player.typetrain == 3 && player.istrain) {
                            message = "Thượng đế đưa người đến đây, chắc muốn ta dạy võ chứ gì\nBắt được con khỉ Bubbles rồi hãy tính";
                        } else if (player.typetrain == 4 && !player.istrain) {
                            message = "Ta là Thần Vũ Trụ Phương Bắc cai quản khu vực Bắc Vũ Trụ\nnếu thắng được ta\nngươi sẽ đến lãnh địa Kaio, nơi ở của thần linh ";
                        } else {
                            message = "Con mạnh nhất phía bắc vũ trụ này rồi đấy nhưng ngoài vũ trụ bao la kia vẫn có những kẻ mạnh hơn nhiều\ncon cần phải tập luyện để mạnh hơn nữa";
                        }

                        if (player.typetrain == 3 && !player.istrain) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Khỉ Bubbles", "Thách đấu Khỉ Bubbles", "Di chuyển");
                        } else if (player.typetrain == 4 && player.istrain) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Khỉ Bubbles", "Thách đấu\nvới Thần\nVũ Trụ", "Di chuyển");
                        } else if (player.typetrain == 3 && player.istrain) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Khỉ Bubbles", "Thách đấu Khỉ Bubbles", "Di chuyển");
                        } else if (player.typetrain == 4 && !player.istrain) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện\nvới Khỉ Bubbles", "Thách đấu\nvới Thần\nVũ Trụ", "Di chuyển");
                        } else {
                            if (!player.istrain) {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Khỉ Bubbles", "Tập luyện \nvới Thần\nVũ Trụ", "Di chuyển");
                            } else {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Khỉ Bubbles", "Tập luyện\nvới Thần\nVũ Trụ", "Di chuyển");
                            }
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                if (!player.istrain) {
                                    this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ " + player.nPoint.getexp() + " sức mạnh mỗi phút", "Hướng dẫn thêm", "Đồng ý 1 ngọc mỗi lần", "Không đồng ý");
                                } else {
                                    player.istrain = false;
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con đã hủy thành công đăng ký tập tự động", "Đóng");
                                }
                            } else if (select == 1) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY0, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                            } else if (select == 2) {
                                if (player.typetrain > 4) {
                                    this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY1, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                                } else if (player.typetrain == 3) {
                                    player.setfight((byte) 1, (byte) 0);
                                    player.zone.load_Me_To_Another(player);
                                    player.zone.load_Another_To_Me(player);

                                } else {
                                    player.setfight((byte) 1, (byte) 1);
                                    player.zone.mapInfo(player);
                                    DataGame.updateMap(player.getSession());
                                    try {
                                        new ThanVuTru(BossID.THAN_VUTRU, BossesData.THAN_VU_TRU, player.zone, this.cx, this.cy);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (select == 3) {
                                this.createOtherMenu(player, ConstNpc.MENU_DI_CHUYEN,
                                        "Con muốn đi đâu?", "Về\nthần điện", "Thánh địa\nKaio", "Con\nđường\nrắn độc", "Từ chối");

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE) {
                            switch (select) {
                                case 0:
                                    Service.gI().sendPopUpMultiLine(player, tempId, this.avartar, ConstNpc.INFOR_TRAIN_OFFLINE);
                                    break;
                                case 1:
                                    player.istrain = true;
                                    NpcService.gI().createTutorial(player, this.avartar, "Từ giờ, quá 30 phút Offline con sẽ tự động luyện tập");
                                    break;
                                case 3:
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY0) {
                            switch (select) {
                                case 0:
                                    player.setfight((byte) 0, (byte) 0);
                                    player.zone.load_Me_To_Another(player);
                                    player.zone.load_Another_To_Me(player);

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY1) {
                            switch (select) {
                                case 0:
                                    player.setfight((byte) 1, (byte) 1);
                                    player.zone.mapInfo(player);
                                    DataGame.updateMap(player.getSession());
                                    try {
                                        new ThanVuTru(BossID.THAN_VUTRU, BossesData.THAN_VU_TRU, player.zone, this.cx, this.cy);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DI_CHUYEN) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 45, -1, 354);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 2:
                                    if (player.clan != null) {
                                        if (player.clan.ConDuongRanDoc != null) {
                                            this.createOtherMenu(player, ConstNpc.MENU_OPENED_CDRD,
                                                    "Bang hội của con đang đi con đường rắn độc cấp độ "
                                                    + player.clan.ConDuongRanDoc.level + "\nCon có muốn đi theo không?",
                                                    "Đồng ý", "Từ chối");
                                        } else {

                                            this.createOtherMenu(player, ConstNpc.MENU_OPEN_CDRD,
                                                    "Đây là Con đường rắn độc \nCác con cứ yên tâm lên đường\n"
                                                    + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                    "Chọn\ncấp độ", "Từ chối");
                                        }
                                    } else {
                                        this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                    }
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_CDRD) {
                            switch (select) {
                                case 0:
                                    if (player.clan.haveGoneConDuongRanDoc) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Bang hội của ngươi đã đi con đường rắn độc lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenConDuongRanDoc, "HH:mm:ss") + " hôm nay. Người mở\n"
                                                + "(" + player.clan.playerOpenConDuongRanDoc.name + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                                        return;
//                                    } else if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
//                                        Service.gI().sendThongBao(player, "Yêu cầu tham gia bang hội trên 2 ngày!");
//                                        return;
                                    } else if (player.nPoint.power < ConDuongRanDoc.POWER_CAN_GO_TO_CDRD) {
                                        this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                                + Util.numberToMoney(ConDuongRanDoc.POWER_CAN_GO_TO_CDRD));
                                        return;
                                    } else {
                                        ChangeMapService.gI().goToCDRD(player);
                                    }
                                    break;

                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_CDRD) {
                            switch (select) {
                                case 0:
                                    if (player.clan.haveGoneConDuongRanDoc) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Bang hội của ngươi đã đi con đường rắn độc lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenConDuongRanDoc, "HH:mm:ss") + " hôm nay. Người mở\n"
                                                + "(" + player.clan.playerOpenConDuongRanDoc.name + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                                        return;
//                                    } else if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
//                                        Service.gI().sendThongBao(player, "Yêu cầu tham gia bang hội trên 2 ngày!");
//                                        return;
                                    } else if (player.nPoint.power < ConDuongRanDoc.POWER_CAN_GO_TO_CDRD) {
                                        this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                                + Util.numberToMoney(ConDuongRanDoc.POWER_CAN_GO_TO_CDRD));
                                        return;
                                    } else {
                                        Input.gI().createFormChooseLevelCDRD(player);
                                    }
                                    break;
                            }

                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCEPT_GO_TO_CDRD) {
                            switch (select) {
                                case 0:
                                    ConDuongRanDocService.gI().openConDuongRanDoc(player, Byte.parseByte(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                    break;
                            }
                        }
                    }
                }
            }

        };
    }

    public static Npc TosuKaio(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    String message;
                    if (this.mapId == 50) {
                        if (player.typetrain >= 5) {
                            message = "Tập luyện với Tổ sư Kaio sẽ tăng " + player.nPoint.getexp() + " sức mạnh mỗi phút, con có muốn đăng ký không?";
                            if (!player.istrain) {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Đồng ý\nluyện tập", "Không đồng ý");
                            } else {
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Đồng ý\nluyện tập", "Không đồng ý");
                            }
                        } else if (player.typetrain < 5) {
                            message = "Hãy đánh bại các cao thủ rồi quay lại đây";
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Vâng ạ");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50 && player.typetrain == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                if (!player.istrain) {
                                    this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ " + player.nPoint.getexp() + " sức mạnh mỗi phút", "Hướng dẫn thêm", "Đồng ý 1 ngọc mỗi lần", "Không đồng ý");
                                } else {
                                    player.istrain = false;
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con đã hủy thành công đăng ký tập tự động", "Đóng");
                                }
                            } else if (select == 1) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY0, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE) {
                            switch (select) {
                                case 0:
                                    Service.gI().sendPopUpMultiLine(player, tempId, this.avartar, ConstNpc.INFOR_TRAIN_OFFLINE);
                                    break;
                                case 1:
                                    player.istrain = true;
                                    NpcService.gI().createTutorial(player, this.avartar, "Từ giờ, quá 30 phút Offline con sẽ tự động luyện tập");
                                    break;
                                case 3:
                                    break;
                            }

                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY0) {
                            switch (select) {
                                case 0:
                                    player.setfight((byte) 1, (byte) 1);
                                    player.zone.mapInfo(player);
                                    DataGame.updateMap(player.getSession());
                                    try {
                                        new ToSuKaio(BossID.TS_KAIO, BossesData.TO_SU_KAIO, player.zone, this.cx, this.cy);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                            }
                        }
                    }
                }
            }

        };
    }

    public static Npc kibit(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Từ chối");
                    }
                    if (this.mapId == 114) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc osin(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến\nKaio", "Đến\nhành tinh\nBill", "Từ chối");
                    } else if (this.mapId == 154) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Về thánh địa", "Đến\nhành tinh\nngục tù", "Từ chối");
                    } else if (this.mapId == 155) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else if (this.mapId == 52) {
                        try {
                            MapMaBu.gI().setTimeJoinMapMaBu();
                            MapMaBu2h.gI().setTimeJoinMapMaBu2h();
                            if (this.mapId == 52) {
                                long now = System.currentTimeMillis();
                                if (now > MapMaBu.TIME_OPEN_MABU && now < MapMaBu.TIME_CLOSE_MABU) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else if (now > MapMaBu2h.TIME_OPEN_MABU_2H && now < MapMaBu2h.TIME_CLOSE_MABU_2H) {
                                    this.createOtherMenu(player, ConstNpc.MENU_OPEN_MMB, "Đại chiến Ma Bư 2 giờ đã mở, "
                                            + "ngươi có muốn tham gia không?",
                                            "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_MMB,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }

                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu osin");
                        }

                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.fightMabu.pointMabu >= player.fightMabu.POINT_MAX) {
                            this.createOtherMenu(player, ConstNpc.GO_UPSTAIRS_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Lên Tầng!", "Quay về", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                    "Quay về", "Từ chối");
                        }
                    } else if (this.mapId == 120) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 50) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 48, -1, 354, 240);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                                    break;
                            }
                        }
                    } else if (this.mapId == 154) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 50, -1, 318, 336);
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMap(player, 155, -1, 111, 792);
                                    break;
                            }
                        }
                    } else if (this.mapId == 155) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMap(player, 154, -1, 200, 312);
                            }
                        }
                    } else if (this.mapId == 52) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.MENU_REWARD_MMB:
                                break;
                            case ConstNpc.MENU_OPEN_MMB:
                                long cc = System.currentTimeMillis();
                                if (cc > MapMaBu.TIME_OPEN_MABU && cc < MapMaBu.TIME_CLOSE_MABU) {

                                    if (select == 0) {
                                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                    } else if (select == 1) {
                                        if (!player.getSession().actived) {
                                            Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                        } else {
                                            ChangeMapService.gI().changeMap(player, 114, -1, 318, 336);
                                        }
                                    }

                                }
                                if (cc > MapMaBu2h.TIME_OPEN_MABU_2H && cc < MapMaBu2h.TIME_CLOSE_MABU_2H) {
                                    if (select == 0) {
                                        NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                    } else if (select == 1) {
                                        if (!player.getSession().actived) {
                                            Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
                                        } else {
                                            ChangeMapService.gI().changeMap(player, 127, -1, 100, 312);
                                        }
                                    }
                                }
                                break;
                            case ConstNpc.MENU_NOT_OPEN_BDW:
                                if (select == 0) {
                                    NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_MAP_MA_BU);
                                }
                                break;

                        }
                    } else if (this.mapId >= 114 && this.mapId < 120 && this.mapId != 116) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.GO_UPSTAIRS_MENU) {
                            switch (select) {
                                case 0:
                                    player.fightMabu.clear();
                                    ChangeMapService.gI().changeMap(player, this.map.mapIdNextMabu((short) this.mapId), -1, this.cx, this.cy);
                                    break;
                                case 1:
                                    if (player.inventory.gold >= 50000000) {
                                        Service.getInstance().changeFlag(player, 9);
                                        player.inventory.gold(50000000);
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Không đủ vàng");
                                    }
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            if (select == 0) {
                                if (player.inventory.gold >= 50000000) {
                                    Service.getInstance().changeFlag(player, 9);
                                    player.inventory.gold(50000000);
                                } else {
                                    Service.getInstance().sendThongBao(player, "Không đủ vàng");
                                }
                            } else if (select == 1) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 120) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    } else if (this.mapId == 127) {
                        if (player.iDMark.getIndexMenu() == ConstNpc.BASE_MENU) {
                            if (select == 0) {
                                ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, 0, -1);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc docNhan(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai_haveGone) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                        return;
                    }

                    boolean flag = true;
                    for (Mob mob : player.zone.mobs) {
                        if (!mob.isDie()) {
                            flag = false;
                        }
                    }
                    for (Player boss : player.zone.getBosses()) {
                        if (!boss.isDie()) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        player.clan.doanhTrai_haveGone = true;
                        player.clan.doanhTrai.setLastTimeOpen(System.currentTimeMillis() + 290_000);
                        player.clan.doanhTrai.DropNgocRong();
                        for (Player pl : player.clan.membersInGame) {
                            ItemTimeService.gI().sendTextTime(pl, (byte) 0, "Doanh trại độc nhãn sắp kết thúc : ", 300);
                            ChangeMapService.gI().goHomefromDT(pl);
                        }
                        player.clan.doanhTrai.timePickDragonBall = true;
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ta đã thả ngọc rồng ở tất cả các map,mau đi nhặt đi. Hẹn ngươi quay lại vào ngày mai", "OK");
                    } else {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Hãy tiêu diệt hết quái và boss trong map", "OK");
                    }

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc linhCanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.clan == null) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Chỉ tiếp các bang hội, miễn tiếp khách vãng lai", "Đóng");
                        return;
                    }
                    if (player.clan.getMembers().size() < DoanhTrai.N_PLAYER_CLAN) {
                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội phải có ít nhất 5 thành viên mới có thể mở", "Đóng");
                        return;
                    }
                    if (player.clan.doanhTrai != null) {
                        createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                                "Bang hội của ngươi đang đánh trại độc nhãn\n"
                                + "Thời gian còn lại là "
                                + TimeUtil.getMinLeft(player.clan.doanhTrai.getLastTimeOpen(),
                                        DoanhTrai.TIME_DOANH_TRAI / 1000)
                                + " phút . Ngươi có muốn tham gia không?",
                                "Tham gia", "Không", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    int nPlSameClan = 0;
                    for (Player pl : player.zone.getPlayers()) {
                        if (!pl.equals(player) && pl.clan != null
                                && pl.clan.equals(player.clan) && pl.location.x >= 1285
                                && pl.location.x <= 1645) {
                            nPlSameClan++;
                        }
                    }
                    if (nPlSameClan < DoanhTrai.N_PLAYER_MAP) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Ngươi phải có ít nhất " + DoanhTrai.N_PLAYER_MAP
                                + " đồng đội cùng bang đứng gần mới có thể\nvào\n"
                                + "tuy nhiên ta khuyên ngươi nên đi cùng với 3-4 người để khỏi chết.\n"
                                + "Hahaha.",
                                "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    if (player.clanMember.getNumDateFromJoinTimeToToday() < 0) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Doanh trại chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
                                "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    if (player.clan.haveGoneDoanhTrai) {
                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                "Bang hội của ngươi đã đi trại lúc "
                                + TimeUtil.formatTime(player.clan.lastTimeOpenDoanhTrai, "HH:mm:ss")
                                + " hôm nay. Người mở\n"
                                + "(" + player.clan.playerOpenDoanhTrai + "). Hẹn ngươi quay lại vào ngày mai",
                                "OK", "Hướng\ndẫn\nthêm");
                        return;
                    }
                    createOtherMenu(player, ConstNpc.MENU_JOIN_DOANH_TRAI,
                            "Hôm nay bang hội của ngươi chưa vào trại lần nào. Ngươi có muốn vào\n"
                            + "không?\nĐể vào, ta khuyên ngươi nên có 3-4 người cùng bang đi cùng",
                            "Vào\n(miễn phí)", "Không", "Hướng\ndẫn\nthêm");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_JOIN_DOANH_TRAI:
                            if (select == 0) {
                                DoanhTraiService.gI().joinDoanhTrai(player);
                            } else if (select == 2) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                        case ConstNpc.IGNORE_MENU:
                            if (select == 1) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_DOANH_TRAI);
                            }
                            break;
                    }
                }
            }
        };
    }

    private static Npc popo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
//                if (player.clanMember.getNumDateFromJoinTimeToToday() < 1 && player.clan != null) {
//                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
//                                "Map Khí Gas chỉ cho phép những người ở trong bang trên 1 ngày. Hẹn ngươi quay lại vào lúc khác",
//                                "OK", "Hướng\ndẫn\nthêm");
//                        return;
//                    }
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (player.getSession().is_gift_box) {
//                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Chào con, con muốn ta giúp gì nào?", "Giải tán bang hội", "Nhận quà\nđền bù");
                        } else {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thượng đế vừa phát hiện 1 loại khí đang âm thầm\nhủy diệt mọi mầm sống trên Trái Đất,\nnó được gọi là Destron Gas.\nTa sẽ đưa các cậu đến nơi ấy, các cậu sẵn sàng chưa?", "Thông Tin Chi Tiết", "OK", "Từ Chối");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 1:
                                if (player.clan != null) {
                                    if (player.clan.khiGas != null) {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPENED_GAS,
                                                "Bang hội của con đang đi DesTroy Gas cấp độ "
                                                + player.clan.khiGas.level + "\nCon có muốn đi theo không?",
                                                "Đồng ý", "Từ chối");
                                    } else if (player.clan.haveGoneGas) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Bang hội của ngươi đã đi Khí gas hủy diệt lúc " + TimeUtil.formatTime(player.clan.timeOpenKhiGas, "HH:mm:ss") + " hôm nay. Người mở\n"
                                                + "(" + player.clan.playerOpenKhiGas.name + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                                        return;
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPEN_GAS,
                                                "Khí Gas Huỷ Diệt đã chuẩn bị tiếp nhận các đợt tấn công của quái vật\n"
                                                + "các con hãy giúp chúng ta tiêu diệt quái vật \n"
                                                + "Ở đây có ta lo\nNhớ chọn cấp độ vừa sức mình nhé",
                                                "Chọn\ncấp độ", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Con phải có bang hội ta mới có thể cho con đi");
                                }
                                break;
//                            case 2:
//                                Clan clan = player.clan;
//                                if (clan != null) {
//                                    ClanMember cm = clan.getClanMember((int) player.id);
//                                    if (cm != null) {
//                                        if (clan.members.size() > 1) {
//                                            Service.gI().sendThongBao(player, "Bang phải còn một người");
//                                            break;
//                                        }
//                                        if (!clan.isLeader(player)) {
//                                            Service.gI().sendThongBao(player, "Phải là bảng chủ");
//                                            break;
//                                        }
////                                        
//                                        NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DISSOLUTION_CLAN, -1, "Con có chắc chắn muốn giải tán bang hội không? Ta cho con 2 lựa chọn...",
//                                                "Yes you do!", "Từ chối!");
//                                    }
//                                    break;
//                                }
//                                Service.gI().sendThongBao(player, "Có bang hội đâu ba!!!");
//                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPENED_GAS) {
                        switch (select) {
                            case 0:
                                if (player.isAdmin() || player.nPoint.power >= Gas.POWER_CAN_GO_TO_GAS) {
                                    ChangeMapService.gI().goToGas(player);
                                } else if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
                                    Service.gI().sendThongBao(player, "Yêu cầu tham gia bang hội trên 2 ngày!");
                                    return;
                                } else {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(Gas.POWER_CAN_GO_TO_GAS));
                                }
                                break;

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPEN_GAS) {
                        switch (select) {
                            case 0:

                                if (player.clan.haveGoneGas) {
                                    createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Bang hội của ngươi đã đi con đường rắn độc lúc " + TimeUtil.formatTime(player.clan.lastTimeOpenConDuongRanDoc, "HH:mm:ss") + " hôm nay. Người mở\n"
                                            + "(" + player.clan.playerOpenConDuongRanDoc.name + "). Hẹn ngươi quay lại vào ngày mai", "OK", "Hướng\ndẫn\nthêm");
                                    return;
                                } else if (player.nPoint.power < Gas.POWER_CAN_GO_TO_GAS) {
                                    this.npcChat(player, "Sức mạnh của con phải ít nhất phải đạt "
                                            + Util.numberToMoney(ConDuongRanDoc.POWER_CAN_GO_TO_CDRD));
                                    return;
//                                    } else if (player.clanMember.getNumDateFromJoinTimeToToday() < 2) {
//                                        Service.gI().sendThongBao(player, "Yêu cầu tham gia bang hội trên 2 ngày!");
//                                        return;
                                } else {
                                    Input.gI().createFormChooseLevelGas(player);
                                }
                                break;
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_ACCPET_GO_TO_GAS) {
                        switch (select) {
                            case 0:
                                GasService.gI().openBanDoKhoBau(player, Integer.parseInt(String.valueOf(PLAYERID_OBJECT.get(player.id))));
                                break;
                        }
                    }
                }
            }
        };
    }

   public static Npc quaTrung(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            private final int COST_AP_TRUNG_NHANH = 1000000000;

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        player.mabuEgg.sendMabuEgg();
                        if (player.mabuEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                    if (this.mapId == 7) {
                        player.billEgg.sendBillEgg();
                        if (player.billEgg.getSecondDone() != 0) {
                            this.createOtherMenu(player, ConstNpc.CAN_NOT_OPEN_EGG, "Bư bư bư...",
                                    "Hủy bỏ\ntrứng", "Ấp nhanh\n" + Util.numberToMoney(COST_AP_TRUNG_NHANH) + " vàng", "Đóng");
                        } else {
                            this.createOtherMenu(player, ConstNpc.CAN_OPEN_EGG, "Bư bư bư...", "Nở", "Hủy bỏ\ntrứng", "Đóng");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == (21 + player.gender)) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_EGG:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.mabuEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.mabuEgg.sendMabuEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.numberToMoney((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_EGG,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_EGG,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        player.mabuEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.mabuEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.mabuEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_EGG:
                                if (select == 0) {
                                    player.mabuEgg.destroyEgg();
                                }
                                break;
                        }
                    }
                    /*  if (this.mapId == 7) {
                        switch (player.iDMark.getIndexMenu()) {
                            case ConstNpc.CAN_NOT_OPEN_BILL:
                                if (select == 0) {
                                    this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_BILL,
                                            "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                } else if (select == 1) {
                                    if (player.inventory.gold >= COST_AP_TRUNG_NHANH) {
                                        player.inventory.gold -= COST_AP_TRUNG_NHANH;
                                        player.billEgg.timeDone = 0;
                                        Service.gI().sendMoney(player);
                                        player.billEgg.sendBillEgg();
                                    } else {
                                        Service.gI().sendThongBao(player,
                                                "Bạn không đủ vàng để thực hiện, còn thiếu "
                                                + Util.numberToMoney((COST_AP_TRUNG_NHANH - player.inventory.gold)) + " vàng");
                                    }
                                }
                                break;
                            case ConstNpc.CAN_OPEN_EGG:
                                switch (select) {
                                    case 0:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_OPEN_BILL,
                                                "Bạn có chắc chắn cho trứng nở?\n"
                                                + "Đệ tử của bạn sẽ được thay thế bằng đệ Mabư",
                                                "Đệ mabư\nTrái Đất", "Đệ mabư\nNamếc", "Đệ mabư\nXayda", "Từ chối");
                                        break;
                                    case 1:
                                        this.createOtherMenu(player, ConstNpc.CONFIRM_DESTROY_BILL,
                                                "Bạn có chắc chắn muốn hủy bỏ trứng Mabư?", "Đồng ý", "Từ chối");
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_OPEN_BILL:
                                switch (select) {
                                    case 0:
                                        player.billEgg.openEgg(ConstPlayer.TRAI_DAT);
                                        break;
                                    case 1:
                                        player.billEgg.openEgg(ConstPlayer.NAMEC);
                                        break;
                                    case 2:
                                        player.billEgg.openEgg(ConstPlayer.XAYDA);
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case ConstNpc.CONFIRM_DESTROY_BILL:
                                if (select == 0) {
                                    player.billEgg.destroyEgg();
                                }
                                break;
                        }
                    }
                     */
                }
            }
        };
    }

    public static Npc quocVuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                this.createOtherMenu(player, ConstNpc.BASE_MENU,
                        "Con muốn nâng giới hạn sức mạnh cho bản thân hay đệ tử?",
                        "Bản thân", "Đệ tử", "Từ chối");
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                if (player.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                    this.createOtherMenu(player, ConstNpc.OPEN_POWER_MYSEFT,
                                            "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của bản thân lên "
                                            + Util.numberToMoney(player.nPoint.getPowerNextLimit()),
                                            "Nâng\ngiới hạn\nsức mạnh",
                                            "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                } else {
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                            "Sức mạnh của con đã đạt tới giới hạn",
                                            "Đóng");
                                }
                                break;
                            case 1:
                                if (player.pet != null) {
                                    if (player.pet.nPoint.limitPower < NPoint.MAX_LIMIT) {
                                        this.createOtherMenu(player, ConstNpc.OPEN_POWER_PET,
                                                "Ta sẽ truền năng lượng giúp con mở giới hạn sức mạnh của đệ tử lên "
                                                + Util.numberToMoney(player.pet.nPoint.getPowerNextLimit()),
                                                "Nâng ngay\n" + Util.numberToMoney(OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) + " vàng", "Đóng");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.IGNORE_MENU,
                                                "Sức mạnh của đệ con đã đạt tới giới hạn",
                                                "Đóng");
                                    }
                                } else {
                                    Service.gI().sendThongBao(player, "Không thể thực hiện");
                                }
                                //giới hạn đệ tử
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_MYSEFT) {
                        switch (select) {
                            case 0:
                                OpenPowerService.gI().openPowerBasic(player);
                                break;
                            case 1:
                                if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                    if (OpenPowerService.gI().openPowerSpeed(player)) {
                                        player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                        Service.gI().sendMoney(player);
                                    }
                                } else {
                                    Service.gI().sendThongBao(player,
                                            "Bạn không đủ vàng để mở, còn thiếu "
                                            + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.OPEN_POWER_PET) {
                        if (select == 0) {
                            if (player.inventory.gold >= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER) {
                                if (OpenPowerService.gI().openPowerSpeed(player.pet)) {
                                    player.inventory.gold -= OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER;
                                    Service.gI().sendMoney(player);
                                }
                            } else {
                                Service.gI().sendThongBao(player,
                                        "Bạn không đủ vàng để mở, còn thiếu "
                                        + Util.numberToMoney((OpenPowerService.COST_SPEED_OPEN_LIMIT_POWER - player.inventory.gold)) + " vàng");
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc Gojo(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta có thể giúp gì cho ngươi ?",
                                "Đến Map Leo Tháp");
                    } else if (this.mapId == 181) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi muốn tiếp tục leo tháp chứ!\n Tháp hiện tại của ngươi là :" + player.capboss,
                                "Thách Đấu", "Xem Top Lep Tháp", "Đến Nghĩa Trang", "Từ chối");
                    }
                }
            }

            @Override

            public void confirmMenu(Player player, int select
            ) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        if (this.mapId == 181) {
                            switch (select) {
                                case 0:
                                    if (player.inventory.gem < 5) {
                                        this.npcChat(player, "Cần 5 ngọc xanh");
                                        return;
                                    }
                                    if (player.nPoint.hpMax + player.nPoint.dame < 20000) {
                                        this.npcChat(player, "Bạn còn quá yếu vui lòng quay lại sau");
                                        return;
                                    }
                                    Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossLV(player.id));
                                    if (oldBossClone != null) {
                                        oldBossClone.setDieLv(oldBossClone);
                                        this.npcChat(player, "Ấn thách đấu lại xem!");
                                    } else {
                                        int hp = 0;

//                                double dk = (player.xuatsu + 1) * 10;
                                        int dk = (player.capboss + 1) * 2;
                                        long hptong = (player.nPoint.hpMax + hp) * dk
                                                * (player.capboss >= 5 ? 2 * dk : 1);
                                        BossData bossDataClone = new BossData(
                                                "Yaritobe Super Red [Lv: " + player.capboss + "]",
                                                ConstPlayer.NAMEC,
                                                new short[]{1441, 1442, 1443, player.getFlagBag(), player.idAura,
                                                    player.getEffFront()},
                                                10_000 * dk,
                                                new long[]{10_000_000 * dk},
                                                new int[]{174},
                                                new int[][]{
                                                    {Skill.LIEN_HOAN, 7, 500},
                                                    {Skill.MASENKO, 7, 3000},
                                                    {Skill.DICH_CHUYEN_TUC_THOI, 7, 60000},
                                                    {Skill.BIEN_KHI, 1, 60000}
                                                },
                                                new String[]{"|-2|Ta sẽ tiêu diệt ngươi"}, // text
                                                // chat 1
                                                new String[]{"|-1|Ta Sẽ đập nát đầu ngươi!"}, // text chat 2
                                                new String[]{"|-1|Hẹn người lần sau"}, // text chat 3
                                                1);
                                        try {
                                            new ThachDauGoJo(Util.createIdBossLV(player.id), bossDataClone, player.zone,
                                                    player.name, player.capboss, player);

                                        } catch (Exception e) {
                                            Logger.logException(NpcFactory.class, e);
                                        }
                                        player.inventory.gem -= 5;
                                        Service.gI().sendMoney(player);
                                    }
                                    break;
                                case 1:
                                    Service.gI().showListTop(player, Manager.TopLeoThap);
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMap(player, 5, -1, -1, -1);
                            }
                        } else if (this.mapId == 5) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMap(player, 181, -1, 513, 480);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc bulmaTL(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu bé muốn mua gì nào?", "Cửa hàng", "Đóng");
                        }
                    } else if (this.mapId == 104) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Kính chào Ngài Linh thú sư!", "Cửa hàng", "Đóng");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 102) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_FUTURE", true);
                            }
                        }
                    } else if (this.mapId == 104) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc rongOmega(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    BlackBallWar.gI().setTime();
                    if (this.mapId == 24 || this.mapId == 25 || this.mapId == 26) {
                        try {
                            long now = System.currentTimeMillis();
                            if (now > BlackBallWar.TIME_OPEN && now < BlackBallWar.TIME_CLOSE) {
                                this.createOtherMenu(player, ConstNpc.MENU_OPEN_BDW, "Đường đến với ngọc rồng sao đen đã mở, "
                                        + "ngươi có muốn tham gia không?",
                                        "Hướng dẫn\nthêm", "Tham gia", "Từ chối");
                            } else {
                                String[] optionRewards = new String[7];
                                int index = 0;
                                for (int i = 0; i < 7; i++) {
                                    if (player.rewardBlackBall.timeOutOfDateReward[i] > System.currentTimeMillis()) {
                                        String quantily = player.rewardBlackBall.quantilyBlackBall[i] > 1 ? "x" + player.rewardBlackBall.quantilyBlackBall[i] + " " : "";
                                        optionRewards[index] = quantily + (i + 1) + " sao";
                                        index++;
                                    }
                                }
                                if (index != 0) {
                                    String[] options = new String[index + 1];
                                    for (int i = 0; i < index; i++) {
                                        options[i] = optionRewards[i];
                                    }
                                    options[options.length - 1] = "Từ chối";
                                    this.createOtherMenu(player, ConstNpc.MENU_REWARD_BDW, "Ngươi có một vài phần thưởng ngọc "
                                            + "rồng sao đen đây!",
                                            options);
                                } else {
                                    this.createOtherMenu(player, ConstNpc.MENU_NOT_OPEN_BDW,
                                            "Ta có thể giúp gì cho ngươi?", "Hướng dẫn", "Từ chối");
                                }
                            }
                        } catch (Exception ex) {
                            Logger.error("Lỗi mở menu rồng Omega");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.MENU_REWARD_BDW:
                            player.rewardBlackBall.getRewardSelect((byte) select);
                            break;
                        case ConstNpc.MENU_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            } else if (select == 1) {
//                                if (!player.getSession().actived) {
//                                    Service.gI().sendThongBao(player, "Vui lòng kích hoạt tài khoản để sử dụng chức năng này");
//
//                                } else
                                player.iDMark.setTypeChangeMap(ConstMap.CHANGE_BLACK_BALL);
                                ChangeMapService.gI().openChangeMapTab(player);
                            }
                            break;
                        case ConstNpc.MENU_NOT_OPEN_BDW:
                            if (select == 0) {
                                NpcService.gI().createTutorial(player, this.avartar, ConstNpc.HUONG_DAN_BLACK_BALL_WAR);
                            }
                            break;
                    }
                }
            }

        };
    }

    public static Npc rong1_to_7s(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isHoldBlackBall()) {
                        this.createOtherMenu(player, ConstNpc.MENU_PHU_HP, "Ta có thể giúp gì cho ngươi?", "Phù hộ", "Từ chối");
                    } else {
                        if (BossManager.gI().existBossOnPlayer(player)
                                || player.zone.items.stream().anyMatch(itemMap -> ItemMapService.gI().isBlackBall(itemMap.itemTemplate.id))
                                || player.zone.getPlayers().stream().anyMatch(p -> p.iDMark.isHoldBlackBall())) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối");
                        } else {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_GO_HOME, "Ta có thể giúp gì cho ngươi?", "Về nhà", "Từ chối", "Gọi BOSS");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHU_HP) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.MENU_OPTION_PHU_HP,
                                    "Ta sẽ giúp ngươi tăng HP lên mức kinh hoàng, ngươi chọn đi",
                                    "x3 HP\n" + Util.numberToMoney(BlackBallWar.COST_X3) + " vàng",
                                    "x5 HP\n" + Util.numberToMoney(BlackBallWar.COST_X5) + " vàng",
                                    "x7 HP\n" + Util.numberToMoney(BlackBallWar.COST_X7) + " vàng",
                                    "Từ chối"
                            );
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_GO_HOME) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, player.gender + 21, -1, 250);
                        } else if (select == 2) {
                            BossManager.gI().callBoss(player, mapId);
                        } else if (select == 1) {
                            this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PHU_HP) {
                        if (player.effectSkin.xHPKI > 1) {
                            Service.gI().sendThongBao(player, "Bạn đã được phù hộ rồi!");
                            return;
                        }
                        switch (select) {
                            case 0:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X3);
                                break;
                            case 1:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X5);
                                break;
                            case 2:
                                BlackBallWar.gI().xHPKI(player, BlackBallWar.X7);
                                break;
                            case 3:
                                this.npcChat(player, "Để ta xem ngươi trụ được bao lâu");
                                break;
                        }
                    }
                }
            }
        };
    }

    public static Npc npcThienSu64(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 14) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 7) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 0) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Ta sẽ dẫn cậu tới hành tinh Berrus với điều kiện\n 2. đạt 80 tỷ sức mạnh "
                            + "\n 3. chi phí vào cổng  50 triệu vàng", "Tới ngay", "Từ chối");
                }
                if (this.mapId == 146) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 147) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 148) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Cậu không chịu nổi khi ở đây sao?\nCậu sẽ khó mà mạnh lên được", "Trốn về", "Ở lại");
                }
                if (this.mapId == 5) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!", "Hướng Dẫn",
                            "Đổi Phiếu\nLấy Điểm", "Điểm Boss","Shop Boss","Top\nSD","TT NV","Đóng");
                }
                if (this.mapId == 154) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Đã tìm đủ nguyên liệu cho tôi chưa?\n Tôi sẽ giúp cậu mạnh lên kha khá đấy!",
                            "Chế Tạo trang bị thiên sứ", "Cửa Hàng\nBán Ấy", "Địt Nhau");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 7) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 1L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.gI().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 146, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 14) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 1L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.gI().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 148, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 0) {
                        if (select == 0) {
                            if (player.getSession().player.nPoint.power >= 1L && player.inventory.gold > COST_HD) {
                                player.inventory.gold -= COST_HD;
                                Service.gI().sendMoney(player);
                                ChangeMapService.gI().changeMapBySpaceShip(player, 147, -1, 168);
                            } else {
                                this.npcChat(player, "Bạn chưa đủ điều kiện để vào");
                            }
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 147) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 0, -1, 450);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 148) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 14, -1, 450);
                        }
                        if (select == 1) {
                        }
                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 146) {
                        if (select == 0) {
                            ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 450);
                        }
                        if (select == 1) {
                        }

                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 5) {
                        if (select == 0) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "x1 phiếu Được 1 Điểm");
                        }
                        if (select == 1) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.DOI_DIEM_VIP);
                        }
                        if (select == 2) {
                            createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ngươi Có " + player.inventory.coupon + " Điểm", "Đóng");
                        }
                        if (select == 3) {
                            ShopServiceNew.gI().opendShop(player, "BILL", false);
                        }
                        if (select == 4) {
                            Service.gI().showListTop(player, Manager.topSD);
                        }
                        if (select == 5) {
                           NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_PLAYER, 12639, "|7|[ Info Player : " + player.name + " ]"
                                    //    + "\nTrạng thái tài khoản : " + (player.getSession().actived == 1 ? " Đã mở thành viên" : " Chưa mở thành viên")
                                      //  + (player.vip == 1 ? " [VIP]" : player.vip == 2 ? " [VIP2]" : player.vip == 3 ? " [VIP3]" : player.vip == 4 ? " [SVIP]" : "")
                                        + "\n|2|Chỉ Số Tổng"
                                        + "\n|5|HP : " + Util.format(player.nPoint.hp) + "/" + Util.format(player.nPoint.hpMax)
                                        + "\nKI : " + Util.format(player.nPoint.mp) + "/" + Util.format(player.nPoint.mpMax)
                                        + "\nSĐ : " + Util.format(player.nPoint.dame)
                                        + "\nDEF : " + Util.format(player.nPoint.def)
                                        + "\nCRIT : " + player.nPoint.crit + "%"
                                        + "\n|2|Chỉ Số Gốc"
                                        + "\n|5|HPG : " + player.nPoint.hpg
                                        + "\nKIG : " + player.nPoint.mpg
                                        + "\nSĐG : " + player.nPoint.dameg
                                        + "\nDEFG : " + player.nPoint.defg
                                        + "\nCRITG : " + player.nPoint.critg + "%"
                                        + "\n|2|Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                        + player.playerTask.sideTask.getLevel() + ")"
                                        + "\n|5|Hiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                        + player.playerTask.sideTask.maxCount + " ("
                                        + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                        + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK,
                                        "Nhập\nGiftcode", "Đổi\nMật Khẩu", "Auto\nBán Vàng", "Auto\nChỉ Số");
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                        switch (player.combineNew.typeCombine) {
                            case CombineServiceNew.DOI_DIEM_VIP:

                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_PHAN_RA_DO_THAN_LINH) {
                        if (select == 0) {
                            CombineServiceNew.gI().startCombine(player);
                        }

                    }
                    if (player.iDMark.isBaseMenu() && this.mapId == 154) {
                        if (select == 0) {
                            CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                        }
                        if (select == 1) {
                            ShopServiceNew.gI().opendShop(player, "WHIS", true);
                        }
                        if (select == 2) {
                            Service.gI().sendThongBaoOK(player, "ok chúc mừng chú em");
                        }

                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                        switch (player.combineNew.typeCombine) {
                            case CombineServiceNew.CHE_TAO_TRANG_BI_TS:

                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_NANG_CAP_DO_TS) {
                        if (select == 0) {
                            CombineServiceNew.gI().startCombine(player);
                        }

                    }
                }
            }

        };
    }

    public static Npc bill(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Gặp Whis Để Đổi Thức Ăn Lấy Điểm Sau Đó Gặp Ta Để Mua Trang Bị Hủy Diệt",
                            "Điểm",
                            "Shop Hủy Diệt", "Đóng");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (this.mapId) {
                        case 48:
                            switch (player.iDMark.getIndexMenu()) {
                                case ConstNpc.BASE_MENU:
                                    if (select == 0) {
                                        createOtherMenu(player, ConstNpc.IGNORE_MENU, "Mày Có " + player.inventory.coupon + " Điểm", "Đóng");
                                    }
                                    if (select == 1) {
                                        if (player.inventory.coupon == 0) {
                                            createOtherMenu(player, ConstNpc.IGNORE_MENU, "Ngươi Không Có Điểm Vui Lòng Đổi Điểm Bằng Thức Ăn", "Đóng");
                                        } else {
                                            ShopServiceNew.gI().opendShop(player, "BILL", false);
                                            break;
                                        }
                                    }
                            }
                            break;
                    }
                }
            }
        };
    }
    public static Npc thuytink(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 5) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thử đánh với ta xem nào.\nNgươi còn 1 lượt cơ mà.",
                            "Chế Tạo", "Ahihi", "Từ chối");
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 5) {
                        switch (select) {
                            case 0:
                                this.createOtherMenu(player, 5, "Ta sẽ giúp ngươi chế tạo trang bị thiên sứ", "Chế tạo", "Shop Thiên Sứ", "Từ chối");
                                break;
                            case 1:
                                Item BiKiepTuyetKy = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1433);
                                if (BiKiepTuyetKy != null) {
                                    if (player.gender == 0) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ Super kamejoko\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                    if (player.gender == 1) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ Ma phông ba\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                    if (player.gender == 2) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ "
                                                + "đíc chưởng liên hoàn\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Hãy tìm bí kíp rồi quay lại gặp ta!");
                                }
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == 5) {
                        switch (select) {
                            case 0:
                                CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                                break;
                            case 1:
                                ShopServiceNew.gI().opendShop(player, "THIEN_SU", false);
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                        switch (player.combineNew.typeCombine) {
                            case CombineServiceNew.CHE_TAO_TRANG_BI_TS:
                                if (select == 0) {
                                    CombineServiceNew.gI().startCombine(player);
                                }
                                break;
                        }
                    
                    } else if (player.iDMark.getIndexMenu() == 6) {
                        switch (select) {
                            case 0:
                                Item sach = InventoryServiceNew.gI().findItemBag(player, 1433);
                                if (sach != null && sach.quantity >= 9999 && player.inventory.gold >= 10000000 && player.inventory.gem > 99 && player.nPoint.power >= 1000000000L) {

                                    if (player.gender == 2) {
                                        SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);
                                    }
                                    if (player.gender == 0) {
                                        SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);
                                    }
                                    if (player.gender == 1) {
                                        SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);
                                    }
                                    InventoryServiceNew.gI().subQuantityItem(player.inventory.itemsBag, sach, 9999);
                                    player.inventory.gold -= 10000000;
                                    player.inventory.gem -= 99;
                                    InventoryServiceNew.gI().sendItemBags(player);
                                } else if (player.nPoint.power < 1000000000L) {
                                    Service.getInstance().sendThongBao(player, "Ngươi không đủ sức mạnh để học tuyệt kỹ");
                                    return;
                                } else if (sach.quantity <= 9999) {
                                    int sosach = 9999 - sach.quantity;
                                    Service.getInstance().sendThongBao(player, "Ngươi còn thiếu " + sosach + " bí kíp nữa.\nHãy tìm đủ rồi đến gặp ta.");
                                    return;
                                } else if (player.inventory.gold <= 10000000) {
                                    Service.getInstance().sendThongBao(player, "Hãy có đủ vàng thì quay lại gặp ta.");
                                    return;
                                } else if (player.inventory.gem <= 99) {
                                    Service.getInstance().sendThongBao(player, "Hãy có đủ ngọc xanh thì quay lại gặp ta.");
                                    return;
                                }

                                break;
                        }
                    }
                }
            }

        };
    }

    public static Npc whis(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (this.mapId == 154) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU, "Thử đánh với ta xem nào.\nNgươi còn 1 lượt cơ mà.",
                            "Nói chuyện", "Học tuyệt kỹ", "Thách Đấu Whis", "Top Thách Đấu", "Từ chối");
                } else if (this.mapId == 48) {
                    this.createOtherMenu(player, ConstNpc.BASE_MENU,
                            "Ngươi tìm ta có việc gì?",
                            "Đóng");

                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu() && this.mapId == 154) {
                        switch (select) {
                            case 0:
                                this.createOtherMenu(player, 5, "Ta sẽ giúp ngươi chế tạo trang bị thiên sứ", "Chế tạo", "Từ chối");
                                break;
                            case 1:
                                Item BiKiepTuyetKy = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 1320);
                                if (BiKiepTuyetKy != null) {
                                    if (player.gender == 0) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ Super kamejoko\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                    if (player.gender == 1) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ Ma phông ba\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                    if (player.gender == 2) {
                                        this.createOtherMenu(player, 6, "|1|Ta sẽ dạy ngươi tuyệt kỹ "
                                                + "đíc chưởng liên hoàn\n" + "|7|Bí kiếp tuyệt kỹ: " + BiKiepTuyetKy.quantity + "/9999\n" + "|2|Giá vàng: 10.000.000\n" + "|2|Giá ngọc: 99",
                                                "Đồng ý", "Từ chối");
                                    }
                                } else {
                                    this.npcChat(player, "Hãy tìm bí kíp rồi quay lại gặp ta!");
                                }
                                break;
                            case 2:
                                if (player.inventory.gem < 5) {
                                    this.npcChat(player, "Cần 5 ngọc xanh");
                                    return;
                                }
                                if (player.nPoint.hpMax + player.nPoint.dame < 20000) {
                                    this.npcChat(player, "Bạn còn quá yếu vui lòng quay lại sau");
                                    return;
                                }
                                Boss oldBossClone = BossManager.gI().getBossById(Util.createIdBossLV(player.id));
                                if (oldBossClone != null) {
                                    oldBossClone.setDieLv(oldBossClone);
                                    this.npcChat(player, "Ấn thách đấu lại xem!");
                                } else {
                                    int hp = 0;
                                    int dk = (player.thachdauwhis + 1) * 2;
                                    long hptong = (player.nPoint.hpMax + hp) * dk
                                            * (player.thachdauwhis >= 5 ? 2 * dk : 1);
                                    BossData bossDataClone = new BossData(
                                            "Whis [Lv: " + player.thachdauwhis + "]",
                                            ConstPlayer.NAMEC,
                                            new short[]{505, 506, 507, -1, -1, -1},
                                            10_000 * dk,
                                            new long[]{10_000_000 * dk},
                                            new int[]{174},
                                            new int[][]{
                                                {Skill.LIEN_HOAN, 7, 500},
                                                {Skill.KAMEJOKO, 7, 3000},
                                                {Skill.DICH_CHUYEN_TUC_THOI, 7, 60000}
                                            },
                                            new String[]{"|-2|Ta sẽ tiêu diệt ngươi"}, // text
                                            // chat 1
                                            new String[]{"|-1|Ta Sẽ đập nát đầu ngươi!"}, // text chat 2
                                            new String[]{"|-1|Hẹn người lần sau"}, // text chat 3
                                            1);
                                    try {
                                        new ThachDauWhis(Util.createIdBossLV(player.id), bossDataClone, player.zone,
                                                player.name, player.thachdauwhis, player);

                                    } catch (Exception e) {
                                        Logger.logException(NpcFactory.class, e);
                                    }
                                    player.inventory.gem -= 5;
                                    Service.gI().sendMoney(player);
                                }
                                break;
                            case 3:
                                Service.gI().showListTop(player, Manager.TopThachDau);
                                break;
                        }
                    } else if (this.mapId == 48) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                            }
                        }
                    } else if (player.iDMark.getIndexMenu() == 5) {
                        switch (select) {
                            // case 0:
                            //    ShopServiceNew.gI().opendShop(player, "THIEN_SU", false);
                            //   break;
                            case 0:
                                CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.CHE_TAO_TRANG_BI_TS);
                                break;
                        }
                        //   } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DAP_DO) {
                        //     if (select == 0) {
                        //       CombineServiceNew.gI().startCombine(player);
                    } else if (player.iDMark.getIndexMenu() == 6) {
                        switch (select) {
                            case 0:
                                Item sach = InventoryServiceNew.gI().findItemBag(player, 1320);
                                if (sach != null && sach.quantity >= 9999 && player.inventory.gold >= 10000000 && player.inventory.gem > 99 && player.nPoint.power >= 1000000000L) {

                                    if (player.gender == 2) {
                                        SkillService.gI().learSkillSpecial(player, Skill.LIEN_HOAN_CHUONG);
                                    }
                                    if (player.gender == 0) {
                                        SkillService.gI().learSkillSpecial(player, Skill.SUPER_KAME);
                                    }
                                    if (player.gender == 1) {
                                        SkillService.gI().learSkillSpecial(player, Skill.MA_PHONG_BA);
                                    }
                                    InventoryServiceNew.gI().subQuantityItem(player.inventory.itemsBag, sach, 9999);
                                    player.inventory.gold -= 10000000;
                                    player.inventory.gem -= 99;
                                    InventoryServiceNew.gI().sendItemBags(player);
                                } else if (player.nPoint.power < 1000000000L) {
                                    Service.getInstance().sendThongBao(player, "Ngươi không đủ sức mạnh để học tuyệt kỹ");
                                    return;
                                } else if (sach.quantity <= 9999) {
                                    int sosach = 9999 - sach.quantity;
                                    Service.getInstance().sendThongBao(player, "Ngươi còn thiếu " + sosach + " bí kíp nữa.\nHãy tìm đủ rồi đến gặp ta.");
                                    return;
                                } else if (player.inventory.gold <= 10000000) {
                                    Service.getInstance().sendThongBao(player, "Hãy có đủ vàng thì quay lại gặp ta.");
                                    return;
                                } else if (player.inventory.gem <= 99) {
                                    Service.getInstance().sendThongBao(player, "Hãy có đủ ngọc xanh thì quay lại gặp ta.");
                                    return;
                                }

                                break;
                        }
                    }
                }
            }

        };
    }

    public static Npc boMong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        if (this.mapId == 47 || this.mapId == 84) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                    "Chào bạn \btôi có thể giúp bạn làm nhiệm vụ", "Nhiệm vụ\nhàng ngày", "Nhận ngọc\nmiễn phí", "Từ chối");
                        }
//                    if (this.mapId == 47) {
//                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
//                                "Xin chào, cậu muốn tôi giúp gì?", "Từ chối");
//                    }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 47 || this.mapId == 84) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    if (player.playerTask.sideTask.template != null) {
                                        String npcSay = "Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                                + player.playerTask.sideTask.getLevel() + ")"
                                                + "\nHiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                                + player.playerTask.sideTask.maxCount + " ("
                                                + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                                + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK;
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_PAY_SIDE_TASK,
                                                npcSay, "Trả nhiệm\nvụ", "Hủy nhiệm\nvụ");
                                    } else {
                                        this.createOtherMenu(player, ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK,
                                                "Tôi có vài nhiệm vụ theo cấp bậc, "
                                                + "sức cậu có thể làm được cái nào?",
                                                "Dễ", "Bình thường", "Khó", "Siêu khó", "Địa ngục", "Từ chối");
                                    }
                                    break;
                                case 1:
                                    player.achievement.Show();
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_LEVEL_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    TaskService.gI().changeSideTask(player, (byte) select);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_OPTION_PAY_SIDE_TASK) {
                            switch (select) {
                                case 0:
                                    TaskService.gI().paySideTask(player);
                                    break;
                                case 1:
                                    TaskService.gI().removeSideTask(player);
                                    break;
                            }

                        }
                    }
                }
            }
        };
    }

    public static Npc karin(int mapId, int status, int cx, int cy, int tempId, int avatar) {
        return new Npc(mapId, status, cx, cy, tempId, avatar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player) && this.mapId == 46) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        String message;
                        if (player.playerTask.taskMain.id == 5 && player.playerTask.taskMain.index == 5) {
                            if (player.istrain) {
                                message = "Muốn chiến thắng Tàu Pảy Pảy phải đánh bại được ta";
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với\nThần Mèo", "Thách đấu với\nThần Mèo");
                            } else {
                                message = "Muốn chiến thắng Tàu Pảy Pảy phải đánh bại được ta";
                                this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với\nThần Mèo", "Thách đấu với\nThần Mèo");
                            }
                        } else if (player.typetrain == 0 && !player.istrain) {
                            message = "Từ giờ Yajirô sẽ luyện tập cùng ngươi. Yajirô đã lên đây đã từng lên đây tập luyện và bây giờ hắn mạnh hơn ta đấy";
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Yajirô", "Thách đấu Yajirô");
                        } else if (player.typetrain != 0 && player.istrain) {
                            message = "Con hãy bay theo cây Gậy Như Ý trên đỉnh tháp để đến Thần Điện gặp Thượng Đế\nCon rất xứng đáng để làm đệ tự của ông ấy";
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Yajirô", "Tập luyện với thần mèo");
                        } else if (player.typetrain == 0 && player.istrain) {
                            message = "Từ giờ Yajirô sẽ luyện tập cùng ngươi. Yajirô đã lên đây đã từng lên đây tập luyện và bây giờ hắn mạnh hơn ta đấy";
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Hủy đăng ký tập tự động", "Tập luyện với Yajirô", "Thách đấu Yajirô");
                        } else if (player.clan != null && player.clan.ConDuongRanDoc != null && player.clan.gobosscdrd) {
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, "Hãy cầm lấy hai hạt đậu cuối cùng của ta đây\nCố giữ mình nhé " + player.name, "Cám ơn\nsư phụ");
                            Service.gI().sendThongBao(player, "Hãy mau bay xuống\nchân tháp Karin");
                        } else {
                            message = "Con hãy bay theo cây Gậy Như Ý trên đỉnh tháp để đến Thần Điện gặp Thượng Đế\nCon rất xứng đáng để làm đệ tự của ông ấy";
                            this.createOtherMenu(player, ConstNpc.BASE_MENU, message, "Đăng ký tập tự động", "Tập luyện với Yajirô", "Tập luyện với thần mèo");
                        }
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player) && this.mapId == 46) {
                    if (player.iDMark.isBaseMenu()) {
                        if (select == 0) {
                            if (player.clan != null && player.clan.ConDuongRanDoc != null && player.clan.gobosscdrd) {
                                player.nPoint.setFullHpMp();
                                //                           ChangeMapService.gI().changeMapInYard(player, 144, 0, 131);
                            } else {
                                if (!player.istrain) {
                                    this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE, "Đăng ký để mỗi khi Offline quá 30 phút, con sẽ được tự động luyện tập với tốc độ " + player.nPoint.getexp() + " sức mạnh mỗi phút", "Hướng dẫn thêm", "Đồng ý 1 ngọc mỗi lần", "Không đồng ý");
                                } else {
                                    player.istrain = false;
                                    this.createOtherMenu(player, ConstNpc.IGNORE_MENU, "Con đã hủy thành công đăng ký tập tự động", "Đóng");
                                }
                            }
                        } else if (select == 1) {
                            if (player.playerTask.taskMain.id == 5 && player.playerTask.taskMain.index == 5) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY0, "Con có chắc muốn tập luyện?\nTập luyện với mèo thần Karin?", "Đồng ý luyện tập", "Không đồng ý");
                            } else {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY0, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                            }
                        } else if (select == 2) {
                            if (player.playerTask.taskMain.id == 5 && player.playerTask.taskMain.index == 5) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY1, "Con có chắc muốn thách đấu?\nThách đấu với mèo thần Karin?", "Đồng ý thách đấu", "Không đồng ý");
                            } else if (player.typetrain != 0) {
                                this.createOtherMenu(player, ConstNpc.MENU_TRAIN_OFFLINE_TRY1, "Con có chắc muốn tập luyện?\nTập luyện với " + player.nPoint.getNameNPC(player, this, (byte) select) + " sẽ tăng " + player.nPoint.getExpbyNPC(player, this, (byte) select) + " sức mạnh mỗi phút", "Đồng ý luyện tập", "Không đồng ý");
                            } else {
                                player.setfight((byte) 1, (byte) 0);
                                player.zone.load_Me_To_Another(player);
                                player.zone.load_Another_To_Me(player);

                            }
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE) {
                        switch (select) {
                            case 0:
                                Service.gI().sendPopUpMultiLine(player, tempId, this.avartar, ConstNpc.INFOR_TRAIN_OFFLINE);
                                break;
                            case 1:
                                player.istrain = true;
                                NpcService.gI().createTutorial(player, this.avartar, "Từ giờ, quá 30 phút Offline con sẽ tự động luyện tập");
                                break;
                            case 3:
                                break;
                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY0) {
                        switch (select) {
                            case 0:
                                if (player.playerTask.taskMain.id == 5 && player.playerTask.taskMain.index == 5) {
                                    player.setfight((byte) 0, (byte) 1);
                                    player.zone.load_Me_To_Another(player);
                                    player.zone.load_Another_To_Me(player);
                                    player.zone.mapInfo(player);
                                    DataGame.updateMap(player.getSession());
                                    try {
                                        new MeoThan(BossID.MEO_THAN, BossesData.THAN_MEO, player.zone, this.cx, this.cy);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    player.setfight((byte) 0, (byte) 0);
                                    player.zone.load_Me_To_Another(player);
                                    player.zone.load_Another_To_Me(player);
                                }

                        }
                    } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_TRAIN_OFFLINE_TRY1) {
                        switch (select) {
                            case 0:
                                if (player.playerTask.taskMain.id == 5 && player.playerTask.taskMain.index == 5) {
                                    player.setfight((byte) 1, (byte) 1);
                                } else {
                                    player.setfight((byte) 0, (byte) 1);
                                }
                                player.zone.load_Me_To_Another(player);
                                player.zone.load_Another_To_Me(player);
                                player.zone.mapInfo(player);
                                DataGame.updateMap(player.getSession());
                                try {
                                    new MeoThan(BossID.MEO_THAN, BossesData.THAN_MEO, player.zone, this.cx, this.cy);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                        }
                    }
                } else if (this.mapId == 104) {
                    if (player.iDMark.isBaseMenu() && select == 0) {
                        ShopServiceNew.gI().opendShop(player, "BUNMA_LINHTHU", true);
                    }
                }

            }
        };
    }

    public static Npc vados(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            public void chatWithNpc(Player player) {
                String[] chat = {
                    "|7|Top Máy Chủ",};
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int index = 0;

                    @Override
                    public void run() {
                        npcChat(player, chat[index]);
                        index = (index + 1) % chat.length;
                    }
                }, 1000, 1000);
            }

            @Override
            public void openBaseMenu(Player player) {
                chatWithNpc(player);
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        createOtherMenu(player, ConstNpc.BASE_MENU,
                                "|2|Ta Vừa Hack Được Top Của Toàn Server\b|7|Mi Muống Xem Tóp Gì?",
                                "Tóp Sức Mạnh", "Top Nhiệm Vụ");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        switch (select) {
                            case 0:
                                Service.gI().showListTop(player, Manager.topSM);
                                break;
                            case 1:
                                Service.gI().showListTop(player, Manager.topNV);
                                break;
                           
                        }
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_1(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 80) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Tới hành tinh\nYardart", "Từ chối");
                    } else if (this.mapId == 131) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Xin chào, tôi có thể giúp gì cho cậu?", "Quay về", "Từ chối");
                    } else {
                        super.openBaseMenu(player);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    switch (player.iDMark.getIndexMenu()) {
                        case ConstNpc.BASE_MENU:
                            if (this.mapId == 131) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 80, -1, 870);
                                }
                            }
                            if (this.mapId == 80) {
                                if (select == 0) {
                                    ChangeMapService.gI().changeMap(player, 131, -1, 901, 240);
                                }
                            }
                            break;
                    }
                }
            }
        };
    }

    public static Npc mavuong(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Xin chào, tôi có thể giúp gì cho cậu?", "Tây thánh địa", "Từ chối");
                    } else if (this.mapId == 156) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Người muốn trở về?", "Quay về", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 153) {
                        if (player.iDMark.isBaseMenu()) {
                            if (select == 0) {
                                //đến tay thanh dia
                                ChangeMapService.gI().changeMapBySpaceShip(player, 156, -1, 360);
                            }
                        }
                    } else if (this.mapId == 156) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                //về lanh dia bang hoi
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 432);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    ///////////////////////////////////////////NPC Chopper///////////////////////////////////////////
    public static Npc chopper(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Êi êi cậu có muốn cùng Chopper đi đến Đảo Kho Báu không,\nnhóm Hải Tặc Mũ Rơm đang chờ đợi cậu đến đó\n Có rất nhiều phần quà mùa hấp dẫn ở đó.\n Đi thôi nào....",
                                "Đi đến\nĐảo Kho Báu", "Chi tiết", "Từ chối");
                    }
                    if (this.mapId == 170) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Cậu muốn quay về Đảo kame à,\nChopper tôi sẽ đưa cậu đi",
                                "Đi thôi", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 170, -1, 1560);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 170) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 312);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    ///////////////////////////////////////////NPC Nami///////////////////////////////////////////
    public static Npc nami(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (!TaskService.gI().checkDoneTaskTalkNpc(player, this)) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "|1|Oh hoan nghên bạn đến với của hàng của tôi\n bạn có muốn đổi vỏ ốc, cua đỏ\nlấy các món đồ mùa hè không?.",
                                "Cửa hàng\nNami");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (player.iDMark.isBaseMenu()) {
                        switch (select) {
                            case 0:
                                ShopServiceNew.gI().opendShop(player, "EVENT_MUA_HE", true);
                                break;
                        }
                    }
                }
            }
        };
    }

    ///////////////////////////////////////////NPC Franky///////////////////////////////////////////
    public static Npc franky(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 170) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Cậu muốn đi ra khơi khám phá?\n Nghe nói Luffy và mọi người đang tìm tên\ngấu tướng cướp ở ngoài đó.",
                                "Ra khơi\nthôi nào", "Từ chối");
                    }
                    if (this.mapId == 0) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "|1|Cậu muốn quay về Đảo kame à,\nđể Franky tôi đưa cậu đi",
                                "Đi thôi", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 170) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapInYard(player, 171, -1, 48);
                                    break;
                            }
                        }
                    }
                    if (this.mapId == 0) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 312);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc gokuSSJ_2(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        this.createOtherMenu(player, ConstNpc.BASE_MENU, "Vào các khung giờ chẵn trong ngày\n"
                                + "Khi luyện tập với Mộc nhân với chế độ bật Cờ sẽ đánh rơi Bí kíp\n"
                                + "Hãy cố găng tập luyện thu thập 9999 bí kíp rồi quay lại gặp ta nhé", "Nhận\nthưởng", "OK");

                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    try {
                        Item biKiep = InventoryServiceNew.gI().findItem(player.inventory.itemsBag, 590);
                        if (select == 0) {
                            if (biKiep != null) {
                                if (biKiep.quantity >= 10000 && InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                    Item yardart = ItemService.gI().createNewItem((short) (player.gender + 592));
                                    yardart.itemOptions.add(new Item.ItemOption(47, 400));
                                    yardart.itemOptions.add(new Item.ItemOption(108, 10));
                                    InventoryServiceNew.gI().addItemBag(player, yardart);
                                    InventoryServiceNew.gI().subQuantityItemsBag(player, biKiep, 10000);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "Bạn vừa nhận được trang phục tộc Yardart");
                                } else if (biKiep.quantity < 10000) {
                                    Service.gI().sendThongBao(player, "Vui lòng sưu tầm đủ\n9999 bí kíp");
                                }
                            } else {
                                Service.gI().sendThongBao(player, "Vui lòng sưu tầm đủ\n9999 bí kíp");
                                return;
                            }
                        } else {
                            return;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        };
    }

    public static Npc GhiDanh(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            String[] menuselect = new String[]{};

            @Override
            public void openBaseMenu(Player pl) {
                if (canOpenNpc(pl)) {
                    if (this.map.mapId == 52) {
                        if (DaiHoiManager.gI().openDHVT && (System.currentTimeMillis() <= DaiHoiManager.gI().tOpenDHVT)) {
                            String nameDH = DaiHoiManager.gI().nameRoundDHVT();
                            this.createOtherMenu(pl, ConstNpc.MENU_DHVT, "Hiện đang có giải đấu " + nameDH + " bạn có muốn đăng ký không? \nSố người đã đăng ký :" + DaiHoiManager.gI().lstIDPlayers.size(), new String[]{"Giải\n" + nameDH + "\n(" + DaiHoiManager.gI().costRoundDHVT() + ")", "Từ chối", "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải siêu hạng"});
                        } else {
                            this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Đã hết hạn đăng ký thi đấu, xin vui lòng chờ đến giải sau", new String[]{"Thông tin\bChi tiết", "OK", "Đại Hội\nVõ Thuật\nLần thứ\n23", "Giải siêu hạng"});
                        }
                    } else if (this.mapId == 129) {
                        int goldchallenge = pl.goldChallenge;
                        if (pl.levelWoodChest == 0) {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Về\nĐại Hội\nVõ Thuật"};
                        } else {
                            menuselect = new String[]{"Thi đấu\n" + Util.numberToMoney(goldchallenge) + " vàng", "Nhận thưởng\nRương cấp\n" + pl.levelWoodChest, "Về\nĐại Hội\nVõ Thuật"};
                        }
                        this.createOtherMenu(pl, ConstNpc.BASE_MENU, "Đại hội võ thuật lần thứ 23\nDiễn ra bất kể ngày đêm,ngày nghỉ ngày lễ\nPhần thưởng vô cùng quý giá\nNhanh chóng tham gia nào", menuselect, "Từ chối");

                    } else {
                        super.openBaseMenu(pl);
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.map.mapId == 52) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    Service.getInstance().sendThongBaoFromAdmin(player, "Lịch thi đấu trong ngày\bGiải Nhi đồng: 8,13,18h\bGiải Siêu cấp 1: 9,14,19h\bGiải Siêu cấp 2: 10,15,20h\bGiải Siêu cấp 3: 11,16,21h\bGiải Ngoại hạng: 12,17,22,23h\nGiải thưởng khi thắng mỗi vòng\bGiải Nhi đồng: 2 ngọc\bGiải Siêu cấp 1: 4 ngọc\bGiải Siêu cấp 2: 6 ngọc\bGiải Siêu cấp 3: 8 ngọc\bGiải Ngoại hạng: 10.000 vàng\bVô địch: 5 viên đá nâng cấp\nVui lòng đến đúng giờ để đăng ký thi đấu");
                                    break;
                                case 1:
                                    Service.getInstance().sendThongBaoFromAdmin(player, "Nhớ Đến Đúng Giờ nhé");
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                    break;
                                case 3:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 113, player.location.x, 360);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_DHVT) {
                            switch (select) {
                                case 0:
//                                    if (DaiHoiService.gI().canRegisDHVT(player.nPoint.power)) {
                                    if (DaiHoiManager.gI().lstIDPlayers.size() < 256) {
                                        if (DaiHoiManager.gI().typeDHVT == (byte) 5 && player.inventory.gold >= 10000) {
                                            if (DaiHoiManager.gI().isAssignDHVT(player.id)) {
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký tham gia đại hội võ thuật rồi");
                                            } else {
                                                player.inventory.gold -= 10000;
                                                Service.getInstance().sendMoney(player);
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký thành công, nhớ có mặt tại đây trước giờ thi đấu");
                                                DaiHoiManager.gI().lstIDPlayers.add(player.id);
                                            }
                                        } else if (DaiHoiManager.gI().typeDHVT > (byte) 0 && DaiHoiManager.gI().typeDHVT < (byte) 5 && player.inventory.gem >= (int) (2 * DaiHoiManager.gI().typeDHVT)) {
                                            if (DaiHoiManager.gI().isAssignDHVT(player.id)) {
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký tham gia đại hội võ thuật rồi");
                                            } else {
                                                player.inventory.gem -= (int) (2 * DaiHoiManager.gI().typeDHVT);
                                                Service.getInstance().sendMoney(player);
                                                Service.getInstance().sendThongBao(player, "Bạn đã đăng ký thành công, nhớ có mặt tại đây trước giờ thi đấu");
                                                DaiHoiManager.gI().lstIDPlayers.add(player.id);
                                            }
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng ngọc để đăng ký thi đấu");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hiện tại đã đạt tới số lượng người đăng ký tối đa, xin hãy chờ đến giải sau");
                                    }

//                                    } else {
//                                        Service.getInstance().sendThongBao(player, "Bạn không đủ điều kiện tham gia giải này, hãy quay lại vào giải phù hợp");
//                                    }
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 129, player.location.x, 360);
                                    break;
                            }
                        }
                    } else if (this.mapId == 129) {
                        int goldchallenge = player.goldChallenge;
                        if (player.levelWoodChest == 0) {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        } else {
                            switch (select) {
                                case 0:
                                    if (InventoryServiceNew.gI().finditemWoodChest(player)) {
                                        if (player.inventory.gold >= goldchallenge) {
                                            MartialCongressService.gI().startChallenge(player);
                                            player.inventory.gold -= (goldchallenge);
                                            PlayerService.gI().sendInfoHpMpMoney(player);
                                            player.goldChallenge += 2000000;
                                        } else {
                                            Service.getInstance().sendThongBao(player, "Không đủ vàng, còn thiếu " + Util.numberToMoney(goldchallenge - player.inventory.gold) + " vàng");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Hãy mở rương báu vật trước");
                                    }
                                    break;
                                case 1:
                                    if (!player.receivedWoodChest) {
                                        if (InventoryServiceNew.gI().getCountEmptyBag(player) > 0) {
                                            Item it = ItemService.gI().createNewItem((short) 674);
                                            it.itemOptions.add(new Item.ItemOption(72, player.levelWoodChest));
                                            it.itemOptions.add(new Item.ItemOption(30, 0));
                                            it.createTime = System.currentTimeMillis();
                                            InventoryServiceNew.gI().addItemBag(player, it);
                                            InventoryServiceNew.gI().sendItemBags(player);

                                            player.receivedWoodChest = true;
                                            player.levelWoodChest = 0;
                                            Service.getInstance().sendThongBao(player, "Bạn nhận được rương gỗ");
                                        } else {
                                            this.npcChat(player, "Hành trang đã đầy");
                                        }
                                    } else {
                                        Service.getInstance().sendThongBao(player, "Mỗi ngày chỉ có thể nhận rương báu 1 lần");
                                    }
                                    break;
                                case 2:
                                    ChangeMapService.gI().changeMapNonSpaceship(player, 52, player.location.x, 336);
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc unkonw(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, 0,
                                "Éc éc Bạn muốn gì ở tôi :3?", "Đến Võ đài Unknow");
                    }
                    if (this.mapId == 112) {
                        this.createOtherMenu(player, 0,
                                "Bạn đang còn : " + player.pointPvp + " điểm PvP Point", "Về đảo Kame", "Đổi Cải trang sự kiên", "Top PVP");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    if (player.getSession().player.nPoint.power >= 10000000000L) {
                                        ChangeMapService.gI().changeMapBySpaceShip(player, 112, -1, 495);
                                        Service.gI().changeFlag(player, Util.nextInt(8));
                                    } else {
                                        this.npcChat(player, "Bạn cần 10 tỷ sức mạnh mới có thể vào");
                                    }
                                    break; // qua vo dai
                            }
                        }
                    }

                    if (this.mapId == 112) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 5, -1, 319);
                                    break; // ve dao kame
                                case 1:  // 
                                    this.createOtherMenu(player, 1,
                                            "Bạn có muốn đổi 500 điểm PVP lấy \n|6|Cải trang Goku SSJ3\n với chỉ số random từ 20 > 30% \n ", "Ok", "Không");
                                    // bat menu doi item
                                    break;

                                case 2:  // 
                                    Service.gI().showListTop(player, Manager.topPVP);
                                    // mo top pvp
                                    break;

                            }
                        }
                        if (player.iDMark.getIndexMenu() == 1) { // action doi item
                            switch (select) {
                                case 0: // trade
                                    if (player.pointPvp >= 500) {
                                        player.pointPvp -= 500;
                                        Item item = ItemService.gI().createNewItem((short) (1227)); // 49
                                        item.itemOptions.add(new Item.ItemOption(49, Util.nextInt(20, 30)));
                                        item.itemOptions.add(new Item.ItemOption(77, Util.nextInt(20, 30)));
                                        item.itemOptions.add(new Item.ItemOption(103, Util.nextInt(20, 30)));
                                        item.itemOptions.add(new Item.ItemOption(207, 0));
                                        item.itemOptions.add(new Item.ItemOption(33, 0));
//                                      
                                        InventoryServiceNew.gI().addItemBag(player, item);
                                        Service.gI().sendThongBao(player, "Chúc Mừng Bạn Đổi Cải Trang Thành Công !");
                                    } else {
                                        Service.gI().sendThongBao(player, "Không đủ điểm bạn còn " + (500 - player.pointPvp) + " Điểm nữa");
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc monaito(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        this.createOtherMenu(player, 0,
                                "Chào bạn tôi sẽ đưa bạn đến hành tinh Cereal?", "Đồng ý", "Từ chối");
                    }
                    if (this.mapId == 170) {
                        this.createOtherMenu(player, 0,
                                "Ta ở đây để đưa con về", "Về Làng Mori", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 7) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 170, -1, 264);
                                    break; // den hanh tinh cereal
                            }
                        }
                    }
                    if (this.mapId == 170) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    ChangeMapService.gI().changeMapBySpaceShip(player, 7, -1, 432);
                                    break; // quay ve

                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc granala(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {

            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {

                    if (this.mapId == 171) {
                        this.createOtherMenu(player, 0,
                                "Ngươi!\n Hãy cầm đủ 7 viên ngọc rồng \n Monaito đến đây gặp ta ta sẽ ban cho ngươi\n 1 điều ước ", "Gọi rồng", "Từ chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {

                    if (this.mapId == 171) {
                        if (player.iDMark.getIndexMenu() == 0) { // 
                            switch (select) {
                                case 0:
                                    this.npcChat(player, "Chức Năng Đang Được Update!");
                                    break; // goi rong

                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc vip_truongchimto(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 181) {
                        this.createOtherMenu(player, ConstNpc.BASE_MENU,
                                "Ngươi tìm ta có việc gì?",
                                "Thức Tỉnh");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 181) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    CombineServiceNew.gI().openTabCombine(player, CombineServiceNew.THUC_TINH_DT);
                                    break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.THUC_TINH_DT:
                                    switch (select) {
                                        case 0:
                                            CombineServiceNew.gI().thuctinhDT(player, 1);
                                            System.out.print("test");
                                            break;
                                        case 1:
                                            CombineServiceNew.gI().thuctinhDT(player, 10);
                                            break;
                                        case 2:
                                            CombineServiceNew.gI().thuctinhDT(player, 100);
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }
    public static Npc nangcap(int mapId, int status, int cx, int cy, int tempId, int avartar) {
        return new Npc(mapId, status, cx, cy, tempId, avartar) {
            @Override
            public void openBaseMenu(Player player) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        this.createOtherMenu(player, ConstNpc.MENU_EVENT,
                                "Ngươi tìm ta có việc gì?",
                                "Điểm\nSự Kiện","Top\nSự Kiện","Giao Bông","Đổi thưởng","Từ Chối");
                    }
                }
            }

            @Override
            public void confirmMenu(Player player, int select) {
                if (canOpenNpc(player)) {
                    if (this.mapId == 5) {
                        if (player.iDMark.isBaseMenu()) {
                            switch (select) {
                                case 0:
                                    Service.gI().sendThongBaoOK(player, "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
                                    break;
                                case 1:
                                    Service.gI().showListTop(player, Manager.topSK);
                                    break;
                                case 2:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người muốn giao bao nhiêu bông...",
                                        "100 bông", "1000 bông", "10000 bông");
                                break; 
                                case 3:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN, -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện đấy... ",
                                        "Đồng ý", "Từ chối");
                                break;
                            }
                        } else if (player.iDMark.getIndexMenu() == ConstNpc.MENU_START_COMBINE) {
                            switch (player.combineNew.typeCombine) {
                                case CombineServiceNew.DOI_DIEM_VIP:                               
                                    switch (select) {                                      
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        };
    }

    public static Npc createNPC(int mapId, int status, int cx, int cy, int tempId) {
        int avatar = Manager.NPC_TEMPLATES.get(tempId).avatar;
        try {
            switch (tempId) {
                case ConstNpc.UNKOWN:
                    return unkonw(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GHI_DANH:
                    return GhiDanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUNG_LINH_THU:
                    return trungLinhThu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.POTAGE:
                    return poTaGe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUY_LAO_KAME:
                    return quyLaoKame(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SONTINK:
                    return thuytink(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MR_POPO:
                    return popo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THO_DAI_CA:
                    return thodaika(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRUONG_LAO_GURU:
                    return truongLaoGuru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VUA_VEGETA:
                    return vuaVegeta(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.ONG_GOHAN:
                case ConstNpc.ONG_MOORI:
                case ConstNpc.ONG_PARAGUS:
                    return ongGohan_ongMoori_ongParagus(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA:
                    return bulmaQK(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DENDE:
                    return dende(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.APPULE:
                    return appule(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DR_DRIEF:
                    return drDrief(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CARGO:
                    return cargo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUI:
                    return cui(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SANTA:
                    return santa(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.URON:
                    return uron(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BA_HAT_MIT:
                    return baHatMit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NANGCAP:
                    return nangcap(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CC:
                    return gohanzombie(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LANVIP1:
                    return truongmylan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.SUKIEN:
                    return sukien(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.VODAI:
                    return Vodai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RUONG_DO:
                    return ruongDo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DAU_THAN:
                    return dauThan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CALICK:
                    return calick(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.JACO:
                    return jaco(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TRONG_TAI:
                    return TrongTai(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUONG_DE:
                    return thuongDe(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.Granola:
                    return granala(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GIUMA_DAU_BO:
                    return mavuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CUA_HANG_KY_GUI:
                    return kyGui(mapId, status, cx, cy, tempId, avatar);
                
                case ConstNpc.VADOS:
                    return vados(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_VU_TRU:
                    return thanVuTru(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.KIBIT:
                    return kibit(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.OSIN:
                    return osin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LY_TIEU_NUONG:
                    return npclytieunuong54(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.LINH_CANH:
                    return linhCanh(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DOC_NHAN:
                    return docNhan(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUA_TRUNG:
                    return quaTrung(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.QUOC_VUONG:
                    return quocVuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOJO:
                    return Gojo(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BUNMA_TL:
                    return bulmaTL(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_OMEGA:
                    return rongOmega(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.RONG_1S:
                case ConstNpc.RONG_2S:
                case ConstNpc.RONG_3S:
                case ConstNpc.RONG_4S:
                case ConstNpc.RONG_5S:
                case ConstNpc.RONG_6S:
                case ConstNpc.RONG_7S:
                    return rong1_to_7s(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NPC_64:
                    return npcThienSu64(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BILL:
                    return bill(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.WHIS:
                    return whis(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.BO_MONG:
                    return boMong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THAN_MEO_KARIN:
                    return karin(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ:
                    return gokuSSJ_1(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOKU_SSJ_:
                    return gokuSSJ_2(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DUONG_TANG:
                    return duongtank(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GOHAN_NHAT_NGUYET:
                    return gohannn(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.CHOPPER:
                    return chopper(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.FRANKY:
                    return franky(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.NAMI:
                    return nami(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TO_SU_KAIO:
                    return TosuKaio(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.MINUONG:
                    return miNuong(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.GAPTHU1:
                    return gapthu(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.DE:
                    return vihop(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.TAPION:
                    return Tapion(mapId, status, cx, cy, tempId, avatar);
                case ConstNpc.THUCTINH:
                    return vip_truongchimto(mapId, status, cx, cy, tempId, avatar);
                default:
                    return new Npc(mapId, status, cx, cy, tempId, avatar) {
                        @Override
                        public void openBaseMenu(Player player) {
                            if (canOpenNpc(player)) {
                                super.openBaseMenu(player);
                            }
                        }

                        @Override
                        public void confirmMenu(Player player, int select) {
                            if (canOpenNpc(player)) {
//                                ShopService.gI().openShopNormal(player, this, ConstNpc.SHOP_BUNMA_TL_0, 0, player.gender);
                            }
                        }
                    };

            }
        } catch (Exception e) {
            Logger.logException(NpcFactory.class,
                    e, "Lỗi load npc");
            return null;
        }
    }


    //girlbeo-mark
    public static void createNpcRongThieng() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.RONG_THIENG, -1) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.SHENRON_CONFIRM:
                        if (select == 0) {
                            SummonDragon.gI().confirmWish();
                        } else if (select == 1) {
                            SummonDragon.gI().reOpenShenronWishes(player);
                        }
                        break;
                    case ConstNpc.SHENRON_1_1:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_1 && select == SHENRON_1_STAR_WISHES_1.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_2, SHENRON_SAY, SHENRON_1_STAR_WISHES_2);
                            break;
                        }
                    case ConstNpc.SHENRON_1_2:
                        if (player.iDMark.getIndexMenu() == ConstNpc.SHENRON_1_2 && select == SHENRON_1_STAR_WISHES_2.length - 1) {
                            NpcService.gI().createMenuRongThieng(player, ConstNpc.SHENRON_1_1, SHENRON_SAY, SHENRON_1_STAR_WISHES_1);
                            break;
                        }
                    default:
                        SummonDragon.gI().showConfirmShenron(player, player.iDMark.getIndexMenu(), (byte) select);
                        break;
                }
            }
        };
    }

    public static void createNpcConMeo() {
        Npc npc = new Npc(-1, -1, -1, -1, ConstNpc.CON_MEO, 351) {
            @Override
            public void confirmMenu(Player player, int select) {
                switch (player.iDMark.getIndexMenu()) {
                    case ConstNpc.IGNORE_MENU:

                        break;
                    case ConstNpc.MAKE_MATCH_PVP: {
                        if (Maintenance.isRuning) {
                            break;
                        }
                        PVPService.gI().sendInvitePVP(player, (byte) select);
                        break;
                    }
//                        else {
//                            Service.gI().sendThongBao(player, "|5|VUI LÒNG KÍCH HOẠT TÀI KHOẢN TẠI\n|7|NROGOD.COM\n|5|ĐỂ MỞ KHÓA TÍNH NĂNG");
//                            break;
//                        }
                    case ConstNpc.MAKE_FRIEND:
                        if (select == 0) {
                            Object playerId = PLAYERID_OBJECT.get(player.id);
                            if (playerId != null) {
                                FriendAndEnemyService.gI().acceptMakeFriend(player,
                                        Integer.parseInt(String.valueOf(playerId)));
                            }
                        }
                        break;
                    case ConstNpc.REVENGE:
                        if (select == 0) {
                            PVPService.gI().acceptRevenge(player);
                        }
                        break;
                    case ConstNpc.TUTORIAL_SUMMON_DRAGON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        }
                        break;
                    case ConstNpc.SUMMON_SHENRON:
                        if (select == 0) {
                            NpcService.gI().createTutorial(player, -1, SummonDragon.SUMMON_SHENRON_TUTORIAL);
                        } else if (select == 1) {
                            SummonDragon.gI().summonShenron(player);
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM1105:
                        if (select == 0) {
                            IntrinsicService.gI().sattd(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().satnm(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().setxd(player);
                        }
                        break;
                    case ConstNpc.INFO:
                        switch (select) {
//                            case 2:
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.CHECK_DH, 12639,
//                                        "|7|- •⊹٭DragonBall Kamui٭⊹• -\nTITLE SERVICE\n"
//                                        + "|2|Danh Hiệu Top Event"
//                                        + "\n|5|Đại Thần (SD +15%, HP,KI +20%) : " + (player.timedh1 > 0 ? Util.msToTime(player.timedh1) : player.usedh1 == false ? "Có thể nhận qua event đua top" : "Đang Bật")
//                                        + "\nTrùm Cuối (SD +15%, HP,KI +20%) : " + (player.timedh2 > 0 ? Util.msToTime(player.timedh2) : player.usedh2 == false ? "Có thể nhận qua event đua top" : "Đang Bật")
//                                        + "\nBất Bại (SD +15%, HP,KI +20%) : " + (player.timedh3 > 0 ? Util.msToTime(player.timedh3) : player.usedh3 == false ? "Có thể nhận qua event đua top" : "Đang Bật")
//                                        + "\n|2|Danh Hiệu Event"
//                                        + "\n|5|Buôn Ve Chai (5% SD,HP,KI): " + (player.dhtang1 == true ? "Đang Bật" : player.titlett == false ? "Có thể nhận được khi tham gia event" : "Đang Tắt")
//                                        + "\nFan Cứng (5% SD,HP,KI) : " + (player.dhtang2 == true ? "Đang Bật" : player.titlett == false ? "Có thể nhận được khi tham gia event" : "Đang Tắt")
//                                        + "\nSức Mạnh (5% SD,HP,KI) : " + (player.dhhanhtinh == true ? "Đang Bật" : player.titlett == false ? "Có thể nhận được khi đạt 180 Tỉ" : "Đang Tắt")
//                                        + "\nPhong Ba (Cập nhật) : " + (player.dhphongba == true ? "Đang Bật" : player.titlett == false ? "Có thể nhận được khi tham gia event" : "Đang Tắt")
//                                        + "\nTuổi Thơ (Cập nhật) : " + (player.dhtuoitho == true ? "Đang Bật" : player.titlett == false ? "Có thể nhận được khi tham gia event" : "Đang Tắt"),
//                                        ("Đại Thần\n" + (player.usedh1 == true ? "'ON'" : "'OFF'")),
//                                        ("Trùm Cuối\n" + (player.usedh2 == true ? "'ON'" : "'OFF'")),
//                                        ("Bất Bại\n" + (player.usedh3 == true ? "'ON'" : "'OFF'")),
//                                        ("Ve Chai\n" + (player.dhtang1 == true ? "'ON'" : "'OFF'")),
//                                        ("Fan Cứng\n" + (player.dhtang2 == true ? "'ON'" : "'OFF'")),
//                                        ("Sức Mạnh\n" + (player.dhhanhtinh == true ? "'ON'" : "'OFF'")),
//                                        ("Phong Ba\n" + (player.dhphongba == true ? "'ON'" : "'OFF'")),
//                                        ("Tuổi Thơ\n" + (player.dhtuoitho == true ? "'ON'" : "'OFF'")),
//                                        "Làm Mới");
//                                break;
                            case 1:
                                if (player.pet != null) {
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.IGNORE_MENU, 12639,
                                            "|7|[ Info Player Pet : " + player.pet.getNameThuctinh(player.pet.thuctinh) + player.pet.name.substring(1) + " ]\n"
                                            + "Trạng thái : " + (player.pet.status == 1 ? "Bảo Vệ" : player.pet.status == 2 ? "Tấn Công" : player.pet.status == 3 ? "Về Nhà" : player.pet.status == 4 ? "Hợp Thể" : "Đi Theo")
                                            + "\n|2|Chỉ Số Tổng"
                                            + "\n|5|HP : " + Util.format(player.pet.nPoint.hp) + "/" + Util.format(player.pet.nPoint.hpMax)
                                            + "\nKI : " + Util.format(player.pet.nPoint.mp) + "/" + Util.format(player.pet.nPoint.mpMax)
                                            + "\nSĐ : " + Util.format(player.pet.nPoint.dame)
                                            + "\nDEF : " + Util.format(player.pet.nPoint.def)
                                            + "\nCRIT : " + Util.format(player.pet.nPoint.crit)
                                            + "\n|2|Chỉ Số Gốc"
                                            + "\n|5|HPG : " + Util.format(player.pet.nPoint.hpg)
                                            + "\nKIG : " + Util.format(player.pet.nPoint.mpg)
                                            + "\nSĐG : " + Util.format(player.pet.nPoint.dameg)
                                            + "\nDEFG : " + Util.format(player.pet.nPoint.defg)
                                            + "\nCRITG : " + Util.format(player.pet.nPoint.critg) + "%", "Đóng");
                                } else {
                                    Service.gI().sendThongBao(player, "Bạn chưa có đệ");
                                }
                                break;
                            case 0:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_PLAYER, 12639, "|7|[ Info Player : " + player.name + " ]"
                                    //    + "\nTrạng thái tài khoản : " + (player.getSession().actived == 1 ? " Đã mở thành viên" : " Chưa mở thành viên")
                                      //  + (player.vip == 1 ? " [VIP]" : player.vip == 2 ? " [VIP2]" : player.vip == 3 ? " [VIP3]" : player.vip == 4 ? " [SVIP]" : "")
                                        + "\n|2|Chỉ Số Tổng"
                                        + "\n|5|HP : " + Util.format(player.nPoint.hp) + "/" + Util.format(player.nPoint.hpMax)
                                        + "\nKI : " + Util.format(player.nPoint.mp) + "/" + Util.format(player.nPoint.mpMax)
                                        + "\nSĐ : " + Util.format(player.nPoint.dame)
                                        + "\nDEF : " + Util.format(player.nPoint.def)
                                        + "\nCRIT : " + player.nPoint.crit + "%"
                                        + "\n|2|Chỉ Số Gốc"
                                        + "\n|5|HPG : " + player.nPoint.hpg
                                        + "\nKIG : " + player.nPoint.mpg
                                        + "\nSĐG : " + player.nPoint.dameg
                                        + "\nDEFG : " + player.nPoint.defg
                                        + "\nCRITG : " + player.nPoint.critg + "%"
                                        + "\n|2|Nhiệm vụ hiện tại: " + player.playerTask.sideTask.getName() + " ("
                                        + player.playerTask.sideTask.getLevel() + ")"
                                        + "\n|5|Hiện tại đã hoàn thành: " + player.playerTask.sideTask.count + "/"
                                        + player.playerTask.sideTask.maxCount + " ("
                                        + player.playerTask.sideTask.getPercentProcess() + "%)\nSố nhiệm vụ còn lại trong ngày: "
                                        + player.playerTask.sideTask.leftTask + "/" + ConstTask.MAX_SIDE_TASK,
                                        "Nhập\nGiftcode", "Đổi\nMật Khẩu", "Auto\nBán Vàng", "Auto\nChỉ Số");
                                break;
                            

                                }
                        
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2000:
                    case ConstNpc.MENU_OPTION_USE_ITEM2001:
                    case ConstNpc.MENU_OPTION_USE_ITEM2002:
                        try {
                            ItemService.gI().OpenSKH(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM2003:
                    case ConstNpc.MENU_OPTION_USE_ITEM2004:
                    case ConstNpc.MENU_OPTION_USE_ITEM2005:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.MENU_OPTION_USE_ITEM736:
                        try {
                            ItemService.gI().OpenDHD(player, player.iDMark.getIndexMenu(), select);
                        } catch (Exception e) {
                            Logger.error("Lỗi mở hộp quà");
                        }
                        break;
                    case ConstNpc.INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().showAllIntrinsic(player);
                        } else if (select == 1) {
                            IntrinsicService.gI().showConfirmOpen(player);
                        } else if (select == 2) {
                            IntrinsicService.gI().showConfirmOpenVip(player);
                        }
                        break;
                    case ConstNpc.HOISINH:
                        if (select == 0) {
                            if (player.inventory.ruby >= 1000) {
                                player.inventory.ruby -= 1000;
                                Service.gI().sendMoney(player);
                                Service.gI().hsChar(player, player.nPoint.hpMax, player.nPoint.mpMax);
                                player.achievement.plusCount(13);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc");
                            }
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC:
                        if (select == 0) {
                            IntrinsicService.gI().open(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_OPEN_INTRINSIC_VIP:
                        if (select == 0) {
                            IntrinsicService.gI().openVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_LEAVE_CLAN:
                        if (select == 0) {
                            ClanService.gI().leaveClan(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_NHUONG_PC:
                        if (select == 0) {
                            ClanService.gI().phongPc(player, (int) PLAYERID_OBJECT.get(player.id));
                        }
                        break;
                    case ConstNpc.BAN_PLAYER:
                        if (select == 0) {
//                            PlayerService.gI().banPlayer((Player) PLAYERID_OBJECT.get(player.id));
                         //   Service.gI().sendThongBao(player, "Ban người chơi " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                        }
                        break;

                    case ConstNpc.BUFF_PET:
                        if (select == 0) {
                            Player pl = (Player) PLAYERID_OBJECT.get(player.id);
                            if (pl.pet == null) {
                                PetService.gI().createNormalPet(pl);
                                Service.gI().sendThongBao(player, "Phát đệ tử cho " + ((Player) PLAYERID_OBJECT.get(player.id)).name + " thành công");
                            }
                        }
                        break;
                    case ConstNpc.UP_TOP_ITEM:
                        if (select == 0) {
                            if (player.inventory.gem >= 50 && player.iDMark.getIdItemUpTop() != -1) {
                                ItemKyGui it = ShopKyGuiService.gI().getItemBuy(player.iDMark.getIdItemUpTop());
                                if (it == null || it.isBuy) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không tồn tại hoặc đã được bán");
                                    return;
                                }
                                if (it.player_sell != player.id) {
                                    Service.gI().sendThongBao(player, "Vật phẩm không thuộc quyền sở hữu");
                                    ShopKyGuiService.gI().openShopKyGui(player);
                                    return;
                                }
                                player.inventory.gem -= 50;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Thành công");
                                it.isUpTop += 1;
                                ShopKyGuiService.gI().openShopKyGui(player);
                            } else {
                                Service.gI().sendThongBao(player, "Bạn không đủ hồng ngọc");
                                player.iDMark.setIdItemUpTop(-1);
                            }
                        }
                        break;
                    case ConstNpc.PETSERVICE:
                        switch (select) {
                            case 0:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.MUA_DE, 348, "|7|Pet Service" + "\n" + "|1|" + "\n"
                                        + "Chào bạn : " + player.name + " | ID: (" + player.id + ") | " + "Map : " + player.zone.map.mapName + "\n"
                                        + "Số Dư Khả Dụng : " + player.getSession().vnd + " VNĐ" + "\n"
                                        + "Bạn có chăc muốn đổi đệ không?" + "\n" + "\n"
                                        + "|7|* Chỉ Cần Mua 1 Đệ Tử Bất Kỳ, Hệ Thống Sẽ \nTự Động Kích Hoạt Tài Khoản Cho Bạn!!\nCHÚ Ý : Đệ nhận miễn phí không tự động Active Account",
                                        "Đệ Thiên Tử\n30.000VNĐ", "Đệ Black Goku\n40.000VNĐ", "Đệ Kaido\n50.000VNĐ", "Đóng");
                                break;
                        }
                        break;
                    case ConstNpc.MUA_DE:
                        switch (select) {
                            case 0:
                                this.createOtherMenu(player, ConstNpc.CONFIRM_MUA_DE_THIENTU, "Bạn có chắc muốn mua đệ Thiên Tử không?", "Đồng ý", "Không");
                                break;
                            case 1:
                                this.createOtherMenu(player, ConstNpc.CONFIRM_MUA_DE_BLACK, "Bạn có chắc muốn mua đệ Black Goku không?", "Đồng ý", "Không");
                                break;
                            case 2:
                                this.createOtherMenu(player, ConstNpc.CONFIRM_MUA_DE_KAIDO, "Bạn có chắc muốn mua đệ Kaido không?", "Đồng ý", "Không");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_MUA_DE_THIENTU:
                        switch (select) {
                            case 0:
                                if (player.getSession().vnd < 30000) {
                                    Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                    return;
                                }
                                if (player.pet == null) {
                                    Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                    return;
                                }
                                player.getSession().vnd -= 30000;
                                PetService.gI().createThienTuPetVip(player, true, player.pet.gender);
                                break;
                            case 1:
                                this.npcChat(player, "Cảm ơn đã quý khách đã ghé shop");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_MUA_DE_BLACK:
                        switch (select) {
                            case 0:
                                if (player.getSession().vnd < 40000) {
                                    Service.gI().sendThongBao(player, "Bạn không có đủ 30k coin");
                                    return;
                                }
                                if (player.pet == null) {
                                    Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                    return;
                                }

                                player.getSession().vnd -= 40000;
                                PetService.gI().createBlackGokuPetVip(player, true, player.pet.gender);
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_MUA_DE_KAIDO:
                        switch (select) {
                            case 0:
                                if (player.getSession().vnd < 50000) {
                                    Service.gI().sendThongBao(player, "Bạn không có đủ 50k coin");
                                    return;
                                }
                                if (player.pet == null) {
                                    Service.gI().sendThongBao(player, "Bạn cần phải có đệ tử thường trước");
                                    return;
                                }

                                player.getSession().vnd -= 50000;
                                PetService.gI().createKaidoPetVip(player, true, player.pet.gender);
                                break;
                        }
                        break;
                    case ConstNpc.CLCK:
                        switch (select) {
                            case 0:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.PETSERVICE, 348, "|7|Pet Service" + "\n" + "|1|" + "\n"
                                        + "Chào bạn : " + player.name + " | ID: (" + player.id + ") | " + "Map : " + player.zone.map.mapName + "\n"
                                        + "Số Dư Khả Dụng : " + player.getSession().vnd + " VNĐ" + "\n"
                                        + "Chọn dịch vụ?" + "\n",
                                        "MUA\nĐỆ TỬ", "Đóng");
                                break;
                            case 1:
                                if (player.inventory.ruby >= 2000000) {
                                    Service.gI().sendThongBao(player, "Quá giới hạn nhận Hồng Ngọc");
                                    break;
                                }
                                player.inventory.ruby = 2000000;
                                Service.gI().sendMoney(player);
                                Service.gI().sendThongBao(player, "Nhận thành công 2 triệu Hồng Ngọc");
                                break;
                            case 2:
                                Item thoivang = null;
                                try {
                                    thoivang = InventoryServiceNew.gI().findItemBag(player, 457);
                                } catch (Exception e) {
                                }
                                if (thoivang == null || thoivang.quantity < 200000) {
                                    Item tvnhan = ItemService.gI().createNewItem((short) 457);
                                    tvnhan.quantity = 200000;
                                    InventoryServiceNew.gI().addItemBag(player, tvnhan);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "Nhận thành công 200k Thỏi Vàng");
                                    break;
                                } else {
                                    thoivang = InventoryServiceNew.gI().findItemBag(player, 457);
                                    if (thoivang.quantity >= 200000) {
                                        Service.gI().sendThongBao(player, "Quá giới hạn nhận Thỏi Vàng");
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                Input.gI().createFormGiftCode(player);
                                break;
                            case 4:
                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_PLAYER, 348,
                                        "|7|Nhân vật: " + player.name + "\n"
                                        + "[Trạng thái tài khoản : " + (player.getSession().actived == true ? " Đã Mở Thành Viên]" : " Chưa Mở Thành Viên]\n")
                                        + "\n|1|Chỉ số hiện tại"
                                        + "\n|2|Hp: " + player.nPoint.hp + "/" + player.nPoint.hpMax
                                        + "\n|2|Ki: " + player.nPoint.mp + "/" + player.nPoint.mpMax
                                        + "\n|2|Sức đánh: " + player.nPoint.dame + "\n"
                                        + "\n|1|Chỉ số gốc"
                                        + "\n|2|Hp gốc: " + player.nPoint.hpg
                                        + "\n|2|Ki gốc: " + player.nPoint.mpg
                                        + "\n|2|Sức đánh gốc: " + player.nPoint.dameg, "Nhập giftcode", "Đổi mật khẩu", "Đóng");
                                break;
                        }
                        break;
                    case ConstNpc.MENU_ADMIN:
                        switch (select) {
                            case 0:
                                this.createMenuConMeo(player, ConstNpc.ADMIN1, 348, "|7| Admin Ngọc Rồng UNITY\b|2| vantanz|4| Người Đang Chơi: " + Client.gI().getPlayers().size() + "\n" + "|8|Current thread: " + (Thread.activeCount() - ServerManager.gI().threadMap) + "\n",
                                        "Ngọc Rồng", "Đệ Tử", "Bảo Trì", "Tìm Kiếm\nPlayer", "Đóng");
                                break;
                            case 1:
                                this.createOtherMenu(player, ConstNpc.CALL_BOSS,
                                        "Chọn Boss?", "Full Cụm\nANDROID", "BLACK", "BROLY", "Cụm\nCell",
                                        "Cụm\nDoanh trại", "DOREMON", "FIDE", "FIDE\nBlack", "Cụm\nGINYU", "Cụm\nNAPPA", "Gắp Thú");
                                break;
                            case 2:
                                this.createOtherMenu(player, ConstNpc.BUFFITEM,
                                        "Buff Item", "Buff Item", "Item Option", "Buff Skh");
                                break;
                            case 3:
                                MaQuaTangManager.gI().checkInfomationGiftCode(player);
                                break;
                            case 4:
                                Input.gI().createFormNapCoin(player);
                                break;
                        }
                        break;
                    case ConstNpc.BUFFITEM:
                        switch (select) {
                            case 0:
                                Input.gI().createFormSenditem(player);
                                break;
                            case 1:
                                Input.gI().createFormSenditem1(player);
                                break;
                            case 2:
                                Input.gI().createFormSenditem2(player);
                                break;
                        }
                        break;
                    case ConstNpc.ADMIN1:
                        switch (select) {
                            case 0:
                                for (int i = 14; i <= 20; i++) {
                                    Item item = ItemService.gI().createNewItem((short) i);
                                    InventoryServiceNew.gI().addItemBag(player, item);
                                }
                                InventoryServiceNew.gI().sendItemBags(player);
                                break;
                            case 1:
                                if (player.pet == null) {
                                    PetService.gI().createNormalPet(player);
                                } else {
                                    if (player.pet.typePet == 1) {
                                        PetService.gI().changePicPet(player);
                                    } else if (player.pet.typePet == 2) {
                                        PetService.gI().changeMabuPet(player);
                                    }
                                    PetService.gI().changeBerusPet(player);
                                }
                                break;
                            case 2:
                                if (player.isAdmin()) {
                                    System.out.println(player.name);
                                    Maintenance.gI().start(15);
                                    System.out.println(player.name);
                                }
                                break;
                            case 3:
                                Input.gI().createFormFindPlayer(player);
                                break;
                        }
                        break;
                    case ConstNpc.CALL_BOSS:
                        switch (select) {
                            case 0:
                                BossManager.gI().createBoss(BossID.ANDROID_13);
                                BossManager.gI().createBoss(BossID.ANDROID_14);
                                BossManager.gI().createBoss(BossID.ANDROID_15);
                                BossManager.gI().createBoss(BossID.APK1920);
                                BossManager.gI().createBoss(BossID.PICPOCKING);
                                break;
                            case 1:
                                BossManager.gI().createBoss(BossID.BLACK);
                                break;
                            case 2:
                                BossManager.gI().createBoss(BossID.BROLY);
                                break;
                            case 3:
                                BossManager.gI().createBoss(BossID.SIEU_BO_HUNG);
                                BossManager.gI().createBoss(BossID.XEN_BO_HUNG);
//                                BossManager.gI().createBoss(BossID.XEN_CON);
                                break;
                            case 4:
                                Service.getInstance().sendThongBao(player, "Không có boss");
                                break;
                            case 5:
                                BossManager.gI().createBoss(BossID.CHAIEN);
                                BossManager.gI().createBoss(BossID.XEKO);
                                BossManager.gI().createBoss(BossID.XUKA);
                                BossManager.gI().createBoss(BossID.NOBITA);
                                BossManager.gI().createBoss(BossID.DORAEMON);
                                break;
                            case 6:
                                BossManager.gI().createBoss(BossID.FIDE);
                                break;
                            case 7:
                                BossManager.gI().createBoss(BossID.FIDE_ROBOT);
                                BossManager.gI().createBoss(BossID.VUA_COLD);
                                break;
                            case 8:
                                BossManager.gI().createBoss(BossID.TDST);
                                break;
                            case 9:
                                BossManager.gI().createBoss(BossID.KUKUMDDRAMBO);
                                break;
                        }
                        break;

                    case ConstNpc.QUY_DOI_HN:
                        switch (select) {
                            case 0:
                                int coin = 10000;
                                int tv = 30;
                                int dans = 3;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 1:
                                coin = 20000;
                                tv = 60;
                                dans = 6;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 2:
                                coin = 30000;
                                tv = 90;
                                dans = 9;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 3:
                                coin = 50000;
                                tv = 160;
                                dans = 16;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 4:
                                coin = 100000;
                                tv = 330;
                                dans = 33;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 5:
                                coin = 200000;
                                tv = 670;
                                dans = 67;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 6:
                                coin = 300000;
                                tv = 1050;
                                dans = 105;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 7:
                                coin = 500000;
                                tv = 1800;
                                dans = 180;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                            case 8:
                                coin = 1000000;
                                tv = 3700;
                                dans = 370;
                                if (player.getSession().coinBar >= coin) {
                                    PlayerDAO.subcoinBar(player, coin);
                                    Item thoiVang = ItemService.gI().createNewItem((short) 457, tv);// x3
                                    Item dns = ItemService.gI().createNewItem((short) 674, dans);// x3
                                    InventoryServiceNew.gI().addItemBag(player, thoiVang);
                                    InventoryServiceNew.gI().addItemBag(player, dns);
                                    InventoryServiceNew.gI().sendItemBags(player);
                                    Service.gI().sendThongBao(player, "bạn nhận được " + tv
                                            + " " + thoiVang.template.name + " và " + dans
                                            + " " + dns.template.name);
                                } else {
                                    Service.gI().sendThongBao(player, "Số tiền của bạn là " + player.getSession().coinBar + " không đủ để quy "
                                            + " đổi ");
                                }
                                break;
                        }
                        break;
                    case ConstNpc.menutd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().settaiyoken(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgenki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setkamejoko(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menunm:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodki(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setgoddam(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setsummon(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.menuxd:
                        switch (select) {
                            case 0:
                                try {
                                    ItemService.gI().setgodgalick(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 1:
                                try {
                                    ItemService.gI().setmonkey(player);
                                } catch (Exception e) {
                                }
                                break;
                            case 2:
                                try {
                                    ItemService.gI().setgodhp(player);
                                } catch (Exception e) {
                                }
                                break;
                        }
                        break;

                    case ConstNpc.CONFIRM_DISSOLUTION_CLAN:
                        switch (select) {
                            case 0:
                                Clan clan = player.clan;
                                clan.deleteDB(clan.id);
                                Manager.CLANS.remove(clan);
                                player.clan = null;
                                player.clanMember = null;
                                ClanService.gI().sendMyClan(player);
                                ClanService.gI().sendClanId(player);
                                Service.gI().sendThongBao(player, "Đã giải tán bang hội.");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_ACTIVE:
                        switch (select) {
                            case 0:
                                if (player.getSession().goldBar >= 20) {
                                    player.getSession().actived = true;
                                    if (PlayerDAO.subGoldBar(player, 20)) {
                                        Service.gI().sendThongBao(player, "Đã mở thành viên thành công!");
                                        break;
                                    } else {
                                        this.npcChat(player, "Lỗi vui lòng báo admin...");
                                    }
                                }
                                Service.gI().sendThongBao(player, "Bạn không có vàng\n Vui lòng NROSEOBI.ME để nạp thỏi vàng");
                                break;
                        }
                        break;
                    case ConstNpc.CONFIRM_REMOVE_ALL_ITEM_LUCKY_ROUND:
                        if (select == 0) {
                            for (int i = 0; i < player.inventory.itemsBoxCrackBall.size(); i++) {
                                player.inventory.itemsBoxCrackBall.set(i, ItemService.gI().createItemNull());
                            }
                            player.inventory.itemsBoxCrackBall.clear();
                            Service.gI().sendThongBao(player, "Đã xóa hết vật phẩm trong rương");
                        }
                        break;
                    case ConstNpc.MENU_FIND_PLAYER:
                        Player p = (Player) PLAYERID_OBJECT.get(player.id);
                        if (p != null) {
                            switch (select) {
                                case 0:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMapYardrat(player, p.zone, p.location.x, p.location.y);
                                    }
                                    break;
                                case 1:
                                    if (p.zone != null) {
                                        ChangeMapService.gI().changeMap(p, player.zone, player.location.x, player.location.y);
                                    }
                                    break;
                                case 2:
                                    Input.gI().createFormChangeName(player, p);
                                    break;
                                case 3:
                                    String[] selects = new String[]{"Đồng ý", "Hủy"};
                                    NpcService.gI().createMenuConMeo(player, ConstNpc.BAN_PLAYER, -1,
                                            "Bạn có chắc chắn muốn ban " + p.name, selects, p);
                                    break;
                                case 4:
                                    Service.gI().sendThongBao(player, "Kik người chơi " + p.name + " thành công");
                                    Client.gI().getPlayers().remove(p);
                                    Client.gI().kickSession(p.getSession());
                                    break;
                            }
                        }
                        break;
                    case ConstNpc.MENU_EVENT:
                        switch (select) {
                            case 0:
                                Service.gI().sendThongBaoOK(player, "Điểm sự kiện: " + player.inventory.event + " ngon ngon...");
                                break;
                            case 1:
                                Service.gI().showListTop(player, Manager.topSK);
                                break;
                            case 2:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.MENU_GIAO_BONG, -1, "Người muốn giao bao nhiêu bông...",
//                                        "100 bông", "1000 bông", "10000 bông");
                                break;
                            case 3:
                                Service.gI().sendThongBao(player, "Sự kiện đã kết thúc...");
//                                NpcService.gI().createMenuConMeo(player, ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN, -1, "Con có thực sự muốn đổi thưởng?\nPhải giao cho ta 3000 điểm sự kiện đấy... ",
//                                        "Đồng ý", "Từ chối");
                                break;

                        }
                        break;
                    case ConstNpc.MENU_GIAO_BONG:
                        ItemService.gI().giaobong(player, (int) Util.tinhLuyThua(10, select + 2));
                        break;
                    case ConstNpc.CONFIRM_DOI_THUONG_SU_KIEN:
                        if (select == 0) {
                            ItemService.gI().openBoxVip(player);
                        }
                        break;
                    case ConstNpc.CONFIRM_TELE_NAMEC:
                        if (select == 0) {
                            NgocRongNamecService.gI().teleportToNrNamec(player);
                            player.inventory.subGemAndRuby(50);
                            Service.gI().sendMoney(player);
                        }
                        break;
                }
            }
        };
    }

}
