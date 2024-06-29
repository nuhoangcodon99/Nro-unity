<div id="stick" style="background: #FFAF4D;">
<title>Minh Buff Không Lỏ</title>
<?php
include"../connect.php";
if(isset($_POST['start'])){
$name = $_POST['name'];
   $sql = "SELECT JSON_EXTRACT(player.data_task, '$[0]') AS data_task1 FROM player WHERE name ='$name'";
                            $result = mysqli_query($conn, $sql);
                            $row = mysqli_fetch_assoc($result);
                            $cc1 = $row['data_task1'];
                            $tong = $cc1 +1;
                            $data_task = "[$tong,0,0]";
                            if($cc1 == 30 ){}else{
        $update_query = "UPDATE player SET data_task = '".Addslashes($data_task)."' WHERE name = '$name'";
mysqli_query($conn, $update_query);
}
}
?>
<h1 style="color: #008000;">Vui lòng thoát game để tránh lỗi</h1>
<form method="POST" action="" id="myform">
<labe>nhập tên nhân vật</labe>
<input name="name" id="serial" type="text" autocomplete="off" class="form-control form-input-main">
</br>
<button name="start" type="submit" class="w-50 rounded-3 btn btn-primary btn-sm">Xác nhận</button>
</form>

