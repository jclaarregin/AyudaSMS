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

import krlitos.ayudasms.db.SQLControlador;


public class ModificarContacto extends Activity implements OnClickListener {

    private EditText et, et2;
    private Button BtnModificar, BtnEliminar;
    private long contact_id;
    SQLControlador dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_contacto);

        et = (EditText) findViewById(R.id.et_contacto_id);
        et2 = (EditText) findViewById(R.id.et_celular_id);
        BtnModificar = (Button) findViewById(R.id.btnModificar);
        BtnEliminar = (Button) findViewById(R.id.btnEliminar);

        Intent i = getIntent();
        String contactID = i.getStringExtra("contactoId");
        String contactName = i.getStringExtra("contactoNombre");
        String contactPhone = i.getStringExtra("contactoCelular");

        contact_id = Long.parseLong(contactID);

        et.setText(contactName);
        et2.setText(contactPhone);

        BtnModificar.setOnClickListener(this);
        BtnEliminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();
        switch (v.getId()) {
            case R.id.btnModificar:

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
                        AlertModificar();
                    }
                }
                break;
            case R.id.btnEliminar:
                AlertEliminar();
                break;
        }
    }

    /*
    * Alerta de confirmación al momento de modificar
    * un contacto
    * */

    private void AlertModificar(){
         AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
          alertDialog.setMessage("¿Está seguro/a de modificar el contacto?");
          alertDialog.setTitle("Un momento!");
          alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
          alertDialog.setCancelable(false);
          alertDialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {

                  /*código Java si se ha pulsado sí
                  * modifico los datos y vuelvo a la lista
                  * */
                  String name = et.getText().toString();
                  String phone = et2.getText().toString();

                  dbcon.modificarDatos(contact_id, name, phone);


                  Intent main = new Intent(ModificarContacto.this, MostrarContactos.class)
                          .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(main);
                  dbcon.cerrar();

              }

          });
           alertDialog.setNegativeButton("No, me arrepentí", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {

                   /*código java si se ha pulsado no
                   * vuelvo a la lista
                   * */
                   Intent main = new Intent(ModificarContacto.this, MostrarContactos.class)
                           .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(main);
               }
           });
            alertDialog.show();
    }

    /*
    * Alerta de confirmación al momento de eliminar
    * un contacto
    * */
    private void AlertEliminar(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Está seguro/a de borrar el contacto?");
        alertDialog.setTitle("Un momento!");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                /*código Java si se ha pulsado sí
                * elimino el contacto y vuelvo a la lista
                * */
                dbcon.eliminarDatos(contact_id);
                returnHome();
                dbcon.cerrar();
            }
        });
        alertDialog.setNegativeButton("No, me arrepentí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /*
                código java si se ha pulsado no
                * Vuelvo a la lista de contactos
                * */
                Intent main = new Intent(ModificarContacto.this, MostrarContactos.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                dbcon.cerrar();
            }
        });
        alertDialog.show();
    }

    public void returnHome(){

        Intent home_intent = new Intent(getApplicationContext(),
                MostrarContactos.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }

}

