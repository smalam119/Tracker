<?php

if($_SERVER('REQUEST_METHOD') == POST)
{
	$username = $_POST['userName'];
	$password = $_POST['password'];
	$phoneNumber = $_POST['phoneNumber'];
	$noOfWatchers = $_POST['noOfWatchers'];
	$noOfWatching = $_POST['noOfWatching'];

	require_once('dbConnect.php');

	$sql = "UPDATE users SET password = $password, phone_Number = $phoneNumber WHERE userIdTag = $userIdTag";

	if(mysqli_query($con,$sql))
	{
 		echo 'Updated Successfully';
 	}
 	else
 	{
 	echo 'Could Not Update';
 	}
  
 	mysqli_close($con);

}

?>