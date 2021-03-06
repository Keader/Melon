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

package com.forcetower.uefs.feature.about

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.forcetower.uefs.core.model.unes.Contributor
import com.forcetower.uefs.databinding.ItemAboutContributorBinding
import com.forcetower.uefs.feature.shared.inflater

class ContributorAdapter(
    private val listener: ContributorActions? = null
) : ListAdapter<Contributor, ContributorHolder>(ContributorDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorHolder {
        val binding = ItemAboutContributorBinding.inflate(parent.inflater(), parent, false)
        return ContributorHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorHolder, position: Int) {
        holder.binding.apply {
            actions = listener
            contributor = getItem(position)
            executePendingBindings()
        }
    }
}

class ContributorHolder(val binding: ItemAboutContributorBinding) : RecyclerView.ViewHolder(binding.root)

private object ContributorDiff : DiffUtil.ItemCallback<Contributor>() {
    override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor) = oldItem == newItem
}

interface ContributorActions {
    fun onContributorClick(contributor: Contributor?)
}