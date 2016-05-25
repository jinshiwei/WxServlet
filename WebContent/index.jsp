<?xml version="1.0" encoding="ISO-8859-1"?> 
<!DOCTYPE web-app 
PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" 
"http://java.sun.com/dtd/web-app_2_3.dtd"> 
<html>
<body>
<CENTER>Rank Checking</CENTER>
<form action="/TestServlet" method="GET">
<input type="hidden" name="Action" Value="Check" />
Name:
<input type="text" name="Name"/><br/>
<input type="hidden" name="Origin" Value="Web" />
<input type="submit" value="Check"/>
</form>
<CENTER>Rank Update</CENTER>
<form action="/TestServlet" method="GET">
<input type="hidden" name="Action" Value="Update" />
Name:
<input type="text" name="Name"/><br/>
Score:
<input type="text" name="Score"/><br/>
<input type="hidden" name="Origin" Value="Web" />
<input type="submit" value="Update"/>
</form>
</body>
</html>