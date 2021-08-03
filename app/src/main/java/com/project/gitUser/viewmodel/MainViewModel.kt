package com.project.gitUser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.gitUser.model.UserData
import com.project.gitUser.repository.GitUserRepository
import com.project.gitUser.utils.GitUserSearchResult
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : ViewModel() {

    private val repository = GitUserRepository(application)
    private val _navigateToDetailFragment = MutableLiveData<UserData>()
    val navigateToDetailFragment: LiveData<UserData>
        get() = _navigateToDetailFragment
    private val queryLiveData = MutableLiveData<String>()
    val repoResult: LiveData<GitUserSearchResult>
        get() =
            repository.searchResults

    val progressBar: LiveData<Boolean>
        get() = repository.progressBar

    fun shownAsteroidDetail() {
        _navigateToDetailFragment.value = null
    }

    fun setUserData(userData: UserData) {
        _navigateToDetailFragment.value = userData
    }

    fun searchGitRepository(queryString: String) {
        queryLiveData.value = queryString
        viewModelScope.launch {
            repository.getSearchResultStream(queryString)
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

}