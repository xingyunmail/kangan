<%--
  Created by IntelliJ IDEA.
  User: lining
  Date: 15-5-12
  Time: 下午1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
<script src="resources/js/deliver/deliverOrder.js"></script>



<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">配送单</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span4">
                            送奶员编号：
                            <input type="text" class="input-medium" id="userName"/>
                        </div>
                        <div class="span4">
                            订单编号：
                            <input type="text" class="input-large" id="orderId" />
                        </div>
                        <div class="span4">
                            <label class="control-label" for="orderType">订单类型：</label>
                            <div class="controls">
                                <select class="" style="width:150px;" id="orderType">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span4">
                            含商品编号：<input type="text" class="input-medium" id="prodId"/>
                        </div>
                        <div class="span4" style="text-align: left;">
                            配送日期：
                            <div class="input-append date" data-date-format="yyyy-mm-dd" id="startDatetimepicker">
                                <input size="16" type="text" class="input-large" id="startDate">
                                <span class="add-on"><i class="icon-th"></i></span>
                            </div>
                            <%--至--%>
                            <%--<div class="input-append date" data-date-format="yyyy-mm-dd" id="endDatetimepicker">--%>
                                <%--<input size="16" type="text" class="input-small" id="endDate">--%>
                                <%--<span class="add-on"><i class="icon-th"></i></span>--%>
                            <%--</div>--%>

                        </div>
                        <div class="span4">
                            <label class="control-label" for="orderArea">区域：</label>
                            <div class="controls">
                                <select class="" multiple="multiple" style="width:100px;"  id="orderArea">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span12">
                            <button class="btn order-btn" type="button" id="queryButton" onclick="queryOrders()">查 询</button>
                            <span style="text-align: right;"><font color="red">*</font>订单编号、商品编号可输入多个，用逗号隔开，区域可多选！</span>
                        </div>
                    </div>
                </div>
                <div id="alert">

                </div>
                <div id="orderListDiv">
                    <div class="" style="margin-bottom:5px;" >
                        <button class="btn order-btn" type="button" id="exportDeliver" onclick="exportDeliverInfo()">导出配送</button>
                        <button class="btn order-btn" type="button" id="exportChange" onclick="exportChangeInfo()">导出变动</button>
                        <a href="#" id="changeInfo" onclick="changeInfo()">查看变动</a>
                        <div style="display: inline;">
                            <span  style="margin-left:20px;" >配送总量：</span>
                            <span  id="numberCount"></span>
                        </div>

                    </div>
                    <div id="orderList">
                        <table class="table table-striped table-bordered">
                            <thead id="thead">
                                <tr>
                                    <td>客户编号</td>
                                    <td>送奶员</td>
                                    <td>客户姓名</td>
                                    <td>联系电话</td>
                                    <td>地址</td>
                                    <td>商品名称</td>
                                    <td>数量</td>
                                </tr>
                            </thead>
                            <tbody id="deliverOrder">
                            </tbody>
                        </table>
                        <span class="span12" id="pageDiv">

                        </span>
                    </div>
                    <div id="changeList" style="display:none;">
                        <table class="table table-striped table-bordered">
                            <thead id="changeHead">
                                <tr>
                                    <td>区域</td>
                                    <td>送奶员</td>
                                    <td>客户姓名</td>
                                    <td>客户手机</td>
                                    <td>地址</td>
                                    <td>商品</td>
                                    <td>变动名称</td>
                                    <td>变动</td>
                                    <td>数量</td>
                                    <td>余量</td>
                                </tr>
                            </thead>
                            <tbody id="changeOrder">
                            </tbody>
                        </table>

                        <button class="btn order-btn" type="button" style="text-align: center;"  onclick="closeChange()">返回</button>
                    </div>

                </div>

            </div>

        </form>
    </div>

</div>