package Resource;

import java.util.List;

/**
 * 多个有序资源集合类
 * @param <R> 资源类泛型R
 */
public interface MultipleSortedResourceEntry<R> extends Resource {
    /**
     * 初始化集合类
     * @param list 传入一个有序的资源的list用于初始化，要求资源不能重复
     */
//    public void setResource(List<R> list);

    /**
     * 获得资源list
     * @return list
     */
    public List<R> getResource();
}
