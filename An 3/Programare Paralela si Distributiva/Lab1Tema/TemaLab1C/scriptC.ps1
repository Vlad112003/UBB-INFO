$param1 = $args[0] # Nume fisier exe
$param2 = $args[1] # No of runs
$param3 = $args[2] # No of threads
$param4 = $args[3] # Matrix rows
$param5 = $args[4] # Matrix cols
$param6 = $args[5] # Function name (secvential, linii, coloane, vectorizare)
$param7 = $args[6] # Convolution matrix size (n)

$suma = 0

for ($i = 0; $i -lt $param2; $i++) {
    Write-Host "Rulare" ($i+1)

    # Capture the output of the C++ program and filter only the line with 'milliseconds'
    $a = (cmd /c .\$param1 $param3 $param4 $param5 $param6 $param7 2>&1 | Select-String "milliseconds")

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

# Determine the allocation type based on the filename
$tipAlocare = if ($param1 -match "Dinamic") { "dinamic" } else { "static" }

# Creare fisier .csv
if (!(Test-Path outC.csv)) {
    New-Item outC.csv -ItemType File
    # Scrie date in csv
    Set-Content outC.csv 'Tip matrice,Matrice convolutie,Tip alocare,Nume functie,Nr. Threads,Timp executie'
}

# Append the average time to the CSV file
Add-Content outC.csv "$($param4)x$($param5),$($param7)x$($param7),$tipAlocare,$($param6),$($param3),$($media)"