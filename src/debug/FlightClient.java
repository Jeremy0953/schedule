package debug;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Note that this class may use the other two class: Flight and Plane.
 * 
 * Debug and fix errors. DON'T change the initial logic of the code.
 *
 */
public class FlightClient {
	
	/**
	 * Given a list of flights and a list of planes, suppose each flight has not yet been
	 * allocated a plane to, this method tries to allocate a plane to each flight and ensures that
	 * there're no any time conflicts between all the allocations. 
	 * For example:
	 *  Flight 1 (2020-01-01 8:00-10:00) and Flight 2 (2020-01-01 9:50-10:40) are all allocated 
	 *  the same plane B0001, then there's conflict because from 9:50 to 10:00 the plane B0001
	 *  cannot serve for the two flights simultaneously.
	 *  
	 * @param planes a list of planes
	 * @param flights a list of flights each of which has no plane allocated
	 * @return false if there's no allocation solution that could avoid any conflicts
	 */
	
	public boolean planeAllocation(List<Plane> planes, List<Flight> flights) {
		boolean bFeasible = true;
		Random r = new Random();
		boolean []flags = new boolean[planes.size()];//为每一架飞机设置一个标志位来判断这个飞机是否被考虑
		//Collections.sort(flights);
		
		for (Flight f : flights) {
			boolean bAllocated = false;
			Arrays.fill(flags,false);//当开始考虑某个航班的时候将所有的飞机标志位都置为false
			while (!bAllocated) {
				boolean flag = true;//flag用于判断所有飞机标志位的情况
				for (int j = 0; j < planes.size(); j++)
				{
					flag = flag&&flags[j];
				}
				if (flag)//如果所有的飞机标志位都为true说明都考虑过了，但是该航班还是没法分配飞机
				{
					bFeasible = false;
					return bFeasible;
					//就直接返回false
					//通过增加标志位的方法防止某航班无法分配时出现死循环
				}
				int i = r.nextInt(planes.size());//随机得到一个飞机
				if (flags[i]==true)
					continue;
				//若该飞机已经考虑过则跳过，再取下一个随机数
				else
					flags[i]=true;
				//若没考虑过则设置标志位之后进行下面的判断
				Plane p = planes.get(i);
				Calendar fStart = f.getDepartTime();
				Calendar fEnd = f.getArrivalTime();
				boolean bConflict = false;
				
				for (Flight t : flights) {
					if (t.equals(f))
						continue;//不再和自己比较
					Plane q = t.getPlane();
					if (! p.equals(q))//由于q有可能为null所以要改
						continue;
					
					Calendar tStart = t.getDepartTime();
					Calendar tEnd = t.getArrivalTime();
					//对于Calendar的比较采用before，after而不是"<"或者">"
					if ((fStart.after(tStart)&&fStart.before(tEnd))|| (tStart.after(fStart) && tStart.before(fEnd))) {
						bConflict = true;
						break;
					}
				}
				
				if (!bConflict) {
					f.setPlane(p);
					break;
				}

			}
		}
		return bFeasible;
	}
}
