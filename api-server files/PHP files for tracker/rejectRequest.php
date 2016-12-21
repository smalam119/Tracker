<?php

if($_SERVER['REQUEST_METHOD'] == POST)
{
	$requester = $_POST['requester'];
	$userName = $_POST['userName'];

	require_once('dbConnect.php');

	$sql = "DELETE FROM watchers WHERE user = '$requester' AND watcher = '$userName'";


if(mysqli_query($con,$sql))
	{
 		echo 'Delete Successfully';
 	}
 	else
 	{
 	echo ("Could not Delete data : " . mysqli_error($con) . " " . mysqli_errno());
 	}
  
 	mysqli_close($con);

}

?>