package com.newtonacademic.newtontutors.adapter;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @author lyy
 * @version v1.0, 2019/7/25
 * @Description recycleView 拖动排序
 */
public class MoveToucheCallBack extends ItemTouchHelper.Callback {

    private IItemHelper itemHelper;

    public MoveToucheCallBack(IItemHelper itemHelper) {
        this.itemHelper = itemHelper;
    }

    /**
     * 官方文档的说明如下：
     * o control which actions user can take on each view, you should override getMovementFlags(RecyclerView, ViewHolder)
     * and return appropriate set of direction flags. (LEFT, RIGHT, START, END, UP, DOWN).
     * 返回我们要监控的方向，上下左右，我们做的是上下拖动，要返回都是UP和DOWN
     * 关键坑爹的是下面方法返回值只有1个，也就是说只能监控一个方向。
     * 不过点入到源码里面有惊喜。源码标记方向如下：
     *  public static final int UP = 1     0001
     *  public static final int DOWN = 1 << 1; （位运算：值其实就是2）0010
     *  public static final int LEFT = 1 << 2   左 值是3
     *  public static final int RIGHT = 1 << 3  右 值是8
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //移动方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * 官方文档的说明如下
     * If user drags an item, ItemTouchHelper will call onMove(recyclerView, dragged, target). Upon receiving this callback,
     * you should move the item from the old position (dragged.getAdapterPosition()) to new position (target.getAdapterPosition())
     * in your adapter and also call notifyItemMoved(int, int).
     * 拖动某个item的回调，在return前要更新item位置，调用notifyItemMoved（draggedPosition，targetPosition）
     * viewHolde:正在拖动item
     * target：要拖到的目标
     *
     * @return true 表示消费事件
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder targetViewHolder) {
        // 拖动的条目从旧位置--到新位置时调用
        return itemHelper.itemMoved(viewHolder.getLayoutPosition(), targetViewHolder.getLayoutPosition());
    }

    /**
     * 谷歌官方文档说明如下：
     * 这个看了一下主要是做左右拖动的回调
     * When a View is swiped, ItemTouchHelper animates it until it goes out of bounds, then calls onSwiped(ViewHolder, int).
     * At this point, you should update your adapter (e.g. remove the item) and call related Adapter#notify event.
     *
     * @param viewHolder 操作的holder
     * @param direction  方向
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 滑动到消失调用
    }

    /**
     * 官方文档说明如下：
     * Returns whether ItemTouchHelper should start a drag and drop operation if an item is long pressed.
     * 是否开启长按 拖动
     *
     * @return bool
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //状态改变时调用 比如正在滑动，正在拖动
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewHolder.itemView.setElevation(10);
                    viewHolder.itemView.setTranslationZ(10);
                }
            }
        }
    }

    @Override
    public void clearView(@NonNull final RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //滑动完，拖动完调用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.itemView.setElevation(0);
            viewHolder.itemView.setTranslationZ(0);
        }
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    /**
     * 官方文档如下：返回true 当前tiem可以被拖动到目标位置后，直接”落“在target上，其他的上面的tiem跟着“落”，
     * 所以要重写这个方法，不然只是拖动的tiem在动，target tiem不动，静止的
     * Return true if the current ViewHolder can be dropped over the the target ViewHolder.
     *
     * @param recyclerView 控件
     * @param current      当前
     * @param target       目标
     * @return bool
     */
    @Override
    public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    /**
     * 传递接口
     */
    public interface IItemHelper {
        /**
         * 移动接口
         *
         * @param oldPosition 旧位置
         * @param newPosition 新位置
         * @return bool
         */
        boolean itemMoved(int oldPosition, int newPosition);
    }
}
