<%--
  Created by IntelliJ IDEA.
  User: scar
  Date: 15/5/14
  Time: 上午9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">奶款单</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <div class="span12">
            <div class="well well-small">
                <div>
                    订单生成时间:
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="startDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    至
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="endDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    区域:
                    <select id="areaId" multiple="multiple">

                    </select>
                    送奶员编号:<input type="text" class="input-small" id="userId"/>
                </div>
                <div>
                    <a href="javascript:void(0);" type="button" class="btn btn-primary" onclick="getList();">查询</a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <td>序号</td>
                <td>区域</td>
                <td>送奶员名称</td>
                <td>订单总数</td>
                <td>金额合计</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>
        <span class="span12" id="pageDiv">

        </span>
    </div>

</div>

<div id="myModal" class="modal hide" style="width: 70%;left: 30%">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button">&times;</button>
        <h3>详情</h3>
    </div>
    <div class="modal-body">
        <table class="table table-bordered">
            <thead>
            <tr>
                <td colspan="7" id="td_info"></td>
            </tr>
            <tr>
                <td>订单编号</td>
                <td>客户编码</td>
                <td>联系方式</td>
                <td>地址</td>
                <td>商品</td>
                <td>单价</td>
                <td>金额</td>
            </tr>
            </thead>
            <tbody id="tbody_info">

            </tbody>
        </table>

    </div>
    <div class="modal-footer">
        <a data-dismiss="modal" class="btn" href="#">取消</a>
    </div>
</div>


<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="resources/css/jquery.multiSelect.css" rel="stylesheet" media="screen">
<link href="resources/css/ui.min.css" rel="stylesheet" media="screen">

<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.multiselect.min.js"></script>
<script src="resources/js/multiselect.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="application/javascript">
    var startNum = 1;
    var limit = 20;

    $(document).ready(function () {
        dateTime();
        getTables();
        selArea("areaId");
    });
    //查看详情
    function getInfo(userId, userName, totalPrice) {
        $("#td_info").empty();
        var startDate = $("#startDate").val();
        if(startDate!="" && startDate.length>0){
            startDate+=" 00:00:00";
        }
        var endDate = $("#endDate").val();
        if(endDate!="" && endDate.length>0){
            endDate+=" 23:59:59";
        }
        var data = {
            "startDate": startDate,
            "endDate": endDate,
            "userId": userId
        };
        $.ajax(
                {
                    url: "finance/newInfo",
                    type: "POST",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (data.status == "success") {
                            $("#tbody_info").empty();
                            $("#td_info").html("送奶员：" + userName + "     合计：" + totalPrice);
                            $.each(data.data, function (index, item) {

                                var product = new Array();
                                console.log(item.products);
                                product = item.products.split(",");
                                console.log(item.product);
                                var price = new Array();
                                price = item.prices.split(",");

                                for (var i = 0; i < product.length; i++) {
                                    if (i == 0) {
                                        $("#tbody_info").append("<tr><td rowspan='" + product.length + "'>" + item.orderId + "</td><td rowspan='" + product.length + "'>" + item.custId + "</td><td rowspan='" + product.length + "'>" + item.custPhone + "</td><td rowspan='" + product.length + "'>" + item.custAddr + "</td><td >" + product[i] + "</td><td>" + price[i] + "</td><td rowspan='" + product.length + "'>" + item.totalPrice + "</td></tr>");
                                    } else {
                                        $("#tbody_info").append("<tr><td >" + product[i] + "</td><td>" + price[i] + "</td></tr>");
                                    }
                                }

                            });
                        } else {
                            message("error", "没有符合条件的数据！");
                        }
                    },
                    dataType: "json",
                    contentType: "application/json"
                });
    }

    function dateTime() {
        $(".date").datetimepicker({
            startView: 2,
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
    }

    function getTables() {

        var startDate = $("#startDate").val();
        if(startDate!="" && startDate.length>0){
            startDate+=" 00:00:00";
        }
        var endDate = $("#endDate").val();
        if(endDate!="" && endDate.length>0){
            endDate+=" 23:59:59";
        }
        var userId = $("#userId").val();
        var areaId = selVal();
        var data = {
            "startDate": startDate,
            "endDate": endDate,
            "areaId": areaId,
            "userId": userId,
            "startNum": startNum,
            "limit": limit
        };
        $("#tbody").empty();
        $.ajax(
                {
                    url: "finance/newList",
                    type: "POST",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (data.status == "success") {
                            $("#tbody").empty();
                            var totalPrice = 0;
                            var totalOrder = 0;
                            $.each(data.data, function (index, item) {
                                $("#tbody").append("<tr><td>" + Number(index + 1) + "</td><td>" + item.areaName + "</td><td>" + item.userName + "</td><td>" + item.orderCounts + "</td><td>" + item.priceCounts + "</td>" +
                                "<td><a href='#myModal' data-toggle='modal' class='btn btn-info' onclick='getInfo(\"" + item.userId + "\",\"" + item.userName + "\",\"" + item.priceCounts + "\")'>详情</a></td></tr>");
                                totalPrice += Number(item.priceCounts);
                                totalOrder += Number(item.orderCounts);
                            });
                            $("#tbody").append("<tr><td colspan='3'>合计：</td><td>" + totalOrder + "</td><td>" + totalPrice.toFixed(2) + "</td><td></td></tr>");
                            getPagination(data.count, startNum, limit);
                        } else {
                            message("error", "没有符合条件的数据！");
                        }
                    },
                    dataType: "json",
                    contentType: "application/json"
                });
    }

    function getList() {
        startNum = 1;
        getTables();
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getTables();
    }

</script>
