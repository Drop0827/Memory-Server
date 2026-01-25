# 批量替换 import liuyuyang 为 import ohh
$ErrorActionPreference = "Continue"
$files = Get-ChildItem -Path . -Recurse -Include *.java

$count = 0
foreach ($file in $files) {
    try {
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        if ($content -match 'import liuyuyang') {
            # 只替换 import 语句中的 liuyuyang
            $newContent = $content -replace 'import liuyuyang', 'import ohh'
            # 使用 UTF8 编码，保留原始换行符
            [System.IO.File]::WriteAllText($file.FullName, $newContent, [System.Text.UTF8Encoding]::new($false))
            Write-Host "已处理: $($file.Name)"
            $count++
        }
    }
    catch {
        Write-Host "处理失败: $($file.FullName) - $_" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "替换完成！共处理 $count 个文件" -ForegroundColor Green
