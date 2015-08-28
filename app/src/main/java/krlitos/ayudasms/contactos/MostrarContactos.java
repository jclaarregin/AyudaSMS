package krlitos.ayudasms.contactos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import krlitos.ayudasms.R;

import krlitos.ayudasms.db.DBhelper;
import krlitos.ayudasms.db.SQLControlador;

public class MostrarContactos extends Activity {

    private Button btnAgregarContacto;
    private ListView lista;
    SQLControlador dbconeccion;
    private TextView tv_conID, tv_conNombre, tv_conCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_contactos);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregarContacto = (Button) findViewById(R.id.btnAgregarContacto);
        lista = (ListView) findViewById(R.id.listViewMiembros);

        //acción del boton agregar miembro
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagregar = new Intent(MostrarContactos.this, AgregarContacto.class);
                startActivity(iagregar);
            }
        });

        // Tomar los datos desde la base de datos para poner en el curso y después en el adapter
        Cursor cursor = dbconeccion.leerDatos();

        String[] from = new String[] {
                DBhelper.CONTACTO_ID,
                DBhelper.CONTACTO_NOMBRE,
                DBhelper.CONTACTO_CELULAR
        };
        int[] to = new int[] {
                R.id.contacto_id,
                R.id.contacto_nombre,
                R.id.contacto_celular
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MostrarContactos.this, R.layout.formato_fila, cursor, from, to);

        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);

        //cursor.close();
        dbconeccion.cerrar();

        // acción cuando hacemos click en item para poder modificarlo o eliminarlo
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                tv_conID = (TextView) view.findViewById(R.id.contacto_id);
                tv_conNombre = (TextView) view.findViewById(R.id.contacto_nombre);
                tv_conCelular = (TextView) view.findViewById(R.id.contacto_celular);

                String aux_contactoId = tv_conID.getText().toString();
                String aux_contactoNombre = tv_conNombre.getText().toString();
                String aux_contactoCelular = tv_conCelular.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModificarContacto.class);
                modify_intent.putExtra("contactoId", aux_contactoId);
                modify_intent.putExtra("contactoNombre", aux_contactoNombre);
                modify_intent.putExtra("contactoCelular", aux_contactoCelular);
                startActivity(modify_intent);
            }
        });
    }  //termina el onCreate
} //termina clase
