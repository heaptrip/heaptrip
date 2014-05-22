/**
 * CKEditor 4.4.0 standard
 */
CKEDITOR.on('dialogDefinition', function (ev) {
    // Take the dialog name and its definition from the event data.
    var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;

    // customize 'Link' dialog
    if (dialogName == 'link') {
        // Remove the 'Target' tab from the 'Link' dialog
        dialogDefinition.removeContents('target');
        // remove “link to anchor” from the “Link” dialog
        var infoTab = dialogDefinition.getContents('info');
        var linktypeField = infoTab.get('linkType');
        linktypeField['items'].splice(1, 1);
    }
});

CKEDITOR.editorConfig = function (config) {
    config.uiColor = '#FFFFFF';

    config.extraPlugins = 'fileUpload,autogrow,preview,justify,image2,widget,lineutils';

    config.autoGrow_onStartup = true;

    // Set the most common block elements.
    config.format_tags = 'p;h1;h2;h3;pre';

    // remove status bar plugin
    config.removePlugins = 'elementspath';


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
                    return element.attributes['src'].indexOf("/rest/image/") >= 0 || element.attributes['src'] == "cke-test";
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
