<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>项目安全样板职责</title>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
	<div id="table-toolbar" class="panel" style="height:40px;padding-top: 1px;margin-bottom: 5px;">
		<button class="btn btn-success" type="button" id="batchImpl">
			<span class="fa fa-file-o"></span>&nbsp;安全样板导入</button>
		<button class="btn btn-success" type="button" id="jobRequire">
			<span class="fa fa-file-o"></span>&nbsp;工作要求</button>
	</div>
	<div class="mainContrainer">
		<div class="leftContainer" id="tree_container"></div>
		<div class="rightContainer" id="tblMain_container">
			<div class="panel" style="margin-bottom: 0px;">
				<div class="" id="tblMain_toolbar">
				</div>
				<div class="panel-body" style="padding: 0px 2px 2px 2px;">
					<table id="tblMain">
						 <thead >
							<tr>
								<th data-field="project_name">工程项目</th>
								<th data-field="name">名称</th>
								<th data-field="number">单号</th>
								<th data-field="branchBaseWbs_name">分部名称</th>
								<th data-field="subentry_name">分项名称</th>
								<th data-field="expectStartDate" data-type="date">预计实施时间</th>
								<th data-field="acceptanceDate" data-type="date">验收时间</th>
								<th data-field="billState" data-type="select">状态</th>
								<th data-field="createUser_name">创建人</th>
								<th data-field="attachs">附件数</th>
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
	var thisBudgetList ;
	function beforeAction(opt){
			if(opt=='addnew'){
					var params = thisOrgList.uiParams(opt);
					var tree = thisOrgList.tree;
					if(!webUtil.isEmpty(params)&&!webUtil.isEmpty(params.tree)
							&&('project'==params.tree.type||'proStructure'==params.tree.type)){
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
        	var height = top.getTopMainHeight()-50;
			var editWin ={title:'项目安全样板',width:1200,height:((height+100)>800?800:(height+100))};
			var treeOpt = {
					setting:{
						data: {
							simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}
						}
					}};
			thisOrgList = $('body').treeListUI({tableEl:'#tblMain',treeUrl:'ec/basedata/projects/projectTree',baseUrl:'ec/safe/templates',title:'项目工程',height:height,
							treeContainer:"#tree_container",editWin:editWin,toolbar:"#table-toolbar",searchParams:{includeChild:true},treeOpt:treeOpt
							,treeNode2QueryProp:treeNode2QueryProp,extendTableOptions:{toolbar:'#tblMain_toolbar',height:height-45,sortStable:false,rowStyle:changeBgColor}});
			thisOrgList.onLoad();
			$('#jobRequire').on('click',function(){
                var _selRows = thisOrgList.listUI.getSelectRow();
                if(!webUtil.isEmpty(_selRows)&&_selRows.length>0){
                    var _thisRowData = _selRows[0];
                    var _win = $.extend(true,{},{title:'工作要求',width:1200,height:((height+100)>800?800:(height+100)),btns:[]});
                    _win.title = _win.title+'-确认';
                    _win.url =  webUtil.toUrl('ec/safe/templates/job/require');
                    _win.uiParams = {proSafeTemplateId:webUtil.uuIdReplaceID(_thisRowData.id)};
                    webUtil.openWin(_win);
                }else{
                    webUtil.mesg('请先选中对应的数据行，方可进行工作要求操作!');
                }
			});
        //安全样板导入
        $('#batchImpl').on('click',function(){
            var tree = thisOrgList.getSelectNode();
            if(webUtil.isEmpty(tree)||'project'!=tree.type){
                webUtil.mesg('请先选择工程项目，然后才能做导入操作!');
                return false;
            }
            var _win = $.extend(true,{},{title:'安全样板导入',width:900,height:570,
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
                                webUtil.ajaxData({url:"ec/safe/template/batch/import",data:pams,async:true,success:function(data){
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
            _win.url =  webUtil.toUrl('ec/safe/templates/batch/import');
            _win.uiParams={targetId:webUtil.uuIdReplaceID(tree.id)};
            _win.colseCallBack =function(){
                thisOrgList.listUI.executeQuery();
            };
            webUtil.openWin(_win);
        });
	});
    function changeBgColor(row, index) {
        var color = "";
        var nowDate = new Date();
        var days = webUtil.betweenDateDays(row.acceptanceDate,nowDate);
        if(row.attachs==0&&days>0){
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