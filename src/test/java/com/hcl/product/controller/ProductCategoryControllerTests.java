package com.hcl.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.product.ProductAppApplication;
import com.hcl.product.Repository.ProductCategoryRepository;
import com.hcl.product.Repository.ProductRepository;
import com.hcl.product.model.Product;
import com.hcl.product.model.ProductCategory;
import com.hcl.product.model.Seller;
import com.hcl.product.service.ProductCategoryService;
import com.hcl.product.service.impl.ProductCategoryServiceImpl;
import com.hcl.product.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductAppApplication.class)
@WebAppConfiguration
public class ProductCategoryControllerTests {
    String json = "";

    protected MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProductCategoryServiceImpl productCategoryService;

    @Mock
    ProductCategoryRepository productCategoryRepository;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productCategoryService.setProductCategoryRepository(productCategoryRepository);

    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void createProductCategoryTest() throws Exception{
        String uri = "/productCategories";
        ProductCategory productCategory = getProductCategory();
        Mockito.when(productCategoryRepository.saveAndFlush(Mockito.any(ProductCategory.class))).thenReturn(productCategory);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(productCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        ProductCategory productCategories =  objectMapper.readValue(content, ProductCategory.class);

        Mockito.verify(productCategoryRepository, Mockito.times(1)).saveAndFlush(Mockito.any(ProductCategory.class));

        Assert.assertNotNull(productCategories);
        Assert.assertEquals(productCategories.getProductCategoryName(), productCategory.getProductCategoryName());
        Assert.assertEquals(productCategories.getProductCategoryId(), productCategory.getProductCategoryId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductId(), productCategory.getProducts().get(0).getProductId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductName(), productCategory.getProducts().get(0).getProductName());

    }

    @Test
    public void getroductCategoriesTest() throws Exception{

        ProductCategory productCategory = getProductCategory();

        Mockito.when(productCategoryRepository.findAll()).thenReturn(Arrays.asList(productCategory));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCategory[] productCategoryList =  objectMapper.readValue(content, ProductCategory[].class);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).findAll();

        Assert.assertNotNull(productCategoryList);
        ProductCategory productCategories = productCategoryList[0];
        Assert.assertEquals(productCategories.getProductCategoryName(), productCategory.getProductCategoryName());
        Assert.assertEquals(productCategories.getProductCategoryId(), productCategory.getProductCategoryId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductId(), productCategory.getProducts().get(0).getProductId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductName(), productCategory.getProducts().get(0).getProductName());


    }

    @Test
    public void getProductCategoryByIdTest() throws Exception{
        ProductCategory productCategory = getProductCategory();

        Mockito.when(productCategoryRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(productCategory));

        productCategoryService.createProductCategory(productCategory);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/productCategories/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCategory productCategories =  objectMapper.readValue(content, ProductCategory.class);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).findById(Mockito.anyInt());

        Assert.assertNotNull(productCategories);
        Assert.assertEquals(productCategories.getProductCategoryName(), productCategory.getProductCategoryName());
        Assert.assertEquals(productCategories.getProductCategoryId(), productCategory.getProductCategoryId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductId(), productCategory.getProducts().get(0).getProductId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductName(), productCategory.getProducts().get(0).getProductName());

    }

    @Test
    public void updateProductCategoryTest() throws Exception{
        String uri = "/productCategories";
        ProductCategory productCategory = getProductCategory();
        Mockito.when(productCategoryRepository.saveAndFlush(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        ProductCategory productCategory1 = productCategoryService.createProductCategory(productCategory);
        productCategory1.setProductCategoryName("Apple");

        Mockito.when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory1);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(productCategory1))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        ProductCategory productCategories =  objectMapper.readValue(content, ProductCategory.class);

        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(Mockito.any(ProductCategory.class));

        Assert.assertNotNull(productCategories);
        Assert.assertEquals(productCategories.getProductCategoryName(), productCategory1.getProductCategoryName());
        Assert.assertNotEquals(productCategories.getProductCategoryName(), "Java");
        Assert.assertEquals(productCategories.getProductCategoryId(), productCategory.getProductCategoryId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductId(), productCategory.getProducts().get(0).getProductId());
        Assert.assertEquals(productCategories.getProducts().get(0).getProductName(), productCategory.getProducts().get(0).getProductName());

    }

    @Test
    public void deleteProductCategoryTest() throws Exception{

        ProductCategory productCategory = getProductCategory();
        //Mockito.when(productCategoryRepository.delete(Mockito.any(ProductCategory.class)));

         productCategoryService.createProductCategory(productCategory);

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .delete("/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(productCategory))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult mvcResult = mockMvc.perform(requestBuilder1).andReturn();
        int status = mvcResult.getResponse().getStatus();

        Assert.assertEquals(HttpStatus.OK.value(), status);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).delete(Mockito.any(ProductCategory.class));


    }

    private ProductCategory getProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("HCL");
        productCategory.setProductCategoryId(1);
        productCategory.setProducts(Arrays.asList(getProduct()));
        return productCategory;
    }

    private Product getProduct(){
        Product product= new Product();
        product.setProductId(1);
        product.setProductName("Java");
        Seller seller = new Seller();
        seller.setProductSold(10);
        seller.setSellerRegion("Hyd");
        seller.setCountry("India");
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());
        return product;
    }

}
