<div id="stick" style="background: #FFAF4D;">
<title>Minh Buff Không Lỏ</title>
<style>
	#snowflakeContainer{position:absolute;left:0px;top:0px;}
	.snowflake{padding-left:15px;font-size:14px;line-height:24px;position:fixed;color:#ebebeb;user-select:none;z-index:1000;-moz-user-select:none;-ms-user-select:none;-khtml-user-select:none;-webkit-user-select:none;-webkit-touch-callout:none;}
	.snowflake:hover {cursor:default}
</style>
<div id='snowflakeContainer'>
<p class='snowflake'>☄️</p>
</div>
<script style='text/javascript'>
	//<![CDATA[
	var requestAnimationFrame=window.requestAnimationFrame||window.mozRequestAnimationFrame||window.webkitRequestAnimationFrame||window.msRequestAnimationFrame;var transforms=["transform","msTransform","webkitTransform","mozTransform","oTransform"];var transformProperty=getSupportedPropertyName(transforms);var snowflakes=[];var browserWidth;var browserHeight;var numberOfSnowflakes=50;var resetPosition=false;function setup(){window.addEventListener("DOMContentLoaded",generateSnowflakes,false);window.addEventListener("resize",setResetFlag,false)}setup();function getSupportedPropertyName(b){for(var a=0;a<b.length;a++){if(typeof document.body.style[b[a]]!="undefined"){return b[a]}}return null}function Snowflake(b,a,d,e,c){this.element=b;this.radius=a;this.speed=d;this.xPos=e;this.yPos=c;this.counter=0;this.sign=Math.random()<0.5?1:-1;this.element.style.opacity=0.5+Math.random();this.element.style.fontSize=4+Math.random()*30+"px"}Snowflake.prototype.update=function(){this.counter+=this.speed/5000;this.xPos+=this.sign*this.speed*Math.cos(this.counter)/40;this.yPos+=Math.sin(this.counter)/40+this.speed/30;setTranslate3DTransform(this.element,Math.round(this.xPos),Math.round(this.yPos));if(this.yPos>browserHeight){this.yPos=-50}};function setTranslate3DTransform(a,c,b){var d="translate3d("+c+"px, "+b+"px, 0)";a.style[transformProperty]=d}function generateSnowflakes(){var b=document.querySelector(".snowflake");var h=b.parentNode;browserWidth=document.documentElement.clientWidth;browserHeight=document.documentElement.clientHeight;for(var d=0;d<numberOfSnowflakes;d++){var j=b.cloneNode(true);h.appendChild(j);var e=getPosition(50,browserWidth);var a=getPosition(50,browserHeight);var c=5+Math.random()*40;var g=4+Math.random()*10;var f=new Snowflake(j,g,c,e,a);snowflakes.push(f)}h.removeChild(b);moveSnowflakes()}function moveSnowflakes(){for(var b=0;b<snowflakes.length;b++){var a=snowflakes[b];a.update()}if(resetPosition){browserWidth=document.documentElement.clientWidth;browserHeight=document.documentElement.clientHeight;for(var b=0;b<snowflakes.length;b++){var a=snowflakes[b];a.xPos=getPosition(50,browserWidth);a.yPos=getPosition(50,browserHeight)}resetPosition=false}requestAnimationFrame(moveSnowflakes)}function getPosition(b,a){return Math.round(-1*b+Math.random()*(a+2*b))}function setResetFlag(a){resetPosition=true};
	//]]>
</script>

<?php
include"../connect.php";
if(isset($_POST['start'])){
$name= $_POST['name'];
$sql = "SELECT * FROM player WHERE name ='$name'";
                            $result = mysqli_query($conn, $sql);
                            if($row = mysqli_fetch_assoc($result)){
$account_id = $row['account_id'];                           
if($_POST['trang_thai'] == 100){
$thong_tin="<h2 style='color:red;'>vui lòng chọn trạng thái</h2>";
}else{                      
                            
if($_POST['trang_thai'] == -1){

   $update_query = "UPDATE account SET ban = 1 WHERE id = '$account_id'";
                                            mysqli_query($conn, $update_query);                         
$thong_tin="<h2 style='color:red;'>khoá thành công tk $name</h2>";
}else{
if($_POST['trang_thai'] == 0){
  $update_query = "UPDATE account SET ban = 0 WHERE id = '$account_id'";
                                            mysqli_query($conn, $update_query);                         
$thong_tin="<h2 style='color:red;'>mở khoá thành công tk $name</h2>";


}else{
if($_POST['trang_thai'] == 1){
  $update_query = "UPDATE account SET active = 1 WHERE id = '$account_id'";
                                            mysqli_query($conn, $update_query);                         
$thong_tin="<h2 style='color:red;'>mở thành viên thành công tk $name</h2>";
}else{
if($_POST['trang_thai'] == 2){
  $update_query = "UPDATE account SET is_admin = 1 WHERE id = '$account_id'";
                                            mysqli_query($conn, $update_query);                         
$thong_tin="<h2 style='color:red;'>mở admin thành công tk $name</h2>";
}
}
}
}
}
}else{
$thong_tin="<h2 style='color:red;'>tên nhân vật không tồn tại</h2>";
}
}else{
$thong_tin="";
}
?>
<?php echo$thong_tin;?>
<h1 style="color: #008000;">Vui lòng thoát game để tránh lỗi</h1>
<form method="POST" action="" id="myform">
<labe>nhập tên nhân vật</labe>
<input name="name" id="serial" type="text" autocomplete="off" class="form-control form-input-main">
<label><b>trạng thái:</b></label>
						<select class="form-control mt-1" name="trang_thai" required="" style="border-radius: 7px; box-shadow: 0px 0px 5px red">
							<option value="100">Chọn trạng thái</option>
							<option value="-1">Khoá</option>
							<option value="0">Mở khoá</option>
							<option value="1">Mở thành viên</option>			
							<option value="2">Buff lệnh</option>			
							
						</select></br>
<button name="start" type="submit" class="w-50 rounded-3 btn btn-primary btn-sm">Xác nhận</button>
</form>
