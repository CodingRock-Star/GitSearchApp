package com.project.gitUser.fragment.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment :Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}