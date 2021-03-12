package Location;

/**
 * Location 接口
 */
public interface Location {
    /**
     * 获取地名
     * @return 返回名字
     */
    public String getName();

    /**
     * 获取是否可共享信息
     * @return 若可共享true，否则false
     */
    public boolean isShareable();
}
