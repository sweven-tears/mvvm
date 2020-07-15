package pers.sweven.mvvm.common;

import pers.sweven.mvvm.base.BaseDialog;

public interface OnConfirmListener<T extends BaseDialog> {
    void onConfirm(T dialog);
}
