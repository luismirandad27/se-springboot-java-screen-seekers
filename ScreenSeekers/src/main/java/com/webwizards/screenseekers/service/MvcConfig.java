/**
 * Java Class: MvcConfig
 * 
 * ------------
 * Description:
 * ------------ 
 * This class consists on a Resource Handler so we can expose the path of the images to the VueJs app
 * 
 * @author Luis Miguel MIranda
 * @version 1.0
 **/

package com.webwizards.screenseekers.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("resources",registry);
    }
     
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
        
        //Fix: The ResourceLocation file prefix must be changed depending on the operating system used to host the backend
        String osName = System.getProperty("os.name");
        String fileUrlPrefix = osName.toLowerCase().startsWith("win") ? "file:/" : "file://";
        
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations(fileUrlPrefix+ uploadPath + "/");
    }
	
}
