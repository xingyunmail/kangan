var valSelected="";
function multiSelect(id){
    $("#"+id+"").multiselect({
        header: false,
        noneSelectedText: "--请选择--",
        minWidth: 200,
        selectedList: 6,
        //hide: ["explode", 500],
        close: function(){
            valSelected =$("#"+id+"").val()+"";
            if(valSelected == "null"){
                valSelected="";
            }
        }
    });
}

function selArea(id) {
    $.get('area/getAreaList', function (data) {
        if (data.status == "success") {
            $.each(data.data, function (i, result) {
                $("#"+id+"").append("<option value='" + result.areaId + "'>" + result.areaName + "</option>")
            });
            multiSelect(id);
        }
    }, 'json');

}

function selVal(){
    return  valSelected;
}