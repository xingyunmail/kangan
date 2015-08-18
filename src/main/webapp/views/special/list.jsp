<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div id="alert"> </div>
<div class="block">
    <div class="navbar navbar-inner block-header">
        <div class="muted pull-left">特殊客户价格设置</div>
    </div>

    <div class="block-content collapse in">
        <div class="well well-small  row span12">
            <div class="span3">
                客户编号：
                <input type="text" class="input-medium" id="customerId" value=""/>
            </div>
            <div class="span5">
                生效时间：
                <div class="input-append date" id="startDatedatetimepicker"
                     data-date-format="yyyy-mm-dd hh:ii">
                    <input size="16" type="text" class="input-small" id="startDate" style="width: 140px;">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
                <!--
                <div class="input-append date" id="endDatedatetimepicker"
                     data-date-format="yyyy-mm-dd hh:ii">
                    <input size="16" type="text" class="input-small" id="endDate" style="width: 140px;">
                    <span class="add-on"><i class="icon-th"></i></span>
                </div>
        -->
            </div>
            <button class="btn" id="submit">查询</button>

        </div>

        <span>&nbsp</span>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td><input type="checkbox" onclick="selectAll(this,'chkItem')"></td>
                <td>客户编号</td>
                <td>最后生效日期</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="customerList">

            </tbody>

        </table>
        <%-- 暂缓开发 <button class="btn" id="batchOpter">批量操作</button>--%>
    </div>
</div>

<!--查看价格弹出层-->
<div id="productPirceDiv" style="width:680px;display:none">
    <input value="" type="hidden" name="custId" id="custID">
    <input value="" type="hidden" name="custId" id="sDate">
    <span style="margin-left: 10px">
        <button class="btn btn-primary" style="margin: 10px;" value="" onclick="closeDivs('productPirceDiv')">返回
        </button>
        <button class="btn btn-primary" style="margin: 10px;" id="updatePrice" value="" onclick='javascript:goUpdatePrice()'>
            修改
        </button>
        </span>

    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>商品</td>
                <td>价格</td>
            </tr>
            </thead>
            <tbody id="productPirceList">
            </tbody>
        </table>
    </div>
    <div>
    </div>
</div>
<!--查看记录弹出层-->
<div id="pirceHistoryDiv" style="width:680px;display:none">
    <span style="margin-left: 10px">
        <button class="btn btn-primary" style="margin: 10px;" value="" onclick="closeDivs('pirceHistoryDiv')">返回
        </button>

        </span>

    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>使用时间</td>
                <td>修改时间</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody id="priceHistoryList">
            </tbody>
        </table>
    </div>
    <div>
    </div>
</div>
<!--修改价格弹出层-->
<div id="updatePirceDiv" style="width:680px;display:none">
    <span style="margin-left: 10px">
        生效时间:<div class="input-append date" id="updatePriceDate"
                  data-date-format="yyyy-mm-dd hh:ii:ss">
        <input size="16" type="text" class="input-medium" id="productPriceUpate">
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
        <button class="btn btn-primary" style="margin: 10px;" id="updatePrice1" value="" onclick="savePriceLog()">
            保存
        </button>
        <button class="btn btn-primary" style="margin: 10px;" value="" onclick="closeDivs('updatePirceDiv')">取消
        </button>
        <br>
        <button class="btn btn-primary" style="margin: 10px;" id="findHistory1" value="" onclick="javascript:AdjustmentPrice(2)">
            调整至正常价格
        </button>
        所有商品 + <input type="text" value="0" class="input-medium input-small" id="AdjustmentPriceID"/>
        <button class="btn btn-primary" style="margin: 10px;" id="findHistory2" value="" onclick="AdjustmentPrice(1)">
            调整
        </button>
        <input type="hidden" value="" id="beginDate"/>
        <input type="hidden" value="" id="customer"/>
        </span>

    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>商品</td>
                <td>价格</td>
            </tr>
            </thead>
            <tbody id="updatePirceDivList">

            </tbody>
        </table>
    </div>
    <div>
    </div>
</div>


<!--批量修改价格弹出层-->
<div id="BatchupdatePirceDiv" style="width:680px;display:none;">
    <span style="margin-left: 10px">
        生效时间:<div class="input-append date" id="BatchupdatePriceDate"
                  data-date-format="yyyy-mm-dd hh:ii:ss">
        <input size="16" type="text" class="input-medium" id="BatchproductPriceUpate">
        <span class="add-on"><i class="icon-th"></i></span>
    </div>
        <button class="btn btn-primary" style="margin: 10px;" id="BatchupdatePrice1" value="" onclick="batchSave()">
            保存
        </button>
        <button class="btn btn-primary" style="margin: 10px;" value="" onclick="closeDivs('BatchupdatePirceDiv')">取消
        </button>
        <br>
        所有商品 + <input type="text" value="0" class="input-medium input-small" id="BatchAdjustmentPriceID"/>
        <button class="btn btn-primary" style="margin: 10px;" id="BatchfindHistory2" value="" onclick="batchAdjustmentPrice(1)">
            调整
        </button>
        </span>
<input type="text" value="" id="BatchPriceID">
    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>商品</td>
                <td>价格</td>
            </tr>
            </thead>
            <tbody id="BatchupdatePirceDivList">

            </tbody>
        </table>
    </div>
    <div>
    </div>
</div>



<!--查看商品价格修改记录弹出层-->
<div id="pirceHistoryByTimeOrCustIdDiv" style="width:680px;display:none">
    <span style="margin-left: 10px">
        <button class="btn btn-primary" style="margin: 10px;" value="" onclick="closeDivs('pirceHistoryByTimeOrCustIdDiv')">返回
        </button>

        </span>

    <div class="modal-body">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <td>商品名称</td>
                <td>价格</td>
            </tr>
            </thead>
            <tbody id="pirceHistoryByTimeOrCustIdList">
            </tbody>
        </table>

    </div>
    <div>

    </div>
</div>
<span class="span12" id="pageDiv"></span>
<link href="resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="resources/js/util.js"></script>
<script src="resources/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="resources/css/ui-dialog.css">
<script src="resources/js/dialog-min.js"></script>
<script src="resources/js/special.js"></script>


<script type="application/javascript">

</script>