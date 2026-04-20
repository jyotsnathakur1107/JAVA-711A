package com.passwordgen.controller;

import com.passwordgen.dao.PasswordDAO;
import com.passwordgen.model.PasswordEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

@WebServlet("/generate")
public class PasswordGeneratorServlet extends HttpServlet {

    private PasswordDAO passwordDAO;

    @Override
    public void init() {
        passwordDAO = new PasswordDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Fetch history and show page
        List<PasswordEntry> history = passwordDAO.getAllPasswords();
        request.setAttribute("history", history);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String lengthStr = request.getParameter("length");
        boolean incUppercase = request.getParameter("uppercase") != null;
        boolean incNumbers = request.getParameter("numbers") != null;
        boolean incSymbols = request.getParameter("symbols") != null;
        String website = request.getParameter("website");

        int length = 12; // default
        if (lengthStr != null && !lengthStr.trim().isEmpty()) {
            try {
                length = Integer.parseInt(lengthStr);
            } catch (NumberFormatException e) {
                length = 12;
            }
        }

        String generatedPassword = generatePassword(length, incUppercase, incNumbers, incSymbols);

        if (website != null && !website.trim().isEmpty()) {
            PasswordEntry entry = new PasswordEntry(website, generatedPassword);
            passwordDAO.savePassword(entry);
        }

        request.setAttribute("generatedPassword", generatedPassword);
        
        // Fetch history updated with the new one
        List<PasswordEntry> history = passwordDAO.getAllPasswords();
        request.setAttribute("history", history);
        
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private String generatePassword(int length, boolean useUpper, boolean useNumbers, boolean useSymbols) {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        StringBuilder characterPool = new StringBuilder(lower);
        if (useUpper) characterPool.append(upper);
        if (useNumbers) characterPool.append(numbers);
        if (useSymbols) characterPool.append(symbols);

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return password.toString();
    }
}
