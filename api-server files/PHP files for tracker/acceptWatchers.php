<?php

echo 'okay';

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$userName = $_POST['userName'];
	$watcherName = $_POST['watcherName'];
	$isAccept = $_POST['isAccept'];

	$sql = "UPDATE watchers SET is_accepted = '$isAccept' WHERE user = '$userName' AND watcher = '$watcherName'";

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