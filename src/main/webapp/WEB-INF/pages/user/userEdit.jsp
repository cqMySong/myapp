<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<%@include file="../inc/webBase.inc"%>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
<body style="padding: 5px;" >
	<div id="editPanel" class="contentpanel editFrom" style="padding:2px;">
		<div class="panel">
            <div class="panel-heading">
              <h4 class="panel-title">用户信息维护</h4>
            </div>
            <div class="panel-body form-group">
              <div class="row">
                <div class="col-sm-6">
                  <div class="input-group">
	                <span class="input-group-addon">用户账号</span>
	                <input class="form-control" type="text" placeholder="userName">
	              </div>
                </div>
                 <div class="col-sm-6 mb15">
                  <div class="input-group">
	                <span class="input-group-addon">用户实名</span>
	                <input class="form-control " type="text" placeholder="name">
	              </div>
                </div>
              </div>
               <div class="row">
               	<div class="col-sm-6">
                  <div class="input-group">
	                <span class="input-group-addon">用户状态</span>
	                <select id="usertate" class="form-control" data-placeholder="用户状态选择...">
	                  <option value="United States">United States</option>
	                  <option value="United Kingdom">United Kingdom</option>
	                  <option value="Japan" selected>日本</option>
	                  <option value="China">China</option>
	                </select>
	              </div>
                </div>
               </div>
              <div class="row" style="margin-top: 10px;">
                <div class="col-sm-12">
                  <div class="input-group">
	                <span class="input-group-addon">备注信息</span>
	                <textarea class="form-control" rows="2"></textarea>
	              </div>
                </div>
              </div>
              
            </div>
        </div>
	</div>
</body>

<script type="text/javascript">
/**
 * 一切操作前的接口函数
 */
function beforeAction(opt){
	return true;
}

$(document).ready(function() {
	var us = $("#usertate").select2({minimumResultsForSearch: Infinity});
	$("#usertate").on('change.select2', function () {
		alert( $("#usertate").val()+' == '+$("#usertate option:selected").text() );
	});
	$("#usertate").parent().find('.select2-selection').on('click', function (evt) {
		evt.stopPropagation();
		evt.preventDefault(); 
		//alert( $("#usertate").val()+' =对对对= '+$("#usertate option:selected").text() );
	});
})
</script>
</html>