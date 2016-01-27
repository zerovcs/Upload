package si.test.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletReturnJson
 */
@WebServlet("/ServletReturnJson")
public class ServletReturnJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReturnJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ReturnObject returnObject = new ReturnObject();
		returnObject.x = "X value";
		returnObject.y = 123;
		returnObject.list = new ArrayList<>();
		returnObject.list.add("list item 1");
		returnObject.list.add("list item 2");
		returnObject.list.add("list item 3");
		returnObject.list.add("list item 4");


		response.getWriter().write("Hello from UploadFormDataServlet");
	}

	
	public static class ReturnObject
	{
		private String x;
		private int y;
		private List<String> list;
		
	}
}
