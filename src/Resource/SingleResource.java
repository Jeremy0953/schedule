package Resource;

/**
 * 单个资源
 * @param <R> 资源类的泛型参数
 */
public interface SingleResource<R> extends Resource {
    /**
     * 设置资源
     * @param r 将r设置为该资源
     */
//    public void setResource(R r);

    /**
     * 获得资源
     * @return 返回R类型的单个资源
     */
    public R getResource();
}
