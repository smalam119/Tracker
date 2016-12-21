<?php

if($_SERVER['REQUEST_METHOD'] == POST)
{
	$userName = $_POST['userName'];
	$currentLat = $_POST['currentLat'];
	$currentLng = $_POST['currentLng'];
	$isInDanger = $_POST['isInDanger'];

	require_once('dbConnect.php');

	$sql = "UPDATE users SET current_lat = '$currentLat', current_lng = '$currentLng', is_in_danger = '$isInDanger' WHERE username = '$userName'";

	if(mysqli_query($con,$sql))
	{
 		echo 'Updated Successfully';
 	}
 	else
 	{
 	echo ("Could not insert data : " . mysqli_error($con) . " " . mysqli_errno());
 	}
  
 	mysqli_close($con);

}

?>