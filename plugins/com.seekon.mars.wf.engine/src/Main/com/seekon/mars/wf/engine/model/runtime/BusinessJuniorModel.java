package com.seekon.mars.wf.engine.model.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.seekon.mars.wf.engine.internal.mapper.WFDaoFactory;
import com.seekon.mars.wf.engine.model.participant.StaffModel;
import com.seekon.mars.wf.engine.util.BusinessJuniorExp;

/**
 * 
 * 业务上下级
 * 表as_wf_business_superior
 *
 */
public class BusinessJuniorModel implements java.io.Serializable {

  private static final long serialVersionUID = 3613166031513138481L;

  public static String COMMON_CODE = "#"; // 表示对单位或组织或职位代码不做限制，全部单位|组织|职位的意思
  
  public static String ASTERISK = "*";//星号代表所有
  
  private static final String AND = "@AND@";
  
  private String id;

  private String projectName;

  private String desc;

  private String priority;

  private String junCoCode;

  private String junOrgCode;

  private String junPosiCode;

  private String junEmpCode;

  private String supCoCode;

  private String supOrgCode;

  private String supPosiCode;

  private String supEmpCode;

  private String supCondition;

  private int nd;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getJunCoCode() {
    return junCoCode;
  }

  public void setJunCoCode(String junCoCode) {
    this.junCoCode = junCoCode;
  }

  public String getJunOrgCode() {
    return junOrgCode;
  }

  public void setJunOrgCode(String junOrgCode) {
    this.junOrgCode = junOrgCode;
  }

  public String getJunPosiCode() {
    return junPosiCode;
  }

  public void setJunPosiCode(String junPosiCode) {
    this.junPosiCode = junPosiCode;
  }

  public String getJunEmpCode() {
    return junEmpCode;
  }

  public void setJunEmpCode(String junEmpCode) {
    this.junEmpCode = junEmpCode;
  }

  public String getSupCoCode() {
    return supCoCode;
  }

  public void setSupCoCode(String supCoCode) {
    this.supCoCode = supCoCode;
  }

  public String getSupOrgCode() {
    return supOrgCode;
  }

  public void setSupOrgCode(String supOrgCode) {
    this.supOrgCode = supOrgCode;
  }

  public String getSupPosiCode() {
    return supPosiCode;
  }

  public void setSupPosiCode(String supPosiCode) {
    this.supPosiCode = supPosiCode;
  }

  public String getSupEmpCode() {
    return supEmpCode;
  }

  public void setSupEmpCode(String supEmpCode) {
    this.supEmpCode = supEmpCode;
  }

  public String getSupCondition() {
    return supCondition;
  }

  public void setSupCondition(String supCondition) {
    this.supCondition = supCondition;
  }

  public int getNd() {
    return nd;
  }

  public void setNd(int nd) {
    this.nd = nd;
  }

  /**
   * 业务规则检查
   * 1、获取与部件相关的条件
   * 2、解析数据，是否符合业务下级选择规则
   * 3、每条规则的表达式间是与的关系，有一个表达式不符合，就表明不符合整条规则
   * @param compoName
   * @param data
   * @return
   */
  public boolean isBelow(TableData data) {
    List compoConditions = this.getCompoConditions();
    
    if ("AS_TEMP".equals(data.getCompoName()) || compoConditions.size() == 0) {
      return true;
    }

    for (Iterator it = compoConditions.iterator(); it.hasNext();) {
      BusinessJuniorModel.CompoCondition cc = (BusinessJuniorModel.CompoCondition) it.next();
      BusinessJuniorExp exp = null;

      if (cc.fieldCode != null) {
        exp = new BusinessJuniorExp(cc.compoCode + "." + cc.fieldCode, cc.val, cc.symbol);
      } else {
        exp = new BusinessJuniorExp(cc.compoCode, cc.val, cc.symbol);
      }
      
      if (!exp.parse(data)){
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * 从条件字串中抽取出与部件相关的条件
   * @return
   */
  public List getCompoConditions() {
    if (supCondition == null){
      return new ArrayList();
    }
    
    List cs = new ArrayList();
    String[] conArr = supCondition.split(AND);
    for (int i = 0; i < conArr.length; i++) {
      String con = conArr[i];
      try {
        BusinessJuniorExp exp = new BusinessJuniorExp(con);
        if (exp.isCompoExpression()) {// 如果是部件表达式
          String compoAttribute = exp.getPara1();
          BusinessJuniorModel b = new BusinessJuniorModel();
          int idx = compoAttribute.indexOf(".");
          
          if (idx > 0) {
            CompoCondition cc = b.new CompoCondition(compoAttribute
              .substring(0, idx), compoAttribute.substring(idx + 1),
              exp.getSymbol(), exp.getPara2());
            cs.add(cc);
          } else {
            if (compoAttribute.equals("@COMPOID")) {// 只是部件名
              CompoCondition cc = b.new CompoCondition(compoAttribute, null, exp
                .getSymbol(), exp.getPara2());
              cs.add(cc);
            }
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return cs;
  }

  /**
   * 
   * 判断对应的业务下级中是否包含有此职员
   * @param staff
   * @return
   */
  public boolean containJuniorStaff(StaffModel staff) {
    return contains(this.junCoCode.split("[,]"), staff.getStaffPosition().getOrg()
      .getCompany().getCode())
      && contains(this.junOrgCode.split("[,]"), staff.getStaffPosition().getOrg()
        .getCode())
      && contains(this.junPosiCode.split("[,]"), staff.getStaffPosition()
        .getPosition().getCode())
      && contains(this.junEmpCode.split("[,]"), staff.getCode());
  }

  /**
   * 
   * 获取对应的业务上级职员列表
   * @return
   */
  public Set transSuperStaffList() {
    List supUserCodeList = Arrays.asList(this.supEmpCode.split("[,]"));
    List supStaffList = WFDaoFactory.getWFRuntimeDao().getStaffListByUserCodeList(supUserCodeList, nd);
    String[] supCoCodeList = this.supCoCode.split("[,]");
    String[] supOrgCodeList = this.supOrgCode.split("[,]");
    String[] supPosiCodeList = this.supPosiCode.split("[,]");
    Set staffList = new HashSet();
    
    Iterator iterator = supStaffList.iterator();
    while(iterator.hasNext()){
      StaffModel staff = new StaffModel((Map)iterator.next());
      boolean allow = false;
      
      String coCode = staff.getStaffPosition().getOrg().getCompany().getCode();
      for(int i = 0; i < supCoCodeList.length; i++){
        if(COMMON_CODE.equals(supCoCodeList[i])){
          staff.getStaffPosition().getOrg().getCompany().setCode(ASTERISK);
          allow = true;
          break;
        }else if(coCode.equals(supCoCodeList[i])){
          allow = true;
          break;
        }
      }
      if(!allow){//单位代码不符合条件
        continue;
      }
      
      String orgCode = staff.getStaffPosition().getOrg().getCode();
      for(int i = 0; i < supOrgCodeList.length; i++){
        if(COMMON_CODE.equals(supOrgCodeList[i])){
          staff.getStaffPosition().getOrg().setCode(ASTERISK);
          allow = true;
          break;
        }else if(orgCode.equals(supOrgCodeList[i])){
          allow = true;
          break;
        }
      }
      if(!allow){//结构代码不符合条件
        continue;
      }
      
      String posiCode = staff.getStaffPosition().getPosition().getCode();
      for(int i = 0; i < supPosiCodeList.length; i++){
        if(COMMON_CODE.equals(supPosiCodeList[i])){
          staff.getStaffPosition().getPosition().setCode(ASTERISK);
          allow = true;
          break;
        }else if(posiCode.equals(supPosiCodeList[i])){
          allow = true;
          break;
        }
      }
      if(!allow){//结构代码不符合条件
        continue;
      }
      
      staffList.add(staff);
    }
    return staffList;
  }

  private static boolean contains(String[] arr, String str) {
    if (arr == null)
      return false;
    for (int i = 0; i < arr.length; i++) {
      if (COMMON_CODE.equals(arr[i]))
        return true;
      if (arr[i].equals(str))
        return true;
      if ("".equals(str))
        return true;
    }
    return false;
  }
  
  public class CompoCondition {
    String compoCode;

    String compoName;

    String fieldCode;

    String fieldName;

    String symbol;

    String val = "";

    public CompoCondition(String compoCode, String fieldCode, String symbol, String val) {
      super();
      this.compoCode = compoCode;
      this.fieldCode = fieldCode;
      this.symbol = symbol;
      this.val = val;
    }
  }
}
