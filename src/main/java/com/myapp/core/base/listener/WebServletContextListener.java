package com.myapp.core.base.listener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 * 初始化应用中的一些全局参数
 *-----------MySong---------------
 */
public class WebServletContextListener implements ServletContextListener {
	private static final Logger log = LogManager.getLogger(WebServletContextListener.class);
	public static final String CONFIG_APP_PARAM = "myapp";
	public void contextInitialized(ServletContextEvent sce) {
		log.info("webContext begin init !!!");
		ServletContext ctx = sce.getServletContext();  
		String ctxParam = ctx.getInitParameter(CONFIG_APP_PARAM);
		if(!BaseUtil.isEmpty(ctxParam)&&StringUtils.hasText(ctxParam)){
			try {
				File file = ResourceUtils.getFile(ctxParam);
				Properties params = new Properties();
				InputStream in = new BufferedInputStream (new FileInputStream(file));
				params.load(in);
				Iterator<String> it= params.stringPropertyNames().iterator();
				while(it.hasNext()){
					String key=it.next();
					String val = BaseUtil.strTransUtfCode(params.getProperty(key),null);
					ctx.setAttribute(key, val);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("webContext init myapp's properties file is not find!!!");
			} catch (IOException e) {
				e.printStackTrace();
				log.error("webContext init load properties file error !!!");
			}
		}
//		context.getInitParameter(name)
	}

	public void contextDestroyed(ServletContextEvent sce) {
		log.info("webContext has shot down!!!");
	}

}
