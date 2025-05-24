package com.example.market.persistence;

import com.example.market.persistence.crud.ProductoCrudRepository;
import com.example.market.persistence.entity.Producto;

import java.util.List;
import java.util.Optional;

public class ProductoRepository {
    private ProductoCrudRepository productoCrudRepository;

    public List<Producto> getAll() {
        return (List<Producto>) productoCrudRepository.findAll();
    }

    public List<Producto> getByCategoria(Integer idcategoria) {
        return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idcategoria);
    }

    public Optional<List<Producto>> getEscasos(int cantidad) {
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);
    }
    public Optional<List<Producto>> getProductoByPrecioVentaLessThanPriceAndByCategory (int precioVenta, int idCategoria) {
        return productoCrudRepository.findByPrecioVentaLessThanAndIdCategoriaOrderByNombreAsc(precioVenta, idCategoria);
    }
}
