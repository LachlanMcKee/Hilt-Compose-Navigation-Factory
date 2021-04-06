package net.lachlanmckee.hilt.compose.navigation.factory.feature1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
internal class Feature1ViewModel @Inject constructor() : ViewModel() {

  private val stateFlowable = MutableStateFlow(State())

  fun incrementCount() {
    stateFlowable.value = stateFlowable.value.let { oldState ->
      oldState.copy(count = oldState.count + 1)
    }
  }

  val state: LiveData<State> = stateFlowable.asLiveData(viewModelScope.coroutineContext)

  data class State(val count: Int = 0)
}
