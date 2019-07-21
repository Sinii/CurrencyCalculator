package com.example.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.example.R
import com.example.interfaces.ActivityDefaultBehavior
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, ActivityDefaultBehavior {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbarView)
        setSupportActionBar(toolbar)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun showError(errorText: String) {
        //todo return universe error showing solution if needed. For now toast is enough
        Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
    }

    override fun setToolbarTitle(title: String) {
        findViewById<Toolbar>(R.id.toolbarView).title = title
    }

    override fun onDestroy() {
        setSupportActionBar(null)
        super.onDestroy()
    }
}