package com.example.shibegallery.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.shibegallery.NavigationIconClickListener;
import com.example.shibegallery.R;
import com.example.shibegallery.adapter.ShibeAdapter;
import com.example.shibegallery.databinding.ActivityBackdropBinding;
import com.example.shibegallery.databinding.ActivityMainBinding;
import com.example.shibegallery.viewmodel.MainViewModel;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements ShibeAdapter.OnImageClickedListener {

    private static final String[] IMAGE_OPTIONS = new String[]{"shibes", "cats", "birds"};
    private static final float DEFAULT_COUNT = 10f;
    private ActivityMainBinding binding;
    private ActivityBackdropBinding backSheetBinding;
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        backSheetBinding = ActivityBackdropBinding.bind(binding.getRoot());
        setContentView(binding.getRoot());
        vm = new ViewModelProvider(this).get(MainViewModel.class);

        setUpToolbar();
        setUpBackSheet();
        //setUpFrontSheet();
        setUpObservers();
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.toolbar);
        /*NavigationIconClickListener listener = new NavigationIconClickListener(
                this,
                binding.imageGrid,
                new AccelerateDecelerateInterpolator(),

        );*/

        binding.toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                binding.imageGrid,
                new AccelerateDecelerateInterpolator(),
                (NavigationIconClickListener.OnBackDropListener) isShown -> {
                    int count = Math.round(backSheetBinding.rangeSlider.getValues().get(0));
                    if (!isShown) loadData(count);

                },
                ContextCompat.getDrawable(this, R.drawable.ic_nav),
                ContextCompat.getDrawable(this, R.drawable.ic_close)
        ));
    }

    private void setUpBackSheet() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.item_dropdown, IMAGE_OPTIONS
        );
        backSheetBinding.filledExposedDropdown.setAdapter(adapter);
        backSheetBinding.filledExposedDropdown.setText(IMAGE_OPTIONS[0], false);

        backSheetBinding.rangeSlider.setValues(DEFAULT_COUNT);
        int count = Math.round(backSheetBinding.rangeSlider.getValues().get(0));
        backSheetBinding.sliderLabel.setText(getString(R.string.dynamic_count, count));
        backSheetBinding.rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            backSheetBinding.sliderLabel.setText(getString(R.string.dynamic_count, Math.round(value)));
            updateTitle(Math.round(value));
        });
        loadData(count);
    }

    private void setUpFrontSheet() {
        ShapeAppearanceModel model = ShapeAppearanceModel
                .builder()
                .setTopLeftCornerSize(80)
                .setTopLeftCorner(new CutCornerTreatment())
                .setTopRightCornerSize(0)
                .setBottomLeftCornerSize(0)
                .setBottomRightCornerSize(0)
                .build();

        MaterialShapeDrawable drawable = new MaterialShapeDrawable(model);
        ColorStateList colorStateList = ColorStateList.valueOf(getThemeColor());
        drawable.setFillColor(colorStateList);
        binding.imageGrid.setBackground(drawable);
    }

    private int getThemeColor() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void setUpObservers() {
        vm.getImageUrls().observe(this, urls -> {
            ShibeAdapter adapter = ((ShibeAdapter) binding.recyclerView.getAdapter());
            if (urls != null && adapter != null && !urls.isEmpty()) {
                adapter.loadImage(urls);
            }
        });
    }

    private void setUpRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(new ShibeAdapter(this));
    }

    private void loadData(int count) {
        String path = updateTitle(count);
        vm.fetchShibeImageUrls(path, count);
    }

    @NotNull
    private String updateTitle(int count) {
        Editable selection = backSheetBinding.filledExposedDropdown.getText();
        String path = selection.toString();
        String title = getString(R.string.dynamic_title, count, path);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        return path;
    }

    @Override
    public void imageSelected(String url) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }
}