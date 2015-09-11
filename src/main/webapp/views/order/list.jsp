<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>


<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"  type="text/javascript"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"  type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/ui-dialog.css">
<script src="resources/js/dialog-min.js"  type="text/javascript"></script>
<script src="resources/js/util.js"  type="text/javascript"></script>
<script src="resources/js/order.js"  type="text/javascript"></script>
<script src="resources/js/bootstrap-typeahead.js"  type="text/javascript"></script>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">订单列表</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="row-fluid">
                <div class="span6">
                    订单时间：
                    <div class="input-append date datetimepicker" data-date-format="yyyy-mm-dd">
                        <input size="10" style="width:100px;" type="text" id="orderDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    <span> -- </span>

                    <div class="input-append date datetimepicker" data-date-format="yyyy-mm-dd">
                        <input size="10" style="width:100px;" type="text" id="orderDate_end">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                </div>
                <div class="span6" style="padding: 10px;" >
                    <input type="checkbox" id="hadStop"/> 含停送商品
                </div>
            </div>
            <div class="row-fluid">

                <div class="span3">
                    客户编号：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="custId"/>
                </div>
                <div class="span3">
                    客 户 姓 名：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="q_custName"/>
                </div>
                <div class="span3">
                    客 户 电 话：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="q_custPhone"/>
                </div>
                <div class="span3">
                    送奶员：
                    <input style="margin-bottom: 0px;" type="text" id="q_deliverId" data-provide="typeahead"/>
                </div>

            </div>
            <div class="row-fluid" style="margin-top:10px;">
                <div class="span3">
                    订单编号：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="orderId"/>
                </div>
                <div class="span3">
                    含商品编号：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="products"/>
                </div>

                <div class="span3">
                    地址关键字：
                    <input class="input-medium " style="margin-bottom: 0px;" type="text" id="address"/>
                </div>
                <div class="span3">
                    <button class="btn order-btn btn-primary" type="submit" onclick="queryOrders()">查 询</button>
                </div>
            </div>
        </div>
        <div class="row span12">
            <button class="btn order-btn" type="button" id="stopMilk" onclick="stopMilk()">停</button>
            <button class="btn order-btn" type="button" id="activeMilk" onclick="activeMilk()">启</button>
            <button class="btn order-btn" type="button" onclick ="returnMilk()">退</button>
        </div>
        <span>&nbsp</span>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td><input type="checkbox" onclick="selectAll(this,'chkItem')"></td>
                <td>订单编号</td>
                <td>客户编号</td>
                <td>姓名</td>
                <td>手机号</td>
                <td>地址</td>
                <td>商品名</td>
                <td>类型</td>
                <td>日配</td>
                <td>频率</td>
                <td>开始时间</td>
                <td>结束时间</td>
                <td>送奶员</td>
                <td>金额(元)</td>
            </tr>
            </thead>
            <tbody id="orderList">
            <!--generate-->
            </tbody>
        </table>
        <span class="span12" id="pageDiv">

        </span>
    </div>

</div>

<!--停奶、启奶产品弹出层-->
<div id="stopMilkDiv"  style="width:680px;display:none" >
    <div class="modal-body">
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <td><input type="checkbox" onclick="selectAll(this,'stopProds')"></td>
            <td>类型</td>
            <td>名称</td>
            <td>日配数量</td>
            <td>配送天数</td>
            <td>配送开始日期</td>
            <td>配送结束日期</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody id="stopMilkProds">

        </tbody>
    </table>
    </div>
    <div >
        <span>
            <input type="button" class="btn" style="margin-left: 20px" id="stopORactive"></button>
        </span>
        <span style="margin-left: 400px">
            <button class="btn btn-primary" style="margin: 15px" id="sacommit" value="" onclick="saMilkCommit(this)">确定</button>
            <button class="btn" style="margin: 15px" onclick="closeDivs('prodsDialog')">取消</button>
        </span>
    </div>
</div>

<!--停奶时间弹出层-->
<div id="stopTimeDiv"  style="display: none">
    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <%--<tr>--%>
                <%--<td>配送周期</td>--%>
                <%--<td>--%>
                    <%--<select class="deliveryCycle" id="s_selecter">--%>
                        <%--<option value="0">--请选择配送周期--</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>停奶日期</td>
                <td>
                    <div class="input-append date datetimepicker" id="stopDate" data-date-format="yyyy-mm-dd">
                        <input size="16" type="text" id="s_stopDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td>配送开始日期</td>
                <td><div class="input-append date datetimepicker" id="beginDate" data-date-format="yyyy-mm-dd">
                    <input size="16" type="text" id="s_beginDate">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div></td>
            </tr>
            <tr>
                <td>配送结束日期</td>
                <td >
                    <input id="s_enddate" readonly="readonly" value="">
                </td>
            </tr>

        </table>
    </div>
    <div style="text-align: center">
        <button class="btn btn-primary" style="margin-right: 15px;" id="s_date_submit" onclick="saveStopDate(this)" value="">确定</button>
        <button class="btn" style="margin-left: 15px;" onclick="closeDivs('stopTimeDialog')">取消</button>
    </div>
</div>

<!--启奶时间弹出层-->
<div id="activeTimeDiv"  style="display: none">
    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <%--<tr>--%>
                <%--<td>配送周期</td>--%>
                <%--<td>--%>
                    <%--<select class="deliveryCycle" id="a_selecter">--%>
                        <%--<option value="0">--请选择配送周期--</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>配送开始日期</td>
                <td><div class="input-append date datetimepicker"  data-date-format="yyyy-mm-dd">
                    <input size="16" type="text" id="a_beginDate">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div></td>
            </tr>
            <tr>
                <td>配送结束日期</td>
                <td >
                    <input id="a_enddate" readonly="readonly" value="">
                </td>
            </tr>

        </table>
    </div>
    <div style="text-align: center">
        <button class="btn btn-primary" style="margin-right: 15px;" id="a_date_submit" onclick="saveActiveDate(this)" value="">确定</button>
        <button calss="btn" style="margin-left: 15px;" onclick="closeDivs('activeTimeDialog')">取消</button>
    </div>
</div>

<!--转奶单选弹出层-->
<div id="updateCustome_one"  style="display: none;">
    <div class="modal-body">
        <div style="margin-bottom: 10px;" id="trans_error_info"></div>
    <table>
        <tr>
            <td style="text-align: right;">编号：</td>
            <td colspan="3"><input type="text" id="transCustId" onblur="showCustomerInfo()"></td>
        </tr>
        <tr>
            <td style="text-align: right;">姓名：</td>
            <td colspan="3"><div><input  type="text" id="custName"><span style="color: red;"> * </span></div></td>
        </tr>
        <tr>
            <td style="text-align: right;">手机号：</td>
            <td colspan="3"><div><input  type="text" id="custPhone"><span style="color: red"> * </span></div></td>
        </tr>
        <tr>
            <td style="text-align: right;">地址：</td>
            <td colspan="3"><div><input  type="text" id="custAddr"><span style="color: red"> * </span></div></td>
        </tr>
        <tr>
            <td style="text-align: right;">启用时间：</td>
            <td colspan="3">
                <div class="input-append date datetimepicker"  data-date-format="yyyy-mm-dd">
                    <input  size="16" type="text" id="affectDate">
                    <span class="add-on"><i class="icon-th"></i><span style="color: red"> * </span></span>
                </div>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;">送奶员编号：</td>
            <td colspan="3"><div><input id="deliverId" type="text"><span style="color: red"> * </span></div></td>
        </tr>
    </table>
        <div style="margin-top: 30px;"><button class="btn" style="margin-left: 100px;" onclick="closeDivs('transferDialog')">取消</button><button class="btn btn-primary" style="margin-left: 100px;" onclick="saveTransferInfo()">确定</button></div>
    </div>
</div>

<!--转奶多选弹出层-->
<div id="updateCustome_all"  style="display: none">
    <div class="modal-body">
        <table>
            <tr>
                <td style="text-align: right;">启用时间：</td>
                <td colspan="2"><div class="input-append date datetimepicker"  data-date-format="yyyy-mm-dd">
                    <input size="16" type="text" id="affectDate_2">
                    <span class="add-on"><i class="icon-th"></i><span style="color: red"> * </span></span>
                </div></td>
            </tr>
            <tr>
                <td style="text-align: right;">送奶员编号：</td>
                <td style="width: 100px;"><input type="text" style="width: 100px;" onblur="showDelivery(this,'deliveryName_all')"></td>
                <td><input id="deliveryName_all" type="text" style="width: 100px; " disabled="disabled"  ><span style="color: red"> * </span></td>

            </tr>
        </table>
    </div>
    <div style="text-align: center">
        <button class="btn btn-primary" style="margin-right: 15px;"  onclick=" " value="">确定</button>
        <button calss="btn" style="margin-left: 15px;" onclick="closeDivs('transferDialog')">取消</button>
    </div>
</div>

<!--换奶弹出层-->
<div id="exchangeMilkDiv" style="display: none;width: 770px;" >
    <div class="modal-body">
        <div>原始商品：</div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>选择</td>
                <td>编号</td>
                <td>名称</td>
                <td>日配量</td>
                <td>余量</td>
                <td>单价</td>
                <td>配送周期</td>
                <td>配送开始日期</td>
                <td>配送结束日期</td>
                <td style="width:60px;">操作</td>
            </tr>
            </thead>
            <tbody id="exchangeMilkProdsDiv">

            </tbody>
        </table>
        <div style="margin-top: 10px;margin-bottom: 10px;"><input type="hidden" id="exchangeOrderId" value=""><button class="btn btn-primary" onclick="showChangeProds()">更换</button> <span style="margin-left: 50px;color: red;">更换差价为：<span id="exchangeDiffPrice">0</span> 元</span></div>
        <div>更换后商品：</div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>编号</td>
                <td>名称</td>
                <td>日配量</td>
                <td>余量</td>
                <td>配送周期</td>
                <td>配送开始日期</td>
                <td>配送结束日期</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="selectedProdsBody">

            </tbody>
        </table>
    </div>
</div>

<!--选择更换商品-->
<div id="exchangeProds" style="display: none;">
    <div class="modal-body">
        <div id="exchangeBtn" style="margin-bottom: 10px;">
        </div>
        <div>
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <td>选择</td>
                    <td>商品编号</td>
                    <td>商品名称</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody id="exchangeProdBody">
                </tbody>
            </table>
        </div>
    </div>

</div>


<!--退奶显示层-->
<div id="returnMilkDiv" style="display: none">
    <div class="modal-body">
        <div>
          <table class="table table-bordered table-striped" id="replaceProdTab">
              <thead>
              <tr>
                  <td><input type="checkbox" onclick="returnSelectAll(this,'rebox')"><span style="margin-left: 5px;">序号</span></td>
                  <td>名称</td>
                  <td>类型</td>
                  <td>已配送</td>
                  <td>余量</td>
                  <td>单价</td>
                  <td>退款金额</td>
              </tr>
              </thead>
            <tbody id="returnProdBody">

            </tbody>
        </table>
        </div>
        <div>
            <span>合计退款 </span><span><input  type="text" id ="totalPrice" disabled="disabled" value="0" style="width:100px;margin: 15px;">元</span>
        </div>
        <div>
            <span>退奶原因：
                <select id="returnSelect">
                    <option>--请选择--</option>
                    <option>不想喝了</option>
                    <option>误订购</option>
                </select>
            </span>
        </div>
    </div>
</div>


<!--更换赠品弹出层-->
<div id="exchangeGiftDiv" style="display: none">
    <div class="modal-body">
        <div><input type="hidden" id="orderId4Gift" value="">
            原始赠品：</div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>选择</td>
                <td>编号</td>
                <td>名称</td>
                <td>日配量</td>
                <td>余量</td>
                <td>配送周期</td>
                <td>配送开始日期</td>
                <td>配送结束日期</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="beforeChangeGiftBody">

            </tbody>
        </table>

        <div style="margin-top: 10px;margin-bottom: 10px;"><input type="hidden" id="exchangeOrderId_gift" value=""><button class="btn btn-primary" onclick="showChangeGift()">更换</button></div>

        <div>更换后赠品：</div>
        <table class="table table-bordered table-striped" id="replaceGiftTab">
            <thead>
            <tr>
                <td>编号</td>
                <td>名称</td>
                <td>日配量</td>
                <td>余量</td>
                <td>配送周期</td>
                <td>配送开始日期</td>
                <td>配送结束日期</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="afterChangeGiftBody">

            </tbody>
        </table>
    </div>
</div>

<!--选择更换后赠品-->
<div id="selectExchangeGift" style="display: none">
    <div class="modal-body">
        <div style="margin: 10px;" id="giftButtonDiv">
        </div>
        <div>
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <td>选择</td>
                    <td>商品编号</td>
                    <td>商品名称</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody id="giftProdList">

                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="diliverRules" style="display:none">
    <select class = "deliveryCycle"   id="selectModel" style="width:100px;margin-bottom: 0px;">

    </select>
</div>


<script type="application/javascript">

    $(document).ready(function(){

        //送奶员提示框数据
        $.post("user/getAllDeliver", function (data) {
            $('#q_deliverId').typeahead({
                source:data.data,
                display:'userName'
            });
            $('#deliverId').typeahead({
                source:data.data,
                display:'userName'
            });
        }, "json");

        //格式化JS中日期的方法加载实例：new Date().Format("yyyy-MM-dd")
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }

        $(".datetimepicker").datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN',
            beforeShow: function (input, inst) {
                $.datepicker._pos = $.datepicker._findPos(input);
                $.datepicker._pos[1] += input.offsetHeight + document.body.scrollTop; // 加入body的scrollTop修正jquery在ie7中定位不準的問題
                inst.dpDiv.css('font-size' ,'70%');
            }
        });

        //获取配送类型
        $.get("order/deliDict", function (data) {
                if (data.status == "success") {
                    if (data.status == "success") {
                        $.each(data.data, function (index, item)
                        {
                            $(".deliveryCycle").append("<option value='" + item.itemValue + "'>" + item.itemDiscrible + "</option>");
                        });
                    }
                }
                else {
                    alert("请求异常！");
                }
            }, "json");

    });


    function getDeliverRuleSelect(selectid,ruleValue,disabled)
    {
        var origilHtml = $("#diliverRules").html();

        $("#selectModel").prop("id",selectid);

        $("#"+selectid+" option").attr("selected", false);
        $("#"+selectid+" option[value="+ruleValue+"]").attr("selected", true);

        $("#"+selectid).prop("disabled",disabled);

        var selectHtml = $("#diliverRules").html();

        $("#diliverRules").html("");
        $("#diliverRules").append(origilHtml);

        return selectHtml;
    }

</script>