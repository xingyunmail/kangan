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
    <title>添加区域</title>
</head>
<body>
<div id="alert"></div>
<div class="block">
    <div class="navbar navbar-inner block-header">

        <div class="muted pull-left">添加区域</div>
        <input type="text" value="${areaId}" id="areaId">
        <input type="text" value="${lineId}" id="lineId2">
    </div>
    <div class="block-content collapse in">
        <div class="span12">

            <form class="form-horizontal" id="form">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label" for="lineId">线路描述</label>

                                    <div class="controls">
                                        <select class="span5" id="lineId">

                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="areaName">区域名称</label>

                                    <div class="controls">
                                        <input id="areaName" class="input-xlarge focused" type="text" value="abcd">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="discrible">区域描述</label>

                                    <div class="controls">
                                        <textarea id="discrible" value="10"/>
                                    </div>
                                </div>

                            </fieldset>
                            <button id="addArea" class="btn btn-primary btn-large" style="margin-left: 26%;">新增
                            </button>
                        </div>
                    </div>
                </div>

                <a href="javaScript:loadPage('area/toList')" id="GoList" style="display: block">跳转到List</a>

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
        getLineList();

        var lineId = $("#lineId2").val()
        var areaId = $("#areaId").val()
        if (areaId != '') {
            console.log("---开始编辑----");
            $("#addArea").html("编辑");
            $.ajax({
                async: "false",
                type: "POST",
                url: "area/editArea",
                data: "areaId=" + areaId + "&lineId=" + lineId,
                success: function (json) {
                    if (json.status == "success") {
                        console.log(json);
                        $("#areaName").val(json.data.areaName)
                        $("#lineId").val(json.data.lineId)
                        $("#discrible").val(json.data.discrible)
                    } else {

                    }
                }
            });
        }
        $("#addArea").click(function () {
            console.log("=================");
            if ($("#areaId").val() != '') {
                submit(2);//编辑
            } else {//新增
                submit(1);
            }
        })

    })
    //获取线路列表
    function getLineList() {
        var htmlOption = "";
        $.post("area/getLineList", function (json) {
            if (json.status = "success") {
                console.log(json.data);
                $.each(json.data, function (index, item) {
                    htmlOption += " <option value='" + item.lineId + "'>" + item.lineName + "</option>"
                });
            }
            if (json.status == "noRecord") {

            }
            $("#lineId").html(htmlOption);
        });
    }
    function submit(v) {

        var areaName = $("#areaName").val().trim();
        var lineId = $("#lineId").val().trim();
        var discrible = $("#discrible").val().trim();
        var areaId = $("#areaId").val().trim();

        var lineId2 = $("#lineId2").val();
        var params = {
            areaId: areaId,
            areaName: areaName,
            lineid: lineId,
            discrible: discrible
        }
        if (areaName == "") {
            alert("区域名称不能为空！");
            return false;
        }
        if (lineId == "") {
            alert("请选择区域");
            return false;
        }
        console.log("===================");
        console.log(params);
        if (v == 1) {
            $.post("area/addArea", params, function (json) {
                console.log(json);
                if (json.status = "success") {
                    alert("添加成功");
                    document.getElementById("GoList").click();
                } else {
                    alert("添加失败");
                }
            });
        } else if (v == 2) {
            $.post("area/updateArea", params, function (json) {
                console.log(json);
                if (json.status = "success") {

                    alert("编辑成功");
                    document.getElementById("GoList").click();
                } else {
                    alert("编辑失败");
                }
            });
        }
    }
</script>
