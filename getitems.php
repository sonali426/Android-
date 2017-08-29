<?php
  	include("connection.php");
  	$mysqli->query("SET NAMES 'utf8'");
  	$sql="SELECT title, url FROM analytics";
  	$result=$mysqli->query($sql);
  	while($e=mysqli_fetch_assoc($result)){
        		$output[]=$e; 
  			}
  	
  	print(json_encode($output)); 
  	$mysqli->close();
  ?>