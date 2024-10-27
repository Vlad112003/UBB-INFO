<?php
$con = mysqli_connect("localhost", "root", "","lab8");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}
$id = $_GET["id"];
$sql = "UPDATE messages SET active = '1' WHERE id = " . $id .";";
mysqli_query($con,$sql);
echo 'Mesaj aprobat cu succes!';
header("Location: http://localhost:63342/LAB7/pr6/indexAdmin.php");
mysqli_close($con);
?>