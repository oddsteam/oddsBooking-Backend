package team.odds.booking.service;

import team.odds.booking.constants.MailSender;
import team.odds.booking.model.Booking;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sibApi.TransactionalEmailsApi;
import team.odds.booking.model.Booking;
import team.odds.booking.util.HelpersUtil;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import sendinblue.*;
import sendinblue.auth.*;
import sibModel.*;
import sibApi.AccountApi;

import java.io.File;
import java.util.*;

@Service
public class MailSendinblueService implements MailSender {

    @Override
    public boolean mailToUser(Booking booking) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();

            // Configure API key authorization: api-key
            ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKey.setApiKey("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");
            // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
            //apiKey.setApiKeyPrefix("Token");

            // Configure API key authorization: partner-key
            ApiKeyAuth partnerKey = (ApiKeyAuth) defaultClient.getAuthentication("partner-key");
            partnerKey.setApiKey("YOUR PARTNER KEY");
            // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
            partnerKey.setApiKeyPrefix("Token");

            AccountApi apiInstance = new AccountApi();
            TransactionalEmailsApi api = new TransactionalEmailsApi();

            SendSmtpEmailSender sendSmtpEmailSender = new SendSmtpEmailSender();
            sendSmtpEmailSender.setEmail("odds.molamola@gmail.com");
            sendSmtpEmailSender.setName("Odds Booking");

            SendSmtpEmailReplyTo sendSmtpEmailReplyTo = new SendSmtpEmailReplyTo();
            sendSmtpEmailReplyTo.setEmail("odds.molamola@gmail.com");
            sendSmtpEmailReplyTo.setName("Odds Booking");

            SendSmtpEmailTo sendSmtpEmailTo = new SendSmtpEmailTo();
            sendSmtpEmailTo.setEmail(booking.getEmail());
            sendSmtpEmailTo.setName(booking.getFullName());

            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.sender(sendSmtpEmailSender);
            sendSmtpEmail.replyTo(sendSmtpEmailReplyTo);
            sendSmtpEmail.to(Arrays.asList(sendSmtpEmailTo));
            sendSmtpEmail.templateId(1L);

            try {
                GetAccount result = apiInstance.getAccount();
                System.out.println(result);
                CreateSmtpEmail result2 = api.sendTransacEmail(sendSmtpEmail);
                System.out.println(result2);
            } catch (ApiException e) {
                System.err.println("Exception when calling AccountApi#getAccount");
            }
        return true;
    }

    @Override
    public boolean mailToOdds(Booking booking) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey("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");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //apiKey.setApiKeyPrefix("Token");

        // Configure API key authorization: partner-key
        ApiKeyAuth partnerKey = (ApiKeyAuth) defaultClient.getAuthentication("partner-key");
        partnerKey.setApiKey("YOUR PARTNER KEY");
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        partnerKey.setApiKeyPrefix("Token");

        AccountApi apiInstance = new AccountApi();
        TransactionalEmailsApi api = new TransactionalEmailsApi();

        SendSmtpEmailSender sendSmtpEmailSender = new SendSmtpEmailSender();
        sendSmtpEmailSender.setEmail("odds.molamola@gmail.com");
        sendSmtpEmailSender.setName("Odds Booking");

        SendSmtpEmailReplyTo sendSmtpEmailReplyTo = new SendSmtpEmailReplyTo();
        sendSmtpEmailReplyTo.setEmail("odds.molamola@gmail.com");
        sendSmtpEmailReplyTo.setName("Odds Booking");

        SendSmtpEmailTo sendSmtpEmailTo = new SendSmtpEmailTo();
        sendSmtpEmailTo.setEmail("phum.project@gmail.com");
        sendSmtpEmailTo.setName("Professional ROV player P'Roof");

        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.sender(sendSmtpEmailSender);
        sendSmtpEmail.replyTo(sendSmtpEmailReplyTo);
        sendSmtpEmail.to(Arrays.asList(sendSmtpEmailTo));
        sendSmtpEmail.templateId(6L);

        try {
            CreateSmtpEmail result2 = api.sendTransacEmail(sendSmtpEmail);
            System.out.println(result2);
        } catch (ApiException e) {
            System.err.println("Exception when calling AccountApi#getAccount");
        }
        return true;
    }
}
