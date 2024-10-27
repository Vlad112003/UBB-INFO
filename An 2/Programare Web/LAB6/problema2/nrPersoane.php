<?php
$con = mysqli_connect("localhost", "root", "","lab7");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$pageNr = $_GET["page"];
$pageLimit = 3;
$result = mysqli_query($con, "SELECT * FROM persoana " .$pageNr ."," . $pageLimit .";");

echo $result->num_rows;

mysqli_close($con);
?>