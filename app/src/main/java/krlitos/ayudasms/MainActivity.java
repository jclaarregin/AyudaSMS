package krlitos.ayudasms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import krlitos.ayudasms.contactos.MostrarContactos;
import krlitos.ayudasms.db.SQLControlador;


/**
 * Created by krlitos on 12/08/15.
 */
public class MainActivity extends Activity{
    private Button btnEnviarSMS, btnVerContactos,btnAñadirContactos;
    SQLControlador db=new SQLControlador(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnviarSMS=(Button)findViewById(R.id.btnEnviarSMS);
        //btnAñadirContactos = (Button) findViewById(R.id.buttonAgregar);
        btnVerContactos=(Button)findViewById(R.id.buttonVer);
        /*
        * creamos el método que llamamos sendSMS,
        * en el cual creamos un objeto SmsManager llamado sms
        * y con el método sendTextMessage de este objeto enviaremos nuestro mensaje,
        * */
        btnEnviarSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.abrirBaseDeDatos();
                Cursor c = db.leerDatos();

                if(c.moveToFirst()){
                    do{
                        String nombre=c.getString(1);
                        String celular=c.getString(2);
                        SendSMS(celular,"ESTOY EN PROBLEMAS, AYUDA POR FAVOR!");;
                        Toast.makeText(MainActivity.this, "Mensaje enviado a "+nombre, Toast.LENGTH_SHORT).show();
                    }while (c.moveToNext());

                }else{
                    Toast.makeText(MainActivity.this, "No tiene contactos Agendados ", Toast.LENGTH_SHORT).show();
                    c.close();

                }
                c.close();
                db.cerrar();

            }
        });

        btnVerContactos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Toast.makeText(MainActivity.this, "ver contactos", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, MostrarContactos.class);
                startActivity(i);
            }
        });
    }
    //envia mensajes al otro dispositivo
    public void SendSMS(String phoneNumber, String message){
        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

}
