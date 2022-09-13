package com.example.fruitsappmvpcat23.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fruitsappmvpcat23.R
import com.example.fruitsappmvpcat23.adapter.EventAdapter
import com.example.fruitsappmvpcat23.database.FruitsDatabase
import com.example.fruitsappmvpcat23.databinding.FragmentAllFruitsBinding
import com.example.fruitsappmvpcat23.model.domain.FruitDomain
import com.example.fruitsappmvpcat23.presenters.AllFruitsPresenter
import com.example.fruitsappmvpcat23.presenters.AllFruitsPresenterImpl
import com.example.fruitsappmvpcat23.presenters.ViewContractAllFruits
import okhttp3.internal.wait

private const val TAG = "AllFruitsFragment"

class AllFruitsFragment : Fragment(), ViewContractAllFruits {

    //private lateinit var viewContractAllFruits: ViewContractAllFruits

    private val eventAdapter by lazy{
        EventAdapter()
    }
    private val binding by lazy{
        FragmentAllFruitsBinding.inflate(layoutInflater)
    }
    /**
     * This is the database object you need to access the DAO
     */
    private val fruitsDatabase by lazy {
        Room.databaseBuilder(
            requireActivity().applicationContext,
            FruitsDatabase::class.java,
            "fruits-db"
        ).build()
    }

    /**
     * This is the presenter object that your view will use to interact with the business logic
     */
    private val presenter: AllFruitsPresenter by lazy {
        AllFruitsPresenterImpl(fruitsDAO = fruitsDatabase.getFruitsDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //eventBinding.getEvent()
        presenter.init(this)

        /*
        arguments?.let {
            it.getSerializable("event") as List<MyEvent>
        }?.let {
            for (counter in it) {
                eventAdapter.updateEvent(counter)
            }
        }
        */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.recyclerobject.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = eventAdapter

            Log.d(TAG, "onCreateView: entering to main recycler $adapter")
            Log.d(TAG, "onCreateView: entering to main recycler ${presenter.getAllFruits()}")
        }

        presenter.getAllFruits()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun loadingFruits(isLoading: Boolean) {
        Toast.makeText(requireContext(), "This is loading: $isLoading", Toast.LENGTH_LONG).show()
        Log.d(TAG, "loadingFruits: $isLoading")
    }

    override fun onSuccess(fruits: List<FruitDomain>) {
        Toast.makeText(requireContext(), "Success: ${fruits.first().fruitName}", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onSuccessTest: $fruits")
        eventAdapter.updateAllEvents(fruits).also {
            Log.d(TAG, "onSuccess: events updated $fruits")
        }

    }

    override fun onFailure(error: Throwable) {
        Toast.makeText(requireContext(), "FAILURE: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onFailure: ${error.localizedMessage}")
    }

    companion object{
        @JvmStatic
        fun newInstance() = AllFruitsFragment()
    }

}