package team.odds.booking.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import team.odds.booking.model.Booking;
import team.odds.booking.util.HelpersUtil;
import java.io.IOException;

@Service
public class MailSenderService {

    private final Environment environment;

    public MailSenderService(Environment environment) {
        this.environment = environment;
    }

    public void mailToUser(Booking booking) throws IOException {
        String expiredDateFormat = HelpersUtil.dateTimeFormatGeneral(booking.getCreatedAt().plusDays(1));
        String templateId = environment.getRequiredProperty("send.grid.user.template.id");
        String sendGridToken = environment.getRequiredProperty("send.grid.token");

        Mail mailCompose = new Mail();
        mailCompose.setFrom(new Email("odds.molamola@gmail.com"));
        mailCompose.setSubject("กรุณายืนยันการจอง");

        Personalization templateCtx = new Personalization();
        templateCtx.addTo(new Email(booking.getEmail()));
        templateCtx.addDynamicTemplateData("fullName", booking.getFullName());
        templateCtx.addDynamicTemplateData("expiredDate", expiredDateFormat);
        templateCtx.addDynamicTemplateData("id", booking.getId());

        mailCompose.addPersonalization(templateCtx);
        mailCompose.setTemplateId(templateId);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mailCompose.build());
            new SendGrid(sendGridToken).api(request);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    public void mailToOdds(Booking booking) throws IOException {
        String templateId = environment.getRequiredProperty("send.grid.odds.template.id");
        String sendGridToken = environment.getRequiredProperty("send.grid.token");

        Mail mailCompose = new Mail();
        mailCompose.setFrom(new Email("odds.molamola@gmail.com"));
        mailCompose.setSubject("รายละเอียดการจอง");

        Personalization templateCtx = new Personalization();
        templateCtx.addTo(new Email("dome@odds.team"));
        templateCtx.addDynamicTemplateData("id", booking.getId());
        templateCtx.addDynamicTemplateData("fullName", booking.getFullName());
        templateCtx.addDynamicTemplateData("email", booking.getEmail());
        templateCtx.addDynamicTemplateData("phoneNumber", booking.getPhoneNumber());
        templateCtx.addDynamicTemplateData("room", booking.getRoom());
        templateCtx.addDynamicTemplateData("reason", booking.getReason());
        templateCtx.addDynamicTemplateData("startDate", HelpersUtil.dateTimeFormatGeneral(booking.getStartDate()));
        templateCtx.addDynamicTemplateData("endDate", HelpersUtil.dateTimeFormatGeneral(booking.getEndDate()));

        mailCompose.addPersonalization(templateCtx);
        mailCompose.setTemplateId(templateId);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mailCompose.build());
            new SendGrid(sendGridToken).api(request);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }
}
