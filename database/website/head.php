<body>
    <div class="container" style="border-radius: 15px; background: #ffaf4c; padding: 0px">
        <div class="container" style="background-color: #e67e22; border-radius: 15px 15px 0px 0px">
            <div class="row bg pb-3 pt-2">
                <div class="col">
                    <div class="text-center mb-2">
                        <a href="../dien-dan"><img class="rounded" src="../image/logo.png" id="logo"></a>
                    </div>
                    <div class="text-center pt-2">
                        <div style="display: inline-block;">
                            <a href="../download/Nro Alockns.apk"> <img class="icon-download" src="../image/android.png">
                            </a><br>
                            <small class="text-dark">2.1.4</small>
                        </div>
                        <div style="display: inline-block;">
                            <a href="../download/Mod Vũ Đăng V225.rar"><img class="icon-download" src="../image/pc.png">
                            </a><br>
                            <small class="text-dark">Mod Vu Dang</small>
                        </div>
                        <div style="display: inline-block;">
                            <a href="../download/Nro Alockns.ipa"><img class="icon-download" src="../image/ip.png"></a><br>
                            <small class="text-dark">Mod IOS</small>
                        </div>
                        <div>
                            <img height="12" src="image/12.png" style="vertical-align: middle;">
                            <small style="font-size: 10px" id="hour3">Dành cho người chơi trên 12 tuổi. Chơi quá 180 phút mỗi ngày sẽ hại sức khỏe.</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <?php if ($_login == null) { ?>
            <div class="container color-main2 pb-2">
                <div class="text-center">
                    <div class="row">
                        <div class="col pr-0"> <a href="../dang-nhap" class="btn p-1 btn-header">Đăng
                                nhập</a> </div>
                        <div class="col"> <a href="../dang-ky" class="btn p-1 btn-header-active">Đăng
                                ký</a> </div>
                    </div>
                </div>
            </div>
        <?php } else {
            if ($_admin == 1) { // Kiểm tra quyền truy cập
                ?>
                <div class="container color-main2 pb-2">
                    <div class="text-center">
                        <div class="row">
                            <div class="col pr-0"> <a href="../dien-dan" class="btn p-1 btn-header">Thảo luận</a> </div>
                            <div class="col pr-0"> <a href="../dien-dan" class="btn p-1 btn-header">Báo lỗi</a> </div>
                            <div class="col"> <a href="../dien-dan" class="btn p-1 btn-header">Góp ý</a> </div>
                        </div>
                    </div>
                </div>
                <div class="container color-main pt-3 pb-4">
                    <div class="text-center">
                        <?php
                        $query = "SELECT player.name, player.gender, account.admin FROM player LEFT JOIN account ON player.account_id = account.id";
                        $result = mysqli_query($conn, $query);
                        $prev_name = "";

                        while ($row = mysqli_fetch_assoc($result)) {
                            // Check if the username is submitted via POST method
                            if (isset($_POST['username'])) {
                                $_username = $_POST['username'];
                            }
                            $sql = "SELECT player.name, player.gender, account.admin, account.tichdiem FROM player INNER JOIN account ON account.id = player.account_id WHERE account.username='$_username'";
                            $result = mysqli_query($conn, $sql);
                            $row = mysqli_fetch_assoc($result);

                            // Hiển thị ảnh đại diện và tên người dùng
                            if (isset($row['gender'])) {
                                $gender = $row['gender'];
                                $tichdiem = $row['tichdiem'];
                                $avatar_url = "";
                                if ($gender == 1) {
                                    $avatar_url = "../image/avatar1.png";
                                } elseif ($gender == 2) {
                                    $avatar_url = "../image/avatar2.png";
                                } else {
                                    $avatar_url = "../image/avatar0.png";
                                }
                                $color = "";
                                if ($tichdiem >= 500) {
                                    $danh_hieu = "(Chuyên Gia)";
                                    $color = "#800000"; // sets color to red
                                } elseif ($tichdiem >= 300) {
                                    $danh_hieu = "(Hỏi Đáp)";
                                    $color = "#A0522D"; // sets color to yellow
                                } elseif ($tichdiem >= 200) {
                                    $danh_hieu = "(Người Bắt Chuyện)";
                                    $color = "#6A5ACD";
                                } else {
                                    $danh_hieu = "";
                                    $color = "";
                                }

                                if (isset($row['admin']) && $row['admin'] == 1) {
                                    $name_str = '<span class="text-danger font-weight-bold">' . $row['name'] . '</span><br>';
                                    $name_str .= '<span class="text-danger pt-1 mb-0">(Admin)</span>';
                                } else {
                                    $name_str = '<p class="text-main font-weight-bold pt-1 mb-0">' . $row['name'] . '</p>';
                                    if ($danh_hieu !== "") {
                                        $name_str .= '<div style="font-size: 9px; padding-top: 5px"><span style="color:' . $color . ' !important">' . $danh_hieu . '</span></div>';
                                    }
                                }

                                echo '<div><img src="' . $avatar_url . '" alt="Avatar" style="width: 50px"></div>';
                                echo $name_str;
                            }
                        }
                        ?>
                        <p class="text-main font-weight-bold pt-1 mb-0">
                        </p>
                        <p class="pt-0">Số dư:
                            <?php echo number_format($_coin, 0, ',') ?> VNĐ
                        </p>
                    </div>
                    <div class="text-center mb-2">
                        <a class="btn btn-main btn-sm" href="admin/index">ADMIN VP</a>
                        <a class="btn btn-main btn-sm" href="../nap-so-du">Nạp số dư</a>
                        <a class="btn btn-main btn-sm" href="../nap-momo">Nạp Momo</a>
                        <a class="btn btn-main btn-sm" href="../pass2">Mật khẩu cấp 2</a>
                        <a class="btn btn-main btn-sm" href="../doi-thoi-vang">Đổi thỏi vàng</a>
                    </div>
                    <div class="text-center">
                        <a class="btn btn-main btn-sm" href="../doi-mat-khau">Đổi mật khẩu</a>
                        <a class="btn btn-main btn-sm" href="../logout">Thoát</a>
                    </div>
                </div>
            <?php } else { ?>
                <div class="container color-main2 pb-2">
                    <div class="text-center">
                        <div class="row">
                            <div class="col pr-0"> <a href="../dien-dan" class="btn p-1 btn-header">Thảo luận</a> </div>
                            <div class="col pr-0"> <a href="../dien-dan" class="btn p-1 btn-header">Báo lỗi</a> </div>
                            <div class="col"> <a href="../dien-dan" class="btn p-1 btn-header">Góp ý</a> </div>
                        </div>
                    </div>
                </div>
                <div class="container color-main pt-3 pb-4">
                    <div class="text-center">
                        <?php
                        $query = "SELECT player.name, player.gender, account.admin FROM player LEFT JOIN account ON player.account_id = account.id";
                        $result = mysqli_query($conn, $query);
                        $prev_name = "";

                        while ($row = mysqli_fetch_assoc($result)) {
                            // Check if the username is submitted via POST method
                            if (isset($_POST['username'])) {
                                $_username = $_POST['username'];
                            }
                            $sql = "SELECT player.name, player.gender, account.admin, account.tichdiem FROM player INNER JOIN account ON account.id = player.account_id WHERE account.username='$_username'";
                            $result = mysqli_query($conn, $sql);
                            $row = mysqli_fetch_assoc($result);

                            // Hiển thị ảnh đại diện và tên người dùng
                            if (isset($row['gender'])) {
                                $gender = $row['gender'];
                                $tichdiem = $row['tichdiem'];
                                $avatar_url = "";
                                if ($gender == 1) {
                                    $avatar_url = "../image/avatar1.png";
                                } elseif ($gender == 2) {
                                    $avatar_url = "../image/avatar2.png";
                                } else {
                                    $avatar_url = "../image/avatar0.png";
                                }
                                $color = "";
                                if ($tichdiem >= 500) {
                                    $danh_hieu = "(Chuyên Gia)";
                                    $color = "#800000"; // sets color to red
                                } elseif ($tichdiem >= 300) {
                                    $danh_hieu = "(Hỏi Đáp)";
                                    $color = "#A0522D"; // sets color to yellow
                                } elseif ($tichdiem >= 200) {
                                    $danh_hieu = "(Người Bắt Chuyện)";
                                    $color = "#6A5ACD";
                                } else {
                                    $danh_hieu = "";
                                    $color = "";
                                }

                                if (isset($row['admin']) && $row['admin'] == 1) {
                                    $name_str = '<span class="text-danger font-weight-bold">' . $row['name'] . '</span><br>';
                                    $name_str .= '<span class="text-danger pt-1 mb-0">(Admin)</span>';
                                } else {
                                    $name_str = '<p class="text-main font-weight-bold pt-1 mb-0">' . $row['name'] . '</p>';
                                    if ($danh_hieu !== "") {
                                        $name_str .= '<div style="font-size: 9px; padding-top: 5px"><span style="color:' . $color . ' !important">' . $danh_hieu . '</span></div>';
                                    }
                                }

                                echo '<div><img src="' . $avatar_url . '" alt="Avatar" style="width: 50px"></div>';
                                echo $name_str;
                            }
                        }
                        ?>
                        <p class="text-main font-weight-bold pt-1 mb-0">
                        </p>
                        <p class="pt-0">Số dư:
                            <?php echo number_format($_coin, 0, ',') ?> VNĐ
                        </p>
                    </div>
                    <div class="text-center mb-2">
                        <a class="btn btn-main btn-sm" href="../nap-so-du">Nạp số dư</a>
                        <a class="btn btn-main btn-sm" href="../nap-momo">Nạp Momo</a>
                        <a class="btn btn-main btn-sm" href="../pass2">Mật khẩu cấp 2</a>
                        <a class="btn btn-main btn-sm" href="../doi-thoi-vang">Đổi thỏi vàng</a>
                    </div>
                    <div class="text-center">
                        <a class="btn btn-main btn-sm" href="../doi-mat-khau">Đổi mật khẩu</a>
                        <a class="btn btn-main btn-sm" href="../logout">Thoát</a>
                    </div>
                </div>
                <?php
            }
        }
        ?>
        