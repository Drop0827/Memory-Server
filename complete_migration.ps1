# Step 1: Replace package names from liuyuyang to ohh
# Step 2: Fix OpenAPI 3 annotation parameters

$files = Get-ChildItem -Path . -Recurse -Include *.java
$step1Count = 0
$step2Count = 0

Write-Host "Step 1: Replacing package names..." -ForegroundColor Cyan

foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.UTF8Encoding]::new($false))
    $originalContent = $content
    
    # Step 1: Replace package imports
    $content = $content -replace 'import liuyuyang\.net\.', 'import ohh.net.'
    $content = $content -replace 'package liuyuyang\.net\.', 'package ohh.net.'
    
    if ($content -ne $originalContent) {
        [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.UTF8Encoding]::new($false))
        Write-Host "  [Package] $($file.Name)" -ForegroundColor Green
        $step1Count++
    }
}

Write-Host "`nStep 2: Fixing OpenAPI 3 annotation parameters..." -ForegroundColor Cyan

foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.UTF8Encoding]::new($false))
    $originalContent = $content
    
    # Fix @Tag annotation
    $content = $content -replace '@Tag\(tags\s*=\s*"([^"]+)"\)', '@Tag(name = "$1")'
    
    # Fix @Operation annotation - only if it's a simple string parameter
    $content = $content -replace '@Operation\("([^"]+)"\)', '@Operation(summary = "$1")'
    
    # Fix @Schema annotation parameters
    $content = $content -replace '@Schema\(value\s*=\s*"([^"]+)"', '@Schema(description = "$1"'
    $content = $content -replace ',\s*required\s*=\s*true\)', ', requiredMode = Schema.RequiredMode.REQUIRED)'
    
    # Fix @Parameter annotation
    $content = $content -replace '@Parameter\(value\s*=\s*"([^"]+)"', '@Parameter(description = "$1"'
    
    if ($content -ne $originalContent) {
        [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.UTF8Encoding]::new($false))
        Write-Host "  [OpenAPI3] $($file.Name)" -ForegroundColor Yellow
        $step2Count++
    }
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Migration Summary:" -ForegroundColor White
Write-Host "  Package names updated: $step1Count files" -ForegroundColor Green
Write-Host "  OpenAPI 3 params fixed: $step2Count files" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
