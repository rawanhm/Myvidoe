package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderTest {


    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setup(){
        User user = createUser();

        when(userRepository.findByUsername("rawan")).thenReturn(user);
        when(orderRepository.findByUser(any())).thenReturn(createOrders());
    }



    @Test
    public void submittset(){

        ResponseEntity<UserOrder> response = orderController.submit("rawan");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder order = response.getBody();

        assertEquals(createItems(), order.getItems());
        assertEquals(createUser().getId(), order.getUser().getId());


        verify(orderRepository,times(1)).save(order);

    }



    @Test
    public void submitInvalidtest(){

        ResponseEntity<UserOrder> response = orderController.submit("invalid username");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        assertNull( response.getBody());

        verify(userRepository, times(1)).findByUsername("invalid username");
    }

    @Test
    public void getOrdersForUsertest(){

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("rawan");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> orders = response.getBody();


        assertEquals(createOrders().size(), orders.size());

    }

    @Test
    public void getOrdersForUserInvalidtest(){}

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rawan");
        user.setPassword("password");
        user.setCart(createCart(user));

        return user;
    }
    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setId(1L);
        List<Item> items = createItems();
        cart.setItems(createItems());
        cart.setTotal(items.stream().map(item -> item.getPrice()).reduce(BigDecimal::add).get());
        cart.setUser(user);

        return cart;
    }
    private List<UserOrder> createOrders() {

        List<UserOrder> orders = new ArrayList<>();

        IntStream.range(0,2).forEach(i -> {
            UserOrder order = new UserOrder();
            Cart cart = createCart(createUser());

            order.setItems(cart.getItems());
            order.setTotal(cart.getTotal());
            order.setUser(createUser());
            order.setId(Long.valueOf(i));

            orders.add(order);
        });
        return orders;
    }
    public static List<Item> createItems() {

        List<Item> items = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            items.add(createItem(i));
        }

        return items;
    }

    public static Item createItem(long id){
        Item item = new Item();
        item.setId(id);

        item.setPrice(BigDecimal.valueOf(id * 1.2));

        item.setName("Item " + item.getId());

        item.setDescription("Description ");
        return item;
    }
}
