<%--
  Created by IntelliJ IDEA.
  User: scar
  Date: 16/4/29
  Time: 下午2:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">添加订单</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">

            <div class="well well-small form-horizontal span12 ">
                <div class="span6">
                    <div class="control-group ">
                        <label class="control-label" for="inputOrderid">订单编号</label>
                        <div class="controls">
                            <input type="text" class="input-medium" id="inputOrderid"><span
                                class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <div class="input-append date" id="datetimepicker1" data-date-format="yyyy-mm-dd hh:ii">
                                <input size="16" type="text" id="inputOrderDate" class="input-medium">
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="selectOrderType">订单类型</label>

                        <div class="controls">
                            <select class="input-medium" id="selectOrderType">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="span6">
                    <div class="control-group">
                        <label class="control-label" for="inputCustid">客户编号</label>

                        <div class="controls">
                            <input type="text" class="input-medium" id="inputCustid"><span
                                class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="inputCustname">姓名</label>

                        <div class="controls">
                            <input type="text" class="input-medium" id="inputCustname"><span
                                class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputPhone">手机号</label>

                        <div class="controls">
                            <input type="text" class="input-medium" id="inputPhone"><span
                                class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="inputAddress">收货地址</label>

                        <div class="controls">
                            <input type="text" class="input-large" id="inputAddress"><span
                                class="help-inline"></span>
                        </div>
                    </div>
                </div>
            </div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <td>编号</td>
                <td>名称</td>
                <td>价格</td>
                <td>日配数量</td>
                <td>配送天数</td>
                <td>配送周期</td>
                <td>配送开始日期</td>
                <td>操作</td>
            </tr>
            <tr>
                <td><input class='input-small' type='text' id='productId'></td>
                <td id='productName'></td>
                <td id='productPrice'></td>
                <td><input class='input-small quantity' type='text' id='delivery_counts'/>
                </td>
                <td><input class='input-small deliverDays' type='text' id='delivery_days'/>
                </td>
                <td>
                    <select class='deliverRules' id='deliveryRule'>

                    </select>
                </td>
                <td>
                    <div class="input-append date" id="datetimepicker2" data-date-format="yyyy-mm-dd">
                        <input size="16" type="text" id="start_day">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                <td>
                    <a class="btn btn-mini" href="JavaScript:addOneDetial()">添加</a>
                </td>
            </tr>
            </thead>
            <tbody id="tb_detial"></tbody>
        </table>
        <div class="well well-small">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="discountId">选择活动</label>

                    <div class="controls">
                        <select id="discountId" onchange="chooseDiscount();"></select> <span
                            id="dicount_info"></span>
                    </div>
                </div>
            </form>
        </div>
        <div id="div_discount">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <td>编号</td>
                    <td>名称</td>
                    <td>价格</td>
                    <td>赠送数量</td>
                    <td>日配数量</td>
                    <td>配送天数</td>
                    <td>配送周期</td>
                    <td>配送开始日期</td>
                </tr>
                </thead>
                <tbody id="tb_discount">

                </tbody>
            </table>
        </div>
        <div class="well well-small">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="inputUserid">送奶员编号</label>

                    <div class="controls">
                        <input type="text" class="input-medium" id="inputUserid"><span id="deliverName"
                                                                                       class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputAddUserid">开户员</label>

                    <div class="controls">
                        <input type="text" class="input-medium" id="inputAddUserid"><span class="help-inline"
                                                                                          id="addUserName"></span>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span>总价:</span> <span id="spanTotalPrice">0</span><span>元</span>
                    </div>
                </div>
            </form>
        </div>
        <div class="span12">
            <div class="span5"></div>
            <div class="span1">
                <a type="button" class="btn btn-primary" href="javascript:void(0);" id="submit_button"
                   onclick="submitData();">提交</a>
            </div>
            <div class="span1">
                <button type="button" class="btn" onclick="loadPage('order/toAdd')">清空</button>
            </div>
            <div class="span5"></div>
        </div>
    </div>
</div>
<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="resources/js/util.js"></script>
<script src="resources/js/order/add.js"></script>
<script type="application/javascript">
    //初始化
    $(document).ready(function () {
        //初始化配送类型
        getDeliveryRule("deliveryRule");
        //初始化时间控件
        getDateTimePicker();
        //初始化订单时间
        getOrderTime();
        //初始化配送时间
        getDevTime();
        //初始化订单类型
        getOrderType();
        //初始化活动列表
        getDisCount();
//        $("#inputOrderid").focus();
    });


</script>
<%--跳转管理--%>
<script type="application/javascript">

    //订单Id
    $("#inputOrderid").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            checkOrderId();
        }
    });
    $("#inputOrderid").blur(function () {
        checkOrderId();
    });

    $("#selectOrderType").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#inputCustid").focus();
        }
    });


    //绑定查询用户事件
    $("#inputCustid").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            checkCustId();
        }
    })
    $("#inputCustid").blur(function () {
        checkCustId();
    });


    //用户信息
    $("#inputCustname").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#inputPhone").focus();
        }
    });
    //手机号码校验
    $("#inputPhone").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            checkPhone();
        }
    });
    $("#inputPhone").blur(function () {
        checkPhone();
    });

    $("#inputAddress").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#productId").focus();
        }
    });

    $("#productId").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            var prodId = $(this).val();
            var custId = $("#inputCustid").val();
            var orderType = $("#selectOrderType").val();
            if (prodId.length == 0) { //没有输入产品id
                if ($("#tb_detial").html().length == 0) {
                    message('error', '至少输入一种商品！');
                } else {
                    $("#inputUserid").focus();
                }
            } else {
                queryProd(custId, prodId, orderType);
            }
        }
    });

    $("#delivery_counts").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#delivery_days").focus();
        }
    });

    $("#delivery_days").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#deliveryRule").focus();
        }
    });
    $("#deliveryRule").bind("keypress", function (event) {
        var curKey = event.which;
        if (event.keyCode == "13" || curKey == 13) {
            getDevTime();
            $("#start_day").focus();
        }
    });
    $("#deliveryRule").bind("onkeydown", function (event) {
        var curKey = event.which;
        if (event.keyCode == "13" || curKey == 13) {
            getDevTime();
            $("#start_day").focus();
        }
    });

    $("#start_day").bind("keypress", function (event) {
        var curKey = event.which;
        if (event.keyCode == "13" || curKey == 13) {
            addOneDetial();
        }
    })
    $("#start_day").bind("onkeydown", function (event) {
        var curKey = event.which;
        if (event.keyCode == "13" || curKey == 13) {
            addOneDetial();
        }
    });

    $("#inputUserid").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            var userid = $("#inputUserid").val();
            getUserInfo(userid, "deliverName");
        }
    });
    $("#inputAddUserid").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            var userid = $("#inputAddUserid").val();
            getUserInfo(userid, "addUserName");
        }
    });

</script>


