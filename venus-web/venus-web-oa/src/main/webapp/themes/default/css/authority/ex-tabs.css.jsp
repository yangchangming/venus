.tabs-header{
	background:filter:alpha(opacity=0);
	border-bottom:0px;
	position:relative;
	overflow:hidden;
	padding:0px;
	padding-top:2px;
	overflow:hidden;
}
.tabs-header-plain{
	border:0px;
	background:transparent;
}
.tabs-scroller-left{
	position:absolute;
	left:0px;
	top:-1px;
	width:18px;
	height:28px!important;
	height:30px;
	font-size:1px;
	display:none;
	cursor:pointer;
	background:#E0ECFF url('<%=request.getContextPath()%>/css/jquery/i/tabs_leftarrow.png') no-repeat 1px 5px;
}
.tabs-scroller-right{
	position:absolute;
	right:0;
	top:-1px;
	width:18px;
	height:28px!important;
	height:30px;
	font-size:1px;
	display:none;
	cursor:pointer;
	background:#E0ECFF url('<%=request.getContextPath()%>/css/jquery/i/tabs_rightarrow.png') no-repeat 2px 5px;
}
.tabs-header-plain .tabs-scroller-left{
	top:2px;
	height:25px!important;
	height:27px;
}
.tabs-header-plain .tabs-scroller-right{
	top:2px;
	height:25px!important;
	height:27px;
}
.tabs-scroller-over{
	background-color:#ECF9F9;
}
.tabs-wrap{
	position:relative;
	left:0px;
	overflow:hidden;
	width:100%;
	margin:0px;
	padding:0px;
}
.tabs-scrolling{
	margin-left:18px;
	margin-right:18px;
}
.tabs{
	list-style-type:none;
	height:26px;
	margin:0px;
	padding:0px;
	padding-left:4px;
	font-size:12px;
	width:5000px;
	border-bottom:1px solid #8DB2E3;
}
.tabs li{
	float:left;
	display:inline;
	margin1:0px 1px;
	margin-right:4px;
	margin-bottom:-1px;
	padding:0;
	position:relative;
	border:1px solid #8DB2E3;
}
.tabs li a.tabs-inner{
	display:inline-block;
	text-decoration:none;
	color:#000000;
	background-color:#dde4ed;
	margin:0px;
	padding:0px 10px;
	height:25px;
	line-height:25px;
	text-align:center;
	white-space:nowrap;
}
.tabs li a.tabs-inner:hover{
	 background-color:#7fcfe3;
}
.tabs li.tabs-selected{
	border:1px solid #8DB2E3;
	border-top1:2px solid #8DB2E3;
}
.tabs li.tabs-selected a{
	color:#FFFFFF;
	font-weight:bold;
	background:#fff;
	 background-color:#089ec4
}
.tabs li.tabs-selected a:hover{
	cursor:default;
	pointer:default;
}
.tabs-with-icon{
	padding-left:20px;
}
.tabs-icon{
	position:absolute;
	width:16px;
	height:16px;
	left:10px;
	top:5px;
}
.tabs-closable{
	padding-right:8px;
}
.tabs li a.tabs-close{
	position:absolute;
	font-size:1px;
	display:block;
	padding:0px;
	width:11px;
	height:11px;
	top:3px;
	right:3px;
	opacity:0.6;
	filter:alpha(opacity=60);
	background:url('../../images/au/tabs_close.gif') no-repeat;
}
.tabs li a:hover.tabs-close{
	opacity:1;
	filter:alpha(opacity=100);
	cursor:hand;
	cursor:pointer;
}


.tabs-panels{
	margin:0px;
	padding:0px;
	border:1px solid #8DB2E3;
	border-top:0px;
	overflow:hidden;
}
