/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.image_previewText=' '; //预览区域显示内容
    config.filebrowserImageUploadUrl= "/upload/uploadImg2"; //待会要上传的action或servlet
    var modalBodyHeight = $("#addIntroModal .modal-content .modal-body").height();
    var editHeight = modalBodyHeight - 140;
    // config.width = 970; //宽度
    config.height = editHeight; //高度
};
