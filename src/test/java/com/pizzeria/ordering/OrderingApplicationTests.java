package com.pizzeria.ordering;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.platform.commons.logging.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pizzeria.ordering.model.PizzaOrder;
//import com.pizzeria.ordering.DTO.OrderingDTO;
import com.pizzeria.ordering.model.Pizza;
import com.pizzeria.ordering.repository.PizzaOrderRepository;

//import lombok.Getter;
//import lombok.Setter;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class OrderingApplicationTests {

	Logger logger = LoggerFactory.getLogger(OrderingApplicationTests.class);
    //private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	//private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
    private ObjectMapper om = new ObjectMapper();	
	protected Map<String, PizzaOrder> dataMap;
    @Autowired
    PizzaOrderRepository pizzaOrderRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws ParseException{
        List<PizzaOrder> list_PO = pizzaOrderRepository.findAllByIsTestData(true);
        for(PizzaOrder po: list_PO){
            pizzaOrderRepository.deleteById(po.getId()); 
        }
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

		LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(formatter);
		LocalDateTimeSerializer dateTimeSerializer = new LocalDateTimeSerializer(formatter);

		JavaTimeModule javaTimeModule = new JavaTimeModule(); 
		javaTimeModule.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
		javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);

		om.registerModule(javaTimeModule);
        dataMap = getTestData();
        //om.setDateFormat(simpleDateFormat); //(dtFormatter);
    }


    private Map<String, PizzaOrder> getTestData() throws ParseException {

        Map<String, PizzaOrder> data = new LinkedHashMap<>();

		Pizza p1 = new Pizza("REGULAR", "LARGE", "MEAT", 10.59, 2);
		Pizza p2 = new Pizza("REGULAR", "SMALL", "PEPERONI", 8.59, 1);
		Pizza p3 = new Pizza("DEEP_DISH", "SMALL", "MUSHROOM", 9.59, 1);
		Pizza p4 = new Pizza("THIN_CRUST", "MEDIUM", "CHEESE", 10.59, 2);
		Pizza p5 = new Pizza("REGULAR", "LARGE", "PEPERONI", 12.59, 1);
		Pizza p6 = new Pizza("DEEP_DISH", "SMALL", "MEAT", 9.99, 3);
		

        PizzaOrder o1 = new PizzaOrder();
		o1.setDateTime(LocalDateTime.now());
		o1.setCustomerName("Tom");
		o1.setCustomerPhoneNumber("9876543210");
		o1.setCustomerAddress("248 State Av.");
		o1.setStatus("PREPARING");
        o1.setTestData(true); 
		//o1.getPizzaList().add(p1);
        o1.getPizzaList().add(new Pizza());
        o1.getPizzaList().get(0).setType("REGULAR");
        o1.getPizzaList().get(0).setSize("LARGE");
        o1.getPizzaList().get(0).setTopping("MEAT");
        o1.getPizzaList().get(0).setPrice(10.59);
        //o1.getPizzaList().get(0).setPizzaorder(o1);
        //o1.getPizzaList().add(p2);
		o1.getPizzaList().add(new Pizza());
        o1.getPizzaList().get(1).setType("REGULAR");
        o1.getPizzaList().get(1).setSize("SMALL");
        o1.getPizzaList().get(1).setTopping("PEPERONI");
        o1.getPizzaList().get(1).setPrice(8.59);
        //o1.getPizzaList().get(1).setPizzaorder(o1);
		//o1.getPizzaList().get(1).setPizzaorder(o1);
		data.put("o1", o1);

		List<Pizza> pizzaList2 = new ArrayList<>();
		pizzaList2.add(p3);
		pizzaList2.add(p4);
		PizzaOrder o2 = new PizzaOrder(null, LocalDateTime.now(), "John", "123 Main St.", "1234567890", "PREPARING", true, null);
		o2.setPizzaList(pizzaList2);
		data.put("o2", o2);

		List<Pizza> pizzaList3 = new ArrayList<>();
		pizzaList3.add(p5);
		pizzaList3.add(p6);
		PizzaOrder o3 = new PizzaOrder(null, LocalDateTime.now(), "Mary", "999 Park St.", "9993456789", "PREPARING", true, null);
		o3.setPizzaList(pizzaList3);
		data.put("o3", o3);
		
        return data;
    }	
	/*
	@Test
	void contextLoads() throws Exception{
		dataMap = getTestData();
	}
    */ 
    
    @Test
    public void testPizzaOrderingEndpointWithPOST() throws Exception {
        
        PizzaOrder expectedRecord = dataMap.get("o1");
		System.out.print("Expected0: "+ om.writeValueAsString(expectedRecord));
        PizzaOrder actualRecord = om.readValue(mockMvc.perform(post("/pizzaria")
                .contentType("application/json")
                .content(om.writeValueAsString(dataMap.get("o1"))))
                .andDo(print())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), PizzaOrder.class);
		//String s = om.writeValueAsString(actualRecord);
		//logger.info(() -> ("Actual0: " + s));
        System.out.print("Actual0: "+ om.writeValueAsString(actualRecord));
        //Assert.isTrue(new ReflectionEquals(expectedRecord, "id", "dateTime").matches(actualRecord), "expectedRecords must match actualRecordes");
		Assert.isTrue(expectedRecord.compare(actualRecord), "expectedRecords must match actualRecordes");
        assertEquals(true, pizzaOrderRepository.findById(actualRecord.getId()).isPresent());
    }
     
    @Test
    public void testPizzaOrderingEndpointWithGETList() throws Exception {
        Map<String, PizzaOrder> data = getTestData();
        data.remove("o2");
        List<PizzaOrder> expectedRecords = new ArrayList<>();

        for (Map.Entry<String, PizzaOrder> kv : data.entrySet()) {
            expectedRecords.add(om.readValue(mockMvc.perform(post("/pizzaria")
                    .contentType("application/json")
                    .content(om.writeValueAsString(kv.getValue())))
                    .andDo(print())
                    .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), PizzaOrder.class));
        }
		//Assert.isTrue(false, "break and check db.");
        Collections.sort(expectedRecords, Comparator.comparing(PizzaOrder::getId));

        List<PizzaOrder> actualRecords = om.readValue(mockMvc.perform(get("/pizzaria/"))
                .andDo(print())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(expectedRecords.size())))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<List<PizzaOrder>>() {
        });

        for (int i = 0; i < expectedRecords.size(); i++) {
			System.out.print("Actual values: " + om.writeValueAsString(actualRecords.get(i)));
            //Assert.isTrue(new ReflectionEquals(expectedRecords.get(i), "id", "dateTime").matches(actualRecords.get(i)), "expectedRecords must match actualRecordes");
			Assert.isTrue(expectedRecords.get(i).compare(actualRecords.get(i)), "expectedRecords must match actualRecordes");
        }
    }
	
    @Test
    public void testPizzaOrderEndpointWithGETById() throws Exception {       
        PizzaOrder expectedRecord = om.readValue(mockMvc.perform(post("/pizzaria")
                .contentType("application/json")
                .content(om.writeValueAsString(dataMap.get("o1"))))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), PizzaOrder.class);
        System.out.print("Expected 1 values: " + om.writeValueAsString(expectedRecord));
        PizzaOrder actualRecord = om.readValue(mockMvc.perform(get("/pizzaria/" + expectedRecord.getId())
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), PizzaOrder.class);
		System.out.print("Actual 1 values: " + om.writeValueAsString(actualRecord));
        //Assert.isTrue(new ReflectionEquals(expectedRecord, "id", "dateTime").matches(actualRecord), "expectedRecords must match actualRecordes");
		Assert.isTrue(expectedRecord.compare(actualRecord), "expectedRecords must match actualRecordes");

        mockMvc.perform(get("/pizzaria/" + Integer.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }    
     
    @Test
    public void testPizzaOrderEndpointDeleteById() throws Exception {
        int i = 0;
        for (Map.Entry<String, PizzaOrder> kv : dataMap.entrySet()) {
            mockMvc.perform(post("/pizzaria")
            .contentType("application/json")
            .content(om.writeValueAsString(kv.getValue())));
            i++;
            if(i>=2) break;
        }
        Integer id = om.readValue(mockMvc.perform(post("/pizzaria")
                .contentType("application/json")
                .content(om.writeValueAsString(dataMap.get("o3"))))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), PizzaOrder.class).getId();
        System.out.print("id to be deleted: " + id);        
        String s = mockMvc.perform(delete("/pizzaria/" + id)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent()).andReturn().getResponse().getContentAsString();

		System.out.print("delete returned values: " + s);

        mockMvc.perform(delete("/pizzaria/" + Integer.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
