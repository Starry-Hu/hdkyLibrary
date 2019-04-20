window.onload = function () {
    var TabbedContent = {
        init: function () {
            $("#tabNav span").hover(function () {
                var background = $(this).parent().find(".moving_bg");
                $(background).stop().animate({
                    left: $(this).position()['left']
                }, {
                    duration: 300
                });
                TabbedContent.slideContent($(this));
                $(this).siblings().removeClass("rb");
                $(this).addClass("rb");

            }, function () {
                $(this).siblings().removeClass("rb");

            });
        },
        slideContent: function (obj) {
            var margin = $(obj).parent().parent().find(".slide_content").width();
            margin = margin * ($(obj).prevAll().length - 1);
            margin = margin * -1;
            $(obj).parent().parent().find(".tabslider").stop().animate({
                marginLeft: margin + "px"
            }, {
                duration: 300
            });
        }
    };


    function isPlaceholer() {
        var input = document.createElement('input');
        return "placeholder" in input;
    }

    function getStyle(obj, attr) {
        if (obj.currentStyle) {
            return obj.currentStyle[attr];
        } else {
            return getComputedStyle(obj, false)[attr];
        }
    }

    function getByClass(tagName, sClss) {
        var aResult = [];
        var aEle = document.getElementsByTagName(tagName);
        for (var i = 0; i < aEle.length; i++) {
            if (aEle[i].className == sClss) {
                aResult.push(aEle[i]);
            }
        }
        return aResult;
    }

    function searchContentDrop() {
        var aUl = getByClass("ul", "search-content-drop");

        var iLiHeight = getStyle(aUl[0], "height");
        var oButtonHidden = document.getElementById("strSearchType");

        var aInput = getByClass("input", "searchContent");

        aUl[0].onmouseover = function () {
            var aLi = this.getElementsByTagName("li");
            var sumHeight = aLi.length * parseInt(iLiHeight) + 15 + "px";
            this.style.height = sumHeight;
            for (var i = 0; i < aLi.length; i++) {
                aLi[i].onmouseover = function () {
                    this.style.background = "#ccc";
                    this.style.color = "#fff";
                };
                aLi[i].onmouseout = function () {
                    this.style.background = "";
                    this.style.color = "#828282";
                };
                aLi[i].onclick = function () {
                    this.style.background = "";
                    this.style.color = "#828282";
                    this.parentNode.insertBefore(this, this.parentNode.children[0]);
                    this.parentNode.style.height = iLiHeight;
                    oButtonHidden.value = this.id;
                    if (!isPlaceholer()) {
                        aInput[1].value = aInput[1].placeholder;
                    }
                }
            }
            ;
        }
        aUl[0].onmouseout = function () {
            this.style.height = iLiHeight;
        }
    }

    function searchContentChange() {
        var oForm2 = document.getElementById("integrate");
        var oDiv = getByClass('div', 'radio-group')[0];
        var aInput = oDiv.getElementsByTagName('input');
        var aInputText = getByClass("input", "searchContent");
        var aP1Explanation = getByClass("div", "explanation")[1].getElementsByTagName("p")[0];
        var oDivHiddenBtnGroup = document.getElementById("hidden-btn-group");
        for (var i = 0; i < aInput.length; i++) {
            aInput[i].index = i;
            // alert(oForm2.innerHTML);
            aInput[i].onclick = function () {
                switch (this.index) {
                    case 0:
                        oForm2.action = "http://www.blyun.com/gosearch.jsp";
                        aInputText[1].placeholder = "百链原文索取检索词…";
                        aInputText[1].name = "sw";
                        aP1Explanation.innerHTML = "说明：<a href='http://www.blyun.com/' target='_blank'>百链</a>提供一站式外文搜索，该平台集成了包括Springer Link等上百个数据库的综合检索，可自行文献传递。";
                        oDivHiddenBtnGroup.innerHTML = '<input id="ecode" type="hidden" name="ecode" value="utf-8"><input id="channel" type="hidden" name="channel" value="0">';
                        break;
                    case 1:
                        oForm2.action = "http://eng.bjtech.superlib.net/gosearch.jsp";
                        aInputText[1].placeholder = "高科联盟检索词…";
                        aInputText[1].name = "sw";
                        aP1Explanation.innerHTML = "说明：<a href='http://eng.bjtech.superlib.net/guide?null' target='_blank'>高科联盟</a>是北京11所高校图书馆馆藏目录导航、馆藏电子文献共享共知及文献传递服务系统，可自行文献传递。";
                        oDivHiddenBtnGroup.innerHTML = '<input id="ecode" type="hidden" name="ecode" value="GBK"><input id="channel" type="hidden" name="channel" value="1">';
                        break;
                    case 2:
                        oForm2.action = "http://ccc.calis.edu.cn/result.php";
                        aInputText[1].placeholder = "外文期刊网检索词…";
                        aInputText[1].name = "at";
                        aP1Explanation.innerHTML = "说明：<a href='http://ccc.calis.edu.cn/index.php' target='_blank'>外文期刊网</a>是中国高校文献保障系统（CALIS）提供的全面揭示国内高校纸本期刊和电子期刊的综合服务平台，可提供文献传递。";
                        oDivHiddenBtnGroup.innerHTML = '<input id="ecode" type="hidden" name="ecode" value="GBK"><input id="channel" type="hidden" name="channel" value="2">';
                        break;
                }
                if (!isPlaceholer()) {
                    aInputText[1].value = aInputText[1].placeholder;
                }
            }
        }
        ;
    }
    
    $(function () {
    	 //获取新闻信息
		getTopNews();
		// 获取左下角items信息
		getAllItems();
		// 获取网络服务信息
		getNetService();
		// 获取更多服务信息
		getMoreService();
		
		
    	// 初始化搜索
        TabbedContent.init();
        searchContentDrop();
        searchContentChange();

        $("#searchButtonContent").click(function () {
            var checkValue = $("#strSearchType").val();
            var searchcontent = $("#strText").val();
            window.location.href = 'http://202.206.214.136:8080/opac/openlink.php?strText=' + searchcontent + '&strSearchType=' + checkValue;
        });

        $("#intSearch").click(function () {
            var searchcontent = $("#intSearchKey").val();
            var channel = $("#channel").val();

            switch (channel) {
                case '0':
                    window.location.href = 'http://www.blyun.com/gosearch.jsp';
                    break;
                case '1':
                    window.location.href = 'http://eng.bjtech.superlib.net/gosearch.jsp';
                    break;
                case '2':
                    window.location.href = 'http://ccc.calis.edu.cn/result.php';
                    break;
            }
        });

        $("#siteSearch").click(function () {
            var searchcontent = $("#siteSearchKey").val();
            window.location.href = 'http://lib.ncepu.edu.cn/front/sASearch.aspx?keyword=' + searchcontent;
        });


    });


    $(function () {
    	 try {
            function id(elem) {
                return document.getElementById(elem)
            }

            function show(elem) {
                elem.style.display = "";
            }

            function hide(elem) {
                elem.style.display = "none";
            }

            function next(elem) {
                do {
                    elem = elem.nextSibling;
                } while (elem && elem.nodeType != 1);
                return elem;
            }

            function tab(a, p) {
                var p = (p === undefined) ? {
                        e: "onclick",
                        n: 1
                    } : p,
                    node = id(a).firstChild;
                x = [];
                p.e = (p.e === undefined) ? "onclick" : p.e;
                p.n = (p.n === undefined) ? 1 : p.n;
                node = (node.nodeType !== 1) ? next(node) : node;
                for (var i = 1, node; node; i++, node = next(node)) {
                    x[i] = node;
                }
                for (var i = 1; x[i]; i++) {
                    if (i % 2 == 0) {
                        hide(x[i]);
                        x[i - 1].id = a + (i / 2)
                    }
                    x[p.n * 2 - 1].className = "cur";
                    show(x[p.n * 2]);
                    temp = function(i) {
                        if (i % 2 == 1) {
                            x[i][p.e] = function() {
                                for (var j = 1; x[j]; j++) {
                                    if (j % 2 == 0) {
                                        hide(x[j]);
                                        x[j - 1].className = ""
                                    }
                                }
                                show(x[i + 1]);
                                x[i].className = "cur"
                            }
                        } else {
                            return null
                        }
                    }(i)
                }
            }

            tab("tab", {
                e: "onmouseover",
                n: 1
            });

        } catch (Exception) {


        }
    })
    
    $(function () {
        var lxfscroll_ul = $(".lxfscroll ul");
        var lxfscroll_li = $(".lxfscroll li");
        var lxfscroll_tli = $(".lxfscroll-title li");
        var lxfscroll_speed = 350;//切换的速度
        var lxfscroll_autospeed = 3000;//自动播放的速度
        var lxfscroll_n = 0;
        var lxfscroll_imgheight = $(".lxfscroll li img").attr("height");//获取图片高度
        var lxfscroll_lilength = lxfscroll_li.length;//获取图片数量
        var lxfscroll_timer;
        var lxfscroll_alt = $(".lxfscroll-alt");

        /* 标题按钮事件 */
        function lxfscroll() {
            lxfscroll_tli.mouseenter(function () {
                var lxfscroll_index = lxfscroll_tli.index($(this));
                var lxfscroll_lipoint = lxfscroll_index * lxfscroll_imgheight;
                var lxfscroll_imgTitle = $(".lxfscroll li img").eq(lxfscroll_index).attr("alt");
                lxfscroll_alt.text(lxfscroll_imgTitle);
                lxfscroll_tli.removeClass("cur");
                $(this).addClass("cur");
                lxfscroll_ul.stop(true, false).animate({"top": -lxfscroll_lipoint + "px"}, lxfscroll_speed);
            });
        }

        /* 自动轮换 */
        function lxfscroll_autoroll() {
            if (lxfscroll_n >= lxfscroll_lilength) {
                lxfscroll_n = 0;
            }
            var lxfscroll_lipoint = lxfscroll_n * lxfscroll_imgheight;
            var lxfscroll_imgTitle = $(".lxfscroll li img").eq(lxfscroll_n).attr("alt");
            lxfscroll_ul.stop(true, false).animate({"top": -lxfscroll_lipoint + "px"}, lxfscroll_speed);
            lxfscroll_tli.removeClass("cur").eq(lxfscroll_n).addClass("cur");
            lxfscroll_alt.text(lxfscroll_imgTitle);
            lxfscroll_n++;
            lxfscroll_timer = setTimeout(lxfscroll_autoroll, lxfscroll_autospeed);
        }

        /* 鼠标悬停即停止自动轮换 */
        function lxfscroll_stoproll() {
            lxfscroll_li.hover(function () {
                clearTimeout(lxfscroll_timer);
                lxfscroll_n = $(this).prevAll().length + 1;
            }, function () {
                lxfscroll_timer = setTimeout(lxfscroll_autoroll, lxfscroll_autospeed);
            });
            lxfscroll_tli.hover(function () {
                clearTimeout(lxfscroll_timer);
                lxfscroll_n = $(this).prevAll().length + 1;
            }, function () {
                lxfscroll_timer = setTimeout(lxfscroll_autoroll, lxfscroll_autospeed);
            });
        }

        lxfscroll();
        lxfscroll_autoroll();
        lxfscroll_stoproll();//启动自动播放功能
    });
}