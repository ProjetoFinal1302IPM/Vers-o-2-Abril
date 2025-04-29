package com.example.ipm.telas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipm.AlterarPaciente;
import com.example.ipm.Banco_Dados;
import com.example.ipm.R;
import com.example.ipm.model.Paciente;

public class Tela1 extends AppCompatActivity {

    Banco_Dados db;
    Paciente paciente;
    TextView nomeT, idadeT, emailT, enderecoT;
    Button alterarBtn , VoltarBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela1);

        db = new Banco_Dados(this);

        // Pega o email passado pelo login
        String email = getIntent().getStringExtra("email");

        paciente = db.getPacientePorEmail(email);
// o T no final da variavel está dizendo que é da tela 1
        nomeT = findViewById(R.id.nomeT);
        idadeT = findViewById(R.id.idadeT);
        emailT = findViewById(R.id.emailT);
        enderecoT = findViewById(R.id.enderecoT);
        alterarBtn = findViewById(R.id.alterarBtn);
        VoltarBtn = findViewById(R.id.VoltarBtn);

        if (paciente != null) {
            nomeT.setText("Nome: " + paciente.nome);
            idadeT.setText("Idade: " + paciente.idade);
            emailT.setText("Email: " + paciente.email);
            enderecoT.setText("Endereço: " + paciente.endereco);
        }
//adicionando uma funcao ao botão
        alterarBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Tela1.this, AlterarPaciente.class);
            //faz que o email siga para a próxima página
            intent.putExtra("email", paciente.email);
            startActivity(intent);
        });
        VoltarBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Tela1.this,  MainActivity.class);

            startActivity(intent);
        });
    }
}
