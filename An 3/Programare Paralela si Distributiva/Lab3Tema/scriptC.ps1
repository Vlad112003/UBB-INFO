$param1 = $args[0] # Nume fisier exe
$param2 = $args[1] # No of runs
$param3 = $args[2] # No of processes
$param4 = $args[3] # Filename1
$param5 = $args[4] # Filename2
$param6 = $args[5] # Function name (secvential, paralel)
$param7 = $args[6] # Compare (1 or 0)

$suma = 0

for ($i = 0; $i -lt $param2; $i++) {
    Write-Host "Rulare" ($i+1)

    if ($param6 -eq "secvential") {
        # Capture the output of the C++ program and filter only the line with 'milliseconds'
        $a = (cmd /c .\$param1 $param4 $param5 $param6 $param7 2>&1 | Select-String "milliseconds")
    } elseif ($param6 -eq "paralel") {
        # Capture the output of the MPI program and filter only the line with 'milliseconds'
        $a = (cmd /c mpiexec -n $param3 .\$param1 $param4 $param5 $param6 $param7 2>&1 | Select-String "milliseconds")
    } else {
        Write-Host "Error: Unknown function name."
        exit 1
    }

    # Extract the time from the output
    if ($a -match "([0-9]+\.[0-9]+)") {
        $time = [double]$matches[1]
        Write-Host $time "milliseconds"
        $suma += $time
    } else {
        Write-Host "Error: Couldn't find execution time."
    }
    Write-Host ""
}

# Compute the average execution time
if ($param2 -gt 0) {
    $media = $suma / $param2
    Write-Host "Timp de executie mediu:" $media "milliseconds"
}

# Creare fisier .csv
if (!(Test-Path outC.csv)) {
    New-Item outC.csv -ItemType File
    # Scrie date in csv
    Set-Content outC.csv 'Filename1,Filename2,Function name,Nr. Processes,Timp executie'
}

# Append the average time to the CSV file
Add-Content outC.csv "$($param4),$($param5),$($param6),$($param3),$($media)"