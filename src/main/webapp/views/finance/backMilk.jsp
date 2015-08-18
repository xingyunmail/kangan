<%--
  Created by IntelliJ IDEA.
  User: zhaijinxu
  Date: 15/5/22
  Time: 上午9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">退奶费用单列表</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <div class="span16">

            <div class="well well-small">

                <div class="control-group">

                    订单生成时间:
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="startDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    -
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="endDate">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>

                    &nbsp;&nbsp;&nbsp;
                    区域:
                    <select id="areaId" multiple="multiple">

                    </select>

                </div>
                <div class="control-group">

                    退款操作时间:
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="startDateBack">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    -
                    <div class="input-append date" data-date-format="yyyy-mm-dd hh:ii">
                        <input size="16" type="text" class="input-medium" id="endDateBack">
                        <span class="add-on"><i class="icon-th"></i></span>
                    </div>
                    &nbsp;&nbsp;&nbsp;
                    订单编号:<input type="text" class="input-small" id="orderId"/>

                </div>

                <div class="control-group">
                    <a href="javascript:void(0);" id ="queryInfo" type="button" class="btn btn-primary" onclick="getTable();">查询</a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <td width="10%">退款时间</td>
                <td width="8%">退款原因</td>
                <td width="8%">送奶员</td>
                <td width="8%">对应订单</td>
                <td width="8%">客户编码</td>
                <td width="8%">联系方式</td>
                <td width="18%">地址</td>
                <td width="8%">退款商品</td>
                <td width="8%">退款数量</td>
                <td width="8%">退款金额</td>
                <td width="8%">合计退款</td>
            </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>
        <span class="span12" id="pageDiv">

        </span>
    </div>
</div>

<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="resources/css/jquery.multiSelect.css" rel="stylesheet" media="screen">
<link href="resources/css/ui.min.css" rel="stylesheet" media="screen">

<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/jquery.multiselect.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="resources/js/multiselect.js"></script>
<script src="resources/js/util.js"></script>
<script type="application/javascript">
    var startNum = 1;
    var limit = 20;

    $(document).ready(function () {
        getOrderTime();
        dateTime();
        selArea("areaId");
        competence();
    });
    //设置订单时间
    function getOrderTime() {
        var dateTime = new Date();
        /*$("#startDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2));
        $("#endDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2));
        $("#startDateBack").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2));
        $("#endDateBack").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2));*/
    }


    function dateTime() {
        $(".date").datetimepicker({
            startView: 2,
            minView: 2,
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
    }

    function getTables() {

        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var startDateBack = $("#startDateBack").val();
        var endDateBack = $("#endDateBack").val();
        var orderId = $("#orderId").val();
        var areaId = selVal();
        if((startDate==""&&endDate=="")&&(startDateBack==""&&endDateBack==""))
        {
            message("error","订单或退款起止日期不能为空！");
            return false;
        }
        var data = {
            "startDate": startDate,
            "endDate": endDate,
            "orderId": orderId,
            "startDateBack":startDateBack,
            "endDateBack":endDateBack,
            "areaId":areaId,
            "startNum": startNum,
            "limit": limit
        };
        console.log(data);
        $.ajax({
            url: "backMilk/getDate",
            data: data,
            success: function (data) {
                if (data.status == "success") {
                    $("#tbody").html("");
                    var total = 0;
                    $.each(data.data, function (index,item) {
                        for(var i = 0 ; i<item.productModel.length ; i++){
                            if (i == 0) {
                                var date = item.backDate.toString() ;

                                var dates = date.substring(0,19)

                                $("#tbody").append("<tr><td rowspan="+item.productModel.length+">" + dates + "</td><td rowspan="+item.productModel.length+">" + item.reasons + "</td><td rowspan="+item.productModel.length+">" + item.userName +"<br/>"+item.deliverId+ "</td><td rowspan="+item.productModel.length+">" + item.orderId +
                                        "</td><td rowspan="+item.productModel.length+">" + item.custNo + "</td><td rowspan="+item.productModel.length+">" + item.phoneNo + "</td><td rowspan="+item.productModel.length+">" + item.address
                                        + "</td><td>"+item.productModel[i].prodName+"</td><td>"+item.productModel[i].number+"</td><td>"+item.productModel[i].price+"</td><td rowspan="+item.productModel.length+">" + item.returnTotal + "</td></tr>");
                                total +=item.returnTotal;
                            }else{
                                $("#tbody").append("<tr><td>"+item.productModel[i].prodName+"</td><td>"+item.productModel[i].number+"</td><td>"+item.productModel[i].price+"</td></tr>");
                            }
                        }
                    });

                    $("#tbody").append("<tr><td> 合计 </td><td colspan='8'></td><td id = 'count'></td></tr>");
                    $("#count").text(total);
                    getPagination(data.count, startNum, limit);
                } else {
                    $("#tbody").html("");
                    $("#tbody").append("<tr><td colspan='10'  style='text-align: center'>查询记录为空！</td></tr>");
                    message("error","查询记录为空！");
                }
            }
        });
    }

    function getTable() {
        startNum = 1;
        getTables();
    }

    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum;
        getTables();
    }


    function competence(){
        var arr = new Array();
        var obj1 = {"num":"02301","id":"queryInfo"};
        arr.push(obj1);
        buttonJudge(arr);
    }

</script>

