package venus.portal.website.dao;

import venus.portal.website.model.Website;

import java.util.List;

public interface IWebsiteDao {
    
    /**
     * 插入单条记录，用Oid作主键，把null全替换为""
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     */
    public void insert(Website vo);
    
    /**
     * 删除单条记录
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     */
    public void delete(Website vo);
    

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     */
    public Website findWebsiteById(String id);

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public void update(Website vo);

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     */
    public int getRecordCount(String queryCondition);
    
    /**
     * 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
     * 
     * @param no 当前页数
     * @param size 每页记录数
     * @param queryCondition 查询条件
     * @param orderStr 排序字符 
     * @return 查询到的VO列表
     */
    public List queryByCondition(int no, int size, String queryCondition, String orderStr);
    /**
     * 根据name进行查询
     * 
     * @param name 用于查找的name
     * @return 查询到的VO对象
     */
    public Website getWebsiteByName(String name);

    /**
     * 根据是否是默认站点进行查询
     *@param 
     *@return List
     *@exception  
     */ 
    public List getWebsiteByIsDefault(String is);
    
    /**
     * 查询所有的站点
     *@param 
     *@return void
     *@exception  
     *@author zhangrenyang 
     *@date  2011-10-20上午06:21:42
     */ 
    public List queryAll();
    /**
     * 根据websiteCode进行查询
     *
     * @param websiteCode 用于查找的websiteCode
     * @return 查询到的VO对象
     */
    public Website getWebsiteByCode(String websiteCode);
}
