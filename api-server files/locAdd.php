<?php

echo 'okay';

if($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$currentLoc = $_POST['currentLoc'];
	$alertState = $_POST['alertState'];

	$sql = "INSERT INTO observer_loc (currentLoc,alertState) VALUES ($currentLoc,$alertState)";

	require_once('dbConnect.php');

	if(mysqli_query($con,$sql))
	{
 		echo 'Added Successfully';
 	}
 	else
 	{
 	echo 'Could Not Add';
 	}
  
 	mysqli_close($con);
 
}

?>