<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工方案编写规划</title>
</head>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
    <div id="table-toolbar" class="panel" style="height:42px;padding-top:2px;">
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="batchScheme">
				<span class="fa fa-file-o"></span>&nbsp;施工方案导入</button>
		</div>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel">
				<div class="" id="tblMain_toolbar">
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="number">编号</th>
								<th data-field="name">名称</th>
								<th data-field="projectName">工程项目</th>
								<th data-field="compilerName">方案编制人</th>
								<th data-field="complileDate" data-type="date">编制日期</th>
								<th data-field="lastFinishDate" data-type="date">最迟完成日期</th>
								<th data-field="schemeTypeName">方案类别</th>
								<th data-field="billState" data-formatter="billState_formatter">状态</th>
								<!--<th data-field="projectBegDate" data-type="date">工程开始日期</th> -->
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>

<%@include file="../../../base/base_treelist.jsp"%>
<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
var listUI;
var thisOrgList ;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		var tree = thisOrgList.tree;
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&'project'==params.tree.type){
		}else{
			webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
function billState_formatter(value, row, index){
	return SchemeState[value];
}
function enableClick(btn){

}
function getAllChildrenNodes(treeNode,result){
    var childrenNodes = treeNode.children;
    if (childrenNodes) {
        for (var i = 0; i < childrenNodes.length; i++) {
            result = getAllChildrenNodes(childrenNodes[i], result);
        }
    }else{
        result.push(treeNode.id);
    }
    return result;
}
$(document).ready(function() {
    var treeNode2QueryProp = ["id","name","number","longNumber","type"];
    var editWin ={title:'施工方案编写规划',width:680,height:430};
    var treeOpt = {setting:{data: {
        	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
   	 }}};
    var height = window.outerHeight-312;
    thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/schemelist',title:'工程项目',height:height+50,
   	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
   	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height,sortStable:false,rowStyle:changeBgColor}});
    thisOrgList.onLoad();
    //施工方案导入
    $('#batchScheme').on('click',function(){
        /*var tree = thisOrgList.getSelectNode();
        if('project'!=tree.type){
            webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
            return false;
        }
        var _win = $.extend(true,{},{title:'施工方案导入',width:900,height:height+200,btns:[]});
        _win.url =  webUtil.toUrl('ec/basedata/schemelist/batch/import');
        _win.uiParams={project:{id:webUtil.uuIdReplaceID(tree.id),name:tree.name,number:tree.number}};
        _win.colseCallBack =function(){
            thisOrgList.listUI.executeQuery();
        };
        webUtil.openWin(_win);*/
        var tree = thisOrgList.getSelectNode();
        if(webUtil.isEmpty(tree)||'project'!=tree.type){
            webUtil.mesg('请先选择工程项目，然后才能做导入操作!');
            return false;
        }
        var _win = $.extend(true,{},{title:'施工方案导入',width:900,height:570,
            btns:['确定','取消'],btnCallBack:function(index,layerIndex,layero){
                if(layero){
                    if(index==1){
                        var iframe_win = $(layero).parent().find('#layui-layer-iframe'+layerIndex)[0].contentWindow;
                        var datas = iframe_win.getData();
                        if(!webUtil.isEmpty(datas)&&datas.length>0){
                            var wbsIds = [];
                            $.each(datas,function(i,val){
                                getAllChildrenNodes(val,wbsIds);
                            });
                            var pams = {structId:tree.id,structCode:tree.number,wbsIds:wbsIds.join(",")};
                            webUtil.ajaxData({url:"ec/basedata/schemeedit/batch/import",data:pams,async:true,success:function(data){
                                var statusCode = $(data).attr('statusCode');
                                if(0==statusCode){
                                    var msg = $(data).attr('statusMesg');
                                    if(!webUtil.isEmpty(msg)){
                                        webUtil.mesg(msg);
                                    }
                                    thisOrgList.listUI.executeQuery();
                                }
                            }});
                            return true;
                        }else{
                            webUtil.mesg("未选择任何数据!");
                            return false;
                        }
                    }
                }
                return true;
            }});
        _win.url =  webUtil.toUrl('ec/basedata/schemelist/batch/import');
        _win.uiParams={project:{id:webUtil.uuIdReplaceID(tree.id),name:tree.name,number:tree.number}};
        _win.colseCallBack =function(){
            thisOrgList.listUI.executeQuery();
        };
        webUtil.openWin(_win);
    });
});
function changeBgColor(row, index) {
    var color = "";
    var days = webUtil.betweenDateDays(row.lastFinishDate,row.projectBegDate);
    if(days==0){
        color=EarlyWarning.warning;
    }else if(days>0){
        color=EarlyWarning.danger;
	}
    if(!color){
        return false;
    }
    var style={css:{'background-color':color}};
    return style;
}
</script>
</html>