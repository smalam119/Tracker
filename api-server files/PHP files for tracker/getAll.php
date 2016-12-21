<?php 
 //Importing Database Script 
 require_once('dbConnect.php');
 
 //Creating sql query
 $sql = "SELECT * FROM users";
 
 //getting result 
 $r = mysqli_query($con,$sql)
 or die("Error: ".mysqli_error($con));
 
 //creating a blank array 
 $result = array();
 
 //looping through all the records fetched
 while($row = mysqli_fetch_array($r)){
 
 //Pushing name and id in the blank array created 
 array_push($result, array("userId" => $row['id'],
						  "password" => $row['password'],
						  "phoneNumber" => $row['phone_number'],
						  "noOfWatchers" => $row['no_of_watchers'],
						  "noOfWatching" => $row['no_of_watching'], ));

}
 
 //Displaying the array in json format 
 echo json_encode(array('result'=>$result));
 
 mysqli_close($con);

 ?>