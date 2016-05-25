package com.iris;

import com.iris.AccessToken;  
import com.iris.pojo.Button;  
import com.iris.pojo.CommonButton;  
import com.iris.pojo.ComplexButton;
import com.iris.pojo.ViewButton;
import com.iris.pojo.Menu;  
import com.iris.WeixinUtil;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory; 

public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        String appId = "wxd3f0a51aa6afc963";  
        // 第三方用户唯一凭证密钥  
        String appSecret = "07414338a5182331681a77f14897737d";  
        
        log.info("Entering Menu Manager... main@MenuManager.");
        
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
        
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
            log.info("Creating Menu...");
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！ main@MenuManager.");  
            else  
                log.info("菜单创建失败，错误码：" + result+ "  main@MenuManager.");  
        } else{
        	log.info("在创建菜单时获取 accesstoken 失败  main@MenuManager.");
        }
    }  
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("灰姑娘");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("丑小鸭");  
        btn12.setType("click");  
        btn12.setKey("12");  
     
        CommonButton btn21 = new CommonButton();  
        btn21.setName("寿司");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("字母");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        ViewButton btn31 = new ViewButton();  
        btn31.setName("搜索");  
        btn31.setType("view");  
        btn31.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("关于我们");  
        btn32.setType("click");  
        btn32.setKey("32");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("双语绘本");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("儿童游戏");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("更多");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
}  
