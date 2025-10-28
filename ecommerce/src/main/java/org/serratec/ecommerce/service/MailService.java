package org.serratec.ecommerce.service;

import org.serratec.ecommerce.exception.EmailException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException; 
import org.springframework.mail.MailSendException;       
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	
	public void enviarEmail(String destinatario, String assunto, String mensagem) {
		SimpleMailMessage email = new SimpleMailMessage();		
		email.setTo(destinatario);
		email.setSubject(assunto);
		email.setText(mensagem); 

		try {
			mailSender.send(email);
		} catch (MailAuthenticationException e) {
            System.err.println("Erro de autenticação ao enviar email para " + destinatario + ": " + e.getMessage());
            throw new EmailException("Erro de autenticação com o servidor de email.", e);
        } catch (MailSendException e) {
             System.err.println("Erro ao enviar email para " + destinatario + ": " + e.getMessage());
            throw new EmailException("Não foi possível enviar o email.", e);
        } catch (Exception e) { 
             System.err.println("Erro inesperado ao enviar email para " + destinatario + ": " + e.getMessage());
             throw new EmailException("Ocorreu um erro inesperado durante o envio do email.", e);
        }
	}
}