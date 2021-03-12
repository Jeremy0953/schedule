package debug;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * In a Store, there are some kinds of items to sell. Each item has a price.
 * 
 * However, there are some special offers, and a special offer consists of one
 * or more different kinds of items with a sale price.
 * 
 * You are given the each item's price, a set of special offers, and the number
 * we need to buy for each item. The job is to output the lowest price you have
 * to pay for exactly certain items as given, where you could make optimal use
 * of the special offers.
 * 
 * Each special offer is represented in the form of an array, the last number
 * represents the price you need to pay for this special offer, other numbers
 * represents how many specific items you could get if you buy this offer.
 * 
 * You could use any of special offers as many times as you want.
 * 
 * Example 1:
 * 
 * Input: [2,5], [[3,0,5],[1,2,10]], [3,2] Output: 14
 * 
 * Explanation:
 * 
 * There are two kinds of items, A and B. Their prices are $2 and $5
 * respectively.
 * 
 * In special offer 1, you can pay $5 for 3A and 0B
 * 
 * In special offer 2, you can pay $10 for 1A and 2B.
 * 
 * You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer
 * #2), and $4 for 2A.
 * 
 * Example 2:
 *
 *
 * Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1] Output: 11
 * 
 * Explanation:
 * 
 * The price of A is $2, and $3 for B, $4 for C.
 * 
 * You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C.
 * 
 * You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer
 * #1), and $3 for 1B, $4 for 1C.
 * 
 * You cannot add more items, though only $9 for 2A ,2B and 1C.
 * 
 * 
 * Note:
 * 
 * 1. There are at most 6 kinds of items, 100 special offers.
 * 
 * 2. For each item, you need to buy at most 6 of them.
 * 
 * 3. You are not allowed to buy more items than you want, even if that would
 * lower the overall price.
 * 
 * ---------------------------------------------------------------------------------------
 * Please debug and fix potential bugs in the following code and make it execute correctly,
 * robust, and completely in accordance with above requirements.
 * 
 * DON'T change the initial logic of the code.
 * ---------------------------------------------------------------------------------------
 * 
 */
public class LowestPrice {

	/**
	 * 计算购买商品所需最少的价钱
	 * @param price 价格列表 size<=6，且里面所有的元素都是正数
	 * @param special 折扣列表 size<=100,并且里面每个元素（就是每个列表的长度都应该是price的长度+1），且里面每个列表中的除了最后一位剩下的都非负，最后一个为正数
	 * @param needs 需求列表 size与price的size相同，并且里面每个元素都小于等于6大于等于0
	 * @return 返回所需最低价格
	 */
	public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		return shopping(price, special, needs);
	}
	/**
	 * 计算购买商品所需最少的价钱
	 * @param price 价格列表 size<=6，且里面所有的元素都是正数
	 * @param special 折扣列表 size<=100,并且里面每个元素（就是每个列表的长度都应该是price的长度+1），且里面每个列表中的除了最后一位剩下的都非负，最后一个为正数
	 * @param needs 需求列表 size与price的size相同，并且里面每个元素都小于等于6大于等于0
	 * @return 返回所需最低价格
	 */
	public int shopping(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		assert price.size()<=6;
		assert needs.size()==price.size();
		for (int i = 0;i<price.size();i++)
		{
			assert price.get(i)>0;
		}
		for (List<Integer> s:special)
		{
			assert s.size() == price.size()+1;
			for(int i = 0;i<s.size()-1;i++)
				assert s.get(i)>=0;
			assert s.get(s.size()-1)>0;
		}
		for (int i = 0;i<needs.size();i++)
		{
			assert needs.get(i)>=0;
		}
		//判断前置条件
		int j = 0, res = dot(needs, price);
		for (List<Integer> s : special) {
			List<Integer> clone = new ArrayList<>(needs);
			for (j = 0; j < needs.size(); j++)
			//防止j越界访问，所以不能带等号
			{
				int diff = clone.get(j) - s.get(j);
				if (diff < 0)
					//由于等于零的情况是刚好可以用优惠，所以不用加等号
					break;
				//由于出现了不符合的情况，整组数据都不能用了，所以要break
				clone.set(j, diff);
			}
			if (j == needs.size())
				res = Math.min(res, s.get(j ) + shopping(price, special, clone));
			//访问s的最后一个元素，下标应该为j
		}
		return res;
	}
	/**
	 * 计算购买商品所需的价钱(原价）
	 * @param b 价格列表 size<=6，且里面所有的元素都是正数
	 * @param a 需求列表 size与a的size相同，并且里面每个元素都小于等于6大于等于0
	 * @return 返回所需最低价格
	 */
	public int dot(List<Integer> a, List<Integer> b) {
		assert b.size()<=6;
		assert b.size()==a.size();
		for (int i = 0;i<a.size();i++)
		{
			assert a.get(i)>=0;
			assert b.get(i)>0;
		}
		int sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i) * b.get(i);
		}
		return sum;
	}

}
