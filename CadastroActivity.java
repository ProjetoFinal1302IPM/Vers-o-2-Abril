package com.example.ipm.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.example.ipm.Banco_Dados;
import com.example.ipm.R;
import com.example.ipm.model.Paciente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CadastroActivity extends AppCompatActivity {
    EditText nomeEt, idadeEt, emailEt, senhaEt, enderecoEt;
    Button cadastrarBtn, Voltarcad;
    Banco_Dados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeEt = findViewById(R.id.nomeEt);
        idadeEt = findViewById(R.id.idadeEt);
        emailEt = findViewById(R.id.emailEt);
        senhaEt = findViewById(R.id.senhaEt);
        enderecoEt = findViewById(R.id.enderecoEt);
        cadastrarBtn = findViewById(R.id.cadastrarBtn);
        Voltarcad = findViewById(R.id.VoltarcadBtn);
        db = new Banco_Dados(this);

        Voltarcad.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroActivity.this,  MainActivity.class);

            startActivity(intent);
        });


        cadastrarBtn.setOnClickListener(v -> {
            String nome = nomeEt.getText().toString();
            int idade = Integer.parseInt(idadeEt.getText().toString());
            String email = emailEt.getText().toString();
            String senha = senhaEt.getText().toString();
            String endereco = enderecoEt.getText().toString();

            String senhaHash = hashPassword(senha);
            Paciente paciente = new Paciente(nome, idade, email, senhaHash, endereco);

            if (db.registrarPaciente(paciente)) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar (email já existe?)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String hashPassword(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
