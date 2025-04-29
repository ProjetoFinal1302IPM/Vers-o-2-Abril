package com.example.ipm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ipm.model.Paciente;

public class Banco_Dados extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Clinica.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PACIENTES = "Pacientes";

    public Banco_Dados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PACIENTES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "idade INTEGER," +
                "email TEXT UNIQUE," +
                "senha TEXT," +
                "endereco TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        onCreate(db);
    }
    public Paciente getPacientePorEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PACIENTES + " WHERE email = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
            int idade = cursor.getInt(cursor.getColumnIndexOrThrow("idade"));
            String endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"));
            String senha = cursor.getString(cursor.getColumnIndexOrThrow("senha")); // apenas se quiser usar em lógica
            cursor.close();
            return new Paciente(nome, idade, email, senha, endereco);
        }
        cursor.close();
        return null;
    }
    public boolean AtualizaPaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", paciente.nome);
        values.put("idade", paciente.idade);
        values.put("endereco", paciente.endereco);

        int result = db.update(TABLE_PACIENTES, values, "email = ?", new String[]{paciente.email});
        return result > 0;
    }
//deleta o cadstro da paciente no sistema
    public boolean deletarPaciente(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PACIENTES, "email = ?", new String[]{email});
        return result > 0;
    }


    public boolean registrarPaciente(Paciente paciente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("idade", paciente.idade);
        values.put("email", paciente.email);
        values.put("senha", paciente.senhaHash);
        values.put("endereco", paciente.endereco);

        long result = db.insert(TABLE_PACIENTES, null, values);
        return result != -1;
    }

    public boolean validarLogin(String email, String senhaHash) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PACIENTES +
                " WHERE email=? AND senha=?", new String[]{email, senhaHash});
        return cursor.moveToFirst();
    }
}
