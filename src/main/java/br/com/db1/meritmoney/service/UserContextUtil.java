package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextUtil {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

}
