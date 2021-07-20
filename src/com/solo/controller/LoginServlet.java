package com.solo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.solo.dao.S_EmployeesDAO;
import com.solo.dto.S_EmployeesVO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String lev=request.getParameter("lev");
		String url=null;
		
		S_EmployeesDAO S_empdao=S_EmployeesDAO.getInstance();
		int result = S_empdao.userCheck(id,pwd,lev);
		
		if(result == 2 || result == 3) {
			S_EmployeesVO emp = new S_EmployeesVO();
			emp=S_empdao.getMember(id);
			
			HttpSession session=request.getSession();
			session.setAttribute("loginUser", emp);
			session.setAttribute("result", result);
			
			url="main.jsp";
		}else {
			if(result == 1) {
				request.setAttribute("message", "로그인 실패 : 등급이 맞지 않습니다.");
			}else if(result == 0) {
				request.setAttribute("message", "로그인 실패 : 비밀번호가 맞지 않습니다.");
			}else if(result == -1) {
				request.setAttribute("message", "로그인 실패 : 아이디가 맞지 않습니다.");
			}
			url = "login.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}































