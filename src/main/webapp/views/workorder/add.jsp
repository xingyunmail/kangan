<%--
  Created by IntelliJ IDEA.
  User: zzq
  Date: 15-5-7
  Time: 上午10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link href="resources/css/ui-dialog.css" rel="stylesheet" media="screen">

<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">新建工单</div>
    </div>
    <div id="alert"></div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div id="messageBox"></div>
                    <div class="control-group">
                        <div class="span10">
                            <label class="control-label" for="selType">工单类型</label>

                            <div class="controls">
                                <select class="span3 m-wrap" id="selType" onchange="changeType(this.value)">
                                    <option value="1">订购</option>
                                    <option value="2">投诉</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div id="purchase">
                        <div class="control-group">
                            <div class="span4">
                                <label class="control-label" for="txtCustomerName">客户姓名</label>

                                <div class="controls">
                                    <input type="text" class="input-medium" id="txtCustomerName">
                                </div>
                            </div>
                            <div class='help-block'><br/></div>
                        </div>
                        <div class="control-group">
                            <div class="span4">

                                <label class="control-label" for="txtPhone">客户手机</label>

                                <div class="controls">
                                    <input type="text" class="input-medium" id="txtPhone">
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="span4">

                                <label class="control-label" for="txtAddr">地址</label>

                                <div class="controls">
                                    <input type="text" class="input-medium" id="txtAddr">
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="span6">
                                <label class="control-label" for="txtRemark">备注</label>

                                <div class="controls">
                                    <textarea class="uneditable-textarea" id="txtRemark"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="span6">
                                <label class="control-label" for="txtMilkUser">送奶员</label>

                                <div class="controls">
                                    <input type="text" class="input-medium" id="txtMilkUser">
                                </div>
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="span6">
                                <label class="control-label" for="selArea">区域</label>

                                <div class="controls" id="divSelArea">
                                    <select class="span3 m-wrap" id="selArea"></select>
                                </div>
                                <div class="controls hidden" id="divArea">
                                    <input type="text" class="input-medium" readonly id="txtArea">
                                    <input type="text" class="hidden" readonly id="txtAreaId">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hidden" id="complaints">
                        <div class="control-group ">
                            <div class="span6">
                                <label class="control-label" for="selComplaints">投诉原因</label>

                                <div class="controls">
                                    <select class="span3 m-wrap" id="selComplaints" onchange="selCompType(this.value)">

                                    </select>
                                    <select class="span3 m-wrap" id="selCon">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <div class="span12">
                                <label class="control-label" for="txtOrder">对应订单</label>

                                <div class="controls" id="order">
                                    <input type="text" style="float: left" class="input-medium" id="txtOrder">

                                    <div class="hidden" style="float: left" id="prod">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="control-group ">
                            <div class="span6">
                                <label class="control-label" for="txtContent">投诉内容</label>

                                <div class="controls">
                                    <textarea class="uneditable-textarea" id="txtContent"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="span12">
                    <div class="span5"></div>
                    <div class="span1">
                        <button type="button" class="btn btn-primary" id="submit">提交</button>
                    </div>
                    <div class="span1">
                        <button type="reset" class="btn">清空</button>
                    </div>
                    <div class="span5"></div>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="resources/js/dialog-min.js"></script>
<script type="application/javascript">

    $(function () {
        var pro = "";
        var cumName = "";
        var cumphone = "";
        var addr = "";
        var areaNum = "";
        var userId = "";
        selArea();
        selComp();
    });
    function getAreaByUserId(value) {
        if (value != "") {
            $.get('user/info', {"userId": value}, function (data) {
                if (data.status == "success") {
                    $("#divArea").removeClass("hidden");
                    $("#divSelArea").addClass("hidden");
                    $("#txtArea").val(data.data.areaName);
                    $("#txtAreaId").val(data.data.areaId);
                } else {
                    message("error","没有查到该编号信息！");
                    $("#divSelArea").removeClass("hidden");
                    $("#divArea").addClass("hidden");
                }
            }, 'json');
        } else {
            $("#divArea").addClass("hidden");
            $("#divSelArea").removeClass("hidden");
        }

    }
    function selArea() {
        $.get('area/getAreaList', function (data) {
            if (data.status == "success") {
                $.each(data.data, function (i, result) {
                    $("#selArea").append("<option value='" + result.areaId + "'>" + result.areaName + "</option>")
                });
            }
        }, 'json');
    }
    function selComp() {
        $.get('complaints/getCompType', function (data) {
            if (data.status == "success") {
                $.each(data.data, function (i, result) {
                    $("#selComplaints").append("<option value='" + result.id + "'>" + result.name + "</option>");
                })
            }
        }, 'json');
    }
    function selCompType(value) {
        $("#selCon").empty();
        $.get('complaints/getCompType/' + value, function (data) {
            if (data.status == "success") {
                $.each(data.data, function (i, result) {

                    $("#selCon").append("<option value='" + result.id + "'>" + result.compName + "</option>");
                })
            }
        }, 'json');
    }

    function changeType(value) {
        if (value == "2") {
            $("#complaints").removeClass("hidden");
            $("#purchase").addClass("hidden");
        } else {
            $("#purchase").removeClass("hidden");
            $("#complaints").addClass("hidden");
        }
    }
    //根据订单号得到商品
    function getProducts(value) {
        if (value != "") {
            $("#prod").removeClass("hidden")
            $("#prod").empty();
            $.get('order/getOrders', {"orderId": value}, function (data) {
                if (data.status == "success") {
                    pro = data.data[0].products;
                    var pros = (data.data[0].products).split(",");
                    cumName = data.data[0].custName;
                    cumPhone = data.data[0].custPhone;
                    addr = data.data[0].custAddr;
                    var areaId = data.data[0].deliverId;
                    areaNum = areaId.substr(0, areaId.length - 2);
                    userId = areaId;
                    for (var i = 0; i < pros.length; i++) {
                        $("#prod").append("<label class='checkbox-inline'><input class='uniform_on' type='checkbox' name='pros' value='" + pros[i] + "'>" + pros[i] + "</input></label>");
                    }
                }else{
                    message("error","未查到该订单商品！");
                }
            }, 'json');
        } else {
            $("#prod").addClass("hidden");
        }
    }

    //提交
    $("#submit").click(function () {
        var ps = ""
        if($("#txtOrder").val()!=""){
            $('input[name="pros"]:checked').each(function () {
                ps += $(this).val()+",";
            });
        }
        var areaId = $("#txtAreaId").val();
        var arr = {};
        arr["workNum"] = "f123";
        arr["type"] = $("#selType").val();
        if ($("#selType").val() == "1") {
            arr["customerName"] = $("#txtCustomerName").val();
            arr["customerPhone"] = $("#txtPhone").val();
            arr["address"] = $("#txtAddr").val();
            if (areaId == "") {
                arr["areaName"] = $("#selArea").val();
            } else {
                arr["areaName"] = areaId;
                $("#txtAreaId").val("");
            }
            arr["milkUser"] = $("#txtMilkUser").val();
            arr["remark"] = $("#txtRemark").val();
        } else {
            if($("#selComplaints").find("option:selected").text()=="质量"){
                if(ps.length==0){
                    message("error","请勾选商品！");
                    return;
                }
            }
            arr["customerName"] = cumName;
            arr["customerPhone"] = cumPhone;
            arr["address"] = addr;
            arr["areaName"] = areaNum;
            arr["milkUser"] = userId;
            arr["compID"] = $("#selCon").val();
            arr["products"] = ps.substr(0,ps.length-1);
            arr["remark"] = $("#txtContent").val();
        }
        $.post('work_order/addWork', arr, function (data) {
            if (data.status == "success") {
                message("success","添加成功！");
            }
        }, 'json');
    });
    function check(id,msg){
        var value = $("#" + id).val();
        if (value == "" || value == null) {
            $("#" + id).parent().parent().parent().removeClass().addClass("control-group error");
            $("#" + id).parent().parent().next().html(msg);
            return false;
        } else {
            //$("#" + id).parent().parent().removeClass().addClass("form-group has-success");
            $("#" + id).parent().parent().removeClass().addClass("control-group");
            $("#" + id).parent().next().html("");
            return true;
        }
    }
    $("#txtCustomerName").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if($("#selType").val()==1){
                if($(this).val().length==0){
                    message("error","客户姓名不能为空");
                }else{
                    $("#txtPhone").focus();
                }
            }
        }
    });
    $("#txtPhone").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if($("#selType").val()==1) {
                if ($(this).val().length == 0) {
                    message("error", "客户手机不能为空");
                } else {
                    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(this).val()))){
                        message("error", "手机号不合法！");
                    }else{
                        $("#txtAddr").focus();
                    }
                }
            }
        }

    });
    $("#txtAddr").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if($("#selType").val()==1) {
                if ($(this).val().length == 0) {
                    message("error", "地址不能为空");
                } else {
                    $("#txtRemark").focus();
                }
            }
        }
    });
    $("#txtRemark").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            $("#txtMilkUser").focus();
        }
    });
    $("#txtMilkUser").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if($("#selType").val()==1) {
                if ($(this).val().length > 0) {
                    getAreaByUserId($(this).val());
                } else {
                    getAreaByUserId($(this).val());
                    $("#selArea").focus();
                }
            }
        }
    });
    $("#txtOrder").bind("keypress", function (event) {
        if (event.keyCode == "13") {
            if($("#selType").val()==2){
                if($("#selCon").val()==null){
                    message("error","请选择投诉原因！");
                }else{
                    if($("#selComplaints").find("option:selected").text()=="质量"){
                        if($(this).val().length==0){
                            message("error","订单号不能为空！");
                        }else{
                            getProducts($(this).val());
                        }
                    }else{
                        if($(this).val().length==0){
                            message("error","订单号不能为空！");
                        }else{
                            $("#txtContent").focus();
                        }

                    }
                }

            }
        }
    });

</script>
