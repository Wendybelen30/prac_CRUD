package com.ejemplo.crud.controller;

import com.couchbase.client.java.json.JsonObject;
import com.ejemplo.crud.model.Usuario;
import com.ejemplo.crud.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public void crear(@RequestBody Usuario usuario) {

        JsonObject obj = JsonObject.create()
                .put("id", usuario.getId())
                .put("nombre", usuario.getNombre())
                .put("apellido", usuario.getApellido())
                .put("edad", usuario.getEdad())
                .put("ciudad", usuario.getCiudad())
                .put("activo", usuario.isActivo())
                .put("rol", usuario.getRol())
                .put("puntos", usuario.getPuntos());

        service.guardar(usuario.getId(), obj);
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
    return service.listar();
    }


    @GetMapping("/{id}")
    public JsonObject obtener(@PathVariable String id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}
