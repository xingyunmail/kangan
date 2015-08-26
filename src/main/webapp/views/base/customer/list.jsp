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
    <title>客户列表</title>
</head>
<body>
<%--搜索条件--%>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">客户列表</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">

                            <label class="control-label" for="custName">客户姓名</label>

                            <div class="controls">
                                <input type="text" class="input-medium" id="custName">

                            </div>

                            <label class="control-label" for="phone">客户手机号</label>

                            <div class="controls">
                                <input class="span5" id="phone"> </input>
                                <ul class="pager wizard">
                                    <li class="previous">
                                        <a href="javascript:void(0);" id="submit">查询</a>
                                    </li>
                                </ul>
                            </div>

                        </div>

                </div>
                <%--列表--%>
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">客户列表</div>
                    </div>
                    <div class="block-content collapse in">
                        <table class="table table-bordered table-striped">

                            <thead>
                            <tr>
                                <th>客户编号</th>
                                <th>客户姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
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
//        getLineList();//获取线路列表
        initInfo();
        //搜索
        $("#submit").on("click", function () {
            initInfo();
        });

    });
    function  initInfo(){
        params = {
            custName:$("#custName").val(),
            phone:$("#phone").val(),
            startNum:startNum,
            limit:limit
        };
        console.log(params)
        getListJson(params);
    }

    //获取列表
    function getListJson(v) {
        var resultN;
        $.post("customer/getCustomerList", v, function (json) {
            if (json.status == "success") {
                console.log(json.data);
                $.each(json.data, function (index, item) {
                    $(".table tbody").html();
                    resultN += "<tr><td>" + item.custId + "</td>" +
                    "<td>" + item.custName + "</td>" +
                    "<td>" + item.phone + "</td>" +
                    "<td>" + item.address + "</td>" +
                    "<td><a tabindex=\"-1\"  href=\"javaScript:loadPage('customer/edit?custId="+item.custId+"')\" id=\"edit\" >编辑<a>" +
                    "&nbsp;<a href=\"javascript:void(0)\" onclick=\"javascript:deleteCustomer('"+item.custId+"')\">删除</a>" +
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
    function deleteCustomer(id){
        console.log(id);
        $.ajax("customer/deleteCustomer",{custId:id}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    custName:$("#custName").val(),
                    phone:$("#phone").val(),
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
        $.ajax("customer/updateCustomer",{custId:custId}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    custName:$("#custName").val(),
                    phone:$("#phone").val(),
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
            custName:$("#custName").val(),
            phone:$("#phone").val(),
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