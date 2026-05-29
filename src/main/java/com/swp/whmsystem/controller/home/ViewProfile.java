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
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        int id = user.getId();
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        dto.setId(id);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);
        dto.setEmail(email);
        dto.setPhone(phone);


        boolean isSuccess = userDAO.updateProfile(dto);

        if (isSuccess) {
            User userUpdated = userDAO.getUserFullInformation(user.getId());
            session.setAttribute("user", userUpdated);
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
