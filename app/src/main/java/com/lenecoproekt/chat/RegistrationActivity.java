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
                pass1 = findViewById(R.id.pass1);
                pass2 = findViewById(R.id.pass2);
                String p1 = pass1.getEditableText().toString();
                String p2 = pass2.getEditableText().toString();
                if (pass1.getText() != null & pass2.getText() != null & p1.equals(p2) ) {
                    try {
                        serverConnection = ServerConnection.getServerConnection();
                        login = findViewById(R.id.loginFieldReg);
                        nickname = findViewById(R.id.nicknameFieldReg);
                        serverConnection.out.writeUTF(String.format("/reg %s %s %s", login.getEditableText().toString(), p1, nickname.getEditableText().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //цикл аутентификации
                                while (true) {
                                    String str = serverConnection.in.readUTF();
                                    if (str.startsWith("/regresult ")) {
                                        String result = str.split("\\s")[1];
                                        if (result.equals("ok")) {
                                            Toast.makeText(getApplicationContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Регистрация не получилась, возможно логин или никнейм заняты", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else Toast.makeText(getApplicationContext(), "пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }