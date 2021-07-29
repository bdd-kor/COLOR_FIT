<?php

$con = mysqli_connect('3.37.62.27','jj','gg','productdb') or die("connection failed");
//mysql_query("SET NAMES utf8");
//mysql_query("set session character_set_client=euckr");
//mysql_query("set session character_set_connection=euckr");
//mysql_query("set session character_set_results=euckr");
mysqli_set_charset($con, "utf8");


// 66걸즈

$pc = "'".$_POST['xxxx']."'";
$query = "SELECT * FROM testgirl";
//$query = "SELECT * FROM testgirl WHERE g_name LIKE '%".$pc."%'";
//$query = "SELECT * FROM testgirl WHERE g_name LIKE '%"."반팔"."%'";

//$query = "SELECT * FROM testgirl WHERE g_name LIKE ".$pc;
//$query = "SELECT * FROM testgirl WHERE category2 = ".$pc;

$res = mysqli_query($con, $query);
$arr = array();	//빈 배열생성

while($row = mysqli_fetch_array($res)){
	array_push($arr,
		array('g_code'=>$row[1],'g_name'=>$row['4'],'price'=>$row['5'],'imgurl'=>$row['6'],'siteurl'=>$row['7'])
//					'goods_title'=>$row['4'],'imgurl'=>$row['5'],'price'=>$row['6'],'color'=>$row['7'],
//					'category'=>$row['8'])
	);
}
echo json_encode(array("Cloth"=>$arr), JSON_UNESCAPED_UNICODE);



// 무신사 
/*
$pc = "'".$_POST['personalcolor22']."'";
$query = "SELECT * FROM goods WHERE goods.color = ".$pc;



//$query = "SELECT * FROM goods WHERE goods.color = '갈색'";


$res = mysqli_query($con, $query);
$arr = array();	//빈 배열생성


while($row = mysqli_fetch_array($res)){
	array_push($arr,
		array('goods_id'=>$row['0'],'sex'=>$row['1'],'brand'=>$row['2'],'goods_code'=>$row['3'],
					'goods_title'=>$row['4'],'imgurl'=>$row['5'],'price'=>$row['6'],'color'=>$row['7'],
					'category'=>$row['8'])
	);
}
echo json_encode(array("Cloth"=>$arr), JSON_UNESCAPED_UNICODE);

*/
//////////////




/*
if($res){
	while($row = mysqli_fetch_array($res)){
		array_push($arr,array('상품명'=>$row['goods_title'], '가격'=>$row['price']);
	}
	header('Content-Type: aplication/json; charset='utf8');
	$jsondata=json_encode(array("productdb"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

}else{
	$jsondata=json_encode("sql문에러", mysqli_error($con));
	exit();
}

echo $jsondata;

echo 'sd';
*/


/*
// 한 행씩 전부 출력
while($row = mysqli_fetch_array($res)){
	echo $row['goods_id'], " ",$row['sex'], " ",$row['brand'], " ", $row['goods_code'], " ", $row['goods_title']," ",$row['imgurl'], " ",$row['price'], " ",$row['color'], " ",$row['category'], "<br>";
}
*/





/*
//작동테스트
$sql = "SELECT VERSION()";
$result = mysqli_query($con, $sql);
$row = mysqli_fetch_array($result);
print_r($row["VERSION()"]);
echo  '<br><br>집에가고싶다23434';
*/

/*
//db생성
$sql = "												// 쿼리문 작성. create, insert,,,
	CREATE DATABASE sqlDB
	( userID		CHAR (8) NOT NULL PRIMARY KEY,
	  name		VARCHAR(9) NOT NULL,
	  birth		INT NOT NULL
	)
";
$res = mysqli_query($con, $sql);			// mysqli_query : 쿼리문 db에전달
if($res){		//성공,실패에러 출력
	echo "생성 성공";
}
else {
	echo "생성 실패, 원인 :".mysqli_error($con);
	exit();
}
*/

/*
//select문***********************************
$sql = "SELECT * FROM goods WHERE goods.color = '흰색'";
$res = mysqli_query($con, $sql);

if($res){
	echo mysqli_num_rows($res), "건 조회<br><br>";
}
else {
	echo "생성 실패, 원인 :".mysqli_error($con);
	exit();
}

// 한 행씩 전부 출력
while($row = mysqli_fetch_array($res)){
	echo $row['brand'], " ", $row['sex'], " ", $row['goods_title'], " ",$row['color'], "<br>";
}
*/

//컬러별로?
/*
$sql = "SELECT * FROM goods WHERE goods.color = '흰색'";
$res = mysqli_query($con, $sql);

if($res){
	echo mysqli_num_rows($res), "건 조회<br><br>";
}
else {
	echo "생성 실패, 원인 :".mysqli_error($con);
	exit();
}
// 한 행씩 전부 출력
while($row = mysqli_fetch_array($res)){
	echo $row['goods_id'], " ",$row['sex'], " ",$row['brand'], " ", $row['goods_code'], " ", $row['goods_title']," ",$row['imgurl'], " ",$row['price'], " ",$row['color'], " ",$row['category'], "<br>";
}
*/


/*
//다른방식
$res = mysqli_query($con, "select * from test");

$result = array();


while($row = mysqli_fetch_array($res)){
	array_push($result,
		array('id'=>$row[0],'personalcolor'=>$row[2]
		));
}

echo json_encode(array("result"=>$result));
*/


mysqli_close($con);	
?>






















