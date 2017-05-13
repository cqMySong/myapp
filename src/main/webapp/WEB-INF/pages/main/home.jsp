<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>mysong</title>
</head>
<%@include file="../inc/webBase.inc"%>
<body>
	<div class="row" style="padding: 5px;">
		<div class="col-md-10 col-lg-9 dash-left">
			<div class="row panel-quick-page">
				<div class="col-xs-4 col-sm-5 col-md-4 page-user">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">用户管理</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-person-stalker"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-4 col-md-4 page-products">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Manage Products</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="fa fa-shopping-cart"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-3 col-md-2 page-events">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Events</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-ios-calendar-outline"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-3 col-md-2 page-messages">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Messages</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-email"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-5 col-md-2 page-reports">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Reports</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-arrow-graph-up-right"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-4 col-md-2 page-statistics">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Statistics</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-ios-pulse-strong"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-4 col-md-4 page-support">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Manage Support</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-help-buoy"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-4 col-md-2 page-privacy">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Privacy</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-android-lock"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-4 col-sm-4 col-md-2 page-settings">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">Settings</h4>
						</div>
						<div class="panel-body">
							<div class="page-icon">
								<i class="icon ion-gear-a"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- row -->

		</div>
		<!-- col-md-9 -->
		<div class="col-md-2 col-lg-3 dash-right">
			<div class="row">
				<div class="panel panel-danger panel-weather">
					<div class="panel-heading">
						<h4 class="panel-title">天气预报</h4>
					</div>
					<div class="panel-body inverse">
						<div class="row mb10">
							<div class="col-xs-6">
								<h2 class="today-day">星期五</h2>
								<h3 class="today-date">2016-02-05</h3>
							</div>
							<div class="col-xs-6">
								<i class="wi wi-hail today-cloud"></i>
							</div>
						</div>
						<p class="nomargin">天气多云转晴，适合外出小恬，漫步!</p>
						<div class="row mt10">
							<div class="col-xs-7">
								<strong>气温:</strong>+12
							</div>
							<div class="col-xs-5">
								<strong>湿度:</strong> +30 mph
							</div>
						</div>
					</div>
				</div>

				<div class="panel panel-primary list-announcement">
					<div class="panel-heading">
						<h4 class="panel-title">工作任务清单</h4>
					</div>
					<div class="panel-body">
						<ul class="list-unstyled mb20">
							<li><a href="">预算管理系统升级...</a> <small>June 30, 2015
									<a href="">7 shares</a>
							</small></li>
							<li><a href="">内部银行系统升级...</a> <small>June 15, 2015
									&nbsp; <a href="">11 shares</a>
							</small></li>
							<li><a href="">系统检测...</a> <small>June 15, 2015
									&nbsp; <a href="">2 shares</a>
							</small></li>
						</ul>
					</div>
					<div class="panel-footer">
						<button class="btn btn-primary btn-block">
							跟多任务跟踪 <i class="fa fa-arrow-right"></i>
						</button>
					</div>
				</div>


				<div class="panel panel-success">
					<div class="panel-heading">
						<h4 class="panel-title">Recent User Activity</h4>
					</div>
					<div class="panel-body">
						<ul class="media-list user-list">
							<li class="media">
								<div class="media-left">
									<a href="#"> <img class="media-object img-circle"
										src="./assets/images/photos/user2.png" alt="">
									</a>
								</div>
								<div class="media-body">
									<h4 class="media-heading nomargin">
										<a href="">Floyd M. Romero</a>
									</h4>
									is now following <a href="">Christina R. Hill</a> <small
										class="date"><i class="glyphicon glyphicon-time"></i>
										Just now</small>
								</div>
							</li>
							<li class="media">
								<div class="media-left">
									<a href="#"> <img class="media-object img-circle"
										src="./assets/images/photos/user5.png" alt="">
									</a>
								</div>
								<div class="media-body">
									<h4 class="media-heading nomargin">
										<a href="">Nicholas T. Hinkle</a>
									</h4>
									liked your video on <a href="">The Discovery</a> <small
										class="date"><i class="glyphicon glyphicon-time"></i>
										June 24, 2015</small>
								</div>
							</li>
							<li class="media">
								<div class="media-left">
									<a href="#"> <img class="media-object img-circle"
										src="./assets/images/photos/user2.png" alt="">
									</a>
								</div>
								<div class="media-body">
									<h4 class="media-heading nomargin">
										<a href="">Floyd M. Romero</a>
									</h4>
									liked your photo on <a href="">My Life Adventure</a> <small
										class="date"><i class="glyphicon glyphicon-time"></i>
										June 24, 2015</small>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>




</body>

</html>