<%--
  Created by IntelliJ IDEA.
  User: lu.wang
  Date: 15/3/26
      Time: 下午4:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<% request.setCharacterEncoding("utf-8");%>

<html>
<head>
    <title>康安运营平台</title>
    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/styles.css" rel="stylesheet" media="screen">
</head>
<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="javaScript:void(0);">康安运营平台</a>

            <div class="nav-collapse collapse">
                <%--用户信息--%>
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i
                                class="icon-user"></i><span id="username"></span><i class="caret"></i>

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                            <a tabindex="-1" href="javaScript:loadPage('user/topsw')">密码修改</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a tabindex="-1" href="#" onclick="logout()">登出</a>
                            </li>
                        </ul>
                    </li>
                </ul>
                <%--菜单信息--%>
                <ul class="nav" id="menu_bar">

                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12" id="content">
        </div>
        <div class="loader" id="loading" hidden="hidden">加载中...</div>
    </div>
    <hr>
    <footer>
        <p style="text-align: center">Copyright&copy; 康安 2015 All rights reserved</p>
    </footer>
    <input type="hidden" id="button_list" value="">
</div>

<div id="cover" class="cover">
    <img src='resources/img/loading.gif' style="position: absolute; left:50%; Top:50%;">
</div>
<!--/.fluid-container-->
<script src="resources/js/jquery-1.9.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/menu.js"></script>
<script>

    $(document).ajaxStart(function () {
        $("#cover").show();
    }).ajaxStop(function () {
        $("#cover").hide();
    });

    $(document).ready(function () {
        getMenu();
        getbuttonlist();
        getInfo();
    });
    function loading() {

    }

    function getInfo() {
        $.getJSON("user/getinfo", function (data) {
            if (data.status == "success") {
                $("#username").html(data.data.userId);
            }
        });
    }

    function logout() {
        $.getJSON("user/logout", function (data) {
            if (data.status == "success") {
                window.location.href = "";
            } else {
                alert("退出失败,请重新操作!");
            }
        });
    }


    function message(type, message) {
        var classType = "";
        var strong = "";
        if (type == 'success') {
            classType = "alert-success";
            strong = "成功！";
        } else if (type == 'info') {
            classType = "alert-info";
            strong = "信息！";
        } else if (type == 'error') {
            classType = "alert-error";
            strong = "失败！";
        } else {
            strong = "警告！";
        }
        $("#alert").html("<div class='alert " + classType + "'><button class='close' data-dismiss='alert'>&times;</button><strong>" + strong + "</strong>" + message + "</div>");
        setTimeout("$('#alert').empty();", 5000);

    }

</script>
</body>

</html>