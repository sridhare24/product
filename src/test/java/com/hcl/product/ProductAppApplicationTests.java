package com.hcl.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = { "com.hcl.product","com.hcl.product.controller" })
class ProductAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
