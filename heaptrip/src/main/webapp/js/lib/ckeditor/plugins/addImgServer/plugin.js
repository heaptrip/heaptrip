CKEDITOR.plugins.add('addImgServer',{
    init: function(editor){
        editor.addCommand( 'abbrDialog', new CKEDITOR.dialogCommand( 'abbrDialog' ) );

        editor.ui.addButton( 'addImgServer', {
            label: 'Загрузить изображение',
            command: 'abbrDialog',
            toolbar: 'about'
        });

        CKEDITOR.dialog.add( 'abbrDialog', this.path + 'formUpload.js' );


        /*var cmd = editor.addCommand('addImgServer', {
            exec:function(editor){

                editor.insertHtml( '<img src="http://altaialtai.ru/upload/company/520dad318e61d.jpg">' ); // собственно сама работа плагина
            }
        });
        cmd.modes = { wysiwyg : 1, source: 1 };// плагин будет работать и в режиме wysiwyg и в режиме исходного текста
        editor.ui.addButton('addImgServer',{
            label: 'Загрузить изображение',
            command: 'addImgServer',
            toolbar: 'about'
        });*/
    },
    icons:'addImgServer', // иконка
});