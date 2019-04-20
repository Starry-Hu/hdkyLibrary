function createEditor() {
    var E = window.wangEditor
        // 全局变量  方便之后使用
    editor = new E('#editor')
        // 或者 var editor = new E( document.getElementById('editor') )
    editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
    editor.customConfig.pasteFilterStyle = true // 开始粘贴样式过滤
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
    // 关闭粘贴样式的过滤
    editor.customConfig.pasteFilterStyle = false
    // 忽略粘贴内容中的图片
    editor.customConfig.pasteIgnoreImg = true

    editor.create();
}


