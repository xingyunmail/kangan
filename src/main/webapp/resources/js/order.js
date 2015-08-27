/**
 *
 * list.jsp 's javascript code
 * Created by sunhao on 15/5/14.
 */

//停奶数据存储
var stopDatas = new Array();
//启奶数据存储
var activeDatas = new Array();
//
var selectedProds = new Array();


var startNum = 1;
var limit = 20;





////////////////////查询订单数据/////////////////////////////////////
function gotoPage(activePage, limitNum) {
    startNum = activePage;
    limit = limitNum;
    getTables();
}
function queryOrders() {
    startNum = 1;
    getTables();
}
function getTables()
{
    var reqData = getQueryData();
    var orderTbody = $("#orderList");
    var tbody = "";
    orderTbody.html("");

    $.post("order/getOrderInfo",reqData,function (data, status) {
        if (status == "success") {
            if (data.status == "success") {
                $.each(data.data, function (index, item) {

                    var details = item.orderDetail;
                    var rowspan = Number(details.length);
                    var rowbody = "";

                    tbody = "<tr><td rowspan= '"+rowspan+"' style='vertical-align: middle; '><input type='checkbox' name='chkItem' value='"+item.orderId+"'><input type='hidden' id='custid_"+item.orderId+"' value='"+item.custId+"'></td>" +
                    "<td rowspan='"+rowspan+"' style='vertical-align: middle; '><a href='javaScript:toOrderDetail("+index+")'><input type='hidden' id='orderid_"+index+"' value='"+item.orderId+"'>"+item.orderId+"</a></td>" +
                    "<td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.custId+"</td>" +
                    "<td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.custName+"</td>" +
                    "<td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.custPhone+"</td>" +
                    "<td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.custAddr+"</td>";

                    $.each(details, function (index, detail) {

                        var selectString = getDeliverRuleSelect("select_"+detail.detailId,detail.deliverRules,true);

                        if(index == 0 )
                        {
                            rowbody += "<td>"+detail.prodName+"</td><td>"+detail.prodTypeName+"</td><td>"+detail.quantity+"</td><td>"+selectString+"</td><td>"+detail.beginDate+"</td><td>"+detail.endDate+"</td><td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.deliverName+"</td><td rowspan='"+rowspan+"' style='vertical-align: middle; '>"+item.orderPrice.toFixed(2)+" 元</td>";
                        }
                        else
                        {
                            rowbody += "<tr><td>"+detail.prodName+"</td><td>"+detail.prodTypeName+"</td><td>"+detail.quantity+"</td><td>"+selectString+"</td><td>"+detail.beginDate+"</td><td>"+detail.endDate+"</td></tr>";
                        }
                    });

                    tbody += rowbody;

                    orderTbody.append(tbody);
                });
                getPagination(data.count, startNum, limit);
            }
            else
            {
                orderTbody.append("<tr><td colspan='14' style='text-align: center'>--无相关记录--</td></tr>");
            }
        }
        else {
            alert("系统忙，请稍后重试！");
        }
    }, "json");



    //$.post("order/getOrders",reqData,
    //    function (data, status) {
    //        if (status == "success") {
    //            if (data.status == "success") {
    //                $.each(data.data, function (index, item) {
    //                    tbody = "<tr><td><input type='checkbox' name='chkItem' value='"+item.orderId+"'><input type='hidden' id='custid_"+item.orderId+"' value='"+item.custId+"'></td>" +
    //                    "<td><a href='javaScript:toOrderDetail("+index+")'><input type='hidden' id='orderid_"+index+"' value='"+item.orderId+"'>"+item.orderId+"</a></td>" +
    //                    "<td>"+item.custId+"</td>" +
    //                    "<td>"+item.custName+"</td>" +
    //                    "<td>"+item.custPhone+"</td>" +
    //                    "<td>"+item.custAddr+"</td>" +
    //                    "<td>"+item.products+"</td>" +
    //                    "<td>"+item.orderPrice.toFixed(2)+"</td>" +
    //                    "<td>"+item.userName+"</td></tr>";
    //                    orderTbody.append(tbody);
    //                });
    //                getPagination(data.count, startNum, limit);
    //            }
    //            else
    //            {
    //                orderTbody.append("<tr><td colspan='8' style='text-align: center'>--无相关记录--</td></tr>");
    //            }
    //        }
    //        else {
    //            alert("系统忙，请稍后重试！");
    //        }
    //    }, "json");
}

////////////////////停奶操作/////////////////////////////////////
function stopMilk()
{
    var selectid = checkOneSelect();
    var param = {
        "selectid":selectid,
        "operation":"停奶",
        "operatScripte":"showStopTimeDiv(this)",
        "orderStatus":1,
        "stopOrActive":1
    }
    showProdList(param);
}

//显示产品层
function showProdList(params)
{
    var selectid = params.selectid;
    var operation = params.operation;
    var operatScripte = params.operatScripte;
    var orderStatus = params.orderStatus;
    var stopOrActive = params.stopOrActive;
    var divtbody = $("#stopMilkProds");
    divtbody.html("");

    if("" != selectid )
    {
        var prodsdiv =  dialog({
            id:"prodsDialog",
            title:operation+"操作"
        });

        //调用查询订单信息方法
        $.get("order/getOrderProds",{"orderId":selectid,"status":orderStatus,"prodType":"1,3"},function(data, status){
            if (status == "success")
            {
                if (data.status == "success")
                {
                    $.each(data.data,function(index,item){
                        divtbody.append("<tr><td><input type='checkbox' name='stopProds' id='detailid_"+index+"' value='"+item.detailId+"'></td>" +
                        "<td>"+item.prodTypeName+"</td>" +
                        "<td>"+item.prodName+"</td>" +
                        "<td>"+item.quantity+"</td>" +
                        "<td>"+item.deliverDays+"</td>" +
                        "<td>"+item.beginDate+"</td>" +
                        "<td id='endDate_"+index+"'>"+item.endDate+"</td>" +
                        "<td><button class='btn' onclick='"+operatScripte+"' value='"+index+"'>"+operation+"</a></td></tr>");
                    });

                    $("#stopORactive").val(operation);
                    $("#sacommit").val(stopOrActive);

                    prodsdiv.content($("#stopMilkDiv"));
                    prodsdiv.showModal();
                }
                else
                {
                    alert("此订单中没有可以"+operation+"的数据！");
                }

            }
            else
            {
                alert("系统忙，请稍后重试！");
            }

        },"json");
    }

}

//选择停奶日期
function showStopTimeDiv(sbutton)
{
    var stopTimeDiv =  dialog({
        id:"stopTimeDialog",
        title:"选择停奶日期"
    });
    if(!dialog.get("stopTimeDialog").open)
    {
        $("#s_enddate").val($("#endDate_"+sbutton.value).text());
        stopTimeDiv.content($("#stopTimeDiv"));
        $("#s_date_submit").val($("#detailid_"+sbutton.value).val());
        stopTimeDiv.show();
    }

}

//存储停奶日期
function saveStopDate(s_date_button) {
    var deliverRules =  $("#s_selecter ").val();
    var stopDate = $("#s_stopDate").val();
    var beginDate = $("#s_beginDate").val();
    var detailId = s_date_button.value;
    var status = 2;

    var insertData = new Object();
    insertData.detailId = detailId;
    insertData.deliverRules = deliverRules;
    insertData.stopDate = stopDate;
    insertData.beginDate = beginDate;
    insertData.status = status;


    if (null != stopDate && "" != stopDate) {
        if (new Date(stopDate) > new Date() && (null == beginDate || "" == beginDate || new Date(beginDate) >= new Date(stopDate))) {
            updateArray(stopDatas, insertData, detailId);
            dialog.get("stopTimeDialog").close().remove();
        }
        else {
            alert("停奶/启奶日期不合法！");
        }
    }
    else {
        alert("停奶日期不能为空！");
    }
}



////////////////////启奶操作//////////////////////////////////////

//启奶操作,显示可以启奶的产品
function activeMilk()
{
    var selectid = checkOneSelect();
    var param = {"selectid":selectid,"operation":"启奶","operatScripte":"showActiveTimeDiv(this)","orderStatus":2,"stopOrActive":2}
    showProdList(param);
}

//选择启奶日期
function showActiveTimeDiv(abutton)
{
    var activeTimeDiv =  dialog({
        id:"activeTimeDialog",
        title:"选择启奶日期"
    });
    if(!dialog.get("activeTimeDialog").open)
    {
        $("#a_enddate").val($("#endDate_"+abutton.value).text());
        activeTimeDiv.content ( $("#activeTimeDiv"));
        $("#a_date_submit").val($("#detailid_"+abutton.value).val());

        activeTimeDiv.show();
    }

}

//存储启奶日期
function saveActiveDate (a_date_button)
{
    var deliverRules = $("#a_selecter ").val();
    var beginDate = $("#a_beginDate").val();
    var detailId = a_date_button.value;
    var status = 1;
    var insertData = new Object();
    insertData.detailId = detailId;
    insertData.deliverRules = deliverRules;
    insertData.beginDate = beginDate;
    insertData.status = status;


    if(null != beginDate && "" != beginDate )
    {
        if(new Date(beginDate) > new Date())
        {
            updateArray(activeDatas,insertData,detailId);
            dialog.get("activeTimeDialog").close().remove();
        }
        else
        {
            alert("启奶日期不合法！");
        }

    }
    else
    {
        alert("启奶日期不能为空！");
    }
}


//停奶或者启奶提交操作commitBtton 的值为1的时候为停奶操作，为2的时候为启奶操作
function saMilkCommit(commitBtton)
{
            if(1 == commitBtton.value && confirm("确定提交？"))
            {
                if(stopDatas.length !=0)
                {
                    var pramdata = {"detialList":stopDatas};
                    stopMilkCommit(pramdata);
                    closeDivs("prodsDialog");
                }
                else
                {
                    alert("无停奶操作！");
                }
            }
            else if(2 == commitBtton.value && confirm("确定提交？"))
            {
                if(activeDatas.length !=0)
                {
                    //启奶的提交操作
                    var pramdata = {"detialList":activeDatas};
                    activeMilkCommit(pramdata);
                   closeDivs("prodsDialog");
                }
                else
                {
                    alert("无启奶操作！");
                }

            }
            else
            {
                activeDatas = [];
                stopDatas = [];
            }


}

//停奶更新操作
function stopMilkCommit(params)
{
    $.ajax({
            url: "order/stopMilk",
            type: "POST",
            data: JSON.stringify(params),
            success: function (data) {
                if (data.status == "success")
                {
                    alert("停奶操作成功！");
                }
                else
                {
                    alert("请求异常！");
                }
            },
            dataType: "json",
            contentType: "application/json"
        });
    activeDatas = [];
    stopDatas = [];
}

//启奶提交操作
function activeMilkCommit(params)
{
    $.ajax({
        url: "order/activeMilk",
        type: "POST",
        data: JSON.stringify(params),
        success: function (data) {
            if (data.status == "success")
            {
                alert("启奶操作成功！");
            }
            else
            {
                alert("请求异常！");
            }
        },
        dataType: "json",
        contentType: "application/json"
    });
    activeDatas = [];
    stopDatas = [];
}


/////////////////////转奶操作/////////////////////////////////////
//显示转订单数据输入窗口
function transferMilk()
{
    var selected = checkSelect("chkItem");
    var transferDialog = dialog({
        id:"transferDialog",
        title:"请输入需要修改的信息"
    });
    if(selected.length == 1 )
    {
        transferDialog.content($("#updateCustome_one"));
        transferDialog.show();
    }
    else if(selected.length > 1 )
    {
        transferDialog.content($("#updateCustome_all"));
        transferDialog.show();
    }

}

//显示配送员姓名
function showDelivery(input,deliveryName)
{
    var deliverId = input.value;

    $.get("user/info",{"userId":deliverId,"userPosition":"3"},function(data,status){

        if (status == "success") {
            if (data.status == "success") {
                $("#"+deliveryName).val(data.data.userName) ;
                $("#deliverId_info").css("display","none");
            }
            else
            {
                $("#deliverId_info").css("display","block");
                $("#deliverId").val("") ;
                $("#"+deliveryName).val("") ;
            }
        }
        else
        {
            alert("请求异常！");
        }

    }, "json");
}


//输入客户ID之后显示客户信息
function showCustomerInfo()
{
    var custId = $("#transCustId").val();
    if(null != custId && "" != custId)
    {
        $.post("customer/info",{"custId":custId},function(data,status){
            if(status == "success")
            {
                if(data.status == "success")
                {
                    $("#trans_error_info").html("");

                    var custName = $("#custName").val(data.data.custName);
                    var custPhone= $("#custPhone").val(data.data.phone);
                    var custAddr = $("#custAddr").val(data.data.address);
                }
                else
                {
                    $("#trans_error_info").html("");
                    $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>客户编号不存在</lable>");
                }

            }
            else
            {
                alert("系统忙，请稍后再试！");
            }

        },"json");
    }
}

//提交转奶信息
function saveTransferInfo()
{
    var selected = checkSelect("chkItem");

    var custId = $.trim($("#transCustId").val());
    var custName = $.trim($("#custName").val());
    var custPhone= $.trim($("#custPhone").val());
    var custAddr = $.trim($("#custAddr").val());
    var affectDate = $.trim($("#affectDate").val());
    var deliverId = $.trim($("#deliverId").val());
    deliverId = $.trim(deliverId.substring(deliverId.indexOf('-')+1,deliverId.length));

    if(validateTransfer(custName,custPhone,custAddr,affectDate,deliverId) &&confirm("确定修改？"))
    {

        if(selected.length == 1)
        {
            var orderID = selected[0];

            $.post("order/saveTransfer",{"orderId":orderID,"custId":custId,"custName":custName,"custPhone":custPhone,"custAddr":custAddr,"orderDate":affectDate,"deliverId":deliverId},function(data,status){
                if(status == "success")
                {
                    if(data.status == "success")
                    {
                        closeDivs('transferDialog');
                        alert("转奶成功！\n订单编号："+orderID+"\n客户编号："+custId+"\n客户姓名："+custName+"\n客户电话："+custPhone+"\n配送地址："+custAddr+"\n生效日期："+affectDate+"\n送奶员编号："+deliverId);
                    }
                    else
                    {
                        closeDivs('transferDialog');
                        alert("转奶失败！");
                    }
                }
                else
                {
                    alert("请求异常！");
                }
            }, "json");
        }

    }

}
//校验转奶填写数据
function validateTransfer(custName,custPhone,custAddr,affectDate,deliverId)
{
    var flag = false;
    if("" == custName)
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>客户名称不能为空</lable>");
    }
    else if("" == custPhone)
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>客户电话不能为空</lable>");
    }
    else if("" == custAddr)
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>客户地址不能为空</lable>");
    }
    else if("" == affectDate)
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>生效日期不能为空</lable>");
    }
    else if(new Date(affectDate) <= new Date())
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>生效日期不合法</lable>");
    }
    else if("" == deliverId)
    {
        $("#trans_error_info").html("");
        $("#trans_error_info").html("<lable style='color: red;font-size: 12px;'>送奶员不能为空</lable>");

    }
    else{
        $("#trans_error_info").html("");
        flag = true;
    }


    return flag;
}





////////////////////换奶操作/////////////////////////////////////
var originalProds = new Array();

function exchangeMilk()
{
    $("#selectedProdsBody").html("");
    $("#exchangeMilkProdsDiv").html("");
    originalProds = new Array();
    //弹出层显示
    var exchangeProdsDialog = dialog({
        id: "exchangeProdsDialog",
        title: "换奶操作",
        cancelValue: "取消",
        cancel: function () {
        },
        okValue: "确认",
        ok: function () {
           // validateExchange();
            submitExchange();
        }
    });


    var selected = checkOneSelect();
    if("" != selected) {
        //调用查询订单信息方法
        $.get("order/getOrderProds",{"orderId":selected ,"prodType":"1","status":3},function(data, status){
            if (status == "success")
            {
                if (data.status == "success")
                {
                    originalProds = data.data;
                    $.each(data.data,function(index,item){
                        $("#exchangeOrderId").val(item.orderId);
                        $("#exchangeMilkProdsDiv").append("<tr>" +
                        "<td><input type='checkbox'  name='exchangeChk' id='exchangeChk_id_"+item.detailId+"' value = '"+item.detailId+"'></td>" +
                        "<td>"+item.prodId+"</td>" +
                        "<td>"+item.prodName+"</td>" +
                        "<td>"+item.quantity+"</td>"+
                        "<td>"+item.leftNum+"</td>" +
                        "<td>"+item.prodPrice+"</td>" +
                        "<td>"+getDeliverRuleSelect("rulrSelect_"+item.detailId,item.deliverRules,true)+"</td>" +
                        "<td>"+item.beginDate+"</td>" +
                        "<td>"+item.endDate+"</td>" +
                        "<td><div id='original_"+item.detailId+"'><span style='margin-left: 10px;'><a onclick=\"keepOldProd('"+item.detailId+"',"+index+")\"style='cursor: hand'>编辑</a></span></div><div id='haveChange_"+item.detailId+"' style = 'display:none'><span>已更换</span></div><div id='haveStay_"+item.detailId+"' style = 'display:none'><span>已保留</span></div></td></tr>");
                    });

                    exchangeProdsDialog.content($("#exchangeMilkDiv"));
                    $("#exchangeDiffPrice").html("0");
                    exchangeProdsDialog.showModal();
                }
                else
                {
                    alert("此订单中没有产品的数据！");
                }
            }
            else
            {
                alert("系统忙，请稍后重试！");
            }
        },"json");

    }

}


//显示选择新产品弹出层detailid,changeHref
function showChangeProds()
{
    var oldProds = checkSelect("exchangeChk");

    if(oldProds.length != 0)
    {
        $("#exchangeProdBody").html("");
        $("#exchangeBtn").html("");
        selectedProds = new Array();

        //弹出层显示
        var selectProdsDialog = dialog({
            id: "selectProdsDialog",
            title: "选择更换后产品",
            width:"400px",
            cancelValue: "关闭",
            content:$("#exchangeProds"),
            cancel: function () {
                $.each(oldProds,function(index,item)
                {
                    $("#exchangeChk_id_"+item).attr("checked",false);
                });
            }
        });

        $.post("order/getProdList", {"prodType": "1"}, function (data, status) {
            if ("success" == status) {
                if ("success" == data.status){
                    selectedProds = data.data;

                    $("#exchangeBtn").append("<button class='btn btn-primary'  onclick=\"selectExchangeProd('"+oldProds+"')\">更换</button>");

                    $.each(data.data, function (index, item) {
                        $("#exchangeProdBody").append("<tr><td><input  type='checkbox' name='exchangeCheck' value='" + item.prodId + "'></td>" +
                        "<td>" + item.prodId + "</td>" +
                        "<td>" + item.prodName + "</td>" +
                        "<td><button class='btn btn-primary'  onclick=\"selectExchangeProd('"+oldProds+"')\">更换</button></td></tr>");
            });
        }
            }
            else
            {
                alert("请求异常");
            }

        }, "json");

        selectProdsDialog.show();
    }

}

//用于之后生成的时间控件加载事件
function loadDateEvent()
{
    $(".datetimepickerx").datetimepicker({
        minView: 2,
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });
}


//点击更换新产品信息操作
function selectExchangeProd(detailids)
{
    var prodList = checkSelect("exchangeCheck");
    $.each(prodList,function(index,item){
        $.each(selectedProds,function(i,prod){
            if(item == prod.prodId)
            {
                if(index == 0)
                {
                    $("#selectedProdsBody").append("<tr class='delete'><td><input   type='hidden' value='"+detailids+"'><input type='hidden' value='1'>"+prod.prodId+"</td>" +
                    "<td>"+prod.prodName+"</td>" +
                    "<td><input type='text' id='exchangedProd_quantity_"+item+"' style='width:50px;' ></td>" +
                    "<td><input type='text' id='exchangedProd_leftNum_"+item+"' style='width:50px;' onblur=\"showExchangePrice('"+prodList+"','"+detailids+"')\" ></td>" +
                    "<td>"+getDeliverRuleSelect("exchangeSelect_"+detailids ,1,false)+"</td>" +
                    "<td><div class='input-append date datetimepickerx no-margin' style='padding:0px;'  data-date-format='yyyy-mm-dd'>" +
                    "<input style='width:100px;' type='text'  > <span class='add-on'><i class='icon-th'></i></span></div></td>" +
                    "<td><input style='width:100px;' disabled='disabled' type='text'  ></td>" +
                    "<td rowspan='"+prodList.length+"'><a onclick=\"showChangeBtn('"+detailids+"',this,0)\" style='cursor: hand'>删除</a></td></tr>");
                }
                else
                {
                    $("#selectedProdsBody").append("<tr class='delete'><td><input  type='hidden' value='"+detailids+"'><input type='hidden' value='1'>"+prod.prodId+"</td>" +
                    "<td>"+prod.prodName+"</td>" +
                    "<td><input type='text' id='exchangedProd_quantity_"+item+"' style='width:50px;' ></td>" +
                    "<td><input type='text' id='exchangedProd_leftNum_"+item+"' style='width:50px;'  onblur=\"showExchangePrice('"+prodList+"','"+detailids+"')\"></td>" +
                    "<td>"+getDeliverRuleSelect("exchangeSelect_"+detailids ,1,false)+"</td>" +
                    "<td><div class='input-append date datetimepickerx no-margin' style='padding:0px;'  data-date-format='yyyy-mm-dd'>" +
                    "<input style='width:100px;' type='text'  > <span class='add-on'><i class='icon-th'></i></span></div></td>" +
                    "<td><input style='width:100px;' disabled='disabled' type='text' ></td></tr>");
                }

            }
        });
    });

    //加载日期选择时间
    loadDateEvent();
    var detailArray = detailids.split(",");
    $.each(detailArray,function(index,detailid){
        $("#haveChange_"+detailid).css('display','block');
        $("#original_"+detailid).css('display','none');
        $("#exchangeChk_id_"+detailid).prop("checked",false);
        $("#exchangeChk_id_"+detailid).attr("disabled","disabled");
    });

    dialog.get("selectProdsDialog").close();

}



//保留原始产品操作
function keepOldProd(detailid,index)
{
    var selected = originalProds[index];
    $("#haveStay_"+detailid).css('display','block');
    $("#original_"+detailid).css('display','none');
    $("#exchangeChk_id_"+detailid).attr("checked",false);
    $("#exchangeChk_id_"+detailid).attr("disabled","disabled");

    $("#selectedProdsBody").append("<tr ><td><input type='hidden' value='"+selected.detailId+"'><input type='hidden' value='0'>"+selected.prodId+"</td>" +
    "<td>"+selected.prodName+"</td>" +
    "<td><input type='text' style='width:50px;'   value='"+selected.quantity+"'></td>" +
    "<td><input type='text' style='width:50px;' disabled='disabled'   value='"+selected.leftNum+"'></td>" +
    "<td>"+getDeliverRuleSelect("exchangeSelect_"+selected.detailId, selected.deliverRules,false)+"</td>" +
    "<td><div><input style='width:100px;' disabled='disabled' type='text'  value='"+selected.beginDate+"'></div></td>" +
    "<td><input style='width:100px;' disabled='disabled' type='text'  value='"+selected.endDate+"'> </td>" +
    "<td><a onclick=\"showChangeBtn(\'"+detailid+"\',this,1)\" style='cursor: hand'>删除</a></td></tr>");

    //加载日期选择时间
    loadDateEvent();

}


//删除后显示可以更换和保留按钮
function showChangeBtn(detailids,atag,flag)
{
    if(flag == 1)
    {
        $("#original_" + detailids).css('display', 'block');
        $("#haveChange_" + detailids).css('display', 'none');
        $("#haveStay_" + detailids).css('display', 'none');
        $("#exchangeChk_id_"+detailids).removeAttr("disabled");
        $(atag).parent().parent().remove();
    }
    else if(flag == 0)
    {
        var detailArray = detailids.split(",");
        $.each(detailArray,function(index,detailid){
            $("#original_" + detailid).css('display', 'block');
            $("#haveChange_" + detailid).css('display', 'none');
            $("#exchangeChk_id_"+detailid).removeAttr("disabled");
        });


        $(".delete").remove();
        $("#exchangeDiffPrice").html(0);

    }

}

//显示换奶的差价
function showExchangePrice(prodlist,detailids)
{
    var details = detailids.split(",");

    var priceBefore = parseFloat(0);
    var priceAfter = 0;
    var diffPrice = 0;

    $.each(originalProds,function(index,item)
    {
        $.each(details,function(index,detailid){
            if(item.detailId == detailid)
            {
                priceBefore = (parseFloat(priceBefore) + parseFloat( (parseFloat(item.leftNum)*parseFloat(item.prodPrice)).toFixed(2))).toFixed(2);
            }
        });
    });

    var changePostData = getSubmitData();

    //从后台获取差价
    if(null != changePostData && changePostData.length > 0)
    {
        $.ajax({
            url: "order/getExchangePrice",
            type: "POST",
            data: JSON.stringify({"detialList":changePostData}),
            success: function (data) {
                if (data.status == "success")
                {
                    priceAfter =  data.data;
                    diffPrice = parseFloat(parseFloat(priceAfter) - parseFloat(priceBefore)).toFixed(2);
                    $("#exchangeDiffPrice").html(diffPrice);
                }
            },
            dataType: "json",
            contentType: "application/json"
        });
    }

}

//获取页面提交的数据
function getSubmitData()
{
    var changePostData = new Array();

    $("#selectedProdsBody").find("tr").each(function(index){
        var prodDetail = new Object();
        var td = $(this).find("td");
        prodDetail.status = td.find("input").eq(1).val();
        prodDetail.detailIds = td.find("input").eq(0).val();
        prodDetail.orderId = $("#exchangeOrderId").val();
        prodDetail.prodId = td.eq(0).text();
        prodDetail.quantity = td.eq(2).find("input").eq(0).val();
        prodDetail.leftNum = td.eq(3).find("input").eq(0).val();
        prodDetail.deliverRules = td.eq(4).find("select").val();
        prodDetail.beginDate = td.eq(5).find("div").eq(0).find("input").eq(0).val();
        changePostData.push(prodDetail);
    });

    return changePostData;

}



//换奶操作数据填写正确性校验
function validateExchange(changePostData,originalProds)
{
    var beforeNumber = 0;
    var afterNumber = 0;
    var detailList = [];
    var dateFlag = true;

    console.log(changePostData);
    $.each(changePostData,function(index){
        beforeNumber += Number(changePostData[index].leftNum);
        var details = changePostData[index].detailIds.split(",");
        $.merge(detailList,details);
        if('1' == changePostData[index].status &&  new Date(changePostData[index].beginDate) <= new Date())
        {
            dateFlag = false;
        }
    });

    detailList = $.unique(detailList);

    $.each(detailList,function(index_1)
    {
        $.each(originalProds,function(index)
        {
            if(detailList[index_1] == originalProds[index].detailId)
            {
                afterNumber += Number(originalProds[index].leftNum);
            }
        });
    });

    if(beforeNumber == afterNumber && dateFlag)
    {
        return true;
    }
    else
    {
        return false;
    }

}


//提交换奶数据
function submitExchange()
{
    var changePostData = getSubmitData();

    if(null != changePostData && changePostData.length > 0 )
    {
        if(validateExchange(changePostData,originalProds))
        {
            $.ajax({
                url: "order/exchangeMilk",
                type: "POST",
                data: JSON.stringify({"detialList":changePostData}),
                success: function (data) {
                    if (data.status == "success")
                    {
                        alert("换奶成功！");
                    }
                    else
                    {
                        alert("系统忙，请稍后重试！");
                    }
                },
                dataType: "json",
                contentType: "application/json"
            });
        }
        else
        {
            alert("数量、时间不正确！");
        }
    }
    else
    {
        alert("没有换任何奶品！");
    }

}





////////////////////退奶操作//////////////////////////////////////
function returnMilk()
{
    var selectid = checkOneSelect();
    var returnDialog = dialog({
        id:"returnDialog",
        title:"退奶商品列表",
        okValue:"确定退订",
        ok:function(){
            confirmReturn();

        },
        cancelValue:"取消",
        cancel:function(){
            closeDivs('returnDialog');
            $("#totalPrice").val(0);
        }
    });

    if("" != selectid)
    {
        $("#returnProdBody").html("");
        $.get("order/getReturnProds",{"orderId":selectid,"status":3},function(data, status){
            if (status == "success")
            {
                if (data.status == "success")
                {

                    $.each(data.data,function(index,item){
                        $("#returnProdBody").append("<tr><td><input type='checkbox' name='rebox' value ='"+item.detailId+"' onclick='chooseRestunProd(this)'><input id='prodid_"+item.detailId+"' type='hidden' value='"+item.prodId+"'><input id='detailOrderid_"+item.detailId+"' type='hidden' value='"+item.orderId+"'><span style='margin-left: 5px;'>"+(index+1)+"</span></td>" +
                        "<td>"+item.prodName+"</td>"+
                        "<td>"+item.prodTypeName+"</td>" +
                        "<td>"+((item.quantity*item.deliverDays)-item.leftNum)+"</td>" +
                        "<td id='leftNum_"+item.detailId+"'>"+item.leftNum+"</td>" +
                        "<td>"+item.prodPrice+"</td>" +
                        "<td id='returnPrice_"+item.detailId+"'>"+(item.prodPrice*item.leftNum).toFixed(2)+"</td></tr>");
                    });

                    returnDialog.content($("#returnMilkDiv"));
                    returnDialog.showModal();
                }
                else
                {
                    alert("此订单中没有数据！");
                }

            }
            else
            {
                alert("请求异常！");
            }

        },"json");
    }
}

//确认退订
function confirmReturn()
{
    var models = new Array();
    var reason =  $("#returnSelect").find("option:selected").text();
    var details =  checkSelect("rebox");
    if(0 != details.length && "--请选择--" != reason)
    {
        $.each(details,function(index,item){
            var obj = new Object();
            obj.orderId = $("#detailOrderid_"+item).val();
            obj.detailId = item;
            obj.leftNum = $("#leftNum_"+item).text();
            obj.prodid = $("#prodid_"+item).val();
            obj.prodPrice = $("#returnPrice_"+item).text();
            obj.prodTypeName = reason;
            models.push(obj);
        });
        $.ajax({
            url: "order/returnMilk",
            type: "POST",
            data: JSON.stringify({"detialList":models}),
            success: function (data) {
                    if (data.status == "success") {
                        alert("退订成功！");
                    }
                    else
                    {
                        alert("退订失败，请稍后重试！");
                    }

            },
            dataType: "json",
            contentType: "application/json"
        });

    }
    else if(0 != details.length)
    {
        alert("请选择退订理由");
    }

}

//选中退奶，关联退款价格
function chooseRestunProd(checkbox)
{
    //总退款
    var totalReturnPrice = parseFloat($("#totalPrice").val()).toFixed(2);
    //选中商品应退款
    var selectPrice = parseFloat($("#returnPrice_"+checkbox.value).text()).toFixed(2);

    if(checkbox.checked==true)
    {
        totalReturnPrice = Number(totalReturnPrice) + Number(selectPrice);
    }
    else
    {
        totalReturnPrice = Number(totalReturnPrice)- Number(selectPrice);
    }

    $("#totalPrice").val(totalReturnPrice.toFixed(2));

}

//退货牛奶全选与全不选 ，关联退款价格
function returnSelectAll(chk,name)
{
    var totalReturnPrice = parseFloat(0).toFixed(2);

    selectAll(chk,name);

    if($(chk).is(":checked"))
    {
        $.each($("[name = "+name+"]:checkbox"),function(index,item){
            totalReturnPrice = Number(totalReturnPrice)+Number(parseFloat($("#returnPrice_"+item.value).text()).toFixed(2));
        })
        $("#totalPrice").val(totalReturnPrice.toFixed(2));
    }
    else
    {
        $("#totalPrice").val(0.00);
    }
}


/////////////////////赠品确认/////////////////////////////////////
function confirmGift()
{
    var orderArray = checkSelect("chkItem");

    var orderids = "";

    $.each(orderArray,function(index,item){
        orderids = orderids+item;
        if(index< orderArray.length - 1)
        {
            orderids =orderids+",";
        }
    })

    if(orderArray.length != 0)
    {
        var confirmDialog = dialog({
            id:"confirmDialog",
            title:"确认赠品",
            content:"确认所勾选订单的赠品已赠送吗？",
            okValue:"确定",
            ok:function(){
                $.post("order/confirmGift",{"prodType":"2","orderId":orderids,"status":"1"},function(data,status){
                    if (status == "success"){
                        if(data.status == "success")
                        {
                            alert("所选订单赠品已确认赠送！");
                        }
                        else
                        {
                            alert("确认失败，请稍后重试！");
                        }

                    }
                },"json");
            },
            cancelValue:"取消",
            cancel:function(){}
        });
        confirmDialog.show();
    }
}


////////////////////更换赠品//////////////////////////////////////
var oldGiftProds;
function exchangeGift()
{
    var orderid = checkOneSelect();
    var changeGiftDialog = dialog({
        id:"changeGiftDialog",
        title:"赠品更换",
        okValue:"确定",
        ok:function(){
            submitExchangeGift();
        },
        cancelValue:"取消",
        cancel:function(){
            closeDivs('changeGiftDialog');
        }
    });

    if("" != orderid) {
        $("#beforeChangeGiftBody").html("");
        $("#afterChangeGiftBody").html("");
        $("#orderId4Gift").val(orderid);
        //调用查询订单信息方法
        $.get("order/getOrderProds", {
            "orderId": orderid,
            "status": 3,
            "prodType": "2,3",
            "isGiven":0
        }, function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    oldGiftProds = data.data;
                    $.each(data.data, function (index, item) {
                        if(item.prodType == "3") {

                            $("#beforeChangeGiftBody").append("<tr><td><input type='checkbox' name='chkGiftMilk' id='giftCheckBtn_"+item.detailId+"' value='"+item.detailId+"'></td>" +
                            "<td>" + item.prodId + "</td>" +
                            "<td>" + item.prodName + "</td>" +
                            "<td>" + item.quantity + "</td>" +
                            "<td>" + item.leftNum + "</td>" +
                            "<td>"+getDeliverRuleSelect("exchangeSelect_"+item.detailId ,item.deliverRules,true)+"</td>" +
                            "<td>" + item.beginDate + "</td>" +
                            "<td>" + item.endDate + "</td>" +
                            "<td><div id='original_"+item.detailId+"'><span style='margin-left: 10px;'><a style='cursor: hand' href=\"javascript:editOldGift("+index+",'"+item.detailId+"')\">保存</a></span></div><div style='display: none' id='haveModify_"+item.detailId+"'>已修改</div><div  style='display: none' id='haveExchange_"+item.detailId+"'>已更换</div></td></tr>");
                        }
                        else
                        {
                            $("#beforeChangeGiftBody").append("<tr><td><input type='checkbox' name='chkGiftMilk' id='giftCheckBtn_"+item.detailId+"' value='"+item.detailId+"'> </td>" +
                            "<td>" + item.prodId + "</td>" +
                            "<td>" + item.prodName + "</td>" +
                            "<td>" + item.quantity + "</td>" +
                            "<td><select style='width:80px;margin-bottom: 0px;' disabled='true' value='"+item.isGiven+"'><option value='0'>未赠</option></select></td>" +
                            "<td>--</td>" +
                            "<td>--</td>" +
                            "<td>--</td>" +
                            "<td><div id='original_"+item.detailId+"'><span style='margin-left: 10px;'><a style='cursor: hand' href=\"javascript:editOldGift("+index+",'"+item.detailId+"')\">保存</a></span></div><div style='display: none' id='haveModify_"+item.detailId+"'>已修改</div><div  style='display: none' id='haveExchange_"+item.detailId+"'>已更换</div></td></tr>");
                        }
                    });
                    changeGiftDialog.content($("#exchangeGiftDiv"));
                    changeGiftDialog.showModal();
                }
                else {
                    alert("此订单中没有可换赠品！");
                }
            }
            else {
                alert("请求异常！");
            }
        }, "json");
    }

}

//显示可更换产品数据。
function showChangeGift()
{
    var orderid =  $("#orderId4Gift").val();
    var detailids = checkSelect("chkGiftMilk");
    $("#giftProdList").html("");
    $("#giftButtonDiv").html("");
    if(detailids.length !=0) {
        var selectGiftProds = dialog({
            id: "selectGiftProds",
            title: "选择赠品"
        });

        //添加提交按钮
        $("#giftButtonDiv").append("<span><button class='btn btn-primary' onclick=\"selectGiftProd('"+detailids+"')\">选 择</button></span><span style='margin-left: 120px;'><button class='btn' onclick='closeGiftDiv()'>关 闭</button></span>");

        $.post("order/getSelectGift", { "orderId": orderid }, function (data, status) {
            if (status == "success") {
                if (data.status == "success")
                {
                    $.each(data.data, function (index, item)
                    {
                        $("#giftProdList").append("<tr><td><input type='checkbox' name ='exchangeGifts' value='" + item.prodId + "'> <input type='hidden' id='exchangeGiftName_" + item.prodId + "' value='"+item.prodName+"'><input type='hidden' id='exchangeGiftType_" + item.prodId + "' value='"+item.prodType+"'></td>" +
                        "<td >" + item.prodId + "</td>" +
                        "<td>" + item.prodName + "</td>" +
                        "<td><a>选择</a></td></tr>");
                    });
                    selectGiftProds.content($("#selectExchangeGift"));
                    selectGiftProds.show();
                }
                else
                {
                    alert("此订单中没有可换赠品！");
                }
            }
            else
            {
                alert("请求异常！");
            }
        }, "json");
    }

}

//保存原有数据
function editOldGift(index,detailid)
{
    var editprod = oldGiftProds[index];
    if(editprod.prodType == "2")
    {
        $("#afterChangeGiftBody").append("<tr><td><input type='hidden' value='"+editprod.prodType+"'><input type='hidden' value='0'><input type='hidden' value='"+detailid+"'>"+editprod.prodId+"</td>" +
        "<td>"+editprod.prodName+"</td>" +
        "<td><input style='width:80px;' type='text' value='"+editprod.quantity+"'></td>" +
        "<td><select style='width:80px;margin-bottom: 0px;' disabled='true' value='"+editprod.isGiven+"'><option value='0'>未赠</option></select></td>" +
        "<td></td>" +
        "<td></td>" +
        "<td></td>" +
        "<td><a style='cursor: hand' onclick=\"deleteOldGift(this,'"+detailid+"',0)\">删除</a></td></tr>");
    }
    else
    {
        $("#afterChangeGiftBody").append("<tr><td><input type='hidden' value='"+editprod.prodType+"'><input type='hidden' value='0'><input type='hidden' value='"+detailid+"'>"+editprod.prodId+"</td>" +
        "<td>"+editprod.prodName+"</td>" +
        "<td><input style='width:80px;' type='text' value='"+editprod.quantity+"'></td>" +
        "<td><input style='width:80px;' type='text' value='"+editprod.leftNum+"'></td>" +
        "<td>"+getDeliverRuleSelect("exchangeSelect_"+detailid ,editprod.deliverRules,false)+"</td>" +
        "<td>"+editprod.beginDate+"</td>" +
        "<td>"+editprod.endDate+"</td>" +
        "<td><a style='cursor: hand' onclick=\"deleteOldGift(this,'"+detailid+"',0)\">删除</a></td></tr>");
    }

    $("#original_"+detailid).css("display","none");
    $("#haveModify_"+detailid).css("display","block");
    $("#giftCheckBtn_"+detailid).attr("disabled","disabled");

}

//选择更换的赠品
function selectGiftProd(detailids)
{
    var prodids = checkSelect("exchangeGifts");
    if(prodids.length !=0)
    {
        $.each(prodids,function(index,prodid){
            var prodName = $("#exchangeGiftName_"+prodid).val();
            var prodType = $("#exchangeGiftType_"+prodid).val();
            if(index == 0)
            {
                var lastTd = "<td rowspan='"+prodids.length+"'><a style='cursor: hand' onclick=\"deleteOldGift(this,'"+detailids+"',1)\">删除</a></td></tr>";
            }
            else
            {
                var lastTd = "</tr>";
            }
                //实物赠品
            if(prodType == 2)
            {
                $("#afterChangeGiftBody").append("<tr class='deleteGift'><td><input type='hidden' value='"+prodType+"'><input type='hidden' value='1'><input type='hidden' value='"+detailids+"'>"+prodid+"</td>" +
                    "<td>"+prodName+"</td>" +
                    "<td><input style='width:80px;' type='text'></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +
                    "<td></td>" +lastTd
                    );
            }
            else if(prodType == 3)
            {
                    $("#afterChangeGiftBody").append("<tr class='deleteGift'><td><input type='hidden' value='"+prodType+"'><input type='hidden' value='1'><input type='hidden' value='"+detailids+"'>"+prodid+"</td>" +
                    "<td>"+prodName+"</td>" +
                    "<td><input style='width:80px;' type='text'></td>" +
                    "<td><input style='width:80px;' type='text'></td>" +
                    "<td>"+getDeliverRuleSelect("giftSelect_"+prodid,1,false)+"</td>" +
                    "<td><div class='input-append date datetimepickerx no-margin' style='padding:0px;'  data-date-format='yyyy-mm-dd'>" +
                    "<input style='width:100px;' type='text'  > <span class='add-on'><i class='icon-th'></i></span></div></td>" +
                    "<td><input style='width:100px;' type='text' disabled='true'></td>" +lastTd);
            }

        });

    }
    loadDateEvent();

    var detailArray = detailids.split(",");
    $.each(detailArray,function(index,detailid){
        $("#giftCheckBtn_"+detailid).prop("checked",false);
        $("#haveExchange_"+detailid).css("display","block");
        $("#haveModify_"+detailid).css("display","none");
        $("#original_"+detailid).css("display","none");
        $("#giftCheckBtn_"+detailid).attr("disabled","disabled");
    });

    dialog.get("selectGiftProds").close().remove();

}

//关闭选择赠品
function closeGiftDiv()
{
    dialog.get("selectGiftProds").close().remove();
}

//删除保留的赠品
function deleteOldGift(atag,detailids,flag)
{
    if(flag == 0)
    {
        $(atag).parent().parent().remove();
        $("#original_"+detailids).css("display","block");
        $("#haveModify_"+detailids).css("display","none");
        $("#giftCheckBtn_"+detailids).removeAttr("disabled");
        $("#haveExchange_"+detailids).css("display","none");
    }
    else if(flag == 1)
    {
        var detailArray = detailids.split(",");
        $.each(detailArray,function(index,detailid){
            $("#original_"+detailid).css("display","block");
            $("#haveModify_"+detailid).css("display","none");
            $("#giftCheckBtn_"+detailid).removeAttr("disabled");
            $("#haveExchange_"+detailid).css("display","none");

        });

        $(".deleteGift").remove();
    }

}


//提交换赠品
function submitExchangeGift()
{
    var exchangeProds = new Array();
    $("#afterChangeGiftBody").find("tr").each(function(index)
    {
        var exchangeGift = new Object();
        var td = $(this).find("td");
        var prodType = td.eq(0).find("input").eq(0).val();
        exchangeGift.status = td.eq(0).find("input").eq(1).val();
        exchangeGift.detailIds = td.eq(0).find("input").eq(2).val();
        exchangeGift.prodId = td.eq(0).text();
        exchangeGift.quantity = td.eq(2).find("input").eq(0).val();
        exchangeGift.orderId = $("#orderId4Gift").val();
        exchangeGift.prodType = prodType;
        if(prodType == '3')
        {
            exchangeGift.leftNum = td.eq(3).find("input").eq(0).val();
            exchangeGift.deliverRules = td.eq(4).find("select").val();
            exchangeGift.beginDate = td.eq(5).find("div").eq(0).find("input").eq(0).val();
        }
        else
        {
            exchangeGift.leftNum = 0;
            exchangeGift.deliverRules = 0;
        }
        exchangeProds.push(exchangeGift);
    });

    $.ajax({
        url: "order/exchangeGift",
        type: "POST",
        data: JSON.stringify({"detialList":exchangeProds}),
        success: function (data) {
            if (data.status == "success") {
                alert("更换赠品成功！");
            } else {
                alert("系统忙，请稍后重试！");
            }
        },
        dataType: "json",
        contentType: "application/json"
    });
}

////////////////////安装奶箱//////////////////////////////////////
function instalMilkBox()
{
    var selectedId = checkOneSelect();
    if("" != selectedId)
    {

        //弹出输入框
        var instalBoxDialog =  dialog({
            id:"instalBoxDialog",
            title:"输入奶箱票号",
            content:"<input type='text' id='ins_milkBoxId'>",
            okValue:"确定",
            ok:function(){
                var boxId = $("#ins_milkBoxId").val();
                var custId = $("#custid_"+selectedId).val();
                if("" != boxId)
                {
                    if(confirm("确定安装?"))
                    {
                        comfirmInstalBox(custId,boxId);
                    }
                }
                else
                {
                    alert("奶箱票号不能为空！");
                }
            },
            cancelValue:"取消",
            cancel:function(){}
        });
        instalBoxDialog.show();
    }

}
//确认安装奶箱
function comfirmInstalBox(custId,boxId)
{
    $.post("order/installBox",{"customeId":custId,"boxId":boxId,"boxStatus":"1","newStatus":"2"},function(data,status){
        if (status == "success"){
            if(data.status == "success")
            {
                alert("安装成功！");
            }
            else
            {
                alert("安装失败，请检查数据后重试！");
            }

        }
    },"json");

}


////////////////////退回奶箱//////////////////////////////////////

//退回奶箱票号填写
function returnMilkBox()
{
    var selectedId = checkOneSelect();
    if("" != selectedId)
    {

        //弹出输入框
        var returnBoxDialog =  dialog({
            id:"returnBoxDialog",
            title:"输入奶箱票号(每行一个奶箱号)",
            content:"<textarea type='text' id='ret_milkBoxId' style='height:100px;'>",
            okValue:"确定",
            ok:function(){
                var boxId = $("#ret_milkBoxId").val();
                var custId = $("#custid_"+selectedId).val();
                if("" != boxId)
                {
                    if(confirm("确定退回?"))
                    {
                        comfirmReturnBox(boxId,custId);
                    }
                }
                else
                {
                    alert("奶箱票号不能为空！");
                }
            },
            cancelValue:"取消",
            cancel:function(){$("#ret_milkBoxId").val("")}
        });
        returnBoxDialog.show();
    }
}

//退回奶箱确认
function comfirmReturnBox(boxid,custId)
{
    $.post("order/returnBox",{"boxId":boxid,"customeId":custId},function(data,status){
        if (status == "success"){
            if(data.status == "success")
            {
                alert("退回成功！");
            }
            else
            {
                alert("退回失败，请检查数据后重试！");
            }

        }
    },"json");
}





////////////////////以下公用方法/////////////////////////////////////

//全选与全不选操作
function selectAll(qcheckbox,name)
{
    if($(qcheckbox).is(":checked"))
    {
        $("[name = "+name+"]:checkbox").prop("checked", true);
    }
    else
    {
        $("[name = "+name+"]:checkbox").prop("checked", false);
    }
}

//获取表单上数据
function getQueryData()
{
    var deliverid = $.trim($("#q_deliverId").val());
    var hasStop = $("#hadStop").prop("checked");
    var orderStatus = '';
    if(hasStop)
    {
        orderStatus = 'beginNull';
    }
    var rdata = {
        "orderStatus":orderStatus,
        "orderDate": $.trim($("#orderDate").val()),
        "orderDateEnd":$.trim($("#orderDate_end").val()),
        "custPhone":$.trim($("#q_custPhone").val()),
        "custName":$.trim($("#q_custName").val()),
        "custId":$.trim($("#custId").val()),
        "products":$.trim($("#products").val()),
        "deliverId":$.trim(deliverid.substring(deliverid.indexOf('-')+1,deliverid.length)),
        "orderId":$.trim($("#orderId").val()),
        "custAddr":$.trim($("#address").val()),
        "startNum":startNum,
        "limit":limit
    }
    return rdata;
}

//取消按钮
function closeDivs(dialogs)
{
    dialog.get(dialogs).close().remove();
    stopDates = [];
    activeDates = [];
    $("#custName").val("");
    $("#custPhone").val("");
    $("#custAddr").val("");
    $("#affectDate").val("");
    $("#deliverId").val("");
    $("#transCustId").val("");

}

//校验只有一个订单被选中
function checkOneSelect()
{
    var i = 0 ;
    var result = "";
    $.each($("[name = chkItem]:checkbox"),function(index,item){
        if($(item).is(":checked"))
        {
            i++;
            result = $(item).val();
        }
    });

    if(i==0)
    {
        alert("请选中一个订单！");
    }
    else if(i>1)
    {
        result = "";
        alert("一次只能操作一个订单！");
    }

    return result;
}

//校验有无订单被选中
function checkSelect(checkboxName)
{
    var result = new Array();
    $.each($("[name = "+checkboxName+"]:checkbox"),function(index,item){
        if($(item).is(":checked"))
        {
            result.push($(item).val())
        }
    });
    if(result.length == 0)
    {
        alert("请选择一条数据！");
    }

    return result;
}

//操作数据数组
function updateArray(arrays,datas,detailid)
{
    if (0 != arrays.length)
    {
        $.each(arrays, function (index, item) {
            if (detailid == item.detailId)
            {
                arrays.splice(index,1);
                arrays.push(datas);
            }
            else
            {
                arrays.push(datas);
            }
        });
    }
    else
    {
        arrays.push(datas);
    }
}

//转到订单详情操作
function toOrderDetail(index)
{
    var orderid = $("#orderid_"+index).val();
    //$("#content").load("order/toDetail",{"orderId":orderid,"status":0});
    window.open("order/toDetail?orderId="+orderid+"&status=0" ,"_blank","toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no");
}


//获取结束时间。
function getEndDate(deliveRule,deliverDays,startDate)
{
    var endDate = new Date(startDate);
    //天天送
    if(deliveRule == 1)
    {
        endDate.setDate(endDate.getDate()+deliverDays);
    }
    //工作日送
    else if(deliveRule == 2)
    {
       while(deliverDays > 0)
       {
           if(endDate.getDay()!= 6 && endDate.getDay()!= 0)
           {
               deliverDays = deliverDays -1;
           }
           endDate.setDate(endDate.getDate()+1);
       }
    }
    //周末送
    else if(deliveRule == 3)
    {
        while(deliverDays > 0)
        {
            endDate.setDate(endDate.getDate()+1);
            if(endDate.getDay()== 6 || endDate.getDay()== 0)
            {
                deliverDays = deliverDays -1;
            }
        }

    }
    //隔天送
    else if(deliveRule == 4)
    {
        endDate.setDate(endDate.getDate()+((deliverDays-1)*2));
    }

    endDate.setDate(endDate.getDate()-1);

    return endDate;
}



function getDeliverRuleSelect(selectid,ruleValue,disabled)
{
    var origilHtml = $("#diliverRules").html();

    $("#selectModel_detail").prop("id",selectid);

    $("#"+selectid+" option").attr("selected", false);
    $("#"+selectid+" option[value='"+ruleValue+"']").attr("selected", true);

    $("#"+selectid).prop("disabled",disabled);

    var selectHtml = $("#diliverRules").html();

    $("#diliverRules").html("");
    $("#diliverRules").append(origilHtml);

    return selectHtml;
}