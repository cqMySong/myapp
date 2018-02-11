<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>施工天气信息</title>
<style type="text/css">
.mainContrainer {
	width: 100%;
	height: 100%;
	overflow:hidden;
	padding: 0px 2px 2px 2px;
}
.leftContainer {
	width: 260px;
	height: 100%;
	float: left;
}
.rightContainer {
	height: 100%;
	overflow:hidden;
	padding-left: 5px;
}
.panel {
	width: 100%;
	height: 100%;
	padding: 0px 2px 2px 2px;
}
</style>
</head>
<script type="text/javascript">
</script>
<body style="padding: 5px;">
<div class="panel">
	<div class="mainContrainer">
		<div class="leftContainer" style="border-right: 2px solid #d8dce3" id="left_container">
		</div>
		<div class="rightContainer" id="main_container">
			<div class="" id="tblMain_toolbar" style="width: 500px;">
				<div class="input-group" style="float: left;">
					<div class="input-group-addon" id="preMonth" style="width: 50px;min-width: 50px;cursor: pointer;">
						上年<span class="glyphicon glyphicon-step-backward"></span>
					</div>
					<input type="text" class="form-control" id="bizDate" readonly="readonly" style="width: 80px;text-align: center;">
					<div class="input-group-addon" id="nextMonth" style="width: 50px;min-width: 50px;cursor: pointer;">
						<span class="glyphicon glyphicon-step-forward"></span>下年
					</div>
				</div>
				<div class="btn-group" style="cursor: pointer;float: left;margin-left: 5px;">
					<button class="btn btn-success" type="button" id="addWeather">
						<span class="fa fa-file-o"></span>&nbsp;新增</button>
				</div>
				<div class="btn-group" style="margin-left: 5px;">
					<button class="btn btn-success" type="button" id="saveWeather">
						<span class="fa fa-save"></span>&nbsp;保存</button>
				</div>
			</div>
			<table id="tblMain">
				<thead >
					<tr>
						<th data-field="fMonth" data-locked="true">日期</th>
						<th data-field="fType" data-locked="true">类型</th>
						<th data-field="fOne" >1</th>
						<th data-field="fTwo" >2</th>
						<th data-field="fThree" >3</th>
						<th data-field="fFour"  >4</th>
						<th data-field="fFive"  >5</th>
						<th data-field="fSix"  >6</th>
						<th data-field="fSeven"  >7</th>
						<th data-field="fEight"  >8</th>
						<th data-field="fNine" >9</th>
						<th data-field="fTen" >10</th>
						<th data-field="fEleven" >11</th>
						<th data-field="fTwelve" >12</th>
						<th data-field="fThirteen" >13</th>
						<th data-field="fFourteen"  >14</th>
						<th data-field="fFifteen"  >15</th>
						<th data-field="fSixteen"  >16</th>
						<th data-field="fSeventeen"  >17</th>
						<th data-field="fEighteen"  >18</th>
						<th data-field="fNineteen" >19</th>
						<th data-field="fTwenty" >20</th>
						<th data-field="fTwentyOne" >21</th>
						<th data-field="fTwentyTwo" >22</th>
						<th data-field="fTwentyThree" >23</th>
						<th data-field="fTwentyFour"  >24</th>
						<th data-field="fTwentyFive"  >25</th>
						<th data-field="fTwentySix"  >26</th>
						<th data-field="fTwentySeven"  >27</th>
						<th data-field="fTwentyEight">28</th>
						<th data-field="fTwentyNine">29</th>
						<th data-field="fThirty">30</th>
						<th data-field="fThirtyOne">31</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</body>
<%@include file="../../../inc/webBase.inc"%>
<script type="text/javascript">
    var orgTree;
    var curSelOrg;
    var tblMain;
    var curYM ;
    var curPeriod = new Date();
    function setCurPeriod(val,query){
        var year = val.format('yyyy');
        $('#bizDate').val(year);
        curPeriod = val;
        var newYm = year;
        if(newYm!=curYM){
            curYM = newYm;
            if(query){
                queryTableData();
            }
        }
    }
    function treeClick(event, treeId, treeNode){
        if(webUtil.isEmpty(curSelOrg)) curSelOrg = {id:'xyz'};
        if(webUtil.isEmpty(curSelOrg.id)) curSelOrg.id = 'xyz';
        if(curSelOrg.id!=treeNode.id){
            curSelOrg = treeNode;
            queryTableData();
        }
    }
    function initOrgTree(height){
        var treeOpt = {view: {dblClickExpand: true,selectedMulti: false}
            ,data: {simpleData: {enable:true,idKey: "id", pIdKey: "parentId",rootPId: ''}}
            ,callback:{onClick:treeClick}
        };
        var treeViewer = $('#left_container').myTreeViewer(null);
        treeViewer.init({height:height-10,theme:"panel-success",title:'<i class="fa fa-building-o" style="font-size: 12px;"></i>&nbsp;工程项目',search:true});
        treeViewer.addTree(treeOpt,[]);
        orgTree = treeViewer.getTree();
        treeViewer.addRefreshBtn({clickFun:function(btn){
            loadTreeData();
        }});
        loadTreeData();
    }
    function loadTreeData(){
        webUtil.ajaxData({url:'ec/basedata/projects/projectTree',async:false,success:function(data){
            var treeDatas = data.data;
            if (treeDatas.length>0&&!webUtil.isEmpty(orgTree)) {
                orgTree.reLoadTree(treeDatas)
                orgTree.selectNodeByIndex(0);
            }
        }});
    }
    function initTable(){
        var height = top.getTopMainHeight()-5;
        var table_options = {type:'entry',height:height,striped:true,sortStable:false,showRefresh:false,selectModel:1
            ,cache:false,showToggle:false,search:false,queryParams:searchPrams,toolbar:"#tblMain_toolbar"
            ,showColumns:false,idField:"id",mypagination:false,url:'ec/basedata/weather/query'};
        tblMain = $('#tblMain').myDataTable(table_options);
        tblMain.initTableColumnEditor('click-cell',{});
    }
    function searchPrams(){
        var params = {};
        params.projectId = curSelOrg?curSelOrg.id:'';
        params.year = $('#bizDate').val();
        return webUtil.json2Str(params);
    }
    $(function(){
        var height = top.getTopMainHeight();
        $(".mainContrainer").height(height);
        initOrgTree(height);
        initTable();
        $('#preMonth').click(function(){
            setCurPeriod(curPeriod.addYears(-1),true);
        });
        $('#nextMonth').click(function(){
            setCurPeriod(curPeriod.addYears(1),true);
        });
        $('#bizDate').val(curPeriod.format("yyyy"));
        $('#addWeather').on("click",function(){
			if(!curSelOrg||curSelOrg.type!='project'){
                webUtil.mesg('请先选择的工程项目组织，然后才能做新增操作!');
				return false;
			}
            webUtil.ajaxData({url:"ec/basedata/weather/add/year",
				data:{projectId:curSelOrg.id,year:$('#bizDate').val()},
				async:false,
				success:function(data){
					if(data.statusCode==0){
						//清空表格
                        tblMain.loadData(data.data);
					}
            }});
		});
        //保存信息信息
        $('#saveWeather').on('click',function(){
            var data = tblMain.getData();
            if(!data||data.length==0){
                webUtil.mesg('没有要保存的天气信息!');
                return false;
            }
            console.log(data);
            var newData = [];
            $.each(data,function(i,v){
				var weatherObj = {};
				for(key in v){
				    var newKey = key.substring(1);
				    if(newKey!="id"){
                        newKey = newKey.substring(0,1).toLowerCase()+newKey.substring(1);
					}
                    weatherObj[newKey] = v[key];
				}
                newData.push(weatherObj);
			});
            webUtil.ajaxData({url:"ec/basedata/weather/save",
                data:{weatherDetail:JSON.stringify(newData)},
                async:false,
                success:function(data){
                	if(data.statusCode==0){
                        webUtil.mesg('保存成功!');
                        return false;
					}
                    var msgObj = {title:".::系统提示::.",type:"info"};//==1 提示
                    msgObj.content = data.statusMesg;
                    if(data.statusCode<0){//异常 错误
                        msgObj.title = ".::系统操作"+(data.statusCode==-100?"异常":"错误")+"::.";
                        msgObj.type = "error";
                    }else if(data.statusCode==100){//警告
                        msgObj.title = ".::系统操作警告::.";
                        msgObj.type = "warning";
                    }
                    webUtil.showMesg(msgObj);
                }});
		});
	});
    function queryTableData(){
        webUtil.ajaxData({url:"ec/basedata/weather/query",
            data:{projectId:curSelOrg.id,year:$('#bizDate').val()},
            async:false,
            success:function(data){
                if(data.statusCode==0){
                    //清空表格
                    tblMain.loadData(data.data);
                }
            }});
	}
</script>
</html>