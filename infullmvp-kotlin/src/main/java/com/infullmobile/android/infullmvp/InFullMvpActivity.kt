package com.infullmobile.android.infullmvp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

abstract class InFullMvpActivity<K : Presenter<T>, out T : PresentedActivityView<K>> : AppCompatActivity() {

    override final fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        injectIntoGraph()
        setContentView(presentedView.layoutResId)
        presentedView.bindUiElements(this, presenter)
        presenter.bind(intent.extras?: Bundle())
    }

    override final fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        presentedView.inflateMenu(menu, menuInflater)
        return true
    }

    final override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presentedView.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    abstract val presenter: K
    abstract val presentedView: T
    abstract fun injectIntoGraph()
}
