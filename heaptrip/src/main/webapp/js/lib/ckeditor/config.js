/**
 * CKEditor 4.4.0 standard
 */
CKEDITOR.editorConfig = function (config) {
    // set color
    config.uiColor = '#FFFFFF';

    // set add-ons
    config.extraPlugins = 'fileUpload,autogrow,preview,justify,image2,widget,lineutils';

    // enable auto start for autoGrow plugin
    config.autoGrow_onStartup = true;

    // set the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // remove status bar plugin
    config.removePlugins = 'elementspath';

    // customize dialogs
    config.removeDialogTabs = 'link:target;link:advanced';

    // config allowed html tags
    config.allowedContent = {
        'b i s u ul ol big small': true,
        'h1 h2 h3 p li': {
            styles: 'text-align'
        },
        a: { attributes: '!href,target' },
        img: {
            attributes: '!src,alt,width,height',
            styles: 'float,width,height',
            classes: 'left,right',
            match: function (element) {
                if (element.attributes['src']) {
                    // enable only heaptrip image
                    return element.attributes['src'].indexOf("/../rest/image/") >= 0 || element.attributes['src'] == "cke-test";
                } else {
                    return true;
                }
            }
        },
        table: {
            attributes: 'border, cellpadding, cellspacing, align',
            styles: 'width,height'
        },
        'figcaption caption': true
    };

    // set toolbar
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
};

// customize dialogs
CKEDITOR.on('dialogDefinition', function (ev) {
    // Take the dialog name and its definition from the event data.
    var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;

    // customize 'Link' dialog
    if (dialogName == 'link') {
        // remove “link to anchor” from the “Link” dialog
        var infoTab = dialogDefinition.getContents('info');
        var linktypeField = infoTab.get('linkType');
        linktypeField['items'].splice(1, 1);
    }
});
