package com.HUMMMM.yopido.pantallas.loguedUser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.HUMMMM.yopido.R;
import com.HUMMMM.yopido.controlador.navegacion.cambiarDeClase;
import com.HUMMMM.yopido.pantallas.BaseActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainMenuLogueado extends BaseActivity {
    String telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_loggeado);
        Button btnReservar;
        Button btnAnularReserva;
        Button btnVerMisReservas;
        String correo = getIntent().getStringExtra("correo");
        String horaInicio = getIntent().getStringExtra("telefono");
        String horaFin = getIntent().getStringExtra("h1");
        String maxPersonas = getIntent().getStringExtra("h2");

        btnReservar = (Button) findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTelefono(correo,v, horaInicio, horaFin, maxPersonas);
            }
        }));

        btnAnularReserva = (Button) findViewById(R.id.btnAnularReserva);
        btnAnularReserva.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Click para ir a pantalla de anular reservas
                getTelefonoverReservas(correo,v, horaInicio, horaFin, maxPersonas);

            }
        }));

        btnVerMisReservas = (Button) findViewById(R.id.btnVerReservas);
        btnVerMisReservas.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Click para ir a pantalla de ver reservas
                cambiarDeClase.MoverA(v.getContext(), UserVerReservas.class, correo, telefono);
            }
        }));
    }

    public void getTelefono(String correo,View v, String horaInicio, String horaFin, String maxReservas) {
        // [START get_all_users]
        FirebaseFirestore.getInstance().collection("usuarios")
                .whereEqualTo("correo",correo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.out.println("Listen failed. "+e);
                            return;
                        }
                        //System.out.println( "Usuarios obtenidos: " + snapshots);

                        List<DocumentSnapshot> docs=snapshots.getDocuments();
                        for(DocumentSnapshot a: docs){
                            telefono=a.getString("telefono");
                        }
                        System.out.println("TELEFONO  "+ telefono);
                        cambiarDeClase.MoverA(v.getContext(), UserReservarLogueado.class, correo, telefono, horaInicio, horaFin, maxReservas);
                    }
                });
    }
    public void getTelefonoverReservas(String correo,View v, String horaInicio, String horaFin, String maxReservas) {
        // [START get_all_users]
        FirebaseFirestore.getInstance().collection("usuarios")
                .whereEqualTo("correo",correo)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.out.println("Listen failed. "+e);
                            return;
                        }
                        //System.out.println( "Usuarios obtenidos: " + snapshots);

                        List<DocumentSnapshot> docs=snapshots.getDocuments();
                        for(DocumentSnapshot a: docs){
                            telefono=a.getString("telefono");
                        }
                        System.out.println("TELEFONO  "+ telefono);
                        cambiarDeClase.MoverA(v.getContext(), UserDeleteReserva.class, telefono);
                    }
                });
    }
}