package pl.akjos.training_manager.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainErrorController implements ErrorController {


    @RequestMapping("/error")
    public String setError(Model model, HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "Page not found";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Some goes wrong with server or data base";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "You don't have access to this page";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "/error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
