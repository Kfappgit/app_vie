package com.example.app_vie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_vie.Database.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends AppCompatActivity {
    EditText edt_email,edt_password;
    Button  btn_cadastrar;
    private TextView textView;

    Connection connection;
    PreparedStatement statement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_email= findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        textView = findViewById(R.id.textView);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Register.checkLogin().execute("");

            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;


        @Override
        protected String doInBackground(String... strings) {
            try {
                connection = connectionClass(ConnectionClass.un.toString(),
                        ConnectionClass.password.toString(),
                        ConnectionClass.db.toString(),
                        ConnectionClass.ip.toString());

                if (connection == null) {
                    z = "Verifica a Internet do seu Celular";

                } else {
                    String sql = "INSERT INTO (username,password)SELECT values('" + username.getText() + "','" + password.get.Text + "')";
                    statement.executeUpdate(sql);
                }
            } catch (SQLException e) {

                isSuccess = false;
                z = e.getMessage();

            }
            return z;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

    @SuppressLint("newApi")
    public Connection connectionClass(String user ,String password, String database ,String server) throws SQLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection =  null;
        String connectionURL = null;
        try {


        connectionURL = "jdbc:jtds:sqlserver://" + server+"/" + database + ";user=" + user + ";password=" + password + ";";
        connection = DriverManager. getConnection(connectionURL);
    }catch (Exception e){
        Log.e("SQL Connection Error : ", e.getMessage());
    }

            return connection;

        }
    }
}

