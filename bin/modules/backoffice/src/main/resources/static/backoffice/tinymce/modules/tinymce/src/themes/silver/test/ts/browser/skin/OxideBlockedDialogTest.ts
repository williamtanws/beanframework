import { ApproxStructure, Assertions, Mouse, Waiter } from '@ephox/agar';
import { describe, it } from '@ephox/bedrock-client';
import { TinyHooks, TinyUiActions } from '@ephox/mcagar';
import { SugarBody } from '@ephox/sugar';

import Editor from 'tinymce/core/api/Editor';
import { Dialog } from 'tinymce/core/api/ui/Ui';
import Theme from 'tinymce/themes/silver/Theme';

describe('browser.tinymce.themes.silver.skin.OxideBlockedDialogTest', () => {
  let testDialogApi: Dialog.DialogInstanceApi<{}>;
  const hook = TinyHooks.bddSetup<Editor>({
    toolbar: 'dialog-button',
    base_url: '/project/tinymce/js/tinymce',
    setup: (ed: Editor) => {
      ed.ui.registry.addButton('dialog-button', {
        type: 'button',
        text: 'Launch Dialog',
        onAction: () => {
          testDialogApi = ed.windowManager.open({
            title: 'Testing Blocking',
            body: {
              type: 'panel',
              items: [
                {
                  type: 'button',
                  name: 'busy-button',
                  text: 'Make Busy'
                }
              ]
            },
            buttons: [ ],
            onAction: (dialogApi, actionData) => {
              if (actionData.name === 'busy-button') {
                dialogApi.block('Dialog is blocked.');
              }
            }
          });
        }
      });
    }
  }, [ Theme ]);

  it('Check structure of font format', async () => {
    const editor = hook.editor();
    TinyUiActions.clickOnToolbar(editor, 'button');
    const dialog = await TinyUiActions.pWaitForDialog(editor);
    Mouse.clickOn(SugarBody.body(), 'button:contains("Make Busy")');
    await Waiter.pTryUntil(
      'Waiting for busy structure to match expected',
      () => Assertions.assertStructure(
        'Checking dialog structure to see where "busy" is',
        ApproxStructure.build((s, str, arr) => s.element('div', {
          classes: [ arr.has('tox-dialog') ],
          children: [
            s.element('div', {
              classes: [ arr.has('tox-dialog__header') ]
            }),
            s.element('div', {
              classes: [ arr.has('tox-dialog__content-js') ]
            }),
            s.element('div', {
              classes: [ arr.has('tox-dialog__footer') ]
            }),
            s.element('div', {
              classes: [ arr.has('tox-dialog__busy-spinner') ],
              children: [
                s.element('div', {
                  classes: [ arr.has('tox-spinner') ],
                  children: [
                    // The three loading dots
                    s.element('div', {}),
                    s.element('div', {}),
                    s.element('div', {})
                  ]
                })
              ]
            })
          ]
        })),
        dialog
      )
    );
    testDialogApi.unblock();
    await Waiter.pTryUntil(
      'Waiting for busy structure to go away',
      () => Assertions.assertStructure(
        'Checking dialog structure to see where "busy" is',
        ApproxStructure.build((s, str, arr) => s.element('div', {
          classes: [ arr.has('tox-dialog') ],
          children: [
            s.element('div', {
              classes: [ arr.has('tox-dialog__header') ]
            }),
            s.element('div', {
              classes: [ arr.has('tox-dialog__content-js') ]
            }),
            s.element('div', {
              classes: [ arr.has('tox-dialog__footer') ]
            })
          ]
        })),
        dialog
      )
    );
  });
});
