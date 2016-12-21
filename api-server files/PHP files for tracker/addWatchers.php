<?php

echo 'okay';

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$userName = $_POST['userName'];
	$watcherName = $_POST['watcherName'];

	$sql = "INSERT INTO watchers (user,watcher) VALUES ('$userName','$watcherName')";

	require_once('dbConnect.php');

	if(mysqli_query($con,$sql))
	{
 		echo 'Added Successfully';
 	}
 	else
 	{
 	echo ("Could not insert data : " . mysqli_error($con) . " " . mysqli_errno());
 	}
  
 	mysqli_close($con);
 
}

?>