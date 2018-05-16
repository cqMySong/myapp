<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>检验批划分</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;margin-bottom:5px;">
		<div class="btn-group">
			<button class="btn btn-success" type="button" id="batchimp">
				<span class="fa fa-file-o"></span>&nbsp;检验批标准导入</button>
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
								<th data-field="project_name">工程项目</th>
								<th data-field="proBaseWbs_displayName" data-width="250" >分部分项工程</th>
								<th data-field="number">编码</th>
								<th data-field="name">名称</th>
								<th data-field="content" data-width="400" data-type="textarea">划分办法</th>
								<th data-field="attachs" data-type='attach'>附件数</th>
								<!--<th data-field="projectStartTime" data-type="date">工程开始时间</th>-->
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
function doLayout(){
	
}
var thisOrgList ;
var includeChild;
function beforeAction(opt){
	if(opt=='addnew'){
		var params = thisOrgList.uiParams(opt);
		if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
				&&'project'==params.tree.type){
		}else{
			webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
			return false;
		}
	}
	return true;
}
//界面参数传递扩展方法
function openUIParams(operate,params){
}
function batchImpData(){
	var treeNode = thisOrgList.getSelectNode();
	if(!webUtil.isEmpty(treeNode)&&'project'==treeNode.type){
		var data = {projectId:treeNode.id};
		var url = 'ec/basedata/probatchtests/batchimp';
		webUtil.ajaxData({url:url,data:data,async:false,success:function(data){
			var statusCode = $(data).attr('statusCode');
			if(0==statusCode){
				var msg = $(data).attr('statusMesg');
				if(!webUtil.isEmpty(msg)){
					webUtil.mesg(msg);
				}
				thisOrgList.listUI.executeQuery();
			}
		}});
	}else{
		webUtil.mesg('请先选择的工程项目组织，然后才能做导入操作!');
	}
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
     var treeNode2QueryProp = ["id","name","number","type"];
     var editWin ={title:'项目检验批划分',width:630,height:450,id:"probatchtests_tab"};
     var treeOpt = {setting:{data: {
         	simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
    	 }}};
     var height =  top.getTopMainHeight()-45;
     thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/basedata/probatchtests'
    	 ,title:'工程项目',height:height,
    	 treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",treeOpt:treeOpt
    	 ,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-53,sortStable:false,rowStyle:changeBgColor}});
     thisOrgList.onLoad();

    //施工技术交底导入
    $('#batchimp').on('click',function(){
        var tree = thisOrgList.getSelectNode();
        if(webUtil.isEmpty(tree)||'project'!=tree.type){
            webUtil.mesg('请先选择工程项目，然后才能做导入操作!');
            return false;
        }
        var _win = $.extend(true,{},{title:'检验批划分标准导入',width:900,height:570,
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
                            webUtil.ajaxData({url:"ec/basedata/probatchtest/batch/import",data:pams,async:true,success:function(data){
                                thisOrgList.listUI.executeQuery();
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
        _win.url =  webUtil.toUrl('ec/basedata/probatchtests/batch/import');
        _win.uiParams={project:{id:webUtil.uuIdReplaceID(tree.id),name:tree.name,number:tree.number}};
        _win.colseCallBack =function(){
            thisOrgList.listUI.executeQuery();
        };
        webUtil.openWin(_win);
    });
});
function changeBgColor(row, index) {
    var color = "";
    if(!row.attachs&&row.projectStartTime){
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