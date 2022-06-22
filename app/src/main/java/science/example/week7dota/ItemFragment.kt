package science.example.week7dota

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import science.example.week7dota.adapter.DotaAdapter
import science.example.week7dota.databinding.FragmentItemListBinding
import science.example.week7dota.model.DotaModel


class ItemFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding: FragmentItemListBinding get() = _binding!!
    private lateinit var viewM: DotaModel
    private lateinit var adapter: DotaAdapter
    private lateinit var fmanager: FragmentManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        adapter = DotaAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        fmanager = requireActivity().supportFragmentManager
        viewM = ViewModelProvider(this)[DotaModel::class.java]
        viewM.onCreate(requireContext())
        viewM.listOfDota.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonApp.setOnClickListener {
                showPopupMenu(view = buttonApp)
            }
        }
        setupClickListener()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListener() {
        adapter.onItemClickListener = {
            fmanager.setFragmentResult(KEY, bundleOf(BUNDLE to it))
            fmanager.beginTransaction()
                .replace(R.id.main_activity, DotaFragment())
                .commit()
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context

        popupMenu.menu.add(0,1, Menu.NONE, context.getString(R.string.app))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                1 -> {
                    val intent = Intent(context, ApplicationActivity::class.java)
                    startActivity(intent)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object {
        const val KEY = "one"
        const val BUNDLE = "bundle"
    }
}