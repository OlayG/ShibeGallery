package com.example.appkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appkotlin.NavigationIconClickListener
import com.example.appkotlin.R
import com.example.appkotlin.adapter.AnimalAdapter
import com.example.appkotlin.databinding.ActivityBackdropBinding
import com.example.appkotlin.databinding.ActivityMainBinding
import com.example.appkotlin.viewmodel.MainViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), AnimalAdapter.OnImageClickedListener {

    companion object {
        private val IMAGE_OPTIONS = arrayOf("shibes", "cats", "birds")
        private const val DEFAULT_COUNT = 10f
    }


    private lateinit var binding: ActivityMainBinding
    private lateinit var backSheetBinding: ActivityBackdropBinding

    private val vm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            binding = it
            backSheetBinding = ActivityBackdropBinding.bind(it.root)
            setContentView(it.root)
        }

        setUpToolBar()
        setUpBackSheet()
        setUpObservers()
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolBar() {
        binding.toolbar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener(NavigationIconClickListener(
                    this@MainActivity,
                    binding.imageGrid,
                    AccelerateDecelerateInterpolator(),
                    NavigationIconClickListener.OnBackDropListener { isShown ->
                        if (!isShown) loadData()
                    },
                    ContextCompat.getDrawable(context, R.drawable.ic_nav),
                    ContextCompat.getDrawable(context, R.drawable.ic_close)

            ))
        }
    }

    private fun setUpBackSheet() {
        backSheetBinding.apply {
            filledExposedDropdown.apply {
                setAdapter(ArrayAdapter(context, R.layout.item_dropdown, IMAGE_OPTIONS))
                setText(IMAGE_OPTIONS[0], false)
            }

            rangeSlider.apply {
                setValues(DEFAULT_COUNT)
                addOnChangeListener { slider, value, fromUser ->
                    backSheetBinding.sliderLabel.text = getString(R.string.dynamic_count, value.roundToInt())
                }
            }

            backSheetBinding.sliderLabel.text = getString(R.string.dynamic_count, DEFAULT_COUNT.roundToInt())

        }
    }

    private fun setUpObservers() {
        vm.urlObservable.observe(this, Observer {
            (binding.recyclerView.adapter as AnimalAdapter).loadImage(it)
        })
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = AnimalAdapter()
        }
    }

    private fun loadData() {
        val path = backSheetBinding.filledExposedDropdown.text.toString()
        val count = backSheetBinding.rangeSlider.values[0].toInt()
        supportActionBar?.title = getString(R.string.dynamic_title, count, path)
        vm.fetchImages(path, count)
    }


    override fun imageSelected(url: String) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}