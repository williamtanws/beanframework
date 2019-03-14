/**
 * Copyright (c) Tiny Technologies, Inc. All rights reserved.
 * Licensed under the LGPL or a commercial license.
 * For LGPL see License.txt in the project root for license information.
 * For commercial licenses see https://www.tiny.cloud/
 */

import { XMLHttpRequest } from '@ephox/sand';
import Promise from 'tinymce/core/api/util/Promise';
import Tools from 'tinymce/core/api/util/Tools';
import { FormData } from '@ephox/dom-globals';
import { BlobCache, BlobInfo } from '../../../../../core/main/ts/api/file/BlobCache';

/**
 * This is basically cut down version of tinymce.core.file.Uploader, which we could use directly
 * if it wasn't marked as private.
 */

export type SuccessCallback = (path: string) => void;
export type FailureCallback = (error: string) => void;
export type ProgressCallback = (percent: number) => void;
export type UploadHandler = (blobInfo: BlobCache, success: SuccessCallback, failure: FailureCallback, progress: ProgressCallback) => void;

export interface UploaderSettings {
  url?: string;
  credentials?: boolean;
  basePath?: string;
  handler?: UploadHandler;
}

const noop = function () {};

const pathJoin = function (path1: string | undefined, path2: string) {
  if (path1) {
    return path1.replace(/\/$/, '') + '/' + path2.replace(/^\//, '');
  }

  return path2;
};

export default function (settings: UploaderSettings) {
  const defaultHandler = function (blobInfo: BlobInfo, success: SuccessCallback, failure: FailureCallback, progress: ProgressCallback) {
    let xhr, formData;

    xhr = XMLHttpRequest();
    xhr.open('POST', settings.url);
    xhr.withCredentials = settings.credentials;

    xhr.upload.onprogress = function (e) {
      progress(e.loaded / e.total * 100);
    };

    xhr.onerror = function () {
      failure('Image upload failed due to a XHR Transport error. Code: ' + xhr.status);
    };

    xhr.onload = function () {
      let json;

      if (xhr.status < 200 || xhr.status >= 300) {
        failure('HTTP Error: ' + xhr.status);
        return;
      }

      json = JSON.parse(xhr.responseText);

      if (!json || typeof json.location !== 'string') {
        failure('Invalid JSON: ' + xhr.responseText);
        return;
      }

      success(pathJoin(settings.basePath, json.location));
    };

    formData = new FormData();
    formData.append('file', blobInfo.blob(), blobInfo.filename());

    xhr.send(formData);
  };

  const uploadBlob = function (blobInfo: BlobCache, handler: UploadHandler) {
    return new Promise<string>(function (resolve, reject) {
      try {
        handler(blobInfo, resolve, reject, noop);
      } catch (ex) {
        reject(ex.message);
      }
    });
  };

  const isDefaultHandler = function (handler: Function) {
    return handler === defaultHandler;
  };

  const upload = function (blobInfo: BlobCache): Promise<string> {
    return (!settings.url && isDefaultHandler(settings.handler)) ? Promise.reject('Upload url missing from the settings.') : uploadBlob(blobInfo, settings.handler);
  };

  settings = Tools.extend({
    credentials: false,
    handler: defaultHandler
  }, settings);

  return {
    upload
  };
}