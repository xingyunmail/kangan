<%--
  Created by IntelliJ IDEA.
  User: zhaijinxu
  Date: 15-5-14
  Time: 下午1:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生产统计单</title>
</head>

<body>

<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">报量表</div>
    </div>
    <div id="alert">

    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">

                    <div class="control-group">
                        <div class="span6">
                            <label class="control-label" for="startDatedatetimepicker">配送日期</label>

                            <div class="controls">

                                <div class="input-append date" id="startDatedatetimepicker"
                                     data-date-format="yyyy-mm-dd ">
                                    <input size="16" type="text" id="startDate" class="input-medium">
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>
                                -
                                <div class="input-append date" id="endDatedatetimepicker"
                                     data-date-format="yyyy-mm-dd ">
                                    <input size="16" type="text" id="endDate" class="input-medium">
                                    <span class="add-on"><i class="icon-th"></i></span>
                                </div>


                            </div>

                        </div>

                        <a href="javascript:void(0);" class="btn" id="submit" onclick="queryData()">查询</a>

                            <button class="btn order-btn" type="button" id="export" onclick="test()">导出</button>

                    </div>

                </div>
                <div class="block-content collapse in">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>数量</th>
                        </tr>
                        </thead>
                        <tbody id = "productionTotal">

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

  /*  $(document).ready(function () {
        competence();
    });
*/

    //加载查询结果
function queryData() {
    if($("#startDate").val()==""||$("#endDate").val()=="")
    {
        message("error","日期不能为空！");
        return false;
    }
    $("#productionTotal").html("");
    var reqdata = selects();
    $.ajax({
        url:"total/list",
        async: true,
        data:reqdata,
        success:function(data) {
            if (data.status = "success") {
                if(data.data!=null) {
                    $.each(data.data, function (index, item) {
                        var tr = $("<tr></tr>");
                        if(item.total!=0){
                            tr.append("<td>" + item.prodName + "</td><td>" + item.total + "</td>");
                        }
                        $("#productionTotal").append(tr);
                    });
                }else{
                    $("#productionTotal").append("<p align='center'>查询记录为空！</p>");
                }
            } else {
                message("error","请求异常！");
            }
        }
    });

}
    //查询日期条件
    function selects(){
        var data = {
            "startDate":$("#startDate").val(),
            "endDate":$("#endDate").val()
        }
        return data ;
    }

    function test() {
        var startDate=$("#startDate").val();
        var endDate=$("#endDate").val();
        var type = 2;
        location.href = "total/getExcel?type=" + type + "&startDate=" + startDate + "&endDate=" + endDate +  "";
    }

    function competence(){
        var arr = new Array();
        var obj1 = {"num":"04101","id":"submit"};
        var obj2 = {"num":"04102","id":"export"};
        arr.push(obj1);
        arr.push(obj2);
        buttonJudge(arr);
    }





//前台获取当前时间加一天
    var d = new Date();
    d.setTime(d.getTime()+24*60*60*1000);
    /*var s = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();*/
  var ss = d.getFullYear() + "-" + fillZero(Number(d.getMonth() + 1), 2) + "-" + fillZero(d.getDate(), 2) + " "
    $("#startDate").val(ss);
    $("#endDate").val(ss);
    $("#startDatedatetimepicker").datetimepicker({
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
    $("#endDatedatetimepicker").datetimepicker({
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
    });$("#delayDatedatetimepicker").datetimepicker({
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



</script>

</body>
</html>
