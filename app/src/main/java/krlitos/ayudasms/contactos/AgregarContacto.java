package krlitos.ayudasms.contactos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import krlitos.ayudasms.R;

import krlitos.ayudasms.MainActivity;
import krlitos.ayudasms.db.SQLControlador;


public class AgregarContacto extends Activity implements OnClickListener {
    private EditText et, et2;
    private Button btnAgregar, read_bt;
    SQLControlador dbconeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_contacto);
        et = (EditText) findViewById(R.id.et_contacto_id);
        et2 = (EditText) findViewById(R.id.et_celular_id);

        btnAgregar = (Button) findViewById(R.id.btnAgregarId);
        btnAgregar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        switch (v.getId()) {
            case R.id.btnAgregarId:
                String name = et.getText().toString();
                String phone = et2.getText().toString();

                //antes de guardar pregunto si hay campos sin completar
                if ((name.equals("")||(phone.equals("")))) {
                    Toast.makeText(this, "Ha dejado campos vacios",
                            Toast.LENGTH_LONG).show();
                }else{
                    //pregunto que la longitud del numero de celular sea correcta (10 caracteres sin contar el 0 y el 15)
                    if(phone.length()<10){
                        Toast.makeText(this, "El celular debe tener 10 digitos", Toast.LENGTH_LONG).show();

                    }else {

                        Mensaje();
                        /*dbconeccion.insertarDatos(name, phone);
                        MainActivity main= new MainActivity();
                        //aviso al contacto agregado que se lo agendo
                        main.SendSMS(phone, "El nro: "+phone+" lo agendó como contacto de emergencia");
                        Toast.makeText(AgregarContacto.this, "Aviso enviado", Toast.LENGTH_SHORT).show();
                        Intent main2 = new Intent(AgregarContacto.this, MostrarContactos.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main2);*/

                        break;
                    }
                }

            default:
                break;
        }

    }

    private void Mensaje(){
        //String name=nombre;
        //String phone=celular;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Al añadir un contacto, este es avisado, para que " +
                "en el futuro no desestime un pedido de ayuda suyo.");
        alertDialog.setTitle("Aviso");

        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                  /*código Java si se ha pulsado sí
                  * guardo los datos y vuelvo a la lista
                  * */
                String name = et.getText().toString();
                String phone = et2.getText().toString();

                dbconeccion.insertarDatos(name, phone);
                MainActivity main= new MainActivity();
                //aviso al contacto agregado que se lo agendo
                main.SendSMS(phone, "El nro: " + phone + " lo agendó como contacto de emergencia");
                Toast.makeText(AgregarContacto.this, "Aviso enviado", Toast.LENGTH_SHORT).show();
                Intent main2 = new Intent(AgregarContacto.this, MostrarContactos.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main2);
                dbconeccion.cerrar();

            }

        });
        /*alertDialog.setNegativeButton("No, me arrepentí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                   /*código java si se ha pulsado no
                   * vuelvo a la lista
                   * */
                /*Intent main = new Intent(ModificarContacto.this, MostrarContactos.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        });*/
        alertDialog.show();
    }
}//cierra la Clase AgregarContacto