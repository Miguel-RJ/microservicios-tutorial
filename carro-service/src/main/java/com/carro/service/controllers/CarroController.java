package com.carro.service.controllers;

import com.carro.service.entities.Carro;
import com.carro.service.services.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

  @Autowired
  private CarroService carroService;

  @GetMapping
  public ResponseEntity<List<Carro>> listarCarro(){
      List<Carro> carros = carroService.getAll();
      if(carros.isEmpty()){
          return ResponseEntity.noContent().build();
      }
      return  ResponseEntity.ok(carros);
  }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> obtenerCarros(@PathVariable("id") int id){
        Carro carro = carroService.getCarro(id);
        if(carro == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carro);
    }

    @PostMapping
    public ResponseEntity<Carro> save(@RequestBody Carro carro){
        Carro nuevoCarro = carroService.save(carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Carro> carros = carroService.byUsuarioId(usuarioId);
        if(carros == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carros);
    }
}
