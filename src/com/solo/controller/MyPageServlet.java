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
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/mypage.do")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		S_EmployeesVO emp =(S_EmployeesVO)session.getAttribute("loginUser");
		if(emp != null) {
			String url = "myPage.jsp";
			
			RequestDispatcher dispatcher=request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("login.do");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		request.setCharacterEncoding("utf-8");
		
		System.out.println(request.getParameter("name"));
		HttpSession session = request.getSession();
		
		S_EmployeesVO member = new S_EmployeesVO();
		
		member.setId(request.getParameter("id"));
		member.setPass(request.getParameter("pwd"));
		member.setName(request.getParameter("name"));
		member.setLev(request.getParameter("lev"));
		member.setGender(request.getParameter("gender"));
		member.setPhone(request.getParameter("phone"));
		
		S_EmployeesDAO edao=S_EmployeesDAO.getInstance();
		edao.updateMember(member);
	
		S_EmployeesVO emp = edao.getMember(member.getId());
		request.setAttribute("member", emp);
		request.setAttribute("message", "회원 정보가 수정 되었습니다.");
		
		session.setAttribute("loginUser", emp);
		
		System.out.println(emp);
		
		int result = edao.userCheck(member.getId(),member.getPass(),member.getLev());
		session.setAttribute("result", result);
		
		String url="JoinSuccess.jsp";
		RequestDispatcher dispatcher=request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
	
	}

}
