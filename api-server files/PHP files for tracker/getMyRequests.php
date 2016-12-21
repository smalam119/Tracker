<?php  

 $userName = $_GET['userName'];


 require_once('dbConnect.php');
 
 //Creating sql query
 $sql = "SELECT * FROM watchers WHERE user = '$userName'";
 
 //getting result 
 $r = mysqli_query($con,$sql)
 or die("Error: ".mysqli_error($con));
 
 //creating a blank array 
 $result = array();
 
 //looping through all the records fetched
 while($row = mysqli_fetch_array($r)){
 
 //Pushing name and id in the blank array created 
 array_push($result,array(
 "watcherName"=>$row['watcher'],
 "isAccepted" => $row['is_accepted']
 ));
 }
 
 //Displaying the array in json format 
 echo json_encode(array('result'=>$result));
 
 mysqli_close($con);