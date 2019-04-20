// 生成右下角更多服务信息
function getMoreService () {
	// 显示我馆简介和华电微图
        $.ajax({
            type: "get",
			async: false,
            url: location.protocol + "//" + window.location.host +
                "/Library/section/getSectionByCid?cid=43E925DC9F5649C29CB174667EA60EE3",
            dataType: "json",
            success: function(response) {
                var $moreServiceWrap = $('#moreServiceWrap');
				// 添加我馆简介
                var $div = `<div class="nettwo" style="margin-left:0"><a href="${response.data.address}?sectionId=${response.data.id}" target="_blank">
        <img src="image/intro.png" /><p>${response.data.name}</p></a></div>`;
                    $moreServiceWrap.append($div);

				// 添加二维码
				var $div2 = `<div class="nettwo"><a href="http://lib.ncepu.edu.cn/front/tDODetail.aspx?type=phone&id=59" target="_blank">
<img src="image/hdwt.jpg" /><p>华电微图</p></a></div>`;
                    $moreServiceWrap.append($div2);
                }
        });

	// 显示各室电话
	$.ajax({
            type: "get",
            url: location.protocol + "//" + window.location.host +
                "/Library/section/getSectionByCid?cid=4E58B63DD27A49FA8031C7C4FACADF2C",
            dataType: "json",
            success: function(response) {
                var $moreServiceWrap = $('#moreServiceWrap');
				// 添加我馆简介
                var $div = `<div class="nettwo"><a href="${response.data.address}?sectionId=${response.data.id}" target="_blank">
        <img src="image/phone.png" /><p>${response.data.name}</p></a></div>`;
                    $moreServiceWrap.append($div);
                }
        });
}


// 生成右下角网络服务信息
function getNetService () {
        $.ajax({
            type: "get",
            url: location.protocol + "//" + window.location.host +
                "/Library/section/getAllChildrenByPid?pid=A5EB59D1CAD44311806EF4DCE64AAF07",
            dataType: "json",
            success: function(response) {
                var $netServiceWrap = $('#netServiceWrap');
                for (let i = 0; i < response.data.length; i++) {
                    const child = response.data[i];

                    // 分情况赋不同的图片
                    if (i % 3 == 0) {
                        var $div = `<div class="nettwo" style="margin-left:0"><a href="${child.address}?sectionId=${child.id}" target="_blank">
        <img src="image/rule.png" /><p>${child.name}</p></a></div>`;
                    } else if (i % 3 == 1) {
                        var $div = `<div class="nettwo"><a href="${child.address}?sectionId=${child.id}" target="_blank">
        <img src="image/ipdownload.png" /><p>${child.name}</p></a></div>`;
                    } else if (i % 3 == 2) {
                        var $div = `<div class="nettwo"><a href="${child.address}?sectionId=${child.id}" target="_blank">
        <img src="image/tuoputu.png" /><p class="tuoputu">${child.name}</p></a></div>`;
                    }

                    $netServiceWrap.append($div);

                }
            }
        });
}


// 生成左下角全部板块信息
function getAllItems() {
        var $wrap = $('#tab');
        // 获取所有的版块信息
        $.ajax({
            type: "get",
            url: location.protocol + "//" + window.location.host + "/Library/section/getAllParents",
            dataType: "json",
            async: false, //防止切换js不执行
            success: function(response) {
                // 206为查找成功码
                if (response.code == 206) {
                    var parentsList = response.data;
                    for (let i = 0; i < parentsList.length; i++) {
                        // 获取每一个父项目
                        const parent = parentsList[i];

                        // 跳过新闻公告和网络服务的显示
                        if (parent.name == '新闻公告' || parent.name == '网络服务') {
                            continue;
                        }
                        // 显示父项目名称
                        var $dt = `<dt> ${parent.name} </dt>`;
                        $wrap.append($dt);

                        // 判断该父项目下是否有子项目
                        if (parent.childrenSection.length > 0) {
                            var $dd = $('<dd></dd>');
                            // 如果取余为0 则加一取余  得对应的样式
                            if (i % 5 == 0) {
                                $dd.addClass(`img_${(i+1)%5}`);
                            } else {
                                $dd.addClass(`img_${i%5}`);
                            }

                            var $li = $("<li></li>");
                            // 子项目的循环展示
                            for (let j = 0; j < parent.childrenSection.length; j++) {
                                const child = parent.childrenSection[j];
                                var $a =
                                    `<a href = "${child.address}?sectionId=${child.id}" target="_blank"> 
                                                        <div id="ff" width="120" height="30"> ${child.name} </div> </a>`;
                                $li.append($a);
                            }
                            $dd.append($li);

                            $wrap.append($dd);
                        }
                    }
                }
            }
        });
}

// 生成前十条首页新闻列表
function getTopNews() {
	 $.ajax({
            type: "get",
            // 并上新闻的sectionId  默认取前十条数据
            url: location.protocol + "//" + window.location.host +
                "/Library/news/getNewsBySectionWithPage?pageNum=1&pageSize=10&sectionId=5CC74DC0472C4FBEBAF5CCECD16FA67F",
            dataType: "json",
            success: function(response) {
                // 206为查询成功的码
                if (response.code == 206) {
                    var ulSource = $('#ulSource');
                    var list = response.data.list;
                    for (let index = 0; index < list.length; index++) {
                        // 循环建立li标签
                        const element = list[index];
						// 获得月开始的下标
						var monthIndex = element.createTimeString.indexOf("-") + 1;
						var createTime = element.createTimeString.substr(monthIndex,5);
						// 获取标题，并控制长度
						var title = element.title;
                        if (title.length > 15) {
                            title = title.substr(0,15) + "...";
                        }
                        var $li = `<li> <a href="next.html?id=${element.id}" target="_blank"> ${title} <span> [ ${createTime} ]</span></li>`;
                        ulSource.append($li);
                    }
                }
            }
        });
}