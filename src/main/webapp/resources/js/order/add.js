/**
 * Created by scar on 15/5/26.
 */
var customer = []
////////////////////查询所有订奶用户//////////////////////////////////
function getCustomerList(){
    $.ajax(
        {
            url: "customer/getCustomerList",
            type: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function (json) {
                if (json.status == "success") {
                    //console.log(json.data);
                    customer = json.data;
                    var prodShowArray = [];
                    $.each(customer, function (index, item) {
                        var show = item.custId + "---" + item.custName+"----"+item.phone;
                      //  console.log(show)
                        var tmp = {
                            'show': show, 'custId': item.custId, 'custName': item.custName
                        }
                       // console.log(tmp)
                        prodShowArray.push(tmp);
                    });
                    $('#likeinfo').typeahead({
                        source: prodShowArray,
                        display: 'show'
                    });
                }
            }
        });
}
/**
 * 获取订奶用户信息
 */
function getProdInfoById() {
    var cust_ID = $("#likeinfo").val().split("---")[0];
    $.each(customer, function (index, item) {
        if (item.custId == cust_ID) {
            $("#inputCustid").val(item.custId);
            $("#inputCustname").val(item.custName);
            $("#inputPhone").val(item.phone);
            $("#inputAddress").val(item.address);
            return false;
        }
    });
}


//获取活动列表
function getDisCount() {
    $.get("activity/selectByDiscountId?discountStatus=all",
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $("#discountId").html("<option value='0'>---请选择---</option>");
                    $.each(data.data, function (index, item) {
                        $("#discountId").append("<option value='" + item.discountId + "'>" + item.discountName + "</option>");
                    });
                } else if (data.status == "noRecord") {
                    $("#discountId").html("<option value='0'>---暂无活动---</option>");

                } else {
                    message('error', '暂无活动数据！');
                }
            }
            else {
                message('error', '获取活动列表失败！');
            }
        }, "json"
    );
}
//获取活动详情
function chooseDiscount() {

    var discountId = $("#discountId").val();
    if (discountId == 0) {
        $("#tb_discount").empty();
        $("#dicount_info").empty();
        return;
    }
    var orderDetials = new Array();
    $("#tb_detial").find("tr").each(function () {
        var td = $(this).find("td");
        var priceid = td.eq(2).children().eq(1).val();
        var price = td.eq(2).children().eq(0).html();
        var deliverDays = td.eq(4).html();
        var quantity = td.eq(3).html();
        var orderDetial = new Object();
        orderDetial.prodId = td.eq(0).html();
        orderDetial.priceid = priceid;
        orderDetial.number = quantity * deliverDays;
        orderDetial.price = price * quantity * deliverDays;
        orderDetial.deliverDays = deliverDays;
        orderDetials.push(orderDetial);
    });
    if (orderDetials.length == 0) {
        message('error', '请至少添加一个商品！');
        $("#tb_discount").empty();
        $("#dicount_info").empty();
        $("#discountId").val(0);
        return;
    }
    var data = {
        'discountId': discountId,
        'productModels': orderDetials
    }
    console.log(data);
    $("#tb_discount").empty();
    $("#dicount_info").empty();
    $.ajax(
        {
            url: "activity/activityCheck",
            type: "POST",
            data: JSON.stringify(data),
            success: function (data) {
                if (data.status == "success") {
                    $.each(data.data.productModels, function (index, item) {
                        if (item.prodType == 3) {
                            $("#tb_discount").append("<tr><input type='hidden' value='" + item.prodType + "'/><td>" + item.prodId + "</td><td>" + item.prodName + "</td><td><span>" + item.price.toFixed(2) + "</span><input type='hidden' value='" + item.priceId + "'/></td><td>" + item.quantity + "</td><td><input type='text'  class='input-small'/></td><td><input type='text' class='input-small'/></td><td><select class='deliveryRule_gift'></select></td><td>" +
                            "<div class='input-append date_for_gift date' data-date-format='yyyy-mm-dd'><input size='16' type='text'><span class='add-on'><i class='icon-th'></i></span></div></td></tr>");
                        } else {
                            $("#tb_discount").append("<tr><input type='hidden' value='" + item.prodType + "'/><td>" + item.prodId + "</td><td>" + item.prodName + "</td><td><span>" + item.price.toFixed(2) + "</span><input type='hidden' value='" + item.priceId + "'/></td><td>" + item.quantity + "</td><td><input type='text'  class='input-small'/></td><td><select class='input-small'><option value='0'>未赠</option><option value='1'>已赠</option></select></td><td></td><td></td></tr>");
                        }
                    });
                    getDeliveryRuleForGift();
                    getDateTimePickerForGift();
                    $("#dicount_info").html("可选择赠品种类为：" + data.data.giftNum * data.data.multiple);
                } else {
                    message('error', '获取赠品数据失败！');
                }
            },
            dataType: "json",
            contentType: "application/json"
        });

}
//获取订单类型
function getOrderType() {
    $.get("orderType/orderTypeInfo",
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $.each(data.data, function (index, item) {
                        if (item.name == '正常订单') {
                            $("#selectOrderType").append("<option selected='selected' value='" + item.id + "'>" + item.name + "</option>");
                        } else {
                            $("#selectOrderType").append("<option value='" + item.id + "'>" + item.name + "</option>");
                        }
                    });
                } else {
                    message('error', '订单数据暂无！');
                }
            }
            else {
                message('error', '获取订单类型数据失败！');
            }
        }, "json"
    );
}
//获取配送类型
function getDeliveryRuleForGift() {
    $.get("order/deliDict", function (data) {
        if (data.status == "success") {
            if (data.status == "success") {
                $.each(data.data, function (index, item) {
                    $(".deliveryRule_gift").append("<option value='" + item.itemValue + "'>" + item.itemDiscrible + "</option>");
                });
            } else {
                message('error', '配送类型数据暂无！');
            }
        }
        else {
            message('error', '获取配送类型数据失败！');
        }
    }, "json");
}
function getDeliveryRule(id) {
    $.get("order/deliDict", function (data) {
        if (data.status == "success") {
            if (data.status == "success") {
                $.each(data.data, function (index, item) {
                    $("#" + id).append("<option value='" + item.itemValue + "'>" + item.itemDiscrible + "</option>");
                });
            } else {
                message('error', '配送类型数据暂无！');
            }
        }
        else {
            message('error', '获取配送类型数据失败！');
        }
    }, "json");
}
//时间控件
function getDateTimePickerForGift() {
    $(".date_for_gift").datetimepicker({
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
function getDateTimePicker() {
    $("#datetimepicker1").datetimepicker({
        minView: '0',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true,
        keyboardNavigation: true,
        language: 'zh-CN'
    });

    $("#datetimepicker2").datetimepicker({
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
//设置订单时间
function getOrderTime() {
    var dateTime = new Date();
    $("#inputOrderDate").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(dateTime.getDate(), 2) + " " + fillZero(dateTime.getHours(), 2) + ":" + fillZero(dateTime.getMinutes(), 2));
}
//设置配送时间
function getDevTime() {
    var dateTime = new Date();
    if (dateTime.getHours() > 15) {
        dateTime = new Date(dateTime.valueOf() + 2 * 24 * 60 * 60 * 1000);
        $("#start_day").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(Number(dateTime.getDate()), 2));
    } else {
        dateTime = new Date(dateTime.valueOf() + 1 * 24 * 60 * 60 * 1000);
        $("#start_day").val(dateTime.getFullYear() + "-" + fillZero(Number(dateTime.getMonth() + 1), 2) + "-" + fillZero(Number(dateTime.getDate()), 2));
    }

}
//添加一条详情
function addOneDetial() {
    var productId = $("#productId").val().trim();
    var productName = $("#productName").html();
    var productPrice = $("#productPrice").html().trim();
    var delivery_counts = $("#delivery_counts").val().trim();
    var delivery_days = $("#delivery_days").val().trim();
    var deliveryRule = $("#deliveryRule").val();
    var deliveryRuleText = $("#deliveryRule").find("option:selected").text();
    var start_day = $("#start_day").val().trim();

    var objDate = new Date(start_day);
    var nowDate = new Date();
    if (objDate < nowDate) {
        message("error", "订单日期信息不正常，请查证！");
        return;
    }

    if (productId != "" && productPrice != "" && delivery_counts != "" && delivery_days != "") {
        $("#tb_detial").append("<tr class='new_detial'>" +
        "<td>" + productId + "</td>" +
        "<td>" + productName + "</td>" +
        "<td>" + productPrice + "</td>" +
        "<td>" + delivery_counts + "</td>" +
        "<td>" + delivery_days + "</td>" +
        "<td><span>" + deliveryRuleText + "</span><input type='hidden' value='" + deliveryRule + "'></td>" +
        "<td>" + start_day + "</td>" +
        "<td><a class='btn btn-mini' href='javascript:void(0);' onclick='delOneDetial(this)'>删除</a></td>" +
        "</tr>");
        getTotalPrice();
        $("#productId").focus();
        $("#productId").val("");
        $("#productName").html("");
        $("#productPrice").html("");
        $("#delivery_counts").val("");
        $("#delivery_days").val("");
        $("#deliveryRule").val(1);
        getDevTime();
    } else {
        message("error", "订单详情信息不完整，请验证！");
    }


}
//删除一条详情
function delOneDetial(obj) {
    $(obj).parent().parent().remove();
}
//获取配送员及开票员
function getUserInfo(userid, item) {
    $.get("user/info", {"userId": userid},
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $("#" + item).parent().parent().removeClass("error");
                    if (item == "deliverName") {
                        $("#inputAddUserid").val($("#" + item).prev().val());
                        $("#inputAddUserid").focus();
                    } else {
                        submitData();
                    }
                    $("#" + item).html(data.data.userName);
                } else {
                    $("#" + item).html("没有查询到该员工信息！");
                    $("#" + item).parent().parent().addClass("error");
                    $("#" + item).prev().focus();
                }
            }
            else {
                $("#" + item).html("查询该员工信息失败！");
                $("#" + item).parent().parent().addClass("error");
                $("#" + item).prev().focus();
            }
        }, "json"
    );
}


//查询出产品相关数据
function queryProd(custId, prodId, orderType) {
    $.get("order/getProdInfo", {
        "custId": custId,
        "prodId": prodId,
        "orderType": orderType
    }, function (data, status) {
        if (status == "success") {
            if (data.status == "success") {
                if (data.data.prodType == 1) {
                    $("#productName").html(data.data.prodName);
                    $("#productPrice").html("<span>" + data.data.price.toFixed(2) + "</span><input type='hidden' value='" + data.data.priceId + "'/>");
                    $("#delivery_counts").focus();
                } else {
                    //Todo 非商品类型，所属赠品类型状态处理
                    message('error', '该商品未在所选区域进行销售！');
                }
            } else {
                message('error', '没有查询到该商品信息！');
            }
        } else {
            message('error', '查询该商品信息失败！');
        }
    }, "json");

}

//获取总价
function getTotalPrice() {
    var prodTotalPrice = 0;
    var giftTotalPrice = 0;
    $("#tb_detial").find("tr").each(function (index) {
        var td = $(this).find("td");
        var price = td.eq(2).children().eq(0).text();
        var quantity = td.eq(3).html();
        var deliverDays = td.eq(4).html();
        prodTotalPrice += Number(price * quantity * deliverDays);
    });
    var totalPrice = Number(prodTotalPrice + giftTotalPrice);
    $("#spanTotalPrice").html(totalPrice.toFixed(2));
}
//提交信息
function submitData() {

    var orderId = $("#inputOrderid").val();
    var orderDate = $("#inputOrderDate").val();
    var custId = $("#inputCustid").val();
    var custName = $("#inputCustname").val();
    var phone = $("#inputPhone").val();
    var address = $("#inputAddress").val();
    var userid = $("#inputUserid").val();
    var addUserid = $("#inputAddUserid").val();
    var orderType = $("#selectOrderType").val();
    var discountId = $("#discountId").val();

    var orderDetials = new Array();
    $("#tb_detial").find("tr").each(function (index) {

        var td = $(this).find("td");
        var priceid = td.eq(2).children().eq(1).val();
        var quantity = td.eq(3).html();
        var deliverDays = td.eq(4).html();
        var deliverRules = td.eq(5).children().eq(1).val();

        var beginDate = td.eq(6).html();
        if (beginDate.length == 0) {
            beginDate = '0000-00-00';
        }
        var orderDetial = new Object();
        orderDetial.orderId = orderId;
        orderDetial.beginDate = beginDate;
        orderDetial.prodId = td.eq(0).html();
        orderDetial.priceid = priceid;
        orderDetial.quantity = quantity;
        orderDetial.deliverDays = deliverDays;
        orderDetial.deliverRules = deliverRules;
        orderDetial.deliverId = userid;
        orderDetial.prodType = 1;
        orderDetial.areaId = userid.substring(0, 4);
        orderDetial.isGiven = 0;

        orderDetials.push(orderDetial);
    });
    $("#tb_discount").find("tr").each(function (index) {
        var prodType = $(this).find("input").eq(0).val();
        var td = $(this).find("td");
        var orderDetial = new Object();

        if (prodType == 3) {
            orderDetial.orderId = orderId;
            orderDetial.deliverId = userid;
            orderDetial.areaId = userid.substring(0, 4);
            orderDetial.prodType = 3;

            orderDetial.beginDate = td.eq(7).find("input").eq(0).val();
            if (orderDetial.beginDate.length == 0) {
                orderDetial.beginDate = '0000-00-00';
            }

            orderDetial.prodId = td.eq(0).html();
            orderDetial.priceid = td.eq(2).children().eq(1).val();
            orderDetial.quantity = td.eq(4).find("input").eq(0).val();

            orderDetial.deliverDays = td.eq(5).find("input").eq(0).val();
            orderDetial.deliverRules = td.eq(6).find("select").eq(0).val();
            orderDetial.isGiven = 0;
        } else {
            orderDetial.orderId = orderId;
            orderDetial.deliverId = userid;
            orderDetial.areaId = userid.substring(0, 4);
            orderDetial.isGiven = td.eq(5).find("select").eq(0).val();
            orderDetial.prodType = 2;

            orderDetial.beginDate = orderDate;
            if (orderDetial.beginDate.length == 0) {
                orderDetial.beginDate = '0000-00-00';
            }
            orderDetial.prodId = td.eq(0).html();
            orderDetial.priceid = td.eq(2).children().eq(1).val();
            orderDetial.quantity = td.eq(4).find("input").eq(0).val();

        }
        if (orderDetial.quantity != "" && orderDetial.quantity.length > 0) {
            orderDetials.push(orderDetial);
        }
    });
    if (orderDetials.length > 0 && orderId.length > 0 && orderDate.length > 0 && userid.length > 0 && addUserid.length > 0) {
        var data = {
            'orderId': orderId,
            'orderDate': orderDate,
            'custId': custId,
            'custName': custName,
            'custPhone': phone,
            'discountId': discountId,
            'custAddr': address,
            'deliverId': userid,
            'addUserId': addUserid,
            'orderType': orderType,
            'detialList': orderDetials
        }
        console.log(data);
        $.ajax(
            {
                url: "order/add",
                type: "POST",
                data: JSON.stringify(data),
                success: function (data) {
                    if (data.status == "success") {
                        message('success', '添加成功！');
                        loadPage('order/toAdd');
                    } else {
                        message('error', '添加失败！');
                    }
                },
                dataType: "json",
                contentType: "application/json"
            });
    } else {
        message("error", "填写数据不完整！");
    }


}

//校验数据
function checkOrderId() {
    //if ($("#inputOrderid").val().trim().length == 8) {
    //    $("#inputOrderid").parent().parent().removeClass("error");
    //    $("#inputOrderid").next().empty();
    $("#inputCustid").focus();
    //} else {
    //    $("#inputOrderid").next().html("请输入符合规范的订单编号！");
    //    $("#inputOrderid").parent().parent().addClass("error");
    //    $("#inputOrderid").focus();
    //}
}

function checkCustId() {
    var custid = $('#inputCustid').val();
    if (custid.length == 10) {
        $.get("customer/info", {"custId": custid},
            function (data, status) {
                if (status == "success") {
                    if (data.status == "success") {
                        $("#inputCustname").val(data.data.custName);
                        $("#inputPhone").val(data.data.phone);
                        $("#inputAddress").val(data.data.address);

                        $("#inputCustid").parent().parent().removeClass("error");
                        $("#inputCustid").next().empty();
                        $("#productId").focus();
                        //初始化送奶员
                        var userid = custid.substring(0, 6);
                        $("#inputUserid").val(userid);
                        $("#inputAddUserid").val(userid);
                    } else {
                        $("#inputCustid").next().html("没有查询到该用户信息！");
                        $("#inputCustid").parent().parent().addClass("error");
                        $("#inputCustid").focus();
                    }
                }
                else {
                    $("#inputCustid").next().html("查询用户信息请求异常！");
                    $("#inputCustid").parent().parent().addClass("error");
                    $("#inputCustid").focus();
                }
            }, "json"
        );
    } else if (custid.length == 0) {
        $("#inputCustid").parent().parent().removeClass("error");
        $("#inputCustid").next().empty();
        $("#inputCustname").focus();
    } else {
        $("#inputCustid").next().html("所输入的用户编号不正确！");
        $("#inputCustid").parent().parent().addClass("error");
        $("#inputCustid").focus();
    }
}

function checkPhone() {
    var phone = $("#inputPhone").val();

    //var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    //var isMob = /^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
    //if (isMob.test(phone) || isPhone.test(phone)) {
    if (phone.length > 0) {
        $("#inputPhone").parent().parent().removeClass("error");
        $("#inputPhone").next().empty();
        $("#inputAddress").focus();
    }
    else {
        $("#inputPhone").next().html("必须输入电话号码！");
        $("#inputPhone").parent().parent().addClass("error");
        $("#inputPhone").focus();
    }

}