$param1 = $args[0] # Nume fisier exe
$param2 = $args[1] # No of runs
$param3 = $args[2] # No of threads
$param4 = $args[3] # No of readers
$param5 = $args[4] # Generate (1 or 0)
$param6 = $args[5] # Compare (1 or 0)
$param7 = $args[6] # Function name (secvential, paralel)

$suma = 0

for ($i = 0; $i -lt $param2; $i++) {
    Write-Host "Rulare" ($i+1)

    # Capture the output of the C++ program and filter only the line with 'milliseconds'
    $a = (cmd /c .\$param1 $param7 $param3 $param4 $param5 $param6 2>&1 | Select-String "milliseconds")

    # Extract the time from the output
    if ($a -match "([0-9]+\.[0-9]+) milliseconds") {
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
    Set-Content outC.csv 'Tip functie,Nr. Threads,Nr. Readers,Timp executie mediu'
}

# Append the average time to the CSV file
Add-Content outC.csv "$($param7),$($param3),$($param4),$($media)"