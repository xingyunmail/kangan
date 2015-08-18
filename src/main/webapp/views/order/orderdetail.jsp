<%--
  Created by IntelliJ IDEA.
  User: sunhao
  Date: 15/5/11
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<% request.setCharacterEncoding("utf-8");%>
<html>
<head>
    <title>康安运营平台</title>
    <!-- Bootstrap -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../resources/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="../resources/css/styles.css" rel="stylesheet" media="screen">
</head>
<body class="no-padding">
${prodList}
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12" id="content">
            <div class="block">
                <div class="navbar navbar-inner block-header">
                    <div class="muted pull-left">订单详情</div>
                </div>
                <div class="block-content collapse in">
                    <table class="table table-striped table-bordered">
                        <tbody>
                        <tr>
                            <td style="text-align: center;width: 200px;">订单编号</td>
                            <td><span>${oderModel.orderId}</span>
                                <span style="margin-left: 50px;">${oderModel.orderDate}</span>
                                <span style="margin-left: 50px;"><a
                                        href="javascript:toOriginalOrder(${oderModel.originalOrder})">原始订单</a></span>
                            </td>

                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">订单类型</td>
                            <td><select disabled="disabled">
                                <option>${oderModel.orderTypeName}</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">客户编号</td>
                            <td>${oderModel.custId}</td>
                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">姓名</td>
                            <td>${oderModel.custName}</td>
                        </tr>
                        <tr>

                            <td style="text-align: center;width: 200px;">手机号</td>
                            <td>${oderModel.custPhone}</td>

                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">收货地址</td>
                            <td>${oderModel.custAddr}</td>

                        </tr>
                        </tbody>

                    </table>

                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <td style="text-align: center">编号</td>
                            <td style="text-align: center">类型</td>
                            <td style="text-align: center">名称</td>
                            <td style="text-align: center">价格</td>
                            <td style="text-align: center">日配数量</td>
                            <td style="text-align: center">配送天数</td>
                            <td style="text-align: center">配送周期</td>
                            <td style="text-align: center">配送开始日期</td>
                            <td style="text-align: center">配送结束日期</td>
                            <td style="text-align: center">已配送</td>
                            <td style="text-align: center">余量</td>
                        </tr>
                        </thead>
                        <tbody id="prodListTable">


                        </tbody>
                    </table>

                    <table class="table table-striped table-bordered">
                        <tbody>
                        <tr>
                            <td style="text-align: center;width: 200px;">开户员</td>
                            <td>${oderModel.addUserName}</td>
                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">区域经理</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td style="text-align: center;width: 200px;">送奶员</td>
                            <td>${oderModel.userName} ${oderModel.deliverId}</td>
                        </tr>

                        </tbody>
                    </table>

                    <table class="table table-striped table-bordered">
                        <tbody>
                        <tr>
                            <td style="text-align: center;width: 200px;">总价</td>
                            <td>${oderModel.orderPrice}元</td>
                        </tr>
                        </tbody>
                    </table>
                    <div align="center">
                        <button class="btn" onclick="goBack()">关闭</button>
                    </div>
                </div>
            </div>


            <div id="diliverRules" style="display:none">
                <select class="deliveryCycle" id="selectModel_detail" style="width:100px;margin-bottom: 0px;">

                </select>
            </div>

        </div>
        <div class="loader" id="loading" hidden="hidden">加载中...</div>
    </div>
    <hr>
    <footer>
        <p style="text-align: center">Copyright&copy; 康安 2015 All rights reserved</p>
    </footer>
    <input type="hidden" id="button_list" value="">
</div>

<div id="cover" class="cover">
    <img src='../resources/img/loading.gif' style="position: absolute; left:50%; Top:50%;">
</div>
<!--/.fluid-container-->
<script src="../resources/js/jquery-1.9.1.min.js"></script>
<script src="../resources/js/bootstrap.min.js"></script>
</body>

</html>

<script type="application/javascript">

    $(document).ready(function () {
        //获取配送类型,同步请求，在所有请求之情执行完成之后其他东西才生成
        $.ajax({
            url: "../order/deliDict",
            type: "GET",
            success: function (data) {
                if (data.status == "success") {
                    if (data.status == "success") {
                        $.each(data.data, function (index, item) {
                            $(".deliveryCycle").append("<option value='" + item.itemValue + "'>" + item.itemDiscrible + "</option>");
                        });
                    }
                }
                else {
                    alert("请求异常！");
                }
            },
            dataType: "json",
            async: false
        });

        //填充显示列表数据
        $.each(prodList, function (index, item) {

            var deliverString = "";
            if (item.prodType == "2") {
                if (item.isGiven == "1") {
                    deliverString = "<select value='" + item.isGiven + "' disabled='true' style='width:100px;margin-bottom:0px;'><option  value ='1'>已赠</option></select>";
                }
                else {
                    deliverString = "<select value='" + item.isGiven + "' disabled='true' style='width:100px;margin-bottom:0px;'><option value ='0'>未赠</option></select>";
                }
            }
            else {
                deliverString = getDeliverRuleSelect("detail_rule_" + item.detailId, item.deliverRules, true);
            }

            $("#prodListTable").append("<tr><td>" + item.prodiId +
            "</td><td>" + item.prodTypeName +
            "</td><td>" + item.prodName +
            "</td><td>" + item.prodPrice +
            "</td><td>" + item.quantity +
            "</td><td>" + item.deliverDays +
            "</td><td>" + deliverString +
            "</td><td>" + item.beginDate +
            "</td><td>" + item.endDate +
            "</td><td>" + (Number(item.deliverDays * item.quantity) - Number(item.leftNum)) +
            "</td><td>" + item.leftNum +
            "</td></tr>")
        });

    });


    function getDeliverRuleSelect(selectid, ruleValue, disabled) {
        var origilHtml = $("#diliverRules").html();

        $("#selectModel_detail").prop("id", selectid);

        $("#" + selectid + " option").attr("selected", false);
        $("#" + selectid + " option[value='" + ruleValue + "']").attr("selected", true);

        $("#" + selectid).prop("disabled", disabled);

        var selectHtml = $("#diliverRules").html();

        $("#diliverRules").html("");
        $("#diliverRules").append(origilHtml);

        return selectHtml;
    }

    function toOriginalOrder(orderId) {
        if ("0" != orderId) {
            $("#content").load("order/toDetail", {"orderId": orderId});
        }
        else {
            alert("此订单无原始订单");
        }
    }


    function goBack() {
        window.close();
    }

</script>
