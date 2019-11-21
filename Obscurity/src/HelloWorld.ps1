# write-host ((0..35)|%{if (($_+1)%3 -eq 0){[char][int]("07210110810811103208711111410810033"[($_-2)..$_] -join '')}}) -separator ''
$chars = "07210110810811103208711111410810033"
$out = ""
for ($i = 0; $i -lt 36; $i++)
{
    # testcomment
    if (($i+1) % 3 -eq 0)
    {
        $out += [char][int]($chars[($i-2)..$i] -join '')
    }
}
Write-Host $out