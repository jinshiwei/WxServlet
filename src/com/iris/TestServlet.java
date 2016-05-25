package com.iris;
//commit here
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter; 

import java.sql.*;
import com.sina.sae.util.SaeUserInfo;


/**
 * Servlet implementation class TestServlet
 */


public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
        //ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out=response.getWriter(); 
		String origin="Web";
		
		try {
            // 获得表单数据
            String action = request.getParameter("Action");
			String name = request.getParameter("Name");
			String score = request.getParameter("Score");
			origin = request.getParameter("Origin");
			
			if (origin.equals("Web")){
				out.println("<html><body>"); 
			}
			
            // 建立数据库连接
            Connection conn = getConn();
            String sql="";
            PreparedStatement pstmt=null;
            
            // 定义查询语句
            if(action.equals("Check")){
            	
            	//out.println("Check: ");
            	
	            sql = "select * from Test where `name`=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, name);
	            
	            //out.println(sql); 
	            
	            // 查询获得结果集
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	            	if (origin.equals("Web")){
	            		out.append("Your score is: ").append(Integer.toString(rs.getInt("score"))).append(". ");
	            	}else{
	            		out.append("{\"status\":\"1\"}");
	            	}
	            }else{
	            	if (origin.equals("Web")){
	            		out.append("Don't have your score!");
	            	}else{
	            		out.append("{\"status\":\"0\"}");
	            	}
	            }
	            rs.close();
	            pstmt.close();
	            conn.close();
	            
            } else if (action.equals("Update")){
            	
            	//out.println("Update: ");
            	
            	sql = "select * from Test where `name`=?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, name);
	                        
	            // 查询获得结果集
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	            	//Find it
	                
	                rs.close();
		            pstmt.close();
		            
		            int i=0;
		            String sql0 = "update Test set score="+score+" where name='"+name+"'";
		            pstmt = conn.prepareStatement(sql0);
		            
		           
		            i=pstmt.executeUpdate(sql0); //update the record
		            if (origin.equals("Web")){
		            	out.append("Your score is: ").append(score).append(" and updated. ");
		            }else{
	            		out.append("{\"status\":\"1\"}");
	            	}
		            pstmt.close();
		            
	            }else{
	            	//cann't find the record
	            	int id=0;
	            	pstmt.close();
	            	
	            	sql = "select max(id) from Test";
		            pstmt = conn.prepareStatement(sql);
		            // check for the max id number
		            
		            rs = pstmt.executeQuery();
		            if (rs.next()) {
		            	id=rs.getInt("max(id)");
		            }
		            
		            rs.close();
			        pstmt.close();
	            	
			        //Insert a new one.
			        String sql1="";
			        if (origin.equals("Web")){
			        	sql1 = "Insert into Test(ID, Name, Score, Origin) Values("+String.valueOf(id+1)+",'"+name+"',"+score+",'Web')";
		            }else{
		            	sql1 = "Insert into Test(ID, Name, Score, Origin) Values("+String.valueOf(id+1)+",'"+name+"',"+score+",'Wechat')";
	            	}
	            	
		            pstmt = conn.prepareStatement(sql1);
		            
		            int i=0;
		            
		            i=pstmt.executeUpdate(sql1);//insert the record
		            
		            if (origin.equals("Web")){
		            	out.append("Your score is: ").append(score).append(" and inserted. ");
		            }else{
	            		out.append("{\"status\":\"1\"}");
	            	}
		            pstmt.close();
	            	
	            }
	            
	            conn.close();
            }
            
            
        
        } catch (SQLException e) {
            // TODO Auto-generated catch block
             e.printStackTrace();
        }
		if (origin.equals("Web")){
			out.println("</body></html>");
		}
        // 关闭结果集，查询语句，数据库连接
		out.flush();
	}
 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    //String url="jdbc:mysql://localhost:3307/Test";
	    String url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_sushiwechatgame";
	    //String username = "5lwww3xjkx";
	    //String password = "453kj2145ykwkhjkw1ik2ij4lyim1w4wmxmw2mly";
	    String username=SaeUserInfo.getAccessKey();
	    String password=SaeUserInfo.getSecretKey();
	    //String username = "root";
	    //String password = "";
	    
	    Connection conn = null;
	    try {
	        //Class.forName(driver); //classLoader,加载对应驱动
	        Class.forName(driver).newInstance();
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	        //conn = (Connection) DriverManager.getConnection("jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_sushiwechatgame", "5lwww3xjkx", "453kj2145ykwkhjkw1ik2ij4lyim1w4wmxmw2mly");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    
	    }
	    return conn;
	}
	
}


