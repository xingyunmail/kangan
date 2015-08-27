<%--
Created by IntelliJ IDEA.
User: Administrator
Date: 2015/5/6
Time: 16:38
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>添加客户信息</title>
</head>
<body>
<div id="alert"></div>
<div class="block">
    <div class="navbar navbar-inner block-header">

        <div class="muted pull-left">添加客户信息</div>
        <input type="text" value="${custId}" id="custId">
    </div>
    <div class="block-content collapse in">
        <div class="span12">

            <form class="form-horizontal" id="form">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label" for="custName">客户姓名</label>

                                    <div class="controls">
                                        <input class="span5" id="custName">

                                        </input>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="phone">手机号</label>

                                    <div class="controls">
                                        <input id="phone" class="input-xlarge focused" size="11" type="text" value="15110141528">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="address">送货地址</label>

                                    <div class="controls">
                                        <textarea id="address" />
                                    </div>
                                </div>

                            </fieldset>
                            <button id="addCustomer" class="btn btn-primary btn-large" style="margin-left: 26%;">新增
                            </button>
                        </div>
                    </div>
                </div>



</form>
            </div></div></div>
<a href="javaScript:loadPage('customer/toList')" id="GoList" style="display: block">跳转到List</a>
</body>
</html>
<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="resources/css/ui-dialog.css">
<script src="resources/js/dialog-min.js"></script>
<script type="application/javascript">
    $(document).ready(function () {
//编辑
        var custId = $("#custId").val()
        if (custId != '') {
            console.log("---开始保存----");
            $("#addCustomer").html("保存");
            $.ajax({
                async: "false",
                type: "POST",
                url: "customer/getCustomerList",
                data: "custId=" + custId ,
                success: function (json) {
                    if (json.status == "success") {
                        console.log(json.data);
                        $("#custName").val(json.data[0].custName)
                        $("#phone").val(json.data[0].phone)
                        $("#address").val(json.data[0].address)
                    } else {

                    }
                }
            });
        }
        $("#addCustomer").click(function () {
            if ($("#custId").val() != '') {
                console.log("1111111111")
                submit(2);//编辑
            } else {//新增
                console.log("2222222222")
                submit(1);
            }
        })

    })

    function submit(v) {

        var custName = $("#custName").val().trim();
        var phone = $("#phone").val().trim();
        var address = $("#address").val().trim();
        var custId = $("#custId").val().trim();

        var params = {
            custName: custName,
            phone: phone,
            address: address,
            custId: custId
        }
        if (custName == "") {
            alert("客户姓名不能为空！");
            return false;
        }
        if (phone == "") {
            alert("手机号不能为空");
            return false;
        }
        console.log("-----------0--"+v);
        console.log(params);
        if (v == 1) {
            $.ajax("customer/insertCustomer", params, function (json) {
                console.log(json);
                if (json.status = "success") {
                    alert("添加成功");
                    $("#GoList").click();
                    //document.getElementById("GoList").click();
                    loadPage('customer/toAdd');
                } else {
                    alert("添加失败");
                }
            });
        } else if (v == 2) {
            console.log("---------------------------------");
            $.ajax({
                type: "POST",
                url: "customer/updateCustomer",
                data: params,
                dataType: "json",
                async:false,
                success: function (json) {
                    console.log("------------------2---------------");
                    console.log(json);
                    if (json.status = "success") {
                        alert("编辑成功");
//                    $("#GoList").click();
                        loadPage('customer/toList');
                   // document.getElementById("GoList").click();
                    } else {
                        alert("编辑失败");
                    }
                }
            });

        }
    }
</script>
