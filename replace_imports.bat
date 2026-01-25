@echo off
echo Starting replacement of liuyuyang with ohh in all Java files...
echo.

for /r %%f in (*.java) do (
    echo Processing: %%f
    powershell -Command "(Get-Content '%%f') -replace 'import liuyuyang', 'import ohh' | Set-Content '%%f'"
)

echo.
echo Replacement completed!
pause
