 /* 管理员相关 */

 /**
  * 获取当前管理员信息
  */
 function getLoginedAdmin() {
     var path = location.protocol + "//" + window.location.host + "/Library/admin/getLoginedAdmin";
     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response);
             if (response.code == 0) {
                 $('#name').text("欢迎您  " + response.data.adminName);
             }
         }
     });
 }


 /**
  * 退出登录
  */
 function Logout() {
     var path = location.protocol + "//" + window.location.host + "/Library/admin/adminLogout";
     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 0表示退出成功
             if (response.code == 0) {
                 alert(response.msg);
                 window.location = "login.html"
             }
         }
     });
 }




 /* 新闻相关 */

 /**
  * 获取select标签里可选的子版块信息 
  * 为新闻选择版块
  */
 function getChildSectionsInSelect() {
     var path = location.protocol + "//" + window.location.host +
         "/Library/section/getAllChildren";
     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 206为查找成功的码
             if (response.code == 206) {
                 var $select = $('#section');
                 // 额外添加一级栏目新闻公告
                 var $option = `<option value="5CC74DC0472C4FBEBAF5CCECD16FA67F">新闻公告</option>`;
                 $select.append($option);
                 
                 for (let i = 0; i < response.data.length; i++) {
                     const element = response.data[i];
                     var $option = `<option value="${element.id}">${element.name}</option>`;
                     $select.append($option);
                 }
             }
         }
     });
 }

 /**
  * 获取原新闻内容 
  * 并渲染到editNews.html
  * 分两种情况讨论：存在与否
  */
 function getOldNewsInfo(id, type) {
     var path;
     if (type == 'id') {
         path = location.protocol + "//" + window.location.host +
             "/Library/news/getNewsById?id=" + searchId;
     }
     if (type == 'sectionId') {
         path = location.protocol + "//" + window.location.host +
             "/Library/news/getAllNewsBySection?sectionId=" + searchId;
         // 如果是通过sectionId过来的  需要进行回显
         $('#section').val(id);
         $('#section').attr('disabled','disabled');  //?需要考虑
     }
     // 判断存在与否
     var exist = true;

     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 206状态码表示查找成功
             if (response.code == 206) {
                 var $baseData;
                 // 分情况获取基本数据
                 if (type == 'id') {
                     $baseData = response.data;
                 }
                 if (type == "sectionId") {
                     $baseData = response.data[0];
                 }

                 // 展示内容
                 $('#nid').val($baseData.id); // 存储id
                 $('#title').val($baseData.title);
                 // 注意是小写
                 $('#section').val($baseData.sectionid);
                 editor.txt.html($baseData.content);

                 // 表示已存在
                 exist = true;
             }
             // 新闻不存在
             if (response.code == 224) {
                 alert("当前新闻不存在，请确保填写或为外部链接");
                 
                 // 返回false  表示不存在
                 exist = false;
             }
         }
     });
     
     return exist;
 }



 /* index页面导航相关 */

 /**
  * 获取所有的section版块信息
  * 来形成树状导航
  */
 function getAllSectionsToTree() {
     var path = location.protocol + "//" + window.location.host + "/Library/section/getAllParents";

     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 206为查找成功的码
             // 返回父版块对象数组
             if (response.code == 206) {
                 tree(response.data);
             }
         }
     });
 }
 
 /**
  * 生成树形导航
  * @param {List} data 
  */
 function tree(data) {
     for (var i = 0; i < data.length; i++) {
         // 一级栏目和其下的二级子栏目
         var parent = data[i];
         var children = parent.childrenSection;

         // 无子栏目情况
         if (children.length == 0) {
             var $li = `<li><span class="children_span"> <a onclick=turnIframe("${parent.id}",2)>${parent.name}</a> </span></li>`;
             $('#rootUL').append($li);
         } else {
             // 有子栏目的情况
             var $li = $('<li></li>');
             var $span = `<span title="关闭">${parent.name}</span>`;
             $li.addClass("parent_li");
             $li.append($span);

             // ul存放二级子栏目
             var $children_ul = $('<ul></ul>');
             for (let j = 0; j < children.length; j++) {
                 const element = children[j];
                 var $children_li = `<li><span class="children_span"><a onclick=turnIframe("${element.id}",0)>${element.name}</a></span></li>`;
                 $children_ul.append($children_li);
             }
             // 加上一个查看全部的按钮
             var $showAll = `<li><span class="btn btn-info children_span" style="border:none"><a onclick=turnIframe("${parent.id}",1)>查看全部</a></span></li>`
             $children_ul.append($showAll);

             $li.append($children_ul);
             $('#rootUL').append($li);
         }
     }
     // 在最后加上 “管理员列表”
     var $li = `<li><span class="children_span"> <a onclick=turnIframe("",3)>管理员列表</a> </span></li>`;
     $('#rootUL').append($li);
 }
 
 /**
  * 
  * 树形导航内的iframe跳转
  * @param {*} sectionId 栏目id
  * @param {0/1} type 跳转种类
  */
 function turnIframe(sectionId, type) {
     var iframePage = $('#iframePage');
     var newSrc;
     // 0为单个页面的展示
     if (type == 0) {
         newSrc = "editNews.html?sectionId=" + sectionId;
     } else if (type == 1) {
         // 1为列表全部展示
         newSrc = "childSectionList.html?sectionId=" + sectionId;
     } else if(type == 2){
    	 // 新闻需要特殊化
    	 // 2为新闻列表全显示
    	 newSrc = "newsList.html?sectionId=" + sectionId;
     } else if(type == 3){
    	 // 查看管理员
    	 newSrc = "adminList.html";
     } else {
         // 不符合要求的返回
         return;
     }
     
     // 其他的移除该样式
 	 $('.children_span').removeClass("focusChild");
 	 // 被点击的添加样式
     $(this).addClass("focusChild");
     // iframe页面跳转
     iframePage.attr('src', newSrc);
 }

 /* 栏目相关 */
 /**
  * 获取所有的父级栏目 
  * 并渲染到下拉框中
  * 以便添加子栏目
  */
 function getAllParentSection() {
     var path = location.protocol + "//" + window.location.host + "/Library/section/getAllParents";

     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 206为查找成功的码
             // 返回父版块对象数组
             if (response.code == 206) {
                 var $wrap = $('#parentId');
                 for (let i = 0; i < response.data.length; i++) {
                     const element = response.data[i];
                     var $option = `<option value="${element.id}">${element.name}</option>`;
                     $wrap.append($option);
                 }
             }
         }
     });
 }

 /**
  * 根据cid获取子栏目信息
  * 渲染到editSection页面
  */
 function getOldSectionInfo(id) {
     // 查找子栏目的id
     var path = location.protocol + "//" + window.location.host +
         "/Library/section/getSectionByCid?cid=" + id;
     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             console.log(response.msg);
             // 206为查找成功的码
             if (response.code == 206) {
                 $('#sname').val(response.data.name);
                 $('#address').val(response.data.address);
                 $("input[name='level']").val(response.data.level).attr('checked','checked');
                 if($("input[name='level']").val() == 2){
                	 $('#pid').css('display','inline-block');
                 }
                 $('#parentId').val(response.data.parentid);
             }
         }
     });
 }

 /* 管理员相关 */
 /**
  * 根据id获取管理员信息
  * 渲染到editAdmin页面中
  * @param {*} id 
  */
 function getOldAdminInfo(id) {
	 var path = location.protocol + "//" + window.location.host +
     "/Library/admin/getAdminById?id=" + id;
     $.ajax({
         type: "get",
         url: path,
         async: false,
         dataType: "json",
         success: function(response) {
             // 106为查找成功
             if (response.code == 106) {
            	 $('#aid').val(response.data.id); // uuid
                 $('#adminId').val(response.data.adminid);
                 $('#adminName').val(response.data.adminname);
                 // 权限的显示  暂空

             }
         }
     });
 }