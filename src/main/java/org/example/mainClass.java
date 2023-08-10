package org.example;

import org.example.httpConnection;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class mainClass {

    public static void main(String[] args) throws IOException, MessagingException {
        WebDriver driver = new ChromeDriver();
        String newUrl = "https://gifts.hamropatro.com/";
        driver.get(newUrl);

        List<String> brokenURL = new ArrayList<>();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        List<WebElement> anchorTags = driver.findElements(By.xpath("//a"));

        for (WebElement anchor : anchorTags) {
            String link = anchor.getAttribute("href");

            if (link != null && !link.isEmpty() && !link.startsWith("javascript:") && !link.contains("mailto") && !link.contains("tel")) {
                List<String> brokenURLSeparated = httpConnection.checkLink(newUrl, link);
                brokenURL.addAll(brokenURLSeparated);
            } else {
                System.out.println("The URL is empty or has 'javascript' as a protocol");
            }
        }
        System.out.println(brokenURL);

        driver.quit();


        if (!brokenURL.isEmpty()) {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.debug", "true");
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.auth", "true");

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("seleniumtry11@gmail.com", "ejrtbsqnqrewetpv");
                }
            };

            Session session = Session.getDefaultInstance(properties, authenticator);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("seleniumtry11@gmail.com"));


            String[] recipients = {
                    "ayush.pokharel8@gmail.com",
                    "mmaharjan@hamropatro.com",
                    "sbrana@hamropatro.com",
                    "sachinshakya@hamropatro.com",
                    "alina.pathak26@gmail.com"
            };

            Address[] recipientAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                recipientAddresses[i] = new InternetAddress(recipients[i]);
            }

            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.setSubject("Broken Links Report");
            message.setText("List of broken links:\n\n" + brokenURL.toString());

            Transport.send(message);
        }
    }
}
