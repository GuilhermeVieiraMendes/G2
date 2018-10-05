package com.example.paulosouza.myapplication;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private String _nome;
    private String _cpf;
    private int _codigo;

    public String  getNome () {return _nome; }

    public void setNome (String nome) {this._nome = nome;}

    public String getCpf() {return _cpf;}

    public void setCpf(String cpf) {this._cpf = cpf;}

    public int getCodigo() {return this._codigo;}

    public void setCodigo(int codigo) {this._codigo = codigo;}

    @Override
    public String toString() {
        return  _nome;
    }
}

