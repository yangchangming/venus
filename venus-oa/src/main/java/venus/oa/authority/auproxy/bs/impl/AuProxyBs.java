/**
 * Copyright 2003-2010 UFIDA Software Engineering Co., Ltd. 
 */
package venus.oa.authority.auproxy.bs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import venus.frames.base.bs.BaseBusinessService;
import venus.frames.base.exception.BaseApplicationException;
import venus.oa.authority.auauthorizelog.bs.IAuAuthorizeLogBS;
import venus.oa.authority.auauthorizelog.vo.AuAuthorizeLogVo;
import venus.oa.authority.auproxy.bs.IAuProxyBs;
import venus.oa.authority.auproxy.bs.IProxyHistoryBs;
import venus.oa.authority.auproxy.vo.ProxyHistoryVo;
import venus.oa.helper.OrgHelper;
import venus.oa.history.bs.IHistoryLogBs;
import venus.oa.history.vo.HistoryLogVo;
import venus.oa.login.vo.LoginSessionVo;
import venus.oa.organization.auparty.vo.PartyVo;
import venus.oa.organization.aupartyrelation.bs.IAuPartyRelationBs;
import venus.oa.organization.aupartyrelation.vo.AuPartyRelationVo;
import venus.oa.util.DateTools;
import venus.oa.util.GlobalConstants;
import venus.pub.lang.OID;
import venus.springsupport.BeanFactoryHelper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zangjian
 * 
 */
@Service
public class AuProxyBs extends BaseBusinessService implements IAuProxyBs, venus.oa.authority.auproxy.util.IConstants {

    @Autowired
    private IHistoryLogBs proxyLogBs;

    @Autowired
    private IProxyHistoryBs proxyHistoryBs;

    @Autowired
    private IAuAuthorizeLogBS auAuthorizeLogBS;

    @Autowired
    private IAuPartyRelationBs auPartyRelationBs;

    /**
     * 关联用户时记录历史日志
     * 
     * @param partyId
     * @param parentRelId
     * @param relType
     * @param vo
     * @return
     */
    public OID addRelation(String partyId, String parentRelId, String relType, LoginSessionVo vo) {
        IAuPartyRelationBs relBs = (IAuPartyRelationBs) BeanFactoryHelper.getBean("auPartyRelationBs");
        OID oid = relBs.addPartyRelation(partyId, parentRelId, relType);// 调用接口添加团体关系
        AuPartyRelationVo relVo = relBs.find(oid.toString());
        Map map = new HashMap();
        map.put("OPERATERID", vo.getParty_id());
        map.put("OPERATERNAME", vo.getName());
        map.put("OPERATETYPE",
                GlobalConstants.HISTORY_LOG_INSERT_PROXY_RELATION);
        Timestamp now = DateTools.getSysTimestamp();
        map.put("SYSDATE", now);
        map.put("PROXYRELATIONVO", relVo);
        map.put("SOURCECODE", relVo.getCode());
        map.put("SOURCEORGTREE", OrgHelper.getOrgNameByCode(relVo.getCode(), false)); // 由于这里保存的是父节点，所以要显示最后一级节点
        //记录关联历史
        OID historyOid = proxyLogBs.insert(map);
        //记录代理业务历史
        addLog(historyOid,"1",now,relVo,vo);
        return oid;// 调用接口添加团体关系
    }

    /**
     * 删除用户关联时记录历史日志
     * 
     * @param ids
     * @param vo
     */
    public void deleteMulti(String ids[], LoginSessionVo vo) {
        for (int i = 0; i < ids.length; i++) {
            Map map = new HashMap();
            map.put("OPERATERID", vo.getParty_id());
            map.put("OPERATERNAME", vo.getName());
            map.put("OPERATETYPE",
                    GlobalConstants.HISTORY_LOG_DELETE_PROXY_RELATION);
            Timestamp now = DateTools.getSysTimestamp();
            map.put("SYSDATE", now);
            AuPartyRelationVo relVo = auPartyRelationBs.find(ids[i]);
            map.put("PROXYRELATIONVO", relVo);
            map.put("SOURCECODE", relVo.getCode());
            map.put("SOURCEORGTREE", OrgHelper.getOrgNameByCode(relVo.getCode(), false));
            //记录解除关联历史
            proxyLogBs.insert(map);
            //记录代理业务历史
            updateLog("2",now, relVo,vo);
            // 删除团体关系
            auPartyRelationBs.deletePartyRelation(ids[i]);
        }
    }
    
    /**
     * 记录代理业务的历史
     * @param historyOid 代理历史id
     * @param operateType 操作类型：1为关联人员，2为解除关联
     * @param now 操作时间
     * @param relVo 关系vo
     * @param vo 安全上下文
     */
    private void addLog(OID historyOid, String operateType, Timestamp now, AuPartyRelationVo relVo, LoginSessionVo vo){
         //准备记录代理业务历史
        List al = proxyLogBs.queryByCondition(" SOURCE_CODE='"+relVo.getParent_code()+"' ", " A.ID DESC ");
        HistoryLogVo historyLogVo = null;
        if(al.size()>0){
            historyLogVo = (HistoryLogVo)al.get(0);
        }else{
            throw new BaseApplicationException(venus.frames.i18n.util.LocaleHolder.getMessage("venus.authority.Lack_of_agency_history_"));
        }
        List aual = auAuthorizeLogBS.queryByCondition(-1,-1," VISITOR_CODE='"+relVo.getParent_code()+"' "," OPERATE_DATE DESC,AUTHORIZE_TAG DESC  ");
        AuAuthorizeLogVo authorizeLogVo = new AuAuthorizeLogVo();
        if(aual.size()>0){
            authorizeLogVo = (AuAuthorizeLogVo)aual.get(0);
        }
        ProxyHistoryVo proxyHistoryVo = new ProxyHistoryVo();
        proxyHistoryVo.setProxy_proxyer_history_id(historyOid.toString());
        proxyHistoryVo.setProxy_history_id(historyLogVo.getId());
        proxyHistoryVo.setProxy_authorize_history_id(authorizeLogVo.getAuthorize_tag());
        proxyHistoryVo.setOperater_id(vo.getParty_id());
        proxyHistoryVo.setOperater_name(vo.getName());
        proxyHistoryVo.setOperater_type(operateType);//addRelation or deleteRelation
        proxyHistoryVo.setLogin_name(vo.getLogin_id());
        proxyHistoryVo.setOperater_date(now);
        PartyVo proxyVo = OrgHelper.getPartyVoByID(relVo.getParent_partyid());//proxy vo
        PartyVo sponsorVo = OrgHelper.getPartyVoByID(proxyVo.getOwner_org());//sponsor vo
        proxyHistoryVo.setSponsor(sponsorVo.getName());//甲方
        proxyHistoryVo.setSponsor_id(sponsorVo.getId());
        proxyHistoryVo.setProxy(proxyVo.getName());//中间方
        proxyHistoryVo.setProxy_id(relVo.getParent_partyid());
        proxyHistoryVo.setRecipient(relVo.getName());//乙方
        proxyHistoryVo.setRecipient_id(relVo.getPartyid());
        //记录代理业务历史
        proxyHistoryBs.insert(proxyHistoryVo);
    }
    
    /**
     * 代理业务的历史
     *
     * @param operateType 操作类型：1为关联人员，2为解除关联
     * @param now 操作时间
     * @param relVo 关系vo
     * @param vo 安全上下文
     */
    private void updateLog(String operateType, Timestamp now, AuPartyRelationVo relVo, LoginSessionVo vo){
        List proxyHis = proxyHistoryBs.queryByCondition(-1,-1," PROXY_ID='"+relVo.getParent_partyid()+"' AND RECIPIENT_ID='"+relVo.getPartyid()+"'"," ID DESC  ");
        ProxyHistoryVo proxyHisVo = new ProxyHistoryVo();
        if(proxyHis.size()>0){
            proxyHisVo = (ProxyHistoryVo)proxyHis.get(0);
        }
        ProxyHistoryVo proxyHistoryVo = new ProxyHistoryVo();
        proxyHistoryVo.setId(proxyHisVo.getId());
        proxyHistoryVo.setOperater_type(operateType);
        proxyHistoryVo.setCanel_date(now);
        proxyHistoryVo.setCanel_id(vo.getParty_id());
        proxyHistoryVo.setCanel_name(vo.getName());
        proxyHistoryBs.update(proxyHistoryVo);
    }
}

