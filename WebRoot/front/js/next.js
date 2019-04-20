$(function() {

    // 设置首页a标签
    var home = location.protocol + "//" + window.location.host + "/Library/front/index.html";
    $('#homePage').attr('href', home);

    // 设置新闻列表a地址
    var lista = location.protocol + "//" + window.location.host + "/Library/front/more.html";
    $('#newsList').attr('href', lista);

    // 载入相应内容
    var str = window.location.href;

    var index1 = str.indexOf('?') + 1;
    var index2 = str.indexOf('=');
    // 获取查找的种类值
    var type = str.substring(index1, index2);
    // 后台接口
    var path;
    // 查找的id
    var searchId = str.substr(str.indexOf('=') + 1);
    
    // 如果是新闻的  则通过id直接获取
    // 如果是二级版块的  则通过版块取数组的第一条
    if (type == 'id') {
        path = location.protocol + "//" + window.location.host +
            "/Library/news/getNewsById?id=" + searchId;
    }
    if (type == 'sectionId') {
        path = location.protocol + "//" + window.location.host +
            "/Library/news/getAllNewsBySection?sectionId=" + searchId;
    }


    $.ajax({
        type: "get",
        url: path,
        dataType: "json",
        success: function(response) {
            console.log(response.msg);
            // 206状态码表示查找成功
            if (response.code == 206) {

                var $wrap = $('#mainContent');
                var $baseData;
                // 分情况获取基本数据
                if (type == 'id') {
                    $baseData = response.data;
                }
                if (type == "sectionId") {
                    $baseData = response.data[0];
                }

                // 展示内容
                var $content = $baseData.content;
                $wrap.html($content);
                // 修改标题
                var $title = $('title');
                $title.text($baseData.title);
            }
        }
    });
})