package com.iris;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
//import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.util.Date;
import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
import com.iris.message.resp.*;
import com.iris.MessageUtil;
import com.iris.MenuManager;

 
/*public class CoreService {
	
	public String processRequest(HttpServletRequest request){
		
		String resultStr="";
		String postStr=null;  
	    
		try{  
	    	
	    	postStr=this.readInputStreamParameter(request.getInputStream());  
	    
	    }catch(Exception e){  
	    
	    	e.printStackTrace();  
	    }  
	    
	    if (null!=postStr&&!postStr.isEmpty()){  
	    	
	    	Document document=null;  
	    	
	    	try{  
	    		document = DocumentHelper.parseText(postStr);  
	    	
	    	}catch(Exception e){  
	    		e.printStackTrace();  
	    	}  
	    	if(null==document){  
	    		  
	    		return resultStr;  
	    	}  
	    	
	    	Element root=document.getRootElement();  
	    	
	    	String fromUsername = root.elementText("FromUserName");  
	    	String toUsername = root.elementText("ToUserName");  
	    	String keyword = root.elementTextTrim("Content");
	    
	    	String time = new Date().getTime()+"";
	    	
	    	String textTpl = "<xml>"+  
	        "<ToUserName><![CDATA["+fromUsername+"]]></ToUserName>"+  
	        "<FromUserName><![CDATA["+toUsername+"]]></FromUserName>"+  
	        "<CreateTime>"+time+"</CreateTime>"; 
	        
	    	if(null!=keyword&&!keyword.equals("")&&keyword.equals("sushi")) {  
	    		resultStr = textTpl+
//	    				"<MsgType><![CDATA[link]]></MsgType>"+
//	    		        "<Title><![CDATA[Sushi Wechat Game]]></Title>"+
//	    		        "<Description><![CDATA[Sushi Wechat Game]]></Description>"+
//	    		        "<Url><![CDATA[http://sushiwechatgame.sinaapp.com/index.html]]></Url>";
	    				"<MsgType><![CDATA[text]]></MsgType>"+		
	    				"<Content><![CDATA[请点击下面连接，开始游戏：\n\n<a href=\"http://sushiwechatgame.sinaapp.com/index.html\">Sushi Game</a>]]></Content>";
	    				
	    	}else if(null!=keyword&&!keyword.equals("")){  
	    		resultStr = textTpl+
	    				"<MsgType><![CDATA[text]]></MsgType>"+		
	    				"<Content><![CDATA[No such keyword!]]></Content>";
	    	}else{
	    		resultStr = textTpl+
	    				"<MsgType><![CDATA[text]]></MsgType>"+		
	    				"<Content><![CDATA[No keyword!]]></Content>";
	    		
	    	}
	        
	       resultStr=resultStr+ "</xml>";  
	    
	    }
		
	    return resultStr;
	}
	
	public String readInputStreamParameter(ServletInputStream input){  
		  StringBuilder inputBuffer = new StringBuilder();  
		  BufferedReader inputReader=null;  
		  try{  
		    inputReader = new BufferedReader(new InputStreamReader(input));  
		    String line=null;  
		    while((line = inputReader.readLine())!=null){  
		    inputBuffer.append(line);  
		    }  
		  }catch(Exception e){  
		    e.printStackTrace();  
		  }finally{  
		    if(null!=inputReader){  
		    try {  
		      inputReader.close();  
		    } catch (IOException e) {  
		      e.printStackTrace();  
		    }  
		    }  
		  }  
		  return inputBuffer.toString();  
		}  
	
	
}*/

public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	private static Logger log = LoggerFactory.getLogger(CoreService.class); 
	
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			String keyword = requestMap.get("Content");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			log.info("sending response... processRequest@CoreService.");
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				
				if(null!=keyword&&!keyword.equals("")&&keyword.equals("sushi")) {
					
					log.info("Entering Sushi Game... processRequest@CoreService.");
					/*LinkMessage linkMessage=new LinkMessage();
					linkMessage.setToUserName(fromUserName);
					linkMessage.setFromUserName(toUserName);
					linkMessage.setCreateTime(new Date().getTime());
					linkMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_LINK);
					linkMessage.setDescription("Sushi Wechat Game");
					linkMessage.setUrl("http://sushiwechatgame.sinaapp.com/index.html");
					linkMessage.setFuncFlag(0);
					
					respMessage = MessageUtil.linkMessageToXml(linkMessage);*/
					
					// 创建图文消息  
	                NewsMessage newsMessage = new NewsMessage();  
	                newsMessage.setToUserName(fromUserName);  
	                newsMessage.setFromUserName(toUserName);  
	                newsMessage.setCreateTime(new Date().getTime());  
	                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
	                newsMessage.setFuncFlag(0);  
	  
	                List<Article> articleList = new ArrayList<Article>();  
	                // 单图文消息  
	                  
                    Article article = new Article();  
                    article.setTitle("Sushi Wechat Game");  
                    article.setDescription("Sushi Wechat Game");  
                    article.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/S6D2F4A3micd8c894SujFUnicKuLka1gz3icM6jsS2gVtxDb1uibEnaghZ7lA8WmWtwj5iaymFFk20D9Tia6jQIvbFUQ/0?wx_fmt=jpeg");  
                    article.setUrl("http://sushiwechatgame.sinaapp.com/index.html");  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
	                
					return respMessage;
					
				}else{
					respContent = "您发送的是文本消息！";
				}
					
				
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					log.info("First time follow. processRequest@CoreService.");
					respContent = "谢谢您关注Fun&Good English Learning！";
					
					MenuManager menuManager= new MenuManager();
					menuManager.main(null);
				
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
					String eventKey = requestMap.get("EventKey");  
					  
                    if (eventKey.equals("11")) {  
                        respContent = "灰姑娘菜单项被点击！";  
                    } else if (eventKey.equals("12")) {  
                        respContent = "丑小鸭菜单项被点击！";  
                    } else if (eventKey.equals("21")) {  
                        respContent = "寿司菜单项被点击！";  
                    } else if (eventKey.equals("22")) {  
                        respContent = "字母菜单项被点击！";  
                    } else if (eventKey.equals("31")) {  
                        respContent = "搜索菜单项被点击！";  
                    } else if (eventKey.equals("32")) {  
                        respContent = "关于我们菜单项被点击！";  
                    }  
				}
			}

			textMessage.setContent(respContent);
			log.info(respContent+"  processRequest@CoreService.");
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
