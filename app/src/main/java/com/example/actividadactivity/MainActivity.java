package com.example.actividadactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Codigo con el que identificamos la respuesta de la camara
    private static final int CODIGO_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button BTFoto = (Button) findViewById(R.id.btnFoto);
        Button BTAceptar = (Button) findViewById(R.id.BTAceptar);
        EditText ETNombreFoto = (EditText) findViewById(R.id.ETNombreFoto);

        // Boton HACER FOTO: Intent implicito, abre la app de camara del sistema
        BTFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intentCamara.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intentCamara, CODIGO_FOTO);
                } else {
                    Toast.makeText(MainActivity.this, "No hay app de camara disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Boton ACEPTAR: Intent explicito, envia el nombre de la foto a AResultado
        BTAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StNombre = ETNombreFoto.getText().toString();
                Intent sIntent = new Intent(MainActivity.this, AResultado.class);
                sIntent.putExtra("STNombre", StNombre);
                startActivity(sIntent);
            }
        });
    }

    // Recibe la foto que devuelve la app de camara y la muestra en el ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_FOTO && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(foto);
        }
    }
}
