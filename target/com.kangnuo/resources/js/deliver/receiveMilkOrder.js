//var areas = new Array();
//var param;

var startNum = 1;
var limit = 20;
$(document).ready(function () {
    //权限判断
    competence();
    //初始化区域
    queryAreas();
    //初始化日期
    initDate();
    //开始查询()
//    queryOrders();


});

function initDate(){
    var dateTime = new Date();
    $("#orderDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) + " ");
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
}

//查询地区
function queryAreas() {
    $.get("area/getAreaList",
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $("#orderArea").append("<option   value=''>请选择</option>");
                    $.each(data.data, function (index, item) {
                          $("#orderArea").append("<option id='areaId_'" + index + "  value='" + item.areaId + "'>" + item.areaName + "</option>");
                    });
                }
            }
            else {
                message("error","请求异常！");
            }
        }, "json"
    );
}
//查询订单
function queryOrdersTest() {
    var queryData = getQueryData();
    var orderHead = $("#thead");
    var orderTbody = $("#milkOrders");
    orderHead.html("");
    orderTbody.html("");
    var tasteMilk = "";
    $.ajaxSetup({
        async : false
    });
    $.post("deliver/getTasteMilk", queryData,function(data,status){
        if(status=='success'){
            if (data.status == "success") {
                tasteMilk = data.data;
            }
        }

    });
    $.post("deliver/getMilkOrderList", queryData,function (data, status) {

            if (status == "success") {
                if (data.status == "success") {
                    $("#milkOrderDiv").show();
                    var prodArray = new Array();
                    var userArray = new Array();
                    var row = "<tr><td>送奶员</td>";
                    //添加表头
                    $.each(data.data, function (index, item) {
                        if (prodArray.indexOf(item.prodId) < 0) {
                            prodArray.push(item.prodId);
                            row += "<td>" + item.prodName + "</td>"
                        }
                    });



                    //合计（按送奶员统计）
                    row += "<td>合计</td><td>回瓶数</td></tr>";
                    orderHead.append(row);

                    //添加表内容
                    $.each(data.data, function (index, item) {
                        if (userArray.indexOf(item.userId) < 0) {
                            var newRow = "<tr>"
                            userArray.push(item.userId);
                            newRow += "<td>" + item.userName + "</td>";
                            //设置每个count框以userId、奶品proId的横纵坐标为ID，方便后面填值
                            $.each(prodArray, function (i, t) {
                                newRow += "<td id='td_" + item.userId + "_" + t + "'></td>";
                            });
                            newRow+="<td id='td_"+item.userId+"'></td><td id='td_"+item.userId+"_prodPackage'></td></tr>"
                            orderTbody.append(newRow);
                        }
                    });

                    //品尝奶和合计(按奶品统计)
                    var tasteRow = "<tr><td>品尝奶</td>";
                    var countRow = "<tr><td>合计</td>";
                    $.each(prodArray,function(index,data){
                        tasteRow+="<td id='td_"+data+"_taste'></td>"
                        countRow+="<td id='td_"+data+"'></td>";
                    });
                    tasteRow+="<td id='taste_count'></td><td id='td_taste_prodPackage'></td></tr>";
                    countRow+="<td id='count_user'></td><td id='returnBottleCount'></td></tr>";
                    orderTbody.append(tasteRow);
                    orderTbody.append(countRow);

                    //每个送奶员对应奶品的数量
                    $.each(data.data,function(j,dd){
                        $.each(userArray,function(u,d){
                            $.each(prodArray,function(p,pd){
                                if(d==dd.userId && pd==dd.prodId){
                                    if($("#td_"+d+"_"+pd).text()=="" || $("#td_"+d+"_"+pd).text()==0){
                                        $("#td_"+d+"_"+pd).text(dd.number);
                                    }else{
                                        $("#td_" + d + "_" + pd).text(Number($("#td_" + d + "_" + pd).text()) + Number(dd.number));
                                    }
                                    //回瓶数
                                    if(dd.prodPackage==1){
                                        if($("#td_"+ d +"_prodPackage").text()==''|| $("#td_"+ d +"_prodPackage").text()==0){
                                            $("#td_"+ d +"_prodPackage").text(Number(dd.number));
                                        }else{
                                            $("#td_"+ d +"_prodPackage").text(Number($("#td_"+ d +"_prodPackage").text())+Number(dd.number));
                                        }
                                    }
                                }
                            });

                        });

                    });
                    //以送奶员为基准算合计
                    var count_user = 0;
                    var returnBottleCount = 0;
                    $.each(userArray,function(u,d){
                        var count=0;
                        $.each(prodArray,function(p,pd){
                            var temp =  $("#td_"+ d+"_"+pd).text();
                            if(temp==""){
                                $("#td_"+ d+"_"+pd).text(0);
                                temp=0;
                            }
                            count=parseInt(count)+parseInt(temp);
                        });

                        $("#td_"+d).text(count);
                        //统计合计
                        count_user += Number(count);
                        //统计回瓶数合计
                        returnBottleCount += Number($("#td_"+d+"_prodPackage").text());

                    });



                    //品尝奶赋值
                    var taste_count = 0;
                    if(tasteMilk!='' && tasteMilk.length>0){
                        $.each(tasteMilk,function(index,item){
                            $.each(prodArray,function(i,t){
                                if(item.prodId == t){
                                    $("#td_"+t+"_taste").text(Number(item.number));
                                    taste_count = Number(taste_count)+Number(item.number);
                                    //品尝奶中的回瓶数
                                    if(item.prodPackage==1){
                                        if($("#td_taste_prodPackage").text()==''|| $("#td_taste_prodPackage").text()==0){
                                            $("#td_taste_prodPackage").text(Number(item.number));
                                        }else{
                                            $("#td_taste_prodPackage").text(Number($("#td_taste_prodPackage").text())+Number(item.number));
                                        }
                                    }
                                }
                            });
                        });
                    }

                    //产品为基准算合计
                    $.each(prodArray,function(p,pd){
                        var count=0;
                        $.each(userArray,function(u,d){
                            var temp =  $("#td_"+ d+"_"+pd).text();
                            if(temp==""){
                                $("#td_"+ d+"_"+pd).text(0);
                                temp=0;
                            }
                            count=parseInt(count)+parseInt(temp);
                        });
                        $("#td_"+pd).text(count+Number($("#td_"+pd+"_taste").text()));
                    });

                    //品尝奶合计
                    if(taste_count!=0){
                        $("#taste_count").text(taste_count);
                    }
                    //合计赋值
                    if(count_user!=0){
                        $("#count_user").text(count_user+taste_count);
                    }
                    if(returnBottleCount!=0){
                        $("#returnBottleCount").text(returnBottleCount+Number($("#td_taste_prodPackage").text()));
                    }
                }else {
                    /*if(tasteMilk!='' && tasteMilk.length>0){
                        var tasteCount = 0;
                        var tasteProdArray = new Array();
                        var row = "<tr><td></td>"
                        $.each(tasteMilk,function(index,item){
                            //添加表头
                            if (tasteProdArray.indexOf(item.prodId) < 0) {
                                tasteProdArray.push(item.prodId);
                                row += "<td>" + item.prodName + "</td>"
                            }
                            //合计（按送奶员统计）
                        });
                        row += "<td >合计</td></tr>";
                        orderHead.append(row);
                        var newRow = "<tr><td>品尝奶</td>";
                        $.each(tasteMilk,function(index,item){
                            $.each(tasteProdArray, function (i, t) {
                                newRow += "<td id='td_"+ t +"'></td>";
                            });
                        });
                        newRow += "<td id='tasteCount'></td></tr>";
                        orderTbody.append(newRow);
                        $.each(tasteMilk,function(index,item){
                            $.each(tasteProdArray, function (i, t) {
                                if(item.prodId == t){
                                    $("#td_"+ t).text(Number(item.number));
                                    tasteCount = Number(tasteCount)+Number(item.number);
                                }
                            });
                        });
                        $("#tasteCount").text(tasteCount);

                    }*/
                    $("#milkOrderDiv").hide();
                    message("info","无记录！");

                }
            }else {
                message("error","请求异常！");
            }
        }, "json");
}

//查询订单
function queryOrders() {
    var queryData = getQueryData();
    var orderHead = $("#thead");
    var orderBody = $("#milkOrders");
    orderHead.html("");
    orderBody.html("");
    var tasteMilk = "";

    $.post("deliver/getMilkOrderList", queryData,function (data, status) {
        if (status == "success") {
            if (data.status == "success") {
                $("#milkOrderDiv").show();
                $.each(data.data,function(index,item){
                    if(index==0){
                        var td_head = "<tr><td style='text-align: center;vertical-align: middle;'>"+item.userName+"</td>";
                        var prodNames = item.head;
                        $.each(item.head,function(h,prodName){
                           td_head+="<td style='text-align: center;vertical-align: middle;'>"+prodName+"</td>"
                        });
                        td_head += "</tr>";
                        orderHead.append(td_head);
                    }else{
                        var body = "<tr><td style='text-align: center;vertical-align: middle;'>"+item.userName+"</td>"
                        $.each(item.countProd,function(cp,cpd){
                            body += "<td style='text-align: center;vertical-align: middle;'>"+cpd+"</td>"
                        });
                        body += "<tr>";
                        orderBody.append(body);
                    }
                });
            }
        }else {
            message("error","请求异常！");
        }
    }, "json");
}


function exportReceive(){
    window.location.href = "deliver/exportReceive?orderArea="+$("#orderArea").val()+"&orderDate="+$("#orderDate").val();
}

//获取表单上数据
function getQueryData() {
    var queryData = {
        "orderDate": $("#orderDate").val(),
        "orderArea": $("#orderArea").val(),
        "startNum":startNum,
        "limit":limit
    }
    return queryData;
}

function gotoPage(activePage, limitNum) {
    startNum = activePage;
    limit = limitNum;
    queryOrders();//获取列表的方法
}

function competence(){
    var arr = new Array();
    var obj1 = {"num":"05101","id":"queryButton"};
    var obj2 = {"num":"05102","id":"export"};

    arr.push(obj1);
    arr.push(obj2);
    buttonJudge(arr);
}