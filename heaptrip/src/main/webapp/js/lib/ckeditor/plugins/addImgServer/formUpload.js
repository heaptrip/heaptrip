CKEDITOR.dialog.add( 'abbrDialog', function( editor ) {
    return {
        title: 'Загурзка изображения',
        minWidth: 400,
        minHeight: 200,
        contents: [
            {
                id: 'tab-basic',
                label: 'Basic Settings',
                elements: [
                    {
                        type: 'html',
                        html: '<div class="uploadImgServer"><form action="http://localhost:8080/heaptrip/image/upload" method="POST" enctype="multipart/form-data"><input type="file" name="myfile"><br><input type="submit" value="Upload File to Server"></form><div class="progress"><div class="bar"></div><div class="percent">0%</div><div id="image"/></div><div id="status"></div></div>'
                    }
                ]
            }
        ],
        onOk: function() {
            var dialog = this;
            var abbr = editor.document.createElement( 'abbr' );
            console.log(dialog);
            console.log(abbr);
            editor.insertHtml( $('.uploadImgServer #image').html() );
        },
        onShow: function() {
            $('div.uploadImgServer').ready(function(){

                var bar = $('.bar');
                var percent = $('.percent');
                var status = $('#status');
                $('.uploadImgServer form').css('color','red');
                $('.uploadImgServer form').ajaxForm({
                    beforeSend: function () {
                        status.empty();
                        var percentVal = '0%';
                        bar.width(percentVal)
                        percent.html(percentVal);
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        var percentVal = percentComplete + '%';
                        bar.width(percentVal)
                        percent.html(percentVal);
                    },
                    success: function (result) {
                        var percentVal = '100%';
                        bar.width(percentVal)
                        percent.html(percentVal);
                        $('#image').html('');
                        $("#image").append("<img id='upload-image' src=' http://localhost:8080/heaptrip/rest/image?imageId=" + result.fileId + "'/><br/>");
                    },
                    complete: function (xhr) {
                        status.html(xhr.responseText);
                    }
                });
            });
        }
    };
});