package com.swp.whmsystem.controller.home;

import java.io.IOException;
import java.io.PrintWriter;

import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.model.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import com.swp.whmsystem.utils.*;

public class ViewProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        UserDTO dto = new UserDTO();
        InputValidationUtil utils = new InputValidationUtil();
        User user = (User) session.getAttribute("user");


        int id = user.getId();
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String passwordRaw = request.getParameter("password");

        dto.setId(id);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setUsername(username);


        boolean isSuccess = userDAO.updateProfile(dto);

        if (isSuccess) {
            request.setAttribute("successMessage", "Update successfully");
            response.sendRedirect("viewprofile");
        } else {
            request.setAttribute("successMessage", "Update failed");
            response.sendRedirect("viewprofile");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
