package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl service;

    @Test
    void testCreateGet() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", isA(Product.class)));
    }

    @Test
    void testCreatePost() throws Exception {
        Product product = Mockito.mock(Product.class);

        mockMvc.perform(post("/product/create").flashAttr("product", product))
                .andExpect(view().name("redirect:list"));
        verify(service).create(product);
    }

    @Test
    void testDeletePost() throws Exception {
        String id = String.valueOf(UUID.randomUUID());

        mockMvc.perform(post("/product/delete/"+id))
                .andExpect(view().name("redirect:/product/list"));
        verify(service).delete(id);
    }

    @Test
    void testEditGet() throws Exception {
        Product product = Mockito.mock(Product.class);
        String id = String.valueOf(UUID.randomUUID());

        when(service.get(id)).thenReturn(product);

        mockMvc.perform(get("/product/edit/"+id))
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", isA(Product.class)))
                .andExpect(model().attribute("product", is(product)));
    }

    @Test
    void testEditPost() throws Exception {
        Product product = Mockito.mock(Product.class);
        String id = String.valueOf(UUID.randomUUID());

        mockMvc.perform(post("/product/edit/"+id).flashAttr("product", product))
                .andExpect(view().name("redirect:/product/list"));

        verify(service).edit(id, product);
    }

    @Test
    void testProductListGet() throws Exception {
        List<Product> productList = new ArrayList<>();

        when(service.findAll()).thenReturn(productList);

        mockMvc.perform(get("/product/list"))
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", isA(List.class)))
                .andExpect(model().attribute("products", is(productList)));
    }

}