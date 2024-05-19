import java.util.List;

import org.example.Data;
import org.example.Models.Item;
import org.example.Services.ItemService;
import org.example.Services.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemServiceTest {

    ItemService item;

    @BeforeEach
    void instantiateItem(){
        item = new ItemServiceImpl();
    }

    @Test
    @DisplayName("Positive Case - Add Item")
    void addItem(){
        int prev_size = Data.items.size();
        item.create(new Item(7, "Daifuku Mochi", 15000));
        int new_size = Data.items.size();
        Assertions.assertEquals(prev_size+1, new_size);
    }
}
