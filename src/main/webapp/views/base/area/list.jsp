<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/6
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>区域列表</title>
</head>
<body>
<%--搜索条件--%>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">区域列表</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">

                            <label class="control-label" for="areaName">区域名称</label>

                            <div class="controls">
                                <input type="text" class="input-medium" id="areaName">

                            </div>

                            <label class="control-label" for="lineId">所属线路</label>

                            <div class="controls">
                                <select class="span5" id="lineId">
                                    <%--<option value="0">--请选择--</option>--%>
                                </select>
                                <ul class="pager wizard">
                                    <li class="previous">
                                        <a href="javascript:void(0);" id="submit">提交</a>
                                    </li>
                                </ul>
                            </div>

                        </div>

                </div>
                <%--列表--%>
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">区域列表</div>
                    </div>
                    <div class="block-content collapse in">
                        <table class="table table-bordered table-striped">

                            <thead>
                            <tr>
                                <th>区域编号</th>
                                <th>区域名称</th>
                                <th>区域描述</th>
                                <th>所属线路</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <span class="span12" id="pageDiv"></span>
                    </div>
                </div>

<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="application/javascript">
    var startNum = 1;
    var limit = 10;
    var params={};
    $(document).ready(function () {
//        getListJson("prodType=2&startNum="+startNum+"&limit"+limit);
        getLineList();//获取线路列表
        initInfo();
        //搜索
        $("#submit").on("click", function () {
            initInfo();
        });

    });
    function  initInfo(){
        params = {
            areaName:$("#areaName").val(),
            lineid:$("#lineId").val(),
            startNum:startNum,
            limit:limit
        };
        console.log(params)
        getListJson(params);
    }
//获取线路列表
    function getLineList(){
        var htmlOption = "";
        $.post("area/getLineList",function(json){
            if(json.status="success"){
                console.log(json.data);
                $.each(json.data, function (index, item) {
                    htmlOption +=" <option value='"+item.lineId+"'>"+item.lineName+"</option>"
                });
            }
            if (json.status == "noRecord") {

            }
            $("#lineId").html(htmlOption);
        });
    }

    //获取列表
    function getListJson(v) {
        var resultN;
        $.post("area/getAreaList", v, function (json) {
            if (json.status == "success") {
                console.log(json.data);
                var discrible = "";
                $.each(json.data, function (index, item) {
                    $(".table tbody").html();
                    if(item.discrible==""){discrible=""};
                    resultN += "<tr><td>" + item.areaId + "</td>" +
                    "<td>" + item.areaName + "</td>" +
                    "<td>" + item.discrible + "</td>" +
                    "<td>" + item.lineName + "</td>" +
                    "<td><a tabindex=\"-1\"  href=\"javaScript:loadPage('area/edit?areaId="+item.areaId+"&lineId="+$("#lineId").val()+"')\" id=\"edit\" >编辑<a>" +
                    "&nbsp;<a href=\"javascript:void(0)\" onclick=\"javascript:deleteArea('"+item.areaId+"','"+$("#lineId").val()+"')\">删除</a>" +
                    "</tr>";

                });
                getPagination(json.count, startNum, limit);
            }
            if (json.status == "noRecord") {
                resultN = "<tr><td colspan='8'>暂无记录<td><tr>";
            }
            $(".table tbody").html(resultN);
        });
    }

    //删除
    function deleteArea(id,lineId){
        console.log(id);
        $.getJSON("area/deleteArea",{areaId:id,lineId:lineId}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    areaId:$("#areaId").val(),
                    lineId:$("#lineId").val(),
                    startNum:startNum,
                    limit:limit
                };
                alert("删除成功");
                getListJson(pageParams);
            }else{
                alert("删除失败");
            }
        });
    }
    //编辑
    function editId(id){
        $.getJSON("area/updateArea",{areaId:id,priceType:priceType}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    areaId:$("#areaId").val(),
                    lineId:$("#lineId").val(),
                    startNum:startNum,
                    limit:limit
                };

            }else{

            }
        });
    }
    //分页
    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum
        var pageParams = {
            prodName:$("#prodName").val(),
            priceType:$("#areaId").val(),
            startNum:startNum,
            limit:limit
        };
        getListJson(pageParams);
    }

    var dateTime = new Date();

    $("#startDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#endDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#delayDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
</script>
</body>
</html>