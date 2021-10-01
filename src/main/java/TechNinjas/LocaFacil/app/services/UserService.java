package TechNinjas.LocaFacil.app.services;

import TechNinjas.LocaFacil.app.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        //O erro bate aqui na exception, na causa diz que tem algo haver com bootstrap
        catch (Exception e) {
            return null;
        }
    }
}
