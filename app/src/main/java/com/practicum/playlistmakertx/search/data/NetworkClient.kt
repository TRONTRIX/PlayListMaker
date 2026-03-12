package com.practicum.playlistmakertx.search.data

import com.practicum.playlistmakertx.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}