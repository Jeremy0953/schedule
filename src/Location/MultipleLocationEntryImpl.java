package Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 多个Location组合的实现类
 * AF：将list映射到一个含有多个地点的列表
 * RI: location列表中不能有重复的地址
 * rep from exposure:进行防御性复制
 */
public class MultipleLocationEntryImpl implements MultipleLocationEntry {
    protected List<Location> list = new ArrayList<>();

    /**
     * 构造方法
     * @param list 传入的地点列表
     */
    public MultipleLocationEntryImpl(List<? extends Location> list) {
        for (int i = 0;i<list.size();i++)
        {
            this.list.add(list.get(i));
        }
        checkRep();
    }

    /**
     * 返回地点列表，进行防御性复制
     * @return
     */
    @Override
    public List<Location> getLocation()
    {
        List<Location> ret = new ArrayList<>();
        for (int i = 0;i<list.size();i++)
        {
            ret.add(list.get(i));
        }
        return ret;
    }
    /**
     * make sure RI = true;
     */
    private void checkRep()
    {
        for (int i = 0;i<list.size()-1;i++)
        {
            for (int j = i+1;j<list.size();j++)
            {
                if (list.get(i).equals(list.get(j)))
                    assert false;
            }
        }
    }
}
