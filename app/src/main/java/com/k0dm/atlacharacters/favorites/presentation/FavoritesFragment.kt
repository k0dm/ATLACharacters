package com.k0dm.atlacharacters.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.k0dm.atlacharacters.core.ProvideRepresentative
import com.k0dm.atlacharacters.databinding.FragmentFavoritesBinding
import com.k0dm.atlacharacters.favorites.presentation.adapter.FavoritesAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var representative: FavoritesRepresentative
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        representative = (requireActivity() as ProvideRepresentative)
            .provideRepresentative(FavoritesRepresentative::class.java)
        favoritesAdapter = FavoritesAdapter(representative)
        binding.favoritesRecyclerView.adapter = favoritesAdapter

        representative.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(object: FavoritesUiObserver {
            override fun updateUi(uiState: FavoritesUiState) {
                uiState.update(favoritesAdapter)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
