package com.concessionaria.exception;

public class ErrorResponse {

    private String erro;
    private String mensagem;

    public ErrorResponse(String erro, String mensagem) {
        this.erro = erro;
        this.mensagem = mensagem;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }
}