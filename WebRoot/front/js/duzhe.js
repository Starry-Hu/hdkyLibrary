window.onload = function() {
    var $telValue = $('#tel').val();
    if ($telValue == "") {
        alert('不能为空！');
        return;
    }
    $.ajax({
        type: 'GET',
        dataType: 'jsonp',
        jsonp: 'callback',
        jsonpCallback: 'getName',
        url: '',
        data: {
            "text": $telValue
        },
        success: function(data) {
            var reslutData = data;
            console.log(reslutData);
            $('#reslut').html("<a href='#'>" + reslutData.text + "</a>");
        },


    })
}