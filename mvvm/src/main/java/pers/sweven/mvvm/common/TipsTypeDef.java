package pers.sweven.mvvm.common;

import android.support.annotation.IntDef;

@IntDef({TipsTypeDef.TIPS_TITLE, TipsTypeDef.TIPS_CANCEL, TipsTypeDef.TIPS_CONFIRM})
public @interface TipsTypeDef {
    int TIPS_TITLE = 0;
    int TIPS_CONFIRM = 1;
    int TIPS_CANCEL = 2;
}
