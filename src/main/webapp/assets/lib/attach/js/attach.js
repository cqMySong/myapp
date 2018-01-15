var _attachRoot = app.root+'/assets/lib/attach/';
function _getFileImg(fname){
	var fimg = 'file.png';
	if(fname){
		if(fname.indexOf('.xls')>0||fname.indexOf('.xlsx')>0){
			fimg = 'excel.png';
		}else if(fname.indexOf('.jpg')>0||fname.indexOf('.jpeg')>0	
			||fname.indexOf('.bmp')>0||fname.indexOf('.gif')>0){
			fimg = 'jpg.png';
		}else if(fname.indexOf('.pdf')>0){
			fimg = 'pdf.png';
		}else if(fname.indexOf('.png')>0){
			fimg = 'png.png';
		}else if(fname.indexOf('.ppt')>0||fname.indexOf('.pptx')>0){
			fimg = 'ppt.png';
		}else if(fname.indexOf('.rar')>0||fname.indexOf('.zip')>0){
			fimg = 'rar.png';
		}else if(fname.indexOf('.doc')>0||fname.indexOf('.docx')>0){
			fimg = 'word.png';
		}
	}
	return fimg;
}
var attachImgRoot =_attachRoot+'img/';
function getTempFileItemEl(file){
	var _fileItem = [];
	_fileItem.push('<li class="media" id="'+file.id+'">');
		_fileItem.push('<a href="#">');
			_fileItem.push('<div class="media-left" style="padding-top:10px;">');
				_fileItem.push('<img src="'+attachImgRoot+_getFileImg(file.name)+'">');
			_fileItem.push('</div>');
			_fileItem.push('<div class="media-body">');
				_fileItem.push('<h4 class="media-heading">'+file.name);
					_fileItem.push('<span style="float:right;">'+_convertFileSize(file.size)+'<span class="_percent">('+file.percent+'%)</span></span>');
				_fileItem.push('</h4>');	
				_fileItem.push('<div style="border:1px solid #bdc3d1;width:100%; ">');	
					_fileItem.push('<div class="_process" style="background-color: #1e649f;width:'+file.percent+'%;">&nbsp;</div>');
				_fileItem.push('</div>');		
				_fileItem.push('<span class="_statusMesg"><i class="fa fa-coffee"></i>&nbsp;文件准备就绪，准备上传......</span>');
			_fileItem.push('</div>');
			_fileItem.push('<div class="media-right _del" style="padding-top:10px;">');
				_fileItem.push('<img src="'+attachImgRoot+'del.png">');
			_fileItem.push('</div>');
		_fileItem.push('</a>');
	_fileItem.push('</li>');
   return _fileItem.join('');
}
function _addFileItemsDom(files){
	if (files) {
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			var _htm = getTempFileItemEl(file);
			$(_htm).data('file',file);
			$('#fileList').append($(_htm));
		}
	}
}
function _initFileEvent(up, files){
	if(up&&files){
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			$('li#'+file.id).find('div._del').click(file,function(e){
				var thisFile = e.data;
				_removeFileItemDom(thisFile);
				up.removeFile(thisFile.id);
			});
		}
	}
}
function _removeFileItemDom(file){
	if(file){
		var fid = file.id;
		$('#'+fid).remove();
	}
}
function _changeTwoDecimal(x){
	var f_x = parseFloat(x);
	if (isNaN(f_x)){
		return x;
	}
	f_x = Math.round(f_x *100)/100;
	return f_x;
}

function _convertFileSize(size){
	var kb = 1024;
	var mb = kb*1024;
	var gb = mb*1024;
	var toDisplay = size+'B';
	if(size>gb){
		toDisplay =  _changeTwoDecimal(size/gb)+"GB";
	}else if(size>mb){
		toDisplay =  _changeTwoDecimal(size/mb)+"MB";
	}else if(size>kb){
		toDisplay =  _changeTwoDecimal(size/kb)+"KB";
	}
	return toDisplay;
}
function _getUploadParams(formData){
	formData.append("sourceBillID", getBillId());
	return formData;
}

var _upFiles = 0;
var attachUrl = app.root+'/base/attach/upload';
$(document).ready(function() {
	var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html
			flash_swf_url : _attachRoot+'js/Moxie.swf',
			silverlight_xap_url : _attachRoot+'js/Moxie.xap',
	        url : attachUrl,//上传文件路径
            chunk_size : '5mb',//分块大小，小于这个大小的不分块
            unique_names : true,//生成唯一文件名
            browse_button : 'selectFile', 
            max_file_size : '5gb',//100b, 10kb, 10mb, 1gb
          	getOtherPrams :_getUploadParams,
            init : {
				FilesAdded: function(up, files) {
					_addFileItemsDom(files);
					_initFileEvent(up,files);
					_upFiles = files.length;
					return false;
				},
                FileUploaded : function(up, file, info) {//文件上传完毕触发
                   if(file){
                   	 var fid = file.id;
                   	 if(file.percent==100){
                   		var _html = '<i class="fa fa-coffee"></i>&nbsp;系统已经上传完毕待后续处理中.....</span>';
                   	 	$('#'+fid).find('span._statusMesg').html(_html);
                   	 }
                   }
                },
                UploadComplete : function( up,files ) {
                	if(files){
                		for(var i=0;i<files.length;i++){
                			_removeFileItemDom(files[i]);
                		}
						_upFiles = 0;
                	}
                },
                UploadProgress : function( up,file ) {
                	if(file){
                		var fid = file.id;                		
                		var _percent = file.percent+'%';
                		$('#'+fid).find('span._percent').html('('+_percent+')');
                		$('#'+fid).find('div._process').css({width:_percent});
                		
                		var msgBar = "文件正在上传中......";
                		if(_percent=='100%'){
                			msgBar = "文件上传完毕，系统处理中";
                		}
                		var _html = '<i class="fa fa-coffee"></i>&nbsp;'+msgBar+'.....</span>';
                		$('#'+fid).find('span._statusMesg').html(_html);
                		refesh = true;
                		$('#btnUploader').html('<i class="fa fa-upload"></i>&nbsp;开始上传');
                	}
                }
            }
        });
	uploader.init();
	$('#btnUploader').click(function(){
		var _text = '开始上传';
		var _icon = 'fa-upload';
	    if(_upFiles>0){
			var bid = getBillId();
			if(bid){
				if(uploader.state == plupload.STOPPED){
					uploader.start();
					_text = '暂停上传';
					_icon = 'fa-stop';
				}else{
					uploader.stop();
				}
			}else{
				alert('请先保存对应的业务单据!');
			}
		}else{
			alert('队列中无文件可以操作!');
		}
	    $(this).html('<i class="fa '+_icon+'"></i>&nbsp;'+_text);
	});
})