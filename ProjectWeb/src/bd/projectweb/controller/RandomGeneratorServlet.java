package bd.projectweb.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bd.projectweb.model.QuestionModel;
import bd.spring.RandomGenerator;

/**
 * Controller servlet for the Random Generator function.
 */
@WebServlet(description = "RandomGenerator controller servlet", urlPatterns = { "/RandomGenerator" })
public class RandomGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomGeneratorServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doInvoke(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doInvoke(request, response);
	}
	
	/**
	 * Process an invocation of the servlet.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doInvoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RandomGenerator randomGenerator = getRandomGenerator();
		QuestionModel questionBean = new QuestionModel();
		
		// TODO questionIndex validation
		String questionIndex = request.getParameter("questionIndex");
		if(isInteger(questionIndex)) {
			// get a specific question
			questionBean.setQuestionText(randomGenerator.getSpecificQuestion(Integer.parseInt(questionIndex)));
			questionBean.setIndex(Integer.parseInt(questionIndex));
		} else if(questionIndex == null) {
			// generate a random quesiton
			questionBean.setQuestionText(randomGenerator.getRandomQuestion());
		} else {	
			// invalid question index
			questionBean.setQuestionText("The questionIndex parameter must be a valid integer");
		}
		
		request.setAttribute("questionBean", questionBean);
		
		RequestDispatcher view = request.getRequestDispatcher("RandomQuestion.jsp");
		view.forward(request,  response);
	}
	
	/**
	 * Check if a string is an integer.
	 * @param str
	 * @return
	 */
	private boolean isInteger(String str) {
		boolean isInteger = true;
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException nfe) {
			isInteger = false;
		}
		return isInteger;
	}
	
	/**
	 * Obtain an instance of the RandomGenerator from the Spring application context.
	 * @return
	 */
	private RandomGenerator getRandomGenerator() {
		WebApplicationContext rootContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		return (RandomGenerator)rootContext.getBean("RandomGeneratorBean");
	}	

}
