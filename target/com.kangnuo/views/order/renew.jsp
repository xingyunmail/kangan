<%--
  Created by IntelliJ IDEA.
  User: sunhao
  Date: 15/5/21
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">
            <select style="width:100px;" onchange="changePage(this)">
                <option selected="selected" value="1">补奶</option>
                <option value="2">品尝奶</option>
            </select>
        </div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="row span12">
            <div class="span4">
                <div class="control-group">
                    送奶员编号：
                    <input type="text" class="input-medium" id="deliverId"/>
                    <span class="help-inline"></span>
                </div>
            </div>
            <div class="span4">
                <div class="control-group">
                    区域经理编号：
                    <input type="text" class="input-medium" id="managerId"/>
                    <span class="help-inline"></span>
                </div>
            </div>
            <div class="span4">
                <span>
                    <button class="btn order-btn" type="button" onclick="queryAddMilkInfo()">查 询</button>
                    <button class="btn btn-primary" type="button" onclick="submitApply()">提交申请</button>
                </span>
            </div>

            </div>
            <div class="row span12" style="margin-left: 0px;">
                <table class="table table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <td style="text-align: center;width:20%;">商品编号</td>
                        <td style="text-align: center;width:20%;">日配数量</td>
                        <td style="text-align: center;width:20%;">配送天数</td>
                        <td style="text-align: center;width:25%;">配送开始日期</td>
                        <td style="text-align: center;width:15%;">添加补奶产品</td>
                    </tr>
                    <tr>

                        <td style="text-align: center;padding: 4px;">
                            <div class="control-group">
                                <input type="text" id="prodId" style="width:100px;margin:0px;"/>
                                <span class="help-inline"></span>
                            </div>
                        </td>
                        <td style="text-align: center;padding: 4px;">
                            <div class="control-group">
                                <input type="text" id="quantity" style="width:100px;margin:0px;"/>
                                <span class="help-inline"></span>
                            </div>
                        </td>
                        <td style="text-align: center;padding: 4px;">
                            <div class="control-group">
                                <input type="text" id="deliverDays" style="width:100px;margin:0px;"/>
                                <span class="help-inline"></span>
                            </div>
                        </td>
                        <td style="text-align: center;padding: 4px;">
                            <div class="control-group">
                                <div class="no-margin input-append date datetimepicker"  data-date-format="yyyy-mm-dd">
                                    <input size="16" type="text" id="beginDate" class="input-small" >
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>
                                <span class="help-inline"></span>
                            </div>
                        </td>
                        <td style="text-align: center;padding: 4px;"><button type="button" class="btn order-btn" onclick="addApply()">添加</button></td>
                    </tr>
                    </thead>

                    <tbody id="addApplyList">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row span12" >
            <table class="table">
                <tr>
                    <td style="text-align: right;width: 120px;">本月配送总数量:</td>
                    <td style="text-align: left"><label id="deliveredNum"></label></td>
                </tr>
                <tr>
                    <td style="text-align: right;width: 120px;">可补奶总量:</td>
                    <td style="text-align: left"><label id="numPerMonth"></label></td>
                </tr>
                <tr>
                    <td style="text-align: right;width: 120px;">已补数量:</td>
                    <td style="text-align: left"><label id="applyNumber"></label></td>
                </tr>
                <tr>
                    <td style="text-align: right;width: 120px;">超出数量:</td>
                    <td style="text-align: left"><label id="beyongNum"></label></td>
                </tr>

            </table>

        </div>
        <div class="well row span12 " style="margin-left: 0px;" >
            <div class="muted pull-left" style="margin-bottom: 15px;margin-top: 15px;">补奶明细：</div>
            <div>
            <table class="table table-bordered table-striped ">
                <thead>
                <tr>
                    <td>申请时间</td>
                    <td>送奶员名称</td>
                    <td>商品名称</td>
                    <td>日配数量</td>
                    <td>配送天数</td>
                    <td>配送开始日期</td>
                    <td>总量</td>
                    <td>区域经理</td>
                </tr>
                </thead>
                <tbody id="applyInfoList">
                <!--generate-->
                </tbody>
            </table>
                <span class="span12" id="pageDiv">

                 </span>
            </div>
        </div>
    </div>
</div>


<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/order/renew.js"></script>

<script type="application/javascript">

    var startNum = 1;
    var limit = 20;

    function gotoPage(activePage, limitNum)
    {
        startNum = activePage;
        limit = limitNum;
        queryAddMilkInfo();
    }

    $(document).ready(function() {

        $(".datetimepicker").datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });

        $("#deliverId").blur(function(){
            checkDeliverId();
        });
//        $("#managerId").blur(function(){
//            checkManagerId();
//        });
        $("#prodId").blur(function(){
            checkProdId();
        });
        $("#quantity").blur(function(){
            checkIsNum("quantity");
        });
        $("#deliverDays").blur(function(){
            checkIsNum("deliverDays");
        });
//        $("#beginDate").blur(function(){
//            checkIsDate();
//        });

    });

    function changePage(selected)
    {
        var selectValue = $(selected).val();
        //torenew
        if(selectValue == 1)
        {
            $("#content").load("order/toRenew");
        }
        //totaste
        else if(selectValue == 2)
        {
            $("#content").load("order/toTaste");

        }



    }

    //查询补奶相关信息
    function queryAddMilkInfo()
    {
        var deliverId = $("#deliverId").val();
        var managerId = $("#managerId").val();
//        if(deliverId != null && deliverId != "")
//        {

            if(!checkDeliverId() ){  // | !checkManagerId()
                return;
            }
            $("#applyInfoList").html("");
            $.post("order/getAddMilkInfo",{"deliverId":deliverId,"managerId":managerId,"applyType":1,"startNum":startNum,"limit":limit},function(data,status){
                if("success" == status)
                {
                    if("success" == data.status)
                    {
                        //取出数据。
                        $.each(data.data,function(index,item){
                            if(index == data.data.length-1){
                                $("#deliveredNum").text(item.deliveredNum);
                                $("#applyNumber").text(item.applyNumber);
                                $("#numPerMonth").text(Math.floor(item.deliveredNum*0.0015));
                                $("#beyongNum").text(Math.floor(item.deliveredNum*0.0015 - item.applyNumber));
                            }
                            else
                            {
                                $("#applyInfoList").append("<tr><td>"+item.applyDate+"</td>" +
                                "<td>"+item.deliverName+"</td>" +
                                "<td>"+item.prodName+"</td>" +
                                "<td>"+item.number+"</td>" +
                                "<td>"+item.deliverDays+"</td>" +
                                "<td>"+item.beginDate+"</td>" +
                                "<td>"+(item.deliverDays*item.number)+"</td>" +
                                "<td>"+item.managerName+"</td></tr>");
                            }
                        });

                        getPagination(data.count, startNum, limit);

                    }
                    else
                    {
                        $("#applyInfoList").append("<tr><td colspan='8'>--无相关记录--</td></tr>")
                    }
                }
                else
                {
                    message("error","系统忙，请稍后重试！");
                }

            },"json");

//        }
//        else
//        {
//            message("error","送奶员编号不能为空！");
//        }

    }

    function addApply() {

        var prodid = $("#prodId").val();
        var quantity = $("#quantity").val();
        var deliverDays = $("#deliverDays").val();
        var beginDate = $("#beginDate").val();

        if(!checkProdId() | !checkIsNum("quantity") | !checkIsNum("deliverDays") | !checkIsDate()){
            return;
        }
        $("#addApplyList").append("<tr><td style='text-align: center;padding: 4px;'>" + prodid + "</td>" +
                "<td style='text-align: center;padding: 4px;'>" + quantity + "</td>" +
                "<td style='text-align: center;padding: 4px;'>" + deliverDays + "</td>" +
                "<td style='text-align: center;padding: 4px;'>" + beginDate + "</td>" +
                "<td style='text-align: center;padding: 4px;'><a onclick ='deleteRow(this)' style='cursor: hand'>删除</a></td></tr>");

        $("#prodId").val("");
        $("#quantity").val("");
        $("#deliverDays").val("");
        $("#beginDate").val("");

    }


        function deleteRow(arow) {
            $(arow).parent().parent().remove();
        }


        //提交申请
        function submitApply() {

            var deliverId = $("#deliverId").val();
            var managerId = $("#managerId").val();
            var applyArray = new Array();
            $("#addApplyList").find("tr").each(function (index) {
                var td = $(this).find("td");
                var apply = new Object();
                apply.deliverId = deliverId;
                apply.managerId = managerId;
                apply.applyType = 1;
                apply.userType = 0;
                apply.prodId = td.eq(0).text();
                apply.number = td.eq(1).text();
                apply.deliverDays = td.eq(2).text();
                apply.beginDate = td.eq(3).text();

                applyArray.push(apply);
            });
            if(!checkDeliverId() ){  // | !checkManagerId()
                return;
            }
            if(applyArray.length==0 ){
                alert("请添加商品");
                return;
            }


            //提交
            $.ajax(
                    {
                        url: "order/addApply",
                        type: "POST",
                        data: JSON.stringify({"applyModelList": applyArray}),
                        success: function (data) {
                            if (data.status == "success") {
                                message("success", "申请成功！！");
                                $("#addApplyList").html("");
                            }
                            else {
                                message("error", "请求异常！！");
                            }
                        },
                        dataType: "json",
                        contentType: "application/json"
                    }
            );

        }



</script>
