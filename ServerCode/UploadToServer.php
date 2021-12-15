<?php

	error_reporting( E_ALL );
	ini_set("display_errors", 1);
	
  
    $file_path = "uploads/";


    $file_path = $file_path.basename($_FILES["uploaded_file"]["name"]);
	
	
	
    if(move_uploaded_file($_FILES["uploaded_file"]["tmp_name"], $file_path)) {
        echo "success";
    } else{
        echo "error!!";
    }

	exec("check.py");


	
#	error_log ("hello log", 3, "/home/proj/debug.log");
# https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=kkyy3402&logNo=221284921252
 ?>