# 批量替换 liuyuyang 为 ohh
$files = Get-ChildItem -Path . -Recurse -Include *.java

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw -Encoding UTF8
    if ($content -match 'liuyuyang') {
        $newContent = $content -replace 'liuyuyang', 'ohh'
        Set-Content -Path $file.FullName -Value $newContent -Encoding UTF8 -NoNewline
        Write-Host "已处理: $($file.FullName)"
    }
}

Write-Host "替换完成！"
