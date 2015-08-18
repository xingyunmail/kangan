//检查送奶员是否存在
function checkDeliverId() {
    if($("#deliverId").val()==''){
        $("#deliverId").next().html("不能为空！");
        $("#deliverId").parent().addClass("error");
        return false;
    }
    $.ajax({
        url: "user/unamecheck?userId=" + $("#deliverId").val(),
        type: "GET",
        success: function (data) {
            if (data.status == 'success') {
                $("#deliverId").parent().removeClass("error");
                $("#deliverId").next().empty();
                return true;
            } else {
                $("#deliverId").next().html("不存在！");
                $("#deliverId").parent().addClass("error");
                return false;
            }

        },
        error:function(data){
            return false;
        }
    });
    return true;
}

//检查区域经理是否存在
function checkManagerId() {
    if($("#managerId").val()==''){
        $("#managerId").next().html("不能为空！");
        $("#managerId").parent().addClass("error");
        return false;
    }
    $.ajax({
        url: "user/unamecheck?userId=" + $("#managerId").val(),  //区域经理
        type: "GET",
        success: function (data) {
            if (data.status == 'success') {
                $("#managerId").parent().removeClass("error");
                $("#managerId").next().empty();
                return true;
            } else {
                $("#managerId").next().html("不存在！");
                $("#managerId").parent().addClass("error");
                return false;
            }

        },
        error:function(data){
            return false;
        }
    });
    return true;
}

//检查商品编号是否存在
function checkProdId() {
    if($("#prodId").val()==''){
        $("#prodId").next().html("不能为空！");
        $("#prodId").parent().removeClass().addClass("control-group error");
        return false;
    }
    $.ajax({
        url: "order/getProdById?prodId=" + $("#prodId").val(),
        type: "GET",
        success: function (data) {
            if (data.status == 'success') {
                $("#prodId").parent().removeClass("control-group error");
                $("#prodId").next().empty();
                $("")
                return true;
            } else {
                $("#prodId").next().html("不存在！");
                $("#prodId").parent().removeClass().addClass("control-group error");
                return false;
            }

        },
        error:function(data){
            return false;
        }
    });
    return true;
}

function checkIsNum(id) {
    var val = $("#"+id).val();
    if(val==""){
        $("#" + id).next().html("不能为空！");
        $("#" + id).parent().removeClass().addClass("control-group error");
        return false;
    }
    var reg = new RegExp("^[1-9][0-9]*$");
    if (reg.test(val)) {
        $("#" + id).parent().removeClass("control-group error");
        $("#" + id).next().empty();
        return true;
    } else {
        $("#" + id).next().html("大于0的数字！");
        $("#" + id).parent().removeClass().addClass("control-group error");
        return false;
    }


}


function checkIsDate(){
    var str = $("#beginDate").val().trim();
    var dateArr = str.split("-");
    if(str==""){
        $("#beginDate").parent().next().html("不能为空！");
        $("#beginDate").parent().parent().removeClass().addClass("control-group error");
        return false;
    }else if(dateArr.length==3){
        var paramDate = new Date(parseInt(dateArr[0]),parseInt(dateArr[1])-1,parseInt(dateArr[2]),0,0,0);
        var paramTime = paramDate.getTime();
        var nowDate = new Date();
        var nowTime = nowDate.getTime();
        if(paramTime>nowTime){
            $("#beginDate").parent().parent().removeClass("control-group error");
            $("#beginDate").parent().next().empty();
            return true;
        }else{
            $("#beginDate").parent().next().html("日期要大于今天！");
            $("#beginDate").parent().parent().removeClass().addClass("control-group error");
            return false;
        }

    }else{
        $("#beginDate").parent().next().html("格式不正确！");
        $("#beginDate").parent().removeClass().addClass("control-group error");
        return false;
    }



}
