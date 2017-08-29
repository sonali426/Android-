<?php	
  	$DB_USER='root';        
  	$DB_PASS='root';        
  	$DB_HOST='localhost';     
  	$DB_NAME='applications';
  	$mysqli = new mysqli($DB_HOST, $DB_USER, $DB_PASS, $DB_NAME);
  	/* check connection */
  	if (mysqli_connect_errno()) {
     		printf("Connect failed: %s\n", mysqli_connect_error());
     		exit();
  	}
  	
  	
  ?>
