package com.example.ipm.model;

public class Paciente {

    public int idade;
    public String email;
    public String senhaHash;
    public String endereco;

    public String nome;

    public Paciente(String nome, int idade,  String email, String senhaHash, String endereco) {
        this.nome = nome;
        this.idade = idade;

        this.email = email;
        this.senhaHash = senhaHash;
        this.endereco = endereco;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


}

