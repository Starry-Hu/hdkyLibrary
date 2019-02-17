/* 新闻类别 */

// 检测是否填写标题
function checkTitle() {
    var title = $('#title').val();
    var tips = $('#title').siblings('span');
    if (title == '') {
        $('#title').addClass('input-error');
        tips.css('display', 'inline');
        return false;
    } else {
        $('#title').removeClass('input-error');
        tips.css('display', 'none');
        return true;
    }
}
//检查是否选择版块
function checkSection() {
    var section = $('#section').val();
    var tips = $('#section').siblings('span');
    if (section == "none") {
        $('#section').addClass('input-error');
        tips.css('display', 'inline');
        return false;
    } else {
        $('#section').removeClass('input-error');
        tips.css('display', 'none');
        return true;
    }
}

/* 栏目类别 */

// 检查栏目名称是否填写
function checkName() {
    var sname = $('#sname').val();
    var tips = $('#sname').siblings('span');
    if (sname == '') {
        $('#sname').addClass('input-error');
        tips.css('display', 'inline');
        return false;
    } else {
        $('#sname').removeClass('input-error');
        tips.css('display', 'none');
        return true;
    }
}

// 检查地址是否填写
function checkAddress() {
    var address = $('#address').val();
    var tips = $('#address').siblings('span');
    if (address == '') {
        $('#address').addClass('input-error');
        tips.css('display', 'inline');
        return false;
    } else {
        $('#address').removeClass('input-error');
        tips.css('display', 'none');
        return true;
    }
}

// 检查等级是否填写  并在二级时展示父栏目版块
function checkLevel() {
    var level = $("input[name='level']:checked").val();
    // 二级时展示父栏目选项
    if (level == 2) {
        $('#pid').css('display', 'inline');
    } else if (level == 1) {
        // 一级时收起父栏目选项
        $('#pid').css('display', 'none');
    }
    return true;
}

// 检查 当版块为二级时是否选择了父栏目
function checkParent() {
    var level = $("input[name='level']:checked").val();
    // 1级栏目时不需要填写
    if (level == 1) {
        return true;
    }
    var parentId = $('#parentId').val();
    var tips = $('#parentId').siblings('span');
    if (parentId == "none") {
        $('#parentId').addClass('input-error');
        tips.css('display', 'inline');
        return false;
    } else {
        $('#parentId').removeClass('input-error');
        tips.css('display', 'none');
        return true;
    }
}


/* 管理员类别 */

//检查管理员账号
function checkAdminId() {
 var adminId = $('#adminId').val();
 var tips = $('#adminId').siblings('span');
 if (adminId == '') {
     $('#adminId').addClass('input-error');
     tips.css('display', 'inline');
     return false;
 } else {
     $('#adminId').removeClass('input-error');
     tips.css('display', 'none');
     return true;
 }
}

//检查管理员名称
function checkAdminName() {
 var adminName = $('#adminName').val();
 var tips = $('#adminName').siblings('span');
 if (adminName == '') {
     $('#adminName').addClass('input-error');
     tips.css('display', 'inline');
     return false;
 } else {
     $('#adminName').removeClass('input-error');
     tips.css('display', 'none');
     return true;
 }
}

//检查密码
function checkPsw() {
 var password = $('#password').val();
 var password2 = $('#password2').val();
 var tips = $('#password').siblings('span');
 var tips2 = $('#password2').siblings('span');

 if (password != '' && password2 != '') {
     $('#password').removeClass('input-error');
     $('#password2').removeClass('input-error');
     tips.css('display', 'none');
     tips2.css('display', 'none');
     return true;
 } else {
     // 第一次未填写
     if (password == '') {
         $('#password').addClass('input-error');
         tips.css('display', 'inline');
     } else {
         // 第二次未填写
         $('#password2').addClass('input-error');
         tips2.css('display', 'inline');
     }
     return false;
 }
}