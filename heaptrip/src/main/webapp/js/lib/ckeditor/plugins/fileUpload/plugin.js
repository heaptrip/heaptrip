CKEDITOR.plugins.add('fileUpload',
    {
        init: function (editor) {

            editor.addCommand('fileUploadDialog', new CKEDITOR.dialogCommand('fileUploadDialog'));

            editor.ui.addButton('FileUpload',
                {
                    label: 'Upload images',
                    command: 'fileUploadDialog',
                    icon: CKEDITOR.plugins.getPath('fileUpload') + 'icons/fileUpload.png'
                });

            CKEDITOR.dialog.add('fileUploadDialog', function (editor) {
                return {
                    title: 'Upload images',
                    minWidth: 800,
                    minHeight: 250,
                    contents: [
                        {
                            id: 'general',
                            label: 'Settings',
                            elements: [
                                {
                                    type: 'html',
                                    html: '<iframe id="UPLOADER_CONTAINER" frameborder="0" onLoad="calculateIframeHeight();" marginwidth="0" marginheight="0" frameborder="0" style="overflow:hidden;height:100%;width:100%" height="100%" width="100%" ></iframe>'
                                }
                            ]
                        }
                    ],
                    onOk: function () {
                        var files = null;
                        var filesDiv = $("#UPLOADER_CONTAINER").contents().find('#FILES_RESULT')
                        if (filesDiv) {
                            var filesString = filesDiv.text();
                            if (filesString) {
                                files = jQuery.parseJSON(filesString);
                                if (files) {
                                    var insertHtml = '';
                                    $.each(files, function (index, value) {
                                        insertHtml = insertHtml + '<br/>';
                                        insertHtml = insertHtml + '<p style="text-align:center"><img width="550" src="' + value.url + '" /></p>';
                                    });
                                    editor.insertHtml(insertHtml);
                                }
                            }
                        }
                        $('#UPLOADER_CONTAINER').attr("src", "");
                    },
                    onShow: function () {
                        $('#UPLOADER_CONTAINER').height($($('[role="presentation"]')[0]).height());
                        $('#UPLOADER_CONTAINER').attr("src", './upload.jsp?image_type=CONTENT_IMAGE&amp;target_id=' + $.getParamFromURL().id);
                    }
                };
            });


        }
    });