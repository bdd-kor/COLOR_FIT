<?php

$con = mysqli_connect('211.247.98.249','cf','colorfit','goods') or die("connection failed");
mysqli_set_charset($con, "utf8");

$cate=isset($_POST['category']) ? $_POST['category'] : '';
$pc=isset($_POST['g_pccode']) ? $_POST['g_pccode'] : '';

if($cate != "" && $pc != ""){
  $query = "select * from tablegirl where category = '$cate' and g_pccode = '$pc'";
  }
elseif($cate != "" && $pc ==""){
  $query = "select * from tablegirl where category = '$cate'";
  }
elseif($cate == "" && $pc !=""){
  $query = "select * from tablegirl where g_pccode = '$pc'";
  }
else{
  $query = "select * from tablegirl";
  }

$res = mysqli_query($con, $query);
$arr = array();	//빈 배열생성

while($row = mysqli_fetch_array($res)){
	array_push($arr,
		array('category'=>$row['1'],'g_name'=>$row['2'],'price'=>$row['3'],
				'imgurl'=>$row['4'],'siteurl'=>$row['5'])
	);
}
echo json_encode(array("Cloth"=>$arr), JSON_UNESCAPED_UNICODE);

mysqli_close($con);	
?>