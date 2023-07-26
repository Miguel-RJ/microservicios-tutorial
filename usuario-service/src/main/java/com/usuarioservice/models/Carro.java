package com.usuarioservice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Carro {
    private String marca;
    private String modelo;
    private int usuarioId;
}
