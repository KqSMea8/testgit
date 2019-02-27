package com.hanweb.jmp.cms.dao.comment;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.comment.Comment;
import com.hanweb.jmp.constant.Tables;

public class CommentDAO extends BaseJdbcDAO<Integer, Comment>{
	
	/**
	 * getSql
	 * @return String
	 */
	private String getSql(){
		String sql = "SELECT a.iid, a.siteid, a.cateid, a.infoid, a.address," 
				   + " a.content, a.createtime, a.clienttype, a.state, a.loginid, a.type," 
				   + " (SELECT ou.name FROM complat_outsideuser ou WHERE ou.loginname = a.loginid " 
				   + " AND ou.siteid=:siteid) name," 
				   + " (SELECT COUNT(1) FROM jmp_good_record gr WHERE gr.titleid = a.iid " 
				   + " AND gr.siteid=:siteid AND gr.type=3) goodCount," 
				   + " (SELECT COUNT(1) FROM jmp_good_record gr WHERE gr.titleid = a.iid " 
				   + " AND gr.siteid=:siteid AND gr.type=3 AND gr.uuid=:uuid) mygoodCount," 
				   + " (SELECT ou.headurl FROM complat_outsideuser ou WHERE ou.loginname = a.loginid " 
				   + " AND ou.siteid=:siteid) headurl" 
				   + " from " 
				   + Tables.COMMENT 
				   + " a";
		return sql;
	} 
	
	/**
	 * 更新审核状态
	 * @param idList List<Integer>
	 * @param state state
	 * @return boolean
	 */
	public boolean updateStateByIds(List<Integer> idList, Integer state){
		UpdateSql updateSql = new UpdateSql(Tables.COMMENT);
		updateSql.addInt("state", state);
		updateSql.setWhere(" iid in (:ids)");
		updateSql.addWhereParam("ids", idList);
		return this.update(updateSql);
	}
	
	/**
	 * 分页请求评论
	 * @param titleid 信息(报料)id
	 * @param commentid 评论id
	 * （刷新时，客户端若本地不存库，则传0，
	 * 若存库，则传最上面一条评论；更多时，传最下面一条评论）
	 * @param page 请求数目
	 * @param siteid Integer
	 * @param ctype 评论类型   1：正文  2：报料
	 * @param type 客户端操作类型   0：刷新   1：更多
	 * @return List<Comment>
	 */
	public List<Comment> findByInfoId(Integer siteid, Integer titleid, Integer commentid, Integer page, 
			                          Integer ctype, Integer type, String uuid){
		String sql = this.getSql() + " WHERE state=1 AND siteid=:siteid " 
		           + " AND infoid=:infoid AND type=:type";
		if(NumberUtil.getInt(type) == 0){
			if(NumberUtil.getInt(commentid) > 0){
				sql += " AND iid > :iid";
			}
		}else{
			if(NumberUtil.getInt(commentid) > 0){
				sql += " AND iid < :iid";
			}
		}
		sql += " ORDER BY iid DESC";
		Query query = this.createQuery(sql);
		query.setStart(0);	
		query.setEnd(page);
		query.addParameter("siteid", siteid);
		query.addParameter("infoid", titleid);
		query.addParameter("type", ctype);
		query.addParameter("uuid", uuid); 
		if(NumberUtil.getInt(commentid) > 0){
			query.addParameter("iid", commentid);
		}
		return this.queryForEntities(query);
	}
	
	/**
	 * 查找文章的评论数
	 * @param titleid Integer
	 * @param ctype Integer
	 * @return int
	 */
	public int findCountByInfoId(Integer titleid, Integer ctype){
		String sql = " SELECT COUNT(*) FROM " + Tables.COMMENT 
	               + " WHERE infoid=:infoid AND type=:type";
		Query query = this.createQuery(sql);
		return this.queryForInteger(query);
	}
	
	/**
	 * 根据信息、报料ids删除评论
	 * @param infoIds ids
	 * @param type 类型 1：信息  2：报料
	 * @return 是否成功
	 */
	public boolean deleteByInfoIds(List<Integer> infoIds, Integer type){
		String sql = "DELETE FROM " + Tables.COMMENT + " WHERE infoid in(:infoids) AND type=:type";
		Query query = createQuery(sql);
		query.addParameter("infoids", infoIds);
		query.addParameter("type", type);
		return this.delete(query);
	}
	
}