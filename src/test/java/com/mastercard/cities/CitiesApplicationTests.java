package com.mastercard.cities;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.mastercard.cities.service.CitiesServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CitiesApplicationTests {

	@SpyBean
	private CitiesServiceImpl citiesServiceImpl;

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	CitiesServiceImpl citiesService;
	
	
	@Autowired
	private List<Set<String>> processedList;

	@Before
	public void setup() throws Exception {
	}

	@Rule
	public ExpectedException exc = ExpectedException.none();
	
	@Test
	public void testLoadFile() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("city.txt").getFile());
			assert(true);
		} catch (Exception e) {
			
		}
	}

	@Test
	public void testValidOriginDestination1() {
		citiesService.setProcessedList("city.txt");
		try {
			this.mvc.perform(get("/connected?origin=philadelphia&destination=boston"))
				.andExpect(status().isOk())
				.andExpect(content().string("yes"));
			assert(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInvalidOriginDestination1() {
		citiesService.setProcessedList("city.txt");
		try {
			this.mvc.perform(get("/connected?origin=mars&destination=earth"))
			.andExpect(status().isOk())
			.andExpect(content().string("no"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@Test
	public void testValidOriginDestination2() {
		citiesService.setProcessedList("city2.txt");
		try {
			this.mvc.perform(get("/connected?origin=D&destination=B"))
				.andExpect(status().isOk())
				.andExpect(content().string("yes"));
			assert(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInvalidOriginDestination2() {
		citiesService.setProcessedList("city2.txt");
		try {
			this.mvc.perform(get("/connected?origin=M&destination=M"))
			.andExpect(status().isOk())
			.andExpect(content().string("no"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@Test
	public void testValidOriginDestination3() {
		citiesService.setProcessedList("city3.txt");
		try {
			this.mvc.perform(get("/connected?origin=C&destination=E"))
				.andExpect(status().isOk())
				.andExpect(content().string("yes"));
			assert(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInvalidOriginDestination3() {
		citiesService.setProcessedList("city3.txt");
		try {
			this.mvc.perform(get("/connected?origin=E&destination=uhoh"))
			.andExpect(status().isOk())
			.andExpect(content().string("no"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@Test
	public void testValidOriginDestination4() {
		citiesService.setProcessedList("city4.txt");
		try {
			this.mvc.perform(get("/connected?origin=New York&destination=Kenville"))
				.andExpect(status().isOk())
				.andExpect(content().string("yes"));
			assert(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testInvalidOriginDestination4() {
		citiesService.setProcessedList("city4.txt");
		try {
			this.mvc.perform(get("/connected?origin=E&destination=bbbb"))
			.andExpect(status().isOk())
			.andExpect(content().string("no"));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
}
