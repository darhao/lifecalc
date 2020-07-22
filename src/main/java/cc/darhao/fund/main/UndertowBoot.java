package cc.darhao.fund.main;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;

import cc.darhao.fund.controller.LifeCounterController;

public class UndertowBoot extends JFinalConfig {
 
    public void configConstant(Constants me) {}
    public void configRoute(Routes me) {
    	me.add("/life", LifeCounterController.class);
    }
    public void configEngine(Engine me) {}
    public void configPlugin(Plugins me) {}
    public void configInterceptor(Interceptors me) {}
    public void configHandler(Handlers me) {}
    
}
