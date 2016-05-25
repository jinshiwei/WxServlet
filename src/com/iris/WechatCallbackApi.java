package com.iris;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WechatCallbackApi
 */
public class WechatCallbackApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String TOKEN = "SushiWechatGame";   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WechatCallbackApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		String[] str = { TOKEN, timestamp, nonce };
		Arrays.sort(str);
		String bigStr = str[0] + str[1] + str[2];
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		if (digest.equals(signature)) {
				response.getWriter().print(echostr);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		 
		// 调用核心业务类接收消息、处理消息
		String respXml = new CoreService().processRequest(request);
		//String respXml = "Do something! "; 
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respXml);
		out.close();
	}

}
