package belaquaa.useredit.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex, Model model) {
        model.addAttribute("errorMessage", "A data integrity error occurred. Please verify your inputs and try again.");
        return "error";
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, SQLException ex, Model model) {
        model.addAttribute("errorMessage", "A database error occurred. Please try again later.");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "error";
    }
}