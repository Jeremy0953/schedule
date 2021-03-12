package Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleSortedResourceEntryImpl<R> implements MultipleSortedResourceEntry<R> {
    /**
     * AF：将resources映射到有序资源列表中
     * RI: 列表中无重复元素
     * rep from exposure:返回时返回immutable形式，设置初始化的时候进行防御性复制
     */
    List<R> resources = new ArrayList<>();

    /**
     * 初始化资源列表
     * @param list 传入一个有序的资源的list用于初始化，要求资源不能重复
     */
/*    @Override
    public void setResource(List<R> list) {
        for (int i = 0;i<list.size();i++)
        {
            resources.add(list.get(i));
        }
        checkRep();
    }*/

    /**
     * 获得资源列表
     * @return 不可改的资源列表
     */
    @Override
    public List<R> getResource() {
        return Collections.unmodifiableList(resources);
    }

    /**
     * 构造方法
     * @param resources 资源列表
     */
    public MultipleSortedResourceEntryImpl(List<R> resources) {
        for (int i = 0;i<resources.size();i++)
        {
            this.resources.add(resources.get(i));
        }
        checkRep();
    }
    /**
     * make sure RI == true
     */
    private void checkRep()
    {
        for (int i = 0;i<resources.size()-1;i++)
        {
            for (int j = i+1;j<resources.size();j++)
            {
                if (resources.get(i).equals(resources.get(j)))
                    assert false;
            }
        }
    }

}
