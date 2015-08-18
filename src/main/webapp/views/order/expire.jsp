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
        <div class="muted pull-left">到期提醒</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <div class="span12">
            <div class="well well-small">
                <div>
                    配送结束日期:
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-small" id="startDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    至
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-small" id="endDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    区域:
                    <select id="areaId" multiple="multiple">

                    </select>
                    送奶员编号:<input type="text" class="input-small" id="userId"/>
                    剩余奶量:<input type="text" class="input-small" id="leftMin"/>至<input type="text" class="input-small"
                                                                                      id="leftMax"/>
                </div>
                <div>
                    <a href="javascript:void(0);" type="button" class="btn btn-primary" onclick="searchLeft();">查询</a>
                    <input type="checkbox" id="searchType"/>只含一个未配送完成订单
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <td>区域</td>
                <td>送奶员</td>
                <td>订单编号</td>
                <td>客户姓名</td>
                <td>客户手机</td>
                <td>地址</td>
                <td>配送结束时间</td>
                <td>剩余袋数</td>
            </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>
        <span class="span12" id="pageDiv">

        </span>
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
        selArea("areaId");

        getTables();
    });

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


    function searchLeft() {
        startNum = 1;
        getTables();
    }

    function getTables() {
        $("#tbody").empty();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var areaId = selVal();
        var userId = $("#userId").val();
        var leftMin = $("#leftMin").val();
        var leftMax = $("#leftMax").val();
        var searchType = $("#searchType").prop('checked') ? 1 : 0;

        var data = {
            "startDate": startDate,
            "endDate": endDate,
            "areaId": areaId,
            "userId": userId,
            "leftMin": leftMin,
            "leftMax": leftMax,
            "searchType": searchType,
            "startNum": startNum,
            "limit": limit
        }

        $.get("order/expireList", data,
                function (data, status) {
                    if (status == "success") {
                        if (data.status == "success") {
//                            message('success', '查询成功！');
                            $.each(data.data, function (index, item) {
                                $("#tbody").append("<tr><td>" + item.areaName + "</td><td>" + item.userName + "</td><td>" + item.orderId + "</td><td>" + item.custName + "</td><td>" + item.custPhone + "</td><td>" + item.custAddr + "</td><td>" + item.endDate + "</td><td>" + item.leftCount + "</td></tr>");
                            });
                            getPagination(data.count, startNum, limit);
                        } else if (data.status == "noRecord") {
                            message('error', '未找到符合条件数据！');
                        } else {
                            message('error', '查询失败！');
                        }
                    }
                    else {
                        message('error', '请求异常！');
                    }
                }, "json"
        );
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getTables();
    }

</script>