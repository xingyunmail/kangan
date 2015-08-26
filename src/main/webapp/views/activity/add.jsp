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
    <title>添加活动</title>
</head>
<body>
<div id="alert"></div>
<div class="block">
    <div class="navbar navbar-inner block-header"><input type="hidden" value="${discountID}" id="discountID"/>

        <div class="muted pull-left">添加活动</div>
    </div>
    <div class="block-content collapse in">
        <div class="span12">

            <form class="form-horizontal" id="form">
                <div class="well well-small">
                    <div class="control-group">
                        <div class="span6">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label" for="nameInput">活动名称</label>

                                    <div class="controls">
                                        <input id="nameInput" class="input-xlarge focused" type="text" value="">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="selectype">活动类型</label>

                                    <div class="controls">
                                        <select id="selectype">
                                            <option value="1">满数量赠</option>
                                            <option value="2">满金额赠</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="contentInput">活动内容</label>

                                    <div class="controls">
                                        满&nbsp;<input id="contentInput" class="focused input-small" type="text"
                                                      value=""><font id="selectResult">&nbsp;袋&nbsp;</font>送赠品
                                    </div>
                                </div>
                                <div class="control-group">
                                <label class="control-label" for="contentInput">活动是否累加</label>

                                <div class="controls">
                                    <label class="checkbox-inline">
                                        <input type="radio" name="optionsRadiosinline" id="optionsRadios"
                                               value="1" checked class="radio"> 是&nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" name="optionsRadiosinline" id="optionsRadios2"
                                               value="0" class="radio "> 否
                                    </label>
                                </div>
                            </div>
                                <div class="control-group">
                                    <label class="control-label" for="contentInput">活动赠品数量</label>

                                    <div class="controls">
                                        <label class="checkbox-inline">
                                            <input id="giftNum" class="focused input-small" type="text" value="">

                                        </label>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="startDatedatetimepicker">活动生效时间</label>

                                    <div class="controls">
                                        <div class="input-append date" id="startDatedatetimepicker"
                                             data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input size="16" type="text" id="startDate">
                                            <span class="add-on"><i class="icon-th"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="endDatedatetimepicker">活动失效时间</label>

                                    <div class="controls">
                                        <div class="input-append date" id="endDatedatetimepicker"
                                             data-date-format="yyyy-mm-dd hh:ii:ss">
                                            <input size="16" type="text" id="endtDate">
                                            <span class="add-on"> <i class="icon-th"></i></span>
                                        </div>
                                    </div>
                                </div>

                            </fieldset>
                        </div>
                    </div>
                </div>

                <input type="hidden" value="" id="prodId"/>
                <input type="hidden" value="" id="priceId"/>

                <div class="span4">

                    <table class="table table-striped table-bordered collapse in">
                        <label class="control-label">参加活动商品列表:</label>
                        <thead>
                        <tr>
                            <td>商品编号</td>
                            <td>商品名称</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody id="tbody">

                        </tbody>


                    </table>

                    商品编号
                    <input id="prodIdplus" type="text" value="">&nbsp;&nbsp;
                    <a data-toggle="modal" data-target="#myModal" id="addProdList"><i class="icon-tasks"></i></a>
                    <a class="btn" id="productadd">添加</a>

                </div>

                <div class="block" style="margin-top: 204px;border: none;">
                    <div class="span12">
                        <input type="hidden" value="" id="prodId2"/>
                        <input type="hidden" value="" id="priceId2"/>
                        <table class="table table-striped table-bordered collapse in">
                            <span style="padding-bottom: 10px;">赠品列表:</span>
                            <thead>
                            <tr>
                                <td>商品编号</td>
                                <td>商品名称</td>
                                <td>赠送数量</td>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody id="tbodyList">

                            </tbody>
                        </table>
                        商品编号
                        <input id="discountId2" type="text" value="">&nbsp;&nbsp;
                        数量
                        <input id="discountNum" type="text" value="" class="input-small">&nbsp;&nbsp;
                        <a data-toggle="modal" data-target="#Giftlayout" id="addProdListGift"><i class="icon-tasks"></i></a>
                        <a class="btn" id="productadd2">添加</a>
                    </div>
                </div>


                <div class="span12" style="margin-top: 50px;">
                    <div class="span5"></div>
                    <div class="span1">
                        <button type="button" class="btn btn-primary" id="submit">提交</button>
                    </div>
                    <div class="span1">
                        <button type="button" class="btn">清空</button>
                    </div>
                    <div class="span5"></div>
                </div>


            </form>
        </div>
    </div>
</div>
</div>

<!--赠品弹出层-->
<div id="activeGiftDialog" style="display: none"> <label class="control-label btn" id="addprodGift" onclick="addprodGift()">添加</label>
    <div class="modal-body">
        <div id="msgs2" style="margin-left: 20px;color:red"></div>
        <table class="table table-striped table-bordered collapse in">

            <thead>
            <tr>
                <td><input type="checkbox" onclick="selectAll(this,'prodlistGift')"></td>
                <td>商品编号</td>
                <td>商品名称</td>

            </tr>
            </thead>
            <tbody id="prodlistGift">

            </tbody>
        </table>
    </div>
</div>

<!--商品列表弹出层-->
<div id="activeproductDialog" style="display: none">

    <span style="padding-bottom: 10px;"><label class="control-label btn" id="addprodCheck" onclick="addprodlist()">添加</label>
        </span>
    <div class="modal-body">
        <div id="msgs" style="margin-left: 20px;color:red"></div>
        <table class="table table-striped table-bordered collapse in">


            <thead>
            <tr>
                <td><input type="checkbox" onclick="selectAll(this,'prodlist')"></td>
                <td>商品编号</td>
                <td>商品名称</td>
            </tr>
            </thead>
            <tbody id="prodlist">

            </tbody>
        </table>
    </div>
</div>
<a href="javaScript:loadPage('activity/list')" id="GoList" style="display: none">跳转到List</a>

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
    $(function () {

        $("#nameInput").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#contentInput").focus();
            }
        });
        $("#contentInput").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#giftNum").focus();
            }
        });
        $("#giftNum").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#startDate").focus();
            }
        });
        $("#startDate").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#endDate").focus();
            }
        });
        $("#endDate").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#prodIdplus").focus();
            }
        });

        $("#prodIdplus").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                document.getElementById("productadd").click();
                $("#discountId2").focus();
            }
        });
        $("#discountId2").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                $("#discountNum").focus();
            }
        });
        $("#discountNum").bind("keypress", function (event) {
            if (event.keyCode == "13") {
                document.getElementById("productadd2").click();
            }
        });


//满 送 活动
        $("#selectype").change(function () {
            if ($(this).val() == 1) {
                $("#selectResult").html("&nbsp;袋&nbsp;");
            } else {
                $("#selectResult").html("&nbsp;元&nbsp;");
            }
        });

//保存状态
        var editDiscountID = $("#discountID").val();
        if (editDiscountID != '') {
            $("#submit").html("保存");
            $.ajax({
                async: "false",
                type: "POST",
                url: "activity/editMessage",
                data: "discountId=" + editDiscountID,
                success: function (json) {
                    if (json.status == "success") {
                        var prodinfo = "", prodId = "", prodId2 = "";
                        var prodisgift = "";
                        var name = $("#nameInput").val(json.data[0].discountName);
                        var selectype = $("#selectype").val(json.data[0].discountType);
                        var contentInput = $("#contentInput").val(json.data[0].discountInfo);
                        var giftNum = $("#giftNum").val(json.data[0].giftNum);
                        var priceId = "",priceId2="";
                        if (json.data[0].iaAdd == "0") {
                            $("input[name='optionsRadiosinline'][value='0']").attr("checked", true);
                        }
                        var startDate = $("#startDate").val(json.data[0].beginDate);
                        var endDate = $("#endtDate").val(json.data[0].endDate);
                        var num = $("#discountNum").val();
                        $.each(json.data, function (i, item) {
                            if (json.data[i].isGift == 0) {//1 是商品 2 是赠品
                                prodinfo += "<tr><td>" + json.data[i].prodId + "</td><td>" + json.data[i].prodName + "</td>" +
                                "<td><a href='javascript:void(0)' onclick='deletetr(this,\"" + json.data[i].prodId + "\"," + json.data[i].priceId + ")'>删除</a></td></tr>";
                                prodId += json.data[i].prodId + ","
                                priceId += json.data[i].priceId + ","
                            } else if (json.data[i].isGift == 1) {
                                //if (num == "") num = json.data[i].quantity;
                                prodisgift += "<tr><td>" + json.data[i].prodId + "</td>" +
                                " <td>" + json.data[i].prodName + "</td>" +
                                "<td><input class='input-small' type='text' value='" + json.data[i].quantity + "' id='prodNum'></td>" +
                                "<td><a href='javascript:void(0)' onclick='deletetrGift(this,\"" + json.data[i].prodId + "\","+json.data[i].priceId+")'>删除</a></td></tr>";
                                prodId2 += json.data[i].prodId + ",";
                                priceId2 += json.data[i].priceId + ",";
                            }
                        });
                        $("#prodId").val($("#prodId").val() + prodId);
                        $("#priceId").val($("#priceId").val() + priceId);
                        $("#tbody").append(prodinfo)
                        $("#prodId2").val($("#prodId2").val() + prodId2);
                        $("#priceId2").val($("#priceId2").val() + priceId2);

                        $("#tbodyList").append(prodisgift)
                    } else {
                        message("info","活动不存在");
                    }
                }
            });
        }

//添加参加活动商品//http://localhost:8083/order/getProdInfo?custId=&prodId=100&orderType=1
        $("#productadd").click(function () {

            if ($("#prodIdplus").val() != '') {
                var rs = "", prodinfo = "", priceid = "";
                $.getJSON("activity/getProductMessage", {prodId: $("#prodIdplus").val(), prodType: 1}, function (json) {
                    if (json.status == "success") {
                        var html = $("#tbody").html();
                        if (html.indexOf(json.data[0].prodId) > -1) {
                        } else {
                            rs += "<tr><td>" + json.data[0].prodId + "</td><td>" + json.data[0].prodName + "</td>" +
                            "<td><a href='javascript:void(0)' onclick='deletetr(this,\"" + json.data[0].prodId + "\"," + json.data[0].priceId + ")'>删除</a></td></tr>";
                            prodinfo += json.data[0].prodId + ","
                            priceid = json.data[0].priceId + ","
                            $("#prodId").val($("#prodId").val() + prodinfo);
                        }

                    } else if (json.status == "noRecord") {
                        message("warning","输入的产品编号不存在");
                       // alert("输入的产品编号不存在");
                    }
                    $("#priceId").val($("#priceId").val() + priceid);
                    $("#tbody").append(rs)

                });
            }
        });

//弹出添加参加活动商品列表
        $("#addProdList").click(function () {
            var rs = "", prodinfo = "";
            $("#msgs").html("");
            $.getJSON("activity/getProductMessage", {prodType: "1"}, function (json) {
                if (json.status == "success") {
                    $.each(json.data, function (i, item) {
                        var td = "<td>" + item.prodId + "</td><td>" + item.prodName + "</td>";
                        rs += "<tr>" +
                        "<td><input type='checkbox'  value='" + td + "'  class='checkbox'  alt='" + item.prodId + "' name='" + item.priceId + "' ></td>" +
                        "<td>" + item.prodId + "</td>" +
                        "<td>" + item.prodName +  "</td> </tr>";
                    });

                } else if (json.status == "noRecord") {
                    rs = "<tr><td colspan='3'>暂无记录</td></tr>";
                }

                var activeproductDiv = dialog({
                    id: "activeproductDialog",
                    title: "参加商品活动列表",
                    cancelValue: "关闭",
                    cancel: function () {
                    }
                });
                activeproductDiv.content($("#activeproductDialog"));
                activeproductDiv.showModal();

                $("#prodlist").html(rs)
            });
        });


//添加赠品活动商品---添加按钮
          $("#productadd2").click(function () {

            if ($("#discountId2").val() != '') {
                var rs = "", prodinfo = "", quantity = "",priceIdgifg="";
                var num = $("#discountNum").val();
                $.getJSON("activity/getProductMessage", {
                    prodId: $("#discountId2").val(),
                    prodType: 2
                }, function (json) {

                    if (json.status == "success") {
                        var html = $("#tbodyList").html();

                        if (html.indexOf(json.data[0].prodId) > -1) {

                        } else {
                            if (num == "") num = 0;
                            rs += "<tr><td>" + json.data[0].prodId + "</td>" +
                            " <td>" + json.data[0].prodName + "</td>" +
                            "<td><input class='input-small' type='text' value='" + num + "' id='prodNum'></td>" +
                            "<td><a href='javascript:void(0)' onclick='deletetrGift(this,\"" + json.data[0].prodId + "\","+json.data[0].priceId+")'>删除</a></td></tr>";
                            prodinfo += json.data[0].prodId + ","
                            priceIdgifg += json.data[0].priceId + ",";
                            quantity += num + ","

                        }

                    } else if (json.status == "noRecord") {
                        message("error","输入的产品编号不存在");
                    }
                    $("#prodId2").val($("#prodId2").val() + prodinfo);
                    $("#priceId2").val($("#priceId2").val() + priceIdgifg);
                    $("#tbodyList").append(rs)

                });
            }
        });

//弹出添加参加活动赠品商品列表
        $("#addProdListGift").click(function () {
            $("#msgs2").html("");
            var rs = "", prodinfo = "";
            $.getJSON("activity/getProductMessage", {prodType: "2"}, function (json) {
                if (json.status == "success") {
                    $.each(json.data, function (i, item) {
                        var td = "<td>" + item.prodId + "</td><td>" + item.prodName + "</td>";
                        rs += "<tr>" +
                        "<td><input type='checkbox' value='" + td + "' class='checkbox' alt='" + item.prodId + "' name='"+item.priceId+"' ></td>" +
                        "<td>" + item.prodId + "</td>" +
                        "<td>" + item.prodName + "</td> </tr>";
                    });

                } else if (json.status == "noRecord") {
                    rs = "<tr><td colspan='3'>暂无记录</td></tr>";
                }

                var activeGiftDiv = dialog({
                    id: "activeGiftDialog",
                    title: "参加赠品活动列表",
                    cancelValue: "关闭",
                    cancel: function () {
                    }
                });
                activeGiftDiv.content($("#activeGiftDialog"));
                activeGiftDiv.showModal();
                $("#prodlistGift").html(rs)

            });
        });

        //取消按钮
        function closeDivs(dialogs) {
            dialog.get(dialogs).close().remove();
        }
//最终提交
        $("#submit").click(function () {
            var name = $("#nameInput").val();
            var selectype = $("#selectype").val();
            var contentInput = $("#contentInput").val();
            var isadd = $('input:radio:checked').val();
            var startDate = $("#startDate").val();
            var endDate = $("#endtDate").val();
            var batchprodId = $("#prodId").val();
            var batchGiftProdId = $("#prodId2").val();
            var quantity = "";
            var discountId = $("#discountID").val();
            var priceId     =  $("#priceId").val();
            var priceIdgifg =  $("#priceId2").val();
            var giftNum = $("#giftNum").val();
            $("#tbodyList input").each(function () {
                quantity += $(this).val() + ","
            });
            if (name == '') {
                message("info","活动名称不能为空！");
                return false;
            }
            if (contentInput == '') {
                message("info","活动内容不能为空！");
                return false;
            }
            if (startDate == '') {
                message("info","开始日期不能为空！");
                return false;
            }
            if (endDate == '') {
                message("info","结束日期不能为空！");
                return false;
            }if (giftNum == '') {
                message("info","赠品数量不能为空！");
                return false;
            }


            $.ajax({
                type: "POST",
                url: "activity/addActivity",
                data: "editdiscountId=" + discountId + "&discountName=" + name + "&discountType=" + selectype + "&discountInfo=" + contentInput + "&isAdd=" + isadd + "&beginDate=" + startDate
                + "&endDate=" + endDate + "&batchGiftProdId=" + batchGiftProdId + "&batchprodId=" + batchprodId + "&quantity=" + quantity + "&priceId=" + priceId + "&giftNum=" +giftNum+"&priceIdGift="+priceIdgifg,
                dataType: "json",
                success: function (json) {
                    if (json.status = "success") {
                        $("input").val('');
                        if (editDiscountID!= '') {
                            message("success","保存成功！");
                        } else {
                            message("success","添加成功！");
                        }
                        document.getElementById("GoList").click();
                    }
                }
            });

        });


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
    //addprodlist 商品列表--添加
    function addprodlist(){
        $("#prodlist  input:checkbox").each(function (index) {
            var prodid = $(this).attr("alt") + ",";
            var priceId = $(this).attr("name") + ",";
            if ($(this).is(':checked')) {
                var html = $("#tbody").html();

                var pid = prodid.substring(0,prodid.length-1);
                  if (html.indexOf(pid) > -1) {
                    $("#msgs").html("商品ID"+pid+",已经添加");
                    message("info","商品ID"+pid+"已经添加");
                } else {
                    $("#prodId").val($("#prodId").val() + prodid);
                    $("#priceId").val($("#priceId").val() + priceId);
                    $("#tbody").append("<tr>" + $(this).val() + "<td><a href='javascript:void(0)' onclick='deletetr(this,\"" + $(this).attr("alt") + "\"," + $(this).attr("name") + ")'>删除</a></td></tr>")
                    $("#closeWindow").click();
                    dialog.get("activeproductDialog").close()
                }
            }
        });
    }
    //addprodlist 赠品列表--添加
function addprodGift(){
    $("#prodlistGift  input:checkbox").each(function (index) {
        var prodid = $(this).attr("alt") + ",";       //赠品编号
        var priceIdgift = $(this).attr("name") + ",";//赠品价格ID
        var rs = "";

        if ($(this).is(':checked')) {

//            var html = $("#prodlistGift").html().trim();
            var html = $("#prodId2").val();
            var gid = prodid.substring(0,prodid.length-1);
            if (html.indexOf(gid) > -1) {
                $("#msgs2").html("商品ID"+gid+",已经添加");
                message("info","商品ID"+gid+"已经添加");
            } else {
                $("#prodId2").val($("#prodId2").val() + prodid);
                $("#priceId2").val($("#priceId2").val() + priceIdgift);
                $("#quantity").val($("#quantity").val() + 0 + ",");

                rs += "<tr>" + $(this).val() + "" +
                "<td><input class='input-small' value='0' type='text' id='delivery_counts_1'></td>" +
                "<td><a href='javascript:void(0)' onclick='deletetrGift(this,\"" + $(this).attr("alt") + "\","+$(this).attr("name")+")'>删除</a></td></tr>";
                $("#tbodyList").append(rs);
                $("#closeWindowGift").click();
                dialog.get("activeGiftDialog").close()
//                        console.log(dialog.get("activeGiftDialog").close());
            }

        }
    });
}

    //删除商品列表信息
    function deletetr(s, prodId, priceId) {
        var result = $("#prodId").val();
        var priceId_rs = $("#priceId").val();
        $("#prodId").val(replaceAll(result, prodId + ",", ""));
        $("#priceId").val(replaceAll(priceId_rs, priceId + ",", ""));
        deleteDiscountProd(prodId, priceId);
        $(s).parent().parent().remove();
    }
    //删除赠品列表信息
    function deletetrGift(s, prodId,priceId) {
        var result = $("#prodId2").val();
        var priceId2 = $("#priceId2").val();
        $("#prodId2").val(replaceAll(result, prodId + ",", ""));
        $("#priceId2").val(replaceAll(priceId2, priceId + ",", ""));
        deleteDiscountProd(prodId, priceId);
        $(s).parent().parent().remove();
    }


    function deleteDiscountProd(prodId, priceId) {
        if ($("#discountID").val() != '') {
            $.ajax({
                type: "POST",
                url: "activity/deleteProdDiscount",
                data: "prodId=" + prodId + "&priceId=" + priceId + "&discountId=" + $("#discountID").val(),
                dataType: "json",
                success: function (json) {
                    if (json.status = "success") {
                        message("success","删除成功！");
                    } else {
                        message("error","删除失败！");
                    }
                }
            });
        }
    }

    //替换
    function replaceAll(obj, str1, str2) {
        var result = obj.replace(eval("/" + str1 + "/gi"), str2);
        return result;
    }
    //全选与全不选操作
    function selectAll(qcheckbox, name) {
        if ($(qcheckbox).is(":checked")) {
            $("#"+name+" input:checkbox").prop("checked", true);
        }
        else {
            $("#"+name+" input:checkbox").prop("checked", false);
        }
    }

</script>