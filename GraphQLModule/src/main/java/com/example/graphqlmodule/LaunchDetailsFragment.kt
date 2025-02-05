package com.example.graphqlmodule

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphqlmodule.databinding.FragmentLaunchDetailsGraphqlBinding
import kotlinx.coroutines.launch

class LaunchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLaunchDetailsGraphqlBinding
    val args: LaunchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchDetailsGraphqlBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                binding.bookButton.visibility = View.GONE
                binding.bookProgressBar.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.error.visibility = View.GONE

                val response = try {
                    apolloClient(requireContext()).query(LaunchDetailsQuery(id = args.launchId))
                        .execute()
                } catch (e: ApolloException) {
                    binding.progressBar.visibility = View.GONE
                    binding.error.text = "Oh no... A protocol error happened"
                    binding.error.visibility = View.VISIBLE
                    return@repeatOnLifecycle
                }

                val launch = response.data?.launch
                if (launch == null || response.hasErrors()) {
                    binding.progressBar.visibility = View.GONE
                    binding.error.text = response.errors?.get(0)?.message
                    binding.error.visibility = View.VISIBLE
                    return@repeatOnLifecycle
                }

                binding.progressBar.visibility = View.GONE

                binding.missionPatch.load(launch.mission?.missionPatch) {
                    placeholder(R.drawable.ic_launcher_background)
                }
                binding.site.text = launch.site
                binding.missionName.text = launch.mission?.name
                val rocket = launch.rocket
                binding.rocketName.text = "🚀 ${rocket?.name} ${rocket?.type}"

                configureButton(launch.isBooked)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun configureButton(isBooked: Boolean) {
        binding.bookButton.visibility = View.VISIBLE
        binding.bookProgressBar.visibility = View.GONE

        binding.bookButton.text = if (isBooked) {
            getString(R.string.cancel)
        } else {
            getString(R.string.book_now)
        }

        binding.bookButton.setOnClickListener {
            if (User.getToken(requireContext()) == null) {
                findNavController().navigate(
                    R.id.open_login
                )
                return@setOnClickListener
            }

            binding.bookButton.visibility = View.INVISIBLE
            binding.bookProgressBar.visibility = View.VISIBLE

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    val mutation = if (isBooked) {
                        CancelTripMutation(id = args.launchId)
                    } else {
                        BookTripMutation(id = args.launchId)
                    }

                    val response = try {
                        apolloClient(requireContext()).mutation(mutation).execute()
                    } catch (e: ApolloException) {
                        configureButton(isBooked)
                        return@repeatOnLifecycle
                    }

                    if (response.hasErrors()) {
                        configureButton(isBooked)
                        return@repeatOnLifecycle
                    }

                    configureButton(!isBooked)
                }
            }
        }
    }
}
