package com.example.shopping.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.shopping.entity.MyOrder;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        // any setup if necessary
    }

    @Test
    public void testGetOrders() {
        // given
        MyOrder order1 = new MyOrder();
        order1.setOrderId(1);
        order1.setOrderDate("2022-01-01");

        MyOrder order2 = new MyOrder();
        order2.setOrderId(2);
        order2.setOrderDate("2023-01-01");

        List<MyOrder> orders = Arrays.asList(order1, order2);

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(orderRepository.findAll()).thenReturn(orders);

        // when
        ResponseEntity<ResponseObject> response = orderService.getOrders(request);

        // then
        verify(orderRepository, times(1)).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getStatus());
        assertEquals("Get orders successfully", response.getBody().getMessage());
        assertEquals(orders, response.getBody().getData());
    }
}
