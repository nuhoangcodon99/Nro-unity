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
    <title>Trang Chủ Chính Thức - Ngọc Rồng Light</title>
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
                <div class="col"> <a href="dien-dan" style="color: white">Quay lại diễn đàn</a> </div>
            </div>
        </div>
<div class="container pt-5 pb-5">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <h4>ĐỔI MẬT KHẨU</h4>
                    <?php
                    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
                        $stmt = $conn->prepare("SELECT password FROM account WHERE username=?");
                        $stmt->bind_param("s", $_username);
                        $stmt->execute();
                        $result = $stmt->get_result();
                        $row = $result->fetch_assoc();

                        $password = $_POST['password'] ?? '';
                        $new_password = $_POST['new_password'] ?? '';
                        $new_passwordxacnhan = $_POST['new_password_confirmation'] ?? '';

                        if (!empty($password) && !empty($new_password) && !empty($new_passwordxacnhan)) {
                            // Kiểm tra mật khẩu hiện tại hợp lệ
                            if ($password !== $row['password']) {
                                echo "<div class='alert alert-danger'>Sai mật khẩu hiện tại</div>";
                            } elseif ($new_password === $password) {
                                echo "<div class='alert alert-danger'>Mật khẩu mới không được giống mật khẩu hiện tại</div>";
                            } elseif ($new_password !== $new_passwordxacnhan) {
                                echo "<div class='alert alert-danger'>Mật khẩu mới không giống nhau</div>";
                            } else {
                                // Cập nhật mật khẩu mới vào database và thông báo
                                $stmt = $conn->prepare("UPDATE account SET password=? WHERE username=?");
                                $stmt->bind_param("ss", $new_password, $_username);

                                if ($stmt->execute()) {
                                    echo '<div class="text-danger pb-2 font-weight-bold">Đổi mật khẩu mới thành công!</div>';
                                    $row['password'] = $new_password;
                                } else {
                                    echo '<div class="text-danger pb-2 font-weight-bold">Lỗi tạo mật khẩu mới!</div>';
                                }
                            }
                        } else {
                            echo '<div class="text-danger pb-2 font-weight-bold">Vui lòng điền đầy đủ thông tin!</div>';
                        }
                    }
                    ?>
                    <form method="POST">
                        <div class="mb-3">
                            <label class="font-weight-bold">Mật Khẩu Hiện Tại:</label>
                            <input type="password" class="form-control" name="password" id="password"
                                placeholder="Mật khẩu hiện tại" required autocomplete="password">
                        </div>
                        <div class="mb-3">
                            <label class="font-weight-bold">Mật Khẩu Mới:</label>
                            <input type="password" class="form-control" name="new_password" id="new_password"
                                placeholder="Mật khẩu mới" required autocomplete="new_password">
                        </div>
                        <div class="mb-3">
                            <label class="font-weight-bold">Nhập Lại Mật Khẩu Mới:</label>
                            <input type="password" class="form-control" name="new_password_confirmation"
                                id="new_password_confirmation" placeholder="Xác nhận mật khẩu mới" required
                                autocomplete="new_password_confirmation">
                        </div>

                        <button class="btn btn-main form-control" type="submit">Thực hiện</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="border-secondary border-top"></div>
        <div class="container pt-4 pb-4 text-white">
            <div class="row">
                <div class="col">
                    <div class="text-center">
                        <div style="font-size: 13px" class="text-dark"> <small>Desgin By Nguyễn Đức Kiên</small><br>
                            <small>2023©NGỌC RỒNG LIGHT</small>
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