<?php

$observerId = $_GET['observerId'];

require_once('dbConnect.php');

$sql = "SELECT * FROM observer_loc WHERE observerId = $observerId";

$r = mysqli_query($con,$sql)
or die("Error: ".mysqli_error($con));

$result = array();

$row = mysqli_fetch_array($r);

array_push($result, array("observerId" => $row['observerId'],
						  "currentLoc" => $row['currentLoc'],
						  "alertState" => $row['alertState'] ));

echo json_encode(array('result'=>$result));
 
mysqli_close($con);

?>