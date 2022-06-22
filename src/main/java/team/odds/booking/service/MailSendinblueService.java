package team.odds.booking.service;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.core.env.Environment;
import team.odds.booking.model.Booking;

import org.springframework.stereotype.Service;
import sibApi.TransactionalEmailsApi;

import sendinblue.*;
import sendinblue.auth.*;
import sibModel.*;
import sibApi.AccountApi;

import java.util.*;

@Service
public class MailSendinblueService {
    private Environment environment;

    public boolean mailToUser(Booking booking) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();

            // Configure API key authorization: api-key
            ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
            apiKey.setApiKey(environment.getProperty("sendinblue.token"));
            System.out.println(environment.getProperty("sendinblue.token"));
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
        return false;
    }

    public boolean mailToOdds(Booking booking) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        Dotenv dotenv = Dotenv.load();
        apiKey.setApiKey(dotenv.get("SENDINBLUE_TOKEN"));
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
        return false;
    }
}
