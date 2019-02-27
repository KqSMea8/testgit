package com.hanweb.jmp.newspush.news.dao;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.newspush.news.entity.NewsDetail;


/**
 * 消息详情
 * 
 * @author Wangjw
 * 
 */
public class NewsDetailDAO extends BaseJdbcDAO<Integer, NewsDetail> {
	
	/**
	 * 通过iid获取详情实体
	 * 
	 * @param iid
	 *            iid
	 * @return 消息实体
	 */
	public NewsDetail findByIid(Integer iid) {
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		NewsDetail infoDetail = this.queryForEntity(query);
		return infoDetail;
	}
	
	/**
	 * 通过infoId,uid获取详情实体
	 * 
	 * @param infoId
	 *            消息ID
	 * @param uid 用户id
	 * @return 消息实体
	 */
	public List<NewsDetail> findByInfoId(Integer infoId, Integer uid, int infoKind) {
		String sql = this.getEntitySql() 
			+ " WHERE infoid=:infoId AND usid=:usid AND infokind=:infokind";
		Query query = createQuery(sql);
		query.addParameter("infoId", infoId);
		query.addParameter("usid", uid);
		query.addParameter("infokind", infoKind);
		List<NewsDetail> infoDetail = this.queryForEntities(query);
		return infoDetail;
	}

	/**
	 * 根据消息id删除详情
	 * @param infoId 消息id
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean removeByInfoId(Integer infoId){
		String sql = "DELETE FROM " + Tables.INFODETAIL + " WHERE infoid=:infoid";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoId);
		return this.delete(query);
	}
	
	/**
	 * 获得用户下推送消息数
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3
	 * @return int 消息数
	 */
	public int findCountByUsids(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.INFODETAIL + " WHERE usid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 清空某条信息的详情
	 * @param infoId 信息id
	 * @return 
	 */
	public void clean(Integer infoId){
		String sql = "DELETE FROM " + Tables.INFODETAIL + " WHERE infoid=:infoid";
		Query query = this.createQuery(sql);
		query.addParameter("infoid", infoId);
		this.execute(query);
	}
	
	/**
	 * 获得消息推给用户的总用户数量
	 * @param infoId 消息ID
	 * @return 数量
	 */
	public int getInfoAllCount(int infoId){
		String sql = "SELECT COUNT(iid) FROM " + Tables.INFODETAIL + " WHERE infoid=:infoId";
		Query query = createQuery(sql);
		query.addParameter("infoId", infoId);
		int num = this.queryForInteger(query);

		return num;
	}
	
	/**
	 * 获得消息推给用户的数量
	 * @param infoId 消息ID
	 * @param state 0未发送 1已发送 2已阅读
	 * @return 数量
	 */
	public int getInfoCount(int infoId, int state){
		String sql = "SELECT COUNT(iid) FROM " 
			+ Tables.INFODETAIL + " WHERE infoid=:infoId AND state=:state";
		Query query = createQuery(sql);
		query.addParameter("infoId", infoId);
		query.addParameter("state", state);
		int num = this.queryForInteger(query);

		return num;
	}
	
	/**
	 * 获得该消息未阅读的实体
	 * @param infoId 消息ID
	 * @return 
	 */
	public List<NewsDetail> getNoReadByInfoId(int infoId){
		if(infoId == 0){
			return null;
		}
		String sql = getEntitySql() + " WHERE infoid=:infoid AND state<>2";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoId);
		return this.queryForEntities(query);
	} 
}
