<?php

	error_reporting( E_ALL );
	ini_set("display_errors", 1);
	
	#sleep(1);
	
	#shell_exec("python test.py");
	#exec("cd /home/www/ && python3 test.py");	
	#$command = shell_exec('python test.py');
	
	#system('python check.py');
	#$output = exec("sudo python3 check.py");  
	#$output = exec("cd /C:/Apache24/htdocs/www/ && python3 check.py");
	
	#Window에 APM을 설치하여 실행하 때
	exec("C:\Users\cf\AppData\Local\Programs\Python\Python36\python.exe C:\Apache24\htdocs\www\check.py");

	sleep(1);

	#$output = array();
	#exec("C:\Users\cf\AppData\Local\Programs\Python\Python36\python.exe C:\Apache24\htdocs\www\check.py", $output);
	#echo $output;


	$fp = fopen("uploads/pc.txt", "r");
	$fr = fread($fp, 10); #10byte
	fclose($fp);
	
	echo $fr;
	
?>