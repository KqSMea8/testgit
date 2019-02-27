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
<title>推送图示列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true"></h:head>
<script type="text/javascript" src="${contextPath}/resources/jmp/log/echartsNew.js"></script>
<script type="text/javascript">
	
	function changeSite(iid){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/interfacelog/chart.do?iid="+iid+"&fromutl="+url; 
		location.href = listUrl; 
	}
	
	function checkDetail(){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/interfacelog/list.do?iid="+${iid}+"&fromutl="+url; 
		location.href = listUrl; 
	}
</script>
</head>
<body>
  <form action="${url }" method="post" id="oprform">
  <div id="page-title">
		<span class="switchmenu"><i class="icon-sitemap"></i>
			 日志管理  - 接口访问日志
			 <i class="icon-caret-down"></i>
		</span>
  </div>
  <br></br>
  <div>
  <c:if test="${iscurrentUser}">
	  &nbsp;&nbsp;&nbsp;&nbsp;所属网站&nbsp;&nbsp;
	  <select id="iid"  name="iid" data-value="${iid}" style="width: 100px;margin-right: 20px;" onchange="changeSite(this.value)">
		<option value="0">请选择网站</option>
		<c:forEach items="${name_map }" var="name">
			<option value="${name.key }">${name.value }</option>
		</c:forEach>
	  </select> 
  </c:if>
  </div>
  <br></br>
  &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-primary" value="查看详细"  onclick="checkDetail();" /> 
  <br></br>
  <div id="main" style="height:400px"></div>
  <script type="text/javascript">

  // 基于准备好的dom，初始化echarts图表
  var myChart = echarts.init(document.getElementById('main')); 
  
  var option = {
  	    title : {
      text: '客户端组成',
      subtext: '',
      x:'center'
  },
  tooltip : {
      trigger: 'item',
      formatter: "{a} <br/>{b} : {c} ({d}%)"
  },
  legend: {
      orient : 'vertical',
      x : 20,
      data:['iPad','iPhone','Android']
  },
  toolbox: {
      show : true,
      padding: [20,120,50,50],
      feature : {
          saveAsImage : {show: true}
      }
  },
  series : [
      {
          name:'访问来源统计图',
          type:'pie',
          radius : '55%',
          center: ['50%', '60%'],
          data:[
              {value:${clienttype2}, name:'iPhone'},
              {value:${clienttype3}, name:'Android'},
              {value:${clienttype4}, name:'iPad'},
          ]
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
      text: '  调用时段统计图',
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
          axisLabel : {
              formatter: '{value} 时'
          },
          boundaryGap : false,
          data : ['0-4','5-9','10-14','15-19','20-23']
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
          data:[${count1}, ${count2}, ${count3}, ${count4}, ${count5}],
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
  <div id="main2" style="height:400px"></div>
  <script type="text/javascript">

  // 基于准备好的dom，初始化echarts图表
  var myChart2 = echarts.init(document.getElementById('main2')); 
  
  var option2 = {
  	    title : {
      text: '  接口使用分布统计图',
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
          axisLabel : {
      	      rotate: -45
          },
          boundaryGap : false,
          data : ['APP封面','取频道下子栏目','通过父栏目取子栏目','信息列表','信息正文','酷图信息正文',
                  '缺省资源接口','版本更新','微博圈','正文发表评论接口','评论列表接口	','阅读/评论/点赞数',
                  '点赞','大家的报料列表接口','我的报料接口','分类接口','提交报料接口','报料详情接口',
                  '外网注册接口','注册修改接口','注册登录接口','领导信箱','意见征集','网上调查','在线阅读','天气预报']                                                    
          
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
          data:[${counts0}, ${counts1}, ${counts2}, ${counts3}, ${counts4}, ${counts5}, 
                ${counts6}, ${counts7}, ${counts8}, ${counts9}, ${counts10}, ${counts11},
                ${counts12},${counts13},${counts14},${counts15},${counts16},${counts17},
                ${counts18},${counts19},${counts20},${counts21},${counts22},${counts23},
                ${counts24},${counts25}],
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
  myChart2.setOption(option2); 
  
  </script>
  </form>
</body>	
</html>