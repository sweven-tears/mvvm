package pers.sweven.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pers.sweven.mvvm.common.OnClickItemListener;

/**
 * Created by Sweven on 2020/7/13--14:34.
 * Email: sweventears@163.com
 */
public abstract class BaseAdapter<Bean, V extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<V>> {
    private Context context;
    private List<Bean> list = new ArrayList<>();

    private LayoutInflater inflater;
    private V binding;
    private OnClickItemListener<Bean> onClickItemListener;
    private int resId;
    private int layoutId;

    /**
     * 适用于单独的类
     *
     * @param context ·
     */
    public BaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 适用于单独的类
     *
     * @param context ·
     * @param list    ·
     */
    public BaseAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 方便在其他类中直接使用
     *
     * @param layoutId 布局 id
     */
    public BaseAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    /**
     * 设置数据
     *
     * @param list datas
     */
    public void setList(List<Bean> list) {
        this.list.clear();
        addList(list);
    }

    /**
     * 追加数据
     *
     * @param list datas
     */
    public void addList(List<Bean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    /**
     * 指定位置添加数据
     *
     * @param position position
     * @param bean     data
     */
    public void addData(int position, Bean bean) {
        this.list.add(position, bean);
        notifyDataSetChanged();
    }

    /**
     * 移除指定位置的数据
     *
     * @param position position
     */
    public void remove(int position) {
        this.list.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 替换指定位置的数据
     *
     * @param position position
     * @param bean     data
     */
    public void replace(int position, Bean bean) {
        this.list.remove(position);
        this.list.add(position, bean);
        notifyDataSetChanged();
    }

    public V getBinding() {
        return binding;
    }

    @NonNull
    @Override
    public BaseViewHolder<V> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        binding = DataBindingUtil.inflate(inflater, bindLayout(), null, false);
        BaseViewHolder<V> holder = new BaseViewHolder<>(binding);
        if (onClickItemListener != null) {
            holder.itemView.setOnClickListener(v -> {
                Bean bean = (Bean) v.getTag();
                onClickItemListener.onClick(holder.getAdapterPosition(), bean);
            });
        }
        if (onClickItemListener != null && resId > 0) {
            holder.itemView.findViewById(resId).setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                Bean bean = (Bean) holder.itemView.getTag(position);
                onClickItemListener.onClick(position, bean);
            });
        }
        return holder;
    }

    protected int bindLayout() {
        return layoutId;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<V> holder, int i) {
        Bean bean = list.get(i);
        holder.itemView.setTag(i, bean);
        onBindData(bean, holder);
    }

    protected abstract void onBindData(Bean bean, BaseViewHolder<V> holder);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setOnClickItemListener(OnClickItemListener<Bean> onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public void setOnClickItemListener(OnClickItemListener<Bean> onClickItemListener, @IdRes int resId) {
        this.onClickItemListener = onClickItemListener;
        this.resId = resId;
    }

}
