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
    editor.create();
    

     // 自定义处理粘贴的文本内容
//     editor.customConfig.pasteTextHandle = function (content) {
//       // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
//       if (content == '' && !content) return ''
//       var str = content
//       str = str.replace(/<xml>[\s\S]*?<\/xml>/ig, '')
//       str = str.replace(/<style>[\s\S]*?<\/style>/ig, '')
//       str = str.replace(/<\/?[^>]*>/g, '')
//       str = str.replace(/[ | ]*\n/g, '\n')
//       str = str.replace(/&nbsp;/ig, '')
//       console.log('****', content)
//       console.log('****', str)
//       return str;
//     }
     
//    editor.customConfig.pasteTextHandle = function removeWordXml(text){
//  	  var html = text;
//  	  html = html.replace(/<\/?SPANYES[^>]*>/gi, "");//  Remove  all  SPAN  tags
//  	  html = html.replace(/<(\w[^>]*)  class=([^|>]*)([^>]*)/gi, "<$1$3");  //  Remove  Class  attributes
//  	  html = html.replace(/<(\w[^>]*)  style="([^"]*)"([^>]*)/gi, "<$1$3");  //  Remove  Style  attributes
//  	  html = html.replace(/<(\w[^>]*)  lang=([^|>]*)([^>]*)/gi, "<$1$3");//  Remove  Lang  attributes
//  	  html = html.replace(/<\\?\?xml[^>]*>/gi, "");//  Remove  XML  elements  and  declarations
//  	  html = html.replace(/<\/?\w+:[^>]*>/gi, "");//  Remove  Tags  with  XML  namespace  declarations:  <o:p></o:p>
//  	  html = html.replace(/ /, "");//  Replace  the   
//  	  html = html.replace(/\n(\n)*( )*(\n)*\n/gi, '\n');
//  	  html = html.replace(/&nbsp;/ig, '')
//  	  //  Transform  <P>  to  <DIV>
//  	  // var  re  =  new  RegExp("(<P)([^>]*>.*?)(<//P>)","gi")  ;            //  Different  because  of  a  IE  5.0  error
//  	  // html = html.replace(re, "<div$2</div>");
//  	  return html;
//  	  
//  	}
}


