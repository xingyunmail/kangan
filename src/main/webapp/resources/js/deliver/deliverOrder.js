var startNum = 1;
var limit = 20;
$(document).ready(function () {
    //权限判断
    competence();
    //初始化区域--多选下拉框
    selArea("orderArea");
    //获取订单类型
    getOrderType();
    //初始化日期
    initDate();
    //开始查询
//    queryOrders();
});

function initDate(){
    var dateTime = new Date();
    $("#startDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) + " ");
//    $("#endDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) + " ");
    $("#startDatetimepicker").datetimepicker({
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
    /*$("#endDatetimepicker").datetimepicker({
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
    });*/

}

//获取订单类型
function getOrderType() {
    $.get("orderType/orderTypeInfo",
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
//                    $("#orderType").append("<option id='' value=''  >请选择订单类型</option>");
                    $.each(data.data, function (index, item) {
                        $("#orderType").append("<option id='' value='" + item.id + "'>" + item.name + "</option>");
                    });
                }
            }
            else {
                message("error","请求异常！");
            }
        }, "json"
    );
}

function numberCount(){
    $("#numberCount").text("");
    var queryData = getQueryData();
    $.post("deliver/numberCount", queryData,
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $("#numberCount").text(data.data.number);
                }else {
                    message("info","无记录！");
                }
            }
            else {
                message("error","请求异常！");
            }
        }, "json");
}

//配送单列表查询，按奶的品种和送奶员分组查询
function queryOrders() {
//    if(checkSubmit()) {
        $("#changeList").hide();
        $("#orderList").show();
        var queryData = getQueryData();
        var orderTbody = $("#deliverOrder");
        orderTbody.html("");
        $.post("deliver/getDeliverOrder", queryData,
            function (data, status) {
                if (status == "success") {
                    if (data.status == "success") {
                        numberCount();
                        $("#orderListDiv").show();
                        $.each(data.data, function (index, item) {
                            var row = "<tr>" +
                                "<td>" + item.custId + "</td>" +
                                "<td>" + item.userName + "</td>" +
                                "<td>" + item.custName + "</td>" +
                                "<td>" + item.phone + "</td>" +
                                "<td>" + item.address + "</td>" +
                                "<td>" + item.prodName + "</td>" +
                                "<td>" + item.number + "</td>" +
                                "</tr>";
                            orderTbody.append(row);
                        });
                        getPagination(data.count, startNum, limit);
                    }else {
                        $("#orderListDiv").hide();
                        message("info","无记录！");
                    }
                }
                else {
                    message("error","请求异常！");
                }
            }, "json");
//    }
}

function exportDeliverInfo(){
    window.location.href = "deliver/exportDeliver?orderArea="+selVal()+"&startDate="+$("#startDate").val()+"&orderId="+$("#orderId").val()
        +"&userId="+$("#userName").val()+"&prodId="+$("#prodId").val()+"&orderType="+$("#orderType").val();
}

//获取表单上数据
function getQueryData() {
    var orderType;
   /* if($("#orderType").val()==null){
        orderType=1;
    }else{
        orderType=$("#orderType").val();
    }*/
    var queryData = {
        "startDate": $("#startDate").val(),
        "orderArea": selVal(),
        "orderId":$("#orderId").val(),
        "userId":$("#userName").val(),
//        "userName":$("#userName").val(),
        "prodId":$("#prodId").val(),
        "orderType":$("#orderType").val(),
        "startNum":startNum,
        "limit":limit
    }
    return queryData;
}

function changeInfo(){

    var queryData = getQueryData();
    var orderTbody = $("#changeOrder");
    orderTbody.html("");
    $.post("deliver/getChangeOrder", queryData,
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $.each(data.data,function(index,item){
                        var row="<tr>" +
                            "<td>"+item.areaName+"</td>" +
                            "<td>"+item.userName+"</td>" +
                            "<td>"+item.custName+"</td>" +
                            "<td>"+item.phone+"</td>" +
                            "<td>"+item.address+"</td>" +
                            "<td>"+item.prodName+"</td>" +
                            "<td>"+item.changeType+"</td>" +
                            "<td>"+item.change+"</td>" +
                            "<td>"+item.number+"</td>" +
                            "<td>"+item.rest+"</td>" +
                            "</tr>";
                        orderTbody.append(row);
                    });
                }else {
                    $("#orderListDiv").hide();
                    message("info","无记录！");
                }
            }
            else {
                message("error","请求异常！");
            }
        }, "json");

    $("#orderList").hide();
    $("#changeList").show();
}
function closeChange(){
    $("#orderList").show();
    $("#changeList").hide();

}

function exportChangeInfo(){
    window.location.href = "deliver/exportChange?orderArea="+selVal()+"&startDate="+$("#startDate").val()+"&orderId="+$("#orderId").val()
                            +"&userId="+$("#userName").val()+"&prodId="+$("#prodId").val()+"&orderType="+$("#orderType").val();

}

function gotoPage(activePage, limitNum) {
    startNum = activePage;
    limit = limitNum;
    queryOrders();//获取列表的方法
}
function competence(){
    var arr = new Array();
    var obj1 = {"num":"05201","id":"queryButton"};
    var obj2 = {"num":"05202","id":"exportDeliver"};
    var obj3 = {"num":"05203","id":"exportChange"};
    var obj4 = {"num":"05204","id":"changeInfo"};
    arr.push(obj1);
    arr.push(obj2);
    arr.push(obj3);
    arr.push(obj4);
    buttonJudge(arr);
}
