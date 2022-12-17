package com.example.nivelacion1234.DB;

import androidx.annotation.NonNull;

import com.example.nivelacion1234.Adaptadores.ProductoAdapter;
import com.example.nivelacion1234.Entidades.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase(){
        db = FirebaseFirestore.getInstance();
    }

    public void insertData(Producto producto){
        // Create a new user with a first and last name
        Map<String, Object> prod = new HashMap<>();
        prod.put("id", producto.getId());
        prod.put("name", producto.getName());
        prod.put("description", producto.getDescription());
        prod.put("price", producto.getPrice());
        prod.put("image", producto.getImage());
        prod.put("latitud", producto.getLatitud());
        prod.put("longitud", producto.getLongitud());

        // Add a new document with a generated ID
        db.collection("productos").add(prod);
    }

    public void getData(ProductoAdapter productoAdapter){
        db.collection("productos")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Producto> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Producto producto = new Producto(
                                    document.getData().get("id").toString(),
                                    document.getData().get("name").toString(),
                                    document.getData().get("description").toString(),
                                    Integer.parseInt(document.getData().get("price").toString()),
                                    document.getData().get("image").toString(),
                                    document.getData().get("latitud").toString(),
                                    document.getData().get("longitud").toString()
                            );
                            list.add(producto);
                        }
                        productoAdapter.setArrayProductos(list);
                        productoAdapter.notifyDataSetChanged();
                    }
                }
            });
    }

    public void deleteData(String id){
        db.collection("productos").whereEqualTo("id",id)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Producto> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                    }
                }
            });
    }

    public void updateData(Producto producto){
        db.collection("productos").whereEqualTo("id",producto.getId())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Producto> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().update(
                                    "name", producto.getName(),
                                    "description", producto.getDescription(),
                                    "price", producto.getPrice(),
                                    "image", producto.getImage(),
                                    "latitud", producto.getLatitud(),
                                    "longitud", producto.getLongitud()
                            );
                        }
                    }
                }
            });
    }
}
