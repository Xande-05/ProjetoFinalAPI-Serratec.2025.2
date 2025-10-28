package org.serratec.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 60, message = "O nome não pode exceder 60 caracteres")
    private String nome;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Formato de email inválido")
    @Size(max = 50, message = "O email não pode exceder 50 caracteres")
    private String email;

    @NotBlank(message = "O telefone não pode estar em branco")
    private String telefone;

    @NotBlank(message = "O CPF não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "O CEP não pode estar em branco")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    private String cep;


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }


}