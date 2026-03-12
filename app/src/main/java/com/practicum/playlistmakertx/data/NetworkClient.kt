package com.practicum.playlistmakertx.data

import com.practicum.playlistmakertx.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}