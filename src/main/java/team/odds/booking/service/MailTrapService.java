package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.threeten.bp.format.DateTimeFormatter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import team.odds.booking.model.Booking;
import team.odds.booking.util.HelpersUtil;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailTrapService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public void mailToUser(Booking booking) throws MessagingException, UnsupportedEncodingException {
        String expireDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getCreatedAt().plusDays(1));
        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo(booking.getEmail());
        mailComposeParts.setSubject("กรุณายืนยันอีเมลล์");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-e"));

        var templateCTX = new Context(LocaleContextHolder.getLocale());
        templateCTX.setVariable("fullName", booking.getFullName());
        templateCTX.setVariable("expiryDate", expireDateFormat);
        templateCTX.setVariable("id", booking.getId());

        String mailContent = this.templateEngine.process("user_confirm", templateCTX);
        mailComposeParts.setText(mailContent, true);

        mailSender.send(mailCompose);

    }

    public void mailToOdds(Booking booking) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mailCompose = this.mailSender.createMimeMessage();
        var mailComposeParts = new MimeMessageHelper(mailCompose, true, "UTF-8");
        mailComposeParts.setTo("roof@odds.team");
        mailComposeParts.setSubject("รายละเอียดการจอง");
        mailComposeParts.setFrom(new InternetAddress("odds.molamola@gmail.com", "odds-e"));

        var templateCTX = new Context(LocaleContextHolder.getLocale());
        templateCTX.setVariable("fullName", booking.getFullName());
        templateCTX.setVariable("email", booking.getEmail());
        templateCTX.setVariable("phoneNumber", booking.getPhoneNumber());
        templateCTX.setVariable("room", booking.getRoom());
        templateCTX.setVariable("reason", booking.getReason());
        templateCTX.setVariable("startDate",HelpersUtil.dateTimeFormatGeneral(booking.getStartDate()));
        templateCTX.setVariable("endDate", HelpersUtil.dateTimeFormatGeneral(booking.getEndDate()));

        templateCTX.setVariable("id", booking.getId());

        String mailContent = this.templateEngine.process("odds_confirm", templateCTX);
        mailComposeParts.setText(mailContent, true);

        mailSender.send(mailCompose);

    }
}
