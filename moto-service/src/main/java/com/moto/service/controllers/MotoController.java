package com.moto.service.controllers;

import com.moto.service.entities.Moto;
import com.moto.service.services.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")

public class MotoController {
    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMoto(){
        List<Moto> motos = motoService.getAll();
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMotos(@PathVariable("id") int id){
        Moto moto = motoService.getMoto(id);
        if(moto == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> save(@RequestBody Moto moto){
        Moto nuevaMoto = motoService.save(moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Moto> motos = motoService.byUsuarioId(usuarioId);
        if(motos == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(motos);
    }
}
