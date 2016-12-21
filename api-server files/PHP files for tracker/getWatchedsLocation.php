<?php

$watcherName = $_GET['watcherName'];

require_once('dbConnect.php');

$sql = "SELECT * FROM users WHERE username = '$watcherName'";

$r = mysqli_query($con,$sql)
or die("Error: ".mysqli_error($con));

$result = array();

$row = mysqli_fetch_array($r);

array_push($result, array("currentLat" => $row['current_lat'],
						  "currentLng" => $row['current_lng'],
						  "isInDanger" => $row['is_in_danger'],
						  "isTrackingOn" => $row['is_tracking_on'] ));

echo json_encode(array('result'=>$result));
 
mysqli_close($con);

?>