<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<%
	String appRoot = request.getContextPath();
%>
<script src="<%=appRoot%>/assets/lib/jquery/jquery.js"></script>
<script>
	$(function() {
		// 浏览器上的websocket对象,用来和服务端通信.
		var socket = null;
		$("#login").click(
				function() {
					var userName = $("#userName").val();
					if ($.trim(userName) == "") {
						return;
					}
					// 创建websocket对象
					socket = new WebSocket('ws://localhost:9999/myapp/websocket.connection?userName='+ userName);
					registerEvent();
				});

		$("#logout").click(function() {
			socket.close();
		});

		$("#send").click(function() {
			var userName = $("#userName").val();
			$("#messagePanel").append("<div>"+userName+":" + $("#message").val() + "</div>");
			var to = $("#to_userName").val();
			var mesg = JSON.stringify({from:userName,to:to,mesg:$("#message").val()});
			socket.send(mesg);
		});

		function registerEvent() {
			socket.onopen = function(event) {
				$("#messagePanel").append(
						"<div>websocket open successfully.</div>");
			}

			socket.onmessage = function(event) {
				$("#messagePanel").append("<div>"+$("#to_userName").val()+":" + event.data + "</div>");
			};

			socket.onclose = function(event) {
				$("#messagePanel").append(
						"<div>websocket close successfully.</div>");
			};
		}
	});
</script>
<style>
.card {
	margin: 20px;
}
</style>
</head>
<body>
	<div class="card">
		from:<input id="userName" value="" placeholder="_from" />
		to:<input id="to_userName" value="" placeholder="_to" />
		<button id="login">login</button>
		<button id="logout">logout</button>
	</div>
	<div class="card">
		message:<input id="message" value="" placeholder="input your message" />
		<button id="send">send</button>
	</div>

	<div class="card" id="messagePanel"></div>
</body>

