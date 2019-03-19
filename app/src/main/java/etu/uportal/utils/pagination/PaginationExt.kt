package etu.uportal.utils.pagination

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.paging(pagingListener: (Int) -> Unit,
                        limit: Int = PaginationTool.DEFAULT_PAGE_SIZE,
                        emptyListCount: Int = PaginationTool.EMPTY_LIST_ITEMS_COUNT) {
    PaginationTool.paging(this, pagingListener, limit, emptyListCount)
}