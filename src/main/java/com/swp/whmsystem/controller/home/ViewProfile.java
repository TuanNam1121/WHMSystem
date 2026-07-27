package com.swp.whmsystem.controller.home;

import java.io.IOException;

import com.swp.whmsystem.utils.InputValidationUtil;

import com.swp.whmsystem.dal.UserDAO;
import com.swp.whmsystem.model.User;
import com.swp.whmsystem.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        request.getRequestDispatcher("WEB-INF/view/user/profile.jsp").forward(request, response);
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
        if (firstname == null || firstname.trim().isEmpty()) {
            firstname = user.getFirstname();
        } else {
            firstname = firstname.trim().replaceAll("\\s+", " ");
            if (firstname.length() > 50 || !InputValidationUtil.isFirstName(firstname)) {
                session.setAttribute("error", "Firstname is not valid or exceeds 50 characters");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
        }

        String lastname = request.getParameter("lastname");
        if (lastname == null || lastname.trim().isEmpty()) {
            lastname = user.getLastname();
        } else {
            lastname = lastname.trim();
            if (lastname.length() > 50 || !InputValidationUtil.isLastName(lastname)) {
                session.setAttribute("error", "Lastname is not valid or exceeds 50 characters");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            email = user.getEmail();
        } else {
            email = email.trim();
            if (email.length() > 100 || !InputValidationUtil.isEmail(email)) {
                session.setAttribute("error", "Email is not valid or exceeds 100 characters");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
            if (userDAO.existsByEmailExceptUserId(email, id)) {
                session.setAttribute("error", "Email already exists");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
        }

        String phone = request.getParameter("phone");
        if (phone == null || phone.trim().isEmpty()) {
            phone = user.getPhone();
        } else {
            phone = phone.trim();
            if (phone.length() > 20 || !InputValidationUtil.isPhone(phone)) {
                session.setAttribute("error", "Phone is not valid");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
            if (userDAO.existsByPhoneExceptUserId(phone, id)) {
                session.setAttribute("error", "Phone already exists");
                request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
                return;
            }
        }

        dto.setId(id);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);
        dto.setEmail(email);
        dto.setPhone(phone);


        boolean isSuccess = userDAO.updateProfile(dto);

        if (isSuccess) {
            User userUpdated = userDAO.getUserFullInformation(user.getId());
            session.setAttribute("user", userUpdated);
            session.setAttribute("successMessage", "Update successfully");
        } else {
            session.setAttribute("error", "Update failed");
        }
        response.sendRedirect("viewprofile");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
