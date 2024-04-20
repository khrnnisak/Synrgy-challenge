package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Models.Item;
import org.example.Models.Order;
import org.example.Services.ItemService;
import org.example.Services.ItemServiceImpl;
import org.example.Services.OrderService;
import org.example.Services.OrderServiceImpl;
import org.example.Utils.FormatMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    OrderService order;

    @BeforeEach
    void instantiateItem(){
        Data.initiateItems();
    }
    
    @Test
    @DisplayName("Positive Case - Add Order")
    void successToAdd() {
        ItemService i_serv = new ItemServiceImpl();
        int prev_size = Data.orders.size();
        order = new OrderServiceImpl();
        Item item = i_serv.getItemById(1L);
        order.create(new Order(1111L, item, 4));
        int new_size = Data.orders.size();
        assertEquals(prev_size+1, new_size);
    }


    @Test
    @DisplayName("Negative Case - Add Order - Duplicate Issue")
    void duplicateIssue() {
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();
        Item item1 = i_serv.getItemById(1L);
        order.create(new Order(1111L, item1, 4));

        //add the same order
        
        Item item2 = i_serv.getItemById(1L);
        String result = order.create(new Order(1111L, item2, 4));
        assertEquals(FormatMessageUtil.errorMessageFormat("Pesanan Sudah tersedia, ubah untuk memperbarui pesanan"), result);
    }

    @Test
    @DisplayName("Negative Case - Add Order - Null Issue")
    void nullIssue(){
        order = new OrderServiceImpl();
        String result = order.create(null);
        assertEquals(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Pesanan"), result);
    }

    @Test
    @DisplayName("Positive Case - Delete Order ")
    void successDelete(){
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();

        //add the order
        Item item1 = i_serv.getItemById(1L);
        System.out.println("add" + order.create(new Order(1111L, item1, 4)));

        //delete the order
        String result = order.delete(1111L);
        assertEquals(FormatMessageUtil.succesToDeleteMessage(), result);
    }

    @Test
    @DisplayName("Negative Case - Delete Order - Not Found Issue")
    void notFoundDelete(){
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();

        //add the order
        Item item1 = i_serv.getItemById(1L);
        order.create(new Order(1111L, item1, 4));

        //delete the order
        String result = order.delete(99L);
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }

    @Test
    @DisplayName("Positive Case - Get Order By ID")
    void testGetOrderById() {
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();

        //add the order
        Item item1 = i_serv.getItemById(1L);
         order.create(new Order(1111L, item1, 4));

        order = new OrderServiceImpl();
        Order orderExist = order.getOrderById(1111L);
        assertEquals(item1.getName(), orderExist.getItem().getName());
    }

    @Test
    @DisplayName("Positive Case - Update Order ")
    void successUpdate(){
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();

        //add the order
        Item item1 = i_serv.getItemById(1L);
        order.create(new Order(1111L, item1, 4));

        order.update(1111L, 7);
        Order getOrder = order.getOrderById(1111L);
        assertEquals(7, getOrder.getQuantity());
    }

    @Test
    @DisplayName("Positive Case - Update Order ")
    void notFoundUpdate(){
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();

        //add the order
        Item item1 = i_serv.getItemById(1L);
        order.create(new Order(1111L, item1, 4));

        String result = order.update(99L, 7);
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }

    @Test
    @DisplayName("Positive Case - Get Total Quantiry")
    void getQuantityTest() {
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();
        Item item1 = i_serv.getItemById(1L);
        Item item2 = i_serv.getItemById(2L);
        Item item3 = i_serv.getItemById(3L);
        order.create(new Order(1111L, item1, 2));
        order.create(new Order(2222L, item2, 3));
        order.create(new Order(3333L, item3, 4));

        int result = order.getTotalqty();
        assertEquals(9, result);
    }

    @Test
    @DisplayName("Positive Case - Get Total Price")
    void getPriceTest() {
        ItemService i_serv = new ItemServiceImpl();
        order = new OrderServiceImpl();
        Item item1 = i_serv.getItemById(1L);
        Item item2 = i_serv.getItemById(2L);
        Item item3 = i_serv.getItemById(3L);
        order.create(new Order(1111L, item1, 2));
        order.create(new Order(2222L, item2, 3));
        order.create(new Order(3333L, item3, 4));

        int result = order.getTotalPrice();
        assertEquals(141000, result);
    }
}
