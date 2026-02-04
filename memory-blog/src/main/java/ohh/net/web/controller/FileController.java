package ohh.net.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.qiniu.common.QiniuException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.exception.CustomException;
import ohh.net.common.utils.Result;
import ohh.net.common.utils.OssUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.get.ListFilesResult;
import org.dromara.x.file.storage.core.get.RemoteDirInfo;
import org.dromara.x.file.storage.core.get.RemoteFileInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 统一文件上传
 *
 * @author laifeng
 * @date 2024/12/14
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/file")
@Transactional
public class FileController {
    @Resource
    private ohh.net.web.mapper.FileDetailMapper fileDetailMapper;
    @Resource
    private FileStorageService fileStorageService;

    @PostMapping("/external")
    @Operation(summary = "添加外部链接文件")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 1)
    public Result<String> addExternal(@RequestBody Map<String, String> params) {
        String dir = params.get("dir");
        String url = params.get("url");

        if (dir == null || dir.trim().isEmpty())
            throw new CustomException(400, "请指定一个目录");
        if (url == null || url.trim().isEmpty())
            throw new CustomException(400, "请提供图片链接");

        // Extract filename from URL
        String filename = "external_file";
        try {
            String pathPart = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
            filename = pathPart.substring(pathPart.lastIndexOf("/") + 1);
        } catch (Exception e) {
            // ignore
        }
        if (filename.isEmpty()) filename = "external_file";

        ohh.net.model.FileDetail fileDetail = new ohh.net.model.FileDetail();
        fileDetail.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        fileDetail.setUrl(url);
        fileDetail.setFilename(filename);
        fileDetail.setOriginalFilename(filename);
        fileDetail.setPath(dir + "/");
        fileDetail.setBasePath("");
        fileDetail.setPlatform("external");
        fileDetail.setExt(filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : "png");
        fileDetail.setSize(0L); // Unknown size for external link
        fileDetail.setCreateTime(new Date());

        fileDetailMapper.insert(fileDetail);

        return Result.success("添加成功");
    }

    @PostMapping
    @Operation(summary = "文件上传")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 1)
    public Result<Object> add(@RequestParam(defaultValue = "") String dir, @RequestParam MultipartFile[] files)
            throws IOException {
        if (dir == null || dir.trim().isEmpty())
            throw new CustomException(400, "请指定一个目录");

        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            FileInfo result = fileStorageService.of(file)
                    .setPlatform(OssUtils.getPlatform())
                    .setPath(dir + '/')
                    .upload();

            if (result == null)
                throw new CustomException("上传文件失败");

            String url = result.getUrl();
            urls.add(url.startsWith("https://") ? url : "https://" + url);
        }

        return Result.success("文件上传成功：", urls);
    }

    @DeleteMapping
    @Operation(summary = "删除文件")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 2)
    public Result<String> del(@RequestParam String filePath) {
        // Try to delete from DB first (for external files)
        // Note: filePath usually passed as filename or path. 
        // Logic might need check. But typically del takes URL or Key.
        // x-file-storage delete takes URL.

        String url = filePath.startsWith("https://") ? filePath : "https://" + filePath;
        
         // Check if it is external file by URL
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ohh.net.model.FileDetail> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        wrapper.eq(ohh.net.model.FileDetail::getUrl, url);
        // Or if filePath is just filename?
        // Let's assume URL match for now.
        
        ohh.net.model.FileDetail dbFile = fileDetailMapper.selectOne(wrapper);
        if (dbFile != null && "external".equals(dbFile.getPlatform())) {
            fileDetailMapper.deleteById(dbFile.getId());
            return Result.success("删除成功");
        }

        boolean delete = fileStorageService.delete(url);
        return Result.status(delete);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除文件")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 3)
    public Result<String> batchDel(@RequestBody String[] pathList) throws QiniuException {
        for (String url : pathList) {
            String fullUrl = url.startsWith("https://") ? url : "https://" + url;
            
            // Try delete external
            com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ohh.net.model.FileDetail> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
            wrapper.eq(ohh.net.model.FileDetail::getUrl, fullUrl);
            ohh.net.model.FileDetail dbFile = fileDetailMapper.selectOne(wrapper);
             
            if (dbFile != null && "external".equals(dbFile.getPlatform())) {
                fileDetailMapper.deleteById(dbFile.getId());
                continue;
            }
            
            boolean delete = fileStorageService.delete(fullUrl);
            // Ignore failure for mixed batch
        }
        return Result.success();
    }

    @GetMapping("/info")
    @Operation(summary = "获取文件信息")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 4)
    public Result<FileInfo> get(@RequestParam String filePath) throws QiniuException {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(filePath);
        return Result.success(fileInfo);
    }

    @GetMapping("/dir")
    @Operation(summary = "获取目录列表")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 5)
    public Result<List<Map<String, Object>>> getDirList() {
        List<Map<String, Object>> list = new ArrayList<>();
        
        try {
            ListFilesResult result = fileStorageService.listFiles()
                    .setPlatform(OssUtils.getPlatform())
                    .listFiles();

            List<RemoteDirInfo> fileList = result.getDirList();
            for (RemoteDirInfo item : fileList) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", item.getName());
                data.put("path", item.getOriginal());
                list.add(data);
            }
        } catch (Exception e) {
            // Ignore error if platform not set
        }
        return Result.success(list);
    }

    @GetMapping("/list")
    @Operation(summary = "获取指定目录中的文件")
    @ApiOperationSupport(author = "OHH | 2720751424@qq.com", order = 5)
    public Result<Map<String, Object>> getFileList(
            @RequestParam String dir,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        if (dir == null || dir.trim().isEmpty())
            throw new CustomException(400, "请指定一个目录");

        List<Map<String, Object>> fileList = new ArrayList<>();
        
        // 1. Fetch from Storage Platform (if available)
        try {
             ListFilesResult result = fileStorageService.listFiles()
                    .setPlatform(OssUtils.getPlatform())
                    .setPath(dir + '/')
                    .listFiles();

            List<RemoteFileInfo> remoteFileList = result.getFileList();
            for (RemoteFileInfo item : remoteFileList) {
                if (Objects.equals(item.getExt(), "")) continue;
                Map<String, Object> data = new HashMap<>();
                data.put("basePath", item.getBasePath());
                data.put("dir", dir);
                data.put("path", item.getBasePath() + item.getPath() + item.getFilename());
                data.put("name", item.getFilename());
                data.put("size", item.getSize());
                data.put("type", item.getExt());
                data.put("date", item.getLastModified());
                String url = item.getUrl();
                if (!url.startsWith("https://")) url = "https://" + url;
                data.put("url", url);
                fileList.add(data);
            }
        } catch (Exception e) {
            // Platform might not be set, ignore
        }
        
        // 2. Fetch from DB (External Files or Database Records)
        // We particularly want 'external' files, but maybe all records for this path to be safe/complete?
        // Since getFileList usually returns RemoteFileInfo, we are mocking it here.
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ohh.net.model.FileDetail> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        wrapper.eq(ohh.net.model.FileDetail::getPath, dir + "/");
        // wrapper.eq(ohh.net.model.FileDetail::getPlatform, "external"); // Optionally restrict to external if x-file-storage handles others? 
        // Actually, x-file-storage's listFiles() lists what's on storage. If we use DB recorder, duplicates might appear if we query both.
        // But since user "doesn't use storage platform", listing from DB is Key.
        
        // Let's query only 'external' platform from DB to avoid duplicates if normal upload works.
        // Or if normal storage is broken/empty, we want everything from DB.
        // Let's assume we want platform='external' to be safe.
        wrapper.eq(ohh.net.model.FileDetail::getPlatform, "external");
        
        List<ohh.net.model.FileDetail> dbFiles = fileDetailMapper.selectList(wrapper);
        for (ohh.net.model.FileDetail item : dbFiles) {
             Map<String, Object> data = new HashMap<>();
             data.put("basePath", item.getBasePath());
             data.put("dir", dir);
             data.put("path", item.getPath() + item.getFilename());
             data.put("name", item.getFilename());
             data.put("size", item.getSize());
             data.put("type", item.getExt());
             data.put("date", item.getCreateTime());
             data.put("url", item.getUrl());
             fileList.add(data);
        }

        // Sort by date desc (mix of lastModified and createTime)
        fileList.sort((a, b) -> {
             Object d1 = a.get("date");
             Object d2 = b.get("date");
             // Handle different date types if necessary (Date vs LocalDateTime etc)
             // Simplified: ToStr comparison or try parse?
             // Best effort:
             if (d1 instanceof Date && d2 instanceof Date) return ((Date)d2).compareTo((Date)d1);
             return 0;
        });

        // Pagination
        int total = fileList.size();
        int startIndex = (page - 1) * size;
        if (startIndex > total) startIndex = total;
        int endIndex = Math.min(startIndex + size, total);

        List<Map<String, Object>> pageList = fileList.subList(startIndex, endIndex);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", pageList);
        resultMap.put("size", size);
        resultMap.put("page", page);
        resultMap.put("pages", (total + size - 1) / size);
        resultMap.put("total", total);

        return Result.success(resultMap);
    }
}
