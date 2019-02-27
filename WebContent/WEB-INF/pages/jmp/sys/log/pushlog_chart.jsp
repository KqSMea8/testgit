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
		var listUrl ="${contextPath}/manager/pushlog/chart.do?iid="+iid+"&fromutl="+url; 
		location.href = listUrl; 
	}
	
	function checkDetail(iid){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/pushlog/list.do?iid="+iid+"&fromutl="+url; 
		location.href = listUrl; 
	}
	
	function changeYear(pushyear){
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/pushlog/chart.do?pushyear="+pushyear+"&fromutl="+url; 
		location.href = listUrl;
	}
</script>
</head>
<body>
  <form action="${url }" method="post" id="oprform">
  <div id="page-title">
		<span class="switchmenu"><i class="icon-sitemap"></i>
			 日志管理  - 推送日志
			 <i class="icon-caret-down"></i>
		</span>
  </div>
  <br></br>
  <c:if test="${iscurrentUser}">
	  &nbsp;&nbsp;&nbsp;&nbsp;所属网站&nbsp;&nbsp;
	  <select name="iid" data-value="${iid}" style="width: 100px;margin-right: 20px;" onchange="changeSite(this.value)">
		<option value="0">请选择网站</option>
		<c:forEach items="${name_map }" var="name">
			<option value="${name.key }">${name.value }</option>
		</c:forEach>
	  </select> 
	  
  </c:if>
   &nbsp;&nbsp;&nbsp;&nbsp;推送年份&nbsp;&nbsp;
	  <select name="pushyear" data-value="${pushyear}" style="width: 100px;margin-right: 20px;" onchange="changeYear( this.value )">
		<option value="0">请选择年份</option>
		<c:forEach items="${yearList}" var="year">
			<option value="${year }">${year}年</option>
		</c:forEach>
	  </select>   
 
  &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-primary" value="查看详细"  onclick="checkDetail(${iid});" /> 
  <br></br>
  <div id="main" style="height:400px"></div>
  
  <script type="text/javascript">

  // 基于准备好的dom，初始化echarts图表
  var myChart = echarts.init(document.getElementById('main'));

  var option = {
  	    title : {
              text: '  推送统计图',
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
                  data : ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
              },
          yAxis :{
                  type : 'value',
                  axisLabel : {
                      formatter: '{value} 次'
                  }
              },
          series : [
              {
                  name:'${name}',
                  type:'line',
                  data:[${count1}, ${count2}, ${count3}, ${count4}, ${count5}, ${count6}, 
                        ${count7}, ${count8}, ${count9}, ${count10}, ${count11}, ${count12}],
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
      text: '  推送时段统计图',
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
          data : ['0-4时','5-9时','10-14时','15-19时','20-23时']
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
          data:[${pushtime1}, ${pushtime2}, ${pushtime3}, ${pushtime4}, ${pushtime5}],
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
      text: '  新增注册用户统计图',
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
          data : ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
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
          data:[${outuser1}, ${outuser2}, ${outuser3}, ${outuser4}, ${outuser5}, ${outuser6}, 
                ${outuser7}, ${outuser8}, ${outuser9}, ${outuser10}, ${outuser11}, ${outuser12}],
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