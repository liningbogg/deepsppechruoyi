package com.ruoyi.web.controller.asr;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.web.domain.asr.AsrTaskset;
import com.ruoyi.web.domain.asr.AsrTask;
import com.ruoyi.web.service.asr.IAsrTaskService;
import com.ruoyi.web.service.asr.IAsrTasksetService;
import com.ruoyi.common.utils.UZipFile;
/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
public class handleUpload
{
    private static final Logger log = LoggerFactory.getLogger(handleUpload.class);

    @Autowired
    private ServerConfig serverConfig;
    
    @Autowired
    private IAsrTasksetService asrTasksetService;
    
    @Autowired
    private IAsrTaskService asrTaskService;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private RedisCache redisCache;
    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */

    /**
     * 通用上传请求
     */
    
    @CrossOrigin
    @Log(title = "单次任务集合", businessType = BusinessType.INSERT)
    @PostMapping("/asr/handleUploadling")
    public AjaxResult handleUploadling(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getProfile();
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String username = loginUser.getUsername();
            filePath = filePath +"/"+ username;
            String fileName = file.getOriginalFilename();
            // 上传并返回新文件名称
            String[] ALLOWED_EXTENSION = {"zip",};
            int fileNamelength = file.getOriginalFilename().length();
            if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
            {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, ALLOWED_EXTENSION);
            String extension = getExtension(file);  // 压缩包扩展名
            String baseName = fileName.substring(0, fileName.lastIndexOf(".")); //basename
            String nowstring = DateUtils.dateTimeNow();
            fileName = baseName + "_" + nowstring +"."+extension;
            File dest = getAbsoluteFile(filePath, fileName);  // 目标文件zip
            file.transferTo(dest);  //传输文件
            String pathFileName = getPathFileName(filePath, fileName);  //相对uploadPath的位置
            String url = serverConfig.getUrl() +"/profile/"+username+"/"+fileName; 
            String fullName = filePath + "/" + fileName;  // 文件全路径
            String savepath = filePath + File.separator + baseName+ "_" 
            		+ nowstring + File.separator; //保存解压文件目录 

            // 添加任务集合
            AsrTaskset asrTaskset = new AsrTaskset();
            asrTaskset.setCreateBy(username);
            asrTaskset.setCreateTime(new Date());
            asrTaskset.setStatus("0");
            asrTaskset.setTasksetName(baseName+ "_" + nowstring);
            asrTasksetService.insertAsrTaskset(asrTaskset);
            Long tasksetid = asrTaskset.getTasksetId();
            
            // 解压zip
            List<String> filelist = UZipFile.unZipFiles(fullName, savepath);
            Iterator<String> iter = filelist.iterator();
            while (iter.hasNext()) {
            	String itemname = iter.next();
                String item = itemname.substring(itemname.lastIndexOf("."),itemname.length()); 
                if (!item.equals(".wav")) {
                    iter.remove();
                    log.info(item +"不是所需要的文件格式");
                }else {
                	// 为任务集合逐个添加wav转写任务
                	AsrTask asrTask = new AsrTask();
                	asrTask.setCreateBy(username);
                	asrTask.setCreateTime(new Date());
                	asrTask.setStatus("0");
                	String taskname = itemname.substring(itemname.indexOf(username),itemname.length()); 
                	System.out.println(taskname);
                	asrTask.setTaskName(taskname);
                	asrTask.setFilepath(itemname);
                	asrTask.setTasksetId(tasksetid);
                	asrTaskService.insertAsrTask(asrTask);
                	Long taskid = asrTask.getTaskId();
                	com.alibaba.fastjson.JSONObject taskobject = new com.alibaba.fastjson.JSONObject();
                	taskobject.put("filepath", itemname);
                	taskobject.put("taskid", taskid.toString());
                	redisCache.lSet("asrtask", taskobject);
                }
            }
            
            /*AsrTaskset tasksetcond = new AsrTaskset();
            tasksetcond.setCreateBy(username);
            tasksetcond.setStatus("0");
            List<AsrTaskset> tasklist = asrTasksetService.selectAsrTasksetList(tasksetcond);
            Iterator<AsrTaskset> itertask = tasklist.iterator();
            while (itertask.hasNext()) {
            	AsrTaskset item = itertask.next();
            	System.out.println(item);
            }*/
            
            
            
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", pathFileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
        	System.out.println(e);
            return AjaxResult.error(e.getMessage());
        }
    }
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }
    private static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = RuoYiConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }
}
