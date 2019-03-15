import { Logger, Pipeline, RawAssertions, Step, Log } from '@ephox/agar';
import { TinyApis, TinyLoader } from '@ephox/mcagar';
import 'tinymce/themes/silver/Theme';
import { UnitTest } from '@ephox/bedrock';
import { PlatformDetection } from '@ephox/sand';
import ColorSwatch from '../../../../../main/ts/ui/core/color/ColorSwatch';

UnitTest.asynctest('GetCurrentColorTest', (success, failure) => {
    const browser = PlatformDetection.detect().browser;

    const sAssertCurrentColor = (editor, format, label, expected) => Logger.t(`Assert current color ${expected}`,
      Step.sync(() => {
        const actual = ColorSwatch.getCurrentColor(editor, format);

        RawAssertions.assertEq(label, expected, actual);
      })
    );

    TinyLoader.setup(function (editor, onSuccess, onFailure) {
      const tinyApis = TinyApis(editor);

      Pipeline.async({}, browser.isIE() ? [] : [
        Log.stepsAsStep('TBA', 'TextColor: getCurrentColor should return the first found forecolor, not the parent color', [
          tinyApis.sFocus,
          tinyApis.sSetContent('<p style="color: blue;">hello <span style="color: red;">world</span></p>'),
          tinyApis.sSetSelection([0, 1, 0], 2, [0, 1, 0], 2),
          sAssertCurrentColor(editor, 'forecolor', 'should return red', 'red')
        ]),
        Log.stepsAsStep('TBA', 'TextColor: getCurrentColor should return the first found backcolor, not the parent color', [
          tinyApis.sFocus,
          tinyApis.sSetContent('<p style="background-color: red;">hello <span style="background-color: blue;">world</span></p>'),
          tinyApis.sSetSelection([0, 1, 0], 2, [0, 1, 0], 2),
          sAssertCurrentColor(editor, 'backcolor', 'should return blue', 'blue')
        ])
      ], onSuccess, onFailure);
    }, {
      plugins: '',
      toolbar: 'forecolor backcolor',
      base_url: '/project/js/tinymce'
    }, success, failure);
  }
);
