package com.example.market.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Cliente {
    @Id
    private String id;
    private String nombre;
    private String apellidos;
    private Long celular;
    private String direccion;

    @Column(name="correo_electronico")
    private String correoElectronico;

    // Relación con Compras
    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;
}
