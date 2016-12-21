<?php

define('HOST','MYSQL5016.HostBuddy.com');
define('USER','9fa072_testdb');
define('PASS','a12345678');
define('DB','db_9fa072_testdb');

$con = mysqli_connect(HOST,USER,PASS,DB) or die('unable to connect');

?>