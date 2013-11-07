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
                        html: '<script src="http://malsup.github.io/jquery.form.js"></script><div class="uploadImgServer"><form action="http://localhost:8080/heaptrip/image/upload" method="POST" enctype="multipart/form-data"><input type="file" name="myfile"><br><input type="submit" value="Upload File to Server"></form><div class="progress"><div class="bar"></div><div class="percent">0%</div><div id="image"/></div><div id="status"></div></div>'
                    }
                ]
            }
        ],
        onOk: function() {
            var dialog = this;

            var abbr = editor.document.createElement( 'abbr' );


            /*abbr.setAttribute( 'title', dialog.getValueOf( 'tab-basic', 'title' ) );
            abbr.setText( dialog.getValueOf( 'tab-basic', 'abbr' ) );

            var id = dialog.getValueOf( 'tab-adv', 'id' );
            if ( id )
                abbr.setAttribute( 'id', id );

            editor.insertElement( abbr );     */
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