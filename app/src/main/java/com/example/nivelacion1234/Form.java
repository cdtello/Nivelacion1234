package com.example.nivelacion1234;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nivelacion1234.DB.DBFirebase;
import com.example.nivelacion1234.Entidades.Producto;

public class Form extends AppCompatActivity {
    private DBFirebase dbFirebase;
    private Button btnForm;
    private EditText editNameForm, editDescriptionForm, editPriceForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        btnForm = (Button) findViewById(R.id.btnForm);
        editNameForm = (EditText) findViewById(R.id.editNameForm);
        editDescriptionForm = (EditText) findViewById(R.id.editDescriptionForm);
        editPriceForm = (EditText) findViewById(R.id.editPriceForm);
        dbFirebase = new DBFirebase();

        Intent intentIN = getIntent();
        if(intentIN.getBooleanExtra("edit",false)){
            editNameForm.setText(intentIN.getStringExtra("name"));
            editDescriptionForm.setText(intentIN.getStringExtra("description"));
            editPriceForm.setText(intentIN.getStringExtra("price"));
        }

        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto = new Producto(
                        editNameForm.getText().toString(),
                        editDescriptionForm.getText().toString(),
                        Integer.parseInt(editPriceForm.getText().toString()),
                        "",
                        "",
                        ""
                );
                if(intentIN.getBooleanExtra("edit",false)){
                    producto.setId(intentIN.getStringExtra("id"));
                    dbFirebase.updateData(producto);
                }else{
                    dbFirebase.insertData(producto);
                }
                Intent intent = new Intent(getApplicationContext(), Catalogo.class);
                startActivity(intent);
            }
        });

    }
}