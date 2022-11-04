package com.hamilton.web;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.hamilton.bean.*;
import com.hamilton.dao.*;

@WebServlet("/")
public class MentCareSystemServlet extends HttpServlet{

    Connection connection;

    public void init() throws ServletException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/showSignIn":
                showSignIn(request,response);
                break;
            default:
                showSignIn(request,response);
                break;
        }
    }

    private void showSignIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("loginPage.jsp");
        dispatcher.forward(request,response);
    }

    // Does retrieval of common info for all users from registration
    // Do not delete this is still used in the other servlets
    public static User getUserInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Date birthdate = Date.valueOf(request.getParameter("birthdate"));
        String address = request.getParameter("address");
        String socialSecurityNumber = request.getParameter("SSN");
       return new User(name, email, password, birthdate, address, socialSecurityNumber);
    }

    // Does retrieval of common info for all users from registration
    // Do not delete this is still used in the other servlets
    public static User getUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String socialSecurityNumber = request.getParameter("SSN");
        return new User(name, email, password, null, address, socialSecurityNumber);
    }

}
