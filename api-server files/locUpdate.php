<?php

if($_SERVER('REQUEST_METHOD') == POST)
{
	$observerId = $_POST['observerId'];
	$currentLoc = $_POST['currentLoc'];
	$alartState = $_POST['alertState'];

	require_once('dbConnect.php');

	$sql = "UPDATE observer_loc SET currentLoc = $currentLoc, alertState = $alartState WHERE observerId = $observerId";

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