package com.usuarioservice.feignclients;

import com.usuarioservice.models.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "carro-service", url = "http://localhost:8082")
@RequestMapping("/carro")
public interface CarroFeignClient {
    @PostMapping
    public Carro save(@RequestBody Carro carro);

    @GetMapping("/usuario/{usuarioId}")
    public List<Carro> getCarro(@PathVariable("usuarioId") int usuarioId);
}
