<?php

	error_reporting( E_ALL );
	ini_set("display_errors", 0);
	
  
    $file_path = "uploads/";

     
    $file_path = $file_path.basename($_FILES["uploaded_file"]["name"]);
	
	
	
    if(move_uploaded_file($_FILES["uploaded_file"]["tmp_name"], $file_path)) {
        echo "success";
    } else{
        echo "에러!!!!!!!!!!!!!";
    }
	
#	error_log ("hello log", 3, "/home/proj/debug.log");
 ?>