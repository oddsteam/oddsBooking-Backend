package team.odds.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import team.odds.booking.model.Booking;
import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;

import java.util.*;

import sendinblue.*;
import sendinblue.auth.*;
import sibModel.*;
import team.odds.booking.util.HelpersUtil;

@Service
@Slf4j
public class MailSendinblueService {

    @Value("${sendinblue.token}")
    private String sendinblueToken;

    public void mailToUser(Booking booking) {
        String expiryDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getCreatedAt().plusDays(1));

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(sendinblueToken);
        System.out.println(sendinblueToken);

        var sendFrom = new SendSmtpEmailSender();
        sendFrom.setEmail("odds.molamola@gmail.com");
        sendFrom.setName("Odds Booking");

        var sendTo = new SendSmtpEmailTo();
        sendTo.setEmail(booking.getEmail());
        sendTo.setName(booking.getFullName());

        var replyTo = new SendSmtpEmailReplyTo();
        replyTo.setEmail("odds.molamola@gmail.com");
        replyTo.setName("Odds Booking");

        var templateCtx = new Properties();
        templateCtx.setProperty("fullName", booking.getFullName());
        templateCtx.setProperty("expiredDate", expiryDateFormat);
        templateCtx.setProperty("id", booking.getId());

        var mailCompose = new SendSmtpEmail();
        mailCompose.sender(sendFrom);
        mailCompose.replyTo(replyTo);
        mailCompose.to(List.of(sendTo));
        mailCompose.setParams(templateCtx);
        mailCompose.templateId(7L);

        var api = new TransactionalEmailsApi();
        var messageId = "";
        try {
            CreateSmtpEmail res = api.sendTransacEmail(mailCompose);
            messageId = res.getMessageId();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        log.info(messageId);
    }

    public void mailToOdds(Booking booking) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        String startDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getStartDate());
        String endDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getEndDate());

        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(sendinblueToken);

        var sendFrom = new SendSmtpEmailSender();
        sendFrom.setEmail("odds.molamola@gmail.com");
        sendFrom.setName("Odds Booking");

        SendSmtpEmailTo sendTo = new SendSmtpEmailTo();
        sendTo.setEmail("roof@odds.team");
        sendTo.setName("Professional ROV player P'Roof");

        var replyTo = new SendSmtpEmailReplyTo();
        replyTo.setEmail("odds.molamola@gmail.com");
        replyTo.setName("Odds Booking");

        var templateCtx = new Properties();
        templateCtx.setProperty("id", booking.getId());
        templateCtx.setProperty("fullName", booking.getFullName());
        templateCtx.setProperty("email", booking.getEmail());
        templateCtx.setProperty("phoneNumber", booking.getPhoneNumber());
        templateCtx.setProperty("room", booking.getRoom());
        templateCtx.setProperty("reason", booking.getReason());
        templateCtx.setProperty("startDate", startDateFormat);
        templateCtx.setProperty("endDate", endDateFormat);

        var mailCompose = new SendSmtpEmail();
        mailCompose.sender(sendFrom);
        mailCompose.replyTo(replyTo);
        mailCompose.to(List.of(sendTo));
        mailCompose.setParams(templateCtx);
        mailCompose.templateId(8L);

        var api = new TransactionalEmailsApi();
        var messageId = "";
        try {
            CreateSmtpEmail res = api.sendTransacEmail(mailCompose);
            messageId = res.getMessageId();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
        log.info(messageId);
    }
}
