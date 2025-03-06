$param1 = $args[0]  # Nume fisier exe (Not used in the script, but it could be passed for dynamic exe names)
$param2 = $args[1]  # No of runs
$param3 = $args[2]  # No of reader threads
$param4 = $args[3]  # No of workers
$param5 = $args[4]  # DeltaT
$param6 = $args[5]  # Compare (1 or 0)

# Start the server process
$serverProcess = Start-Process -FilePath "./Server.exe" -ArgumentList "$param3 $param4 $param5 $param6" -NoNewWindow -PassThru

# Wait for the server to start
Start-Sleep -Seconds 2

# Start the client process
$clientProcess1 = Start-Process -FilePath "./Client.exe" -ArgumentList "Romania.txt"
$clientProcess2 = Start-Process -FilePath "./Client.exe" -ArgumentList "USA.txt"
$clientProcess3 = Start-Process -FilePath "./Client.exe" -ArgumentList "France.txt"
$clientProcess4 = Start-Process -FilePath "./Client.exe" -ArgumentList "Germany.txt"

Start-Sleep -Seconds 2

$clientProcess5 = Start-Process -FilePath "./Client.exe" -ArgumentList "Italy.txt"