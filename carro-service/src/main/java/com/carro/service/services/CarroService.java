package com.carro.service.services;

import com.carro.service.entities.Carro;
import com.carro.service.repositories.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getAll(){
        return carroRepository.findAll();
    }

    public Carro getCarro(int id){
        return carroRepository.findById(id).orElse(null);
    }

    public Carro save(Carro usuario){
        Carro nuevoCarro = carroRepository.save(usuario);
        return nuevoCarro;
    }

    public List<Carro> byUsuarioId(int usuarioId){
        return carroRepository.findByUsuarioId(usuarioId);
    }
}
