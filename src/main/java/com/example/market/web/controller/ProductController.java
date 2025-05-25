package com.example.market.web.controller;

import com.example.market.domain.Product;
import com.example.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos recuperada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un producto por ID", description = "Devuelve un producto específico dado su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@Parameter(description = "The id of the product",
                                                required = true, example = "7")
                                              @PathVariable("id") int productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Obtener productos por categoría", description = "Devuelve una lista de productos pertenecientes a una categoría específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada o sin productos")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@Parameter(description = "El ID de la categoría a buscar",
            required = true, example = "3")
                                                           @PathVariable("categoryId")int categoryId){
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Guardar un producto", description = "Guarda un nuevo producto en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado con éxito",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto dado su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@Parameter(description = "El ID del producto a eliminar",
            required = true, example = "5")
                                     @PathVariable("id") int productId) {
        return new ResponseEntity(this.productService.delete(productId)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}
