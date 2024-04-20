package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Models.Item;
import org.example.Services.ItemService;
import org.example.Services.ItemServiceImpl;
import org.example.Utils.FormatMessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemServiceImplTest {
    ItemService item;

    @BeforeEach
    void instantiateItem(){
        Data.initiateItems();
    }

    /**
     * 
     */
    @Test
    @DisplayName("Positive Case - Add Item")
    void successToAdd() {
        int prev_size = Data.items.size();
        item = new ItemServiceImpl();
        item.create(new Item(7, "Daifuku Mochi", 15000));
        int new_size = Data.items.size();
        assertEquals(prev_size+1, new_size);
    }

    @Test
    @DisplayName("Negative Case - Add Item - Duplicate Issue")
    void duplicateIssue(){
        item = new ItemServiceImpl();
        String result = item.create(new Item(1, "Nasi Goreng", 150000));
        assertEquals(FormatMessageUtil.duplicateMessage(), result);
    }

    @Test
    @DisplayName("Negative Case - Add Item - Null Issue")
    void nullIssue(){
        
        item = new ItemServiceImpl();
        String result = item.create(null);
        assertEquals(FormatMessageUtil.errorMessageFormat("Kesalahan saat menambahkan Item"), result);
    }
    
    @Test
    @DisplayName("Positive Case - Delete Item ")
    void successDelete(){
        int size = Data.items.size();
        item = new ItemServiceImpl();
        item.delete(1L);
        int new_size = Data.items.size();
        assertEquals(size-1, new_size);
    }

    @Test
    @DisplayName("Negative Case - Delete Item - Not Found Issue")
    void notFoundDelete(){
        item = new ItemServiceImpl();
        String result = item.delete(10L);
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }

    @Test
    @DisplayName("Positive Case - Get Item By ID")
    void testGetItemById() {
        item = new ItemServiceImpl();
        Item itemExist = item.getItemById(1L);
        assertEquals("Nasi Goreng", itemExist.getName());
    }

    @Test
    @DisplayName("Positive Case - Update Item ")
    void successUpdate(){
        item = new ItemServiceImpl();
        item.update(1L, new Item(1L, "Mochi", 20000));
        Item newItem = item.getItemById(1L);
        assertEquals("Mochi", newItem.getName());
    }

    @Test
    @DisplayName("Negative Case - Update Item - Not Found Issue")
    void notFoundUpdate(){
        item = new ItemServiceImpl();
        String result = item.update(10L, new Item(1L, "Mochi", 20000));
        assertEquals(FormatMessageUtil.notFoundMessage(), result);
    }

}
