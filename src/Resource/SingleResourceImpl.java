package Resource;

public class SingleResourceImpl<R> implements SingleResource<R> {
    private R resource;
    /**
     * AF:将resource映射为单个的R类型资源的一个映射到代表单个资源R的集合中
     * RI:resource!=null
     * rep from exposure:将resource设置为private类型，且该项目中的resource均为immutable类型
     */

    /**
     * 构造方法
     * @param resource 将resource设置为该单个资源
     */
    public SingleResourceImpl(R resource) {
        this.resource = resource;
        checkRep();
    }
    /**
     * 设置资源
     * @param r 将r设置为该资源
     */
/*    @Override
    public void setResource(R r) {
        resource = r;
        checkRep();
    }*/
    /**
     * 获得资源
     * @return 返回R类型的单个资源
     */
    @Override
    public R getResource() {
        return resource;
    }

    /**
     * 保证RI
     */
    private void checkRep()
    {
        if (resource==null)
            assert false;
    }
}
