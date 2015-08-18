<%--
Created by IntelliJ IDEA.
User: Administrator
Date: 2015/5/6
Time: 16:38
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>添加牛奶</title>
</head>
<body>
<div id="alert"></div>
<div class="block">
    <div class="navbar navbar-inner block-header">

        <div class="muted pull-left">添加牛奶</div>
        <input type="text" value="${prodId}" id="productId">
        <input type="text" value="${priceType}" id="areaId">
    </div>
    <div class="block-content collapse in">
        <div class="span12">

            <form class="form-horizontal" id="form">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label" for="priceType">所属区域</label>

                                    <div class="controls">
                                        <select class="span5" id="priceType">
                                            <option value="0">--请选择--</option>
                                            <option value="1">武安</option>
                                            <option value="2">邯郸</option>
                                            <option value="3">邢台</option>
                                            <option value="4">安阳</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="prodName">牛奶名称</label>

                                    <div class="controls">
                                        <input id="prodName" class="input-xlarge focused" type="text" value="abcd">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="prodName">牛奶重量</label>

                                    <div class="controls">
                                        <input id="prodweight" class="input-small focused" type="text" value="10">&nbsp;g
                                    </div>
                                </div>
                                <%--商品包装 0 袋 1 瓶 2 箱 3 大包 4 包 9实物--%>
                                <div class="control-group">
                                    <label class="control-label" for="prodPakage">牛奶包装</label>

                                    <div class="controls">
                                        <select id="prodPakage">
                                            <option value="0">袋</option>
                                            <option value="1">瓶</option>
                                            <option value="2">箱</option>
                                            <option value="3">大包</option>
                                            <option value="4">包</option>
                                            <option value="5">实物</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="prodName">牛奶价格</label>

                                    <div class="controls">
                                        <input id="price" class="input-small focused" type="text" value="3.2">&nbsp;元
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="priceType">牛奶类型</label>
                                    <%--商品=1 实物赠品=2 赠奶=3--%>
                                    <div class="controls">
                                        <select class="span5" id="prodType">
                                            <option value="0">--请选择--</option>
                                            <option value="1">正常牛奶</option>
                                            <option value="2">实物赠品</option>
                                            <option value="3">赠送奶品</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="startDatedatetimepicker">价格生效时间</label>

                                    <div class="controls">
                                        <div class="input-append date" id="startDatedatetimepicker"
                                             data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input size="16" type="text" id="startDate">
                                            <span class="add-on"><i class="icon-th"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="endDatedatetimepicker">价格失效时间</label>

                                    <div class="controls">
                                        <div class="input-append date" id="endDatedatetimepicker"
                                             data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input size="16" type="text" id="endDate">
                                            <span class="add-on"> <i class="icon-th"></i></span>
                                        </div>
                                    </div>
                                </div>

                            </fieldset>
                            <button id="addproduct" class="btn btn-primary btn-large" style="margin-left: 26%;">新增
                            </button>
                        </div>
                    </div>
                </div>

                <%--<a href="javaScript:loadPage('product/toList')" id="GoList" style="display: block">跳转到List</a>--%>

</body>
</html>
<%--时间控件--%>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="resources/css/ui-dialog.css">
<script src="resources/js/dialog-min.js"></script>
<script type="application/javascript">
    $(document).ready(function () {
//编辑

        var pid = $("#productId").val()
        var areaId = $("#areaId").val()
        if(pid !=''){
            $("#addproduct").html("编辑");
            $.ajax({
                async: "false",
                type: "POST",
                url: "product/editProduct",
                data: "prodId=" + pid +"&priceType="+areaId,
                success: function (json) {
                    if (json.status == "success") {
                        console.log(json);
                        console.log(formatdate(json.data.endDate, "yyyy/MM/dd HH:mm:ss"));
                        $("#prodName").val(json.data.prodName)
                        $("#priceType").val(json.data.priceType)
                        $("#prodweight").val(json.data.prodweight)
                        $("#prodPakage").val(json.data.prodPakage)
                        $("#price").val(json.data.price)
                        $("#prodType").val(json.data.prodType)
                        $("#startDate").val(formatdate(json.data.beginDate, "yyyy/MM/dd HH:mm:ss"))
                        $("#endDate").val(formatdate(json.data.endDate, "yyyy/MM/dd HH:mm:ss"))

                    } else {

                    }
                }
            });
        }

        $("#addproduct").click(function () {
            if($("#productId").val()!=''){
                submit(2);//编辑
            }else{//新增
                submit(1);
            }
        })



        $("#startDatedatetimepicker").datetimepicker({
            minView: '0',
            format: 'yyyy-mm-dd hh:ii:00',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
        $("#endDatedatetimepicker").datetimepicker({
            minView: '0',
            format: 'yyyy-mm-dd hh:ii:00',
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            keyboardNavigation: true,
            language: 'zh-CN'
        });
    })
    function submit(v){
        var prodName = $("#prodName").val().trim();
        var priceType = $("#priceType").val().trim();
        var prodweight = $("#prodweight").val().trim();
        var prodPakage = $("#prodPakage").val().trim();
        var price = $("#price").val().trim();
        var prodType = $("#prodType").val().trim();
        var startDate = $("#startDate").val().trim();
        var endDate = $("#endDate").val().trim();

        var prodId = $("#productId").val();
        var params = {
            prodId:prodId,
            prodName:prodName,
            priceType: priceType,
            prodweight: prodweight,
            prodPakage: prodPakage,
            price: price,
            prodType: prodType,
            beginDate: startDate,
            endDate: endDate
        }
        console.log(params)
        if (prodName == "" ) {
            alert("牛奶名称不能为空！");
            return false;
        }
        if (priceType == "") {
            alert("请选择区域");
            return false;
        }
        if (prodweight == "") {
            alert("请输入重量！");
            return false;
        }
        if (prodPakage == "") {
            alert("请选择包装！");
            return false;
        }
        if (price == "") {
            alert("价格不能为空！");
            return false;
        }
        if (prodType == "") {
            alert("请选择牛奶类型！");
            return false;
        }
        if (startDate == "") {
            alert("请选择生效日期！");
            return false;
        }
        if (endDate == "") {
            alert("请选择失效日期！");
            return false;
        }
        if(v==1) {
            $.post("product/addProduct", params, function (json) {
                console.log(json);
                if (json.status = "success") {
                    alert("添加成功");
//                    document.getElementById("GoList").click();
                } else {
                    alert("添加失败");
                }
            });
        }else if(v == 2){
            $.post("product/updateProduct", params, function (json) {
                console.log(json);
                if (json.status = "success") {

                    alert("编辑成功");
                    document.getElementById("GoList").click();
                } else {
                    alert("编辑失败");
                }
            });
        }


    }
    //时间错转日期
    function formatdate(time, format) {
        if (null != time) {
            var t = new Date(time);
            var tf = function (i) {
                return (i < 10 ? '0' : '') + i
            };
            return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
                switch (a) {
                    case 'yyyy':
                        return tf(t.getFullYear());
                        break;
                    case 'MM':
                        return tf(t.getMonth() + 1);
                        break;
                    case 'mm':
                        return tf(t.getMinutes());
                        break;
                    case 'dd':
                        return tf(t.getDate());
                        break;
                    case 'HH':
                        return tf(t.getHours());
                        break;
                    case 'ss':
                        return tf(t.getSeconds());
                        break;
                }
            })
        }
        else {
            return "";
        }
    }
</script>
