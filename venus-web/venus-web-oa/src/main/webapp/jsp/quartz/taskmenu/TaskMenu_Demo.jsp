<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="Text/css">
<!--
BODY{margin:0px;border:0px;background-color:#ffffff}
//-->
</style>
<script>
function changeStyle()
{
	var s = document.getElementById("tStyle");
	var v = s.options[s.selectedIndex].value;
	with(parent.window.frames[0])
	{
		TaskMenu.setStyle(v);
	}
}
function addItem()
{	
	var items = new Array();
	var oitems = new Array();
	var aIndex = parseInt(document.getElementById("aIndex").value);
	var s = document.getElementById("oItems");
	for(var i = 0; i < s.length; i++)
	{
		if(s.options[i].selected == true)
		{
			items[items.length] = "item" + s.options[i].value;
		}
	}
	for(var j = 0; j < items.length; j++)
	{
		with(parent.window.frames[0])
		{
			taskMenu3.add(eval(items[j]),aIndex++);
		}
	}
}

function delItem()
{
	try
	{
		var i = parseInt(document.getElementById("dIndex").value);
		var j = parseInt(document.getElementById("dCount").value);
		with(parent.window.frames[0])
		{
			taskMenu3.remove(i,j);
		}
	}
	catch(ex)
	{
		alert(ex.description);
	}
}
</script>
</head>
<body>
<table width=100% border=0 cellpadding=0 cellspacing=1 bgcolor=#666666>
  <tr height=50>
    <td width=100% bgcolor=#cecfff style='font:bold 18px Tahoma,Verdana;padding-left:50px'>
    菜单设置
    </td>
  </tr>
  <tr>
    <td width=100% style='padding:20px;background-color:#efefef'>
      <div style='width:100%;;font:12px Tahoma,verdana'>
		更改TaskMenu的主题样式 : <select id="tStyle" >
                                   <option value="<%=request.getContextPath()%>/css/Blue/blueStyle.css">蓝色主题</option>
                                   <option value="<%=request.getContextPath()%>/css/Silver/silverStyle.css">银色主题</option>
                                   <option value="<%=request.getContextPath()%>/css/Classic/classicStyle.css">经典主题</option>
				   </select>
                &nbsp;&nbsp;<input type="button" onclick ="Javascript:changeStyle()" value="更 改">
      </div>
    </td>
  </tr>
  <tr>
    <td width=100% style='padding:20px;background-color:#efefef'>
      <div style='width:100%;;font:12px Tahoma,verdana'>
               测试更改菜单条目的功能:<BR>
               对taskMenu3菜单添加条目[可多选]
               <select id="oItems" multiple>
               <option value="1">item1
               <option value="2">item2
               <option value="3">item3
               <option value="7">item7
               <option value="8">item8
               </select>&nbsp;添加在位置：
		<input id="aIndex" value="0" style='width:30px'>
		<input type="button" onclick="addItem()" value="添 加">
		<BR><BR>
	  	从第<input id="dIndex" value="0" style='width:30px'>条目开始，删除
	  	<input id="dCount" value="1" style='width:30px'>个&nbsp;
	  	<input type="button" onclick="Javascript:delItem()" value="删 除">
      </div>
    </td>
  </tr>
</table>