package debug;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LowestPriceTest {

    LowestPrice lowestprice = new LowestPrice();
    List<Integer> price = null;
    List<Integer> needs = null;
    List<List<Integer>> special = null;

    //商品个数为0个，1个，多个
    //对某个商品的需求个数为0个，1个，多个
    //对每个商品打折售卖个数<需求个数，=需求个数， >需求个数


    //1.测试商品个数为0的情况
    //1.1测试对该商品需求为0个的情况
    //1.1.1测试对该商品打折个数为0的情况
    //1.1.2测试对该商品打折个数为多个的情况
    //1.1.3测试以上两种打折情况一起出现的情况

    //1.2测试对该商品需求为1个或多个的情况
    //1.2.1测试对该商品打折个数小于需求个数的情况
    //1.2.2测试对该商品打折个数等于需求个数的情况
    //1.2.3测试对该商品打折个数大于需求个数的情况
    //1.2.4测试以上三种种打折情况两两结合或者一起出现的情况

    @Test
    public void test_1() {
		/*
		price = Arrays.asList();
		needs = Arrays.asList(0);
		special = Arrays.asList(Arrays.asList(0,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(0);
		special = Arrays.asList(Arrays.asList(2,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


		price = Arrays.asList();
		needs = Arrays.asList(0);
		special = Arrays.asList(Arrays.asList(0,0),Arrays.asList(2,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(0,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(2,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(3,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(0,0),Arrays.asList(2,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(2,0),Arrays.asList(3,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(3,0),Arrays.asList(0,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		price = Arrays.asList();
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(3,0),Arrays.asList(2,0),Arrays.asList(0,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));
		*/
    }

    //2测试商品个数为1的情况
    //2.1测试对该商品需求为0个的情况
    //2.1.1测试对该商品打折个数为0的情况
    //2.1.2测试对该商品打折个数为多个的情况
    //2.1.3测试以上两种打折情况一起出现的情况

    //2.2测试对该商品需求为1个或多个的情况
    //2.2.1测试对该商品打折个数小于需求个数的情况
    //2.2.2测试对该商品打折个数等于需求个数的情况
    //2.2.3测试对该商品打折个数大于需求个数的情况
    //2.2.4测试以上三种种打折情况两两结合或者一起出现的情况

    @Test
    public void test_2() {
		/*
		price = Arrays.asList(5);
		needs = Arrays.asList(0);
		special = Arrays.asList(Arrays.asList(0,0));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


		price = Arrays.asList(5);
		needs = Arrays.asList(0);
		special = Arrays.asList(Arrays.asList(0,0),Arrays.asList(2,8));
		assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

		//打折售卖小于需求且为0
		price = Arrays.asList(5);
		needs = Arrays.asList(2);
		special = Arrays.asList(Arrays.asList(0,0));
		assertEquals(10,lowestprice.shoppingOffers(price, special, needs));
		 */

        price = Arrays.asList(5);
        needs = Arrays.asList(0);
        special = Arrays.asList(Arrays.asList(2,8));
        assertEquals(0,lowestprice.shoppingOffers(price, special, needs));

        //打折售卖小于需求不为0
        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(1,4));
        assertEquals(8,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(2,7));
        assertEquals(7,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(3,9));
        assertEquals(10,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(1,4),Arrays.asList(2,7));
        assertEquals(7,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(2,7),Arrays.asList(3,9));
        assertEquals(7,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(3,9),Arrays.asList(1,4));
        assertEquals(8,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(5);
        needs = Arrays.asList(2);
        special = Arrays.asList(Arrays.asList(3,9),Arrays.asList(2,7),Arrays.asList(1,4));
        assertEquals(7,lowestprice.shoppingOffers(price, special, needs));



    }

    //3测试商品个数为多个的情况
    //3.1测试对全部商品需求为0个的情况
    //3.1.1测试对某些商品打折个数为0的情况
    //3.1.2测试对某些商品打折个数为多个的情况
    //3.1.3测试以上两种打折情况一起出现的情况

    //3.2测试对部分商品需求为0个的情况
    //3.2.1测试对某些商品打折个数为0的情况
    //3.2.2测试对某些商品打折个数为多个的情况
    //3.2.3测试以上两种打折情况一起出现的情况

    //3.3测试对商品需求为1个或多个的情况
    //3.3.1测试对某些商品打折个数小于需求个数的情况
    //3.3.2测试对某些商品打折个数等于需求个数的情况
    //3.3.3测试对某些商品打折个数大于需求个数的情况
    //3.3.4测试以上三种种打折情况两两结合或者一起出现的情况

    @Test
    public void test_3() {
        price = Arrays.asList(2,5);
        needs = Arrays.asList(0,0);
        special = Arrays.asList(Arrays.asList(3,0,5));
        assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(0,0);
        special = Arrays.asList(Arrays.asList(1,2,10));
        assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(0,0);
        special = Arrays.asList(Arrays.asList(3,0,5),Arrays.asList(1,2,10));
        assertEquals(0,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(0,2);
        special = Arrays.asList(Arrays.asList(3,0,5));
        assertEquals(10,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,6);
        needs = Arrays.asList(0,2);
        special = Arrays.asList(Arrays.asList(1,2,10));
        assertEquals(12,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,6);
        needs = Arrays.asList(0,2);
        special = Arrays.asList(Arrays.asList(3,0,5),Arrays.asList(1,2,10));
        assertEquals(12,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(1,0,1));
        assertEquals(13,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(3,2,10));
        assertEquals(10,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(4,2,10));
        assertEquals(16,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(1,0,1),Arrays.asList(4,2,10));
        assertEquals(13,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(3,0,5),Arrays.asList(1,2,10));
        assertEquals(14,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(3,0,5),Arrays.asList(1,2,10));
        assertEquals(14,lowestprice.shoppingOffers(price, special, needs));

        price = Arrays.asList(2,5);
        needs = Arrays.asList(3,2);
        special = Arrays.asList(Arrays.asList(4,2,11),Arrays.asList(3,0,5),Arrays.asList(1,2,10));
        assertEquals(14,lowestprice.shoppingOffers(price, special, needs));


        price = Arrays.asList(2,3,4);
        needs = Arrays.asList(1,2,1);
        special = Arrays.asList(Arrays.asList(1,1,0,4),Arrays.asList(2,2,1,9));
        assertEquals(11,lowestprice.shoppingOffers(price, special, needs));

    }
}
