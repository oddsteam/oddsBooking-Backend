package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.odds.booking.model.Booking;
import team.odds.booking.util.HelpersUtil;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void mailToUser(Booking booking) throws MessagingException, UnsupportedEncodingException {
        String expireDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getCreatedAt().plusDays(1));
        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo(booking.getEmail());
        mailComposeParts.setSubject("กรุณายืนยันอีเมลล์");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-e"));

        var templateCtx = new Context(LocaleContextHolder.getLocale());
        templateCtx.setVariable("id", booking.getId());
        templateCtx.setVariable("fullName", booking.getFullName());
        templateCtx.setVariable("expiryDate", expireDateFormat);

        String mailContent = this.templateEngine.process("user_confirm", templateCtx);
        mailComposeParts.setText(mailContent, true);
        mailSender.send(mailCompose);
    }

    public void mailToOdds(Booking booking) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo("dome@odds.team");
        mailComposeParts.setSubject("รายละเอียดการจอง");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-e"));

        var templateCtx = new Context(LocaleContextHolder.getLocale());
        templateCtx.setVariable("id", booking.getId());
        templateCtx.setVariable("fullName", booking.getFullName());
        templateCtx.setVariable("email", booking.getEmail());
        templateCtx.setVariable("phoneNumber", booking.getPhoneNumber());
        templateCtx.setVariable("room", booking.getRoom());
        templateCtx.setVariable("reason", booking.getReason());
        templateCtx.setVariable("startDate",HelpersUtil.dateTimeFormatGeneral(booking.getStartDate()));
        templateCtx.setVariable("endDate", HelpersUtil.dateTimeFormatGeneral(booking.getEndDate()));

        String mailContent = this.templateEngine.process("odds_confirm", templateCtx);
        mailComposeParts.setText(mailContent, true);
        mailSender.send(mailCompose);
    }
}
