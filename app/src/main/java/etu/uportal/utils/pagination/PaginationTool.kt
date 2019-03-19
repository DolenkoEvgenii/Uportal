package etu.uportal.utils.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

object PaginationTool {
    const val EMPTY_LIST_ITEMS_COUNT = 0
    const val DEFAULT_PAGE_SIZE = 50

    fun paging(recyclerView: RecyclerView?, pagingListener: (Int) -> Unit,
               limit: Int = DEFAULT_PAGE_SIZE, emptyListCount: Int = EMPTY_LIST_ITEMS_COUNT): Disposable {

        if (recyclerView == null) {
            throw PagingException("null recyclerView")
        }
        if (recyclerView.adapter == null) {
            throw PagingException("null recyclerView adapter")
        }
        if (limit <= 0) {
            throw PagingException("limit must be greater then 0")
        }
        if (emptyListCount < 0) {
            throw PagingException("emptyListCount must be not less then 0")
        }

        return getScrollObservable(recyclerView, limit, emptyListCount)
                .distinctUntilChanged()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { pagingListener(it) }

    }

    private fun getScrollObservable(recyclerView: RecyclerView, limit: Int, emptyListCount: Int): Observable<Int> {
        return Observable.create { subscriber ->
            val sl = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!subscriber.isDisposed) {
                        val position = getLastVisibleItemPosition(recyclerView)
                        val updatePosition = recyclerView.adapter!!.itemCount - 1 - limit / 2
                        if (position >= updatePosition) {
                            subscriber.onNext(recyclerView.adapter!!.itemCount)
                        }
                    }
                }
            }
            recyclerView.addOnScrollListener(sl)
            subscriber.setDisposable(object : Disposable {
                var _isDisposed = false

                override fun dispose() {
                    _isDisposed = true
                    recyclerView.removeOnScrollListener(sl)
                }

                override fun isDisposed(): Boolean {
                    return isDisposed
                }
            })
            if (recyclerView.adapter!!.itemCount == emptyListCount) {
                subscriber.onNext(recyclerView.adapter!!.itemCount)
            }
        }
    }

    private fun getLastVisibleItemPosition(recyclerView: RecyclerView): Int {
        val recyclerViewLMClass = recyclerView.layoutManager!!.javaClass
        if (recyclerViewLMClass == LinearLayoutManager::class.java || LinearLayoutManager::class.java.isAssignableFrom(recyclerViewLMClass)) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            return linearLayoutManager!!.findLastVisibleItemPosition()
        } else if (recyclerViewLMClass == StaggeredGridLayoutManager::class.java || StaggeredGridLayoutManager::class.java.isAssignableFrom(recyclerViewLMClass)) {
            val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager?
            val into = staggeredGridLayoutManager!!.findLastVisibleItemPositions(null)
            val intoList = ArrayList<Int>()
            for (i in into) {
                intoList.add(i)
            }
            return Collections.max(intoList)
        }
        throw PagingException("Unknown LayoutManager class: $recyclerViewLMClass")
    }


    class PagingException(detailMessage: String) : RuntimeException(detailMessage)
}