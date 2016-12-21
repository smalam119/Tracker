<?php

$userName = $_GET['userName'];

require_once('dbConnect.php');

$sql = "SELECT * FROM users WHERE username = '$userName'";

$r = mysqli_query($con,$sql)
or die("Error: ".mysqli_error($con));

$result = array();

$row = mysqli_fetch_array($r);

array_push($result, array("userId" => $row['id'],
						  "userName" => $row['username'],
						  "password" => $row['password'],
						  "phoneNumber" => $row['phone_number'],
						  "noOfWatchers" => $row['no_of_watchers'],
						  "noOfWatching" => $row['no_of_watching'] ));

echo json_encode(array('result'=>$result));
 
mysqli_close($con);

?>