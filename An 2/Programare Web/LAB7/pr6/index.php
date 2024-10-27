<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Problema 6</title>
    <link rel="stylesheet" type="text/css" href="mystyle.css">
</head>
<body>

<div class ="container">
    <table>
        <tr>
            <td>
                <h2>Authentication</h2>
                <form  "<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>" method = "POST" enctype="multipart/form-data">
                <input type = 'hidden' name = 'tip' value ='login'>
                <label>Username</label><br>
                <input type="text" name='username'  placeholder="Enter Username" required><br>
                <label>Password</label><br>
                <input type="password" name='password'  placeholder="Enter Password" required>
                <br><br>
                <button type="submit" >Login </button>
                </form>
            </td>
            <td>
                <article>



                    <h1>CHEC PUFOS CU NUCA SI CACAO</h1>

                    <img src="chec.webp" alt="chec" width="300" height="400">
                    <p>Păi… când nu avem timp de rețete de prăjituri elaborate recurgem și la câte o rețetă de chec pufos și simplu și economic,
                        așa cum e cea pe care v-am adus-o eu azi 😉 Ș-apoi, nu știu cum să vă spun, dar pe cât o fi checul ăsta de simplu,
                        pe atâta e de gustos. La mine dovada cea mai grăitoare e ca în câteva ceasuri checul pufos era doar o amintire.
                        Incercați și voi?</p>
                    <p>INGREDIENTE</p>
                    <cite>
                        <ul>
                            <li>4 oua</li>
                            <li>4 linguri zahăr</li>
                            <li>4 linguri ulei</li>
                            <li>4 linguri făină</li>
                            <li>2 linguri cacao</li>
                            <li>1 lingurițâ praf de copt</li>
                            <li>1/2 linguriță coajă confiată de portocală</li>
                            <li>esență de rom</li>
                            <li>~100 g  miez de nucă</li>
                        </ul>
                    </cite>

                    <img src="chec-pufos.webp" alt="chec-pufos" width="400" height="300">
                    <p>
                        MOD DE PREPARARE
                    <ol>
                        <li>Separam albusurile de galbenusuri. Albusurile le batem spuma tare cu un praf de sare.
                            Cand spuma s-a intarit bine adaugam zaharul si mixam in continuare pana obtinem o bezea ferma si lucioasa.</li>
                        <li>Separat frecam galbenusurile cu cele 4 linguri de ulei. Emulsia obtinuta o turnam peste spuma de albusuri si amestecam usor, la viteza cea mai mica a mixerului.
                            Cernem faina impreuna cu praful de copt si o adaugam in compozitie amestecand usor cu o spatula, cu miscari de jos in sus.</li>
                        <li>Impartim compozitia in 2 parti egale. In una din parti cernem cacaoa si aromam cu esenta de rom, in cealalta incorporam coaja confiata de portocala.</li>
                        <li>Intr-o tava de chec tapetata cu hartie de copt turnam cele 2 compozitii una peste alta, in ce ordine vreti.
                            Nuca se rupe cu mana si se presara pe toata suprafata compozitiei din tava.</li>
                        <li>Dam tava la cuptor la foc mediu pentru cca jumatate de ora, pana trece testul cu scobitoarea.
                            Se lasa sa se raceasca, apoi se feliaza dupa dorinta.</li>
                    </ol>
                    </p>
                </article>
                <h3> Comentarii</h3>

                <?php
                ob_start();
                $con = mysqli_connect("localhost", "root", "","lab8");
                if (!$con) {
                    die('Could not connect: ' . mysqli_error());
                }

                $sql = "SELECT * FROM messages WHERE active = 1 ;";
                $result = mysqli_query($con, $sql);
                echo "<table class = 'myTable'>";
                while($row = mysqli_fetch_array($result)){

                    echo "<tr><th><hr>" .$row["username"]. ":" ."</th></tr>";
                    echo "<tr><td>" .$row["message"] . "</td> </tr>";
                }
                echo "</table>";
//                ob_end_flush();
                ?>
                <br>
                <h3> Adauga un nou comentariu </h3>
                <form "<?php echo htmlspecialchars($_SERVER['PHP_SELF']);?>" method = "POST">
                <input type = 'hidden'  name = 'tip' value = 'comentariu'>
                <input type = 'text' name = 'username' placeholder = 'Username'><br>
                <input type = 'text' name = 'message' placeholder = 'Comentariu'><br>

                <button type ='submit'>Submit</button>
                </form>
            </td>
        </tr>
    </table>
</div>

<?php
ob_start();
function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$username = "";
$password = "";
$message = "";
$tip = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $tip = test_input($_POST["tip"]);
    if ($tip =='login'){
        $username = test_input($_POST["username"]);
        $password = test_input($_POST["password"]);
        if (preg_match("/^[a-zA-Z][a-zA-Z0-9-_\.]{1,30}$/",$username)) {
            $con = mysqli_connect("localhost", "root", "","lab8");
            if (!$con) {
                die('Could not connect: ' . mysqli_error());
            }
            $sql = "SELECT count(*) FROM users WHERE username = '" . $username ."' AND password = '" . $password . "';";
            $result=mysqli_query($con,$sql);
            $row=mysqli_fetch_array($result);
            if ($row[0]==1 and $username == 'admin'){
                header("Location: http://localhost:63342/LAB7/pr6/indexAdmin.php?username=".$username);
            }
            else {
                echo("Invalid username or password");
            }
            mysqli_close($con);
        }
        else{
            echo "erro";
        }
    }
    else  {
        if ($tip =='comentariu'){
            $con = mysqli_connect("localhost", "root", "","lab8");
            if (!$con) {
                die('Could not connect: ' . mysqli_error());
            }
            $username = test_input($_POST["username"]);
            $message = test_input($_POST["message"]);

            $sql = "INSERT INTO messages(username, message, active) VALUES ('" . $username ."','" . $message . "', 0);";
            $result=mysqli_query($con,$sql);
            header("Location: http://localhost:63342/LAB7/pr6/index.php");
            echo("Comentariul este in asteptare de aprobare");
            mysqli_close($con);

        }
    }
}
ob_end_flush();
?>
</body>
</html>