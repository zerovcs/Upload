package si.test.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();

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
		System.out.println("doPost invoked inside UploadServlet");
	    InputStream in = request.getInputStream();
	    byte[] buffer = new byte[BUFFER_SIZE];
	    FileOutputStream out = new FileOutputStream("D:\\development\\eclipse_4.3.2\\workspace\\html5-upload\\temp.jar");
//	    ByteArrayOutputStream out = new  ByteArrayOutputStream(BUFFER_SIZE);
	    try
	    {
			while (true) 
			{
				int byteCount = in.read(buffer);
				if (byteCount == -1) {
					break;
				}
				out.write(buffer, 0, byteCount);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
//				System.out.write(buffer, 0, byteCount);;
//				out.write(buffer, 0, byteCount);
				
			}
		}
	    finally 
	    {
			if (in != null) {
				in.close();
			}
			if(out != null)
				out.close();
		}		
		response.getWriter().write("Server responsed...");
	}

}
