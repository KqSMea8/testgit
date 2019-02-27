<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作日志图示列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true"></h:head>
<script type="text/javascript" src="${contextPath}/resources/jmp/log/echartsNew.js"></script>
<script type="text/javascript">
    
	function changeSite(iid){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/log/chart.do?iid="+iid+"&fromutl="+url; 
		location.href = listUrl; 
	}
	
	function checkDetail(){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/log/list.do?"+"&fromutl="+url; 
		location.href = listUrl; 
	}
</script>
</head>
<body>
  <form action="${url }" method="post" id="oprform">
  <div id="page-title">
		<span class="switchmenu"><i class="icon-sitemap"></i>
			 日志管理  - 操作日志
			 <i class="icon-caret-down"></i>
		</span>
  </div>
  <br></br>
  <c:if test="${iscurrentUser}">
	  &nbsp;&nbsp;&nbsp;&nbsp;所属网站&nbsp;&nbsp;
	  <select name="iid" data-value="${iid}" style="width: 100px;margin-right: 20px;" onchange="changeSite(this.value)">
		<option value="-1">请选择网站</option>
		<c:forEach items="${name_map }" var="name">
			<option value="${name.key }">${name.value }</option>
		</c:forEach>
	  </select> 
  </c:if>
  &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-primary" value="查看详细"  onclick="checkDetail();" /> 
  <br></br>
  <div id="main" style="height:400px"></div>
  
  <script type="text/javascript">


  // 基于准备好的dom，初始化echarts图表
  var myChart = echarts.init(document.getElementById('main')); 
  
  var option = {
  	    title : {
      text: '  功能模块操作总次数统计图',
      subtext: ' '
  },
  tooltip : {
      trigger: 'axis'
  },
  legend: {
      data:['${name}']
  },
  toolbox: {
      show : true,
      padding: [20,120,50,50],
      feature : {
          magicType : {show: true, type: ['line', 'bar']},
          saveAsImage : {show: true}
      }
  },
  xAxis : {
          type : 'category',
          boundaryGap : false,
          data : ['站点管理','栏目分类','信息管理','导航管理','推送信息','报料管理','机构管理','用户管理','角色管理','意见反馈','版本更新']
      },
  yAxis : {
          type : 'value',
          axisLabel : {
              formatter: '{value} 次'
          }
      },
  series : [
      {
          name:'${name}',
          type:'line',
          data:[${module1}, ${module2}, ${module3}, ${module4}, ${module6}, ${module8}, 
                ${module9}, ${module10}, ${module11}, ${module12}, ${module14}],
          markPoint : {
              data : [
                  {type : 'max', name: '最大值'},
                  {type : 'min', name: '最小值'}
              ]
          },
          markLine : {
              data : [
                  {type : 'average', name: '平均值'}
              ]
          }
      }
  ]
};

  // 为echarts对象加载数据 
  myChart.setOption(option); 

	
  </script>
  
  <div id="main1" style="height:400px"></div>
  
  <script type="text/javascript">

  // 基于准备好的dom，初始化echarts图表
  var myChart1 = echarts.init(document.getElementById('main1')); 
  
  var option1 = {
  	    title : {
      text: '  操作类型总次数统计图',
      subtext: ' '
  },
  tooltip : {
      trigger: 'axis'
  },
  legend: {
      data:['${name}']
  },
  toolbox: {
      show : true,
      padding: [20,120,50,50],   
      feature : {
          magicType : {show: true, type: ['line', 'bar']},
          saveAsImage : {show: true}
      }
  },
  xAxis : {
          type : 'category',
          boundaryGap : false,
          data : ['新增','删除','修改','审核','撤审','排序','导入','导出','上传','下载','启用',
                  '停用','登录','登出','订阅','清空','推送','获取']
      },
  yAxis : {
          type : 'value',
          axisLabel : {
              formatter: '{value} 次'
          }
      },
  series : [
      {
          name:'${name}',
          type:'line',
          data:[${func1}, ${func2}, ${func3}, ${func4}, ${func5}, ${func6}, ${func7}, ${func8}, 
                ${func9}, ${func10}, ${func11}, ${func12}, ${func13}, ${func14}, ${func15}, ${func16}, ${func17}, ${func18}],
          markPoint : {
              data : [
                  {type : 'max', name: '最大值'},
                  {type : 'min', name: '最小值'}
              ]
          },
          markLine : {
              data : [
                  {type : 'average', name: '平均值'}
              ]
          }
      }
  ]
};

  // 为echarts对象加载数据 
  myChart1.setOption(option1); 
  </script>
  
  
  </form>
</body>	
</html>