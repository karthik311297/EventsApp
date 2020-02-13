

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeSevlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		PrintWriter printwriter=response.getWriter();
//		printwriter.println("SUCCESS");
		if(request.getParameter("whichurl").equals("AssociateLiscenceServlet")){
			RequestDispatcher requestdispatcher=request.getRequestDispatcher("/AssociateLiscenceServlet");
			requestdispatcher.forward(request, response);
		}
		if(request.getParameter("whichurl").equals("DissociateLiscenceServlet")){
			RequestDispatcher requestdispatcher=request.getRequestDispatcher("/AssociateLiscenceServlet");
			requestdispatcher.forward(request, response);
		}
		
		
		
//		RequestDispatcher requestdispatcher=request.getRequestDispatcher("/Action.html");
//		requestdispatcher.include(request, response);
		
	}



}
