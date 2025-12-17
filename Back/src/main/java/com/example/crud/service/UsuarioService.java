package com.ejemplo.crud.service;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.json.JsonObject;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final Cluster cluster;
    private final Collection collection;

    public UsuarioService(Cluster cluster) {
        this.cluster = cluster;

        Bucket bucket = cluster.bucket("HUS_BUCKET");
        Scope scope = bucket.scope("demo");
        this.collection = scope.collection("usuarios");
    }

    public void guardar(String id, JsonObject data) {
        collection.upsert(id, data);
    }


    public JsonObject obtener(String id) {
        return collection.get(id).contentAsObject();
    }


    public void eliminar(String id) {
        collection.remove(id);
    }


public List<Map<String, Object>> listar() {

    String query = """
        SELECT
            u.id,
            u.nombre,
            u.apellido,
            u.edad,
            u.ciudad,
            u.activo,
            u.rol,
            u.puntos
        FROM HUS_BUCKET.demo.usuarios u
    """;

    QueryResult result = cluster.query(query);

    List<Map<String, Object>> lista = new ArrayList<>();

    result.rowsAsObject().forEach(row -> {
        lista.add(row.toMap());
    });

    return lista;
}

}
