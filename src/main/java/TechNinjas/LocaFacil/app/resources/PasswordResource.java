package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.dtos.EmailDTO;
import TechNinjas.LocaFacil.app.services.UsuarioService;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Api("PARTE DE RECUPERAÇÃO DE SENHA")
@RestController
@RequestMapping
public class PasswordResource {

    @Autowired
    private UsuarioService customerService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/defpassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("pageTitle","Forgot Password");
        return "forgot_password_form";
    }

    @ApiOperation(value = "Faz a verificação do email, caso existe um valido, ele manda então um email ao destinatario," +
            "para resetar a senha")
    @PostMapping(value = "/defpassword", consumes = "application/json")
    public ResponseEntity<Object> processForgotPasswordForm(HttpServletRequest request, HttpServletResponse response,
                                                            Model model,@RequestBody EmailDTO email) throws IOException {
        //String email = request.getParameter("email");
        //String token = RandomString.make(30);
        String email2 = email.getEmail();
        String password = RandomString.make(15);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        try{
            customerService.updateResetPasswordToken(encodedPassword, email2);
            String pass = password;
            //String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
//            sendEmail(email, resetPasswordLink);
            sendEmail(email2, pass);
            response.getWriter().write("Enviamos um link de redefinicao de senha para o seu e-mail. Por favor, verifique.");
            return ResponseEntity.status(HttpStatus.FOUND).build();
        } catch (CustomerNotFoundException ex) {
            response.getWriter().write(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MessagingException | IOException e) {
            response.getWriter().write("Erro ao enviar e-mail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendEmail(String email, String pass) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("locafacilcacambas@gmail.com", "LocaFacil Suporte");
        helper.setTo(email);

//        String subject = "Aqui está o link para resetar a senha";
//        String content = "<p>Olá,</p>"
//                + "<p>Você solicitou para redefinir sua senha.</p>"
//                + "<p>Clique no link abaixo para alterar sua senha:</p>"
//                + "<p><a href=\"" + resetPasswordLink + "\">Mudar minha senha</a></p>"
//                + "<br>"
//                + "<p>Ignore este e-mail se você se lembrar da sua senha, "
//                + "ou você não fez o pedido.</p>";

        String subject = "Aqui está o link para resetar a senha";
        String content = "<p>Olá,</p>"
                + "<p>Você esqueceu sua senha?</p>"
                + "<p>Aqui está uma senha temporaria para pode usar:</p>"
                + "<p>Senha: " + pass + "</p>"
                + "<br>"
                + "<p>Após entrar no sistema, você deve mudar sua senha para uma desejada"
                + "vá para (Alterar Senha).</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

//    @ApiOperation(value = "Faz a verificação do token")
//    @GetMapping("/reset_password")
//    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
//        Client client = (Client) customerService.getByResetPasswordToken(token);
//        model.addAttribute("token", token);
//
//        if (client == null) {
//            model.addAttribute("message", "Invalid Token");
//            return "message";
//        }
//
//        return "reset_password_form";
//    }
//
//    @PostMapping("/reset_password")
//    public String processResetPassword(HttpServletRequest request, Model model) {
//        String token = request.getParameter("token");
//        String password = request.getParameter("password");
//
//        Client client = (Client) customerService.getByResetPasswordToken(token);
//        model.addAttribute("title", "Reset your password");
//
//        if (client == null) {
//            model.addAttribute("message", "Invalid Token");
//            return "message";
//        } else {
//            customerService.updatePassword(client, password);
//
//            model.addAttribute("message", "You have successfully changed your password.");
//        }
//
//        return "message";
//    }
}
