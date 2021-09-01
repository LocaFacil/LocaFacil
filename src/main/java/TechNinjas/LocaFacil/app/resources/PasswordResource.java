package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.configurations.Utility;
import TechNinjas.LocaFacil.app.services.UsuarioService;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping
public class PasswordResource {

    @Autowired
    private UsuarioService customerService;

    @Autowired
    private JavaMailSender mailSender;

//    @Bean
//    public JavaMailSenderImpl mailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setProtocol("SMTP");
//        javaMailSender.setHost("127.0.0.1");
//        javaMailSender.setPort(25);
//
//        return javaMailSender;
//    }

    @GetMapping("/defpassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("pageTitle","Forgot Password");
        return "forgot_password_form";
    }

    @PostMapping("/defpassword")
    public String processForgotPasswordForm(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try{
            customerService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/defpassword?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (CustomerNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "forgot_password_form";
    }

    private void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("locafacilcacambas@gmail.com", "LocaFacil Suporte");
        helper.setTo(email);

        String subject = "Aqui está o link para resetar a senha";
        String content = "<p>Olá,</p>"
                + "<p>Você solicitou para redefinir sua senha.</p>"
                + "<p>Clique no link abaixo para alterar sua senha:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Mudar minha senha</a></p>"
                + "<br>"
                + "<p>Ignore este e-mail se você se lembrar da sua senha, "
                + "ou você não fez o pedido.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
