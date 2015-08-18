/**
 * Created by ZH on 2015/5/18.
 */
var startNum = 1;
var limit = 20;
    //时间空间设置操作
$(document).ready(function () {

    $("#startDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#endDatedatetimepicker").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });

    $("#updatePriceDate").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    $("#BatchupdatePriceDate").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
    //getList(specialData());
    //console.log(specialData())
    $("#submit").click(function () {
        var sdata = specialData();
            if (sdata.customer != '') {
                getList(sdata);
        } else {
            message("info","请输入查询条件");
        }
    });
    //批量操作
    $("#batchOpter").click(function(){
        batchOpter();
    });
});

//获取客户价格列表
function getList(sdata) {
    $.getJSON("special/getList", sdata, function (json) {
        if (json.status == "success") {
            var result = "";
            $.each(json.data, function (i, item) {
                var bdate = StringformatDate(item.beginDate,"yyyy-MM-dd HH:mm:ss");
                result += "<tr><td><input type='checkbox' name='chkItem' ></td>" +
                "<td>" + item.customer + "</td>" +
                "<td>" + bdate + "</td>" +
                "<td><a href='javascript:void(0)' onclick='javascript:productPirceList(\"" + item.customer + "\",\""+bdate+"\",1)'>查看当前价格</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<a href='javascript:void(0)' onclick='javascript:updatePirceList(\""+item.customer +"\",\""+bdate+"\",3)'>修改价格</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                " <a href='javascript:void(0)' onclick='pirceHistoryList(\"" + item.customer + "\",2)'>查看记录</a></td></tr>";
            });
            getPagination(json.count, startNum, limit);
        } else if (json.status == "noRecord") {
            result += "<tr><td colspan='3'>暂无记录</td></tr>";
        }
        $("#customerList").html(result)
    });
}

//表单数据
function specialData() {
    var sDate = {
        customer: $("#customerId").val(),
        beginDate: $("#startDate").val(),
        endDate: $("#endDate").val(),
        startNum:startNum,
        limit:limit
    };
    return sDate
}

//查看当前价格 way 1
function productPirceList(custID,bdate,way) {
    var prodPirceDiv = dialog({
        id: "productPirceDiv",
        title: "商品价格"
    });
    if (!dialog.get("productPirceDiv").open) {
        var result = "";
        $.getJSON("special/getList", {customer:custID,way:way}, function (json) {

            if (json.status == "success") {
               // console.log("custID:" + custID+","+bdate)
                $("#custID").val(custID);
                $("#sDate").val(bdate);
                $.each(json.data, function (i, item) {
                    result += "<tr><td>" + item.prodName + "</td>" +
                    "<td>" + item.price + "</td></tr>";
                });
            } else if (json.status == "noRecord") {
                result += "<tr><td colspan='3'>暂无记录</td></tr>";
            }
            $("#productPirceList").html(result);
        });


        prodPirceDiv.content($("#productPirceDiv"));
        prodPirceDiv.showModal();
    }
}
//way 2查看记录
function pirceHistoryList(custId,way) {
    var pirceHistoryDiv = dialog({
        id: "pirceHistoryDiv",
        title: "商品修改记录"
    });
    if (!dialog.get("pirceHistoryDiv").open) {
        var result = "";
        $.getJSON("special/getList", {customer: custId,way:way}, function (json) {
            if (json.status == "success") {
                $.each(json.data, function (i, item) {
                    var endD = StringformatDate(item.endDate,"yyyy-MM-dd HH:mm:ss")
                    if(undefined==endD) endD="";

                    result += "<tr><td>" + formatDate(item.beginDate) + "至" + endD + "</td>" +
                    "<td>" + StringformatDate(item.updateTime,"yyyy-MM-dd HH:mm:ss") + "</td>" +
                    "<td><a href='javascript:void(0)' onclick='javascript:findUpdatePriceHistory(\"" + custId + "\",\"" + formatDate(item.beginDate) + "\",\"" + endD + "\")'>查看历史</a></td></tr>";
                });
            }
            $("#priceHistoryList").html(result)
        });

        pirceHistoryDiv.content($("#pirceHistoryDiv"));
        pirceHistoryDiv.showModal();
    }
}
//根据客户ID，有效时间 内查看商品更新的历史记录 way=3
function findUpdatePriceHistory(custId, startime, endTime) {
    var sDate = {
        customer: custId,
        beginDate: startime,
        endDate: endTime,
        way:"3"
    };
    var phbtoc = dialog({
        id: "pirceHistoryByTimeOrCustIdDiv",
        title: "商品价格记录"
    });
    if (!dialog.get("pirceHistoryByTimeOrCustIdDiv").open) {
        phbtoc.content($("#pirceHistoryByTimeOrCustIdDiv"));
        var result = "";
        $.getJSON("special/getList", sDate, function (json) {
            if (json.status == "success") {
                $.each(json.data, function (i, item) {
                    result += "<tr><td>" + item.prodName + "</td>" +
                    "<td>" + item.price + "</td></tr>";
                });
              //  console.log(result)

            } else if (json.status == "noRecord") {
                result += "<tr><td colspan='2'>暂无记录</td></tr>";
            }
            $("#pirceHistoryByTimeOrCustIdList").html(result);
        });

        phbtoc.showModal();
    }
}

//修改价格 按钮
function updatePirceList(custId,startD,way) {
    var updatePirceDiv = dialog({
        id: "updatePirceDiv",
        title: "商品价格修改"
    });
    if (!dialog.get("updatePirceDiv").open) {
        var result = "";
        //special/getprodPriceList
        $.getJSON("special/getList", {customer: custId,beginDate: startD, way:way}, function (json) {
            if (json.status == "success") {
                console.log("custID:" + custId)
                $("#custID").val(custID);
                $.each(json.data, function (i, item) {
                    result += "<tr><td>" + item.prodName + "</td>" +
                    "<td><input type='text' class='input-medium' name='"+item.prodId+"' value='" + item.price + "'/></td></tr>";
                });
            } else if (json.status == "noRecord") {
                result += "<tr><td colspan='3'>暂无记录</td></tr>";
            }
            $("#beginDate").val(startD);
            $("#customer").val(custId);
            $("#updatePirceDivList").html(result);
        });
        updatePirceDiv.content($("#updatePirceDiv"));
        updatePirceDiv.showModal();
    }
}
//1调整价格 2//恢复价格
function AdjustmentPrice(v){
    var AdjustmentPriceID = $("#AdjustmentPriceID").val();
    if(AdjustmentPriceID!=''&& !isNaN(AdjustmentPriceID)){
        $("#updatePirceDivList input").each(function(i){
            if(v==1){
                this.value = parseFloat(this.value) + parseFloat(AdjustmentPriceID)
            }else if(v==2){
                 //way=4获取所有商品的价格
                var result = "";
                //special/getprodPriceList
                $.getJSON("special/getList", {way:4}, function (json) {
                    if (json.status == "success") {
                        $.each(json.data, function (i, item) {
                            result += "<tr><td>" + item.prodName + "</td>" +
                            "<td><input type='text' class='input-medium' name='"+item.prodId+"' value='" + item.price + "'/></td></tr>";
                        });
                    } else if (json.status == "noRecord") {
                        result += "<tr><td colspan='3'>暂无记录</td></tr>";
                    }
                    $("#updatePirceDivList").html(result);
                });

            }
        });
    }else{
        if(v==1) {
            message("info","请输入需要调整的价格");
        }else{

        }
    }
}
//保存价格，N条记录插入N条语句,更新（custid,时间）
function savePriceLog(){
    var list = new Array();
    var str = "";
    $("#updatePirceDivList input").each(function(i){
        var obj = new Object();
        obj.beginDate = $("#beginDate").val();
        obj.customer = $("#customer").val();
        obj.endDate = $("#productPriceUpate").val();
        obj.price = $(this).val();
        obj.prodId = $(this).attr("name");
        list.push(obj)
    });
   // console.log(list);
    $.ajax({
        type: "POST",
        url: "special/batchInsert",
        data: JSON.stringify({"modelList":list}),
        dataType:"json",
        contentType:"application/json",
        success: function(json){
            if (json.status == "success") {
                message("success","更新成功");
                $("#AdjustmentPriceID").val();
                $("#productPriceUpate").val();
                var sdata = specialData();
                    getList(sdata);
                closeDivs('updatePirceDiv');
            }else{
                message("error","更新失败！");
            }
        }
    });
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//弹出批量操作价格
function batchOpter(){
    var list = new Array();
    $("input[type='checkbox']").each(function(i){
        var obj = new Object();
        if ($(this).is(":checked")) {
            obj.beginDate = $(this).parent().nextAll("td").eq(1).text();//最后生效时间
            obj.customer = $(this).parent().nextAll("td").eq(0).text();//客户编号
            list.push(obj);
        }
    });
    if(list.length == 0){
        message("info","请至少选择一项！");
        return false;
    }
  //  console.log( JSON.stringify({"modelList":list}));
    $("#BatchPriceID").val(list);
    var BatchupdatePirceDiv = dialog({
        id: "BatchupdatePirceDiv",
        title: "批量商品价格修改"
    });
    if (!dialog.get("BatchupdatePirceDiv").open) {
        //way=4获取所有商品的价格
        var result = "";
        //special/getprodPriceList

        $.getJSON("special/getList", {way:4}, function (json) {
          //  console.log(json.data)
            if (json.status == "success") {
                $.each(json.data, function (i, item) {
                    result += "<tr><td>" + item.prodName + "</td>" +
                    "<td><input type='text' class='input-medium' name='"+item.prodId+"' value='" + item.price + "'/></td></tr>";
                });
            } else if (json.status == "noRecord") {
                result += "<tr><td colspan='3'>暂无记录</td></tr>";
            }
            $("#BatchupdatePirceDivList").html(result);
        });

    }
    BatchupdatePirceDiv.content($("#BatchupdatePirceDiv"));
    BatchupdatePirceDiv.showModal();
}
//批量加价
function batchAdjustmentPrice(v){

    var BatchAdjustmentPriceID = $("#BatchAdjustmentPriceID").val();
    if(BatchAdjustmentPriceID!=''&& !isNaN(BatchAdjustmentPriceID)) {

        $("#BatchupdatePirceDivList input").each(function (i) {
                this.value = parseFloat(this.value) + parseFloat(BatchAdjustmentPriceID);
        });
    }
}
//批量保存价格
function batchSave() {
    console.log( JSON.stringify($("#BatchPriceID").val()))
    //暂缓开发，下一版本加入批量保存
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//跳转到修改页面
function goUpdatePrice(){
   //alert( $("#custID").val() + $("#sDate").val());
    updatePirceList($("#custID").val(),$("#sDate").val(),3);
}



//取消按钮
function closeDivs(dialogs) {
    dialog.get(dialogs).close().remove();
}
//全选与全不选操作
function selectAll(qcheckbox, name) {
    if ($(qcheckbox).is(":checked")) {
        $("[name = " + name + "]:checkbox").prop("checked", true);
    }
    else {
        $("[name = " + name + "]:checkbox").prop("checked", false);
    }
}

//日期转换格式
function formatDate(millsTimes) {
    var now = new Date(millsTimes);
    var s = now.getFullYear() + "-";
    s += ("0" + (now.getMonth() + 1)).slice(-2) + "-";
    s += ("0" + now.getDate()).slice(-2) + " ";
    s += ("0" + now.getHours()).slice(-2) + ":";
    s += ("0" + now.getMinutes()).slice(-2) + ":"
    s += ("0" + now.getSeconds()).slice(-2);
//    s+= ("00"+now.getMilliseconds()).slice(-3);
    return s;
}

function StringformatDate(date, format) {
    if (!date) return;
    if (!format) format = "yyyy-MM-dd";
    switch(typeof date) {
        case "string":
            date = new Date(date.replace(/-/, "/"));
            break;
        case "number":
            date = new Date(date);
            break;
    }
    if (!date instanceof Date) return;
    var dict = {
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
        return dict[arguments[0]];
    });
}
//替换
function replaceAll(obj, str1, str2) {
    var result = obj.replace(eval("/" + str1 + "/gi"), str2);
    return result;
}
function gotoPage(activePage, limitNum) {
    startNum = activePage;
    limit = limitNum;
    getList(specialData());
}