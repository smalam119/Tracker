<?php

$userName = $_GET['userName'];

require_once('dbConnect.php');

//$sql = "DELETE FROM users WHERE username = '$userName'" ;
//$sql .= "DELETE FROM watchers WHERE user = '$userName'" ;

$sql = "DELETE users,watchers FROM users INNER JOIN watchers ON users.username = watchers.user WHERE users.username = '$userName'";

if(mysqli_query($con,$sql))
	{
 		echo 'Employee Deleted Successfully';
	}
 else
 	{
 		echo 'Could Not Delete Employee Try Again';
 	}
 
 //closing connection 
 mysqli_close($con);

 ?>