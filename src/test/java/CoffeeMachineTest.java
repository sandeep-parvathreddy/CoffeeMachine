import com.dunzo.mapper.CoffeeMachineRequestMapper;
import com.dunzo.model.CoffeeMachineResponse;
import com.dunzo.service.CoffeeProcessor;
import com.dunzo.service.impl.DefaultCoffeeProcessorService;
import com.dunzo.exception.CoffeeMachineRequestProcessingException;
import com.dunzo.exception.CoffeeMachineRequestValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public class CoffeeMachineTest {

    private CoffeeProcessor coffeeProcessor;

    @Before
    public void init(){
        coffeeProcessor = new DefaultCoffeeProcessorService();
    }

    @Test
    public void testAllBeveragesPrepared(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":3},\"total_items_quantity\":{\"hot_water\":1000,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":200,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30}}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertArrayEquals(coffeeMachineResponse.getResponse().toArray(),new String[]{"hot_tea is prepared", "hot_coffee is prepared", "black_tea is prepared"});
    }

    @Test
    public void testOnlyOneBeverageIsPrepared(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":3},\"total_items_quantity\":{\"hot_water\":100,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":100,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"green_tea\":{\"hot_water\":100,\"ginger_syrup\":30,\"sugar_syrup\":50,\"green_mixture\":30}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertTrue(coffeeMachineResponse.getResponse().contains("hot_coffee is prepared"));
        Assert.assertEquals(1,coffeeMachineResponse.getResponse().stream().filter(e-> e.contains("is prepared")).count());
    }

    @Test
    public void testOnlyTwoBeveragesPrepared(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":3},\"total_items_quantity\":{\"hot_water\":500,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":100,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"green_tea\":{\"hot_water\":100,\"ginger_syrup\":30,\"sugar_syrup\":50,\"green_mixture\":30}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertEquals(2,coffeeMachineResponse.getResponse().stream().filter(e-> e.contains("is prepared")).count());
    }

    @Test
    public void testItemNotAvailableToPrepare(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":3},\"total_items_quantity\":{\"hot_water\":500,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":100,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"green_tea\":{\"hot_water\":100,\"ginger_syrup\":30,\"sugar_syrup\":50,\"green_mixture\":30}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertTrue(coffeeMachineResponse.getResponse().contains("green_tea cannot be prepared because green_mixture is not available"));

    }

    @Test
    public void testAllBeveragesPreparedInSequence(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":1},\"total_items_quantity\":{\"hot_water\":1000,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":200,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30}}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertArrayEquals(coffeeMachineResponse.getResponse().toArray(),new String[]{"hot_tea is prepared", "hot_coffee is prepared", "black_tea is prepared"});
    }

    @Test
    public void testNoBeveragePrepared(){
        String input = "{\"machine\":{\"outlets\":{\"count_n\":2},\"total_items_quantity\":{\"hot_water\":99,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":200,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30}}}}}";
        CoffeeMachineResponse coffeeMachineResponse = processRequest(input);
        Assert.assertEquals(0,coffeeMachineResponse.getResponse().stream().filter(e-> e.contains("is prepared")).count());
    }

    @Test(expected = CoffeeMachineRequestValidationException.class)
    public void testNoOutletsToPrepare() throws CoffeeMachineRequestProcessingException, CoffeeMachineRequestValidationException {
        String input = "{\"machine\":{\"outlets\":{\"count_n\":0},\"total_items_quantity\":{\"hot_water\":99,\"hot_milk\":500,\"ginger_syrup\":100,\"sugar_syrup\":200,\"tea_leaves_syrup\":100},\"beverages\":{\"hot_tea\":{\"hot_water\":200,\"hot_milk\":100,\"ginger_syrup\":10,\"sugar_syrup\":10,\"tea_leaves_syrup\":30},\"hot_coffee\":{\"hot_water\":100,\"ginger_syrup\":30,\"hot_milk\":400,\"sugar_syrup\":50,\"tea_leaves_syrup\":30},\"black_tea\":{\"hot_water\":300,\"ginger_syrup\":30,\"sugar_syrup\":50,\"tea_leaves_syrup\":30}}}}}";
        coffeeProcessor.process(CoffeeMachineRequestMapper.mapFromJson(input));
    }

    private CoffeeMachineResponse processRequest(String input) {
        CoffeeMachineResponse coffeeMachineResponse = null;
        try {
            coffeeMachineResponse = coffeeProcessor.process(CoffeeMachineRequestMapper.mapFromJson(input));
        } catch (Exception e) {
            System.out.println("Exception in processing the request");
        }
        return coffeeMachineResponse;
    }


}

