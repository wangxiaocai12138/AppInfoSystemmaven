package cn.appsys.tools;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

//单文件上传工具类
public class FileUploadUtil {
    private static String fileUpload(MultipartFile attach, HttpServletRequest request,
                                     Integer filesize, String [] prefixs,String versionNo){
        //1、上传后的文件路径
        String filePath=null;
        //2、查看文件域是否为空
        if(!attach.isEmpty()){//true为空
            //3、定义上传目标的路径
            String path=request.getSession().getServletContext().//separator自适应的文件路径分隔符，减低代码的可入侵性
                    getRealPath("static"+ File.separator+"uploadfiles");
            //4、获取源文件名
            String oldFileName=attach.getOriginalFilename();
            //文件后缀
            String prefix= FilenameUtils.getExtension(oldFileName);
            //文件大小判断
            if(attach.getSize()>filesize){//说明文件过大
                request.setAttribute("fileerror","上传文件过大！");
                return "fileMaxError";
            }

            for (int i=0;i<prefixs.length;i++){
                if(prefix.equalsIgnoreCase(prefixs[i])){
                    //5、开始上传
                    File targetFile=new File(path,oldFileName);
                    if(!targetFile.exists()){//判断文件夹是否存在，不存在就创建
                        targetFile.mkdirs();
                    }
                    //6、接收文件流
                    try {
                        attach.transferTo(targetFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "fileException";//文件发生异常
                    }
                    filePath=path+ File.separator+oldFileName;//文件路径
                    return filePath;
                }
            }
        }
        return "fileFormatError";//文件格式不正确

    }
}
