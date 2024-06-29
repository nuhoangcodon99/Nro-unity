<?php
$ip_sv = "localhost";
$dbname_sv = "unity";
$user_sv = "root";
$pass_sv = "hgsisuagh811@62..281##@711";

//GMT +7
date_default_timezone_set('Asia/Ho_Chi_Minh');

// Create connection
$conn = new mysqli($ip_sv, $user_sv, $pass_sv, $dbname_sv);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>