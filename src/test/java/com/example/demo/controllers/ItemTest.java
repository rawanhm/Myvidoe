package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.demo.controllers.OrderTest.createItem;
import static com.example.demo.controllers.OrderTest.createItems;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;
    @Before
    public void setup(){

        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(1)));
        when(itemRepository.findAll()).thenReturn(createItems());


    }

    @Test
    public void getItemstest(){
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();

        assertEquals(createItems(), items);

        verify(itemRepository , times(1)).findAll();
    }


    @Test
    public void getItemByIdtest(){
        // create a test item
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("This is a test item");
        item.setPrice(BigDecimal.valueOf(9.99));

        // configure the item repository mock to return the test item
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // call the getItemById method with id 1
        ResponseEntity<Item> response = itemController.getItemById(1L);

        // verify that the response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // verify that the returned item is the same as the test item
        assertEquals(item, response.getBody());
    }




}
