<%--
  Created by IntelliJ IDEA.
  User: zzq
  Date: 15-5-7
  Time: 下午2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link href="resources/css/jquery.multiSelect.css" rel="stylesheet"/>
<link href="resources/css/ui.min.css" rel="stylesheet"/>
<link href="resources/css/ui-dialog.css" rel="stylesheet" media="screen">

<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">订购工单列表</div>
    </div>
    <div id="alert"></div>
    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="span6">
                日期：
                <div class="input-append date" id="datetimepickerbegin" data-date-format="yyyy-mm-dd hh:ii">
                    <input size="5" type="text" id="begin">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
                <div class="input-append date" id="datetimepickerend" data-date-format="yyyy-mm-dd hh:ii">
                    <input size="5" type="text" id="end">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
            </div>
            <div class="span3">
                区域：
                <select id="example" class='span1 m-wrap' name="example" title="区域" multiple="multiple">
                </select>
                <input type="hidden" id="areaVal">
            </div>
            <div class="span3">
                状态：
                <select id='selStus' class='span4 m-wrap'></select>
            </div>
            <div class="span3">
                <button class="btn order-btn" id="search" onclick="getPurchase()">查 询</button>
            </div>
        </div>
        <div class="row span12">
            <button class="btn order-btn" id="export" type="button" onclick="">导出</button>
        </div>
        <span>&nbsp</span>
        <div id="messageBox"></div>
        <table style="table-layout: fixed;WORD-BREAK: break-all; WORD-WRAP: break-word" class="table table-bordered table-striped" id="result">
            <thead>
            <tr>
                <th>工单号</th>
                <th>提交时间</th>
                <th>客户姓名</th>
                <th>客户手机</th>
                <th>客户地址</th>
                <th>记录</th>
                <th>状态</th>
                <th>备注</th>
                <th>区域</th>
                <th>送奶员</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="purchase">
            </tbody>
        </table>
        <span class="span12" id="pageDiv"></span>
    </div>
    <div id="dialog-workOrder" style="width: 600px;display: none">
    </div>
</div>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.multiselect.min.js"></script>
<script src="resources/js/multiselect.js"></script>

<script src="resources/js/dialog-min.js"></script>
<script src="resources/js/util.js"></script>
<script type="application/javascript">
    var startNum = 1;
    var limit = 20;
    $(document).ready(function () {
        $("#datetimepickerbegin").datetimepicker({
            startView: 2,
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
        $("#datetimepickerend").datetimepicker({
            startView: 2,
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
    });
    $(function () {
        getSel();
        selArea("example");
        getPurchase();
    });
    function competence(idArr){
        var arr = new Array();
        if(idArr.length>0){
            for(var i=0;i<idArr.length;i++){
                var obj = {};
                obj["num"]="06203";
                obj["id"]= idArr[i];
                arr.push(obj);
            }
        }
        var obj1 = {"num":"06201","id":"search"};
        var obj2 = {"num":"06202","id":"export"};
        arr.push(obj1);
        arr.push(obj2);
        buttonJudge(arr);
    }

    function getPurchase() {
        $("#purchase").empty();
        var idcs = selVal();
        var begin = $("#begin").val();
        var end = $("#end").val();
        var status = $("#selStus").val();
        var type = 1;
        $.get('work_order/getList', {"type": type, "area": idcs, "beginTime": begin, "endTime": end, "stats": status}, function (data) {
            if (data.status == "success") {
                var idArr = new Array();
                $.each(data.data, function (i, a) {
                    idArr.push(("edit"+a.id));
                    $("#purchase").append("<tr><td>" + "00000"+a.id + "</td><td>" + a.createTime + "</td>" +
                            "<td>" + a.customerName + "</td><td>" + a.customerPhone + "</td>" +
                            "<td>" + a.address + "</td><td>" + a.remark + "</td>" +
                            "<td>" + a.itemDiscrible + "</td><td>" + a.editRemark + "</td>" +
                            "<td>" + a.areaName + "</td><td>" + a.userName + "</td><td>" +
                            "<a id='edit"+ a.id+"' href=javascript:edit('" + a.id + "','" + a.status + "','" + a.editRemark + "')>编辑</a>" +
                            "</td></tr>");
                });
                competence(idArr);
                getPagination(data.count, startNum, limit);
            }else{
                message("error","没有符合条件的数据！");
            }
        }, 'json');
    }

    function getEdit(staValue, remark) {
        $.get('work_order/statusDict', function (data) {
            $("#dialog-workOrder").empty();
            $("#dialog-workOrder").append(
                            "<div><label class='control-label'>状&nbsp;态:&nbsp;&nbsp;</label><select id='selStats'class='span3 m-wrap'></select></div>" +
                            "<div><label class='control-label'>备&nbsp;注:&nbsp;&nbsp;</label><textarea id='remark'>" + remark + "</textarea></div>"
            );
            $("#remark").val(remark);
            if (data.status == "success") {
                $.each(data.data, function (i, a) {
                    $("#selStats").append("<option value='" + a.itemValue + "'>" + a.itemDiscrible + "</option>");
                });
                $("#selStats").val(staValue);
            }
        }, 'json');
    }

    function edit(id, staValue, remark) {
        getEdit(staValue, remark);
        var confirmDialog = dialog({
            id: "confirmDialog",
            //width: 500,
            title: "编辑",
            content: $("#dialog-workOrder"),
            okValue: "提交",
            ok: function () {
                var status = $("#selStats").val();
                var remark = $("#remark").val();
                $.post('work_order/edit', {"id": id, "status": status, "editRemark": remark},
                        function (data) {
                            if (data.status == "success") {
                                message("success","修改成功！");
                                getPurchase();
                            }
                        }, 'json');
            },
            cancelValue: "取消",
            cancel: function () {
            }
        });
       confirmDialog.showModal();
    }
    function getSel() {
        $.get('work_order/statusDict', function (data) {
            $('#selStus').empty();
            $('#selStus').append("<option value=''>-请选择-</option>");
            if (data.status == "success") {
                $.each(data.data, function (i, a) {
                    $("#selStus").append("<option value='" + a.itemValue + "'>" + a.itemDiscrible + "</option>");

                });
            }
        }, 'json');
    }
    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getPurchase();
    }
</script>
