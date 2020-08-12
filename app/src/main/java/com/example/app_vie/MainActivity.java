package com.example.app_vie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_vie.Database.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class MainActivity extends AppCompatActivity {

    Button btn_cadastrar;
    EditText edt_password,edt_email;
    TextView status;


    private Connection connection;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        status = findViewById(R.id.textView);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public class checkLogin extends AsyncTask<String, String, String> {

            String z = null;
            Boolean isSuccess = false;


            @Override
            protected void onPreExecute() {
                status.setText("Enviando para o database");

            }

            @Override
            protected void onPostExecute(String s) {
                status.setText("Enviado ");
                edt_email.setText("");
                edt_password.setText("");

            }

        @SuppressLint("NewApi")
        public Connection connectionClass(String user, String password, String database, String server){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            String connectionURL = null;
            try{
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectionURL = "jdbc:jtds:sqlserver://" + server+"/" + database + ";user=" + user + ";password=" + password + ";";
                connection = DriverManager.getConnection(connectionURL);
            }catch (Exception e){
                Log.e("SQL Connection Error : ", e.getMessage());
            }

            return connection;
        }

        @Override
        protected String doInBackground(String... strings) {
            connection = connectionClass(ConnectionClass.un.toString(),
                    ConnectionClass.password.toString(),
                    ConnectionClass.db.toString(),
                    ConnectionClass.ip.toString());
            if(connection== null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    String sql = "SELECT * FROM register WHERE email = '" + edt_email.getText() + "' AND password = '" + edt_password.getText() + "' ";
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "Success";

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Check email or password", Toast.LENGTH_LONG).show();
                            }
                        });

                        edt_email.setText("");
                        edt_password.setText("");
                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }
    }


}


