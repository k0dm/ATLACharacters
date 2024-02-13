package com.k0dm.atlacharacters.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.k0dm.atlacharacters.core.ProvideRepresentative
import com.k0dm.atlacharacters.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var representative: CharactersRepresentative

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        representative = (requireActivity() as ProvideRepresentative)
            .provideRepresentative(CharactersRepresentative::class.java)

        actionButton.setOnClickListener {
            representative.randomCharacter()
        }

        favoriteImageView.setOnClickListener {
            representative.changeFavoriteStatus()
        }

        representative.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(object: CharactersUiObserver {
            override fun updateUi(uiState: CharacterUiState) {
                uiState.show(binding)
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