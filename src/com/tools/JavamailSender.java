package com.tools;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavamailSender {

	public static void send(String mem_acct,String email,String context) {
		String host = "smtp.gmail.com";
		int port = 587;
		final String username = "ba101g4@gmail.com";
		final String password = "ba101g40214";

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ba101g4@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("COFFEEPUZZLE 認證信");
			message.setContent(context,	"text/html;charset=UTF-8");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
}