package com.concessionaria.exception;

public class ErrorResponse {

    private int status;
    private String erro;
    private String mensagem;
    private String path;

    public ErrorResponse(int status, String erro, String mensagem, String path) {
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getErro() {
        return erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getPath() {
        return path;
    }
}