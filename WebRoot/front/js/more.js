$(function() {
        // 设置首页a标签
        var home = location.protocol + "//" + window.location.host + "/Library/front/index.html";
        $('#homePage').attr('href', home);

        // 新闻所在的sectionId
        var sectionId = '5CC74DC0472C4FBEBAF5CCECD16FA67F';
        var totalPage = getNewsByPage(1, 15, sectionId);
        $('#pageDemo').pagination({
            pageCount: totalPage,
            jump: true,
             callback: function(api) {
                 getNewsByPage(api.getCurrent, 15, sectionId);
             }
        });
    })
    
    
    
/**
     * 获取当前新闻版块的全部新闻信息
     * 用于分页新闻版块获取全部新闻信息
     */
    function getNewsByPage(pageNum, pageSize, sectionId) {
        var path = location.protocol + "//" + window.location.host + "/Library/news/getNewsBySectionWithPage";
        // 数目总页数
        var totalPage = 0;
        $.ajax({
            type: "get",
            url: path,
            data: {
                sectionId: sectionId,
                pageNum: pageNum,
                pageSize: pageSize,
            },
            async:false,
            dataType: "json",
            success: function(response) {
                // 206为查找成功的码
                if (response.code == 206) {
                    totalPage = response.data.totalPage;
                    var $tbody = $('#tbody');
                    
                    // 清空原tbody
                    $tbody.html('');
                    for (let i = 0; i < response.data.list.length; i++) {
                        const element = response.data.list[i];

                        // 获取每条新闻的信息
                        var $td1 = `<td>${i + 1}</td>`; //序号
                        var $td2 = `<td>${element.title}</td>`;
                        var $td3 = `<td>${element.createTimeString}</td>`; // 发布时间

                        var $tr = $('<tr></tr>');
                        $tr.bind("click", function() {
                            $('this').css('background-color', '#dee2e6');
                            var newSrc = `next.html?id=${element.id}`;
                            window.open(newSrc);
                        });
                        $tr.append($td1 + $td2 + $td3);
                        $tbody.append($tr);
                    }
                }
            }
        });
        return totalPage;
    }