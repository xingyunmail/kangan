<%--
  Created by IntelliJ IDEA.
  User: zhaijinxu
  Date: 15-5-19
  Time: 下午3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>跟车报告</title>
</head>
<body>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">跟车报告</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="orderArea">线路</label>
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
                        <div class="span6">
                            <div class="controls">
                                <button class="btn order-btn" type="button" id="queryInfo" onclick="queryOrders()">查询
                                </button>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="controls">
                                <button class="btn order-btn" type="button" id="export" onclick="test()">导出</button>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="control-group">
                    <table class="table table-striped table-bordered">
                        <thead id="thead">
                        <tr></tr>
                        </thead>
                        <tbody id="milkOrders">
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </div>
</div>

<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="application/javascript">
    var lines = new Array();
    var param;
    var params;
    $(document).ready(function () {
        competence();
        queryAreas();
       // queryOrders();
        $("#orderArea").change(function () {
            var checkText = $("#orderArea").find("option:selected").text();
            console.log($("#orderArea").val());
           /* var tr = $("<tr><td>"+checkText+"</td></tr>");
            $("#thead").append(tr);*/

        });

        var d = new Date();
        d.setTime(d.getTime()+24*60*60*1000);
        /*var s = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();*/
        var ss = d.getFullYear() + "-" + fillZero(Number(d.getMonth() + 1), 2) + "-" + fillZero(d.getDate(), 2) + " "
        $("#orderDate").val(ss);
        $("#datetimepicker").datetimepicker({
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

    });

    function changeParam(src){
        params = src.getAttribute("name");
        console.log("=============="+params)
        $("#thead").append("<tr><td></td>"+params+"</tr>");
        param = src.getAttribute("id");
        queryOrders();
        param = undefined;
        params = undefined;
    }
    function queryAreas() {//加载配送路线
        $.get("carReport/getLineList",
                function (data, status) {
                    if (status == "success") {
                        if (data.status == "success") {
                            $.each(data.data, function (index, item) {
                                lines.push(item.lineName);
                                $("#orderArea").append("<option  value='" + item.lineId + "'>" + item.lineName + "</option>");
                                $("#areaPage").append("<span style='cursor:pointer;' onclick='changeParam(this)' name='"+item.lineName+"' id='"+item.lineId+"'>"+item.lineName+"<span>  ");
                            });
                        }
                        $("#thead").append("<tr>"+lines[0]+"</tr>");
                    }
                    else {
                        message("error","请求异常！");
                    }
                }, "json" );
    }


    //查询表格数据
    function queryOrders() {
        if($("#orderDate").val()=="")
        {
            message("error","日期不能为空！");
            return false;
        }
        var reqDate = getQueryData();
        console.log(reqDate);
        var orderHead = $("#thead");
        var orderTbody = $("#milkOrders");
        orderHead.html("");
        orderTbody.html("");
        $.ajax({
            url:"carReport/getAllDate",
            data:reqDate,
            success:function(data,status){

                if (status == "success") {
                    if (data.status == "success") {
                        var prodArray = new Array();
                        var prodNameArray = new Array();
                        var row = "<tr><td>品种</td>";
                        //添加表头
                        $.each(data.data, function (index, item) {
                            if (prodArray.indexOf(item.areaId) < 0) {
                                prodArray.push(item.areaId);
                                row += "<td>" + item.areaName + "</td>"
                            }
                        });
                        row += "<td>合计</td><td>漏袋</td>"
                        $("#thead").append(row);

                        //添加表内容
                        $.each(data.data, function (index, item) {
                            var newRow = "<tr>"
                            if (prodNameArray.indexOf(item.prodId) < 0) {
                                prodNameArray.push(item.prodId);
                                newRow += "<td>" + item.prodName + "</td>";
                                $.each(prodArray, function (i, t) {
                                    newRow += "<td id='td_" + item.prodId + "_" + t + "'></td>";
                                });
                                newRow += "<td id='td_" + item.prodId + "'></td><td></td></tr>"
                            }
                            orderTbody.append(newRow);
                        });


                        //品种数量合计
                        var countRow = "<tr><td>合计</td>";
                        $.each(prodArray, function (index, data) {
                            countRow += "<td id='td_" + data + "'></td>";
                        });

                        countRow += "<td></td><td></td></tr>";

                        orderTbody.append(countRow);

                        //每个区域所对应的品种数量
                        $.each(data.data, function (j, dd) {
                            $.each(prodNameArray, function (u, d) {
                                $.each(prodArray, function (p, pd) {
                                    if (d == dd.prodId && pd == dd.areaId) {
                                        $("#td_" + d + "_" + pd).text(dd.total);
                                    }
                                });
                            });
                        });

                        var totals = 0;
                        var totalLuoDai=0;
                        $.each(prodNameArray, function (u, d) {
                            var count = 0;
                            var loudai = 0;
                            $.each(prodArray, function (p, pd) {
                                var temp = $("#td_" + d + "_" + pd).text();
                                if (temp == "") {
                                    $("#td_" + d + "_" + pd).text(0);
                                    temp = 0;
                                }
                                count = parseInt(count) + parseInt(temp);
                                loudai = Math.round(count*0.002);
                                var tr = $("#td_" + d + "_" + pd).parent().find("td:last");
                                tr.html(loudai);
                            });
                             if(count==0){
                             $("#td_" + d).parent().html("");
                             }
                            $("#td_" + d).text(count);
                            totalLuoDai=totalLuoDai+loudai;

                        });

                        $.each(prodArray, function (p, pd) {
                            var count = 0;
                            $.each(prodNameArray, function (u, d) {
                                var temp = $("#td_" + d + "_" + pd).text();
                                if (temp == "") {
                                    $("#td_" + d + "_" + pd).text(0);
                                    temp = 0;
                                }
                                count = parseInt(count) + parseInt(temp);
                            });
                            totals = totals + count

                            $("#td_" + pd).text(count);
                        });
                        var index = $("#milkOrders").find("tr:last").find("td").length-2;
                        var tr = $("#milkOrders").find("tr:last").find("td:eq("+index+")");
                        var trs = $("#milkOrders").find("tr:last").find("td:last");
                        tr.html(totals);
                        trs.html(totalLuoDai);
                    }
                    else {
                        $("#milkOrders").append("<p align='center'>查询记录为空！</p>");
                    }
                }
                else {
                    alert("请求异常！");
                }
            }

        });
       var checkText;
        if(params==undefined){
            checkText=$("#orderArea").find("option:selected").text();
        }else{
            checkText= params;
        }
        console.log(checkText);
        var trs = $("<tr><td>"+checkText+"</td></tr>");
        $("#thead").append(trs);

    }

    //获取表单上数据
    function getQueryData() {
        var areaValue;
        if(param==undefined){
            areaValue=$("#orderArea").val();
        }else{
            areaValue=param;
        }
        var queryData = {
            "prodDate": $("#orderDate").val(),
            "lineid":areaValue
        }
        return queryData;
    }

    function test() {
      var prodDate =$("#orderDate").val().trim();
      var lineid=$("#orderArea").val();
      var type = 2;
        console.log(prodDate)
        location.href = "carReport/getExcel?type="+type+"&prodDate="+prodDate+"&lineid="+lineid;
    }

    function competence(){
        var arr = new Array();
        var obj1 = {"num":"05301","id":"queryInfo"};
        var obj2 = {"num":"05302","id":"export"};
        arr.push(obj1);
        arr.push(obj2);
        buttonJudge(arr);
    }


</script>
</body>
</html>
