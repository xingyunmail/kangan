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
    <title>活动列表</title>
</head>
<body>
<%--搜索条件--%>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">活动</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">

                            <label class="control-label" for="activityName">活动名称</label>

                            <div class="controls">
                                <input type="text" class="input-medium" id="activityName">

                            </div>
                        </div>
                        <div class="span6">

                            <label class="control-label" for="selectActivityType">活动类型</label>

                            <div class="controls">
                                <select class="span5" id="selectActivityType">
                                    <option value="1">满数量赠</option>
                                    <option value="2">满金额赠</option>
                                </select>
                            </div>
                        </div>

                    </div>
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="startDatedatetimepicker">时效</label>

                            <div class="controls">
                                <div class="input-append date" id="startDatedatetimepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:ss">
                                    <input size="16" type="text" id="startDate">
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>
                                至
                                <div class="input-append date" id="endDatedatetimepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:ss">
                                    <input size="16" type="text" id="endDate">
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>
                            </div>

                        </div>
                        <div class="span6">

                            <label class="control-label" for="selectActivitystatus">状态</label>

                            <div class="controls">
                                <select class="span5" id="selectActivitystatus">
                                    <option value="1">进行中</option>
                                    <option value="0">已停止</option>
                                </select>
                            </div>
                        </div>
                        <div class="span6">
                            <ul class="pager wizard">
                                <li class="previous">
                                    <a href="javascript:void(0);" id="submit">提交</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                </div>
                <%--活动列表--%>
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">活动列表</div>
                    </div>
                    <div class="block-content collapse in">
                        <table class="table table-bordered table-striped">

                            <thead>
                            <tr>
                                <th>活动编号</th>
                                <th>活动名称</th>
                                <th>发布时间</th>
                                <th>促销类型</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <span class="span12" id="pageDiv"></span>
                    </div>
                </div>


                <%--延期--%>
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    ×
                                </button>
                                <h4 class="modal-title" id="myModalLabel">
                                    延期修改<input type="hidden" value="" id="delayupdateId" />
                                </h4>
                            </div>
                            <div class="modal-body">
                                <div class="controls" style="margin-left: 2px;">延期时间：
                                    <div class="input-append date" id="delayDatedatetimepicker"
                                         data-date-format="yyyy-mm-dd hh:ii:ss">
                                        <input size="16" type="text" id="delayDate">
                                        <span class="add-on"><i class="icon-th"></i></span>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal" id="closeWindow">关闭
                                </button>
                                <button type="button" class="btn btn-primary" id="delayButton">
                                    更改
                                </button>
                            </div>
                        </div>
                    </div>
                </div>


<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="application/javascript">
    var startNum = 1;
    var limit = 20;
    $(document).ready(function () {
        getListJson("startNum="+startNum+"&limit"+limit);
        //搜索
        $("#submit").on("click", function () {
            var discountName = $("#activityName").val();
            var discountType = $("#selectActivityType").val();
            var beginDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var discountStatus = $("#selectActivitystatus").val();
//            console.info(discountName+","+discountType+","+discountStatus);
            var rs = "discountName=" + discountName + "&discountType=" + discountType + "&beginDate=" + beginDate + "&endDate=" + endDate + "&discountStatus=" + discountStatus + "&startNum="+startNum+"&limit"+limit;
            getListJson(rs);
        });
        //延期修改
        $("#delayButton").on("click",function(){
            var endDate = $("#delayDate").val();
            var discountId = $("#delayupdateId").val();
            $.getJSON("activity/update",{endDate:endDate,discountId:discountId,discountStatus:"update"}, function (json) {
                if (json.status == "success") {
                    alert("更新成功");
                    getListJson("startNum="+startNum+"&limit"+limit);
                    $("#closeWindow").click();

                }else{
                    alert("更新失败");
                }
            });

        });


    });

    //获取活动列表
    function getListJson(v) {
        var resultN;
        $.post("activity/getListResult", v, function (json) {
            if (json.status == "success") {
                $.each(json.data, function (index, item) {
                    $(".table tbody").html();
                    var type = item.discountType;
                    var status = item.discountStatus;
                    var str_type, str_status;
                    if (type == 1) {
                        str_type = "满数量赠";
                    } else if (type == 2) {
                        str_type = "满金额赠";
                    }
                    if (status == 1) {
                        str_status = "进行中";
                    } else if (status == 0) {
                        str_status = "已停止";
                    }
                    resultN += "<tr><td>" + item.discountId + "</td>" +
                    "<td>" + item.discountName + "</td>" +
                    "<td>" + item.insertDate + "</td>" +
                    "<td>" + str_type + "</td>" +
                    "<td>" + item.beginDate + "</td>" +
                    "<td>" + item.endDate + "</td>" +
                    "<td>" + str_status + "</td>" +
                    "<td><a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"javascript:updateID("+item.discountId+")\" >延期</a>" +
                    "&nbsp;<a href=\"javascript:voi(0)\" onclick=\"javascript:stopID("+item.discountId+")\">停止</a>" +
                    "&nbsp;<a tabindex=\"-1\"  href=\"javaScript:loadPage('activity/edit?discountId="+item.discountId+"')\" id=\"edit\" >编辑<a></td>" +
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
    //赋值更新ID
    function updateID(id){
        $("#delayupdateId").val(id);
    }

    //停止活动
    function stopID(id){
        $.getJSON("activity/update",{discountId:id,discountStatus:"close"}, function (json) {
            if (json.status == "success") {
                alert("停止成功");
                getListJson("startNum="+startNum+"&limit"+limit);
            }else{
                alert("停止失败");
            }
        });
    }
    //编辑
    function editId(id){

    }
    //分页
    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getList(specialData());
    }

    var dateTime = new Date();

    // $("#inputOrderid").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) + " " + fillZero(dateTime.getHours(), 2) + ":" + fillZero(dateTime.getMinutes(), 2));

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