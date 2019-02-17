$(function() {

    var json = [{
        "name": "新闻公告",
        "code": "ZKCHQ",
        "icon": "icon-th",
        "child": []
    }, {
        "name": "读者服务",
        "code": "ZKCH",
        "icon": "icon-th",
        "child": [{
                "name": "我馆简介",
                "icon": "",
                "code": "BJZKCH",
                "parentCode": "ZKCH",
                "child": []
            },
            {
                "name": "二级目录",
                "icon": "",
                "code": "BJZKCH",
                "parentCode": "ZKCH",
                "child": []

            }, {
                "name": "大家一起来读书",
                "code": "GZZKK",
                "icon": "",
                "parentCode": "ZKKJ",
                "child": []
            },
            {
                "name": "读书节",
                "code": "GZZK",
                "icon": "",
                "parentCode": "ZKKJ",
                "child": []
            }, {
                "name": "读者协会",
                "code": "GZZ",
                "icon": "",
                "parentCode": "ZKKJ",
                "child": []
            }
        ]
    }, {
        "name": "新生专栏",
        "code": "ZKC",
        "icon": "icon-th",
        "child": [{
                "name": "二级目录",
                "icon": "",
                "code": "GZZKC",
                "parentCode": "ZKC",
                "child": []
            },
            {
                "name": "二级目录",
                "icon": "",
                "code": "BJZKC",
                "parentCode": "ZKC",
                "child": []
            }
        ]
    }, {
        "name": "读者服务",
        "code": "ZKCHP",
        "icon": "icon-th",
        "child": [{
                "name": "随书光盘",
                "icon": "",
                "code": "BJZKCHP",
                "parentCode": "ZKCHP",
                "child": []
            }, {
                "name": "光盘检索",
                "icon": "",
                "code": "BJZKCHP",
                "parentCode": "ZKCHP",
                "child": []
            }, {
                "name": "下载规定",
                "icon": "",
                "code": "BJZKCHP",
                "parentCode": "ZKCHP",
                "child": []
            },
            {
                "name": "电子图书",
                "icon": "",
                "code": "BJZK",
                "parentCode": "ZKCHP",
                "child": []
            },
            {
                "name": "数据库导航",
                "icon": "",
                "code": "BJZKCHP",
                "parentCode": "ZKCHP",
                "child": []

            }, {
                "name": "电子期刊",
                "icon": "",
                "code": "BJZKCHP",
                "parentCode": "ZKCHP",
                "child": []
            }
        ]
    }];


    function tree(data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].icon == "icon-th") {
                $("#rootUL").append("<li data-id='" + data[i].id + "'><span>" + data[i].name + "</span></li>");
            } else {
                var children = $("li[data-name='" + data[i].parentCode + "']").children("ul");
                if (children.length == 0) {
                    $("li[data-name='" + data[i].parentCode + "']").append("<ul></ul>")
                }
                $("li[data-name='" + data[i].parentCode + "'] > ul").append(
                    "<li data-name='" + data[i].code + "'>" +
                    "<span>" +
                    "<i class='" + data[i].icon + "'></i> " +
                    data[i].name +
                    "</span>" +
                    "</li>")
            }
            for (var j = 0; j < data[i].child.length; j++) {
                var child = data[i].child[j];
                var children = $("li[data-name='" + child.parentCode + "']").children("ul");
                if (children.length == 0) {
                    $("li[data-name='" + child.parentCode + "']").append("<ul></ul>")

                }
                $("li[data-name='" + child.parentCode + "'] > ul").append(
                    "<li data-name='" + child.code + "'>" +
                    "<span>" +
                    "<i class='" + child.icon + "'></i> " +
                    "<a href='http://www.w3school.com.cn/jquery/manipulation_append.asp'> " + child.name + "</a>" +
                    "</span>" +
                    "</li>")
                var child2 = data[i].child[j].child;
                tree(child2)
            }
            tree(data[i]);
        }

    }

    tree(json)


});