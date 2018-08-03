package cn.zeemoo.rbac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 文件服务器
 *
 * @author zhang.shushan
 */
//@Configuration
public class FileServerConfig extends WebMvcConfigurationSupport {

    @Value("${file.dir}")
    private String mImagesPath;

    /**
     * 访问文件
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String jar = ".jar";
        final String classes = "classes";

        if(mImagesPath.equals("") || mImagesPath.equals("${file.dir}")){
            String imagesPath = FileServerConfig.class.getClassLoader().getResource("").getPath();
            if(imagesPath.indexOf(jar)>0){
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(jar));
            }else if(imagesPath.indexOf(classes)>0){
                imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf(classes));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
            mImagesPath = imagesPath;
        }
        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        super.addResourceHandlers(registry);
    }
}
