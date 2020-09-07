package pl.akjos.training_manager.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().replace("[", "").replace("]", "");
    }
}
