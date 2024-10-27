<?php
$con = mysqli_connect("localhost", "root", "","lab7");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$result = mysqli_query($con, "SELECT cid FROM carte");
while($row = mysqli_fetch_array($result)){
    echo "<li>" .$row[0] ."</li>";
}
mysqli_close($con);
?>