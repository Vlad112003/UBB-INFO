$param1 = $args[0]  # Nume fisier exe (Not used in the script, but it could be passed for dynamic exe names)
$param2 = $args[1]  # No of runs
$param3 = $args[2]  # No of reader threads
$param4 = $args[3]  # No of workers
$param5 = $args[4]  # DeltaT
$param6 = $args[5]  # Compare (1 or 0)

$suma = 0

for ($i = 0; $i -lt $param2; $i++) {
    Write-Host "Rulare" ($i+1)

    # Start the server process
    $serverProcess = Start-Process -FilePath "./Server.exe" -ArgumentList "$param3 $param4 $param5 $param6" -PassThru -NoNewWindow

    # Wait for the server to start
    Start-Sleep -Seconds 2

    # Start the client process
    $clientProcess = Start-Process -FilePath "./Client.exe" -PassThru -NoNewWindow

    # Wait for the server to complete its work
    Start-Sleep -Seconds 30

    # Check if server_output.txt exists and contains data
    if (Test-Path ".\server_output.txt") {
        $a = Get-Content ".\server_output.txt" | Select-String "milliseconds"

        # Ensure that we matched the expected time format
        if ($a -match "([0-9]+\.[0-9]+) milliseconds") {
            $time = [double]$matches[1]
            Write-Host $time "milliseconds"
            $suma += $time
        } else {
            Write-Host "Error: Couldn't find execution time in the format 'xxx.xxx milliseconds'."
        }
    } else {
        Write-Host "Error: server_output.txt does not exist."
    }

    Write-Host ""

    # Stop the server process if it exists
    if ($serverProcess -and !$serverProcess.HasExited) {
        Write-Host "Stopping server process..."
        Stop-Process -Id $serverProcess.Id
    } else {
        Write-Host "Error: Server process not found or already exited."
    }

    # Stop client process (optional, depending on how you want to manage it)
    if ($clientProcess -and !$clientProcess.HasExited) {
        Stop-Process -Id $clientProcess.Id
    }
}

# Compute the average execution time
if ($param2 -gt 0) {
    $media = $suma / $param2
    Write-Host "Timp de executie mediu:" $media "milliseconds"
}

# Creare fisier .csv
if (!(Test-Path "outC.csv")) {
    New-Item "outC.csv" -ItemType File
    # Scrie date in csv
    Set-Content "outC.csv" 'Nr. Reader Threads,Nr. Workers,Timp executie mediu'
}

# Append the average time to the CSV file
Add-Content "outC.csv" "$($param3),$($param4),$($media)"
