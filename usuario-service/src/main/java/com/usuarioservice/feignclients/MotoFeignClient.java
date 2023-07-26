package com.usuarioservice.feignclients;

import com.usuarioservice.models.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "moto-service", url = "http://localhost:8083")
@RequestMapping("/moto")
public interface MotoFeignClient {
    @PostMapping
    public Moto save(@RequestBody Moto carro);

    @GetMapping("/usuario/{usuarioId}")
    public List<Moto> getMotos(@PathVariable("usuarioId") int usuarioId);

}
