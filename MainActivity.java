package com.example.ipm.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ipm.Banco_Dados;
import com.example.ipm.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    EditText emailEt, senhaEt;
    Button loginBtn, registrarBtn;
    Banco_Dados db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt = findViewById(R.id.emailEt);
        senhaEt = findViewById(R.id.senhaEt);
        loginBtn = findViewById(R.id.loginBtn);
        registrarBtn = findViewById(R.id.registrarBtn);
        db = new Banco_Dados(this);

//adicionando uma funcao ao botão e a entrada de texto

        loginBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            String senha = senhaEt.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            String senhaHash = hashPassword(senha);

            if (db.validarLogin(email, senhaHash)) {
                Intent intent = new Intent(MainActivity.this, Tela1.class);
                //passa o email para a próxima tela
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        });

        registrarBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CadastroActivity.class));
        });
    }
//hashing com SHA-256
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
