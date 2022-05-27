package team.odds.booking.service;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.odds.booking.model.Booking;
import team.odds.booking.util.HelperUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Service
public record MailSenderService(JavaMailSender mailSender, TemplateEngine templateEngine) {

    public void mailToUser(String confirmUrl, Booking booking)
            throws MessagingException, UnsupportedEncodingException {
        String expiryDateFormat = HelperUtils.dateTimeFormatGeneral(booking.getCreatedAt().plusDays(1));

        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo(booking.getEmail());
        mailComposeParts.setSubject("กรุณายืนยันอีเมล์");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-booking"));

        var templateCtx = new Context(LocaleContextHolder.getLocale());
        templateCtx.setVariable("fullName", booking.getFullName());
        templateCtx.setVariable("expiryDate", expiryDateFormat);
        templateCtx.setVariable("confirmUrl", confirmUrl);

        String mailContent = this.templateEngine.process("user_confirm", templateCtx);
        mailComposeParts.setText(mailContent, true);

        mailSender.send(mailCompose);
    }

    public void mailToOdds(String bookingDetailsUrl, Booking booking)
            throws MessagingException, UnsupportedEncodingException, IllegalAccessException {

        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo("phum.project@gmail.com");
        mailComposeParts.setSubject("รายละเอียดการจอง");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-booking"));

        var templateCtx = new Context(LocaleContextHolder.getLocale());
        for (Field field : booking.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            boolean isDateTime = field.get(booking).getClass().getSimpleName().equals("LocalDateTime");
            templateCtx.setVariable(field.getName(),
                    isDateTime ?
                            HelperUtils.dateTimeFormatGeneral(LocalDateTime.parse((field.get(booking)).toString()))
                            : field.get(booking));
        }
        templateCtx.setVariable("bookingDetailsUrl", bookingDetailsUrl);

        String mailContent = this.templateEngine.process("odds_confirm", templateCtx);
        mailComposeParts.setText(mailContent, true);

        mailSender.send(mailCompose);
    }
}
