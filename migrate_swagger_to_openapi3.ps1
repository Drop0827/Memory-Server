# Migrate Swagger 2 annotations to OpenAPI 3
$files = Get-ChildItem -Path . -Recurse -Include *.java

$replacements = @{
    'import io.swagger.annotations.Api;'              = 'import io.swagger.v3.oas.annotations.tags.Tag;'
    'import io.swagger.annotations.ApiModel;'         = 'import io.swagger.v3.oas.annotations.media.Schema;'
    'import io.swagger.annotations.ApiModelProperty;' = 'import io.swagger.v3.oas.annotations.media.Schema;'
    'import io.swagger.annotations.ApiOperation;'     = 'import io.swagger.v3.oas.annotations.Operation;'
    'import io.swagger.annotations.ApiParam;'         = 'import io.swagger.v3.oas.annotations.Parameter;'
    '@Api\('                                          = '@Tag('
    '@ApiModel\('                                     = '@Schema('
    '@ApiModelProperty\('                             = '@Schema('
    '@ApiOperation\('                                 = '@Operation('
    '@ApiParam\('                                     = '@Parameter('
}

$count = 0
foreach ($file in $files) {
    $content = [System.IO.File]::ReadAllText($file.FullName, [System.Text.UTF8Encoding]::new($false))
    $modified = $false
    
    foreach ($key in $replacements.Keys) {
        if ($content -match $key) {
            $content = $content -replace $key, $replacements[$key]
            $modified = $true
        }
    }
    
    if ($modified) {
        [System.IO.File]::WriteAllText($file.FullName, $content, [System.Text.UTF8Encoding]::new($false))
        Write-Host "Updated: $($file.Name)"
        $count++
    }
}

Write-Host ""
Write-Host "Migration completed! Updated $count files" -ForegroundColor Green
