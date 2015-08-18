<%--
  Created by IntelliJ IDEA.
  User: lining
  Date: 15-5-12
  Time: 下午1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="resources/js/menu.js"></script>

<script src="resources/js/util.js"></script>
<script src="resources/js/deliver/receiveMilkOrder.js"></script>


<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">收奶单</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">

                            <label class="control-label" for="orderArea">区域</label>

                            <div class="controls">
                                <select class="span5" id="orderArea">
                                </select>
                            </div>
                        </div>
                        <div class="span6">

                            <label class="control-label" for="orderDate">配送日期</label>

                            <div class="controls">
                                <div class="input-append date" id="datetimepicker" data-date-format="yyyy-mm-dd">
                                    <input size="16" type="text" id="orderDate">
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="span10">
                            <div class="controls">
                                <button class="btn order-btn" type="button" id="queryButton" onclick="queryOrders()">查询
                                </button>
                            </div>
                        </div>

                    </div>

                    <%--<div class="control-group">--%>
                        <%--<div class="navbar navbar-inner block-header">--%>
                            <%--<div id="areaPage"  style="text-align:right;">--%>

                            <%--</div>--%>
                        <%--</div>--%>


                    <%--</div>--%>

                </div>
                <div id="alert">

                </div>
                <div id="milkOrderDiv" style="overflow: auto;">
                    <div class="" style="margin-bottom:5px;" >
                        <button class="btn order-btn" type="button" id="export" onclick="exportReceive()">导出</button>
                    </div>
                        <table class="table table-striped table-bordered" >
                            <thead id="thead" >

                            </thead>
                            <tbody id="milkOrders" >

                            </tbody>
                        </table>
                        <%--<span class="span12" id="pageDiv">--%>

                        <%--</span>--%>
                </div>
            </div>
        </form>
    </div>
</div>

