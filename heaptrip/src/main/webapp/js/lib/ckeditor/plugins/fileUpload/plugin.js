CKEDITOR.plugins.add('fileUpload',
    {
        init: function (editor) {
            // editor.addCommand( 'OpenDialog',new CKEDITOR.dialogCommand( 'OpenDialog' ) );
            editor.ui.addButton('FileUpload',
                {
                    label: 'Upload images',
                    command: 'OpenDialog',
                    icon: CKEDITOR.plugins.getPath('fileUpload') + '/icons/icon.png'
                });

            editor.addCommand('OpenDialog', { exec: function () {
                var callBackFunction = function (jsonUploadResult) {
                    editor.insertHtml('<img src="test" />');
                };
                uploader.show(callBackFunction);
                }
            });
        }
    });