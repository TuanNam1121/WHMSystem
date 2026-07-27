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

@WebServlet(name = "viewProfile", urlPatterns = {"/viewProfile"})
public class ViewProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        // neu de empty thi giu ten cu
        if (firstname == null || firstname.trim().isEmpty()) {
            firstname = user.getFirstname();
        } else if (!InputValidationUtil.isFirstName(firstname)) { // co nhap thi check format
            session.setAttribute("error", "Firstname is not valid");
            request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
            return;
        } else {
            firstname = firstname.trim().replaceAll("\\s+", " ");
        }

        String lastname = request.getParameter("lastname");
        if (lastname == null || lastname.trim().isEmpty()) {
            lastname = user.getLastname();
        } else if (!InputValidationUtil.isLastName(lastname)) {
            session.setAttribute("error", "Lastname is not valid");
            request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
            return;
        } else {
            lastname = lastname.trim();
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            email = user.getEmail();
        } else if (!InputValidationUtil.isEmail(email)) {
            session.setAttribute("error", "Email is not valid");
            request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
            return;
        }

        String phone = request.getParameter("phone");
        if (phone == null || phone.trim().isEmpty()) {
            phone = user.getPhone();
        } else if (!InputValidationUtil.isPhone(phone)) {
            session.setAttribute("error", "Phone is not valid");
            request.getRequestDispatcher("/WEB-INF/view/user/profile.jsp").forward(request, response);
            return;
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
