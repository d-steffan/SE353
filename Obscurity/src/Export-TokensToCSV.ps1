param([String]$Path, [String]$Destination)
$errors = $null
[System.Management.Automation.PSParser]::Tokenize((get-content $Path), [ref]$errors) | Export-Csv -Path $Destination -NoTypeInformation