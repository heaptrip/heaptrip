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
                var callBackFunction = function (files) {
                    if (files) {
                        $.each(files, function (index, value) {
                            editor.insertHtml('<br/>');
                            editor.insertHtml('<img src="' + value.url + '" />');
                        });
                    }
                };
                uploader.show(callBackFunction);
            }
            });
        }
    });