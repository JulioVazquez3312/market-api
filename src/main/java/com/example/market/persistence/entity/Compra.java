package com.example.market.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Compra {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCompra;

        @Column(name = "id_cliente")
        private String idCliente;

        private LocalDateTime fecha;

        @Column(name = "medio_pago")
        private String medioPago;

        private String comentario;

        private Boolean estado;
}
