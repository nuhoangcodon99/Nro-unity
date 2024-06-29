<?php
include_once 'set.php';
include_once 'connect.php';
include('head.php');
if ($_login == null) {
    header("location:dang-nhap");
}
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Trang Chủ Chính Thức - Ngọc Rồng Alockns</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="description"
        content="Website chính thức của Chú Bé Rồng Online – Game Bay Vien Ngoc Rong Mobile nhập vai trực tuyến trên máy tính và điện thoại về Game 7 Viên Ngọc Rồng hấp dẫn nhất hiện nay!">
    <meta name="keywords"
        content="Chú Bé Rồng Online,ngoc rong mobile, game ngoc rong, game 7 vien ngoc rong, game bay vien ngoc rong">
    <meta name="twitter:card" content="summary">
    <meta name="twitter:title"
        content="Website chính thức của Chú Bé Rồng Online – Game Bay Vien Ngoc Rong Mobile nhập vai trực tuyến trên máy tính và điện thoại về Game 7 Viên Ngọc Rồng hấp dẫn nhất hiện nay!">
    <meta name="twitter:description"
        content="Website chính thức của Chú Bé Rồng Online – Game Bay Vien Ngoc Rong Mobile nhập vai trực tuyến trên máy tính và điện thoại về Game 7 Viên Ngọc Rồng hấp dẫn nhất hiện nay!">
    <meta name="twitter:image" content="image/logo.png">
    <meta name="twitter:image:width" content="200">
    <meta name="twitter:image:height" content="200">
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <script src="assets/jquery/jquery.min.js"></script>
    <script src="assets/notify/notify.js"></script>
    <link rel="icon" href="image/icon.png?v=99">
    <link href="assets//main.css" rel="stylesheet">
</head>

<body>
    <div class="container color-forum pt-1 pb-1">
        <div class="row">
            <div class="col">
                <a href="dien-dan" style="color: white">Quay lại diễn đàn</a>
            </div>
        </div>
    </div>
    <div class="container pt-5 pb-5">
        <div class="row">
            <div class="col-lg-6 offset-lg-3">
                <h4>ĐỔI THỎI VÀNG - TỰ ĐỘNG</h4>
                <form method="POST">
                    <label for="vnd_amount">Số Dư Cần Đổi:</label>
                    <select class="form-control form-control-alternative" name="vnd_amount" id="vnd_amount" required>
                        <option value="">Chọn Số Dư</option>
                        <option value="10000">10,000 VNĐ</option>
                        <option value="20000">20,000 VNĐ</option>
                        <option value="30000">30,000 VNĐ</option>
                        <option value="50000">50,000 VNĐ</option>
                        <option value="100000">100,000 VNĐ</option>
                        <option value="200000">200,000 VNĐ</option>
                        <option value="300000">300,000 VNĐ</option>
                        <option value="500000">500,000 VNĐ</option>
                        <option value="1000000">1,000,000 VNĐ</option>
                    </select>
                    <label>Số thỏi vàng sẽ nhận: <span class="font-weight-bold" id="gold">0</span> thỏi</label>
                    <br>
                    <button class="btn btn-main form-control" name="doithoivang" type="submit">Thực hiện</button>
                </form>
                <?php
                if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                    if (isset($_POST['doithoivang'])) {
                        $account_id = $_SESSION['id'];
                        $vnd_amount = $_POST['vnd_amount'];

                        // Mệnh giá và số lượng thỏi vàng tương ứng
                        $options = array(
                            array("amount" => 10000, "quantity" => 25),
                            array("amount" => 20000, "quantity" => 60),
                            array("amount" => 30000, "quantity" => 90),
                            array("amount" => 50000, "quantity" => 160),
                            array("amount" => 100000, "quantity" => 360),
                            array("amount" => 200000, "quantity" => 670),
                            array("amount" => 500000, "quantity" => 1700),
                            array("amount" => 1000000, "quantity" => 3500),
                        );

                        $gold_quantity = 0;
                        foreach ($options as $option) {
                            if ($option["amount"] == $vnd_amount) {
                                $gold_quantity = $option["quantity"];
                                break;
                            }
                        }

                        if ($gold_quantity > 0) {
                            // Lấy dữ liệu tài khoản từ cơ sở dữ liệu
                            $account_query = "SELECT * FROM account WHERE id = $account_id LIMIT 1";
                            $account_result = mysqli_query($conn, $account_query);

                            if (mysqli_num_rows($account_result) > 0) {
                                $account_data = mysqli_fetch_assoc($account_result);
                                $current_vnd = $account_data['vnd'];

                                if ($current_vnd >= $vnd_amount) {
                                    // Cập nhật số dư VND
                                    $new_vnd = $current_vnd - $vnd_amount;
                                    $update_query = "UPDATE account SET vnd = $new_vnd WHERE id = $account_id";
                                    mysqli_query($conn, $update_query);

                                    // Cập nhật hành trang
                                    $player_query = "SELECT * FROM player WHERE account_id = $account_id LIMIT 1";
                                    $player_result = mysqli_query($conn, $player_query);

                                    if (mysqli_num_rows($player_result) > 0) {
                                        $player_data = mysqli_fetch_assoc($player_result);
                                        $_items_bag = $player_data['items_bag'];
                                        $replacement = "[457,$gold_quantity,\\\"[\\\\\\\\\\\"[73,1]\\\\\\\\\\\"]\\\"";
                                        $_items_bag = preg_replace('/\[-1,0,\\\"\[\]\\\"/', $replacement, $_items_bag, 1, $count);

                                        if ($count > 0) {
                                            $escaped_items_bag = mysqli_real_escape_string($conn, $_items_bag);
                                            $update_query = "UPDATE player SET items_bag = '$escaped_items_bag' WHERE account_id = $account_id";
                                            mysqli_query($conn, $update_query);

                                            echo '<div class="text-danger pb-2 font-weight-bold">';
                                            echo "Đổi quà thành công! Nhận được $gold_quantity thỏi vàng.";
                                            echo '</div>';
                                        } else {
                                            echo '<div class="text-danger pb-2 font-weight-bold">';
                                            echo 'Hành trang đầy, không thể nhận quà.';
                                            echo '</div>';
                                        }
                                    } else {
                                        echo '<div class="text-danger pb-2 font-weight-bold">';
                                        echo 'Không tìm thấy dữ liệu người chơi.';
                                        echo '</div>';
                                    }
                                } else {
                                    echo '<div class="text-danger pb-2 font-weight-bold">';
                                    echo 'Số dư VND không đủ.';
                                    echo '</div>';
                                }
                            } else {
                                echo '<div class="text-danger pb-2 font-weight-bold">';
                                echo 'Không tìm thấy dữ liệu tài khoản.';
                                echo '</div>';
                            }
                        } else {
                            echo '<div class="text-danger pb-2 font-weight-bold">';
                            echo 'Không tìm thấy món quà phù hợp.';
                            echo '</div>';
                        }
                    }
                }
                ?>
                <script>
                    document.getElementById('vnd_amount').addEventListener('change', function () {
                        var vndAmount = parseInt(this.value);
                        var goldQuantity = 0;
                        var options = [
                            { amount: 10000, quantity: 25 },
                            { amount: 20000, quantity: 60 },
                            { amount: 30000, quantity: 90 },
                            { amount: 50000, quantity: 160 },
                            { amount: 100000, quantity: 360 },
                            { amount: 200000, quantity: 670 },
                            { amount: 500000, quantity: 1700 },
                            { amount: 1000000, quantity: 3500 }
                        ];

                        for (var i = 0; i < options.length; i++) {
                            if (options[i].amount === vndAmount) {
                                goldQuantity = options[i].quantity;
                                break;
                            }
                        }

                        document.getElementById('gold').textContent = goldQuantity;
                    });
                </script>
            </div>
        </div>
    </div>
    <div class="border-secondary border-top"></div>
    <div class="container pt-4 pb-4 text-white">
        <div class="row">
            <div class="col">
                <div class="text-center">
                    <div style="font-size: 13px" class="text-dark">
                                    <small>IP:
                                        <?php echo $_IP; ?>
                                    </small><br>
                                    <small>Desgin By Nguyễn Đức Kiên</small><br>
                                    <small>2023© Ngọc Rồng Alockns</small>
                                </div>
                            </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="assets/main.js"></script>
</body><!-- Bootstrap core JavaScript -->

</html>