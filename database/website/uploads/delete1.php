<?php
// Kết nối đến cơ sở dữ liệu MySQL
include "../connect.php";

$sql = "SHOW TABLES";
$result = $conn->query($sql);

if ($result === false) {
    die("Lỗi truy vấn: ");
}

// Lưu trữ danh sách các bảng vào một mảng
$tables = array();
while ($row = $result->fetch_assoc()) {
    $tables[] = $row['Tables_in_' . $dbname_sv];
}

// Duyệt qua mảng từ cuối cùng đến phần tử đầu tiên và xóa các bảng
for ($i = count($tables) - 1; $i >= 0; $i--) {
    $tableName = $tables[$i];
    $sql = "DROP TABLE $tableName";

    $conn->query("SET FOREIGN_KEY_CHECKS = 0");
    
    if ($conn->query($sql) === false) {
        echo "Không thể xóa bảng $tableName: ";
    } else {
        echo "Đã xóa bảng $tableName thành công.<br>";
    }

    $conn->query("SET FOREIGN_KEY_CHECKS = 1");
}


?>
                      
                                            