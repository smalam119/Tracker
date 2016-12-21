<?php

if($_SERVER['REQUEST_METHOD'] == POST)
{
	$rawUserName = $_POST['rawUserName'];
	$username = $_POST['userName'];

	require_once('dbConnect.php');

	$sql = "UPDATE users SET username = '$username' WHERE username = '$rawUserName'";

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