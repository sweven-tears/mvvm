package pers.sweven.mvvm.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Sweven on 2020/7/13--14:47.
 * Email: sweventears@163.com
 */
public class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private V binding;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public BaseViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public V getBinding() {
        return binding;
    }

    public void setBinding(V binding) {
        this.binding = binding;
    }
}
