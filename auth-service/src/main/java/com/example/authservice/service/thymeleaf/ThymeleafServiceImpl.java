package com.example.authservice.service.thymeleaf;


import com.example.authservice.common.EmailType;
import com.example.authservice.dto.mail.InputEmailDto;
import com.example.authservice.service.ThymeleafService;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Objects;

@Service
public class ThymeleafServiceImpl implements ThymeleafService {

    private static final String MAIL_TEMPLATE_BASE_NAME = "template/mail/MailMessages";
    private static final String MAIL_TEMPLATE_PREFIX = "/template/mail/";
    private static final String MAIL_TEMPLATE_SUFFIX = ".html";
    private static final String UTF_8 = "UTF-8";
    private static final String FIRSTNAME_KEY = "firstname";
    private static final String LASTNAME_KEY = "lastname";
    private static final String NEW_PASSWORD_KEY = "password";
    private static final String EMAIL = "email";

    private static final String FORGOT_PASSWORD_TEMP = "forgot-password-template";
    private static final String GENERATE_ACCOUNT_PASSWORD_TEMP = "generate-password-template";

    private static final TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Override
    public String getContent(EmailType emailType, InputEmailDto inputEmailDTO) {
        final Context context = new Context();
        if (EmailType.FORGOT_PASSWORD.equals(emailType)) {
            this.setVariablesForPassword(inputEmailDTO, context);
            this.setVariablesForFirstName(inputEmailDTO,context);
            this.setVariablesForLastName(inputEmailDTO,context);
            return templateEngine.process(FORGOT_PASSWORD_TEMP, context);
        }
        if (EmailType.GENERATE_ACCOUNT_PASSWORD.equals(emailType)) {
            this.setVariablesForPassword(inputEmailDTO, context);
            this.setVariablesForFirstName(inputEmailDTO,context);
            this.setVariablesForLastName(inputEmailDTO,context);
            this.setVariablesForEmail(inputEmailDTO,context);

            return templateEngine.process(GENERATE_ACCOUNT_PASSWORD_TEMP, context);
        }
        return null;
    }

    private void setVariablesForPassword(InputEmailDto inputEmailDTO, Context context) {
        if (Objects.nonNull(inputEmailDTO) && inputEmailDTO.getNewPassword() != null) {
            context.setVariable(NEW_PASSWORD_KEY, inputEmailDTO.getNewPassword());
        }
    }
    private void setVariablesForFirstName(InputEmailDto inputEmailDTO, Context context) {
        if (Objects.nonNull(inputEmailDTO) && inputEmailDTO.getNewPassword() != null) {
            context.setVariable(FIRSTNAME_KEY, inputEmailDTO.getFirstname());
        }
    }
    private void setVariablesForLastName(InputEmailDto inputEmailDTO, Context context) {
        if (Objects.nonNull(inputEmailDTO) && inputEmailDTO.getNewPassword() != null) {
            context.setVariable(LASTNAME_KEY, inputEmailDTO.getLastname());
        }
    }
    private void setVariablesForEmail(InputEmailDto inputEmailDTO, Context context) {
        if (Objects.nonNull(inputEmailDTO) && inputEmailDTO.getNewPassword() != null) {
            context.setVariable(EMAIL, inputEmailDTO.getEmailTo());
        }
    }
}
