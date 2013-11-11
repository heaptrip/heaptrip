CKEDITOR.plugins.add('addImgServer',{
    init: function(editor){
        editor.addCommand( 'abbrDialog', new CKEDITOR.dialogCommand( 'abbrDialog' ) );

        editor.ui.addButton( 'addImgServer', {
            label: 'Загрузить изображение',
            command: 'abbrDialog',
            toolbar: 'about'
        });

        CKEDITOR.dialog.add( 'abbrDialog', this.path + 'formUpload.js' );
    },
    icons:'addImgServer', // иконка
});