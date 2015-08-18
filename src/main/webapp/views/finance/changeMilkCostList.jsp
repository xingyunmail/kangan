<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <div class="muted pull-left">换奶费用单列表</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            变动时间：
                            <div class="input-append date" data-date-format="yyyy-mm-dd hi:ii" id="startDatetimepicker">
                                <input size="16" type="text" class="input-medium" id="startDate">
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                            至
                            <div class="input-append date" data-date-format="yyyy-mm-dd" id="endDatetimepicker">
                                <input size="16" type="text" class="input-medium" id="endDate">
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                        </div>
                        <div class="span3">
                            送奶员编号：<input type="text" class="input-medium" id="deliverId"/>
                        </div>
                        <div class="span3">
                            区域：
                            <select class="" multiple="multiple"  id="orderArea">
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span4">
                            订单类型：
                            <select class="" style="width:150px;" id="orderType">
                            </select>
                            <div id="orderTypeDiv" style="display:none;">
                                <input type="checkbox"  id="orderTypeCheck" value="">差额小于0
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span4">
                            <button class="btn order-btn" type="button" id="queryButton" onclick="queryChangeList()">查 询</button>
                        </div>
                    </div>
                </div>
                <div  id="changeListDiv">
                    <div class="" style="margin-bottom:5px;">
                        <button class="btn order-btn" type="button" id="exportInfo" onclick="exportInfo()">导出明细</button>
                    </div>
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <td>送奶员</td>
                                <td>变动订单数</td>
                                <%--<td>变动次数</td>--%>
                                <td>总差额</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody id="changeList" >
                        </tbody>
                    </table>
                </div>
                <div id="alert">

                </div>
                <div style="display: none;" id="changeInfoDiv">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <td>客户编号</td>
                            <td>客户名称</td>
                            <td>地址</td>
                            <td>变动日期</td>
                            <td>原商品</td>
                            <td>原品余量</td>
                            <td>新商品</td>
                            <td>数量</td>
                            <td>差额</td>
                            <td>合计差额</td>
                        </tr>
                        </thead>
                        <tbody id="changeInfo">
                        </tbody>
                    </table>
                    <button class="btn order-btn" type="button" style="text-align: center;"  onclick="closeChange()">返回</button>
                </div>
            </div>
         </form>
    </div>

</div>

<link href="resources/css/ui.min.css" rel="stylesheet" media="screen">
<link href="resources/css/jquery.multiSelect.css" rel="stylesheet" media="screen">
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="resources/js/menu.js"></script>

<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.multiselect.min.js"></script>
<script src="resources/js/multiselect.js"></script>
<script src="resources/js/util.js"></script>



<script type="application/javascript">
    var isLessZero;
    $(document).ready(function(){
        //权限判断
        competence();
        //初始化区域--多选下拉框
        selArea("orderArea");
        //初始化日期
        initDate();
        //获取订单类型
        getOrderType();

        //订单类型是报奶时，显示差额小于0的订单
        $("#orderType").change(function() {
            var checkText = $("#orderType").find("option:selected").text();
            if(checkText=='大众点评'){//要改成报奶
                $("#orderTypeDiv").attr("style","display:block;display:inline");
                $("#orderTypeCheck").prop("checked",true);
            }else{
                $("#orderTypeDiv").attr("style","display:inline;");
                $("#orderTypeDiv").attr("style","display:none;");
                $("#orderTypeCheck").prop("checked",false);
            }
        });
        //开始查询
//        queryChangeList();
    });

    function initDate(){
//        var dateTime = new Date();
//        $("#startDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) );
//        $("#endDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) );
        $("#startDatetimepicker").datetimepicker({
            minView: '0',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: false,
            language: 'zh-CN',
            todayBtn: true,
            todayHighlight: true
        });
        $("#endDatetimepicker").datetimepicker({
            minView: '0',
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            startView: 2,
            minView: 2,
            forceParse: false,
            language: 'zh-CN',
            todayBtn: true,
            todayHighlight: true
        });
    }
    //获取订单类型
    function getOrderType() {
        $.get("orderType/orderTypeInfo",
                function (data, status) {
                    if (status == "success") {
                        if (data.status == "success") {
                            $.each(data.data, function (index, item) {
                                $("#orderType").append("<option id='' value='" + item.id + "'>" + item.name + "</option>");
                            });
                        }
                    }
                    else {
                       message("error","请求异常");
                    }
                }, "json"
        );
    }

    function queryChangeList(){
        $("#changeInfoDiv").hide();
        $("#changeListDiv").show();
        var queryData = getQueryData();
        var orderTbody = $("#changeList");
        orderTbody.html("");
        $.post("finance/getChangeList", queryData,
                function (data, status) {
                    if (status == "success") {
                        if (data.status == "success") {
                            $.each(data.data, function (index, item) {
                                var row = "<tr>" +
                                        "<td> "+item.deliverName+"</td>" +
                                        "<td> "+item.exchangeOrderCount+" </td>" +
//                                        "<td> item.changeCount </td>" +
                                        "<td>"+item.diffPrice+"</td>" +
                                        "<td><a href='' data-toggle='modal' id='queryInfo' class='btn btn-info' onclick='exchangeInfo(\""+item.deliverId+"\")'>查看详细</a></td>" +
                                        "</tr>";
                                orderTbody.append(row);
                            });
                        }else {
                            $("#changeListDiv").hide();
                            message("info","无记录!");
                        }
                    }
                    else {
                        message("error","请求异常");
                    }
                }, "json");
    }

    function exchangeInfo(deliverId){
        $("#changeListDiv").hide();
        $("#changeInfoDiv").show();
        var isLessZero;
        if($("#orderTypeCheck").attr("checked")){//选中
            isLessZero=1;
        }else{
            isLessZero=0;
        }
        var startDate;
        if($("#startDate").val()==''){
            startDate = ''
        }else{
            startDate = $("#startDate").val()+" 00:00:00"
        }
        var endDate;
        if($("#endDate").val()==''){
            endDate = ''
        }else{
            endDate = $("#endDate").val()+" 23:59:59"
        }
        var queryData = {
            "startDate": startDate,
            "endDate": endDate,
            "orderArea": selVal(),
            "deliverId":deliverId,
            "orderType":$("#orderType").val(),
            "isLessZero":isLessZero
        }
        var orderTbody = $("#changeInfo");
        orderTbody.html("");
        $.ajax({
            url:"finance/getChangeInfo",   //?deliverId="+deliverId+"&isLessZero="+isLessZero,
            type:"post",
            data:queryData,
            success:function(data,status){
                if(status=="success"){
                    if(data.status=="success"){
                        $.each(data.data,function(index,singleData){
                            $.each(singleData.exchangeCostModels,function(i,ecm){

                                var oldProdName="";
                                var oldProdNum="";
                                $.each(ecm.oldProds,function(j,oldProd){
                                    oldProdName+=oldProd.prodName+";";
                                    oldProdNum+=oldProd.quantity+";";
                                });
                                var newProdName="";
                                var newProdNum="";
                                $.each(ecm.newProds,function(j,newProd){
                                    newProdName+=newProd.prodName+";";
                                    newProdNum+=newProd.quantity+";";
                                });
                                oldProdName = oldProdName.substr(0,oldProdName.length-1);
                                oldProdNum = oldProdNum.substr(0,oldProdNum.length-1);
                                newProdName = newProdName.substr(0,newProdName.length-1);
                                newProdNum = newProdNum.substr(0,newProdNum.length-1);
                                var row = "";
                                if(i==0){
                                    row = "<tr>"+
                                            "<td>"+ecm.custId+"</td>"+
                                            "<td>"+ecm.custName+"</td>"+
                                            "<td>"+ecm.custAddr+"</td>"+
                                            "<td>"+ecm.changeDate+"</td>"+
                                            "<td>"+oldProdName+"</td>"+
                                            "<td>"+oldProdNum+"</td>"+
                                            "<td>"+newProdName+"</td>"+
                                            "<td>"+newProdNum+"</td>"+
                                            "<td>"+ecm.diffPrice+"</td>"+
                                            "<td align='center' valign='middle' rowspan='"+singleData.exchangeCostModels.length+"'>"+singleData.diffCount+"</td>"+
                                            "</tr";
                                }else{
                                    row = "<tr>"+
                                            "<td>"+ecm.custId+"</td>"+
                                            "<td>"+ecm.custName+"</td>"+
                                            "<td>"+ecm.custAddr+"</td>"+
                                            "<td>"+ecm.changeDate+"</td>"+
                                            "<td>"+oldProdName+"</td>"+
                                            "<td>"+oldProdNum+"</td>"+
                                            "<td>"+newProdName+"</td>"+
                                            "<td>"+newProdNum+"</td>"+
                                            "<td>"+ecm.diffPrice+"</td>"+
                                            "</tr";
                                }
                                orderTbody.append(row);
                            });
                        });
                    }else{
                        $("#changeListDiv").hide();
                        message("info","无记录！");
                    }
                }
            },
            error: function(error){
                message("error","请求异常！");
            }
        });
    }


    //获取表单上数据
    function getQueryData() {
        var orderType;
        if($("#orderType").val()==''){
            orderType = 1;
        }else{
            orderType = $("#orderType").val();
        }
        if($("#orderTypeCheck").attr("checked")){//选中
            isLessZero=1;
        }else{
            isLessZero=0;
        }
        var startDate;
        if($("#startDate").val()==''){
            startDate = ''
        }else{
            startDate = $("#startDate").val()+" 00:00:00"
        }
        var endDate;
        if($("#endDate").val()==''){
            endDate = ''
        }else{
            endDate = $("#endDate").val()+" 23:59:59"
        }
        var queryData = {
            "startDate": startDate,
            "endDate": endDate,
            "orderArea": selVal(),
            "deliverId":$("#deliverId").val(),
            "orderType":orderType,
            "isLessZero":isLessZero
        }
        return queryData;
    }

    function closeChange(){
        $("#changeListDiv").show();
        $("#changeInfoDiv").hide();
    }

    function competence(){
        var arr = new Array();
        var obj1 = {"num":"02201","id":"queryButton"};
        var obj2 = {"num":"02202","id":"exportInfo"};
        var obj3 = {"num":"02203","id":"queryInfo"};
        arr.push(obj1);
        arr.push(obj2);
        arr.push(obj3);
        buttonJudge(arr);
    }
</script>