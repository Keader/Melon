/*
 * Copyright (c) 2019.
 * João Paulo Sena <joaopaulo761@gmail.com>
 *
 * This file is part of the UNES Open Source Project.
 *
 * UNES is licensed under the MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.forcetower.uefs.feature.feedback

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.forcetower.uefs.R
import com.forcetower.uefs.core.storage.repository.FeedbackRepository
import com.forcetower.uefs.core.vm.Event
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(
    private val repository: FeedbackRepository,
    private val context: Context
) : ViewModel() {
    private val _sendFeedback = MediatorLiveData<Event<Boolean>>()
    val sendFeedback: LiveData<Event<Boolean>>
        get() = _sendFeedback

    private val _textError = MutableLiveData<Event<String?>>()
    val textError: LiveData<Event<String?>>
        get() = _textError

    @MainThread
    fun onSendFeedback(text: String?) {
        if (text.isNullOrBlank()) {
            _textError.value = Event(context.getString(R.string.feedback_text_empty))
        } else {
            _textError.value = Event(null)
            val source = repository.onSendFeedback(text)
            _sendFeedback.addSource(source) {
                _sendFeedback.value = Event(it)
                _sendFeedback.removeSource(source)
            }
        }
    }
}
