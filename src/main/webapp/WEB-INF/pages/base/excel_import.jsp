<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>导入清单</title>
</head>
<script type="text/javascript">
</script>
<body style="padding:2px;overflow: hidden;">
<div class="panel">
	<div class="btn-group mt10">
		<button class="btn btn-success" id="selectFile" type="button">
			<i class="glyphicon glyphicon-file"></i>&nbsp;选择文件
		</button>
		<button class="btn btn-success" id="btnUploader"  type="button">
			<i class="fa fa-upload"></i>&nbsp;开始上传
		</button>
		<a class="btn btn-success" id="btnTemplate" href="${appRoot}/importTemplate/${templateName}">
			<i class="fa fa-download"></i>&nbsp;文件模版
		</a>
	</div>

	<ul id="fileList" class="media-list media-list-contacts mt20"
		style="height:370px;overflow-x:hidden;overflow-y: auto;border:1px solid #bdc3d1;" >

	</ul>

</div>
</body>
<%@include file="/WEB-INF/pages/inc/webBase.inc"%>
<script type="text/javascript">
    var billId = 'importExcel';
    function getBillId(){
        return billId;
    }
    //附件断点续传功能的相关配置
    var _attach_initFile = {
        url:app.root+'/base/attach/initFile',
        sourceId:billId
    };
    function getImportUrl() {
		return '${uploadPath}';
    }
</script>
<script src="${appRoot}/assets/lib/attach/js/moxie.js"></script>
<script src="${appRoot}/assets/lib/attach/js/plupload.dev.js?v=2"></script>
<script src="${appRoot}/assets/lib/attach/js/jquery.md5.js"></script>
<script src="${appRoot}/assets/lib/attach/js/attach_import.js?v=33"></script>
<iframe id="downWin" width=0 height=0 marginheight=0 marginwidth=0 scrolling=no src="" style="display: none;"></iframe>
</html>