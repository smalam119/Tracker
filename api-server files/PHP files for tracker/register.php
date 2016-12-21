<?php

echo 'okay';

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$username = $_POST['userName'];
	$password = $_POST['password'];
	$phoneNumber = $_POST['phoneNumber'];
	//$noOfWatchers = $_POST['noOfWatchers'];
	//$noOfWatching = $_POST['noOfWatching'];

	$sql = "INSERT INTO users (username,password,phone_number) VALUES ('$userName','$password','$phoneNumber')";

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