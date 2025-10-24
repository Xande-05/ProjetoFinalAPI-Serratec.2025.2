package org.serratec.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.serratec.ecommerce.dto.ErroResposta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandle extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			erros.add(error.getField() + ": " + error.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(),
				"Existem Campos Inválidos, confira o preenchimento!", LocalDateTime.now(), erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		ErroResposta erroResposta = new ErroResposta(status.value(), ex.getMessage(), LocalDateTime.now(), null);

		return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(EmailException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		System.err.println("ERRO NO ENVIO DE EMAIL DETECTADO");
		System.err.println("Mensagem original: " + ex.getMessage());
		ex.printStackTrace();
		System.err.println("FIM DO ERRO DE EMAIL");

		ErroResposta erroResposta = new ErroResposta(status.value(), ex.getMessage(), LocalDateTime.now(), null);

		return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(EnumValidationException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(EnumValidationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		ErroResposta erroResposta = new ErroResposta(status.value(), ex.getMessage(), LocalDateTime.now(), null);

		return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		System.err.println("Erro inesperado: " + ex.getMessage());
		ex.printStackTrace();

		ErroResposta erroResposta = new ErroResposta(status.value(), "Ocorreu um erro interno inesperado no servidor.",
				LocalDateTime.now(), null);

		return handleExceptionInternal(ex, erroResposta, new HttpHeaders(), status, request);
	}
}
