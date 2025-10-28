package org.serratec.ecommerce.config; 

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.serratec.ecommerce.entity.Cliente;
import org.serratec.ecommerce.repository.ClienteRepository;
import org.serratec.ecommerce.service.MailService;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
//Envia o email para os clientes do banco falando da promoção de outubro
@Component
public class InicializadorPromocaoOutubro implements CommandLineRunner {

   
    private static final Logger logger = LoggerFactory.getLogger(InicializadorPromocaoOutubro.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Verificando se é mês de promoção (Outubro)...");

       
        if (LocalDateTime.now().getMonth() == Month.OCTOBER) {
            logger.info("É Outubro! Iniciando envio de e-mails promocionais.");

            List<Cliente> todosClientes = clienteRepository.findAll();

            if (todosClientes.isEmpty()) {
                logger.warn("Nenhum cliente encontrado para enviar e-mail promocional.");
                return;
            }

            logger.info("Encontrados {} clientes. Enviando e-mails...", todosClientes.size());

            String assunto = "🎉 Desconto Especial de Outubro!";
            String corpoEmail = """
                    Olá %s,

                    Outubro chegou com uma oferta imperdível! Use o cupom OUTUBRO20 e ganhe 20%% de desconto em seus pedidos durante todo o mês.

                    Aproveite agora mesmo!

                    Atenciosamente,
                    Equipe E-commerce Serratec
                    """;

            int emailsEnviados = 0;
            int emailsFalharam = 0;

            for (Cliente cliente : todosClientes) {
                if (cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
                    try {
                        String corpoPersonalizado = String.format(corpoEmail, cliente.getNome());
                        mailService.enviarEmail(cliente.getEmail(), assunto, corpoPersonalizado);
                        logger.debug("E-mail promocional enviado para: {}", cliente.getEmail());
                        emailsEnviados++;
                    } catch (Exception e) {
                        
                        logger.error("Falha ao enviar e-mail promocional para {}: {}", cliente.getEmail(), e.getMessage());
                        emailsFalharam++;
                    }
                } else {
                    logger.warn("Cliente ID {} sem e-mail cadastrado.", cliente.getId());
                }
            }

            logger.info("Envio de e-mails promocionais concluído. Enviados: {}, Falhas: {}", emailsEnviados, emailsFalharam);

        } else {
            logger.info("Não é Outubro. Nenhuma promoção de cupom será enviada.");
        }
    }
}