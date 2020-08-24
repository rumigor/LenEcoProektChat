package com.lenecoproekt.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegistrationActivity extends AppCompatActivity {
    ServerConnection serverConnection;
    EditText pass1;
    EditText pass2;
    EditText login;
    EditText nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button reg = findViewById(R.id.RegButton2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = findViewById(R.id.loginFieldReg);
                nickname = findViewById(R.id.nicknameFieldReg);
                pass1 = findViewById(R.id.pass1);
                pass2 = findViewById(R.id.pass2);
                String l = login.getEditableText().toString().trim();
                String nick = nickname.getEditableText().toString().trim();
                String p1 = pass1.getEditableText().toString().trim();
                String p2 = pass2.getEditableText().toString().trim();
                if (login.getEditableText().toString().equals("") || nickname.getEditableText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass1.getText() != null & pass2.getText() != null & p1.equals(p2) ) {
                    try {
                        serverConnection = new ServerConnection();
                        String msg = serverConnection.registration(l, nick, p1);
                        if (msg.equals("Регистрация прошла успешно")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else Toast.makeText(getApplicationContext(), "пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }