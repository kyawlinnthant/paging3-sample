package mdy.klt.paging3_test.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import mdy.klt.paging3_test.databinding.FragmentListBinding
import mdy.klt.paging3_test.ui.MovieListAdapter
import mdy.klt.paging3_test.ui.PagingLoadStateAdapter
import timber.log.Timber


@AndroidEntryPoint
class ListFragment : Fragment() {

    private val vm: ListViewModel by viewModels()
    lateinit var adapter: MovieListAdapter
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observe()
    }

    private fun initRecycler() {

        adapter = MovieListAdapter { getItemClick(it) }.apply {
            binding.swipeRefresh.setOnRefreshListener { refresh() }
            binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recycler.adapter = this
            binding.recycler.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
        }

    }

    private fun observe() {

        with(vm) {

            Timber.tag("frag :").e(vm.movies.toString())

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                movies.collect {
                    adapter.submitData(it)
                }
            }
        }

    }

    private fun getItemClick(position: Int) {
        val item = adapter.getClickItem(position)

        Timber.tag("click").d(item?.original_title)
    }

}