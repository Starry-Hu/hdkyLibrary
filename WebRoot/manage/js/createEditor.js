function createEditor() {
    var E = window.wangEditor
        // 全局变量  方便之后使用
    editor = new E('#editor')
        // 或者 var editor = new E( document.getElementById('editor') )
    editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片

    var imgPath = location.protocol + "//" + window.location.host +
        "/Library/news/uploadImg";
    // editor.customConfig.uploadImgServer = 'http://localhost:8080/Library/news/uploadImg'
    editor.customConfig.uploadImgServer = imgPath;
    editor.customConfig.uploadFileName = 'image';

    editor.customConfig.uploadImgHooks = {
        before: function(xhr, editor, files) {
            alert("准备上传图片");
        },
        success: function(xhr, editor, result) {},
        fail: function(xhr, editor, result) {
        	alert(result)
        },
        error: function(xhr, editor) {},
        timeout: function(xhr, editor) {}

    }
    editor.create();
}

function uploadAttachment(Attachment) {
    var attachmentPath = location.protocol + "//" + window.location.host +
        "/Library/news/uploadAttachment";
    $.ajax({
        type: "post",
        url: attachmentPath,
        data: {
            attachment: Attachment
        },
        dataType: "json",
        success: function(response) {
            // 上传成功
            if (response.errno == 0) {
                console.log(response.data);
                alert("上传附件成功");
            }
        }
    });
}