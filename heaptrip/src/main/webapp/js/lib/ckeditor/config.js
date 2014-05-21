/**
 * @license Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */


CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here.
    // For complete reference see:
    // http://docs.ckeditor.com/#!/api/CKEDITOR.config

    config.uiColor = '#FFFFFF';
    //config.language = 'fr';
    //config.height = '100%';

    config.extraPlugins = 'fileUpload,autogrow,preview,justify,image2,widget,lineutils';
    config.autoGrow_onStartup = true;


    config.toolbar =
        [
            { name: 'styles', items: [ 'Format'] },
            { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'RemoveFormat' ] },
            { name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
            '/',
            { name: 'insert', items: [ 'FileUpload', 'Image', 'Table', 'SpecialChar' ] },
            { name: 'links', items: [ 'Link', 'Unlink' ] },
            { name: 'document', items: [ 'Source', '-', 'Preview' ] },
            { name: 'tools', items: [ 'Maximize' ] }
        ];

    // Remove some buttons provided by the standard plugins, which are
    // not needed in the Standard(s) toolbar.
    //config.removeButtons = 'Underline,Subscript,Superscript';

    // Set the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // Simplify the dialog windows.
    config.removeDialogTabs = 'image:advanced;link:advanced';

    // remove status bar plugin
    config.removePlugins = 'elementspath';
};
