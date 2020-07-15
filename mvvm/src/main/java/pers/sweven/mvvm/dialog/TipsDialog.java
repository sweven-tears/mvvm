package pers.sweven.mvvm.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import pers.sweven.mvvm.R;
import pers.sweven.mvvm.base.BaseDialog;
import pers.sweven.mvvm.common.OnConfirmListener;
import pers.sweven.mvvm.common.TipsTypeDef;
import pers.sweven.mvvm.databinding.DialogTipsBinding;

/**
 * Created by Sweven on 2020/7/13--17:08.
 * Email: sweventears@163.com
 */
public class TipsDialog extends BaseDialog<DialogTipsBinding> {
    public TipsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int bindLayout() {
        return R.layout.dialog_tips;
    }

    @Override
    protected void initView() {
        String cancel = "取消";
        getBinding().cancel.setText(cancel);
        String confirm = "确定";
        getBinding().confirm.setText(confirm);
    }

    @Override
    protected void doBusiness() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public static class Builder {
        private Context context;
        private CharSequence title;
        private OnConfirmListener<TipsDialog> confirmListener;

        public Builder(Context context, CharSequence title) {
            this.context = context;
            this.title = title;
        }

        public Builder setConfirmListener(OnConfirmListener<TipsDialog> confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public TipsDialog build(@TipsTypeDef int tipsType) {
            TipsDialog tipsDialog = new TipsDialog(context);
            tipsDialog.getBinding().title.setText(title);
            tipsDialog.getBinding().confirm.setOnClickListener(v -> {
                if (confirmListener != null) {
                    confirmListener.onConfirm(tipsDialog);
                }
            });
            tipsDialog.getBinding().cancel.setOnClickListener(v -> tipsDialog.dismiss());
            switch (tipsType) {
                case TipsTypeDef.TIPS_CONFIRM:
                    tipsDialog.getBinding().cancel.setVisibility(View.GONE);
                    tipsDialog.getBinding().centerView.setVisibility(View.GONE);
                    break;
                case TipsTypeDef.TIPS_TITLE:
                    tipsDialog.getBinding().buttonPanel.setVisibility(View.GONE);
                    break;
                case TipsTypeDef.TIPS_CANCEL:
                    break;
            }
            return tipsDialog;
        }

        public void show(@TipsTypeDef int tipsType) {
            build(tipsType).show();
        }
    }
}
