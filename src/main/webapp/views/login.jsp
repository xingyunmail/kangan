<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title>后台管理登录</title>
    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/styles.css" rel="stylesheet" media="screen">
</head>
<body id="login">
<div class="container">
    <form class="form-signin">
        <h2 class="form-signin-heading">康安管理系统</h2>
        <input type="text" id="userId" class="input-block-level" placeholder="用户名">
        <input type="password" id="password" class="input-block-level" placeholder="密码">
        <button class="btn btn-large btn-primary" type="submit" id="loginButton">登录</button>
    </form>
</div>
<script src="resources/js/jquery-1.9.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
</body>

<script type="application/javascript">

    $(function(){
        $("body").keydown(function() {
            if (event.keyCode == "13") {
                $('#loginButton').click();
            }
        });
    });

    $(document).ready(function(){

        $("#loginButton").click(function(){
            var userId = $("#userId").val();
            var password = $("#password").val();
            $.ajax({
                type: "post",
                url: "user/login",
                data:{"userId":userId,"password":password},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.status == "success") {
                        window.location = "/";
                    }else{
                        alert("用户名或密码错误!");
                    }
                }
            });
        });

    });

</script>

</html>
