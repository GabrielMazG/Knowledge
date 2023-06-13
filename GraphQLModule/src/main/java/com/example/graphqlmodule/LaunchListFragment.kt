package com.example.graphqlmodule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphqlmodule.databinding.FragmentLaunchListGraphqlBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LaunchListFragment : Fragment() {
    private lateinit var binding: FragmentLaunchListGraphqlBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchListGraphqlBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launches = mutableListOf<LaunchListQuery.Launch>()
        val adapter = LaunchListAdapter(launches)
        binding.launches.layoutManager = LinearLayoutManager(requireContext())
        binding.launches.adapter = adapter

        val channel = Channel<Unit>(Channel.CONFLATED)

        // Send a first item to do the initial load else the list will stay empty forever
        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                var cursor: String? = null
                for (item in channel) {
                    val response = try {
                        apolloClient(requireContext()).query(LaunchListQuery(Optional.Present(cursor)))
                            .execute()
                    } catch (e: ApolloException) {
                        Log.d("LaunchList", "Failure", e)
                        return@repeatOnLifecycle
                    }

                    val newLaunches = response.data?.launches?.launches?.filterNotNull()

                    if (newLaunches != null) {
                        launches.addAll(newLaunches)
                        adapter.notifyDataSetChanged()
                    }

                    cursor = response.data?.launches?.cursor
                    if (response.data?.launches?.hasMore != true) {
                        break
                    }
                }

                adapter.onEndOfListReached = null
                channel.close()
            }

            adapter.onItemClicked = { launch ->
                findNavController().navigate(
                    LaunchListFragmentDirections.openLaunchDetails(launchId = launch.id)
                )
            }
        }
    }
}
