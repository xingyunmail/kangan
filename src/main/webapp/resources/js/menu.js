/**
 * Created by lu.wang on 15/4/29.
 */
function getMenu() {
    $.get(
        "menu/list",
        function (data, status) {
            if (status == "success") {
                if (data.status == "success") {
                    $.each(data.data, function (index, item) {
                        if (item.parentId == 0) {
                            $("#menu_bar").append('<li class="dropdown"><a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">' + item.name + '<i class="caret"></i></a><ul class="dropdown-menu" id="menu_bar_' + item.id + '"></ul></li>');
                        }
                    });
                    $.each(data.data, function (index, item) {
                        if (item.parentId != 0) {
                            $("#menu_bar_" + item.parentId).append('<li><a tabindex="-1" href="javaScript:loadPage(\'' + item.url + '\')">' + item.name + '</a></li>');
                        }
                    });
                }
            } else {
                alert("请求异常！");
            }
        }, "json"
    )
}

function loadPage(url) {
    var div=document.getElementById("dialog-workOrder");
    if(div !=null){
        div.parentNode.removeChild(div);
    }
    clearOldDivs();
    $("#content").load(url);
}


/**
 * create by sunhao 清除由于弹出框导致的DIV遗留
 */
function clearOldDivs()
{
    var divs = new Array()
    divs.push(document.getElementById("stopMilkDiv"));
    divs.push(document.getElementById("stopTimeDiv"));
    divs.push(document.getElementById("activeTimeDiv"));
    divs.push(document.getElementById("updateCustome_one"));
    divs.push(document.getElementById("updateCustome_all"));
    divs.push(document.getElementById("exchangeMilkDiv"));
    divs.push(document.getElementById("exchangeProds"));
    divs.push(document.getElementById("returnMilkDiv"));
    divs.push(document.getElementById("exchangeGiftDiv"));
    divs.push(document.getElementById("selectExchangeGift"));

    divs.push(document.getElementById("activeproductDialog"));
    divs.push(document.getElementById("activeGiftDialog"));
    divs.push(document.getElementById("productPirceDiv"));
    divs.push(document.getElementById("pirceHistoryDiv"));
    divs.push(document.getElementById("updatePirceDiv"));
    divs.push(document.getElementById("BatchupdatePirceDiv"));
    divs.push(document.getElementById("pirceHistoryByTimeOrCustIdDiv"));

    $.each(divs,function(index,div)
    {
        if(div !=null){
            div.parentNode.removeChild(div);
        }
    });

}


function getbuttonlist(){
    var idArray = new Array();
    $.getJSON("competence/buttonlist",function(data){
        if(data.status == "success"){
            $.each(data.data,function(index,item){
                idArray.push(item.competenceId);
            });
            $("#button_list").val(idArray);
        }
    });
}

function buttonJudge(rightArray){
    var idlist = $("#button_list").val();
    var idArray = idlist.split(",");
    for(var j = 0; j < rightArray.length;j++){
        if($.inArray(rightArray[j].num, idArray) < 0){
            $("#"+rightArray[j].id).attr("disabled",true);
        }
    }

}

/**
 * 消息提醒
 * @param type danger/warning/success
 * @param message
 */
function messageBox(name, type, message) {
    $("#" + name).html('<div class="alert alert-' + type + '"><button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>' + message + '</div>').show(300).delay(5000).hide(300);
    setTimeout(function () {
        $("#" + name).empty();
    }, 5000);
}


