package com.example.ipm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipm.model.Paciente;

public class AlterarPaciente extends AppCompatActivity {

    EditText nome, idade, endereco;
    Button salvarBtn , excluirBtn;
    Banco_Dados bd;
    Paciente paciente;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_paciente);

        bd = new Banco_Dados(this);

        nome= findViewById(R.id.nome);
        idade = findViewById(R.id.idade);
        endereco = findViewById(R.id.endereco);
        salvarBtn = findViewById(R.id.salvarBtn);
        excluirBtn = findViewById(R.id.excluirBtn);

        String email = getIntent().getStringExtra("email");
        paciente = bd.getPacientePorEmail(email);

        if (paciente != null) {
            nome.setText(paciente.nome);
            idade.setText(String.valueOf(paciente.idade));
            endereco.setText(paciente.endereco);
        }

        salvarBtn.setOnClickListener(v -> {
            paciente.nome = nome.getText().toString();
            paciente.idade = Integer.parseInt(idade.getText().toString());
            paciente.endereco = endereco.getText().toString();

            if (bd.AtualizaPaciente(paciente)) {
                Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao atualizar dados", Toast.LENGTH_SHORT).show();
            }
        });
        excluirBtn.setOnClickListener(v -> {
            if (bd.deletarPaciente(paciente.email)) {
                Toast.makeText(this, "Paciente excluído com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Fecha a tela após excluir
            } else {
                Toast.makeText(this, "Erro ao excluir paciente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
