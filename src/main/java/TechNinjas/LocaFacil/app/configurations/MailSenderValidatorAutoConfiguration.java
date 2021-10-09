package TechNinjas.LocaFacil.app.configurations;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

@Configuration
@AutoConfigureAfter(MailSenderAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.mail", value = "test-connection")
@ConditionalOnSingleCandidate(JavaMailSenderImpl.class)
public class MailSenderValidatorAutoConfiguration {

    private final JavaMailSenderImpl mailSender;

    public MailSenderValidatorAutoConfiguration(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void validateConnection() {
        try {
            this.mailSender.testConnection();
        }
        catch (MessagingException ex) {
            throw new IllegalStateException("Mail server is not available", ex);
        }
    }
}