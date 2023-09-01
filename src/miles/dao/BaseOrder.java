package miles.dao;

/**
 * @author by Miles
 * @date 2023/8/31
 */
public interface BaseOrder {
    /**
     * 查询订单的总数
     *
     * @param id
     * @param <T>
     * @return
     */
    <T> Integer queryForPageTotalCount(T id);
}
