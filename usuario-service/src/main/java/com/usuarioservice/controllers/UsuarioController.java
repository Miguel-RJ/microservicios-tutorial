package com.usuarioservice.controllers;

import com.usuarioservice.entities.Usuario;
import com.usuarioservice.models.Carro;
import com.usuarioservice.models.Moto;
import com.usuarioservice.services.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuario() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarioService.getUsuario(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable int usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Carro> carro = usuarioService.getCarros(usuarioId);

        if (carro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carro);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable int usuarioId) {
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Moto> motos = usuarioService.getMotos(usuarioId);

        if (motos == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro) {
        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarVehiculosUsuario(@PathVariable("usuarioId") int usuarioId) {
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehicles(usuarioId);
        return ResponseEntity.ok(resultado);
    }

    private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("Usuario: " + id + "tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException exception) {
        return new ResponseEntity("Usuario: " + id + "no tiene suficientes fondos para el carro " + exception.getMessage(), HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("Usuario: " + id + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException exception) {
        return new ResponseEntity("Usuario: " + id + "no tiene suficientes fondos para la moto", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") int id, RuntimeException exception) {
        return new ResponseEntity("Usuario: " + id + "tiene los vehículos en el taller", HttpStatus.OK);
    }
}
