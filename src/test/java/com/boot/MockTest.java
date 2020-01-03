package com.boot;

import com.boot.module.demo.DemoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@RunWith(SpringJUnit4ClassRunner.class) //与@RunWith(SpringRunner.class) 二者皆可
@RunWith(SpringRunner.class) 
@SpringBootTest
public class MockTest {
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		 //mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		 mvc = MockMvcBuilders.standaloneSetup(new DemoController()).build();
	}

	@Test
    public void testHello() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/hello")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(equalTo("Hello World!")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String retrunStr = new String(response.getContentAsByteArray());
		//打印返回结果
		System.err.println(retrunStr);
	}
}