<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/6
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>牛奶列表</title>
</head>
<body>
<%--搜索条件--%>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">牛奶列表</div>
    </div>
    <div class="block-content collapse in">
        <form class="form-horizontal">
            <div class="span12">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">

                            <label class="control-label" for="prodName">牛奶名称</label>

                            <div class="controls">
                                <input type="text" class="input-medium" id="prodName">

                            </div>

                            <label class="control-label" for="areaId">所在区域</label>

                            <div class="controls">
                                <select class="span5" id="areaId">
                                    <%--<option value="0">--请选择--</option>--%>
                                    <option value="1">武安</option>
                                    <option value="2">邯郸</option>
                                    <option value="3">邢台</option>
                                    <option value="4">安阳</option>
                                </select>
                                <ul class="pager wizard">
                                    <li class="previous">
                                        <a href="javascript:void(0);" id="submit">提交</a>
                                    </li>
                                </ul>
                            </div>

                        </div>

                </div>
                <%--列表--%>
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">牛奶列表</div>
                    </div>
                    <div class="block-content collapse in">
                        <table class="table table-bordered table-striped">

                            <thead>
                            <tr>
                                <th>牛奶编号</th>
                                <th>牛奶名称</th>
                                <th>牛奶价格</th>
                                <th>牛奶重量</th>
                                <th>牛奶包装</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <span class="span12" id="pageDiv"></span>
                    </div>
                </div>

<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="application/javascript">
    var startNum = 1;
    var limit = 20;
    var params={};
    $(document).ready(function () {
//        getListJson("prodType=2&startNum="+startNum+"&limit"+limit);
        initInfo();
        //搜索
        $("#submit").on("click", function () {
            initInfo();
        });

    });
    function  initInfo(){
        params = {
            prodName:$("#prodName").val(),
            priceType:$("#areaId").val(),
            startNum:startNum,
            limit:limit
        };
        console.log(params)
        getListJson(params);
    }

    //获取列表
    function getListJson(v) {
        var resultN;
        $.post("product/getProductList", v, function (json) {
            if (json.status == "success") {
                var prodPakage = "",msg="",statusmsg="",weight="";
                $.each(json.data, function (index, item) {
                    $(".table tbody").html();
//                     0 袋 1 瓶 2 箱 3 大包 4 包 9实物
                    prodPakage = item.prodPakage;
                    if(item.prodPakage==0 ){ msg = "袋"}
                    if(item.prodPakage==1 ){ msg = "瓶"}
                    if(item.prodPakage==2 ){ msg = "箱"}
                    if(item.prodPakage==3 ){ msg = "大包"}
                    if(item.prodPakage==4 ){ msg = "包"}
                    if(item.prodPakage==5 ){ msg = "实物"}
                    if(item.prodStatus==0 ){ statusmsg = "正常"}
                    if(item.prodStatus==1 ){ statusmsg = "失效"}
                    if(item.prodweight=="" ){ weight = 0}else{weight=item.prodweight}

                    resultN += "<tr><td>" + item.prodId + "</td>" +
                    "<td>" + item.prodName + "</td>" +
                    "<td>" + item.price + "</td>" +
                    "<td>" + weight + "g</td>" +
                    "<td>" + msg + "</td>" +
                    "<td>" + statusmsg + "</td>" +
                    "<td><a tabindex=\"-1\"  href=\"javaScript:loadPage('product/edit?prodId="+item.prodId+"&priceType="+$("#areaId").val()+"')\" id=\"edit\" >编辑<a>" +
                    "&nbsp;<a href=\"javascript:voi(0)\" onclick=\"javascript:deleteProduct("+item.prodId+","+$("#areaId").val()+")\">删除</a>" +
                    "</tr>";

                });
                getPagination(json.count, startNum, limit);
            }
            if (json.status == "noRecord") {
                resultN = "<tr><td colspan='8'>暂无记录<td><tr>";
            }
            $(".table tbody").html(resultN);
        });
    }

    //删除
    function deleteProduct(id,priceType){
        $.getJSON("product/deleteProduct",{prodId:id,priceType:priceType}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    prodName:$("#prodName").val(),
                    priceType:$("#areaId").val(),
                    startNum:startNum,
                    limit:limit
                };
                alert("删除成功");
                getListJson(pageParams);
            }else{
                alert("删除失败");
            }
        });
    }
    //编辑
    function editId(id){
        $.getJSON("product/updateProduct",{prodId:id,priceType:priceType}, function (json) {
            if (json.status == "success") {
                var pageParams = {
                    prodName:$("#prodName").val(),
                    priceType:$("#areaId").val(),
                    startNum:startNum,
                    limit:limit
                };

            }else{

            }
        });
    }
    //分页
    function gotoPage(activePage, limitNum) {
        startNum = activePage;
        limit = limitNum
        var pageParams = {
            prodName:$("#prodName").val(),
            priceType:$("#areaId").val(),
            startNum:startNum,
            limit:limit
        };
        getListJson(pageParams);
    }

    var dateTime = new Date();

    $("#startDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#endDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#delayDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
</script>
</body>
</html>