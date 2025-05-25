package com.example.market.web.controller;

import com.example.market.domain.Purchase;
import com.example.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Operation(summary = "Obtener todas las compras", description = "Devuelve una lista de todas las compras realizadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras recuperada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener compras por cliente", description = "Devuelve una lista de compras realizadas por un cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras del cliente recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontraron compras para el cliente proporcionado")
    })
    @GetMapping("/client/{idClient}")
    public ResponseEntity<List<Purchase>> getByClient(@Parameter(description = "El ID del cliente para buscar las compras",
            required = true, example = "12345")
                                                          @PathVariable("idClient") String clientId) {
        return purchaseService.getByClient(clientId).map(
                purchases -> new ResponseEntity<>(purchases, HttpStatus.OK)
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Guardar una compra", description = "Guarda una nueva compra en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra creada con éxito",
                    content = @Content(schema = @Schema(implementation = Purchase.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la compra inválidos")
    })
    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}
