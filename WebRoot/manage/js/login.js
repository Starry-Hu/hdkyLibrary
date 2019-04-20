function username() {
    var username = $('#username').val();
    var tips = $('#username').siblings('span');
    if (username == '') {
        $('#username').addClass('input-error');
        tips.html('用户名不能为空');
        return false;
    } else {
        $('#username').removeClass('input-error');
        tips.html('');
        return true;
    }
}

function password() {
    var password = $('#password').val();
    var tips = $('#password').siblings('span');
    if (password == '') {
        $('#password').addClass('input-error');
        tips.html('密码不能为空');
        return false;
    } else {
        $('#password').removeClass('input-error');
        tips.html('');
        return true;
    }
}

$(function() {

    $('#username').blur(username);
    $('#password').blur(password);

    $('input').on('focus', function() {
        $(this).removeClass('input-error');
    });


    $('#submit').click(function(e) {
        var path = location.protocol + "//" + window.location.host + "/Library/admin/adminLogin";

        if (username() && password()) {
            $.ajax({
                type: "post",
                url: path,
                data: {
                    adminId: $('#username').val(),
                    password:$('#password').val(),
                },
                dataType: "json",
                success: function(response) {
                    if (response.code != 115) {
                    	alert(response.msg);
                    } else {
                        alert('登陆成功');
                        window.location = 'index.html';
                    }
                }
            });
        } else {
            alert('信息填写不完整！');
            e.preventDefault();
        }
    });
})