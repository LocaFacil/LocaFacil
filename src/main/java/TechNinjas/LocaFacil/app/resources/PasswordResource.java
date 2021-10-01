package TechNinjas.LocaFacil.app.resources;

import TechNinjas.LocaFacil.app.models.dtos.EmailDTO;
import TechNinjas.LocaFacil.app.services.ClientService;
import TechNinjas.LocaFacil.app.services.CompanyService;
import TechNinjas.LocaFacil.app.services.exceptions.CustomerNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    private ClientService customerService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/defpassword")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("pageTitle","Forgot Password");
        return "forgot_password_form";
    }

    @ApiOperation(value = "Reset password for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reseted password"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature"),
            @ApiResponse(code = 500, message = "An exception was generated"),
    })
    @PostMapping(value = "/defpassword", consumes = "application/json")
    public ResponseEntity<Object> clientForgotPassword(HttpServletRequest request, HttpServletResponse response,
                                                            Model model,@RequestBody EmailDTO email) throws IOException {
        String email2 = email.getEmail();
        String password = RandomString.make(15);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        try{
            customerService.updateResetPasswordToken(encodedPassword, email2);
            String pass = password;
            sendEmail(email2, pass);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException ex) {
            response.getWriter().write(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MessagingException | IOException e) {
            response.getWriter().write("Error sending email");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void sendEmail(String email, String pass) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("locafacilcacambas@gmail.com", "LocaFacil Suporte");
        helper.setTo(email);
        String subject = "Aqui está o link para resetar a senha";
        String content = "<p>Olá,</p>"
                + "<p>Você esqueceu sua senha?</p>"
                + "<p>Aqui está uma senha temporaria para poder usar:</p>"
                + "<p>Senha: <h2><font color=\"#fc9722\">" + pass + "</font></h2></p>"
                + "<br>"
                + "<p>Após entrar no sistema, você deve mudar sua senha para uma senha desejada, "
                + "vá para (Alterar Senha).</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
