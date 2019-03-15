/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.extraPlugins = 'floatpanel,colorbutton,colordialog,googledocs';
	config.colorButton_enableMore = true;
	
	config.filebrowserBrowseUrl: 		'/backoffice/ckeditor/browser/browse';
	config.filebrowserImageBrowseUrl: 	'/backoffice/ckeditor/browser/browse?type=Images';
	
	config.filebrowserUploadUrl: 		'/backoffice/ckeditor/uploader/upload?type=File';
	config.filebrowserImageUploadUrl: 	'/backoffice/ckeditor/uploader/upload?type=Images';
	config.filebrowserFlashUploadUrl: 	'/backoffice/ckeditor/uploader/upload?type=Flash';
};
