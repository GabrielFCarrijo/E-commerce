package com.desario.gcarrijo.service;

import com.desario.gcarrijo.config.exceptions.ProductNotFoundException;
import com.desario.gcarrijo.entity.Product;
import com.desario.gcarrijo.entity.dto.ProductRequestDTO;
import com.desario.gcarrijo.entity.dto.ProductResponseDTO;
import com.desario.gcarrijo.entity.mapper.ProductMapper;
import com.desario.gcarrijo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private UUID productId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        productId = UUID.randomUUID();
    }

    @Test
    void testCreateProductSuccess() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome("Produto Teste");
        dto.setDescricao("Descrição");
        dto.setPreco(new BigDecimal("99.90"));
        dto.setCategoria("Categoria");
        dto.setQuantidadeEmEstoque(10);

        UUID productId = UUID.randomUUID();

        Product savedProduct = new Product();
        savedProduct.setId(productId);
        savedProduct.setNome(dto.getNome());
        savedProduct.setDescricao(dto.getDescricao());
        savedProduct.setPreco(dto.getPreco());
        savedProduct.setCategoria(dto.getCategoria());
        savedProduct.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());

        when(productRepository.save(any())).thenReturn(savedProduct);

        ProductResponseDTO result = productService.create(dto);

        assertNotNull(result);
        assertEquals("Produto Teste", result.getNome());
        assertEquals("Categoria", result.getCategoria());
    }

    @Test
    void testUpdateProductSuccess() {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setNome("Novo Nome");
        dto.setDescricao("Nova Desc");
        dto.setPreco(new BigDecimal("150.0"));
        dto.setCategoria("Nova Categoria");
        dto.setQuantidadeEmEstoque(5);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setNome("Antigo");
        existingProduct.setDescricao("Antiga Desc");
        existingProduct.setPreco(new BigDecimal("100.0"));
        existingProduct.setCategoria("Antiga Categoria");
        existingProduct.setQuantidadeEmEstoque(20);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(existingProduct);

        ProductResponseDTO result = productService.update(productId, dto);

        assertEquals("Novo Nome", result.getNome());
        assertEquals("Nova Categoria", result.getCategoria());
    }

    @Test
    void testUpdateProductNotFound() {
        ProductRequestDTO dto = new ProductRequestDTO();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(productId, dto));
    }

    @Test
    void testDeleteSuccess() {
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        assertDoesNotThrow(() -> productService.delete(productId));
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.existsById(productId)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.delete(productId));
    }

    @Test
    void testGetByIdSuccess() {
        Product product = new Product();
        product.setId(productId);
        product.setNome("Produto X");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getById(productId);

        assertEquals("Produto X", result.getNome());
    }

    @Test
    void testGetByIdNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById(productId));
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product();
        p1.setId(UUID.randomUUID());
        p1.setNome("P1");

        Product p2 = new Product();
        p2.setId(UUID.randomUUID());
        p2.setNome("P2");

        List<Product> products = List.of(p1, p2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = productService.getAll();

        assertEquals(2, result.size());
    }
}