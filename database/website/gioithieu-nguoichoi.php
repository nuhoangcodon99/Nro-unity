<?php
include_once 'set.php';
include_once 'connect.php';
include_once 'head.php';
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
    <base href="">
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
    <link href="assets/main.css" rel="stylesheet">
</head>

<body>
    <div class="container color-forum pt-1 pb-1">
        <div class="row">
            <div class="col"> <a href="dien-dan" style="color: white">Quay lại diễn đàn</a> </div>
        </div>
    </div>
    <div class="container pt-5 pb-5">
        <div class="row">
            <div class="col-lg-6 offset-lg-3">
                <h4>GIỚI THIỆU NGƯỜI CHƠI MỚI</h4><br>
                <b class="text-danger">Cư dân đã giới thiệu được :
                    <?php echo $_gioithieu; ?> Người
                </b><br><br>
                <b> Link Giới Thiệu:</b>
                <b style="color:red">
                    <?php echo $_domain ?>/dang-ky.php?ref=
                    <?php echo $_SESSION['id'] ?>
                </b>
                <br>
                <br>
                <?php if ($_gioithieu > 0) { ?>
                    <span class="text-danger"><strong>
                            Số Điểm Giới Thiệu Là
                            <?php echo $_gioithieu; ?> Người Bạn Nhận Được :
                        </strong></span><br>
                    #<b><span style="color:red">
                            <?php echo ($_gioithieu == 1) ? '5,000 VNĐ' :
                                (($_gioithieu == 2) ? '10,000 VNĐ' :
                                    '15,000 VNĐ'); ?>
                        </span></b><br>
                    <?php
                }
                ?>
                <br>
                <br>
                <b class="text text-danger">Phổ Biến Luật Sự Kiện: </b><br>
                <b>- Đây là Link riêng của mỗi cư dân Nro Alockns
                    <br>
                    - Người chơi phải đăng ký thành công mới được tính điểm
                    <br>
                    - Chỉ tính điểm với người chơi mới, tối đa là 3 người chơi mới
                    <br>
                    <br>
                    <span style="color:red"><strong>Quan Trọng : <span style="color:212529">
                        </strong></span>
                    </span>
                    <br>
                    <b>- Các cư dân lưu ý không <span style="color:red">spam</span> để tránh làm phiền người chơi
                        khác
                        <br>
                        - Mỗi tài khoản chỉ đạt được <span style="color:red">3 Điểm</span> thôi và phần quà sẽ gửi
                        vào <span style="color:red">hành trang</span> của cư dân khi
                        đạt đủ số điểm tích luỹ
                        <br>
                        - Khi cư dân đạt đủ <span style="color:red">3 Điểm</span> tích luỹ thì sẽ hiển thị nút <span
                            style="color:red">Đổi Quà</span>
                        <br>
                        <br>
                        <?php
                        // Các giá trị mốc quà và điểm tương ứng
                        $moc_qua = [
                            1 => [
                                'diem' => 1,
                                'gia_tri' => 5000
                            ],
                            2 => [
                                'diem' => 2,
                                'gia_tri' => 10000
                            ],
                            3 => [
                                'diem' => 3,
                                'gia_tri' => 15000
                            ]
                        ];

                        // Kiểm tra xem người chơi có điểm tích luỹ hay không
                        if ($_gioithieu > 0) {
                            // Kiểm tra xem điểm tích luỹ nằm trong mốc quà nào
                            if (array_key_exists($_gioithieu, $moc_qua)) {
                                $moc = $moc_qua[$_gioithieu];
                                $diem_moc = $moc['diem'];
                                $gia_tri_moc = $moc['gia_tri'];

                                // Kiểm tra xem người chơi đã nhấn nút "Đổi Quà" hay chưa
                                if (isset($_POST['doi_qua'])) {
                                    // Thêm giá trị của mốc quà vào cột "vnd"
                                    $_coin += $gia_tri_moc;

                                    // Thực hiện trừ điểm tích luỹ tương ứng với mốc đó
                                    $_gioithieu -= $diem_moc;

                                    // Cập nhật cơ sở dữ liệu với giá trị mới của cột "vnd" và "gioithieu"
                                    $sql = "UPDATE account SET coin = $_coin, gioithieu = $_gioithieu WHERE id = $_SESSION[id]";
                                    if (mysqli_query($conn, $sql)) {
                                        echo "Đổi quà thành công!";
                                    } else {
                                        echo "Lỗi cập nhật cơ sở dữ liệu: " . mysqli_error($conn);
                                    }
                                }
                            }
                        }
                        ?>


                        <!-- Hiển thị nút "Đổi Quà" nếu người chơi có điểm tích luỹ -->
                        <?php if ($_gioithieu > 0): ?>
                            <form method="POST">
                                <input class="btn btn-main btn-sm" type="submit" name="doi_qua" value="Đổi Quà">
                            </form>
                        <?php endif; ?>
            </div>
        </div>
    </div>
    <div class=" border-secondary border-top">
    </div>
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