/**
 * Created by scar on 15/5/5.
 */
function fillZero(number, digits) {
    number = String(number);
    var length = number.length;
    if (number.length < digits) {
        for (var i = 0; i < digits - length; i++) {
            number = "0" + number;
        }
    }
    return number;
}

var formatdate = function (time, format) {
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


function getPagination(count, startNum, limitNum) {


    $("#pageDiv").html("<div id='pages' class='span7'></div><div class='span3 pagination'><label>跳到<input type='text' class='input-mini' id='pageNum' value='1'>页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每页显示<select class='input-mini' id='limitNum'><option value='20' selected='selected'>20</option><option value='50'>50</option><option value='100'>100</option><option value='200'>200</option></select>条</label></div><div class='span2 pagination'><ul><li><a href='javaScript:void(0);' onclick='changePage()'>跳转</a></li></ul></div>");

    var pages = count % limitNum == 0 ? count / limitNum : Math.floor(count / limitNum) + 1;

    //pages sum's view start
    var pageSum = $("<a>");
    pageSum.attr("href", "#");
    pageSum.append("共 ", $("<Strong>").text(pages), " 页");

    var liSum = $("<li>");
    liSum.addClass("disabled");
    liSum.html(pageSum);

    var ulSum = $("<ul>").html(liSum);

    //pages sum's view end

    var activeNum = startNum;
    var limit = limitNum;

    var countPage=0;
    if ((pages - startNum) >= 5) {
        countPage = Number(startNum) + 5;
    } else {
        countPage = pages + 1;
        startNum = (countPage - 5) <= 0 ? 1 : countPage - 5;
    }
    var aBefore = $("<a>");
    if ((activeNum - 1) > 0) {
        aBefore.attr("href", "javascript:gotoPage(" + (activeNum - 1) + "," + limit + ")");//toPages(activeNum - 1));
    }

    aBefore.append($("<Strong>").text("上一页"))
    var liBefore = $("<li>");
    if ((activeNum - 1) <= 0) {
        liBefore.addClass("disabled");
    }
    liBefore.html(aBefore);

    var ulPages = $("<ul>").html(liBefore);

    for (var i = startNum; i < countPage; i++) {
        var liPages = $("<li>");
        if (activeNum == i) {
            liPages.addClass("active");
        }

        var a = $("<a>").text(i);
        a.attr("href", "javascript:gotoPage(" + i + "," + limit + ")");

        ulPages.append(liPages.html(a));//toPages(i)).html(i)));
    }
    var aNext = $("<a>");
    if (activeNum != pages) {
        aNext.attr("href", "javascript:gotoPage(" + (activeNum + 1) + "," + limit + ")");
    }
    aNext.append($("<Strong>").text("下一页"));

    var liNext = $("<li>");
    if (activeNum == pages) {
        liNext.addClass("disabled");
    }
    liNext.html(aNext);

    ulPages.append(liNext);

    var divPage = $("<div>").append(ulSum, ulPages);
    divPage.addClass("pagination pull-right");

    $("#pages").html(divPage);

    $("#pageNum").val(activeNum);
    $("#limitNum").val(limit);

}

function changePage() {
    gotoPage($("#pageNum").val(), $("#limitNum").val());
}